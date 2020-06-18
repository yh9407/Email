package lect.omok.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import lect.omok.MessageListener;
import lect.omok.ProtocolAnalyzer;

public class MessageHandler implements Runnable {
	private DataOutputStream dos;
	private DataInputStream dis;
	private Socket socket;
	private boolean closed = false;
	MessageListener listener;
	public MessageHandler(Socket socket, MessageListener listener) throws IOException {
		this.listener = listener;
		try {
			this.socket = socket;
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
		} catch(IOException e) {
			closeHandler();
			throw e;
		}
		new Thread(this).start();
	}
	@Override
	public void run() {
		while(true) {
			try {
				ProtocolAnalyzer.analyze(dis.readUTF(), listener);
			} catch(IOException e) {
				closeHandler();
				break;
			}
		}
		System.out.println("Message Handler Thread down");
	}
	public void sendMessage(String msg) {
		try {
			dos.writeUTF(msg);
		} catch(IOException e) {
			closeHandler();
		}
	}
	public void closeHandler() {
		if(closed) return;
		closed = true;
		try {
			socket.close();
		} catch(IOException e) {
		} finally {
			ProtocolAnalyzer.connectionLost(listener);
		}
	}
}
