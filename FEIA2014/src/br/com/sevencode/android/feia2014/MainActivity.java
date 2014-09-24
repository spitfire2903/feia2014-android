package br.com.sevencode.android.feia2014;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import br.com.sevencode.android.feia2014.db.DaoMaster;
import br.com.sevencode.android.feia2014.db.DaoMaster.DevOpenHelper;
import br.com.sevencode.android.feia2014.db.DaoSession;
import br.com.sevencode.android.feia2014.db.Event;
import br.com.sevencode.android.feia2014.db.Event.EventCategory;
import br.com.sevencode.android.feia2014.db.EventDao;
import br.com.sevencode.android.feia2014.db.EventDao.Properties;
import br.com.sevencode.android.feia2014.db.MyEventDao;
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

        showThrobber();
        
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        
        setTitle(mTitle);
        
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
        } else{
        	//makeChanges();
        	hideThrobber();
        }
        
        //showNotifications();
        
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
    	BaseFragment fragment = getFragmentByPosition(position);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
    
    @Override
    public void setTitle(CharSequence title) {
    	super.setTitle(title);
    	
    	mTitle = title;
    	
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
    	EventInfoFragment fragment = new EventInfoFragment(event, this);
    	
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
			fragment = new CalendarFragment(this);
			break;
		case 1:
			fragment = new ExhibitionFragment(this);
			break;
		case 2:
			fragment = new PartyFragment(this);
			break;
		case 3:
			fragment = new PartnerFragment(this);
			break;
		case 4:
			//fragment = new ma();
			break;
		case 5:
			fragment = new WorkshopListFragment(EventCategory.GENERAL,this);
			break;
		case 6:
			fragment = new WorkshopListFragment(EventCategory.VISUAL_ARTS,this);
			break;
		case 7:
			fragment = new WorkshopListFragment(EventCategory.PERFORMING_ARTS,this);
			break;
		case 8:
			fragment = new WorkshopListFragment(EventCategory.DANCING,this);
			break;
		case 9:
			fragment = new WorkshopListFragment(EventCategory.MUSIC,this);
			break;
		default:
			fragment = new WorkshopListFragment(EventCategory.MEDIALOGY,this);
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

	public void showNotifications(){
		NotificationManager mNotificationManager = null;
		NotificationCompat.Builder mNotifyBuilder = null;
		int numMessages = 0;
		
		mNotificationManager =
		        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// Sets an ID for the notification, so it can be updated
		int notifyID = 1;
		mNotifyBuilder = new NotificationCompat.Builder(this)
		    .setContentTitle("O Evento vai começar")
		    .setContentText("Você tem novas notificações!")
		    .setSmallIcon(R.drawable.ic_notification_small);
		numMessages = 0;
		
		mNotifyBuilder.setContentText("current Texto");//.setNumber(++numMessages);

		mNotificationManager.notify(notifyID, mNotifyBuilder.build());
	}
	
	public void makeChanges(){
		Event event = null;
		List<Event> events = null;
		
		events = eventDao.queryBuilder().where(Properties.EventId.eq(93)).list();
		
		// Altera João Bosco para apresentações ao inves de noite FEIA
		for (Event e : events) {
			if(e.getName().contains("Bosco")){
				e.setType(1);
				e.setCategory(1);
				e.setName(e.getName().substring(e.getName().indexOf("João Bosco")));
		
				eventDao.update(e);
			}
		}
		
		events = eventDao.queryBuilder().where(Properties.EventId.eq(83)).list();
		
		// Remove Duo Catrumano 
		for (Event e : events) {
			if(e.getName().contains("Catrumano")){
				eventDao.delete(e);
			}
		}
		
		events = eventDao.queryBuilder().where(Properties.EventId.eq(85)).list();
		
		// Altera lugar do Meia duzia de 3 ou 4
		for (Event e : events) {
			if(e.getName().contains("3 ou 4")){
				e.setPlaceData("Vão do PB");
				
				eventDao.update(e);
			}
		}
		
		events = eventDao.queryBuilder().where(Properties.EventId.eq(90)).list();
		
		// Altera noite feia do padre do balão para galo de briga
		for (Event e : events) {
			if(e.getName().contains("Padre")){
				e.setName("Noite FEIA - Galo de Briga");
				e.setDescription("O grupo Galos de Briga se propõe a compartilhar um " +
						"repertório vasto que abrange várias vertentes do samba, " +
						"valorizando tanto a formação tradicional de instrumentos " +
						"que compõem uma clássica roda de samba como uma " +
						"instrumentação mais moderna. De Nelson Cavaquinho, Cartola, " +
						"Paulinho da Viola a sambistas mais recentes como compositores " +
						"do Cacique de Ramos e outros contemporâneos, o repertório " +
						"abarca de maneira significativa a trajetória pela qual esse " +
						"estilo musical se consolidou e vem se ressignificando. O " +
						"grupo é composto por músicos amigos que estudaram e se " +
						"conheceram na Unicamp e em projetos culturais que têm como " +
						"norte principal o samba, buscando não somente a execução " +
						"mecânica da música, mas sim o engrandecimento deste em sua " +
						"expressão no âmbito sócio-cultural.");
				
				eventDao.update(e);
			}
		}
		
	}

}
