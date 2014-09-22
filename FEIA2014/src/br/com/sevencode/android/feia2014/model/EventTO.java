package br.com.sevencode.android.feia2014.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



public class EventTO implements Serializable {

	public static enum EventType{
		WORKSHOP, EXHIBITION, PARTY;
		
		public EventType getEventType(int value){
			EventType et = null;
			
			switch(value){
			case 0:
				et = WORKSHOP;
				break;
			case 1:
				et = EXHIBITION;
				break;
			case 2:
				et = PARTY;
				break;
			
			}
			
			return et;
		}
		
		public int intValue(){
			int result = 0;
			
			switch (this) {
			case WORKSHOP:
				result = 0;
				break;
			case EXHIBITION:
				result = 1;
				break;
			case PARTY:
				result = 2;
				break;
			}
			
			return result;
		}
		
	}
	
	public static enum EventCategory{
		DANCING, MUSIC, VISUAL_ARTS, PERFORMING_ARTS, MEDIALOGY, GENERAL;
		
		public static EventCategory getEventCategory(int value){
			EventCategory et = null;
			
			switch(value){
			case 0:
				et = DANCING;
				break;
			case 1:
				et = MUSIC;
				break;
			case 2:
				et = VISUAL_ARTS;
				break;
			case 3:
				et = PERFORMING_ARTS;
				break;
			case 4:
				et = MEDIALOGY;
				break;
			case 5:
				et = GENERAL;
				break;
			
			}
			
			return et;
		}
		
		public String getEventCategoryDescription(){
			String description = null;
			
			switch (this) {
			case GENERAL:
				description = "Interdisciplinar";
				break;
			case VISUAL_ARTS:
				description = "Artes Visuais";
				break;
			case PERFORMING_ARTS:
				description = "Artes Cênicas";
				break;
			case DANCING:
				description = "Dança";
				break;
			case MUSIC:
				description = "Música";
				break;
			case MEDIALOGY:
				description = "Midialogia";
				break;
			}
			
			return description;
		}
		
		public int intValue(){
			int result = 0;
			
			switch (this) {
			case DANCING:
				result = 0;
				break;
			case MUSIC:
				result = 1;
				break;
			case VISUAL_ARTS:
				result = 2;
				break;
			case PERFORMING_ARTS:
				result = 3;
				break;
			case MEDIALOGY:
				result = 4;
				break;
			case GENERAL:
				result = 5;
				break;
			}
			
			return result;
		}
		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1127105278706424404L;
	
	

	private int eventId;
	

	private String name;
	

	private EventCategory category;
	
	
	private EventType type;
	
	
	private String author;
	
	
	private int duration;
	
	
	private Date date;
	
	
	private List<Date> dateArray;
	

	private String description;
	
	
	private String placeData;
	
	public EventTO() {
		// TODO Auto-generated constructor stub
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Date> getDateArray() {
		return dateArray;
	}

	public void setDateArray(List<Date> dateArray) {
		this.dateArray = dateArray;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlaceData() {
		return placeData;
	}

	public void setPlaceData(String placeData) {
		this.placeData = placeData;
	}

	public EventCategory getCategory() {
		return category;
	}

	public void setCategory(EventCategory category) {
		this.category = category;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

}
