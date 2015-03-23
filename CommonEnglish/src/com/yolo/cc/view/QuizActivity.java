package com.yolo.cc.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class QuizActivity extends Activity implements OnClickListener {

	private TextView backToUnit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz);
		initView();
	}

	public void initView() {
		backToUnit = (TextView) findViewById(R.id.quiz_back);
		backToUnit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.quiz_back:
			finish();
			break;

		default:
			break;
		}
	}
}
