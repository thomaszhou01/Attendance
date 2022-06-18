import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextArea;

public class Menu extends Panel implements ActionListener{
	
	private JTextArea calendarText, attendanceText;
	private JButton calendar, attendance;
	private boolean addAttendance = false;
	private boolean addCalendar = false;
	
	public Menu() {
		
		add(createText(calendarText, "Access Calendar", new Font("Times", Font.BOLD, 40), 100, 140, 350, 80));
		add(createText(attendanceText, "Access Attendance", new Font("Times", Font.BOLD, 40), 500, 140, 400, 80));
		
		calendar = new JButton("Calendar");
		calendar.setBounds(150, 450, 200, 50);
		calendar.setBackground(Color.orange);
		calendar.addActionListener(this);
        this.add(calendar);
        
        attendance = new JButton("Attendance");
        attendance.setBounds(450, 450, 200, 50);
        attendance.setBackground(Color.orange);
        attendance.addActionListener(this);
        this.add(attendance);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Calendar")) {
			addCalendar = true;
		}
		else if(e.getActionCommand().equals("Attendance")) {
			addAttendance = true;
		}
	}
	
	public boolean getCalendar() {
		return addCalendar;
	}
	
	public void setCalendar(boolean status) {
		addCalendar = status;
	}
	
	public boolean getAttendance() {
		return addAttendance;
	}
	
	public void setAttendance(boolean status) {
		addAttendance = status;
	}
	
	
}
