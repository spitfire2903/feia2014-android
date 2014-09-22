package br.com.sevencode.android.feia2014;

import java.util.List;

import android.app.Fragment;
import br.com.sevencode.android.feia2014.db.Event;

public class BaseFragment extends Fragment {

	private List<Event> mEvents;
	
	public BaseFragment() {
		// TODO Auto-generated constructor stub
	}

	public List<Event> getEvents() {
		return mEvents;
	}

	public void setEvents(List<Event> events) {
		this.mEvents = events;
	}
	
	public void setAdapter(){}

	public void showThrobber(){
		((MainActivity)getActivity()).showThrobber();
	}
	
	public void hideThrobber(){
		((MainActivity)getActivity()).hideThrobber();
	}
}
