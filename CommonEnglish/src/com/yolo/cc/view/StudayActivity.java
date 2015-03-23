package com.yolo.cc.view;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.j256.ormlite.dao.Dao;
import com.yolo.cc.info.UnitContentInfo;
import com.yolo.cc.util.ReadAndWriteUtil;
import com.yolo.cc.util.ResourceIdUtil;
import com.yolo.cc.util.UnitContentDatabaseHelper;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StudayActivity extends FragmentActivity implements OnClickListener{
	private ViewPager pager;
	private ContentFragmentAdapter adapter;
	private LinearLayout progressLayout;
	private LinearLayout.LayoutParams lp;
	private ImageView unitIcon;
	private TextView unitNameTextView,quiz;
	private String unitId = "en_sl_";
	private List<UnitContentInfo> unitContentInfos;
	private Dao<UnitContentInfo, Integer> unitContentInfoDao;
	private UnitContentDatabaseHelper unitContentDatabaseHelper;
	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.study);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		if (unitContentDatabaseHelper == null) {
			unitContentDatabaseHelper = new UnitContentDatabaseHelper(this);
		}
		unitContentInfoDao = unitContentDatabaseHelper.getUnitContentInfoDao();

		initView();
	}

	@SuppressLint("InflateParams")
	public void initView() {
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.progress, null);
		dialog = new Dialog(this, R.style.AppTheme);
		dialog.setContentView(view);
		dialog.setCancelable(false);

		quiz = (TextView) findViewById(R.id.unit_quiz);
		quiz.setOnClickListener(this);
		Intent intent = getIntent();
		String imageName = intent.getStringExtra("imageName");
		unitIcon = (ImageView) findViewById(R.id.unit_icon);
		unitIcon.setImageResource(ResourceIdUtil.getImageResourceId(imageName));
		unitNameTextView = (TextView) findViewById(R.id.unit_name);
		unitNameTextView.setText(intent.getStringExtra("unitName"));
		unitId = unitId + (intent.getIntExtra("position", 0) + 1);

		LoadDataByAsyncTask loadDataByAsyncTask = new LoadDataByAsyncTask();
		loadDataByAsyncTask.execute();

		System.out.println("unitId:" + unitId);

		progressLayout = (LinearLayout) findViewById(R.id.progress);
		lp = new LinearLayout.LayoutParams(calculateDpToPx(8),
				calculateDpToPx(8), 1);

		pager = (ViewPager) findViewById(R.id.pager);
		// adapter = new ContentFragmentAdapter(getSupportFragmentManager());
		// pager.setAdapter(adapter);
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				for (int i = 0; i < adapter.getCount(); i++) {
					ImageView imageView = (ImageView) progressLayout
							.getChildAt(i);
					if (i == position) {
						imageView.setImageResource(R.drawable.round_down);
					} else {
						imageView.setImageResource(R.drawable.round_up);
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

	}

	/**
	 * 把dp转换为px<br>
	 * 
	 * @param tv
	 * @return
	 */
	private int calculateDpToPx(int padding_in_dp) {
		final float scale = this.getResources().getDisplayMetrics().density;
		return (int) (padding_in_dp * scale + 0.5f);
	}

	class LoadDataByAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			dialog.show();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			InputStream inputStream = getResources().openRawResource(
					R.raw.quiz_config);
			String unitContentString = ReadAndWriteUtil
					.inputstream2string(inputStream);
			JSONArray jsonArray = null;
			try {
				jsonArray = new JSONArray(unitContentString);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonParse(jsonArray);
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			try {
				unitContentInfos = unitContentInfoDao.queryBuilder().where()
						.eq("unitId", unitId).query();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("unitContentInfos size:"
					+ unitContentInfos.size());
			adapter = new ContentFragmentAdapter(getSupportFragmentManager(),
					StudayActivity.this);
			adapter.setUnitContentList(unitContentInfos);
			pager.setAdapter(adapter);

			for (int i = 0; i < adapter.getCount(); i++) {
				ImageView imageView = new ImageView(StudayActivity.this);
				if (i == 0) {
					imageView.setImageResource(R.drawable.round_down);
				} else {
					imageView.setImageResource(R.drawable.round_up);
				}
				lp.setMargins(calculateDpToPx(3), 0, 0, 0);
				imageView.setLayoutParams(lp);
				progressLayout.addView(imageView);
			}
			dialog.dismiss();
			super.onPostExecute(result);
		}
	}

	public void jsonParse(JSONArray jsonArray) {
		try {
			if (unitContentInfoDao.queryForAll().size() == 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					UnitContentInfo unitContentInfo = new UnitContentInfo(
							jsonObject.getString("quiz_id"),
							jsonObject.getString("unit_id"),
							jsonObject.getString("unit_name"),
							jsonObject.getString("quiz_sentence"),
							jsonObject.getString("option_right"),
							jsonObject.getString("option_wrong1"),
							jsonObject.getString("option_wrong2"),
							jsonObject.getString("audio"));
					unitContentInfoDao.create(unitContentInfo);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.unit_quiz:
			Toast.makeText(StudayActivity.this, "quiz 入口", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}
}
