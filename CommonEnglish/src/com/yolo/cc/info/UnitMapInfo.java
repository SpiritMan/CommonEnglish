package com.yolo.cc.info;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "unitMapInfo")
public class UnitMapInfo {
	@DatabaseField
	private String status; // 单元状态
	@DatabaseField
	private String image; // 单元icon
	// 获得星星数
	@DatabaseField
	private int starCount; // 获得星星数
	// 总星星数
	@DatabaseField
	private int count; // 总星星数
	@DatabaseField
	private String unitName; // 单元名字
	@DatabaseField
	private String unitId; // 单元id

	public UnitMapInfo() {

	}

	public UnitMapInfo(String status, String image, int starCount, int count,
			String unitName, String unitId) {
		this.starCount = starCount;
		this.status = status;
		this.image = image;
		this.count = count;
		this.unitName = unitName;
		this.unitId = unitId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getStarCount() {
		return starCount;
	}

	public void setStarCount(int starCount) {
		this.starCount = starCount;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

}
