package com.yolo.cc.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.yolo.cc.info.UnitContentInfo;
import com.yolo.cc.info.UnitMapInfo;
import com.yolo.cc.util.AudioPlayer;
import com.yolo.cc.util.CreateRandomUtil;
import com.yolo.cc.util.UnitContentDatabaseHelper;
import com.yolo.cc.util.UnitMapDatabaseHelper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QuizActivity extends Activity implements OnClickListener {

	private TextView backToUnit;
	private String unitId;
	private UnitContentInfo unitContentInfo;
	private Dao<UnitContentInfo, Integer> unitContentInfoDao;
	private UnitMapDatabaseHelper unitMapDatabaseHelper;
	private Dao<UnitMapInfo, Integer> unitMapInfoDao;
	private UnitContentDatabaseHelper unitContentDatabaseHelper;
	private List<UnitContentInfo> unitContentInfos;
	private TextView sentenceTextView, resultTitel, resultAchi;
	private Button optionOneBtn, optionTwoBtn, submitBtn;
	private RelativeLayout sentenceLayout;
	private int index = 0, select = 0, answer = 0, rightCount = 0,
			starCount = 0, position = 0;
	private AudioPlayer audioPlayer;
	private ProgressBar mProgressBar;
	private List<Integer> sortList;
	private String STATUS = "inspect";
	private Dialog dialog;
	private RatingBar starRatingBar;
	private String lockId = "en_lock_";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		initView();
	}

	public void initView() {
		if (unitContentDatabaseHelper == null) {
			unitContentDatabaseHelper = new UnitContentDatabaseHelper(this);
		}
		unitContentInfoDao = unitContentDatabaseHelper.getUnitContentInfoDao();

		if (unitMapDatabaseHelper == null) {
			unitMapDatabaseHelper = new UnitMapDatabaseHelper(this);
		}
		unitMapInfoDao = unitMapDatabaseHelper.getUnitMapInfoDao();
		unitId = getIntent().getStringExtra("unitId");
		position = getIntent().getIntExtra("position", 0);
		lockId = lockId + (position / 6);

		unitContentInfos = new ArrayList<UnitContentInfo>();
		audioPlayer = new AudioPlayer();
		LoadDataByAsyncTask loadDataByAsyncTask = new LoadDataByAsyncTask();
		loadDataByAsyncTask.execute();
		backToUnit = (TextView) findViewById(R.id.quiz_back);
		sentenceTextView = (TextView) findViewById(R.id.quiz_sentence);
		optionOneBtn = (Button) findViewById(R.id.quiz_option_one);
		optionTwoBtn = (Button) findViewById(R.id.quiz_option_two);
		sentenceLayout = (RelativeLayout) findViewById(R.id.quiz_sentenceLayout);
		submitBtn = (Button) findViewById(R.id.quiz_submit);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
		mProgressBar.setProgress(index + 1);

		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.custom_dialog_view, null);
		dialog = new Dialog(this, R.style.AppTheme);
		dialog.setCancelable(false);
		dialog.setContentView(view);
		Button nextButton = (Button) view.findViewById(R.id.result_next);
		nextButton.setOnClickListener(this);
		Button againButton = (Button) view.findViewById(R.id.result_again);
		againButton.setOnClickListener(this);
		starRatingBar = (RatingBar) view.findViewById(R.id.result_star);
		resultAchi = (TextView) view.findViewById(R.id.result_achi);
		resultTitel = (TextView) view.findViewById(R.id.result_titel);

		submitBtn.setOnClickListener(this);
		sentenceLayout.setOnClickListener(this);
		sentenceTextView.setOnClickListener(this);
		optionOneBtn.setOnClickListener(this);
		optionTwoBtn.setOnClickListener(this);
		backToUnit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.result_again:
			sortList.clear();
			sortList = CreateRandomUtil.getNumberList(unitContentInfos.size());
			index = 0;
			rightCount = 0;
			recoverDefaultSetting();
			dialog.dismiss();
			break;
		case R.id.result_next:
			Intent intent = new Intent(QuizActivity.this, MainActivity.class);
			startActivity(intent);
			break;
		case R.id.quiz_back:
			finish();
			break;
		case R.id.quiz_sentenceLayout:
		case R.id.quiz_sentence:
			if (audioPlayer.isPlaying()) {
				audioPlayer.stop();
			}
			audioPlayer
					.playAudio(unitContentInfo.getAudio(), QuizActivity.this);
			break;
		case R.id.quiz_option_one:
			select = 0;
			submitBtn.setEnabled(true);
			submitBtn.setBackgroundResource(R.drawable.custombts_green);
			optionTwoBtn.setBackgroundResource(R.drawable.btn_blue_up);
			optionOneBtn.setBackgroundResource(R.drawable.btn_blue_down);
			break;
		case R.id.quiz_option_two:
			select = 1;
			submitBtn.setEnabled(true);
			submitBtn.setBackgroundResource(R.drawable.custombts_green);
			optionOneBtn.setBackgroundResource(R.drawable.btn_blue_up);
			optionTwoBtn.setBackgroundResource(R.drawable.btn_blue_down);
			break;
		case R.id.quiz_submit:
			if (STATUS.equals("inspect")) {
				STATUS = "next";
				index = index + 1;
				System.out.println("answer:" + answer + "   " + "select:"
						+ select);
				submitBtn.setText(R.string.next);
				optionOneBtn.setEnabled(false);
				optionTwoBtn.setEnabled(false);
				if (select == answer) {
					rightCount = rightCount + 1;
					if (select == 0) {
						optionOneBtn
								.setBackgroundResource(R.drawable.btn_green_up);
					} else {
						optionTwoBtn
								.setBackgroundResource(R.drawable.btn_green_up);
					}
				} else {
					if (select == 0) {
						optionOneBtn
								.setBackgroundResource(R.drawable.btn_red_up);
						optionTwoBtn
								.setBackgroundResource(R.drawable.btn_green_up);
					} else {
						optionTwoBtn
								.setBackgroundResource(R.drawable.btn_red_up);
						optionOneBtn
								.setBackgroundResource(R.drawable.btn_green_up);
					}
				}
			} else {
				if (unitContentInfos.size() > index) {
					recoverDefaultSetting();
				} else {
					dialog.show();
					resultAchi.setText("本次成绩:" + (rightCount * 150));
					if (rightCount >= 9) {
						starCount = 3;
						resultTitel.setText(R.string.congratulations_);
						starRatingBar.setNumStars(3);
						starRatingBar.setRating(3);
					} else if (rightCount >= 6) {
						starCount = 2;
						resultTitel.setText(R.string.come_on_);
						starRatingBar.setNumStars(2);
						starRatingBar.setRating(2);
					} else {
						starCount = 1;
						resultTitel.setText(R.string.come_on_);
						starRatingBar.setNumStars(1);
						starRatingBar.setRating(1);
					}
					UnitMapInfo unitMapInfo = new UnitMapInfo();
					try {
						unitMapInfo = unitMapInfoDao.queryBuilder().where()
								.eq("unitId", unitId).query().get(0);
						if (unitMapInfo.getStarCount() < starCount) {
							int beforStarCount = unitMapInfo.getStarCount();
							UpdateBuilder<UnitMapInfo, Integer> updateBuilder = unitMapInfoDao
									.updateBuilder();
							updateBuilder.where().eq("unitId", unitId);
							updateBuilder.updateColumnValue("starCount",
									starCount);
							updateBuilder.updateColumnValue("status", "finish");
							updateBuilder.update();

							unitMapInfo = unitMapInfoDao.queryBuilder().where()
									.eq("unitId", lockId).query().get(0);

							UpdateBuilder<UnitMapInfo, Integer> lockBuilder = unitMapInfoDao
									.updateBuilder();
							lockBuilder.where().eq("unitId", lockId);
							lockBuilder
									.updateColumnValue(
											"starCount",
											(unitMapInfo.getStarCount()
													+ starCount - beforStarCount));
							if (((unitMapInfo.getStarCount() + starCount - beforStarCount) > 0)
									&& (unitMapInfo.getStarCount() + starCount - beforStarCount) < unitMapInfo
											.getCount()) {
								lockBuilder
										.updateColumnValue("status", "green");
							} else if ((unitMapInfo.getStarCount() + starCount - beforStarCount) == unitMapInfo
									.getCount()) {
								lockBuilder
										.updateColumnValue("status", "finish");
							}
							lockBuilder.update();

							unitMapInfo = unitMapInfoDao.queryBuilder().where()
									.eq("unitId", "en_sl_" + (position + 2))
									.query().get(0);
							if (((unitMapInfo.getStarCount() + starCount) <= unitMapInfo
									.getCount()) && ((position % 6) == 5)) {

							} else if (unitMapInfo.getStatus().equals("gray")) {
								UpdateBuilder<UnitMapInfo, Integer> builder = unitMapInfoDao
										.updateBuilder();
								builder.where().eq("unitId",
										"en_sl_" + (position + 2));
								builder.updateColumnValue("status", "green");
								builder.update();
							}
						}

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * 异步加载题库
	 * 
	 * @author yolo.cc
	 * 
	 */
	class LoadDataByAsyncTask extends
			AsyncTask<Void, Void, List<UnitContentInfo>> {
		@Override
		protected List<UnitContentInfo> doInBackground(Void... params) {
			try {
				return unitContentInfoDao.queryBuilder().where()
						.eq("unitId", unitId).query();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(List<UnitContentInfo> result) {
			if (result != null) {
				unitContentInfos = result;
				sortList = new ArrayList<Integer>();
				System.out.println("size: " + unitContentInfos.size());
				sortList = CreateRandomUtil.getNumberList(unitContentInfos
						.size());
				setQuestion(unitContentInfos.get(sortList.get(index)));
			}
			super.onPostExecute(result);
		}
	}

	/**
	 * 布置题目
	 * 
	 * @param unitContentInfo
	 */
	public void setQuestion(UnitContentInfo unitContentInfo) {
		this.unitContentInfo = unitContentInfo;
		sentenceTextView.setText(unitContentInfo.getQuizSentence());
		audioPlayer.playAudio(unitContentInfo.getAudio(), QuizActivity.this);
		answer = new Random().nextInt(2);
		if (answer == 0) {
			optionOneBtn.setText(unitContentInfo.getOptionRight());
			optionTwoBtn.setText(unitContentInfo.getOptionWrong1());
		} else {
			optionOneBtn.setText(unitContentInfo.getOptionWrong2());
			optionTwoBtn.setText(unitContentInfo.getOptionRight());
		}
	}

	/**
	 * 恢复布局的默认属性，并准备下一题
	 */
	public void recoverDefaultSetting() {
		if(audioPlayer.isPlaying()) {
			audioPlayer.stop();
		}
		select = 0;
		optionOneBtn.setEnabled(true);
		optionOneBtn.setBackgroundResource(R.drawable.btn_blue_up);
		optionTwoBtn.setEnabled(true);
		optionTwoBtn.setBackgroundResource(R.drawable.btn_blue_up);

		submitBtn.setEnabled(false);
		submitBtn.setBackgroundResource(R.drawable.btn_disabled);
		STATUS = "inspect";
		setQuestion(unitContentInfos.get(sortList.get(index)));
		mProgressBar.setProgress(index + 1);
	}
}
