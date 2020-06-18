package lect.omok.client;

import javax.swing.*;

import lect.omok.User;
@SuppressWarnings("serial")
public class UserList extends JList<User>{
	public UserList() {
		setModel(new DefaultListModel<User>());
	}
	public void addUser(User user) {
		DefaultListModel<User> model = (DefaultListModel<User>)getModel();
		model.addElement(user);
	}
	public void removeUser(User user) {
		DefaultListModel<User> model = (DefaultListModel<User>)getModel();
		model.removeElement(user);
	}
}
