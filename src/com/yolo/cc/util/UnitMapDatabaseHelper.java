package com.yolo.cc.util;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.yolo.cc.info.UnitMapInfo;

public class UnitMapDatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static final String DATABASE_NAME = "unitMapInfo.db";
	private static int DATABASE_VERSION = 1;
	private Dao<UnitMapInfo, Integer> unitMapInfoDao = null;

	public UnitMapDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase,
			ConnectionSource connectionSource) {
		try {
			TableUtils.createTableIfNotExists(getConnectionSource(),
					UnitMapInfo.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase,
			ConnectionSource connectionSource, int arg2, int arg3) {
		try {
			TableUtils.dropTable(connectionSource, UnitMapInfo.class, true);
			onCreate(sqLiteDatabase, connectionSource);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Dao<UnitMapInfo, Integer> getUnitMapInfoDao() {
		if (unitMapInfoDao == null) {
			try {
				unitMapInfoDao = getDao(UnitMapInfo.class);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return unitMapInfoDao;
	}

	public void close() {
		super.close();
		unitMapInfoDao = null;
	}
}
