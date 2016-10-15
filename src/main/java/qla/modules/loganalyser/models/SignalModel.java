package qla.modules.loganalyser.models;

import java.io.Serializable;

public class SignalModel extends LogModel implements Serializable{
	private static final long serialVersionUID = 4745470349469522718L;
	private String system;
	private String signalName;
	private String service;
	private String errors;
	private String warnings;
	private String type;
	//Can be XML,JSON,TEXT
	private String contentType;

	public SignalModel(int id, int line, String rqRsName, String servantName, String source, String errors,
			String warnings) {
		super();
		signalName = rqRsName;
		this.service = servantName;
		this.source = source;
		this.errors = errors;
		this.warnings = warnings;
	}

	public SignalModel() {
		system = "";
		signalName = "";
		service = "";
		source = "";
		errors = "";
		warnings = "";
		type = "";
		contentType = "TEXT";
	}

	public String getRqRsName() {
		return signalName;
	}

	public SignalModel setRqRsName(String rqRsName) {
		signalName = rqRsName;
		return this;
	}

	public String getServantName() {
		return service;
	}

	public SignalModel setServantName(String servantName) {
		this.service = servantName;
		return this;
	}

	public String getSource() {
		return source;
	}

	public String getErrors() {
		return errors;
	}

	public SignalModel setErrors(String errors) {
		this.errors = errors;
		return this;
	}

	public String getWarnings() {
		return warnings;
	}

	public SignalModel setWarnings(String warnings) {
		this.warnings = warnings;
		return this;
	}

	public String getSystem() {
		return system;
	}

	public SignalModel setSystem(String system) {
		this.system = system;
		return this;
	}

	public SignalModel setType(String type) {
		this.type = type;
		return this;
	}

	public String getType() {
		return type;
	}

	public String getContentType() {
		return contentType;
	}

	public SignalModel setContentType(String contentType) {
		this.contentType = contentType;
		return this;
	}
	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(id + "\n");
		stringBuffer.append(lineNumber + "\n");
		stringBuffer.append(system + "\n");
		stringBuffer.append(type + "\n");
		stringBuffer.append(signalName + "\n");
		stringBuffer.append(service + "\n");
		stringBuffer.append(source + "\n");
		stringBuffer.append(errors + "\n");
		stringBuffer.append(warnings + "\n");
		return stringBuffer.toString();
	}

}
