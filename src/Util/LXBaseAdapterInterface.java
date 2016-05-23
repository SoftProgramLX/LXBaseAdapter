package Util;

import android.view.View;
import android.view.ViewGroup;

public interface LXBaseAdapterInterface {
	
	/**
	 * 点击listView的触发方法onItemClick里调用的接口，区别点击的item、头或尾，与对应位置
	 * */
	public interface LXOnListViewClick {
		public void onItemClick(LXIndexPath idnexPath);
		public void onHeaderClick(int section);
		public void onfooterClick(int section);
	}
	
	//下面的所有解释都是对于继承LXBaseAdapterInterface的类的子类而言
	
	/**
	 * 子类必须实现的方法 
	 **/
	//返回listview有几组
	public int getSectionInListView();
	//返回第section组有几个item行，不包括组头与组尾
	public int getCountInSection(int section);
	//创建并返回在indexPath位置的item视图（不要摄入头尾）
	public View getItemView(LXIndexPath indexPath, View convertView, ViewGroup parent);
	
	
	/**
	 * 子类可选实现的方法 
	 **/
	/*
	 * 下面两个方法若要实现必须成对实现/
	 */
	//定义第section组是否需要显示头。若返回TRUE，则必须实现下面getHeaderViewInSection方法。
	public boolean showHeaderViewInSection(int section);
	//创建并返回第section组的头视图
	public View getHeaderViewInSection(int section, View convertView, ViewGroup parent);

	/*
	 * 下面两个方法若要实现必须成对实现/
	 */
	//定义第section组是否需要显示尾。若返回TRUE，则必须实现下面getFooterViewInSection方法。
	public boolean showFooterViewInSection(int section);
	//创建并返回第section组的尾视图
	public View getFooterViewInSection(int section, View convertView, ViewGroup parent);
	
	/*
	 * item需要实现多种布局的接口/
	 */
	//返回indexPath位置的item类型
	public int getOnlyItemViewType(LXIndexPath indexPath);
	//返回item一共有几种类型（不包含头与尾）
	public int getOnlyItemViewTypeCountSum();
	
	
	/**
	 * 子类不能复写的接口
	 * */
	//用于item被点击时传入position取到对应的indexPath（当row==-1代表为头视图，row==-2代表为尾视图）
	public LXIndexPath getIndexPathWithPosition(int position, LXOnListViewClick listener);
}



