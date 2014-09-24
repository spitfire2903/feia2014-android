package br.com.sevencode.android.feia2014;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PartnerFragment extends BaseFragment {
    public PartnerFragment(MainActivity activity) {
		super(activity);
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_partner, container, false);
        
        return rootView;
    }
	
	@Override
	public void onStart() {
		super.onStart();
		
		getMainActivity().setTitle("Parceiros");
	}
}
