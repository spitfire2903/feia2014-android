package br.com.sevencode.android.feia2014;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CalendarFragment extends BaseFragment {

	public CalendarFragment() {
		// TODO Auto-generated constructor stub
	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        getActivity().setTitle("Meu Calendário");
        return rootView;
    }
}
