package br.com.sevencode.android.feia2014;

import br.com.sevencode.android.feia2014.adapter.CalendarAdapter;
import br.com.sevencode.android.feia2014.task.GetMyEventTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CalendarFragment extends BaseFragment {

	private ListView calendarList;
	
	public CalendarFragment() {
		// TODO Auto-generated constructor stub
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
    public void setAdapter() {
    	// TODO Auto-generated method stub
    	super.setAdapter();
    	if(calendarList != null)
    		calendarList.setAdapter(new CalendarAdapter(getActivity(), R.layout.calendar_item, getEvents()));
    }
}
