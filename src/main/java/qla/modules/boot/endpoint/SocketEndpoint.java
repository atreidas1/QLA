package qla.modules.boot.endpoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import qla.modules.actions.AbstractAction;
import qla.modules.actions.ActionHelper;
import qla.modules.actions.Actions;
import qla.modules.actions.IAction;
import qla.modules.actions.UIActions;
import qla.modules.actions.models.AbstractActionCommand;
import qla.modules.confuguration.AppConfiguration;
import qla.modules.serialiser.JsonSerialiser;


public class SocketEndpoint extends TextWebSocketHandler{
	static {
		System.out.println("SocketEndpoint");
		AppConfiguration.init();
	}
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		Map<String, String> rs = new HashMap<>();
        rs.put(IAction.ACTION_PROP, UIActions.START);
        sendJson(rs, session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
		String message = textMessage.getPayload();
		//System.out.println("[" + session.getId() + "] Message Received:\n" + message);
        try {
        	int delimitterIndex = message.indexOf(' ');
        	String actionName = message.substring(0, delimitterIndex);
    		String data = message.substring(delimitterIndex+1, message.length());
        	@SuppressWarnings("rawtypes")
			AbstractAction action = Actions.getAction(actionName);
        	AbstractActionCommand rq = (AbstractActionCommand) JsonSerialiser
        			.deserialise(data, action.getRqClass());
        	rq.setSession(session);
			AbstractActionCommand rs = 
	        action.execute(rq);
			sendJson(rs, session);
		} 
        catch (Exception e) {
        	e.printStackTrace();
        	Map<String, Object> resp= ActionHelper
        			.createErrorAction(e.getMessage());
			sendJson(resp, session);
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("afterConnectionClosed");
	}
	
	/**
     * Convert java object to JSON and send it to client.
     * @param object - object that will be converted to JSON.
     * @param session - current session.
     */
    public void sendJson(Object object, WebSocketSession session) {
    	SendMessage(JsonSerialiser.serialise(object), session);
	}
    
    /**
     * Send text message to client.
     * @param message - text message.
     * @param session  - current session.
     */
    public void SendMessage(String message, WebSocketSession session) {
    	try {
    		//System.out.println("Send message:\n" + message);
			session.sendMessage(new TextMessage(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
