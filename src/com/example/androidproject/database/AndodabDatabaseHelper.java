package com.example.androidproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.projetandroid2015.tables.DicObjectEntryTable;
import com.example.projetandroid2015.tables.DicoObjectTable;
import com.example.projetandroid2015.tables.EntryTable;
import com.example.projetandroid2015.tables.ObjectEntryTable;
import com.example.projetandroid2015.tables.ObjectPPrimitiveTable;
import com.example.projetandroid2015.tables.ObjectTable;
import com.example.projetandroid2015.tables.PrimitiveEntryTable;
import com.example.projetandroid2015.tables.PrimitiveObjectTable;
import com.example.projetandroid2015.tables.RootObjectTable;

public class AndodabDatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "AndodabDB";
	private static int DATABASE_VERSION = 1;

	public AndodabDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Method called during the creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		RootObjectTable.onCreate(database);
		ObjectTable.onCreate(database);
		ObjectEntryTable.onCreate(database);
		ObjectPPrimitiveTable.onCreate(database);
		PrimitiveObjectTable.onCreate(database);
		EntryTable.onCreate(database);
		PrimitiveEntryTable.onCreate(database);
		DicoObjectTable.onCreate(database);
		DicObjectEntryTable.onCreate(database);
	}

	// Method called during an upgrade of the database, if you increase the
	// database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		DATABASE_VERSION += 1;
		RootObjectTable.onUpgrade(database, oldVersion, newVersion);
		ObjectTable.onUpgrade(database, oldVersion, newVersion);
		ObjectEntryTable.onUpgrade(database, oldVersion, newVersion);
		ObjectPPrimitiveTable.onUpgrade(database, oldVersion, newVersion);
		PrimitiveObjectTable.onUpgrade(database, oldVersion, newVersion);
		PrimitiveEntryTable.onUpgrade(database, oldVersion, newVersion);
		EntryTable.onUpgrade(database, oldVersion, newVersion);
		DicoObjectTable.onUpgrade(database, oldVersion, newVersion);
		DicObjectEntryTable.onUpgrade(database, oldVersion, newVersion);
	}
	
	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		db.execSQL("PRAGMA foreign_keys = ON;"); 
	}
}
