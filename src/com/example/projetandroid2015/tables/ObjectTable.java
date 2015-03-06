package com.example.projetandroid2015.tables;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ObjectTable {
	// Database Table
	public static final String TABLE_NAME = "Object";
	public static final String COLUMN_ID = "_id";
	public static final String OBJECT_TYPE = "objecttype";
	public static final String ROOT = "root";

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME
			+ "(" + COLUMN_ID + " VARCHAR(50) NOT NULL, " + OBJECT_TYPE
			+ " VARCHAR(50) NOT NULL, " + ROOT
			+ " INTEGER NOT NULL, PRIMARY KEY (" + COLUMN_ID
			+ "), FOREIGN KEY (" + ROOT + ") REFERENCES RootObject("
			+ COLUMN_ID + "));";

	private static final String DB_TRIGGER = "CREATE TRIGGER delete_allobj"
			+ " BEFORE DELETE ON " + ObjectTable.TABLE_NAME
			+ " FOR EACH ROW BEGIN" + " DELETE FROM "
			+ DicoObjectTable.TABLE_NAME + " WHERE " + ObjectTable.TABLE_NAME
			+ "." + ObjectTable.COLUMN_ID + " = " + DicoObjectTable.TABLE_NAME
			+ "." + DicoObjectTable.COLUMN_ID + "; " + "END;";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(RootObjectTable.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(database);
	}
}
