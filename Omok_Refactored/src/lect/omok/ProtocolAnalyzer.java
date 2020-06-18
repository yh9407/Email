package lect.omok;

import java.util.StringTokenizer;

public class ProtocolAnalyzer{
	public static final String CMD_ENTER_ROOM = "A";
	public static final String CMD_LEAVE_ROOM = "B";
	public static final String CMD_REQUEST_GAME = "C";
	public static final String CMD_STONE_LAID = "D";
	public static final String CMD_TIME_OUT = "E";
	public static final String CMD_CHAT = "F";
	public static final String CMD_WIN = "G";
	public static final String CMD_ENTRY_LIST= "L";
	public static final String CMD_RESPONSE_GAME = "R";
	public static final String DELIMETER = "\n";
	private static ProtocolAnalyzer instance = new ProtocolAnalyzer();
	private ProtocolAnalyzer () {}
	public static ProtocolAnalyzer getInstance() { return instance; }
	public static void analyze(String msg, MessageListener listener) {
		String header = msg.substring(0, 1);
		if(header.equals(CMD_CHAT)) {
			listener.chatMessage(msg.substring(1));
		} else if(header.equals(CMD_STONE_LAID)) {//X,Y+DEL+FROMUID,TOUID
			int idx = msg.indexOf(DELIMETER);
			String coord = msg.substring(1, idx);
			String uids = msg.substring(idx + 1);
			
			idx = coord.indexOf(",");
			System.out.println(coord);
			int x = Integer.valueOf(coord.substring(0, idx));
			int y = Integer.valueOf(coord.substring(idx + 1));
			
			idx = uids.indexOf(",");
			String fromUid = uids.substring(0, idx);
			String toUid = uids.substring(idx + 1);
			listener.stoneLaid(x, y, fromUid, toUid);
		} else if(header.equals(CMD_LEAVE_ROOM)) {
			listener.leftRoom(msg.substring(1));
		} else if(header.equals(CMD_TIME_OUT)) {
			int idx = msg.indexOf(DELIMETER);
			String fromUid = msg.substring(1, idx);
			String toUid = msg.substring(idx + 1);
			listener.timedOut(fromUid, toUid);
		} else if(header.equals(CMD_ENTER_ROOM)) {
			int idx = msg.indexOf(DELIMETER);
			String name = msg.substring(1, idx);
			String uid = msg.substring(idx + 1);
			listener.enteredRoom(name, uid);
		} else if(header.equals(CMD_WIN)) {
			int idx = msg.indexOf(DELIMETER);
			String fromUid = msg.substring(1, idx);
			String toUid = msg.substring(idx + 1);
			listener.wonGame(fromUid, toUid);
		} else if(header.equals(CMD_REQUEST_GAME)) {
			int idx = msg.indexOf(DELIMETER);
			String fromUid = msg.substring(1, idx);
			String toUid = msg.substring(idx + 1);
			listener.requestedGame(fromUid, toUid);
		} else if(header.equals(CMD_RESPONSE_GAME)) {
			String responseCode = msg.substring(1,2);
			int idx = msg.indexOf(DELIMETER);
			String fromUid = msg.substring(2, idx);
			String toUid = msg.substring(idx+1);
			listener.respondedGame(responseCode, fromUid, toUid);
		} else if(header.equals(CMD_ENTRY_LIST)) {
			StringTokenizer st = new StringTokenizer(msg.substring(1));
			String userInfo, name, uid;
			int idx;
			while(st.hasMoreTokens()) {
				userInfo = st.nextToken();
				idx = userInfo.indexOf(",");
				name = userInfo.substring(0, idx);
				uid = userInfo.substring(idx + 1);
				listener.newEntry(name, uid);
			}
		} else {
			listener.unknownMessage(msg);
		}
	}
	public static void connectionLost(MessageListener listener) {
		listener.connectionLost();
	}
	public static String formatMessage(String cmd, Object ... args) {
		StringBuilder sb = new StringBuilder(cmd);
		if(cmd.equals(CMD_CHAT)) {//CHAT
			System.out.println(args[0]);
			sb.append(args[0]);
		} else if(cmd.equals(CMD_STONE_LAID)) {//X,Y+DEL+FROMUID,TOUID
			sb.append(args[0]).append(",").append(args[1]).append(DELIMETER).append(args[2]).append(",").append(args[3]);
		} else if(cmd.equals(CMD_LEAVE_ROOM)) {//uid
			sb.append(args[0]);
		} else if(cmd.equals(CMD_TIME_OUT)) {//FROMUID+DEL+TOUID
			sb.append(args[0]).append(DELIMETER).append(args[1]);
		} else if(cmd.equals(CMD_ENTER_ROOM)) {//NAME+DEL+UID
			sb.append(args[0]).append(DELIMETER).append(args[1]);
		} else if(cmd.equals(CMD_WIN)) {//FROMUID+DEL+TOUID
			sb.append(args[0]).append(DELIMETER).append(args[1]);
		} else if(cmd.equals(CMD_REQUEST_GAME)) {//FROMUID+DEL+TOUID
			sb.append(args[0]).append(DELIMETER).append(args[1]);
		} else if(cmd.equals(CMD_RESPONSE_GAME)) {//Y/N+FROMUID+DEL+TOUID
			sb.append(args[0]).append(args[1]).append(DELIMETER).append(args[2]);
		} else if(cmd.equals(CMD_ENTRY_LIST)) {
			sb.append(args[0]);
		} else {
			throw new IllegalArgumentException("unknown command: " + cmd);
		}
		return sb.toString();
	}
	
}
