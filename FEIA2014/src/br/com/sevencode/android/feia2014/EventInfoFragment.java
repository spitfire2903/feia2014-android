package br.com.sevencode.android.feia2014;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.com.sevencode.android.feia2014.components.SCTextView;
import br.com.sevencode.android.feia2014.db.DaoMaster;
import br.com.sevencode.android.feia2014.db.DaoMaster.DevOpenHelper;
import br.com.sevencode.android.feia2014.db.DaoSession;
import br.com.sevencode.android.feia2014.db.Event;
import br.com.sevencode.android.feia2014.db.MyEvent;
import br.com.sevencode.android.feia2014.db.MyEventDao;
import br.com.sevencode.android.feia2014.db.MyEventDao.Properties;
import br.com.sevencode.android.feia2014.model.EventTO.EventCategory;
import br.com.sevencode.android.feia2014.model.EventTO.EventType;

public class EventInfoFragment extends BaseFragment {
	private Event event;

	private SCTextView eventName;
	private SCTextView eventDate;
	private SCTextView eventAuthor;
	private SCTextView eventDescription;

	private RelativeLayout headerView;

	private SQLiteDatabase db;

	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private MyEventDao myEventDao;

	public EventInfoFragment(Event event) {
		this.event = event;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		DevOpenHelper helper = null;

		helper = new DaoMaster.DevOpenHelper(getActivity(), "feia-db",
				null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		myEventDao = daoSession.getMyEventDao();
		
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (!((MainActivity)getActivity()).getNavigationDrawerFragment().isDrawerOpen()) {
        	inflater.inflate(R.menu.event_info, menu);
        	
//        	getActivity().getLayoutInflater().setFactory(new Factory() {
//              public View onCreateView(String name, Context context,
//                      AttributeSet attrs) {
//  
//                  if (name.equalsIgnoreCase(
//                          "com.android.internal.view.menu.IconMenuItemView")) {
//                      try {
//                          LayoutInflater li = LayoutInflater.from(context);
//                          final View view = li.createView(name, null, attrs);
//                          new Handler().post(new Runnable() {
//                              public void run() {
//                                  // set the background drawable if you want that
//                                  //or keep it default -- either an image, border
//                                  //gradient, drawable, etc.
//                                  
//                                  // set the text color
//                              	Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GeosansLight.ttf");    
//                                  ((TextView) view).setTypeface(face);      
//                              }
//                          });
//                          return view;
//                      } catch (InflateException e) {
//                          //Handle any inflation exception here
//                      } catch (ClassNotFoundException e) {
//                          //Handle any ClassNotFoundException here
//                      }
//                  }
//                  return null;
//              }
//          });
//        }
        }
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {

		super.onPrepareOptionsMenu(menu);
		if(isEventFavorited()){
			menu.getItem(0).setEnabled(false);
			menu.getItem(0).setTitle(R.string.action_saved);
			menu.getItem(0).setChecked(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_save:
			
			MyEvent myEvent = new MyEvent();
			myEvent.setEventId(event.getId());
			myEvent.setEvent(event);
			
			myEventDao.insert(myEvent);
			
			item.setEnabled(false);
			item.setTitle(R.string.action_saved);
			item.setChecked(true);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_event_info, container, false);
		getActivity().setTitle(event.getName());

		String eventDateStr = "";

		eventName = (SCTextView)rootView.findViewById(R.id.eventInfoName);
		eventDate = (SCTextView)rootView.findViewById(R.id.eventInfoDate);
		eventAuthor = (SCTextView)rootView.findViewById(R.id.eventInfoAuthor);
		eventDescription = (SCTextView)rootView.findViewById(R.id.eventInfoDescription);
		headerView = (RelativeLayout)rootView.findViewById(R.id.headerView);

		headerView.setBackgroundColor(getEventColor());

		eventName.setText(event.getName());
		eventAuthor.setText(event.getAuthor());
		eventDescription.setText(event.getDescription());

		for (String dateStr : event.getDateArray().split("\\|")) {

			eventDateStr += (dateStr+" / ");
		}

		eventDateStr = eventDateStr.substring(0, eventDateStr.lastIndexOf(" /"));

		eventDate.setText(eventDateStr);

		return rootView;
	}

	public Boolean isEventFavorited(){
		Boolean isFavorited = false;
		List<MyEvent> events = null; 

		events = myEventDao.queryBuilder().where(Properties.EventId.eq(event.getId())).list();

		isFavorited = (events != null && events.size() > 0 ? true : false);

		return isFavorited;
	}

	public int getEventColor(){
		int color = 0;

		switch (EventCategory.getEventCategory(event.getCategory().intValue())) {
		case DANCING:
			color = getResources().getColor(R.color.dancingColor);	
			break;
		case MUSIC:
			color = getResources().getColor(R.color.musicColor);	
			break;
		case PERFORMING_ARTS:
			color = getResources().getColor(R.color.performingArtsColor);	
			break;
		case VISUAL_ARTS:
			color = getResources().getColor(R.color.visualArtsColor);	
			break;
		case MEDIALOGY:
			color = getResources().getColor(R.color.medialogyColor);	
			break;
		case GENERAL:
			color = getResources().getColor(R.color.generalColor);	
			break;
		default:
			break;
		}

		if(EventType.PARTY.intValue() == event.getType().intValue()){
			color = getResources().getColor(R.color.partyColor);	
		}

		return color;

	}
}
