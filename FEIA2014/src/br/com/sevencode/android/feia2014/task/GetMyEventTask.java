package br.com.sevencode.android.feia2014.task;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import br.com.sevencode.android.feia2014.BaseFragment;
import br.com.sevencode.android.feia2014.MainActivity;
import br.com.sevencode.android.feia2014.db.DaoMaster;
import br.com.sevencode.android.feia2014.db.DaoMaster.DevOpenHelper;
import br.com.sevencode.android.feia2014.db.DaoSession;
import br.com.sevencode.android.feia2014.db.Event;
import br.com.sevencode.android.feia2014.db.Event.EventCategory;
import br.com.sevencode.android.feia2014.db.Event.EventType;
import br.com.sevencode.android.feia2014.db.EventDao;
import br.com.sevencode.android.feia2014.db.EventDao.Properties;
import br.com.sevencode.android.feia2014.db.MyEvent;
import br.com.sevencode.android.feia2014.db.MyEventDao;
import de.greenrobot.dao.query.QueryBuilder;

public class GetMyEventTask extends AsyncTask<Void, Void, Void> {

	private BaseFragment mFragment;
	private MainActivity mActivity;
	private List<Event> mEvents;
	private EventCategory mCategory;
	private EventType mType;

	private SQLiteDatabase db;

	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private MyEventDao myEventDao;
	private EventDao eventDao;

	public GetMyEventTask(BaseFragment fragment, EventCategory category,
			EventType type) {
		mFragment = fragment;
		mActivity = (MainActivity) fragment.getActivity();
		mCategory = category;
		mType = type;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

		DevOpenHelper helper = new DaoMaster.DevOpenHelper(mActivity, "feia-db",
				null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		myEventDao = daoSession.getMyEventDao();
		eventDao = daoSession.getEventDao();
		
		mActivity.showThrobber();
		
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		List<MyEvent> myEvents = null;
		List<Long> eventIds = null;
		QueryBuilder<Event> query = null;
		
		myEvents = myEventDao.queryBuilder().list();
		
		eventIds = new ArrayList<Long>();
		
		for (MyEvent event : myEvents) {
			eventIds.add(event.getEventId());
		}
		
		query = eventDao.queryBuilder();
		query.where(Properties.Id.in(eventIds));
		mEvents = query.orderAsc(Properties.Date).list();

		return null;
	};

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		mFragment.setEvents(mEvents);
		mFragment.setAdapter(mActivity);
		mActivity.hideThrobber();
	}

}
