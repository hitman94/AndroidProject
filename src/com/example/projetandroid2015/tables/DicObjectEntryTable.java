package com.example.projetandroid2015.tables;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DicObjectEntryTable {
	// Database Table
	public static final String TABLE_NAME = "DicObjectEntry";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_ID2 = "_id";

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME
			+ "(" + COLUMN_ID + " INTEGER NOT NULL, " + COLUMN_ID2
			+ " VARCHAR(50) NOT NULL, PRIMARY KEY (" + COLUMN_ID + ", "
			+ COLUMN_ID2 + "), FOREIGN KEY (" + COLUMN_ID
			+ ") REFERENCES Entry(" + COLUMN_ID + "), FOREIGN KEY ("
			+ COLUMN_ID2 + ") REFERENCES DicoObject(" + COLUMN_ID2 + "));";

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
