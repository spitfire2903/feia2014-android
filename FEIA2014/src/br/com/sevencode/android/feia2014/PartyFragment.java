package br.com.sevencode.android.feia2014;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import br.com.sevencode.android.feia2014.adapter.PartyAdapter;
import br.com.sevencode.android.feia2014.model.EventTO.EventType;
import br.com.sevencode.android.feia2014.task.GetEventTask;

public class PartyFragment extends BaseFragment {

	public PartyFragment(MainActivity activity) {
		super(activity);
	}

	private GridView partyList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_party, container, false);
		getActivity().setTitle("Noites FEIA");

		partyList = (GridView) rootView.findViewById(R.id.partyListGrid);
		partyList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				((MainActivity)getActivity()).goToEventInfo(getEvents().get(position));

			}
		});

		GetEventTask task = new GetEventTask(this, null, EventType.PARTY);
		task.execute();
		
		return rootView;
	}

//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//
//		GetEventTask task = new GetEventTask(this, null, EventType.PARTY);
//		task.execute();
//
//	}

	@Override
	public void setAdapter(Context context){
		if (partyList != null) {
			partyList.setAdapter(new PartyAdapter(context,
					R.layout.party_item, getEvents()));
		}
	}
}
