package qla.modules.actions;

import java.util.HashMap;
import java.util.Map;

import qla.modules.actions.exeption.ActionException;

public class Actions {
	public static final String SELECT_FOLDER_ACTION = "selectFolder";
	public static final String GET_CONFIG_ACTION = "getConfig";
	public static final String SAVE_PROPERTY_ACTION = "saveProperty";
	public static final String PARSE_LOG_ACTION = "parseLog";
	public static final String DRAW_DIAGRAMM_ACTION = "readFlowAction";
	public static final String DEFINE_THREAD_ACTION = "DEFINE_THREAD_ACTION";
	public static final String ANALYSE_LOGFILE_ACTION = "ANALYSE_LOGFILE_ACTION";
	public static final String SIGNAL_INFO_ACTION = "SIGNAL_INFO_ACTION";
	public static final String OPEN_IN_NOTEPAD_ACTION = "OPEN_IN_NOTEPAD_ACTION";
	public static final String DELETE_FILES_ACTION = "DELETE_FILES_ACTION";
	public static final String READ_SERVERS_ACTION = "READ_SERVERS_ACTION";
	public static final String RUN_PUTTY_ACTION = "RUN_PUTTY_ACTION";
	public static final String EXECUTE_SSH_COMMAND_ACTION = "EXECUTE_SSH_COMMAND_ACTION";
	private static final String LOAD_EXCEPTIONS_ACTION = "LOAD_EXCEPTIONS_ACTION";
	private static Map<String, AbstractAction> actions = new HashMap<>();
	
	@SuppressWarnings("rawtypes")
	public static AbstractAction getAction(String actionName) throws ActionException{
		if(!actions.containsKey(actionName)) {
			throw new ActionException(String.format("Action %s not found!", actionName));
		}	
		return actions.get(actionName);
	}

	public static Map<String, AbstractAction> getActions() {
		return actions;
	}

	public static void setActions(Map<String, AbstractAction> actions) {
		Actions.actions = actions;
	}
	
	
}
