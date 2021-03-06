package com.yolo.cc.view;

import java.util.List;

import com.yolo.cc.fragment.ContentFragment;
import com.yolo.cc.info.UnitContentInfo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ContentFragmentAdapter extends FragmentPagerAdapter {
	protected static final String[] CONTENT = new String[] { "This", "Is", "A",
			"Test" };
	private int mCount = 10;
	private Context context;
	private List<UnitContentInfo> unitContentInfos;

	public ContentFragmentAdapter(FragmentManager fm, Context context) {
		super(fm);

		this.context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int position) {
		System.out.println("position:"+position);
		ContentFragment contentFragment = new ContentFragment(
				unitContentInfos.get(position), context);
		return contentFragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCount;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return ContentFragmentAdapter.CONTENT[position % CONTENT.length];
	}

	public void setUnitContentList(List<UnitContentInfo> unitContentInfos) {
		this.unitContentInfos = unitContentInfos;
	}
}
