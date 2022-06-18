import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class Calendar extends Panel implements ActionListener{
	
	private JTable calendar;
	private JButton add, delete, update, saveFile, sort, backToMenu;
	private JTextField year, time, eventTitle;
	private JTextArea details;
	private JTextArea title, dayText, monthText, yearText, timeText, eventText, detailsText, sortText, sortTypeText;
	private JComboBox day, month, sortColumn, sortType;
	private DefaultTableModel model;
	private String[] columnForSort = {"Day", "Month", "Year", "Time", "Event Title", "Details"};
    private String[] columnForSortType = {"Ascending", "Descending"};
	private boolean returnToMenu = false;
	
	private ArrayList<Integer> listIndexes = new ArrayList<Integer>();
	private LinkedList calendarHistory = new LinkedList();


	
	public Calendar() {
		calendar = new JTable(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		calendar.setRowHeight(40);
		String[] columns = {"Day", "Month", "Year", "Time", "Event Title", "Details"};
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		calendar.setModel(model);
		

		JScrollPane js = new JScrollPane(calendar);
		js.setBounds(20, 70, 700, 600);
		
		
		add = new JButton("Add Event");
		delete = new JButton("Delete Event");
		update = new JButton("Update Event");
		saveFile = new JButton("Save");
		sort = new JButton("Sort");
		backToMenu = new JButton("Menu");
		
		add.setBounds(730, 540, 140, 30);
		delete.setBounds(730, 580, 140, 30);
		update.setBounds(730, 620, 140, 30);
		sort.setBounds(730, 660, 140, 30);
		saveFile.setBounds(920, 560, 200, 50);
		backToMenu.setBounds(920, 630, 200, 50);
		
		add.setBackground(Color.orange);
		delete.setBackground(Color.orange);
		update.setBackground(Color.orange);
		saveFile.setBackground(Color.orange);
		sort.setBackground(Color.orange);
		backToMenu.setBackground(Color.orange);
		
		add.addActionListener(this);
		delete.addActionListener(this);
		update.addActionListener(this);
		saveFile.addActionListener(this);
		sort.addActionListener(this);
		backToMenu.addActionListener(this);
		
		year = new JTextField();
		year.setBounds(800, 130, 350, 25);
		add(year);
		
		time = new JTextField();
		time.setBounds(800, 170, 350, 25);
		add(time);
		
		eventTitle = new JTextField();
		eventTitle.setBounds(800, 210, 350, 25);
		add(eventTitle);
		
		details = new JTextArea();
		details.setBounds(730, 290, 420, 150);
		details.setLineWrap(true);
		details.setWrapStyleWord(true);
		add(details);

		
		String[] days = {"Monday","Tuesday","Wednesday","Thursday", "Friday", "Saturday", "Sunday"};
		day = new JComboBox(days);
		day.setBounds(800, 50, 140, 30); 
		day.addActionListener(this);
        add(day);
        
		String[] months = {"January","February","March","April", "May", "June", "July", "August", "September", "October", "November", "December"};
		month = new JComboBox(months);
		month.setBounds(800, 90, 140, 30); 
		month.addActionListener(this);
        add(month);
        
        sortColumn = new JComboBox(columnForSort);
		sortColumn.setBounds(730, 500, 140, 30); 
		sortColumn.addActionListener(this);
        add(sortColumn);
		
        sortType = new JComboBox(columnForSortType);
        sortType.setBounds(900, 500, 140, 30); 
        sortType.addActionListener(this);
        add(sortType);
		
		calendar.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int i = calendar.getSelectedRow();
				day.setSelectedItem(model.getValueAt(i, 0).toString());
				month.setSelectedItem(model.getValueAt(i, 1).toString());
				
				year.setText(model.getValueAt(i, 2).toString());
				time.setText(model.getValueAt(i, 3).toString());
				eventTitle.setText(model.getValueAt(i, 4).toString());
				details.setText(model.getValueAt(i, 5).toString());

			}
		});
		
		setupText();
		readFromFile();

		add(add);
		add(delete);
		add(update);
		add(saveFile);
		add(sort);
		add(backToMenu);
		add(js);

	}
	

	public void setupText() {
		add(createText(title, "Calendar", new Font("Times", Font.BOLD, 40), 20, 20, 400, 50));
		add(createText(dayText, "Day:", new Font("Times", Font.PLAIN, 20), 730, 50, 100,25));
		add(createText(monthText, "Month:", new Font("Times", Font.PLAIN, 20), 730, 90, 100,25));
		add(createText(yearText, "Year:", new Font("Times", Font.PLAIN, 20), 730, 130, 100,25));
		add(createText(timeText, "Time:", new Font("Times", Font.PLAIN, 20), 730, 170, 100,25));
		add(createText(eventText, "Event:", new Font("Times", Font.PLAIN, 20), 730, 210, 140,25));
		add(createText(detailsText, "Details:", new Font("Times", Font.PLAIN, 20), 730, 250, 140,25));
		add(createText(sortText, "Sort Column:", new Font("Times", Font.PLAIN, 20), 730, 460, 140,25));
		add(createText(sortTypeText, "Sort Type:", new Font("Times", Font.PLAIN, 20), 900, 460, 140,25));

	}
	
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Menu")) {
			returnToMenu = true;
		}
		else if(e.getActionCommand().equals("Add Event")) {
			String [] info = new String[6];
			info[0] = (String) day.getSelectedItem();
        	info[1] = (String) month.getSelectedItem();
        	info[2] = year.getText().replace("\n", "").replace("\r", "");
        	info[3] = time.getText().replace("\n", "").replace("\r", "");
        	info[4] = eventTitle.getText().replace("\n", "").replace("\r", "");
        	info[5] = details.getText().replace("\n", "").replace("\r", "");
        	
        	calendarHistory.insert(calendarHistory.size(), info);
        	
        	listIndexes.add(calendarHistory.size()-1);

        	model.addRow(info);
        }
		else if(e.getActionCommand().equals("Delete Event")) {
			int i = calendar.getSelectedRow();
			if(i>=0) {
				model.removeRow(i);
				calendarHistory.delete(listIndexes.get(i));
				setCorrectListValues(listIndexes.get(i));
				listIndexes.remove(i);
			}
		}
		else if(e.getActionCommand().equals("Update Event")) {
			int i = calendar.getSelectedRow();
			if(i>=0) {
				model.setValueAt((String) day.getSelectedItem(), i, 0);
				model.setValueAt((String) month.getSelectedItem(), i, 1);
				model.setValueAt(year.getText().replace("\n", "").replace("\r", ""), i, 2);
				model.setValueAt(time.getText().replace("\n", "").replace("\r", ""), i, 3);
				model.setValueAt(eventTitle.getText().replace("\n", "").replace("\r", ""), i, 4);
				model.setValueAt(details.getText().replace("\n", "").replace("\r", ""), i, 5);

				calendarHistory.lookUp(listIndexes.get(i))[0] = (String) day.getSelectedItem();
				calendarHistory.lookUp(listIndexes.get(i))[1] = (String) month.getSelectedItem();
				calendarHistory.lookUp(listIndexes.get(i))[2] = year.getText().replace("\n", "").replace("\r", "");
				calendarHistory.lookUp(listIndexes.get(i))[3] = time.getText().replace("\n", "").replace("\r", "");
				calendarHistory.lookUp(listIndexes.get(i))[4] = eventTitle.getText().replace("\n", "").replace("\r", "");
				calendarHistory.lookUp(listIndexes.get(i))[5] = details.getText().replace("\n", "").replace("\r", "");

			}
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
			if("Descending".equalsIgnoreCase((String)sortType.getSelectedItem())) {
				ascending = false;
			}
			
			
			sortList(temp, ascending, calendarHistory, listIndexes, model, 6);		}
	}
	
	public boolean getReturnToMenu() {
		return returnToMenu;
	}
	
	public void setReturnToMenu(boolean status) {
		returnToMenu = status;
	}
	
	public void writeToFile() {
		try {
		      RandomAccessFile calendarFile = new RandomAccessFile("calendar.txt", "rw");
		      calendarFile.setLength(0);
		      //puts pointer at end of file
		      for(int i = 0; i <calendarHistory.size(); i++) {
		    	  calendarFile.seek((int)calendarFile.length());
		    	  if(calendarHistory.lookUp(i)[5].length() == 0) {
		    		  	calendarHistory.lookUp(i)[5] += " ";
		    	  }
		    	  calendarFile.writeBytes(calendarHistory.lookUp(i)[0] + ":" + calendarHistory.lookUp(i)[1] + ":" + calendarHistory.lookUp(i)[2] +":" + 
		    			  calendarHistory.lookUp(i)[3] + ":" + calendarHistory.lookUp(i)[4] + ":" + calendarHistory.lookUp(i)[5] +"\n");
		      }
		      calendarFile.close();
		} 
		catch (IOException e) {
		      e.getMessage();
		}
		    
	}
	
	public void readFromFile() {
		try {
			RandomAccessFile calendarFile = new RandomAccessFile("calendar.txt", "r");
			if (calendarFile.length() != 0L) {
				for(int i = 0; i < countLineBufferedReader("calendar.txt"); i++) {
					String line = calendarFile.readLine();
					String [] readFile = line.split(":");

					calendarHistory.insert(calendarHistory.size(), readFile);
		        	model.addRow(readFile);
		        	listIndexes.add(calendarHistory.size()-1);
				}
				
			}
			calendarFile.close();
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
}
