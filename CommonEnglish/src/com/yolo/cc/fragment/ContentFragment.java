package com.yolo.cc.fragment;

import com.yolo.cc.info.UnitContentInfo;
import com.yolo.cc.util.AudioPlayer;
import com.yolo.cc.view.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ContentFragment extends Fragment implements OnClickListener {

	private View view;
	// original 用来显示要学的英文句子， translate 用来显示英文句子的翻译
	private TextView original, translate;
	private UnitContentInfo unitContentInfo;
	private RelativeLayout mRelativeLayout;
	private AudioPlayer audioPlayer;
	private Context mContext;

	public ContentFragment(UnitContentInfo unitContentInfo, Context context) {
		this.unitContentInfo = unitContentInfo;
		this.mContext = context;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.myfragment, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		initView();
		super.onActivityCreated(savedInstanceState);
	}

	public void initView() {
		original = (TextView) view.findViewById(R.id.original);
		translate = (TextView) view.findViewById(R.id.translate);
		mRelativeLayout = (RelativeLayout) view.findViewById(R.id.layout);
		translate.setText(unitContentInfo.getOptionRight());
		original.setText(unitContentInfo.getQuizSentence());
		original.setOnClickListener(this);
		translate.setOnClickListener(this);
		mRelativeLayout.setOnClickListener(this);
		audioPlayer = new AudioPlayer();
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.original:
		case R.id.translate:
		case R.id.layout:
			if (audioPlayer.isPlaying()) {
				audioPlayer.stop();
				audioPlayer.playAudio(unitContentInfo.getAudio(), mContext);
			} else {
				System.out.println("auido:" + unitContentInfo.getAudio());
				audioPlayer.playAudio(unitContentInfo.getAudio(), mContext);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onPause() {
		if(audioPlayer.isPlaying()) {
			audioPlayer.stop();
		}
		super.onPause();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
