var log = console.log;
var dir = console.dir;
appData = {
  logfiles: [],
}

uiElements = {
  parsedLogsContainer: $("#parsed-logs"),
  logFilesContainer: $("#logfiles"),
  jsessionIdInp: $("#JSESSIONID"),
  threadInp: $("#thread"),
  currentLog: $("#logname"),
  totalSignals: $("#total-signals"),
  totalWarnings: $("#total-warnings"),
  totalErrors: $("#total-errors"),
  totalExceptions: $("#total-exceptions"),
  signalsTable: $("#analyse-result-table table.body tbody"),
  sourceModal: $("#signal-source"),
  progressBar: $("#progress-modal"),
  exceptionsModal: $("#exception-source"),
  signalNameInp: $("#signalInp"),
  searchContent: $("#searchContent")
},

backendActions = {
  SELECT_FOLDER_ACTION : "selectFolder",
  GET_LOG_INFO_ACTION: "GET_LOG_INFO_ACTION",
  GET_CONFIG_ACTION : "getConfig",
  SAVE_PROPERTY_ACTION : "saveProperty",
  PARSE_LOG_ACTION : "parseLog",
  READ_FLOW_ACTION : "readFlowAction",
  DEFINE_THREAD_ACTION : "DEFINE_THREAD_ACTION",
  ANALYSE_LOGFILE_ACTION : "ANALYSE_LOGFILE_ACTION",
  SIGNAL_INFO_ACTION : "SIGNAL_INFO_ACTION",
  OPEN_IN_NOTEPAD_ACTION : "OPEN_IN_NOTEPAD_ACTION",
  DELETE_FILES_ACTION: "DELETE_FILES_ACTION",
  READ_SERVERS_ACTION: "READ_SERVERS_ACTION",
  RUN_PUTTY_ACTION: "RUN_PUTTY_ACTION",
  EXECUTE_SSH_COMMAND_ACTION: "EXECUTE_SSH_COMMAND_ACTION",
  LOAD_EXCEPTIONS_ACTION: "LOAD_EXCEPTIONS_ACTION",
};

