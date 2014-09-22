package br.com.sevencode.android.feia2014;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import br.com.sevencode.android.feia2014.db.DaoMaster;
import br.com.sevencode.android.feia2014.db.DaoMaster.DevOpenHelper;
import br.com.sevencode.android.feia2014.db.DaoSession;
import br.com.sevencode.android.feia2014.db.Event;
import br.com.sevencode.android.feia2014.db.EventDao;
import br.com.sevencode.android.feia2014.db.MyEventDao;
import br.com.sevencode.android.feia2014.model.EventTO.EventCategory;
import br.com.sevencode.android.feia2014.task.LoadEventTask;
import br.com.sevencode.android.feia2014.util.TypefaceSpan;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	private SQLiteDatabase db;

	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private EventDao eventDao;
	private MyEventDao myEventDao;

	private int menuSelected;
	
	private Cursor cursor;
	
	protected ProgressDialog progressDialog = null;
	
	public void showThrobber(){
		this.hideThrobber();
		this.progressDialog = new ProgressDialog(this);
		this.progressDialog.setTitle("Carregando...");
		this.progressDialog.setMessage("Aguarde um instante");
		this.progressDialog.show();
	}
	
	public void hideThrobber(){
		if(this.progressDialog != null){
			if(this.progressDialog.isShowing()){
				this.progressDialog.dismiss();
			}
			
			this.progressDialog = null;
		}
	}
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        

		DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "feia-db",
				null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		eventDao = daoSession.getEventDao();
		myEventDao = daoSession.getMyEventDao();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        
        //eventDao.deleteAll();
        
        List<Event> events = eventDao.queryBuilder().list();
        
        if(events == null || events.size() == 0){
        	new LoadEventTask(this).execute();
        }
        
//        int titleId = getResources().getIdentifier("action_bar_title", "id",
//                "android");
//        TextView yourTextView = (TextView) findViewById(titleId);
//        Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/GeosansLight.ttf");
//        yourTextView.setTypeface(font);
    }
    
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//    	// TODO Auto-generated method stub
//    	super.onRestoreInstanceState(savedInstanceState);
//    	//menuSelected = savedInstanceState.getInt("menuSelected");
//    	goToFragment(getFragmentByPosition(menuSelected));
//    }
//    
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//    	// TODO Auto-generated method stub
//    	super.onSaveInstanceState(outState);
//    	outState.putInt("menuSelected", menuSelected);
//    }
    
    public void saveEvents(List<Event> events){
    	//eventDao.insertInTx(events);
    	for (Event object : events) {
			eventDao.insert(object);
		}
    	hideThrobber();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
    	BaseFragment fragment = getFragmentByPosition(position);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
    
    @Override
    public void setTitle(CharSequence title) {
    	// TODO Auto-generated method stub
    	super.setTitle(title);
    	
    	SpannableString s = new SpannableString(title);
    	s.setSpan(new TypefaceSpan(this, "GeosansLight.ttf"), 0, s.length(),
    	        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

    	// Update the action bar title with the TypefaceSpan instance
    	ActionBar actionBar = getActionBar();
    	actionBar.setTitle(s);
    }
    
    public void goToEventInfo(Event event) {
        // update the main content by replacing fragments
    	
//    	Intent intent = new Intent(this, EventInfoActivity.class);
//    	intent.putExtra("event", event);
//    	startActivity(intent);
    	menuSelected = -1;
    	EventInfoFragment fragment = new EventInfoFragment(event);
    	
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
        		.replace(R.id.container, fragment)
        		.addToBackStack("info")
                .commit();
    }
    
    public BaseFragment getFragmentByPosition(int position){
    	BaseFragment fragment = null;
    	
    	switch (position) {
		case 0:
			fragment = new CalendarFragment();
			break;
		case 1:
			fragment = new ExhibitionFragment();
			break;
		case 2:
			fragment = new PartyFragment();
			break;
		case 3:
			fragment = new PartnerFragment();
			break;
		case 4:
			//fragment = new ma();
			break;
		case 5:
			fragment = new WorkshopListFragment(EventCategory.GENERAL);
			break;
		case 6:
			fragment = new WorkshopListFragment(EventCategory.VISUAL_ARTS);
			break;
		case 7:
			fragment = new WorkshopListFragment(EventCategory.PERFORMING_ARTS);
			break;
		case 8:
			fragment = new WorkshopListFragment(EventCategory.DANCING);
			break;
		case 9:
			fragment = new WorkshopListFragment(EventCategory.MUSIC);
			break;
		default:
			fragment = new WorkshopListFragment(EventCategory.MEDIALOGY);
			break;
		}
    	
    	return fragment;
    }
    
    public int getPositionByFragment(BaseFragment fragment){
    	int position = 0;
    	
    	if(fragment instanceof CalendarFragment){
    		position = 0;
    	} else if(fragment instanceof ExhibitionFragment){
    		position = 1;
    	} else if (fragment instanceof PartyFragment) {
			position = 2;
		} else if (fragment instanceof PartnerFragment) {
			position = 3;
		//} else if (fragment instanceof ma) {
		//	position = 4;
		} else if (fragment instanceof WorkshopListFragment) {
			switch (((WorkshopListFragment)fragment).getSelectedCategory()) {
			case GENERAL:
				position = 5;
				break;
			case VISUAL_ARTS:
				position = 6;
				break;
			case PERFORMING_ARTS:
				position = 7;
				break;
			case DANCING:
				position = 8;
				break;
			case MUSIC:
				position = 9;
				break;
			case MEDIALOGY:
				position = 10;
				break;
			default:
				break;
			}
		}
    		
		return position;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
//            getMenuInflater().inflate(R.menu.main, menu);
//            restoreActionBar();
//            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	public EventDao getEventDao() {
		return eventDao;
	}

	public void setEventDao(EventDao eventDao) {
		this.eventDao = eventDao;
	}

	public MyEventDao getMyEventDao() {
		return myEventDao;
	}

	public void setMyEventDao(MyEventDao myEventDao) {
		this.myEventDao = myEventDao;
	}

	public int getMenuSelected() {
		return menuSelected;
	}

	public void setMenuSelected(int menuSelected) {
		this.menuSelected = menuSelected;
	}

	public NavigationDrawerFragment getNavigationDrawerFragment() {
		return mNavigationDrawerFragment;
	}

	public void setNavigationDrawerFragment(
			NavigationDrawerFragment mNavigationDrawerFragment) {
		this.mNavigationDrawerFragment = mNavigationDrawerFragment;
	}

//    /**
//     * A placeholder fragment containing a simple view.
//     */
//   / public static class PlaceholderFragment extends Fragment {
//        /**
//         * The fragment argument representing the section number for this
//         * fragment.
//         */
//        private static final String ARG_SECTION_NUMBER = "section_number";
//
//        /**
//         * Returns a new instance of this fragment for the given section
//         * number.
//         */
//        public static PlaceholderFragment newInstance(int sectionNumber) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        public PlaceholderFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//            return rootView;
//        }
//
//        @Override
//        public void onAttach(Activity activity) {
//            super.onAttach(activity);
//            ((MainActivity) activity).onSectionAttached(
//                    getArguments().getInt(ARG_SECTION_NUMBER));
//        }
//    }

}
