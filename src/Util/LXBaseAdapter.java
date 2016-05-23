package Util;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class LXBaseAdapter extends BaseAdapter implements
		LXBaseAdapterInterface {

	private LXBaseAdapter subClass;

	// recodeIndexs的子元素是含有三个元素的数组，第一个元素标记position，第二个元素>0表示为item视图，TYPE_HEADER表示为头，为TYPE_FOOTER表示尾。
	private List<int[]> recodeIndexs = new ArrayList<int[]>();
	private final int TYPE_HEADER = -1;
	private final int TYPE_FOOTER = -2;

	@Override
	public int getCount() {

		/*
		 * 设计思路：
		 * 
		 * 统计返回的视图的总个数。
		 * 将每组里面的所有行元素顺序加起来就是position值。
		 * 如果有头试图或尾视图，那么position将增加相应的值额外用来展现它们。 在此函数返回所有item个数与头试图和尾视图的总和。
		 * 需要记录在哪个position位置展现item或头或尾，因此创建了一个三维数组recodeIndex[][]用于记录。
		 * recodeIndex[][0]代表特殊位置的position。
		 * recodeIndex[][1]代表（item、头或尾）视图所在的组位置
		 * recodeIndex[][2]>0表示为item视图，TYPE_HEADER表示为头，为TYPE_FOOTER表示尾。
		 * 
		 * */

		int section = subClass.getSectionInListView();
		int count = 0;
		recodeIndexs.clear();
		for (int i = 0; i < section; i++) {

			//判断有无组头，若有则记录位置与类型
			if (getHeaderBool(i) == 1) {
				int[] headerIndex = new int[3];
				headerIndex[0] = count;
				headerIndex[1] = TYPE_HEADER;
				headerIndex[2] = i;
				count++;
				recodeIndexs.add(headerIndex);
			}
			
			//记录组的行成员
			for (int j = 0; j < subClass.getCountInSection(i); j++) {
				int[] index = new int[3];
				index[0] = count+j;
				index[1] = j;
				index[2] = i;
				recodeIndexs.add(index);
			}
			count += subClass.getCountInSection(i);

			//判断有无组尾，若有则记录位置与类型
			if (getfooterBool(i) == 1) {
				int[] footerIndex = new int[3];
				footerIndex[0] = count;
				footerIndex[1] = TYPE_FOOTER;
				footerIndex[2] = i;
				recodeIndexs.add(footerIndex);
				count++;
			}
		}

		for (int i = 0; i < recodeIndexs.size(); i++) {
			Log.e("recodeIndex", recodeIndexs.get(i)[0] + "."+recodeIndexs.get(i)[1] + "."+recodeIndexs.get(i)[2]);
		}
		return count;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		switch (getItemViewType(position)) {
		case TYPE_HEADER:
			return subClass.getHeaderViewInSection(recodeIndexs.get(position)[2],
					convertView, parent);

		case TYPE_FOOTER:
			return subClass.getFooterViewInSection(recodeIndexs.get(position)[2],
					convertView, parent);

		//根据子类定义的类型种类取得不同布局的item
		default:
			LXIndexPath itemIndexPath = new LXIndexPath();
			itemIndexPath.row = recodeIndexs.get(position)[1];
			itemIndexPath.section = recodeIndexs.get(position)[2];
			return subClass.getItemView(itemIndexPath, convertView, parent);
		}
	}

	@Override
	public int getViewTypeCount() {
		int itemTypeCount = subClass.getOnlyItemViewTypeCountSum();
		return 2 + (itemTypeCount > 0 ? itemTypeCount : 1);
	}

	@Override
	public int getItemViewType(int position) {
		LXIndexPath itemIndexPath = new LXIndexPath();
		itemIndexPath.row = recodeIndexs.get(position)[1];
		itemIndexPath.section = recodeIndexs.get(position)[2];
		
		int type = subClass.getOnlyItemViewType(itemIndexPath);
		if (type > 0) {
			//代表item有多种布局
			return type;
		}
		
		if (itemIndexPath.row >= 0) {
			//代表item只有一种布局
			return 0;
		}
		
		//代表返回头或尾的布局
		return itemIndexPath.row;
	}
	
	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int id) {
		return id;
	}


	/**
	 * setter and getter
	 */
	private int getHeaderBool(int section) {
		return (subClass.showHeaderViewInSection(section) ? 1 : 0);
	}

	private int getfooterBool(int section) {
		return (subClass.showFooterViewInSection(section) ? 1 : 0);
	}

	public LXBaseAdapter getSubClass() {
		return subClass;
	}

	public void setSubClass(LXBaseAdapter subClass) {
		this.subClass = subClass;
	}

	/**
	 * 下面实现了接口的部分方法使子类可选实现
	 * */
	@Override
	public boolean showHeaderViewInSection(int section) {
		return false;
	}

	@Override
	public View getHeaderViewInSection(int section, View convertView,
			ViewGroup parent) {
		return null;
	}

	@Override
	public boolean showFooterViewInSection(int section) {
		return false;
	}

	@Override
	public View getFooterViewInSection(int section, View convertView,
			ViewGroup parent) {
		return null;
	}

	@Override
	public int getOnlyItemViewType(LXIndexPath indexPath) {
		return 0;
	}

	@Override
	public int getOnlyItemViewTypeCountSum() {
		return 1;
	}

	/**
	 * 子类不能实现的接口方法
	 * */
	@Override
	public LXIndexPath getIndexPathWithPosition(int position, LXOnListViewClick listener) {
		//转换position为indexPath
		LXIndexPath posiIndexPath = new LXIndexPath();
		posiIndexPath.row = recodeIndexs.get(position)[1];
		posiIndexPath.section = recodeIndexs.get(position)[2];
		
		//根据类型调用相应的点击事件
		if (posiIndexPath.row == TYPE_HEADER) {
			listener.onHeaderClick(posiIndexPath.section);
		} else if (posiIndexPath.row == TYPE_FOOTER) {
			listener.onfooterClick(posiIndexPath.section);
		} else {
			listener.onItemClick(posiIndexPath);
		}
		
		return posiIndexPath;
	}
}
