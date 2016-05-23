package com.examplelx.lxbaseadapterdemo;

import java.util.ArrayList;

import Util.LXBaseAdapterInterface.LXOnListViewClick;
import Util.LXIndexPath;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LXTestDifficultActivity extends Activity implements OnItemClickListener, OnClickListener {

	private ListView listView;
	private TextView titleTextView;
	private Button backButton;
	private ArrayList<ArrayList<String>> infoList = new ArrayList<ArrayList<String>>(); 
	private LXTestDifficultAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);

		initViews();
		initSetup();
		downLoadData();
	}
	
	private void initViews() {
		listView = (ListView) findViewById(R.id.lx_list_info);
		titleTextView = (TextView) findViewById(R.id.my_activity_title);
		backButton = (Button) findViewById(R.id.btn_go_back);
		backButton.setVisibility(View.VISIBLE);
	}
	
	private void initSetup() {
		adapter = new LXTestDifficultAdapter(this);
		adapter.setInfoList(infoList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		backButton.setOnClickListener(this);
		titleTextView.setText("灵活使用LXBaseAdapter");
	}

	@Override
	public void onItemClick(AdapterView<?> parantView, View itemView, int position, long id) {
		
		//需要adapter调用getIndexPathWithPosition方法才会知道点的是item或头或尾的对应位置
		adapter.getIndexPathWithPosition(position, new LXOnListViewClick() {
			
			@Override
			public void onItemClick(LXIndexPath indexPath) {
				Log.e("onItemClick", "indexPath:" + indexPath.section+":"+indexPath.row);
				Toast.makeText(LXTestDifficultActivity.this, "section："+ indexPath.section +"   row："+indexPath.row, Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onHeaderClick(int section) {
				Log.e("onHeaderClick", "section:"+section);
				Toast.makeText(LXTestDifficultActivity.this, "可选择显示自定义头视图，section："+ section, Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onfooterClick(int section) {
				Log.e("onfooterClick", "section:"+section);
				Toast.makeText(LXTestDifficultActivity.this, "可选择显示自定义尾视图，section："+ section, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void downLoadData() {
		
		//组合listview所需二维数据
		ArrayList<String> itemArrayList0 = new ArrayList<String>();
		itemArrayList0.add("0");
		infoList.add(itemArrayList0);
		
		ArrayList<String> itemArrayList1 = new ArrayList<String>();
		itemArrayList1.add("1");
		infoList.add(itemArrayList1);
		
		ArrayList<String> itemArrayList2 = new ArrayList<String>();
		itemArrayList2.add("2");
		itemArrayList2.add("22");
		infoList.add(itemArrayList2);

		ArrayList<String> itemArrayList3 = new ArrayList<String>();
		itemArrayList3.add("3");
		itemArrayList3.add("33");
		itemArrayList3.add("333");
		infoList.add(itemArrayList3);

		ArrayList<String> itemArrayList4 = new ArrayList<String>();
		itemArrayList4.add("4");
		itemArrayList4.add("44");
		itemArrayList4.add("444");
		itemArrayList4.add("4444");
		infoList.add(itemArrayList4);

		ArrayList<String> itemArrayList5 = new ArrayList<String>();
		itemArrayList5.add("5");
		itemArrayList5.add("55");
		itemArrayList5.add("555");
		itemArrayList5.add("5555");
		itemArrayList5.add("55555");
		infoList.add(itemArrayList5);
		
		new Handler().postDelayed(new Runnable() {
			//模拟网络请求加载更多数据
			@Override
			public void run() {
				
				ArrayList<String> itemArrayList6 = new ArrayList<String>();
				itemArrayList6.add("6");
				itemArrayList6.add("66");
				itemArrayList6.add("666");
				itemArrayList6.add("6666");
				itemArrayList6.add("66666");
				itemArrayList6.add("666666");
				infoList.add(itemArrayList6);
				adapter.notifyDataSetChanged();
			}
		}, 3000);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_go_back:
			finish();
			break;

		default:
			break;
		}
	}
}


