package lect.omok.client;
import java.awt.*;
import java.awt.event.*;
import java.net.Socket;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Document;

import lect.omok.MessageListener;
import lect.omok.User;
import static lect.omok.ProtocolAnalyzer.*;
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener, MessageListener, StoneBoardListener, ListSelectionListener{
	private StoneBoard board = new StoneBoard();
	private UserList usersList = new UserList();
	private JButton enterButton = new JButton("접속 하기");
	private JButton requestButton = new JButton("대전 신청");
	private String name, uid, toUid;
	private JTextField msgField = new JTextField();
	private boolean isPlayingGame;
	private JTextArea msgArea = new JTextArea() {
		{
			setEditable(false);
		}
		@Override
		public void append(String text) {
			if(getText().equals("")) {
				super.append(text);
			} else {
				super.append("\n" + text);
			}
			Document doc = this.getDocument();
			this.setCaretPosition(doc.getLength());
		}
	};
	public static final String CONNECT = "접속 하기";
	public static final String DISCONNECT = "연결 끊기";
	private MessageHandler messageHandler;
	public GamePanel() {
		super(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;	gbc.gridy = 0;
		gbc.gridheight = 6;
		this.add(board, gbc);
		
		gbc.gridx = 1;	gbc.gridy = 0;	gbc.gridwidth = 2; gbc.gridheight = 1;
		this.add(new JLabel("사용자 명단"), gbc);
		
		gbc.gridy++; gbc.weighty = .1; gbc.weightx = .1;
		gbc.fill = GridBagConstraints.BOTH;
		this.add(new JScrollPane(usersList), gbc);
		
		gbc.gridy++;
		gbc.weighty = 0; gbc.fill = GridBagConstraints.NONE;
		this.add(new JLabel("메시지"), gbc);
		
		gbc.gridy++; gbc.weighty = .1; gbc.fill = GridBagConstraints.BOTH;
		this.add(new JScrollPane(msgArea), gbc);
		
		gbc.gridy++; gbc.weighty = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(msgField, gbc);
		
		
		gbc.gridy++; gbc.gridwidth = 1; gbc.weighty = 0.1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weighty = 0; gbc.weightx = 0.05;
		this.add(enterButton, gbc);
		
		gbc.gridx++;
		this.add(requestButton, gbc);
		Dimension preferredSize = board.getPreferredSize();
		setPreferredSize(new Dimension((int)(preferredSize.width * 1.5), preferredSize.height));
		
		usersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		usersList.addListSelectionListener(this);
		msgField.addActionListener(this);
		enterButton.addActionListener(this);
		requestButton.addActionListener(this);
		requestButton.setEnabled(false);
		uid = new java.rmi.server.UID().toString();
		board.addStoneBoardListener(this);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String  cmd = e.getActionCommand();
		if(e.getSource() == msgField) {
			String text = msgField.getText();
			if(!text.trim().equals("")) {
				msgField.setText("");
				sendChat(text);
			}
		} else if(cmd == CONNECT) {
			name = JOptionPane.showInputDialog(null, "Enter Omok User Name");
			if(name == null || name.trim().equals("")) return;
			try {
				connect();
				enterButton.setText(DISCONNECT);
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "연결에 실패하였습니다.", "연결 오류", JOptionPane.WARNING_MESSAGE);
			}
		} else if(cmd == DISCONNECT) {
			disconnect();
			enterButton.setText(CONNECT);
		} else {//request button
			sendRequest();
		}		
	}
	private void sendRequest() {
		if(messageHandler != null) {
			User user = usersList.getSelectedValue();
			messageHandler.sendMessage(formatMessage(CMD_REQUEST_GAME, uid, user.getUid()));
		}
	}
	private void connect() throws Exception {	
		Socket socket = new Socket("localhost", 1223);
		messageHandler = new MessageHandler(socket, this);
		messageHandler.sendMessage(formatMessage(CMD_ENTER_ROOM, name, uid));
	}
	private void disconnect() {
		if(messageHandler != null) {
			messageHandler.closeHandler();
		}
	}
	private void sendChat(String text) {
		if(messageHandler != null)
		messageHandler.sendMessage(formatMessage(CMD_CHAT, String.format("[%s] %s", name,text)));
	}

	@Override
	public void chatMessage(String msg) {
		msgArea.append(msg);
	}

	@Override
	public void stoneLaid(int x, int y, String fromUid, String toUid) {
		System.out.println("stoneLaid");
		board.oponentLaidStoneAt(x, y);
	}

	@Override
	public void timedOut(String fromUid, String toUid) {
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized void leftRoom(final String uid) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				User user = getUser(uid);
				DefaultListModel<User> model = (DefaultListModel<User>)usersList.getModel();
				model.removeElement(user);
			}
		});
		
	}

	@Override
	public void enteredRoom(String name, String uid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void wonGame(String fromUid, String toUid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestedGame(String fromUid, String toUid) {
		if(isPlayingGame) {
			if(messageHandler != null) messageHandler.sendMessage(formatMessage(CMD_RESPONSE_GAME, "N", uid, toUid));
		}
		
		User user = getUser(fromUid);
		int yesNo = JOptionPane.showConfirmDialog(null, (user != null? user.getName(): "") + " 님이 대전요청을 하였습니다.\n수락하시겠습니까?", "대전 요청", JOptionPane.YES_NO_OPTION);
		String res;
		if(yesNo == JOptionPane.YES_OPTION) {
			this.toUid = fromUid;
			res = "Y";
		} else {
			res = "N";
		}
		if(messageHandler != null) messageHandler.sendMessage(formatMessage(CMD_RESPONSE_GAME, res, uid, fromUid));
		if(res.equals("Y")) {
			board.startGame(StoneBoard.WHITE_STONE, user.getName());
		}
	}
	@Override
	public void respondedGame(String responseCode, String fromUid, String toUid) {
		if(!isPlayingGame) {
			this.toUid = fromUid;
			User user = getUser(fromUid);
			String name = user != null? user.getName() : "";
			if(responseCode.equals("Y")) {
				isPlayingGame = true;
				board.startGame(StoneBoard.BLACK_STONE, name);
			} else {
				board.showMessage(name + " 이 요청 거절", 3000);
			}
		}
	}
	@Override
	public void unknownMessage(String msg) {
		
	}
	@Override
	public void connectionLost() {
		System.out.println("connectionLost");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				enterButton.setText(CONNECT);
				DefaultListModel<User> model = (DefaultListModel<User>)usersList.getModel();
				model.removeAllElements();
				msgArea.setText("");
			}
		});
		messageHandler = null;
	}

	@Override
	public void newEntry(final String name, final String uid) {
		if(this.uid.equals(uid)) return; 
		final DefaultListModel<User> model = (DefaultListModel<User>)usersList.getModel();
		User user;
		int size = model.getSize();
		for(int i = 0; i < size; i++) {
			user = model.get(i);
			if(user.getUid().equals(uid)) {
				return;
			}
		}
		SwingUtilities.invokeLater(new Runnable () {
			public void run() {
				User user = new User();
				user.setName(name);
				user.setUid(uid);
				model.addElement(user);
			}
		});
		
	}

	@Override
	public void wonGame() {//FROMUID+DEL+TOUID
		if(messageHandler != null)
			messageHandler.sendMessage(formatMessage(CMD_WIN, uid, toUid));
	}

	@Override
	public void laidStoneAt(int x, int y, int color) {//X,Y+DEL+FROMUID,TOUID
		if(messageHandler != null) {
			messageHandler.sendMessage(formatMessage(CMD_STONE_LAID, x,y, uid, toUid));
		}
	}
	
	private synchronized User getUser(String uid) {
		User user = null;
		DefaultListModel<User> model = (DefaultListModel<User>)usersList.getModel();
		int size = model.size();
		for(int i = 0; i < size; i++) {
			user = model.get(i);
			if(user.getUid().equals(uid)) {
				break;
			}
		}
		return user;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting()) return;
		User user = usersList.getSelectedValue();
		requestButton.setEnabled(user != null);
	}

}
