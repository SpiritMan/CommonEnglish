package com.yolo.cc.view;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.j256.ormlite.dao.Dao;
import com.yolo.cc.info.UnitMapInfo;
import com.yolo.cc.util.ReadAndWriteUtil;
import com.yolo.cc.util.UnitMapDatabaseHelper;

/**
 * @author yolo.cc
 * 
 */
public class MainActivity extends Activity {
	private UnitMapDatabaseHelper unitMapDatabaseHelper;
	private Dao<UnitMapInfo, Integer> unitMapInfoDao;
	private List<UnitMapInfo> unitMapInfos;
	private ListView unitMapListView;
	private UnitMapListAdapter adapter;
	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 设置只调节媒体音量
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		initView();
	}

	/**
	 * 初始化数据
	 */
	@SuppressLint("InflateParams")
	public void initView() {
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.progress, null);
		dialog = new Dialog(this, R.style.AppTheme);
		dialog.setContentView(view);
		dialog.setCancelable(false);
		LoadByAsyncTask loadByAsyncTask = new LoadByAsyncTask();
		loadByAsyncTask.execute();
		if (unitMapDatabaseHelper == null) {
			unitMapDatabaseHelper = new UnitMapDatabaseHelper(this);
		}
		unitMapInfoDao = unitMapDatabaseHelper.getUnitMapInfoDao();

		unitMapListView = (ListView) findViewById(R.id.unit_map);

		unitMapListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {

				if ((position + 1) != unitMapInfos.size()) {
					if (((position + 1) % 7) != 0) {
						Intent intent = new Intent(MainActivity.this,
								StudayActivity.class);
						intent.putExtra("imageName", unitMapInfos.get(position)
								.getImage());
						intent.putExtra("unitName", unitMapInfos.get(position)
								.getUnitName());
						intent.putExtra("position",
								(position - ((position + 1) / 7)));
						startActivity(intent);
					}
				}
			}
		});

	}

	/**
	 * 异步加载map数据
	 * 
	 * @author yolo.cc
	 * 
	 */
	class LoadByAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			dialog.show();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {

			InputStream inputStream = getResources().openRawResource(
					R.raw.achievement);
			String mapDataString = ReadAndWriteUtil
					.inputstream2string(inputStream);
			JSONArray mapJsonArray = null;
			try {
				mapJsonArray = new JSONArray(mapDataString);
			} catch (JSONException e) {
				e.printStackTrace();
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			jsonParse(mapJsonArray);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			try {
				unitMapInfos = unitMapInfoDao.queryForAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			adapter = new UnitMapListAdapter(MainActivity.this, unitMapInfos);
			unitMapListView.setAdapter(adapter);
			dialog.dismiss();
			super.onPostExecute(result);
		}

	}

	public void jsonParse(JSONArray jsonArray) {
		int starCount = 0;
		try {
			if (unitMapInfoDao.queryForAll().size() == 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					UnitMapInfo unitMapInfo = new UnitMapInfo();
					unitMapInfo.setCount(jsonObject.getInt("count"));
					String status = jsonObject.getString("status");
					unitMapInfo.setStatus(status);
					String imageName = jsonObject.getString("image");
					// 用来找到图片的名字例如：icon_achi_introduct_.png
					// 取icon_achi_introduct_
					int imageNmaeEndIndex = imageName.indexOf(".");
					imageName = imageName.substring(0, imageNmaeEndIndex)
							+ status;
					System.out.println("imageName:" + imageName);
					unitMapInfo.setImage(imageName);
					starCount = starCount + jsonObject.getInt("star");
					unitMapInfo.setStarCount(jsonObject.getInt("star"));
					if (((i + 1) % 7) == 0) {
						unitMapInfo.setStarCount(starCount);
						starCount = 0;
					}
					unitMapInfo.setUnitName(jsonObject.getString("name"));
					unitMapInfoDao.create(unitMapInfo);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		unitMapDatabaseHelper.close();
		super.onDestroy();
	}
}