var commands = {
  
  /*
  *Function that says backend to open given signal source in notepad.
  *@param {signalId:Integer}
  */
  openSignalInNotepad: function(signalId) {
      var data = {
        signalId: signalId,
        parsedfileName: uiElements.signalsTable.attr("parsedfile")
      };
      app.sendAction(backendActions.OPEN_IN_NOTEPAD_ACTION, data);
  },
  
  /*
  * Select all text in container with id that given in attribute "container"
  * of button.
  * For example:
  * If ve have button:
  * <button onclick="commands.selectTextInContainer(event)" container="signal-source-code">
  * Then on click will select all text in container with id="signal-source-code"
  */
  selectTextInContainer: function(event) {
   var doc = document;
   var element = doc
   .getElementById(event.currentTarget.getAttribute("container"))
   if (doc.body.createTextRange) {
       var range = document.body.createTextRange();
       range.moveToElementText(element);
       range.select();
   } else if (window.getSelection) {
       var selection = window.getSelection();        
       var range = document.createRange();
       range.selectNodeContents(element);
       selection.removeAllRanges();
       selection.addRange(range);
   }
  },
  
  higlightRow: function(event){
    var row = event.target;
    while("TR" != row.nodeName) {
      row = row.parentElement;
    }
    uiElements.signalsTable
      .find(".selected-row").removeClass("selected-row");
    $(row).addClass("selected-row");
  },
  
  LoadExceptionSource: function(id){
    var target = $("#exception-" + id);
    var isCollapsed = target.hasClass("in");
    if(isCollapsed){
      target.collapse("hide");
    } else {
      app.sendAction(backendActions.LOAD_EXCEPTIONS_ACTION, {exceptionId: id,
                                                            parsedfile: uiElements.signalsTable.attr("parsedfile"),
                                                            successAction: "exceptionLoaded"});
    }
  },
  
  exceptionLoaded: function(data){
    var exeption = data.exception;
    var id = exeption.id;
    $("#exception-" + id + " pre").text(exeption.source);
    
    $("#exception-" + id).collapse("show");
  },
  
  /*
  * Search in signals by content.
  */
  searchInSignals: function(){
    var rq = {
      successAction: "buildRqRsTable",
      searchContent: uiElements.searchContent.val(),
      infoFile: uiElements.signalsTable.attr("parsedfile")
    }
    app.sendAction(backendActions.GET_LOG_INFO_ACTION, rq);
  },
  
  filterRowsByClass: function(event) {
    var element = $(event.target);
    var isHide = "true" == element.attr("is-hide");
    var classname = element.attr("classname");
    var rows = uiElements.signalsTable.children();
    if(isHide){
      rows.each(function(index, row){
        $(row).show();
      })
      element.attr("is-hide", "false");
    } else {
      rows.each(function(index, row){
        if(!$(row).hasClass(classname)){
          $(row).hide();
        }
      })
       element.attr("is-hide", "true");
    }
  },
  
  filter: function(event){
    var input = $(event.target);
    var selector = input.attr("filter-by");
    var inpValue = input.val();
    console.log(inpValue)
    var rows = uiElements.signalsTable.children();
    var pattern = new RegExp(inpValue, "i");
    var filterName = input.attr("filter-name");
    for(var i = 0; i< rows.length; i++) {
      var row =  $(rows[i]);
      var cellValue = row.find(selector).text();
      var hiddenBy = row.attr("hidden-by");
      if(cellValue.match(pattern)) {
        if(hiddenBy) {
          filtersNames = hiddenBy.split(",");
          var index = filtersNames.indexOf(filterName);
          if(index != -1) {
            filtersNames.splice(index, 1);
            row.attr("hidden-by", filtersNames.join(","));
            if(!row.attr("hidden-by")){
              row.show();
            }
          }
        } else {
          row.show();
        }
      } else {
        if(hiddenBy) {
          filtersNames = hiddenBy.split(",");
          var index = filtersNames.indexOf(filterName);
          if(index == -1){
            filtersNames.push(filterName);
            row.attr("hidden-by", filtersNames.join(","));
          }
        } else {
          row.attr("hidden-by", filterName);
        }
        row.hide();
      }
    }
  },
  
  getLogInfo: function(data) {
    var infoFile;
    var jsessionId = uiElements.jsessionIdInp.val();
    var threadName =  uiElements.threadInp.val();
    if(data) {
      infoFile = data.parsedfile;
      commands.refreshParsedLogfiles();
    } else {
      infoFile = appData.choosedParsedLog || uiElements.signalsTable.attr("parsedfile");
    }
    if(infoFile){
      app.sendAction(backendActions.GET_LOG_INFO_ACTION,
                    {jSessionId: jsessionId,
                     thread: threadName,
                     infoFile: infoFile,
                     successAction: "buildRqRsTable"});
    } else {
      commands.error({message: "Choose any logfile!"});
    }
  },

  LOGFILE_PROCESSING_START: function(data){
    uiElements.progressBar.modal();
  },

  LOGFILE_PROCESSING_PROGRESS: function(data){
    $("#progress-line")
      .attr("style", "width:" + data.PROGRESS + "%");
  },

  LOGFILE_PROCESSING_END: function(data){
    var progressLine = $("#progress-line");
    progressLine.attr("style", "width:100%");
    uiElements.progressBar.modal("hide");
    progressLine.attr("style", "width:0%");
  },

  showExceptionInNotepad: function(event){
    event.stopPropagation();
    event.preventDefault();
    var lineNumber = event.target.getAttribute("line");
    commands.showLineInLog(lineNumber);
  },

  showExceptions: function(data){
    var exceptionsContainer = $("#exceptions-container");
    exceptionsContainer.empty();
    var exceptions = data.exceptions;
    for(var i = 0; i < exceptions.length; i++) {
      commands.renderTemplate(templates.EXCEPTION_TEMPLATE,
                              exceptions[i],
                              exceptionsContainer);

    }
    uiElements.exceptionsModal.modal();
  },

  loadExceptions: function(){
    var parsedFile = uiElements.signalsTable.attr("parsedfile");
    var thread = uiElements.threadInp.val();
    var numberOfExceptions = parseInt(uiElements.totalExceptions.html());
    if(parsedFile && numberOfExceptions){
      app.sendAction(backendActions.LOAD_EXCEPTIONS_ACTION,
                    {successAction: "showExceptions",
                     parsedfile: parsedFile,
                     thread: thread
                    })
    }
  },

  sendPredefinedSSHCommand: function(ip, commandKey) {
    app.sendAction(backendActions.EXECUTE_SSH_COMMAND_ACTION,
                   {commandKey: commandKey,
                    ip: ip});
  },

  restartTomcat: function(button){
    commands.sendPredefinedSSHCommand($(button).attr("ip"), "restartTomcatCmd");
  },

  restartJboss: function(button){
    commands.sendPredefinedSSHCommand($(button).attr("ip"), "restartJbossCmd");
  },

  setAccessToJbossLogs: function(button){
    commands.sendPredefinedSSHCommand($(button).attr("ip"), "accessToJbossLogsCmd");
  },

  runPutty: function(button){
    app.sendAction(backendActions.RUN_PUTTY_ACTION,{
      ip: $(button).attr("ip")
    });
  },

  loadServers: function() {
    app.sendAction(backendActions.READ_SERVERS_ACTION,{
      successAction: "loadServersHandled"
    });
  },

  loadServersHandled: function(data) {
    var serversContainer = $("#servers");
    serversContainer.empty();
    appData.servers = data.servers;
    for(var i = 0; i < appData.servers.length; i++) {
      commands.renderTemplate(templates.SERVER_BOX_TEMPLATE,  appData.servers[i], serversContainer);
    }
  },

  deletePaesedFile: function(){
    var parsedFiles = [];
    if(appData.choosedParsedLog){
      parsedFiles.push(appData.choosedParsedLog);
    }

    if(parsedFiles.length > 0){
      app.sendAction(backendActions.DELETE_FILES_ACTION,
                     {parsedFiles: parsedFiles,
                      successAction: "deleteFilesHandler"});
    }
  },

  deleteLogFile: function(){
    var logfiles = [];
    if(appData.chosedLog){
      logfiles.push(appData.chosedLog);
    }
    if(logfiles.length > 0){
      app.sendAction(backendActions.DELETE_FILES_ACTION,
                     {logfiles: logfiles,
                    successAction: "deleteFilesHandler"});
    }
  },

  deleteFilesHandler:function(data) {
    if(data.logfilesDeleted) {
      commands.refreshLogFiles();
    }
    if(data.parsedfilesDeleted){
      commands.refreshParsedLogfiles();
    }
  },

  openInNotepad: function(button){
    var data = {
      logFilename: $(button).attr("filename"),
    };
    app.sendAction(backendActions.OPEN_IN_NOTEPAD_ACTION, data);
  },

  showLineInLog: function(lineNumber){
    var data = {
      logFilename: uiElements.signalsTable.attr("logfile"),
      lineNumber: ""+lineNumber,
    };
    app.sendAction(backendActions.OPEN_IN_NOTEPAD_ACTION, data);
  },

  readSignalErrors : function(id) {
    commands.readSignal(id,
                        "showSignalErrors",
                        uiElements.signalsTable.attr("parsedfile"));
  },

  readSignalSource: function(id){
    commands.readSignal(id,
                        "showSignalSource",
                        uiElements.signalsTable.attr("parsedfile"));
  },

  readSignalWarnings : function(id) {
    commands.readSignal(id,
                        "showSignalWarnings",
                        uiElements.signalsTable.attr("parsedfile"));
  },

  showSignalErrors: function(data){
    commands.highlightCode(data.signal.errors, data.signal.contentType);
    uiElements.sourceModal.modal();
  },

  showSignalSource: function(data){
    commands.highlightCode(data.signal.source, data.signal.contentType);
    uiElements.sourceModal.modal();
  },

  highlightCode: function(source, contentType){
     var mapping = {
      "JSON" : "language-json",
      "XML"  : "language-markup",
      "TEXT" : "language-textile"
    };
    var sourceContainer = $("#signal-source-code");
    var sourcePre = $("#source");
    sourceContainer.text("");
    sourceContainer.text(source);
    sourceContainer.attr("class", mapping[contentType]);
    sourcePre.attr("class", mapping[contentType]);
    if(source.length <= 200000 && contentType) {
      Prism.highlightElement(sourceContainer[0]);
    }
  },

  showSignalWarnings : function(data) {
    var source = $("#signal-source-code");
    commands.highlightCode(data.signal.warnings, data.signal.contentType)
    uiElements.sourceModal.modal();
  },

  readSignal: function(id, successAction, parsedFile) {
    app.sendAction(backendActions.SIGNAL_INFO_ACTION, {signalId: "" + id,
                                                       parsedFile: parsedFile,
                                                       successAction: successAction});
  },

  analyseLogFile: function(){
    var choosedLog = appData.chosedLog;
    if(choosedLog){
      app.sendAction(backendActions.ANALYSE_LOGFILE_ACTION,
                    {choosedLog: choosedLog,
                     successAction: "getLogInfo"});
    } else {
      commands.error({message: "Choose any logfile!"});
    }
  },

  showRqRs : function() {
    app.sendAction(backendActions.READ_FLOW_ACTION,
                  {
                    file: appData.choosedParsedLog,
                    flowInitiator: $("#flow-initiator").val(),
                    successAction: "buildRqRsTable",
                  });
  },

  buildRqRsTable : function(data) {
    var table = uiElements.signalsTable;
    table.attr("parsedfile", data.infoFile);
    table.attr("logfile", data.logfile);
    table.empty();
    var signals = data.signals;
    var totalSignals = signals.length;
    var signalsWithWarnings = 0;
    var signalsWitherrors = 0;
    var exceptions = 0;
    for(var i = 0; i < signals.length; i++){
      var signal = signals[i];
      if(signal.warnings){
        signal.classes = "warning-row";
        signal.displayWarning = "inline";
        signalsWithWarnings+=1;
      } else {
        signal.displayWarning = "none";
      }
      if(signal.errors){
        signal.classes = "error-row";
        signal.displayError = "inline";
        signalsWitherrors+=1;
      } else {
        signal.displayError = "none";
      }
      commands.renderTemplate(templates.SIGNAL_TEMPLATE, signal, table);
    }
    $("#total-signals").html(totalSignals);
    $("#total-warnings").html(signalsWithWarnings);
    $("#total-errors").html(signalsWitherrors);
    $("#total-exceptions").html(data.numberOfExceptions);
    $("#logname").html(data.logfile);
  },

  refreshParsedLogfiles : function() {
    var parsedLogfilesFolderKey = "parsed.logs.folder";
    var succesAction = "refreshPasedFilesList";
    app.sendAction(backendActions.SELECT_FOLDER_ACTION,
                  {propKey: parsedLogfilesFolderKey, successAction: "refreshPasedFilesList"});
  },

  refreshPasedFilesList: function(data) {
    var parsedFilesList = $("#parsed-logs");
    parsedFilesList.empty();
    for(var i = 0; i < data.listOflogfiles.length; i++) {
      commands.renderTemplate(templates.PARSED_LOGFILE_TEMPLATE,
                             {fileName: data.listOflogfiles[i]},
                             parsedFilesList);
    }
  },

  chooseParsedLog : function(parsedLogContainer) {
    var container = $(parsedLogContainer);
    var filename = container.attr("filename");
    appData.choosedParsedLog = filename;
    if(appData.parsedLogContainer){
      appData.parsedLogContainer.toggleClass("selected-file");
    }
    appData.parsedLogContainer = container;
    container.toggleClass("selected-file");
  },

  defineThreadByJSessionId: function() {
    var jsessionId = $("#JSESSIONID").val();
    app.sendAction(backendActions.DEFINE_THREAD_ACTION,
                   {jsessionId: jsessionId, 
                    logFile: uiElements.signalsTable.attr("parsedfile")});
  },

  threadDefined: function(data){
    $("#thread").val(data.threadName);
  },

  start: function(data) {
    this.config();
    this.refreshLogFiles();
    this.refreshParsedLogfiles();
  },

  config: function(data) {
    $("#settings-tab").on("show.bs.tab", this.loadSettings);
    $("#servers-tab").on("show.bs.tab", this.loadServers);
  },

  loadSettings: function(data) {
    app.sendAction(backendActions.GET_CONFIG_ACTION, {});
  },

  settingsLoaded: function(data) {
    var self = this;
    appData.config = data.config;
    var settingsContainer = $("#settings");
    settingsContainer.empty();
    appData.config.forEach(function(prop, index){
      prop.index = index;
      self.renderTemplate(templates.SETTING_BOX_TEMPLATE, prop, settingsContainer);
    })
  },

  renderTemplate: function(template, props, domObj) {
    var src = commands.bindToTemplate(template, props);
    domObj.append($(src));
  },

  bindToTemplate: function(template, props){
    var src = template;
    var propKeys = Object.keys(props);
    propKeys.forEach(function(key) {
        src = src.replace(new RegExp("{{"+ key +"}}", "g"), props[key]);
      })
    return src;
  },

  saveProperty: function(property) {
    var newValue = $("input[id='" + property.key + "']").val();
    property.value = newValue;
    app.sendAction("saveProperty", property);
  },

  updateLogFiles: function(data) {
    var parsedFilesList = $("#logfiles");
    parsedFilesList.empty();
    for(var i = 0; i < data.listOflogfiles.length; i++) {
      commands.renderTemplate(templates.LOGFILE_TEMPLATE,
                             {fileName: data.listOflogfiles[i]},
                             parsedFilesList);
    }
  },

  chooseLog: function(logfileBox){
    selectedFileContainer = $(logfileBox);
    var filename = selectedFileContainer.attr("filename");
    if(appData.selectedFileContainer){
      appData.selectedFileContainer.toggleClass("selected-file");
    }
    appData.selectedFileContainer = selectedFileContainer;
    selectedFileContainer.toggleClass("selected-file");
    appData.chosedLog = filename;
  },

  parseLog: function() {
    var pathToLogfile;
    if(appData.selectedFolder){
      pathToLogfile = appData.selectedFolder;
    }
    app.sendAction(backendActions.PARSE_LOG_ACTION, {logfile: appData.chosedLog,
                                                     path: pathToLogfile});
  },

  buildFlow: function(event) {
    appData.flowFile = $("#flow-file").val();
    appData.flowInitiator = $("#flow-initiator").val();
    var data = {
      file: appData.choosedParsedLog,
      flowInitiator: appData.flowInitiator,
    }
    app.sendAction(backendActions.READ_FLOW_ACTION, data);
  },
  
  drawDiagram: function(data) {
    diagramm.drawDiagramm(data.flowData);
  },

  refreshLogFiles: function() {
    var parsedLogfilesFolderKey = "logfiles.folder";
    var succesAction = "updateLogfilesList";
    appData.chosedLog = null;
    appData.selectedFileContainer = null;
        app.sendAction(backendActions.SELECT_FOLDER_ACTION,
                  {propKey: parsedLogfilesFolderKey, successAction: "updateLogFiles"});
  },

  /**
  * Display error notification message on page.
  * @param {message:{String}, errorAction:{String}}
  * if errorAction is not null - call it.
  */
  error: function(data) {
    data.messageType = "danger";
    this.displayMessage(data);
    if(data.errorAction) {
      this[data.errorAction](data);
    }
  },

  /**
  * Display success notification message on page.
  * @param {message:{String}, successAction:{String}}
  * if successAction is not null - call it.
  */
  success: function(data) {
    data.messageType = "success";
    if(data.message){
      this.displayMessage(data);
    }
    if(data.successAction) {
      this[data.successAction](data);
    }
  },

  /**
  * Display notification message on page.
  * @param {message:{String}, messageType:{String}}
  * messageType can be "danger" or "success"
  */
  displayMessage: function(data) {
    var messageBox = $("#alert-box");
    if(!data.messageType){
      data.messageType = "default";
    }
    this.renderTemplate(templates.NOTIFICATION_TEMPLARE, data, messageBox);
  },
}

