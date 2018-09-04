import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.JOptionPane;

public class Hiker implements Serializable{
	
	private String hikerID;
	
	
	
	public ArrayList<LocalDateTime> hikeDates= new ArrayList<LocalDateTime>();
	
	
	
	protected static final DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	
	SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	private int weeklyHikes=0;
	private int cumulativeHikes;
	
	public static Map<String, Hiker> instance= new HashMap<>();
	
  
	
	Hiker(String hikerID){
		this.hikerID=hikerID;
		
	}

	public String getHikerID() {
		return hikerID;
	}

	public void setHikerID(String hikerID) {
		this.hikerID = hikerID;
	}
	
	LocalDateTime dateTimeHiked(String timeEntered){
		while(true){
			try{
			return LocalDateTime.parse(timeEntered, formatter);}
			
			catch(Exception e){
				JOptionPane.showMessageDialog(null, "Not all entries could be added as the dates were in the wrong format. "
						+timeEntered+ " is not a valid date and time.  You can correct that cell and re-upload the list and it will skip over any duplicates.");


				
				
			}
			return null;
		}
		
		
	}

	public int getWeeklyHikes() {
		return weeklyHikes;
	}

	public void setWeeklyHikes(int weeklyHikes) {
		this.weeklyHikes = weeklyHikes;
	}

	public int getCumulativeHikes() {
		return cumulativeHikes;
	}

	public void setCumulativeHikes(int cumulativeHikes) {
		this.cumulativeHikes = cumulativeHikes;
	}
	
	public static Hiker getInstance(String id){
		if(!instance.containsKey(id)){
			instance.put(id, new Hiker(id));
			return instance.get(id);
		}
		return instance.get(id);
	}
	
	public String toString(){
		String display= getHikerID();
		return display;
		
	}

}
