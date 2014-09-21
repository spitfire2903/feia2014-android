package br.com.sevencode.android.feia2014;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import br.com.sevencode.android.feia2014.db.DaoMaster;
import br.com.sevencode.android.feia2014.db.DaoMaster.DevOpenHelper;
import br.com.sevencode.android.feia2014.db.DaoSession;
import br.com.sevencode.android.feia2014.db.Event;
import br.com.sevencode.android.feia2014.db.EventDao;
import br.com.sevencode.android.feia2014.db.MyEventDao;
import br.com.sevencode.android.feia2014.model.EventTO.EventCategory;
import br.com.sevencode.android.feia2014.task.LoadEventTask;


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
        
        List<Event> events = eventDao.queryBuilder().list();
        
        if(events == null || events.size() == 0){
        	new LoadEventTask(this).execute();
        }
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onRestoreInstanceState(savedInstanceState);
    	//menuSelected = savedInstanceState.getInt("menuSelected");
    	goToFragment(getFragmentByPosition(menuSelected));
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	// TODO Auto-generated method stub
    	super.onSaveInstanceState(outState);
    	outState.putInt("menuSelected", menuSelected);
    }
    
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
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void goToFragment(BaseFragment fragment) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
        
        this.menuSelected = getPositionByFragment(fragment);
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
    
    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
