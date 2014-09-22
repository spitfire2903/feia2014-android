package br.com.sevencode.android.feia2014.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.sevencode.android.feia2014.R;
import br.com.sevencode.android.feia2014.components.SCTextView;
import br.com.sevencode.android.feia2014.db.Event;

public class PartyAdapter extends ArrayAdapter<Event> {
	private Context context;
	private List<Event> eventList;
	
	public PartyAdapter(Context context, int resource, List<Event> objects) {
		super(context, resource, objects);
		this.context = context;
		eventList = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Event e = null;
		String dateText = null;
		LayoutInflater inflater = null;
		View partyRow = null;
		SimpleDateFormat sdf = null;
		
		e = eventList.get(position);
		dateText = "";
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		partyRow = inflater.inflate(R.layout.party_item, parent, false);
		sdf = new SimpleDateFormat("dd/MM HH:mm");
		
		SCTextView name = (SCTextView) partyRow.findViewById(R.id.partyName);
		SCTextView date = (SCTextView) partyRow.findViewById(R.id.partyDate);
		
//		for (String dateStr : e.getDateArray().split("|")) {
//			dateText += "";
//		}
		
		name.setText(e.getName());
		date.setText(sdf.format(e.getDate()));
		
		
		return partyRow;
	}
}
