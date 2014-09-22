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

public class ExhibitionAdapter extends ArrayAdapter<Event> {
	private Context context;
	private List<Event> eventList;

	public ExhibitionAdapter(Context context, int resource, List<Event> objects) {
		super(context, resource, objects);
		this.context = context;
		eventList = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Event e = null;
		String dateText = null;
		LayoutInflater inflater = null;
		View exhibitionRow = null;
		SimpleDateFormat sdf = null;

		e = eventList.get(position);
		dateText = "";
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		exhibitionRow = inflater.inflate(R.layout.exhibition_item, parent, false);
		sdf = new SimpleDateFormat("dd/MM HH:mm");

		SCTextView name = (SCTextView) exhibitionRow.findViewById(R.id.exhibitionName);
		SCTextView date = (SCTextView) exhibitionRow.findViewById(R.id.exhibitionDate);
		ImageView icon = (ImageView) exhibitionRow.findViewById(R.id.exhibitionIcon);
		//		for (String dateStr : e.getDateArray().split("|")) {
		//			dateText += "";
		//		}

		name.setText(e.getName());
		date.setText(sdf.format(e.getDate()));
		icon.setImageDrawable(getEventIcon(e));

		return exhibitionRow;
	}

	public Drawable getEventIcon(Event event){
		Drawable drawable = null;

		switch (EventCategory.getEventCategory(event.getCategory().intValue())) {
		case DANCING:
			drawable = this.context.getResources().getDrawable(R.drawable.ic_dancing_small);	
			break;
		case MUSIC:
			drawable = this.context.getResources().getDrawable(R.drawable.ic_music_small);	
			break;
		case PERFORMING_ARTS:
			drawable = this.context.getResources().getDrawable(R.drawable.ic_performing_arts_small);	
			break;
		case VISUAL_ARTS:
			drawable = this.context.getResources().getDrawable(R.drawable.ic_visual_arts_small);	
			break;
		case MEDIALOGY:
			drawable = this.context.getResources().getDrawable(R.drawable.ic_medialogy_small);	
			break;
		case GENERAL:
			drawable = this.context.getResources().getDrawable(R.drawable.ic_credits_small);	
			break;
		default:
			drawable = this.context.getResources().getDrawable(R.drawable.ic_credits_small);
			break;
		}

		return drawable;

	}
}
