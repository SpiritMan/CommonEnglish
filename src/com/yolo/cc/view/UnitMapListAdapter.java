package com.yolo.cc.view;

import java.util.List;

import com.yolo.cc.info.UnitMapInfo;
import com.yolo.cc.util.ResourceIdUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UnitMapListAdapter extends BaseAdapter {

	private List<UnitMapInfo> list;
	private LayoutInflater inflater;
	private UnitMapInfo unit, preUnit;
	private int starCount = 0;

	public UnitMapListAdapter(Context context, List<UnitMapInfo> list) {
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		TopItem topItem = null;
		BelowItem belowItem = null;
		LeftItem leftItem = null;
		RightItem rightItem = null;
		CenterItem centerItem = null;
		unit = list.get(position);
		if (position != 0) {
			preUnit = list.get((position - 1));
		}
		// if (convertView == null) {
		if (position == 0) {
			convertView = inflater.inflate(R.layout.unitmaplist_item_top,
					parent, false);
			topItem = new TopItem();
			topItem.topStarLayout = (LinearLayout) convertView
					.findViewById(R.id.item_top_starlayout);
			topItem.topUnitImage = (ImageView) convertView
					.findViewById(R.id.item_top_map_image);
			topItem.topUnitImage.setImageResource(ResourceIdUtil
					.getImageResourceId(unit.getImage()));
			starCount = starCount + unit.getStarCount();
			setStarImage(unit, topItem.topStarLayout);

			// convertView.setTag(topItem);
		} else if (position == (list.size() - 1)) {
			convertView = inflater.inflate(R.layout.unitmaplist_item_below,
					parent, false);
			belowItem = new BelowItem();
			belowItem.belowStarLayout = (LinearLayout) convertView
					.findViewById(R.id.item_below_starlayout);
			belowItem.belowUnitImage = (ImageView) convertView
					.findViewById(R.id.item_below_map_image);
			belowItem.belowUnitImage.setImageResource(ResourceIdUtil
					.getImageResourceId(unit.getImage()));
			setStarImage(preUnit, belowItem.belowStarLayout);
			starCount = starCount + preUnit.getStarCount();
			// convertView.setTag(belowItem);
		} else if (((position + 1) % 7) == 0) {
			starCount = starCount + preUnit.getStarCount();
			convertView = inflater.inflate(R.layout.unitmaplist_item_center,
					parent, false);
			centerItem = new CenterItem();
			centerItem.centerLockImage = (ImageView) convertView
					.findViewById(R.id.item_center_lock);
			centerItem.centerStarCount = (TextView) convertView
					.findViewById(R.id.item_center_starcount);
			centerItem.centerStarImage = (ImageView) convertView
					.findViewById(R.id.item_center_staricon);
			centerItem.centerStarLayout = (LinearLayout) convertView
					.findViewById(R.id.item_center_starlayout);
			centerItem.centerStarCount.setText(unit.getStarCount() + "/"
					+ unit.getCount());
			System.out.println("preUnit status:" + preUnit.getStatus());
			setStarImage(preUnit, centerItem.centerStarLayout);
			if (starCount != 0) {
				centerItem.centerStarImage
						.setImageResource(R.drawable.icon_achi_star_green);
			} else if (starCount == unit.getCount()) {
				centerItem.centerStarImage
						.setImageResource(R.drawable.icon_achi_star_finish);
			}
			starCount = 0;
			// convertView.setTag(centerItem);
		} else if (((position % 7) % 2) == 0) {
			convertView = inflater.inflate(R.layout.unitmaplist_item_right,
					parent, false);
			rightItem = new RightItem();
			rightItem.rightStarLayout = (LinearLayout) convertView
					.findViewById(R.id.item_right_starlayout);
			rightItem.rightUnitImage = (ImageView) convertView
					.findViewById(R.id.item_right_map_image);
			if ((position % 7) == 0) {
				rightItem.rightStarLayout.setVisibility(View.INVISIBLE);
			}
			starCount = starCount + preUnit.getStarCount();
			rightItem.rightUnitImage.setImageResource(ResourceIdUtil
					.getImageResourceId(unit.getImage()));
			setStarImage(preUnit, rightItem.rightStarLayout);
			// convertView.setTag(rightItem);
		} else {
			convertView = inflater.inflate(R.layout.unitmaplist_item_left,
					parent, false);
			leftItem = new LeftItem();
			leftItem.leftStarLayout = (LinearLayout) convertView
					.findViewById(R.id.item_left_starlayout);
			leftItem.leftUnitImage = (ImageView) convertView
					.findViewById(R.id.item_left_map_image);
			leftItem.leftUnitImage.setImageResource(ResourceIdUtil
					.getImageResourceId(unit.getImage()));
			if (position == 1) {
				leftItem.leftStarLayout.setVisibility(View.INVISIBLE);
			} else {
				setStarImage(preUnit, leftItem.leftStarLayout);
				starCount = starCount + preUnit.getStarCount();
			}
			// convertView.setTag(leftItem);
		}

		return convertView;
	}

	private class TopItem {
		LinearLayout topStarLayout;
		ImageView topUnitImage;
	}

	private class LeftItem {
		LinearLayout leftStarLayout;
		ImageView leftUnitImage;
	}

	private class RightItem {
		LinearLayout rightStarLayout;
		ImageView rightUnitImage;
	}

	private class BelowItem {
		LinearLayout belowStarLayout;
		ImageView belowUnitImage;
	}

	private class CenterItem {
		ImageView centerLockImage;
		TextView centerStarCount;
		ImageView centerStarImage;
		LinearLayout centerStarLayout;
	}

	/**
	 * 设置每个单元星星的icon
	 * 
	 * @param unitMapInfo
	 * @param linearLayout
	 */
	private void setStarImage(UnitMapInfo unitMapInfo, LinearLayout linearLayout) {
		if (unitMapInfo.getStarCount() <= 3) {
			for (int i = 0; i < unitMapInfo.getStarCount(); i++) {
				ImageView imageView = (ImageView) linearLayout.getChildAt(i);
				if (unitMapInfo.getStatus().equals("finish")) {
					imageView
							.setImageResource(R.drawable.icon_achi_star_finish);
				} else {
					imageView.setImageResource(R.drawable.icon_achi_star_green);
				}
			}
		}
	}
}
