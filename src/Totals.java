import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;

public class Totals extends JPanel {
	
	JScrollPane listPane;
	static JTable listTable;
	private JButton printButton;
	static boolean exclude0;

	
	
	Totals() throws PrinterException{
		
		String[] columnNames=  {"Hiker ID", "Weekly", "Cumulative"};
		 
		setBorder( new EmptyBorder(20, 20, 20, 20) );
		setBackground(Color.BLUE);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		
		JLabel totalLabel= new JLabel("Totals");
		totalLabel.setForeground(Color.YELLOW);
		totalLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		totalLabel.setAlignmentX(0.5f);
		
		add(totalLabel);
		add(Box.createVerticalStrut(20));
		DefaultTableModel model= new DefaultTableModel( columnNames,0){
		 public Class getColumnClass(int column) {
		        if (column >= 0 && column <= getColumnCount())
		          return getValueAt(0, column).getClass();
		        else
		          return Object.class;
		      }
		    
		};
		 listTable= new JTable(model);
		 
		
		
		listPane= new JScrollPane(listTable);
		listPane.setPreferredSize(new Dimension(250,250));
			 add(listPane);
		
		RowSorter<TableModel> sorter= new TableRowSorter<TableModel>(model);
			
		 listTable.setRowSorter(sorter);
			 
			 
			 
			 add(Box.createVerticalStrut(20));
			 
			 printButton= new JButton("Print");
			 printButton.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent arg0) {
			 		printTable(listTable);
			 	}
			 });
			 printButton.setForeground(Color.YELLOW);
			 printButton.setBackground(Color.RED);
			 printButton.setAlignmentX(0.5f);
			 add(printButton);
			 
			 add(Box.createVerticalStrut(20));
		
			initTablesModel(model);
		
				
	}
	
	public static void createAndShowTable() throws PrinterException{
		JFrame frame= new JFrame("Totals");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(new Totals());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void initTablesModel(DefaultTableModel model){
       
		
		 
		
		 for(Hiker h: Home.hikerList){
			 if(exclude0==true){
			if(h.getWeeklyHikes()!=0){
			 model.addRow(new Object[] {h.getHikerID(), h.getWeeklyHikes(), h.getCumulativeHikes()});}
			}
			else{
			 model.addRow(new Object[] {h.getHikerID(), h.getWeeklyHikes(), h.getCumulativeHikes()}); 
			 }
			 }
		 }
		
	private static void printTable(JTable table){

        try {
         
		MessageFormat headerFormat = new MessageFormat("Hiker Counts");
          MessageFormat footerFormat = new MessageFormat("- {0} -");
          table.print(JTable.PrintMode.NORMAL, headerFormat, footerFormat);
        } catch (PrinterException pe) {
          System.err.println("Error printing: " + pe.getMessage());
        }
      }		 
			
			
		 
			 
					 
				
		
		
	
		
	
			 
	
}


