import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Frame extends JFrame{
	
	public Attendance attendance;
	public Calendar calendar;
	public Login log;
	public Menu mainMenu;
	
	
	private int W = 1200;
	private int H = 800;
	private Container c = getContentPane();

	
	private JMenuItem menuItem[] = new JMenuItem[4];


	public Frame() {
		super("String Orchestra Leader Application");
		c.setLayout(null);
		attendance = new Attendance();
		calendar = new Calendar();
		log = new Login();
		mainMenu = new Menu();
		c.add(log);
		menuSetup();
	}

	public void menuSetup() {
		this.setSize(W, H);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
    public void paint(Graphics g) {
		super.paint(g);
    	if(log.getLoginStatus()) {
    		log.setLoginStatus(false);
    		c.remove(log);
    		c.add(mainMenu);
    	}
    	if(mainMenu.getAttendance()) {
    		mainMenu.setAttendance(false);
    		c.remove(mainMenu);
    		c.add(attendance);
    	}
    	if(mainMenu.getCalendar()) {
    		mainMenu.setCalendar(false);
    		c.remove(mainMenu);
    		c.add(calendar);
    	}
    	if(attendance.getReturnToMenu()) {
    		attendance.setReturnToMenu(false);
    		c.remove(attendance);
    		c.add(mainMenu);
    	}
    	if(calendar.getReturnToMenu()) {
    		calendar.setReturnToMenu(false);
    		c.remove(calendar);
    		c.add(mainMenu);
    	}
    	repaint();
    }
	

}
