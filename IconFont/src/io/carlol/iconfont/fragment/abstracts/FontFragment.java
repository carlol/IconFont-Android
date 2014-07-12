package io.carlol.iconfont.fragment.abstracts;

import io.carlol.iconfont.R;
import io.carlol.iconfont.base.BaseSimpleFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

/**
 * 
 * @author c.luchessa
 *
 */
public abstract class FontFragment extends BaseSimpleFragment {

	private GridView mIconContainer;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.fragment_icon_grid, null);

		mIconContainer = (GridView) mContentView.findViewById(R.id.icon_container);
		
		return mContentView;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mIconContainer.setAdapter( ArrayAdapter.createFromResource(getActivity()
				, this.getIconStringArrayResourceId()
				, this.getIconLayoytResourceId()
				));
		
		mIconContainer.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Toast.makeText(getActivity(), position+"", Toast.LENGTH_LONG).show();
			}
		});
	}
	
	
	protected abstract int getIconStringArrayResourceId();
	
	
	protected abstract int getIconLayoytResourceId();
}
