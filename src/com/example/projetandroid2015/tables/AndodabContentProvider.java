package com.example.projetandroid2015.tables;

import java.util.Arrays;
import java.util.HashSet;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class AndodabContentProvider extends ContentProvider {

  // database
  private AndodabDatabaseHelper database;

  // used for the UriMacher
  private static final int TODOS = 10;
  private static final int TODO_ID = 20;

  private static final String AUTHORITY = "project.andodab.items";

  private static final String BASE_PATH = "andodab";
  public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
      + "/" + BASE_PATH);

  public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
      + "/andodab";
  public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
      + "/ando";

//  private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//  static {
//    sURIMatcher.addURI(AUTHORITY, BASE_PATH, TODOS);
//    sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", TODO_ID);
//  }

  @Override
  public boolean onCreate() {
    database = new AndodabDatabaseHelper(getContext());
    return false;
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection,
      String[] selectionArgs, String sortOrder) {

    return null ;
  }

  @Override
  public String getType(Uri uri) {
    return null;
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
	return uri;
    
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
	return 0;
    
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection,
      String[] selectionArgs) {
		return 0;

  }

  private void checkColumns(String[] projection) {
   
  }

} 