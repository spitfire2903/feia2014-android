package br.com.sevencode.android.feia2014;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import br.com.sevencode.android.feia2014.adapter.WorkshopAdapter;
import br.com.sevencode.android.feia2014.db.Event.EventCategory;
import br.com.sevencode.android.feia2014.db.Event.EventType;
import br.com.sevencode.android.feia2014.task.GetEventTask;

public class WorkshopListFragment extends BaseFragment {
	private GridView workshopGridView = null;
	private EventCategory selectedCategory;
	
	public WorkshopListFragment(EventCategory category, MainActivity activity) {
		super(activity);
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
		
        workshopGridView = (GridView) rootView.findViewById(R.id.workshopListGrid);
		workshopGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				((MainActivity)getActivity()).goToEventInfo(getEvents().get(position));

			}
		});

		GetEventTask task = new GetEventTask(this, selectedCategory, EventType.WORKSHOP);
		task.execute();

		return rootView;
	}

    @Override
    public void onStart() {
    	super.onStart();
    	
    	getMainActivity().setTitle("Oficina "+selectedCategory.getEventCategoryDescription());
    }
    
	@Override
	public void setAdapter(Context context) {
		super.setAdapter(context);
		if(workshopGridView != null)
			workshopGridView.setAdapter(new WorkshopAdapter(context, R.layout.workshop_item, getEvents()));
	}
}
