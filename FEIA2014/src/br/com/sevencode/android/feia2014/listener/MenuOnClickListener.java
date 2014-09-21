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
	private NavigationDrawerFragment mNavigationDrawer;
	
	public MenuOnClickListener(NavigationDrawerFragment navigationDrawer, BaseFragment fragment) {
		this.mFragment = fragment;
		mNavigationDrawer = navigationDrawer;
		this.mActivity = (MainActivity) navigationDrawer.getActivity();
	}
	
	@Override
	public void onClick(View v) {
		mNavigationDrawer.dismissDrawer();
		mActivity.goToFragment(this.mFragment);
		//((Button)v).setSelected(true);
	}

}
