package lect.omok.server;
import java.io.*;
import java.net.*;
public class Server implements  Runnable {
	private ServerSocket ss = null;
	int port = 1223;
	public Server() throws IOException {
		ss = new ServerSocket(port);
		new Thread(this).start();
	}
	public void run() {
		while(true) {
			try {
				Socket socket = ss.accept();
				new Thread(new RequestHandler(socket)).start();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String [] args ) throws Exception {
		new Server();
	}
}
