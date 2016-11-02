var templates = {
  LOGFILE_TEMPLATE:
  '<div href="#" onclick="commands.chooseLog(this)" ondblclick="commands.analyseLogFile()" class="list-group-item cursor-pointer" filename="{{fileName}}">'+
    '<i class="glyphicon glyphicon-file file-icon-box"></i>{{fileName}}'+
    '<i class="glyphicon glyphicon-eye-open open-in-notepad-button" onclick="commands.openInNotepad(this)" filename="{{fileName}}"></i>'+
  '</div>',

  PARSED_LOGFILE_TEMPLATE:'<div href="#" onclick="commands.chooseParsedLog(this)" ondblclick="commands.getLogInfo()" class="list-group-item cursor-pointer" filename="{{fileName}}"><i class="glyphicon glyphicon-file file-icon-box"></i>{{fileName}}</div>',

  SETTING_BOX_TEMPLATE:
      '<div class="setting-box">'+
        '<div class="bold">{{key}}</div>'+
        '<div class="input-group">'+
          '<input type="text" id="{{key}}" value="{{value}}" class="form-control input-sm">'+
          '<div class="input-group-btn">'+
            '<button prop-to-save="{{key}}" onclick="commands.saveProperty(appData.config[{{index}}])" type="button" class="btn btn-success btn-sm">Save</button>'+
          '</div>'+
        '</div>'+
      '</div>',

  NOTIFICATION_TEMPLARE:
    '<div class="alert alert-{{messageType}} fade in notification-message">'+
      '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>'+
      '{{message}}'+
    '</div>',

  SERVER_BOX_TEMPLATE:
      '<div class="panel panel-default server-box">'+
        '<div class="panel-heading bold text-center">'+
          '<a target="_blank" href="http://{{ip}}">{{ip}}</a>'+
            '<span class="dropdown">'+
            '<button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="pull-right"><i class="glyphicon glyphicon-menu-hamburger"></i></button>'+
            '<ul class="dropdown-menu" aria-labelledby="dLabel">'+
              '<li><a href="#" ip="{{ip}}" onclick="commands.restartTomcat(this)">Restart tomcat<a></li>'+
              '<li><a href="#" ip="{{ip}}" onclick="commands.restartJboss(this)">Restart jboss<a></li>'+
              '<li><a href="#" ip="{{ip}}" onclick="commands.setAccessToJbossLogs(this)">Set access to jboss logs<a></li>'+
              '<li><a href="#" ip="{{ip}}" onclick="commands.runPutty(this)">Run putty<a></li>'+
              '<li><a href="#" ip="{{ip}}" onclick="commands.executeCommand(this)">Execute command<a></li>'+
            '</ul>'+
          '</div>'+
        '</span>'+
          '<div class="panel-body">'+
            '<div class="bold"><a target="_blank" href="{{repoLink}}">{{branch}}</a></div>'+
            '<div class="bold">Jboss logs:</div>'+
            '<ul>'+
              '<li><a href="http://{{ip}}{{pathToJbossLogs}}matrixtdp.log" download>matrixtdp.log</a></li>'+
              '<li><a href="http://{{ip}}{{pathToJbossLogs}}matrixtdp.log.1" download>matrixtdp.log.1</a></li>'+
              '<li><a href="http://{{ip}}{{pathToJbossLogs}}matrixtdp.log.2" download>matrixtdp.log.2</a></li>'+
              '<li><a href="http://{{ip}}{{pathToJbossLogs}}matrixtdp.log.3" download>matrixtdp.log.3</a></li>'+
            '</ul>'+
            '<div class="bold">Tomcat logs:</div>'+
            '<ul>'+
              '<li><a href="http://{{ip}}{{pathToTomcatLogs}}flypal.log" download>flypal.log</a></li>'+
              '<li><a href="http://{{ip}}{{pathToTomcatLogs}}flypal.log.1" download>flypal.log.1</a></li>'+
            '</ul>'+
             '<div class="bold">TDP Logs:</div>'+
            '<ul>'+
              '<li><a href="http://{{ip}}/logs/" target="_blank">TDP Logs</a></li>'+
            '</ul>'+
            '<a href="http://{{ip}}:8080/jmx-console/HtmlAdaptor?action=updateAttributes&name=com.datalex:service=LoggerMonitor&MinimumLogLevel=3" target="_blank">Set jboss to debug level</a>'+
        '</div>'+
      '</div>',

  SIGNAL_TEMPLATE:
  '<tr class="{{classes}}">'+
    '<td class="line-number"><span class="hover-underline" onclick="commands.showLineInLog({{lineNumber}})">{{lineNumber}}</span></td>'+
    '<td class="display-child-on-hover rqrs-name"><span class="bold signal-name hover-underline" onclick="commands.readSignalSource({{id}})">{{signalName}}</span>'+
      '<i style="display: {{displayWarning}};" class="glyphicon glyphicon-warning-sign text-warning cursor-pointer" title="Show warnings." onclick="commands.readSignalWarnings({{id}})"></i>'+
      '<i style="display: {{displayError}};" class="glyphicon glyphicon-exclamation-sign text-danger cursor-pointer" title="Show errors." onclick="commands.readSignalErrors({{id}})"></i>'+
      '<i class="glyphicon glyphicon-open-file cursor-pointer visible-on-parent-hover" title="Open in notepad" onclick="commands.openSignalInNotepad({{id}})"></i>'+
    '</td>'+
    '<td class="service"><span class="service-name">{{service}}</span></td>'+
    '<td class="system"><span class="system-name">{{system}}</span></td>'+
    '<td class="action-cell">{{thread}}</td>'+
  '</tr>',

  EXCEPTION_TEMPLATE:
  '<div class="exception-name-container">'+
     '<div role="button" exception-id={{id}} data-target="#exception-{{id}}" onclick="commands.LoadExceptionSource({{id}})">'+
       '<span class="exception-ln" line="{{lineNumber}}" onclick="commands.showExceptionInNotepad(event)">Line {{lineNumber}}:</span>'+
       '<span class="exception-name">{{name}}</span>'+
    '</div>'+
  '</div>'+
  '<div class="collapse" id="exception-{{id}}">'+
    '<pre>{{source}}</pre>'+
  '</div>',
  
  EXCEPTION_IN_SIGNAL_TABLE_TEMPLATE:
  '<tr class="exception-in-signal-table">'+
    '<td class="line-number"><span class="hover-underline" onclick="commands.showLineInLog({{lineNumber}})">{{lineNumber}}</span></td>'+
    '<td class="rqrs-name"><span class="bold signal-name hover-underline" >{{name}}</span>'+
    '</td>'+
    '<td class="service"><span class="service-name"></span></td>'+
    '<td class="system"><span class="system-name"></span></td>'+
    '<td class="action-cell">{{thread}}</td>'+
  '</tr>',
}
