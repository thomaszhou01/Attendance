import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JOptionPane;
import javax.swing.*;


public class Login extends Panel implements ActionListener{
	
	private JTextField login, password;
	private JTextArea logText, pasText;
	private JButton register, loginButton;
	private boolean loggedIn = false;
	
	public Login() {

		login = new JTextField();
		password = new JTextField();
		
		login.setBounds(600, 140, 400, 50);
		password.setBounds(600, 290, 400, 50);
		
		add(createText(logText, "Login Username", new Font("Times", Font.BOLD, 40), 100, 140, 350, 80));
		add(createText(pasText, "Password", new Font("Times", Font.BOLD, 40), 100, 290, 350, 80));
		add(login);
		add(password);
		
		register = new JButton("Register");
		register.setBounds(150, 550, 200, 50);
		register.setBackground(Color.orange);
		register.addActionListener(this);
        this.add(register);
		
        loginButton = new JButton("Log In");
        loginButton.setBounds(550, 550, 200, 50);
        loginButton.setBackground(Color.orange);
        loginButton.addActionListener(this);
        this.add(loginButton);
		
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Register")) {
			try {
				RandomAccessFile login = new RandomAccessFile("login.txt", "rw");
				new Register();
			} catch (IOException e1) {
				e1.getMessage();
			}
		}
		else if(e.getActionCommand().equals("Log In")) {
			LogIntoProgram();
		}
	}
	
	public void LogIntoProgram() {
		boolean loginStatus = false;
		try {
			RandomAccessFile main = new RandomAccessFile("login.txt", "r");
			if (main.length() != 0L) {
				for(int i = 0; i < countLineBufferedReader("login.txt"); i++) {
					String line = main.readLine();
					String name = line.substring(0, line.indexOf("|"));
					String pass = line.substring(line.indexOf("|") + 1);
					String pass1 = "";
					for (int j = 0; j < pass.length(); j++) {
						int k = pass.charAt(j) - 1;
						pass1 = pass1 + (char)k;
					}
					if (name.equals(login.getText()) && pass1.equals(password.getText())) {
						loggedIn = true;
						loginStatus = true;
						break;
					}
				}
				if(!loginStatus) {
					JOptionPane.showMessageDialog(this, "Username or Password is incorrect", "Incorrect credentials", JOptionPane.WARNING_MESSAGE);
				}
			}
			main.close();
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Please register a username and password", "Registration Required", JOptionPane.WARNING_MESSAGE);
			e.getMessage();
		}
		
	}
	
	public boolean getLoginStatus() {
		return loggedIn;
	}
	
	public void setLoginStatus(boolean status) {
		loggedIn = status;
	}

}
