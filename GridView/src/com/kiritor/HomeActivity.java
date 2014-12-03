package com.kiritor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class HomeActivity extends Activity implements MultiChoiceModeListener {

	private GridView mGridView;
	private GridAdapter mGridAdapter;
	private TextView mActionText;
	private static final int MENU_SELECT_ALL = 0;
	private static final int MENU_UNSELECT_ALL = MENU_SELECT_ALL + 1;
	private Map<Integer, Boolean> mSelectMap = new HashMap<Integer, Boolean>();

	private int[] mImgIds = new int[] { R.drawable.w, R.drawable.w,
			R.drawable.w, R.drawable.w, R.drawable.w,
			R.drawable.w, R.drawable.w, R.drawable.w,
			R.drawable.w, R.drawable.w, R.drawable.w,
			R.drawable.w, R.drawable.w, R.drawable.w,
			R.drawable.w, R.drawable.w, R.drawable.w,
			R.drawable.w, R.drawable.w, R.drawable.w,
			R.drawable.w, R.drawable.w, R.drawable.w,
			R.drawable.w, R.drawable.w, R.drawable.w,
			R.drawable.w, R.drawable.w, R.drawable.w };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mGridView = (GridView) findViewById(R.id.gridview);
		mGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
		mGridAdapter = new GridAdapter(this);
		mGridView.setAdapter(mGridAdapter);
		mGridView.setMultiChoiceModeListener(this);
	}

	/** Override MultiChoiceModeListener start **/
	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {

		View v = LayoutInflater.from(this).inflate(R.layout.actionbar_layout,
				null);
		mActionText = (TextView) v.findViewById(R.id.action_text);

		mActionText.setText(formatString(mGridView.getCheckedItemCount()));

		mode.setCustomView(v);
		getMenuInflater().inflate(R.menu.action_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		menu.getItem(MENU_SELECT_ALL).setEnabled(
				mGridView.getCheckedItemCount() != mGridView.getCount());
		return true;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_select:
			for (int i = 0; i < mGridView.getCount(); i++) {
				mGridView.setItemChecked(i, true);
				mSelectMap.put(i, true);
			}
			break;
		case R.id.menu_unselect:
			for (int i = 0; i < mGridView.getCount(); i++) {
				mGridView.setItemChecked(i, false);
				mSelectMap.clear();
			}
			break;
		}
		return true;
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		
		mGridAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemCheckedStateChanged(ActionMode mode, int position,
			long id, boolean checked) {

		mActionText.setText(formatString(mGridView.getCheckedItemCount()));
		mSelectMap.put(position, checked);
		mode.invalidate();
	}

	/** Override MultiChoiceModeListener end **/

	private String formatString(int count) {
		return String.format(getString(R.string.selection), count);
	}

	private class GridAdapter extends BaseAdapter {

		private Context mContext;

		public GridAdapter(Context ctx) {
			mContext = ctx;
		}

		@Override
		public int getCount() {
			
			return mImgIds.length;
		}

		@Override
		public Integer getItem(int position) {
			
			return Integer.valueOf(mImgIds[position]);
		}

		@Override
		public long getItemId(int position) {
			
			return position;
		}

		@SuppressWarnings("deprecation")
		@Override

		public View getView(int position, View convertView, ViewGroup parent) {
			GridItem item;
			if (convertView == null) {
				item = new GridItem(mContext);
				item.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
						LayoutParams.FILL_PARENT));
			} else {
				item = (GridItem) convertView;
			}
			item.setImgResId(getItem(position));
			item.setChecked(mSelectMap.get(position) == null ? false
					: mSelectMap.get(position));
			return item;
		}
	}

}