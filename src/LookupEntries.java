import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.awt.event.ActionEvent;

public class LookupEntries extends JPanel {
	
	private JScrollPane entriesPane;
	private JList entriesJList;
	private DefaultListModel entriesModel;
	private JTextField entryField;
	private JButton searchButton;
	private String barcode;
	
	LookupEntries(){
		setBackground(Color.BLACK);
		setBorder(new EmptyBorder(20,20,20,20));
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel lookupEntries= new JLabel("Lookup Barcode Entries");
		lookupEntries.setForeground(new Color(153, 50, 204));
		 lookupEntries.setFont(new Font("Tahoma", Font.BOLD, 30));
		 lookupEntries.setAlignmentX(0.5f);
		 add(lookupEntries);
		 add(Box.createVerticalStrut(20));
		 
		 JLabel enterBarcodeNumber= new JLabel("Enter Bardcode Number");
		 enterBarcodeNumber.setForeground(new Color(153, 50, 204));
		 enterBarcodeNumber.setAlignmentX(0.5f);
		 add(enterBarcodeNumber);
		 add(Box.createVerticalStrut(10));
		 
		 entryField=new JTextField();
		 entryField.setBackground(new Color(0, 255, 0));
		 entryField.setForeground(new Color(0, 0, 255));
		 entryField.setAlignmentX(0.5f);
		 entryField.setPreferredSize( new Dimension( 100, 25 ) );
		 entryField.setMaximumSize( entryField.getPreferredSize() );
		 add(entryField);
		 
		 add(Box.createVerticalStrut(20));
		 searchButton=new JButton("Search");
		 searchButton.setForeground(new Color(255, 255, 255));
		 searchButton.setBackground(new Color(153, 50, 204));
		 searchButton.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent arg0) {
		 		entriesModel.removeAllElements();
		 		 barcode= entryField.getText();
		 	
		 			initEntries(barcode);
		 		
		 	}
		 });
		 searchButton.setAlignmentX(0.5f);
		 add(searchButton);
		 
		 add(Box.createVerticalStrut(20));
		 entriesModel= new DefaultListModel();
		 entriesJList=  new JList(entriesModel);
		 entriesJList.setForeground(new Color(0, 0, 255));
		 entriesJList.setBackground(new Color(0, 255, 0));
		 
		 entriesPane= new JScrollPane();
		 entriesPane.setAlignmentX(0.5f);
		 entriesPane.setPreferredSize(new Dimension(10,200));
		 
		 entriesPane.setViewportView(entriesJList);
		 add(entriesPane);
		
		 add(Box.createVerticalStrut(30));
		 
		 
		 
		 
	}
	private void initEntries(String hikerBarcode){
		
		for(Hiker hiker: Home.hikerList){
		
			if(hiker.getHikerID().equals(hikerBarcode)){
				
				
				for(int i=0; i<hiker.hikeDates.size();i++){
					
				entriesModel.addElement(hiker.hikeDates.get(i));}
				}}
		if(entriesJList.getModel().getSize()==0){
			JOptionPane.showMessageDialog(null, "Barcode Not Found");
		}
		}
	
	public static void showEntryLookup(){
		JFrame frame= new JFrame("Lookup Entries");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(new LookupEntries());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
				
	}

}
