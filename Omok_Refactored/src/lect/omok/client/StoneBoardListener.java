package lect.omok.client;

public interface StoneBoardListener {
	public void wonGame();
	public void laidStoneAt(int x, int y, int color);
}
