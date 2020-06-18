package lect.omok;

public class User {
	private String name = "Unknown", uid;
	private boolean isPlayingGame;
	private String ipAddress;
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public boolean isPlayingGame() {
		return isPlayingGame;
	}
	public void setPlayingGame(boolean isPlayingGame) {
		this.isPlayingGame = isPlayingGame;
	}
	public boolean equals(Object obj) {
		if(obj instanceof User && obj != null) {
			return this.uid.equals(((User)obj).uid);
		} else {
			return false;
		}
	}
	public String toString() {
		return String.format("%s %s", name, isPlayingGame? "[∞‘¿”¡ﬂ]": "");
	}
}
