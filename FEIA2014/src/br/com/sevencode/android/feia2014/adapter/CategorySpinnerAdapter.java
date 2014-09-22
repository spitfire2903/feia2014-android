package br.com.sevencode.android.feia2014.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.sevencode.android.feia2014.BaseFragment;
import br.com.sevencode.android.feia2014.R;

public class CategorySpinnerAdapter extends BaseAdapter {

	private static final String[] CATEGORY = new String[] { "Todos",
        "Dança", "Música", "Artes Visuais", "Artes Cênicas", "Midialogia" };
	
	private LayoutInflater mInflater;
	private BaseFragment fragment;

	public CategorySpinnerAdapter(BaseFragment fragment) {
		// TODO Auto-generated constructor stub
		mInflater = LayoutInflater.from(fragment.getActivity());
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return CATEGORY.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ListContent holder;
		View v = convertView;
		if (v == null) {
			v = mInflater.inflate(R.layout.category_spinner_item, null);
			holder = new ListContent();

			holder.name = (TextView) v.findViewById(R.id.textView1);

			v.setTag(holder);
		} else {

			holder = (ListContent) v.getTag();
		}

		holder.name.setText(""+CATEGORY[position]);

		return v;
	}

	static class ListContent {

		TextView name;

	}
}

