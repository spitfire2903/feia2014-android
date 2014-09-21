package br.com.sevencode.android.feia2014.task;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import br.com.sevencode.android.feia2014.MainActivity;
import br.com.sevencode.android.feia2014.db.Event;

public class LoadEventTask extends AsyncTask<Void, Void, Void> {

	private MainActivity parent = null;
	private List<Event> events = null;

	public LoadEventTask(MainActivity activity) {
		// TODO Auto-generated constructor stub
		this.parent = activity;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		events = new ArrayList<Event>();
		parent.showThrobber();
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		String json = loadJSONFromAsset();
		readJson(json);

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		parent.saveEvents(events);
	}

	private String loadJSONFromAsset() {
		String json = null;
		try {

			InputStream is = parent.getAssets().open("eventsLatin.json");

			int size = is.available();

			byte[] buffer = new byte[size];

			is.read(buffer);

			is.close();

			json = new String(buffer, "UTF-8");

		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}

		return json;
	}

	private void readJson(String json) {
		try {

			JSONObject obj = new JSONObject(json);
			JSONArray m_jArry = obj.getJSONArray("events");

			for (int i = 0; i < m_jArry.length(); i++) {
				JSONObject jo_inside = m_jArry.getJSONObject(i);
				Event e = convertJsonToEvent(jo_inside);
				
				events.add(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Event convertJsonToEvent(JSONObject json){
		Event event = new Event();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		JSONArray dates = null;
		JSONObject date = null;
		String dateStr = null;
		
		try {
			event.setEventId(json.getInt("eventId"));
			// event.setDuration(json.getInt("duration"));
			event.setName(json.getString("name"));
			event.setType(json.getInt("type"));
			event.setCategory(json.getInt("category"));
			event.setAuthor(json.getString("author"));
			event.setDescription(json.getString("shortDescription"));
			event.setPlaceData(json.getString("placeData"));
			
			dates = json.getJSONArray("date");
			
			
			for (int i = 0; i < dates.length(); i++) {
				if(i == 0){
					dateStr = dates.getString(i);
					event.setDate(sdf.parse(dateStr));
				} else{
					dateStr += "|"+dates.getString(i);
				}
			}
			event.setDateArray(dateStr);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return event;
	}

}
