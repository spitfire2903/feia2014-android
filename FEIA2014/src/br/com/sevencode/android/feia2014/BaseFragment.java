package br.com.sevencode.android.feia2014;

import java.util.List;

import android.app.Fragment;
import android.content.Context;
import br.com.sevencode.android.feia2014.db.Event;

public class BaseFragment extends Fragment {
	protected MainActivity mMainActivity;
	private List<Event> mEvents;
	
	public BaseFragment(MainActivity activity) {
		// TODO Auto-generated constructor stub
		this.mMainActivity = activity;
	}

	public List<Event> getEvents() {
		return mEvents;
	}

	public void setEvents(List<Event> events) {
		this.mEvents = events;
	}
	
	public void setAdapter(Context context){}

	public void showThrobber(){
		((MainActivity)getActivity()).showThrobber();
	}
	
	public void hideThrobber(){
		((MainActivity)getActivity()).hideThrobber();
	}

	public MainActivity getMainActivity() {
		return mMainActivity;
	}

	public void setMainActivity(MainActivity mMainActivity) {
		this.mMainActivity = mMainActivity;
	}
}
