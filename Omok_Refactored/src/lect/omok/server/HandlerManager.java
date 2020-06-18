package lect.omok.server;
import static lect.omok.ProtocolAnalyzer.*;

import java.util.*;

import lect.omok.ProtocolAnalyzer;

public class HandlerManager {
	private Vector<RequestHandler> handlers = new Vector<RequestHandler>();
	private static HandlerManager instance = new HandlerManager();
	private HandlerManager() {}
	public static HandlerManager getInstance() { 
		return instance; 
	}
	public void addHandler(RequestHandler handler) { 
		handlers.add(handler);
		broadcast(formatMessage(CMD_ENTER_ROOM, handler.getName(), handler.getUID()));
	}
	public void removeHandler(RequestHandler handler) {
		handlers.remove(handler);
		broadcast(formatMessage(CMD_LEAVE_ROOM, handler.getUID()));
	}
	public void broadcast(String msg) {
		RequestHandler handler;
		try {
			for(int i = 0; i < handlers.size(); i++) {
				handler = handlers.get(i);
				handler.sendMessage(msg);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void broadcastEntryInfo() {
		RequestHandler handler;
		StringBuilder sb = new StringBuilder();
		try {
			for(int i = 0; i < handlers.size(); i++) {
				handler = handlers.get(i);
				if(i > 0) sb.append(ProtocolAnalyzer.DELIMETER);
				sb.append(handler.getName()).append(",").append(handler.getUID());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		broadcast(formatMessage(CMD_ENTRY_LIST, sb.toString()));
	}
	public void sendMessageTo(String toUid, String message) {
		RequestHandler handler;
		try {
			for(int i = 0; i < handlers.size(); i++) {
				handler = handlers.get(i);
				if(handler.getUID().equals(toUid)) {
					handler.sendMessage(message);
					System.out.println("sent to " + toUid + ": " + message);
					break;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
