import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;


import java.awt.*;

import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Home extends JPanel implements Serializable{
	
	private JButton importList;
	private JButton viewTotals;
	private JButton viewEntries;
	private JButton clearCumulativeEntries;
	private JButton load;
	private JButton save;
	static boolean fileLoaded;
	static boolean fileChanged;
	static File f;
	static JFileChooser fileChooser;
	
	Home()
	{
		setBackground(new Color(176, 224, 230));
		setBorder( new EmptyBorder(20, 100, 20, 100) );
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel hikerTracking= new JLabel("Hiker Entry Log");
		hikerTracking.setFont(new Font("Tahoma", Font.BOLD, 30));
		hikerTracking.setAlignmentX(0.5f);
		add(hikerTracking);
		add(Box.createVerticalStrut(20));
		
		importList= new JButton("Import Entry List");
		
		importList.setAlignmentX(0.5f);
		add(importList);
		add(Box.createVerticalStrut(10));
		
		viewTotals= new JButton("View Totals");
		
		viewTotals.setAlignmentX(0.5f);
		add(viewTotals);
		add(Box.createVerticalStrut(10));
		
		viewEntries= new JButton("Lookup Hiker Entries");
		
		viewEntries.setAlignmentX(0.5f);
		add(viewEntries);
		add(Box.createVerticalStrut(10));
		
		clearCumulativeEntries = new JButton("Clear Cumulative Totals");
	
		clearCumulativeEntries.setAlignmentX(0.5f);
		add(clearCumulativeEntries);
		
		add(Box.createVerticalStrut(30));
		
		load= new JButton("Load");
	
		load.setAlignmentX(0.5f);
		add(load);
		add(Box.createVerticalStrut(10));
		
		save= new JButton("Save");
		
		save.setAlignmentX(0.5f);
		add(save);
		
		addActionListeners();
		fileLoaded=false;
		fileChanged=false;
		
		
				
	}
	private void addActionListeners(){
		viewTotals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object[]choices={"Include 0 Weekly Entries", "Exclude 0 Weekly Entries"};
				JPanel panel= new JPanel();
				panel.add(new JLabel("Select a Display Option"));
			int option=	JOptionPane.showOptionDialog(null, panel, "Choose", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,null, choices, null);
				Totals totals;
				try {
					totals = new Totals();
					if(option==JOptionPane.YES_OPTION){
						Totals.exclude0=false;
					}
					else{
						Totals.exclude0=true;
					}
					totals.setVisible(true);
					
					totals.createAndShowTable();
				} catch (PrinterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
		}});
		importList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					readHikerEntries();
					fileChanged=true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		clearCumulativeEntries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearTotals();
				fileChanged=true;
			}
		});
		viewEntries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LookupEntries entryLookup= new LookupEntries();
				LookupEntries.showEntryLookup();
				entryLookup.setVisible(true);
			}
		});
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(fileLoaded==true){
					saveSelectedEntries();
				}
				else{
					saveEntries();
				}
			}
		});
		
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					loadEntries();
					
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	private static void homeScreen(){
		JFrame frame= new JFrame("Hiker Entry Log");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().add(new Home());
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	if(fileChanged==false){
            		System.exit(0);
            	}
            	int response=  JOptionPane.showConfirmDialog( null, "Would you like to save your changes?");
           		     if(response== JOptionPane.YES_OPTION) 
            	    {
           		    	if(fileLoaded==true){
           		    		saveSelectedEntries();
           		    		System.exit(0);}
           		    	else{
           		    		saveEntries();
           		    		System.exit(0);
           		    }
           		    }
           		    
                    	else if(response==JOptionPane.NO_OPTION){
                    		     System.exit(0);
                    	 
                    	 }
                    	 else{
                    		 return;
                    	 }
                    	
                    	 
                    	 
                     }});
		frame.setVisible(true);
	}
	
	
	static ArrayList<Hiker> hikerList= new ArrayList<Hiker>();
	
	
