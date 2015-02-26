package com.example.projetandroid2015.tables;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AndodabDatabaseHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "andodabtable.db";
  private static final int DATABASE_VERSION = 1;

  public AndodabDatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  // Method is called during creation of the database
  @Override
  public void onCreate(SQLiteDatabase database) {
    DicObjectEntryTable.onCreate(database);
    DicoObjectTable.onCreate(database);
    EntryTable.onCreate(database);
    ObjectEntry.onCreate(database);
    ObjectPPrimitive.onCreate(database);
    ObjectTable.onCreate(database);
    PrimitiveEntryTable.onCreate(database);
    PrimitiveObjectTable.onCreate(database);
    RootObjectTable.onCreate(database);
  }

  // Method is called during an upgrade of the database,
  // e.g. if you increase the database version
  @Override
  public void onUpgrade(SQLiteDatabase database, int oldVersion,
      int newVersion) {
    DicObjectEntryTable.onUpgrade(database, oldVersion, newVersion);
    DicoObjectTable.onUpgrade(database, oldVersion, newVersion);
    EntryTable.onUpgrade(database, oldVersion, newVersion);
    ObjectEntry.onUpgrade(database, oldVersion, newVersion);
    ObjectPPrimitive.onUpgrade(database, oldVersion, newVersion);
    ObjectTable.onUpgrade(database, oldVersion, newVersion);
    PrimitiveEntryTable.onUpgrade(database, oldVersion, newVersion);
    PrimitiveObjectTable.onUpgrade(database, oldVersion, newVersion);
    RootObjectTable.onUpgrade(database, oldVersion, newVersion);
  }
}
 