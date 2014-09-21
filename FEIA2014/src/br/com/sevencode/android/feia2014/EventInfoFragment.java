package br.com.sevencode.android.feia2014;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.sevencode.android.feia2014.model.EventTO;

public class EventInfoFragment extends BaseFragment {
	private EventTO event;
	
	public EventInfoFragment(EventTO event) {
		this.event = event;
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_info, container, false);
        getActivity().setTitle(event.getName());
        return rootView;
    }
}
