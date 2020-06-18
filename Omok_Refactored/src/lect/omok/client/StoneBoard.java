package lect.omok.client;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class StoneBoard extends JPanel implements MouseListener {
	public static final int NONE = 0;
	public static final int WHITE_STONE = 1;
	public static final int BLACK_STONE = 2;
	private int size, cell;
	private static Color bgColor = new Color(192, 161, 72);
	private int stoneColor;
	private int prefSize;
	private int [][] map;
	private boolean myTurn;
	private String msg, opponentName;
	private Font msgFont;
	private ArrayList<StoneBoardListener> listeners = new ArrayList<StoneBoardListener>();
	public StoneBoard() { this(15, 30); }
	public StoneBoard (int size, int cell) {
		super(true);
		this.size = size; 
		this.cell = cell;
		prefSize = size * cell + cell;
		map = new int[size][size];
		msgFont = new Font("Dialog", Font.BOLD, cell * 1);
		Dimension dim = new Dimension(prefSize, prefSize);
		setPreferredSize(dim);
		setMinimumSize(dim);
		setMaximumSize(dim);
		addMouseListener(this);
	}
	public void addStoneBoardListener(StoneBoardListener listener) {
		listeners.add(listener);
	}
	private void resetGame() {
		for(int x  = 0; x < size; x++) {
			for(int y = 0; y < size; y++) {
				map[x][y] = NONE;
			}
		}
		myTurn = false;
		repaint();
	}
	public void paintComponent(Graphics g) {
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
		g.setColor(bgColor);
		g.fillRect(0, 0, prefSize, prefSize);
		drawLines(g);
		if(msg != null) {
			g.setFont(msgFont);
			FontMetrics fm = g.getFontMetrics(msgFont);
			int stringWidth = fm.stringWidth(msg);
			g.drawString(msg, (prefSize)/2 - (stringWidth)/2, prefSize/2 + fm.getHeight()/2);
		}
		drawStones(g);
	}
	private void drawLines(Graphics g) {
		g.setColor(Color.BLACK);
		for (int i = 1; i <= size; i++) {
			g.drawLine(cell, i * cell, cell * size, i * cell);
			g.drawLine(i * cell, cell, i * cell, cell * size);
		}
	}
	private void drawStones(Graphics g) {
		int y;
		for(int x = 0; x < size; x++) {
			for(y = 0; y < size; y++ ) {
				switch(map[x][y]) {
					case BLACK_STONE:
						g.setColor(Color.BLACK);
						g.fillOval((x + 1) * cell - cell/2, (y + 1) * cell - cell/2, cell, cell);
						break;
					case WHITE_STONE:
						g.setColor(Color.WHITE);
						g.fillOval((x +1) * cell - cell/2, (y + 1) * cell - cell/2, cell, cell);
						break;
					case NONE:
					default:
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(!myTurn) return;
		Point p = e.getPoint();
		int x = (int)Math.round(p.getX()/cell) -1;
		int y = (int)Math.round(p.getY()/cell) -1;
		
		if(x < 0 || x > size || y < 0 || y > size || map[x][y] != NONE) return;
		myTurn = false;
		map[x][y] = stoneColor;
		for(StoneBoardListener listener : listeners) {
			listener.laidStoneAt(x, y, stoneColor);
		}
		repaint();
		if(checkWin(x, y, stoneColor)) {
			for(StoneBoardListener listener : listeners) {
				listener.wonGame();
			}
			wonGame(x, y);
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

	public void oponentLaidStoneAt(int x, int y) {
		map[x][y] = (stoneColor == BLACK_STONE) ? WHITE_STONE: BLACK_STONE;
		repaint();
		myTurn = true;
	}
	public void openentWonGame(int x, int y) {
		oponentLaidStoneAt(x, y);
		JOptionPane.showMessageDialog(null, (stoneColor == BLACK_STONE ? "Èæ ÆÐ!!" :"¹é ÆÐ!!"));
		resetGame();
	}
	public void wonGame(int x, int y) {
		JOptionPane.showMessageDialog(null, (stoneColor == BLACK_STONE ? "Èæ ½Â!!" :"¹é ½Â!!"));
		resetGame();
	}
	
	public void startGame(int color, String name) {
		this.opponentName = name;
		stoneColor = color;
		myTurn = (stoneColor == BLACK_STONE);
		showMessage(name + "°ú ´ëÀü ½ÃÀÛ", 3500);
	}
	public void showMessage(String str, final int duration) {
		msg = str;
		repaint();
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(duration);
					msg = null;
					repaint();
				} catch(Exception e) {}
			}
		}).start();
	}
	private int count(int x, int y, int clr, int vX, int vY) {
		int count = 1;
		try {
			for(;map[x + count * vX][y + count * vY] == clr; count++);
		} catch(ArrayIndexOutOfBoundsException e) {//when trying to acces index that is out of bounds of the map array, can safely ignore it
		}
		return count -1;
	}
	private boolean checkWin(int x, int y, int clr) {
		return (count(x, y, clr, 1, 0) + count(x, y, clr, -1, 0)) == 4 
			|| (count(x, y, clr, 0, 1) + count(x, y, clr, 0, -1)) == 4 
			|| (count(x, y, clr, 1, -1) + count(x, y, clr, -1, 1)) == 4 
			|| (count(x, y, clr, -1, -1) + count(x, y, clr, 1, 1)) == 4;
	}
}
