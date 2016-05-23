package com.examplelx.lxbaseadapterdemo;

import java.util.ArrayList;
import java.util.List;

import Util.LXBaseAdapter;
import Util.LXIndexPath;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 若需实现头视图或尾视图，适配器必须继承LXBaseAdapter，且不实现BaseAdapter的系统方法，只能去实现LXBaseAdapterInterface的方法。
 * */

public class LXTestEaseAdapter extends LXBaseAdapter {
	
	private Context context;
	 private List<String> infoList = new ArrayList<String>();
	private final int TYPE_ITEM = 0;
	private final int TYPE_HEADER_VIEW = 1;
	private final int TYPE_FOOTER_VIEW = 2;
	
	public LXTestEaseAdapter(Context context) {
		this.context = context;
		super.setSubClass(this);//这句代码必须调用。
	}

	/**
	 * 下面的方法必须实现
	 * *************************************************************************************/
	@Override
	public int getSectionInListView() {
		if (infoList == null) {
			return 0;
		}
		return infoList.size();
	}

	@Override
	public int getCountInSection(int section) {
		return 1;
	}

	@Override
	public View getItemView(LXIndexPath indexPath, View convertView,
			ViewGroup parent) {

		String indexPathStr = "section:" + indexPath.section + "  row:"
				+ indexPath.row + ".  ";

		LXInfoItemHolder holder = null;
		if (convertView == null) {
//			Log.e("createViewItem", "section:" + indexPath.section
//					+ "  row:" + indexPath.row);
			holder = new LXInfoItemHolder();
			convertView = createViewByType(TYPE_ITEM);
			holder.titleTextView = (TextView) convertView
					.findViewById(R.id.text_my_info_title);
			convertView.setTag(holder);
		} else {
			holder = (LXInfoItemHolder) convertView.getTag();
		}

		holder.titleTextView.setText(indexPathStr
				+ infoList.get(indexPath.section));
		
		return convertView;
	}

	/**
	 * 下面的方法可选实现
	 * *************************************************************************************/
	public boolean showHeaderViewInSection(int section) {
		return true;
	}

	public View getHeaderViewInSection(int section, View convertView,
			ViewGroup parent) {

		LXInfoHeaderHolder holder = null;
		if (convertView == null) {
			holder = new LXInfoHeaderHolder();
			convertView = createViewByType(TYPE_HEADER_VIEW);
			holder.headerTextView = (TextView) convertView
					.findViewById(R.id.text_headerView);
			convertView.setTag(holder);
		} else {
			holder = (LXInfoHeaderHolder) convertView.getTag();
		}

		holder.headerTextView.setText("第"+ section +"组的头视图");
		return convertView;
	}

	/*public boolean showFooterViewInSection(int section) {
		return true;
	}

	public View getFooterViewInSection(int section, View convertView,
			ViewGroup parent) {

		LXInfoFooterHolder holder = null;
		if (convertView == null) {
			holder = new LXInfoFooterHolder();
			convertView = createViewByType(TYPE_FOOTER_VIEW);
			holder.footerTextView = (TextView) convertView
					.findViewById(R.id.text_footerView);
			convertView.setTag(holder);
		} else {
			holder = (LXInfoFooterHolder) convertView.getTag();
		}

		holder.footerTextView.setText("第"+ section +"组的尾视图");
		return convertView;
	}*/
	
	public int getOnlyItemViewTypeCountSum() {
		return 1;
	}

	public int getOnlyItemViewType(LXIndexPath indexPath) {
		return TYPE_ITEM;
	}

	/**
	 * private method
	 * **************************************************************************************/
	private View createViewByType(int type) {
		switch (type) {
		case TYPE_ITEM:
			return LayoutInflater.from(context).inflate(
					R.layout.item_lx_my_info, null);
		case TYPE_HEADER_VIEW:
			return LayoutInflater.from(context).inflate(
					R.layout.item_lx_header, null);

		default:
			return LayoutInflater.from(context).inflate(
					R.layout.item_lx_footer, null);
		}
	}

	/**
	 * setter and getter
	 **************************************************************************************/
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public List<String> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<String> infoList) {
		this.infoList = infoList;
	}

	/**
	 * 下面是listview缓存不同布局的holder类
	 **************************************************************************************/
	public class LXInfoItemHolder {
		TextView titleTextView;
	}

	public class LXInfoItemHolder2 {
		TextView titleTextView2;
	}

	public class LXInfoHeaderHolder {
		TextView headerTextView;
	}

	public class LXInfoFooterHolder {
		TextView footerTextView;
	}
}
