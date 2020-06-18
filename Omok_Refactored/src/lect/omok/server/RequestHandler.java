package lect.omok.server;
import java.io.*;
import java.net.*;

import lect.omok.MessageListener;
import lect.omok.ProtocolAnalyzer;
public class RequestHandler implements Runnable, MessageListener {
	private DataInputStream dis;
	private DataOutputStream dos;
	private Socket socket;
	private HandlerManager handlerManager;
	private String uid, name, message;
	public RequestHandler(Socket socket) throws IOException {
		this.socket = socket;
		try {
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			handlerManager = HandlerManager.getInstance();
			handlerManager.addHandler(this);
		} catch(IOException e) {
			closeHandler();
			throw e;
		}
	}
	public void run() {
		while(true) {
			try {
				message = dis.readUTF();
				ProtocolAnalyzer.analyze(message, this);
			} catch(IOException e) {
				closeHandler();
				break;
			}
		}
	}
	public void sendMessage(String msg) {
		try {
			dos.writeUTF(msg);
		} catch(IOException e) {
			closeHandler();
		}
	}
	private void closeHandler() {
		try {
			handlerManager.removeHandler(this);
			socket.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	public String getName() { return name; }
	public String getUID() { return uid; }
	@Override
	public void chatMessage(String msg) {
		System.out.println("chatMessage: " + message);
		handlerManager.broadcast(message);
	}
	@Override
	public void stoneLaid(int x, int y, String fromUid, String toUid) {
		System.out.println(message);
		handlerManager.sendMessageTo(toUid, message);
	}
	@Override
	public void timedOut(String fromUid, String toUid) {
		handlerManager.sendMessageTo(toUid, message);
	}
	@Override
	public void leftRoom(String uid) {
		handlerManager.broadcast(message);
		
	}
	@Override
	public void enteredRoom(String name, String uid) {
		this.name = name;
		this.uid = uid;
		handlerManager.broadcastEntryInfo();
	}
	@Override
	public void wonGame(String fromUid, String toUid) {
		handlerManager.sendMessageTo(toUid, message);
	}
	@Override
	public void requestedGame(String fromUid, String toUid) {
		handlerManager.sendMessageTo(toUid, message);
	}
	@Override
	public void unknownMessage(String msg) {
		throw new IllegalArgumentException("unknown message: " + message);
		
	}
	@Override
	public void connectionLost() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void newEntry(String name, String uid) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void respondedGame(String responseCode, String fromUid, String toUid) {
		handlerManager.sendMessageTo(toUid, message);
	}
}
