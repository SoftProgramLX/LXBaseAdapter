# LXBaseAdapter
为了灵活使用listView，封装了BaseAdapter，可对listView进行分组管理，每组都可以设置头与尾，每组的多行可以定义多种布局文件，实现了类似iOS的tableView功能。（The listview group management to flexibly using the listview encapsulates the BaseAdapter. In each group can be set up to the head and tail, each multi line can define multiple layout file, to achieve the tableview function similar to the IOS.）

[效果图再最后](#效果图)

API使用非常简单，与BaseAdapter的接口类似，按照例子的方法写很容易实现，demo有两个界面，从简单实现到灵活运用。
只需要根据自己的业务创建一个Adapter继承自LXBaseAdapter，然后实现的方法查看LXBaseAdapterInterface接口选择使用。
接口方法如下:<br>
```java
  	/**
	 * 点击listView的触发方法onItemClick里调用的接口，区别点击的item、头或尾，与对应位置
	 **/
	public interface LXOnListViewClick {
		public void onItemClick(LXIndexPath idnexPath);
		public void onHeaderClick(int section);
		public void onfooterClick(int section);
	}

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
```



特别注意，在Activity里面监听了listView的点击事件onItemClick方法，我们需要adapter调用getIndexPathWithPosition方法才会知道点的是item或头或尾的对应位置，然后再处理相应的业务。代码如下：<br>
```java
  adapter.getIndexPathWithPosition(position, new LXOnListViewClick() {
	@Override
	public void onItemClick(LXIndexPath indexPath) {
		Log.e("onItemClick", "indexPath:" + indexPath.section+":"+indexPath.row);
	}
	
	@Override
	public void onHeaderClick(int section) {
		Log.e("onHeaderClick", "section:"+section);
	}
			
	@Override
	public void onfooterClick(int section) {
		Log.e("onfooterClick", "section:"+section);
	}
});
```


效果图
 ![image](https://github.com/SoftProgramLX/LXBaseAdapter/blob/master/0C5CADD9B0F72C4A98C73866C4EABA34.png)
