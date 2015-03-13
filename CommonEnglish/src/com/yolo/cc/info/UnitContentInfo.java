package com.yolo.cc.info;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * 用来存储单元内要学习的内容和测试题目
 * 
 * @author yolo.cc
 * 
 */
@DatabaseTable(tableName = "unitContentInfo")
public class UnitContentInfo {

	@DatabaseField
	private String quizId;
	@DatabaseField
	private String unitId;
	@DatabaseField
	private String unitName;
	@DatabaseField
	private String quizSentence;
	@DatabaseField
	private String optionRight;
	@DatabaseField
	private String optionWrong1;
	@DatabaseField
	private String optionWrong2;
	@DatabaseField
	private String audio;

	public UnitContentInfo() {

	}

	public UnitContentInfo(String quizId, String unitId, String unitName,
			String quizSentence, String optionRight, String optionWrong1,
			String optionWrong2, String audio) {
		this.quizId = quizId;
		this.unitId = unitId;
		this.unitName = unitName;
		this.quizSentence = quizSentence;
		this.optionRight = optionRight;
		this.optionWrong1 = optionWrong1;
		this.optionWrong2 = optionWrong2;
		this.audio = audio;
	}

	public String getQuizId() {
		return quizId;
	}

	public void setQuizId(String quizId) {
		this.quizId = quizId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getQuizSentence() {
		return quizSentence;
	}

	public void setQuizSentence(String quizSentence) {
		this.quizSentence = quizSentence;
	}

	public String getOptionRight() {
		return optionRight;
	}

	public void setOptionRight(String optionRight) {
		this.optionRight = optionRight;
	}

	public String getOptionWrong1() {
		return optionWrong1;
	}

	public void setOptionWrong1(String optionWrong1) {
		this.optionWrong1 = optionWrong1;
	}

	public String getOptionWrong2() {
		return optionWrong2;
	}

	public void setOptionWrong2(String optionWrong2) {
		this.optionWrong2 = optionWrong2;
	}

	public String getAudio() {
		return audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}

}
