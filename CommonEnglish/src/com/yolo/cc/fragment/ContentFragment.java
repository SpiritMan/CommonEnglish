package com.yolo.cc.fragment;

import com.yolo.cc.info.UnitContentInfo;
import com.yolo.cc.view.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ContentFragment extends Fragment implements OnClickListener {

	private View view;
	// original 用来显示要学的英文句子， translate 用来显示英文句子的翻译
	private TextView original, translate;
	private UnitContentInfo unitContentInfo;
	private RelativeLayout mRelativeLayout;

	public ContentFragment(UnitContentInfo unitContentInfo) {
		this.unitContentInfo = unitContentInfo;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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
		mRelativeLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.original:
//			Toast.makeText(getActivity(), original.getText(),
//					Toast.LENGTH_SHORT).show();
			break;
		case R.id.layout:
			Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

}
