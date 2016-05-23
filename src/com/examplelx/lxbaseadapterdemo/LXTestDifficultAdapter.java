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

public class LXTestDifficultAdapter extends LXBaseAdapter {
	
	private Context context;
	private List<ArrayList<String>> infoList = new ArrayList<ArrayList<String>>();
	// private List<String> infoList = new ArrayList<String>();
	private final int TYPE_ITEM1 = 0;
	private final int TYPE_ITEM2 = 1;
	private final int TYPE_HEADER_VIEW = 3;
	private final int TYPE_FOOTER_VIEW = 4;

	public LXTestDifficultAdapter(Context context) {
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
		ArrayList<String> arr = infoList.get(section);
		return arr.size();
	}

	@Override
	public View getItemView(LXIndexPath indexPath, View convertView,
			ViewGroup parent) {

		String indexPathStr = "section:" + indexPath.section + "  row:"
				+ indexPath.row + ".  ";
		ArrayList<String> itemArr = infoList.get(indexPath.section);
		
		int type = getOnlyItemViewType(indexPath);
		switch (type) {
		case TYPE_ITEM1: {
			LXInfoItemHolder holder = null;
			if (convertView == null) {
//				Log.e("createViewItem", "section:" + indexPath.section
//						+ "  row:" + indexPath.row);
				holder = new LXInfoItemHolder();
				convertView = createViewByType(TYPE_ITEM1);
				holder.titleTextView = (TextView) convertView
						.findViewById(R.id.text_my_info_title);
				convertView.setTag(holder);
			} else {
				holder = (LXInfoItemHolder) convertView.getTag();
			}

			holder.titleTextView.setText(indexPathStr
					+ itemArr.get(indexPath.row));
		}
			break;

		default: {
			LXInfoItemHolder2 holder2 = null;
			if (convertView == null) {
				holder2 = new LXInfoItemHolder2();
				convertView = createViewByType(TYPE_ITEM2);
				holder2.titleTextView2 = (TextView) convertView
						.findViewById(R.id.text_my_info_title2);
				convertView.setTag(holder2);
			} else {
				holder2 = (LXInfoItemHolder2) convertView.getTag();
			}
			
			holder2.titleTextView2.setText(indexPathStr
					+ itemArr.get(indexPath.row));
		}
			break;
		}

		return convertView;
	}

	/**
	 * 下面的方法可选实现
	 * *************************************************************************************/
	public boolean showHeaderViewInSection(int section) {
		if (section != 2) {
			return true;
		}
		return false;
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

	public boolean showFooterViewInSection(int section) {
		if (section != 3) {
			return true;
		}
		return false;
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
	}

	public int getOnlyItemViewType(LXIndexPath indexPath) {
		if (indexPath.row != 1) {
			return TYPE_ITEM1;
		}
		return TYPE_ITEM2;
	}

	public int getOnlyItemViewTypeCountSum() {
		return 2;
	}

	/**
	 * private method
	 * **************************************************************************************/
	private View createViewByType(int type) {
		switch (type) {
		case TYPE_ITEM1:
			return LayoutInflater.from(context).inflate(
					R.layout.item_lx_my_info, null);
		case TYPE_ITEM2:
			return LayoutInflater.from(context).inflate(
					R.layout.item_lx_my_info2, null);
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

	public List<ArrayList<String>> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<ArrayList<String>> infoList) {
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
