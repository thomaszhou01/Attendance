import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;

public class Register extends JFrame implements ActionListener{
	
	private final String ADMIN_KEY = "ABC123";
	
	private JTextField login, password, adminKey;
	private JButton register;
	private JTextArea usernameText, passwordText, adminText;
	private Container c = getContentPane();

	
	public Register() {
		c.setLayout(null);
	    setVisible(true);
	    toFront();
	    requestFocus();
		
	    
	    login = new JTextField();
		login.setBounds(200, 50, 200, 25);
		add(login);

		password = new JTextField();
		password.setBounds(200, 100, 200, 25);
		add(password);
		
		adminKey = new JTextField();
		adminKey.setBounds(200, 150, 200, 25);
		add(adminKey);
		
		add(createText(usernameText, "Username", new Font("Times", Font.BOLD, 20), 50, 50, 100, 30));
		add(createText(passwordText, "Password", new Font("Times", Font.BOLD, 20), 50, 100, 100, 30));
		add(createText(adminText, "Admin Key", new Font("Times", Font.BOLD, 20), 50, 150, 100, 30));

		
		
		register = new JButton("Register");
		register.setBounds(100, 300, 200, 50);
		register.setBackground(Color.orange);
		register.addActionListener(this);
		
		add(register);
		setTitle("Register User");
	    setBounds(425, 78, 500, 400);
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Register")) {
			if(adminKey.getText().equals(ADMIN_KEY)) Create();
			else {
				JOptionPane.showMessageDialog(c, "The Admin Key is not correct \nPlease try again", "Incorrect Admin Key", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	public void Create() {
	    String login = this.login.getText();
	    String password = this.password.getText();
	    String passwordNew = "";
	    try {
	      RandomAccessFile loginFile = new RandomAccessFile("login.txt", "rw");
	      for (int i = 0; i < password.length(); i++) {
	        int k = password.charAt(i) + 1;
	        passwordNew = passwordNew + (char)k;
	      }
	      //puts pointer at end of file
	      loginFile.seek((int)loginFile.length());
	      loginFile.writeBytes(login + "|" + passwordNew + "\n");
	      loginFile.close();
	    } catch (IOException e) {
	      e.getMessage();
	    }
	    dispose();
	    
	  }
	
	public JTextArea createText(JTextArea a, String name, Font newFont, int boundX, int boundY, int x, int y) {
		a = new JTextArea(name);
		a.setFont(newFont);
		a.setBounds(boundX, boundY, x, y);
		a.setBackground(null);
		a.setEditable(false);
		return a;
	}
}
