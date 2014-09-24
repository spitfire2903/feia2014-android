package br.com.sevencode.android.feia2014;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.sevencode.android.feia2014.adapter.CalendarAdapter;
import br.com.sevencode.android.feia2014.db.Event;
import br.com.sevencode.android.feia2014.task.GetMyEventTask;

public class CalendarFragment extends BaseFragment {

	private ListView calendarList;
	
	public CalendarFragment(MainActivity activity) {
		super(activity);
	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        getActivity().setTitle("Meu Calendário");
        
        calendarList = (ListView) rootView.findViewById(R.id.calendarList);
        calendarList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				((MainActivity)getActivity()).goToEventInfo(getEvents().get(position));

			}
		});
        GetMyEventTask task = new GetMyEventTask(this, null, null);
        task.execute();
        
        return rootView;
    }
    
    @Override
    public void setEvents(List<Event> events) {
    	super.setEvents(events);
    	
    	if(events == null || events.size() == 0){
    		Toast.makeText(getMainActivity(), "Ainda não há eventos no seu calendário", Toast.LENGTH_LONG).show();
    	}
    }
    
    @Override
    public void setAdapter(Context context) {
    	// TODO Auto-generated method stub
    	super.setAdapter(context);
    	if(calendarList != null)
    		calendarList.setAdapter(new CalendarAdapter(context, R.layout.calendar_item, getEvents()));
    }
}
