package br.com.sevencode.android.feia2014;

import android.app.ActionBar.OnNavigationListener;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import br.com.sevencode.android.feia2014.adapter.CategorySpinnerAdapter;
import br.com.sevencode.android.feia2014.adapter.ExhibitionAdapter;
import br.com.sevencode.android.feia2014.db.Event.EventCategory;
import br.com.sevencode.android.feia2014.db.Event.EventType;
import br.com.sevencode.android.feia2014.task.GetEventTask;

public class ExhibitionFragment extends BaseFragment implements OnNavigationListener{
	private EventCategory selectedCategory;
	private GridView exhibitionGridView = null;

	private static final String[] CATEGORY = new String[] { "Todos",
		"Dança", "Música", "Artes Visuais", "Artes Cênicas", "Midialogia" };

	public ExhibitionFragment(MainActivity activity) {
		super(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		selectedCategory = EventCategory.GENERAL;
		
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		if (!((MainActivity)getActivity()).getNavigationDrawerFragment().isDrawerOpen()) {
			inflater.inflate(R.menu.exhibition, menu);
			MenuItem mSpinnerItem1 = menu.findItem( R.id.menu_category);
			View view1 = mSpinnerItem1.getActionView();
			if (view1 instanceof Spinner)
			{
				int position = 0;
				if(selectedCategory != null && selectedCategory != EventCategory.GENERAL){
					position = selectedCategory.intValue()+1;
				}
				
				final Spinner spinner = (Spinner) view1;
				SpinnerAdapter adapter = new CategorySpinnerAdapter(this);//ArrayAdapter.createFromResource(getActivity(), R.array.category_type, android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(adapter);
				spinner.setSelection(position);
				spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						EventCategory category = null;

						switch (arg2) {
						case 0:

							break;
						case 1:
							category = EventCategory.DANCING;
							break;
						case 2:
							category = EventCategory.MUSIC;
							break;
						case 3:
							category = EventCategory.VISUAL_ARTS;
							break;
						case 4:
							category = EventCategory.PERFORMING_ARTS;
							break;
						case 5:
							category = EventCategory.MEDIALOGY;
							break;

						default:
							break;
						}

						selectedCategory = category;
						
						GetEventTask task = new GetEventTask(ExhibitionFragment.this, category, EventType.EXHIBITION);
						task.execute();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

			}

		}
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {

		super.onPrepareOptionsMenu(menu);
		//		if(isEventFavorited()){
		//			menu.getItem(0).setEnabled(false);
		//			menu.getItem(0).setTitle(R.string.action_saved);
		//			menu.getItem(0).setChecked(true);
		//		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_category:
			/*
			MyEvent myEvent = new MyEvent();
			myEvent.setEventId(event.getId());
			myEvent.setEvent(event);

			myEventDao.insert(myEvent);

			item.setEnabled(false);
			item.setTitle(R.string.action_saved);
			item.setChecked(true);*/
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_exhibition, container, false);

		exhibitionGridView = (GridView) rootView.findViewById(R.id.exhibitionListGrid);
		exhibitionGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				((MainActivity)getActivity()).goToEventInfo(getEvents().get(position));

			}
		});

		GetEventTask task = new GetEventTask(this, null, EventType.EXHIBITION);
		task.execute();

		return rootView;
	}

	@Override
	public void onStart() {
		super.onStart();
		
		getMainActivity().setTitle("Apresentações");
	}
	
	@Override
	public void setAdapter(Context context) {
		super.setAdapter(context);
		if(exhibitionGridView != null)
			exhibitionGridView.setAdapter(new ExhibitionAdapter(context, R.layout.exhibition_item, getEvents()));
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public EventCategory getSelectedCategory(){
		return selectedCategory;
	}


}
