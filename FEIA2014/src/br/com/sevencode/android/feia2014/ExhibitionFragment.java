package br.com.sevencode.android.feia2014;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.sevencode.android.feia2014.model.EventTO.EventCategory;

public class ExhibitionFragment extends BaseFragment {
	private EventCategory selectedCategory;
	
	public ExhibitionFragment() {
		
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exhibition, container, false);
        getActivity().setTitle("Apresentações");
        return rootView;
    }
}
