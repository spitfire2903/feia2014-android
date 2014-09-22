package br.com.sevencode.android.feia2014.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import br.com.sevencode.android.feia2014.R;
import br.com.sevencode.android.feia2014.components.SCTextView;
import br.com.sevencode.android.feia2014.db.Event;
import br.com.sevencode.android.feia2014.model.EventTO.EventCategory;
import br.com.sevencode.android.feia2014.model.EventTO.EventType;

public class WorkshopAdapter extends ArrayAdapter<Event> {
	private Context context;
	private List<Event> eventList;

	public WorkshopAdapter(Context context, int resource, List<Event> objects) {
		super(context, resource, objects);
		this.context = context;
		eventList = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Event e = null;
		String dateText = null;
		LayoutInflater inflater = null;
		View workshopRow = null;
		SimpleDateFormat sdf = null;

		e = eventList.get(position);
		dateText = "";
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		workshopRow = inflater.inflate(R.layout.workshop_item, parent, false);
		sdf = new SimpleDateFormat("dd/MM HH:mm");

		SCTextView name = (SCTextView) workshopRow.findViewById(R.id.workshopName);
		SCTextView author = (SCTextView) workshopRow.findViewById(R.id.workshopAuthor);
		SCTextView date = (SCTextView) workshopRow.findViewById(R.id.workshopDate);
		
		for (String dateStr : e.getDateArray().split("\\|")) {
			dateText += (dateStr+" / ");
		}
		
		dateText = dateText.substring(0, dateText.lastIndexOf("/"));
		
		name.setText(e.getName());
		date.setText(dateText);
		author.setText(e.getAuthor());

		return workshopRow;
	}

}
