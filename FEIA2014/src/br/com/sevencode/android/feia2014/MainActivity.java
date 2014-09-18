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
import android.widget.EditText;
import br.com.sevencode.android.feia2014.db.DaoMaster;
import br.com.sevencode.android.feia2014.db.DaoSession;
import br.com.sevencode.android.feia2014.db.DaoMaster.DevOpenHelper;
import br.com.sevencode.android.feia2014.db.Event;
import br.com.sevencode.android.feia2014.db.EventDao;
import br.com.sevencode.android.feia2014.db.MyEventDao;
import br.com.sevencode.android.feia2014.task.LoadEventTask;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	private SQLiteDatabase db;

	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private EventDao eventDao;
	private MyEventDao myEventDao;

	private Cursor cursor;
	
	protected ProgressDialog progressDialog = null;
	
	public void showThrobber(){
		this.hideThrobber();
		this.progressDialog = new ProgressDialog(this);
		this.progressDialog.setTitle("Carregando...");
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
/*
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "feia-db",
				null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		eventDao = daoSession.getEventDao();
		myEventDao = daoSession.getMyEventDao();*/
/*
		String textColumn = NoteDao.Properties.Text.columnName;
		String orderBy = textColumn + " COLLATE LOCALIZED ASC";
		cursor = db.query(noteDao.getTablename(), noteDao.getAllColumns(),
				null, null, null, null, orderBy);
		String[] from = { textColumn, NoteDao.Properties.Comment.columnName };
		int[] to = { android.R.id.text1, android.R.id.text2 };

		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_2, cursor, from, to);
		setListAdapter(adapter);

		editText = (EditText) findViewById(R.id.editTextNote);
		addUiListeners();
	
	}
*/
/*
	private void addNote() {
		String noteText = editText.getText().toString();
		editText.setText("");

		final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
				DateFormat.MEDIUM);
		String comment = "Added on " + df.format(new Date());
		Note note = new Note(null, noteText, comment, new Date());
		noteDao.insert(note);
		Log.d("DaoExample", "Inserted new note, ID: " + note.getId());

		cursor.requery();
	}*/

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
    
    public void saveEvents(List events){
    	eventDao.insertInTx(events);
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
