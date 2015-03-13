package com.yolo.cc.util;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.yolo.cc.info.UnitContentInfo;

public class UnitContentDatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "unitContentInfo.db";
	private static int DATABASE_VERSION = 1;
	private Dao<UnitContentInfo, Integer> unitContentDao = null;

	public UnitContentDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase,
			ConnectionSource connectionSource) {
		try {
			TableUtils.createTableIfNotExists(getConnectionSource(),
					UnitContentInfo.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase,
			ConnectionSource connectionSource, int arg2, int arg3) {
		try {
			TableUtils.dropTable(connectionSource, UnitContentInfo.class, true);
			onCreate(sqLiteDatabase, connectionSource);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Dao<UnitContentInfo, Integer> getUnitContentInfoDao() {
		if (unitContentDao == null) {
			try {
				unitContentDao = getDao(UnitContentInfo.class);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return unitContentDao;
	}

	public void close() {
		super.close();
		unitContentDao = null;
	}
}
