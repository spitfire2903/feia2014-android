package br.com.sevencode.android.feia2014.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "events")
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
		
	}
	
	public static enum EventCategory{
		DANCING, MUSIC, VISUAL_ARTS, PERFORMING_ARTS, MEDIALOGY, GENERAL;
		
		public EventCategory getEventCategory(int value){
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
		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1127105278706424404L;
	
	
	@DatabaseField(id=true)
	private int eventId;
	
	@DatabaseField
	private String name;
	
	@DatabaseField
	private EventCategory category;
	
	@DatabaseField
	private EventType type;
	
	@DatabaseField
	private String author;
	
	@DatabaseField
	private int duration;
	
	@DatabaseField
	private Date date;
	
	@DatabaseField
	private List<Date> dateArray;
	
	@DatabaseField(dataType=DataType.LONG_STRING)
	private String description;
	
	@DatabaseField
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
