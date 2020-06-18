package lect.omok;

public interface MessageListener {
	public void chatMessage(String msg);
	public void stoneLaid(int x, int y, String fromUid, String toUid);
	public void timedOut(String fromUid, String toUid);
	public void leftRoom(String uid);
	public void enteredRoom(String name, String uid);
	public void wonGame(String fromUid, String toUid);
	public void requestedGame(String fromUid, String toUid);
	public void unknownMessage(String msg);
	public void connectionLost();
	public void newEntry(String name, String uid);
	public void respondedGame(String responseCode, String fromUid, String toUid);
}