public static void main(String [] args) throws IOException{
	homeScreen();

	
}
	
	
public static void readHikerEntries() throws IOException{
	int option=JOptionPane.showConfirmDialog(null, "Would you like to clear your previous weekly entries?");
	if(option==JOptionPane.YES_OPTION){
	for(int i=0; i<hikerList.size(); i++){
		
	
		Hiker hiker= hikerList.get(i);
		hiker.setWeeklyHikes(0);}
	}
	System.out.println(hikerList);
		JFileChooser hikerFile= new JFileChooser();
	
	
	if(hikerFile.showOpenDialog(null) != JFileChooser.APPROVE_OPTION){
		JOptionPane.showMessageDialog(null, "The following file type is incompatible.");
		return;
		
    }
	
	
	try {
		File file= hikerFile.getSelectedFile();
		InputStream fis= new FileInputStream(file);
	
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet worksheet = workbook.getSheetAt(0);
		
		//fis = new FileInputStream(file);
		
		int rowsCount= worksheet.getLastRowNum();
		
		for(int i=0; i<=rowsCount; i++){
			
			
			
			XSSFRow row= worksheet.getRow(i);
			
			
			
			XSSFCell cell= row.getCell(0);
			String barcode= cell.getStringCellValue();
			if(((((barcode.charAt(0)=='0')||(barcode.charAt(0)=='1'))||(barcode.charAt(0)=='2')||(barcode.charAt(0)=='3')
					||(barcode.charAt(0)=='4')||(barcode.charAt(0)=='5')||(barcode.charAt(0)=='6')||(barcode.charAt(0)=='7')||
					barcode.charAt(0)=='8')||(barcode.charAt(0)=='9')&&(barcode.charAt(5)=='-'))){
				
				
			
		
			String hikerNumber= barcode.substring(0,4);
			
			
			
				
			
			Hiker hiker= Hiker.getInstance(hikerNumber);
			
			
			
			if(barcode.length()==24){
				barcode= barcode+"0";
				System.out.println(barcode);
			}
			
			int endingIndex= barcode.length()-1;
			LocalDateTime hikeEntry= hiker.dateTimeHiked(barcode.substring(5,endingIndex));
			hiker.hikeDates.add(hikeEntry);
			if(hikeEntry!=null){
			
			if(!hikerList.contains(hiker)){
			hikerList.add(hiker);
			System.out.println(barcode.length());}
			
			
			hiker.setWeeklyHikes(hiker.getWeeklyHikes()+1);
			
			hiker.setCumulativeHikes(hiker.getCumulativeHikes()+1);}
			
			
			//System.out.println(hiker.hikeDates+" "+hiker.getWeeklyHikes()+", "+hiker.getCumulativeHikes());
			
			//System.out.println(barcode);
			
			//System.out.println(Hiker.getInstance(barcode.substring(0,4)).getHikerID());
			//for(int i=0;i<Hiker.getInstance(barcode.substring(0,4)).hikeDates.size();i++){
			//System.out.println(Hiker.getInstance(barcode.substring(0,4)).hikeDates.get(i).format(formatter2));}
			//System.out.println(hikerList);
			}
			
		}
		JOptionPane.showMessageDialog(null, "Success!");
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, "File Not Supported");
	}
	
	
	
}



private void clearTotals(){
	for(Hiker h: hikerList){
		h.setCumulativeHikes(0);
		
	}
	JOptionPane.showMessageDialog(null, "Cumulative Totals Cleared!");

}
private static void saveEntries(){
	 fileChooser= new JFileChooser();
	  
	  
	  
	 if(fileChooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION){
			
			return;
			
	    }
	
	ObjectOutputStream oos;
	try{
		f=fileChooser.getSelectedFile();
		FileOutputStream fos= new FileOutputStream(f);
		oos= new ObjectOutputStream(fos);
		oos.writeObject(hikerList);
		oos.close();
		fos.close();
		fileChanged=false;
	}
	catch(IOException e){
		e.printStackTrace();
	}
}

private void loadEntries() throws ClassNotFoundException{
	fileChooser= new JFileChooser();
	
	if(fileChooser.showOpenDialog(null)!=JFileChooser.APPROVE_OPTION){
		return;
	}
	try{
		f=fileChooser.getSelectedFile();
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
		hikerList= (ArrayList)ois.readObject();
		fileLoaded=true;
		fileChanged=false;
		
	}
	catch(FileNotFoundException e){
		e.printStackTrace();
	}
	catch(IOException e){
		e.printStackTrace();
	}
}

private static void saveSelectedEntries(){
	ObjectOutputStream oos;
	try{
		FileOutputStream fos = new FileOutputStream(f);
		oos = new ObjectOutputStream(fos);
		oos.writeObject(hikerList);
		 oos.close();
	     fos.close();
	     fileChanged=false;
	}
	catch(IOException e){
		e.printStackTrace();
	}
	
}

}
