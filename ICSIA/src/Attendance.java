import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.JTextComponent;

public class Attendance extends Panel implements ActionListener{
	
	private JTable table;
	private JButton add, delete, update, setAttendance, saveFile, sortList, backToMenu;
	private JTextField firstName, lastName, instrument, grade;
	private JComboBox sortColumn, sortType;
	private JTextArea title, fNameText, lNameText, instrumentText, gradeText, sortColumnText, sortTypeText;
	private String[] columnForSort = {"First Name", "Last Name", "Instrument", "Grade", "Times On Time", "Times Absent", "Times Late"};
    private String[] columnForSortType = {"Ascending", "Descending"};
	private boolean returnToMenu = false;

	private DefaultTableModel model;
	private ArrayList<Integer> listIndexes = new ArrayList<Integer>();
	private LinkedList attendanceList = new LinkedList();
	
	
	public Attendance() {
		table = new JTable(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return column==4 ? true : false;
		    }
		};
		
		table.setRowHeight(40);
		
		String[] columns = {"First Name", "Last Name", "Instrument", "Grade", "Attendance", "Times On Time", "Times Absent", "Times Late"};
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		table.setModel(model);
		
		
		TableColumn attendance = table.getColumnModel().getColumn(4);
		String[] status = {"On time","Absent","Late"};
		JComboBox comboBox = new JComboBox(status);
		attendance.setCellEditor(new DefaultCellEditor(comboBox));
		
		JScrollPane js = new JScrollPane(table);
		js.setBounds(20, 70, 1120, 450);
		
		add = new JButton("Add");
		delete = new JButton("Delete");
		update = new JButton("Update");
		setAttendance = new JButton("Set Attendance");
		saveFile = new JButton("Save");
		sortList = new JButton("Sort");
		backToMenu = new JButton("Menu");
		
		firstName = new JTextField();
		lastName = new JTextField();
		instrument = new JTextField();
		grade = new JTextField();

		
		add.setBounds(700, 540, 140, 30);
		delete.setBounds(700, 580, 140, 30);
		update.setBounds(700, 620, 140, 30);
		setAttendance.setBounds(700, 660, 140,30);
		sortList.setBounds(440, 660, 100, 30);
		saveFile.setBounds(920, 560, 200, 50);
		backToMenu.setBounds(920, 620, 200, 50);

		firstName.setBounds(140, 540, 200,25);
		lastName.setBounds(140, 580, 200,25);
		instrument.setBounds(140, 620, 200,25);
		grade.setBounds(140, 660, 200,25);
		
		add.setBackground(Color.orange);
		delete.setBackground(Color.orange);
		update.setBackground(Color.orange);
		setAttendance.setBackground(Color.orange);
		sortList.setBackground(Color.orange);
		saveFile.setBackground(Color.orange);
		backToMenu.setBackground(Color.orange);
		
		add.addActionListener(this);
		delete.addActionListener(this);
		update.addActionListener(this);
		setAttendance.addActionListener(this);
		saveFile.addActionListener(this);
		sortList.addActionListener(this);
		backToMenu.addActionListener(this);
		
		sortColumn = new JComboBox(columnForSort);
		sortColumn.setBounds(500, 580, 140, 30); 
		sortColumn.addActionListener(this);
        add(sortColumn);
		
