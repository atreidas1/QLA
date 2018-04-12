var modalEditor = ace.edit("source");
modalEditor.setTheme("ace/theme/monokai");
document.getElementById('source').style.fontSize='13px';
modalEditor.$blockScrolling = Infinity;
modalEditor.setShowPrintMargin(false);

var requesteditor = ace.edit("request");
requesteditor.setTheme("ace/theme/monokai");
document.getElementById('request').style.fontSize='13px';
requesteditor.$blockScrolling = Infinity;
requesteditor.getSession().setMode("ace/mode/xml");
requesteditor.setShowPrintMargin(false);

var responseEditor = ace.edit("response");
responseEditor.setTheme("ace/theme/monokai");
document.getElementById('response').style.fontSize='13px';
responseEditor.$blockScrolling = Infinity;
responseEditor.getSession().setMode("ace/mode/xml");
responseEditor.setShowPrintMargin(false);
responseEditor.setAutoScrollEditorIntoView(true);