var app = {
  SERVER_URL: "ws://localhost:6565/myHandler",
  socket: null,

  establishConnection: function () {
    var socket = new WebSocket(this.SERVER_URL);
    this.socket = socket;
    socket.onerror = this.onError;
    socket.onopen = this.onOpen;
    socket.onmessage = this.onRecieve;
    socket.close = this.onclose;
  },

  onclose: function() {
    $("#connection-status-connected").attr("id","connection-status-disconnected");
    commands.error({message: "Connection lost!"});
  },

  onOpen: function(event) {
    //log(event);
    $("#connection-status-disconnected").attr("id","connection-status-connected");
  },

  onRecieve: function(event) {
    //log(event);
    var object = JSON.parse(event.data);
    commands[object.action](object);
  },

  onError: function(event) {
    $("#connection-status-connected").attr("id","connection-status-disconnected");
  },

  sendText: function(text){
    if(app.socket.readyState != 1){
      commands.error({message: "Connection lost!!!"});
      $("#connection-status-connected").attr("id","connection-status-disconnected");
      return;
    }
    this.socket.send(text);
  },

  sendAction: function(actionName, data){
    data.action = actionName;
    var message = actionName + " " + JSON.stringify(data);
    this.sendText(message);
  },

  disconnect: function(){
    this.socket.close();
     $("#connection-status-connected").attr("id","connection-status-disconnected");
  },
}

app.establishConnection();
