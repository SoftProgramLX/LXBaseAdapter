package com.examplelx.lxbaseadapterdemo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class LXTestEaseActivity extends Activity implements OnItemClickListener {

	private ListView listView;
	private TextView titleTextView;
	private ArrayList<String> infoList = new ArrayList<String>();
	private LXTestEaseAdapter adapter;

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
	}

	private void initSetup() {

		adapter = new LXTestEaseAdapter(this);
		adapter.setInfoList(infoList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		titleTextView.setText("简单使用LXBaseAdapter");
	}

	@Override
	public void onItemClick(AdapterView<?> parantView, View itemView,
			int position, long id) {
		Intent intent = new Intent(this, LXTestDifficultActivity.class);
		startActivity(intent);
	}

	private void downLoadData() {

		// 组合listview的数据

		ArrayList<String> tempList = new ArrayList<String>();
		for (int i = 0; i < 50; i++) {
			tempList.add("text:" + i);
		}
		infoList.addAll(tempList);

	}
}
