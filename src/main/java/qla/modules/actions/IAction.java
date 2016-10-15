package qla.modules.actions;

import qla.modules.actions.exeption.ActionException;
import qla.modules.actions.models.AbstractActionCommand;

public interface IAction<T> {
	public static final String STATUS_PROP = "status";
	public static final String ERROR_STATUS = "error";
	public static final String MESSAGE_PROP = "message";
	public static final String ACTION_PROP = "action";
	public static final String SUCCESS_ACTION_PROP = "successAction";
	
	AbstractActionCommand execute(T data) throws ActionException;
	Class getRqClass();
}
