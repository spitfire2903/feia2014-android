package br.com.sevencode.android.feia2014.listener;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.sevencode.android.feia2014.BaseFragment;
import br.com.sevencode.android.feia2014.MainActivity;
import br.com.sevencode.android.feia2014.NavigationDrawerFragment;

public class MenuOnClickListener implements OnClickListener {

	private BaseFragment mFragment;
	private MainActivity mActivity;
	private int mPosition = 0;
	private NavigationDrawerFragment mNavigationDrawer;
	
	public MenuOnClickListener(NavigationDrawerFragment navigationDrawer, BaseFragment fragment) {
		this.mFragment = fragment;
		mNavigationDrawer = navigationDrawer;
		this.mActivity = (MainActivity) navigationDrawer.getActivity();
	}
	
	public MenuOnClickListener(NavigationDrawerFragment navigationDrawer, int position) {
		this.mPosition = position;
		mNavigationDrawer = navigationDrawer;
		this.mActivity = (MainActivity) navigationDrawer.getActivity();
	}
	
	@Override
	public void onClick(View v) {
		if(mFragment != null){
			mNavigationDrawer.selectItem(mActivity.getPositionByFragment(mFragment));
		} else {
			mNavigationDrawer.selectItem(mPosition);
		}
		
		mNavigationDrawer.dismissDrawer();
		//mActivity.goToFragment(this.mFragment);
		//((Button)v).setSelected(true);
	}

}
