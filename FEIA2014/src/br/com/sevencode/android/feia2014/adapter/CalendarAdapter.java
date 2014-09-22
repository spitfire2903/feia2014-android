package br.com.sevencode.android.feia2014.adapter;

import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import br.com.sevencode.android.feia2014.R;
import br.com.sevencode.android.feia2014.components.SCTextView;
import br.com.sevencode.android.feia2014.db.Event;

public class CalendarAdapter extends ArrayAdapter<Event> {
	private Context context;
	private List<Event> eventList;
	private Hashtable<String, Boolean> dates;
	

	public CalendarAdapter(Context context, int resource, List<Event> objects) {
		super(context, resource, objects);
		this.context = context;
		eventList = objects;
		dates = new Hashtable<String, Boolean>();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Event e = null;
		String dateText = null;
		LayoutInflater inflater = null;
		View calendarRow = null;
		SimpleDateFormat sdf = null;
		String hashKey = null;
		
		LinearLayout headerView = null;
		SCTextView headerViewText = null;

		e = eventList.get(position);
		
		dateText = "";
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		calendarRow = inflater.inflate(R.layout.calendar_item, parent, false);
		sdf = new SimpleDateFormat("HH:mm");

		SCTextView name = (SCTextView) calendarRow.findViewById(R.id.eventName);
		SCTextView date = (SCTextView) calendarRow.findViewById(R.id.eventDate);

		headerView = (LinearLayout) calendarRow.findViewById(R.id.dateHeader);
		headerViewText = (SCTextView) calendarRow.findViewById(R.id.dateHeaderText);

		//		for (String dateStr : e.getDateArray().split("|")) {
		//			dateText += "";
		//		}

		name.setText(e.getName());
		date.setText(sdf.format(e.getDate()));
		
		sdf = new SimpleDateFormat("dd/MM");
		hashKey = sdf.format(e.getDate());
		headerViewText.setText(hashKey);
		
		if(dates.get(hashKey) == null || !dates.get(hashKey)){
			dates.put(hashKey, true);
			headerView.setVisibility(View.VISIBLE);
		} else{
			headerView.setVisibility(View.GONE);
		}
		
		return calendarRow;
	}


}
