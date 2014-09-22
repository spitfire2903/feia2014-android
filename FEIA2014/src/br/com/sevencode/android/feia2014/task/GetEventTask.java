package br.com.sevencode.android.feia2014.task;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import br.com.sevencode.android.feia2014.BaseFragment;
import br.com.sevencode.android.feia2014.MainActivity;
import br.com.sevencode.android.feia2014.db.DaoMaster;
import br.com.sevencode.android.feia2014.db.DaoMaster.DevOpenHelper;
import br.com.sevencode.android.feia2014.db.DaoSession;
import br.com.sevencode.android.feia2014.db.Event;
import br.com.sevencode.android.feia2014.db.EventDao;
import br.com.sevencode.android.feia2014.db.EventDao.Properties;
import br.com.sevencode.android.feia2014.model.EventTO.EventCategory;
import br.com.sevencode.android.feia2014.model.EventTO.EventType;
import de.greenrobot.dao.query.QueryBuilder;

public class GetEventTask extends AsyncTask<Void, Void, Void> {

	private BaseFragment mFragment;
	private MainActivity mActivity;
	private List<Event> mEvents;
	private EventCategory mCategory;
	private EventType mType;

	private SQLiteDatabase db;

	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private EventDao eventDao;

	public GetEventTask(BaseFragment fragment, EventCategory category,
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
		eventDao = daoSession.getEventDao();
		
		mActivity.showThrobber();
		
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		QueryBuilder<Event> query = null;
		query = eventDao.queryBuilder();

		if (mCategory != null) {
			query.where(Properties.Category.eq(mCategory.intValue()));
		}

		if (mType != null) {
			query.where(Properties.Type.eq(mType.intValue()));
		}

		mEvents = query.orderAsc(Properties.Date).list();

		return null;
	};

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		mFragment.setEvents(mEvents);
		mFragment.setAdapter();
		mActivity.hideThrobber();
	}

}
