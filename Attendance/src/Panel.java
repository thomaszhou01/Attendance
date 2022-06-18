import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class Panel extends JPanel{
	
	public Panel() {
		this.setLayout(null);		

		
		setDoubleBuffered(true);
		this.setBackground(Color.lightGray);
		this.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.black));
		this.setBounds(20, 20, 1160, 720);
	}
	
	public JTextArea createText(JTextArea a, String name, Font newFont, int boundX, int boundY, int x, int y) {
		a = new JTextArea(name);
		a.setFont(newFont);
		a.setBounds(boundX, boundY, x, y);
		a.setBackground(Color.lightGray);
		a.setEditable(false);
		return a;
	}
	
	public static long countLineBufferedReader(String fileName) {
		long lines = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			while (reader.readLine() != null) lines++;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	
	public void sortList(int column, boolean ascending, LinkedList list, ArrayList<Integer> arr, DefaultTableModel mod, int indexSize) {
		int j=0;
    	int num;
    	String placeHolder = "";
    	
    	if(ascending) {
    		for(int i = 1; i<list.size(); i++) {
        		placeHolder = list.lookUp(arr.get(i))[column];
        		num = arr.get(i);
        		j = i;
        		while(j>0 && list.lookUp(arr.get(j-1))[column].compareToIgnoreCase(placeHolder)>0) {
        			arr.set(j, arr.get(j-1));
        			j--;
        		}
        		arr.set(j, num);
        	}
    	}
    	else {
    		for(int i = 1; i<list.size(); i++) {
        		placeHolder = list.lookUp(arr.get(i))[column];
        		num = arr.get(i);
        		j = i;
        		while(j>0 && list.lookUp(arr.get(j-1))[column].compareToIgnoreCase(placeHolder)<0) {
        			arr.set(j, arr.get(j-1));
        			j--;
        		}
        		arr.set(j, num);
        	}
    	}
    	
    	mod.setRowCount(0);
		String [] info = new String[indexSize];
    	for(int i = 0; i < list.size(); i++) {
    		info = list.lookUp(arr.get(i));
    		mod.addRow(info);
		}
	}
}
