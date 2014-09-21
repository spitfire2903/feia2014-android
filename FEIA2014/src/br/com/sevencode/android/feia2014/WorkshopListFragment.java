package br.com.sevencode.android.feia2014;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.sevencode.android.feia2014.model.EventTO.EventCategory;

public class WorkshopListFragment extends BaseFragment {
	
	private EventCategory selectedCategory;
	
	public WorkshopListFragment(EventCategory category) {
		selectedCategory = category;
	}

	public EventCategory getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(EventCategory selectedCategory) {
		this.selectedCategory = selectedCategory;
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_workshop, container, false);
        getActivity().setTitle("Oficina "+selectedCategory.getEventCategoryDescription());
        return rootView;
    }
}
