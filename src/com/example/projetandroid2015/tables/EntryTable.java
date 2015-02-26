package com.example.projetandroid2015.tables;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class EntryTable {
	// Database Table
	public static final String TABLE_NAME = "Entry";
	public static final String COLUMN_ID = "_id";
	public static final String NAME = "name";
	public static final String ENTRYTYPE = "entryType";

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME
			+ "(" + COLUMN_ID + " INTEGER NOT NULL, " + NAME
			+ " VARCHAR(50) NOT NULL, " + ENTRYTYPE
			+ " ENUM('ObjectEntry', 'PrimitiveEntry'), PRIMARY KEY ("
			+ COLUMN_ID + "));";

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
