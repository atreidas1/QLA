package qla.modules.actions.exeption;

public class ActionException extends Exception{

	private static final long serialVersionUID = 1L;

	public ActionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ActionException(String string) {
		super(string);
	}
	
}