        sortType = new JComboBox(columnForSortType);
        sortType.setBounds(500, 620, 140, 30); 
        sortType.addActionListener(this);
        add(sortType);
		
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int i = table.getSelectedRow();
				firstName.setText(model.getValueAt(i, 0).toString());
				lastName.setText(model.getValueAt(i, 1).toString());
				instrument.setText(model.getValueAt(i, 2).toString());
				grade.setText(model.getValueAt(i, 3).toString());

			}
		});
		setupText();
		readFromFile();

		add(add);
		add(delete);
		add(update);
		add(setAttendance);
		add(saveFile);
		add(sortList);
		add(backToMenu);
		
		add(firstName);
		add(lastName);
		add(instrument);
		add(grade);
		add(js);
	}
	
	
	public void setupText() {
		add(createText(title, "Attendance List", new Font("Times", Font.BOLD, 40), 20, 20, 400, 50));
		add(createText(fNameText, "First Name:", new Font("Times", Font.PLAIN, 20), 20, 540, 100,25));
		add(createText(lNameText, "Last Name:", new Font("Times", Font.PLAIN, 20), 20, 580, 100,25));
		add(createText(instrumentText, "Instrument:", new Font("Times", Font.PLAIN, 20), 20, 620, 100,25));
		add(createText(gradeText, "Grade:", new Font("Times", Font.PLAIN, 20), 20, 660, 100,25));
		add(createText(sortColumnText, "Sort Column:", new Font("Times", Font.PLAIN, 20), 380, 580, 140,25));
		add(createText(sortTypeText, "Sort type:", new Font("Times", Font.PLAIN, 20), 380, 620, 140,25));

	}

	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Add")) {
			String [] info = new String[8];
        	info[0] = firstName.getText().replace("\n", "").replace("\r", "");
        	info[1] = lastName.getText().replace("\n", "").replace("\r", "");
        	info[2] = instrument.getText().replace("\n", "").replace("\r", "");
        	info[3] = grade.getText().replace("\n", "").replace("\r", "");
        	info[5] = "0";
        	info[6] = "0";
        	info[7] = "0";
        	attendanceList.insert(attendanceList.size(), info);
        	listIndexes.add(attendanceList.size()-1);
        	model.addRow(info);
        }
		else if(e.getActionCommand().equals("Delete")) {
			int i = table.getSelectedRow();
			if(i>=0) {
				model.removeRow(i);
				
				attendanceList.delete(listIndexes.get(i));
				setCorrectListValues(listIndexes.get(i));
				listIndexes.remove(i);
			}
		}
		
		//check for bugs
		else if(e.getActionCommand().equals("Update")) {
			int i = table.getSelectedRow();
			if(i>=0) {
				model.setValueAt(firstName.getText().replace("\n", "").replace("\r", ""), i, 0);
				model.setValueAt(lastName.getText().replace("\n", "").replace("\r", ""), i, 1);
				model.setValueAt(instrument.getText().replace("\n", "").replace("\r", ""), i, 2);
				model.setValueAt(grade.getText().replace("\n", "").replace("\r", ""), i, 3);
				attendanceList.lookUp(listIndexes.get(i))[0] = firstName.getText().replace("\n", "").replace("\r", "");
				attendanceList.lookUp(listIndexes.get(i))[1] = lastName.getText().replace("\n", "").replace("\r", "");
				attendanceList.lookUp(listIndexes.get(i))[2] = instrument.getText().replace("\n", "").replace("\r", "");
				attendanceList.lookUp(listIndexes.get(i))[3] = grade.getText().replace("\n", "").replace("\r", "");

			}
		}
		else if(e.getActionCommand().equals("Set Attendance")) {
			for(int i = 0; i< table.getRowCount(); i++) {
				if(model.getValueAt(i, 4) == null);
				else if(model.getValueAt(i, 4).toString().equals("On time")) {
					model.setValueAt(Integer.parseInt(model.getValueAt(i, 5).toString())+1, i, 5);
					attendanceList.lookUp(listIndexes.get(i))[5] = (Integer.parseInt(model.getValueAt(i, 5).toString()))+"";
				}
				else if(model.getValueAt(i, 4).toString().equals("Absent")) {
					model.setValueAt(Integer.parseInt(model.getValueAt(i, 6).toString())+1, i, 6);
					attendanceList.lookUp(listIndexes.get(i))[6] = (Integer.parseInt(model.getValueAt(i, 6).toString()))+"";
				}
				else if(model.getValueAt(i, 4).toString().equals("Late")) {
					model.setValueAt(Integer.parseInt(model.getValueAt(i, 7).toString())+1, i, 7);
					attendanceList.lookUp(listIndexes.get(i))[7] = (Integer.parseInt(model.getValueAt(i, 7).toString()))+"";

				}
				model.setValueAt("", i, 4);
			}
		}
		else if(e.getActionCommand().equals("Menu")) {
			returnToMenu = true;
		}
		
		else if(e.getActionCommand().equals("Save")) {
			writeToFile();

		}
		else if(e.getActionCommand().equals("Sort")) {
			int temp = 0;
			boolean ascending = true;
			for(int i = 0; i< columnForSort.length;i++ ) {
				if(columnForSort[i].equalsIgnoreCase((String)sortColumn.getSelectedItem())) {
					temp = i;
					break;
				}
			}
			if(temp >= 4)temp++;
			if("Descending".equalsIgnoreCase((String)sortType.getSelectedItem())) {
				ascending = false;
			}
			
			
			sortList(temp, ascending, attendanceList, listIndexes, model, 8);
		}
	}
	
	
	public boolean getReturnToMenu() {
		return returnToMenu;
	}
	
	
	public void setReturnToMenu(boolean status) {
		returnToMenu = status;
	}
	
	
	public void writeToFile() {
		try {
		      RandomAccessFile attendanceFile = new RandomAccessFile("attendance.txt", "rw");
		      attendanceFile.setLength(0);
		      //puts pointer at end of file
		      for(int i = 0; i <attendanceList.size(); i++) {
		    	  attendanceFile.seek((int)attendanceFile.length());
		    	  attendanceFile.writeBytes(attendanceList.lookUp(i)[0] + ":" + attendanceList.lookUp(i)[1] + ":" + attendanceList.lookUp(i)[2] +":" + 
		      attendanceList.lookUp(i)[3] + ":" + attendanceList.lookUp(i)[4] + ":" + attendanceList.lookUp(i)[5] + ":" + attendanceList.lookUp(i)[6] + 
		      ":" + attendanceList.lookUp(i)[7] +"\n");
		      }
		      attendanceFile.close();
		    } 
		catch (IOException e) {
		      e.getMessage();
		    }
		    
	}
	
	
	public void readFromFile() {
		try {
			RandomAccessFile attendanceFile = new RandomAccessFile("attendance.txt", "r");
			if (attendanceFile.length() != 0L) {
				for(int i = 0; i < countLineBufferedReader("attendance.txt"); i++) {
					String line = attendanceFile.readLine();
					String [] readFile = line.split(":");
					readFile[4] = "";

					attendanceList.insert(attendanceList.size(), readFile);
		        	model.addRow(readFile);
		        	listIndexes.add(attendanceList.size()-1);
				}
				
			}
			attendanceFile.close();
			
		}
		catch (IOException e) {
			e.getMessage();
		}
	}
	

	public void setCorrectListValues(int removedValue) {
		for(int i = 0; i <listIndexes.size(); i++) {
			if(listIndexes.get(i)> removedValue) {
				listIndexes.set(i, listIndexes.get(i)-1);
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
}
