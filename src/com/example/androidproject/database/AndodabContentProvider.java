package com.example.androidproject.database;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.projectandroid2015.util.ContentProviderUtil;
import com.example.projetandroid2015.tables.DicObjectEntryTable;
import com.example.projetandroid2015.tables.DicoObjectTable;
import com.example.projetandroid2015.tables.EntryTable;
import com.example.projetandroid2015.tables.ObjectEntryTable;
import com.example.projetandroid2015.tables.ObjectPPrimitiveTable;
import com.example.projetandroid2015.tables.ObjectTable;
import com.example.projetandroid2015.tables.PrimitiveEntryTable;
import com.example.projetandroid2015.tables.PrimitiveObjectTable;

public class AndodabContentProvider extends ContentProvider {

	// database declarations
	AndodabDatabaseHelper dbHelper;
	private SQLiteDatabase database;

	// fields for the content provider
	private static final String AUTHORITY = "com.example.andodab.provider.Andodab";
	private static final String OBJECT_PATH = "object";
	private static final String OBJECTENTRY_PATH = "objectentry";
	private static final String OBJECTPPRIMITIVE_PATH = "objectpprimitive";
	private static final String ENTRY_PATH = "entry";
	private static final String OBJECTPRIMITIVE_PATH = "objectprimitive";
	private static final String PRIMITIVEENTRY_PATH = "primitiveentry";
	private static final String DICOOBJENTRY_PATH = "dicoobjentry";
	private static final String DICOOBJ_PATH = "dicoobj";

	public static final Uri CONTENT_URI_OBJECT = Uri.parse("content://"
			+ AUTHORITY + "/" + OBJECT_PATH);
	public static final Uri CONTENT_URI_OBJECTENTRY = Uri.parse("content://"
			+ AUTHORITY + "/" + OBJECTENTRY_PATH);
	public static final Uri CONTENT_URI_OBJECTPPRIMITIVE = Uri
			.parse("content://" + AUTHORITY + "/" + OBJECTPPRIMITIVE_PATH);
	public static final Uri CONTENT_URI_ENTRY = Uri.parse("content://"
			+ AUTHORITY + "/" + ENTRY_PATH);
	public static final Uri CONTENT_URI_OBJECTPRIMITIVE = Uri
			.parse("content://" + AUTHORITY + "/" + OBJECTPRIMITIVE_PATH);
	public static final Uri CONTENT_URI_PRIMITIVEENTRY = Uri.parse("content://"
			+ AUTHORITY + "/" + PRIMITIVEENTRY_PATH);
	public static final Uri CONTENT_URI_DICOOBJENTRY = Uri.parse("content://"
			+ AUTHORITY + "/" + DICOOBJENTRY_PATH);
	public static final Uri CONTENT_URI_DICOOBJ = Uri.parse("content://"
			+ AUTHORITY + "/" + DICOOBJ_PATH);

	// integer values used in content URI
	private static final int OBJECT = 10;
	private static final int OBJECT_ID = 11;
	private static final int OBJECTENTRY = 20;
	private static final int OBJECTENTRY_ID = 21;
	private static final int OBJECTPPRIMITIVE = 30;
	private static final int OBJECTPPRIMITIVE_ID = 31;
	private static final int ENTRY = 40;
	private static final int ENTRY_ID = 41;
	private static final int OBJECTPRIMITIVE = 50;
	private static final int OBJECTPRIMITIVE_ID = 51;
	private static final int PRIMITIVEENTRY = 60;
	private static final int PRIMITIVEENTRY_ID = 61;
	private static final int DICOOBJENTRY = 70;
	private static final int DICOBJENTRY_ID = 71;
	private static final int DICOOBJ = 80;
	private static final int DICOOBJ_ID = 81;

	// projection map for a query
	private static HashMap<String, String> map;

	static final UriMatcher sURIMatcher;
	static {
		sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sURIMatcher.addURI(AUTHORITY, OBJECT_PATH, OBJECT);
		sURIMatcher.addURI(AUTHORITY, OBJECT_PATH + "/#", OBJECT_ID);
		sURIMatcher.addURI(AUTHORITY, OBJECTENTRY_PATH, OBJECTENTRY);
		sURIMatcher.addURI(AUTHORITY, OBJECTENTRY_PATH + "/#", OBJECTENTRY_ID);
		sURIMatcher.addURI(AUTHORITY, OBJECTPPRIMITIVE_PATH, OBJECTPPRIMITIVE);
		sURIMatcher.addURI(AUTHORITY, OBJECTPPRIMITIVE_PATH + "/#",
				OBJECTPPRIMITIVE_ID);
		sURIMatcher.addURI(AUTHORITY, ENTRY_PATH, ENTRY);
		sURIMatcher.addURI(AUTHORITY, ENTRY_PATH + "/#", ENTRY_ID);
		sURIMatcher.addURI(AUTHORITY, OBJECTPRIMITIVE_PATH, OBJECTPRIMITIVE);
		sURIMatcher.addURI(AUTHORITY, OBJECTPRIMITIVE_PATH + "/#",
				OBJECTPRIMITIVE_ID);
		sURIMatcher.addURI(AUTHORITY, PRIMITIVEENTRY_PATH, PRIMITIVEENTRY);
		sURIMatcher.addURI(AUTHORITY, PRIMITIVEENTRY_PATH + "/#",
				PRIMITIVEENTRY_ID);
		sURIMatcher.addURI(AUTHORITY, DICOOBJENTRY_PATH, DICOOBJENTRY);
		sURIMatcher.addURI(AUTHORITY, DICOOBJENTRY_PATH + "/#", DICOBJENTRY_ID);
		sURIMatcher.addURI(AUTHORITY, DICOOBJ_PATH, DICOOBJ);
		sURIMatcher.addURI(AUTHORITY, DICOOBJ_PATH + "/#", DICOOBJ_ID);
	}

	@Override
	public boolean onCreate() {
		Context context = getContext();

		dbHelper = new AndodabDatabaseHelper(context);
		database = dbHelper.getWritableDatabase();

		if (database == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// Using SQLiteQueryBuilder instead of query() method
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

		switch (sURIMatcher.match(uri)) {
		// Map all database column names
		case OBJECT:
			queryBuilder.setTables(ObjectTable.TABLE_NAME);
			queryBuilder.setProjectionMap(map);
			break;

		case OBJECT_ID:
			queryBuilder.setTables(ObjectTable.TABLE_NAME);
			queryBuilder.appendWhere(ObjectTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;

		case OBJECTENTRY:
			queryBuilder.setTables(ObjectEntryTable.TABLE_NAME);
			queryBuilder.setProjectionMap(map);
			break;

		case OBJECTENTRY_ID:
			queryBuilder.setTables(ObjectEntryTable.TABLE_NAME);
			queryBuilder.appendWhere(ObjectEntryTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;
		case OBJECTPPRIMITIVE:
			queryBuilder.setTables(ObjectPPrimitiveTable.TABLE_NAME);
			queryBuilder.setProjectionMap(map);
			break;

		case OBJECTPPRIMITIVE_ID:
			queryBuilder.setTables(ObjectPPrimitiveTable.TABLE_NAME);
			queryBuilder.appendWhere(ObjectEntryTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;
		case ENTRY:
			queryBuilder.setTables(EntryTable.TABLE_NAME);
			queryBuilder.setProjectionMap(map);
			break;

		case ENTRY_ID:
			queryBuilder.setTables(EntryTable.TABLE_NAME);
			queryBuilder.appendWhere(EntryTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;
		case OBJECTPRIMITIVE:
			queryBuilder.setTables(PrimitiveObjectTable.TABLE_NAME);
			queryBuilder.setProjectionMap(map);
			break;

		case OBJECTPRIMITIVE_ID:
			queryBuilder.setTables(PrimitiveObjectTable.TABLE_NAME);
			queryBuilder.appendWhere(PrimitiveObjectTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;
		case PRIMITIVEENTRY:
			queryBuilder.setTables(PrimitiveEntryTable.TABLE_NAME);
			queryBuilder.setProjectionMap(map);
			break;

		case PRIMITIVEENTRY_ID:
			queryBuilder.setTables(PrimitiveEntryTable.TABLE_NAME);
			queryBuilder.appendWhere(PrimitiveEntryTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;
		case DICOOBJENTRY:
			queryBuilder.setTables(DicObjectEntryTable.TABLE_NAME);
			queryBuilder.setProjectionMap(map);
			break;

		case DICOBJENTRY_ID:
			queryBuilder.setTables(DicObjectEntryTable.TABLE_NAME);
			queryBuilder.appendWhere(DicObjectEntryTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;
		case DICOOBJ:
			queryBuilder.setTables(DicoObjectTable.TABLE_NAME);
			queryBuilder.setProjectionMap(map);
			break;

		case DICOOBJ_ID:
			queryBuilder.setTables(DicoObjectTable.TABLE_NAME);
			queryBuilder.appendWhere(DicoObjectTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;

		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		if (sortOrder == null || sortOrder == "") {
			sortOrder = "_id";
		}
		Cursor cursor = queryBuilder.query(database, projection, selection,
				selectionArgs, null, null, sortOrder);

		/* register to watch a content URI for changes */
		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriType = sURIMatcher.match(uri);
		long id = 0;
		ContentValues contentValues;

		switch (uriType) {
		case OBJECT:
			String toAdd = values.getAsString(ObjectTable.NAME);
			String tmp;

			if (!toAdd.equals("")) {
				String sealed = values.getAsString(DicoObjectTable.SEALED);
				values.remove(DicoObjectTable.SEALED);

				// ID random
				if (values.getAsString(ObjectTable.NAME).equals("root")) {
					values.put(ObjectTable.COLUMN_ID,
							ContentProviderUtil.idroot);
				} else {
					Random random = new Random();
					values.put(ObjectTable.COLUMN_ID, random.nextLong());

					// meaning the ancestor is the root
					if (values.get(ObjectTable.ANCESTOR) == null
							&& !(values.getAsString(ObjectTable.NAME)
									.equals(ContentProviderUtil.idroot))) {
						values.put(ObjectTable.ANCESTOR,
								ContentProviderUtil.idroot);
					}
				}

				// Setting the time of the creation of the field
				values.put(ObjectTable.TIMESTAMP, new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss").format(new Date()));

				
				tmp = values.getAsString(ObjectTable.OBJECT_TYPE);
				values.remove(ObjectTable.OBJECT_TYPE);
				// insert an object type
				if (tmp.toUpperCase().equals("OBJECT")) {
					values.put(ObjectTable.OBJECT_TYPE, "Object");
					id = database.insert(ObjectTable.TABLE_NAME, "", values);
					
					ContentValues val_object = new ContentValues();
					val_object.put(DicoObjectTable.COLUMN_ID,
							values.getAsString(ObjectTable.COLUMN_ID));
					val_object.put(DicoObjectTable.SEALED, sealed);

					database.insert(DicoObjectTable.TABLE_NAME, "", val_object);
				} else if (tmp.toUpperCase().equals("PRIMITIVE")) {
					values.put(ObjectTable.OBJECT_TYPE, "Primitive");
					id = database.insert(ObjectTable.TABLE_NAME, "", values);
					
					ContentValues val_object = new ContentValues();
					val_object.put(ObjectPPrimitiveTable.COLUMN_ID,
							values.getAsString(ObjectTable.COLUMN_ID));

					database.insert(ObjectPPrimitiveTable.TABLE_NAME, "",
							val_object);
				} else {
					Toast.makeText(getContext(),
							"Object type must be Object or Primitive",
							Toast.LENGTH_LONG);
					return uri;
				}

				// if record is added successfully
				if (id > 0) {
					Uri newuri = ContentUris.withAppendedId(CONTENT_URI_OBJECT,
							id);
					getContext().getContentResolver()
							.notifyChange(newuri, null);
					return newuri;
				}
			} else {
				Toast.makeText(
						getContext(),
						"The fields for the new Object are not in a correct format. Use id:String, type:ObjectPPrimitive/DicoObject !",
						Toast.LENGTH_LONG).show();
				return uri;
			}

			break;

		case ENTRY:
			try {
				String EtoAdd = values.getAsString(EntryTable.NAME);

				if (!EtoAdd.equals("")) {
					String value = values.getAsString(ObjectEntryTable.VALUE);

					values.remove(ObjectEntryTable.VALUE);

					// ID random
					Random random = new Random();
					Long r = random.nextLong();
					values.put(EntryTable.COLUMN_ID, r);
					String type = values.getAsString(EntryTable.ENTRYTYPE);
					
					values.remove(EntryTable.ENTRYTYPE);
					
					

					// insert an object type
					if (type.toUpperCase().equals("OBJECT")) {
						values.put(EntryTable.ENTRYTYPE, "Object");
						id = database.insert(EntryTable.TABLE_NAME, "", values);
						
						ContentValues val_object = new ContentValues();
						val_object.put(ObjectEntryTable.COLUMN_ID, r);
						val_object.put(ObjectEntryTable.VALUE, value);

						database.insert(ObjectEntryTable.TABLE_NAME, "",
								val_object);

					} else if (type.toUpperCase().equals("PRIMITIVE")) {
						values.put(EntryTable.ENTRYTYPE, "Primitive");
						id = database.insert(EntryTable.TABLE_NAME, "", values);
						
						ContentValues val_object = new ContentValues();
						val_object.put(PrimitiveEntryTable.COLUMN_ID, r);
						val_object.put(PrimitiveEntryTable.VALUE, value);

						database.insert(PrimitiveEntryTable.TABLE_NAME, "",
								val_object);

					} else {
						Toast.makeText(getContext(),
								"Entry type must be Object or Primitive",
								Toast.LENGTH_LONG);
						return uri;
					}

					// if record is added successfully
					if (id > 0) {
						Uri newuri = ContentUris.withAppendedId(
								CONTENT_URI_OBJECT, id);
						getContext().getContentResolver().notifyChange(newuri,
								null);
						return newuri;
					}
				} else {
					Toast.makeText(
							getContext(),
							"The fields for the new Entry are not in a correct format. Use id:integer, type:ObjectEntry/PrimitiveEntry !",
							Toast.LENGTH_LONG).show();
					return uri;
				}
			} catch (NumberFormatException nfe) {
				Toast.makeText(getContext(),
						"The id for entry you entered is not an integer !",
						Toast.LENGTH_LONG).show();
				return uri;
			}
			break;

		case OBJECTPRIMITIVE:
			if (values.get(PrimitiveObjectTable.COLUMN_ID) != null) {
				if (values.get(PrimitiveObjectTable.ANCESTOR) == null) {
					values.put(PrimitiveObjectTable.ANCESTOR,
							ContentProviderUtil.idroot);
				}
				id = database.insert(PrimitiveObjectTable.TABLE_NAME, "",
						values);
			} else {
				Toast.makeText(getContext(), "You must define an id!",
						Toast.LENGTH_LONG).show();
				return uri;
			}
			if (id > 0) {
				Uri newuri = ContentUris.withAppendedId(
						CONTENT_URI_OBJECTPRIMITIVE, id);
				getContext().getContentResolver().notifyChange(newuri, null);
				return newuri;
			}
			break;

		case DICOOBJENTRY:
			id = database.insert(DicObjectEntryTable.TABLE_NAME, "", values);

			contentValues = new ContentValues();
			contentValues.put(ObjectTable.TIMESTAMP, new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(new Date()));
			database.update(ObjectTable.TABLE_NAME, contentValues, "_id = "
					+ values.getAsString(ObjectTable.COLUMN_ID), null);

			if (id > 0) {
				Uri newuri = ContentUris.withAppendedId(
						CONTENT_URI_DICOOBJENTRY, id);
				getContext().getContentResolver().notifyChange(newuri, null);
				return newuri;
			}
			break;

		case OBJECTENTRY:
			id = database.insert(ObjectEntryTable.TABLE_NAME, "", values);

			if (id > 0) {
				Uri newuri = ContentUris.withAppendedId(
						CONTENT_URI_OBJECTENTRY, id);
				getContext().getContentResolver().notifyChange(newuri, null);
				return newuri;
			}

			break;
		case PRIMITIVEENTRY:
			id = database.insert(PrimitiveEntryTable.TABLE_NAME, "", values);

			if (id > 0) {
				Uri newuri = ContentUris.withAppendedId(
						CONTENT_URI_PRIMITIVEENTRY, id);
				getContext().getContentResolver().notifyChange(newuri, null);
				return newuri;
			}

			break;
		}

		Toast.makeText(getContext(), "Bad insert, check your data !",
				Toast.LENGTH_LONG).show();
		return uri;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;

		switch (sURIMatcher.match(uri)) {
		case OBJECT:
			count = database.delete(ObjectTable.TABLE_NAME, selection,
					selectionArgs);
			break;

		case OBJECT_ID:
			count = database.delete(ObjectTable.TABLE_NAME,

			ObjectTable.COLUMN_ID
					+ " = "
					+ uri.getLastPathSegment()
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection
							+ ')' : ""), selectionArgs);
			break;

		case DICOOBJENTRY:
			count = database.delete(DicObjectEntryTable.TABLE_NAME, selection,
					selectionArgs);
			break;

		case DICOBJENTRY_ID:
			count = database.delete(
					DicObjectEntryTable.TABLE_NAME,
					DicObjectEntryTable.COLUMN_ID + " = "
							+ uri.getLastPathSegment() + " AND (" + selection
							+ ")", selectionArgs);
			break;

		case DICOOBJ:
			count = database.delete(DicoObjectTable.TABLE_NAME, selection,
					selectionArgs);
			break;

		case DICOOBJ_ID:
			count = database.delete(DicoObjectTable.TABLE_NAME,

			DicoObjectTable.COLUMN_ID
					+ " = "
					+ uri.getLastPathSegment()
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection
							+ ')' : ""), selectionArgs);
			break;

		case ENTRY:
			count = database.delete(EntryTable.TABLE_NAME, selection,
					selectionArgs);
			break;

		case ENTRY_ID:
			count = database.delete(EntryTable.TABLE_NAME,

			EntryTable.COLUMN_ID
					+ " = "
					+ uri.getLastPathSegment()
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection
							+ ')' : ""), selectionArgs);
			break;

		case OBJECTENTRY:
			count = database.delete(ObjectEntryTable.TABLE_NAME, selection,
					selectionArgs);
			break;

		case OBJECTENTRY_ID:
			count = database.delete(ObjectEntryTable.TABLE_NAME,

			ObjectEntryTable.COLUMN_ID
					+ " = "
					+ uri.getLastPathSegment()
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection
							+ ')' : ""), selectionArgs);
			break;

		case PRIMITIVEENTRY:
			count = database.delete(PrimitiveEntryTable.TABLE_NAME, selection,
					selectionArgs);
			break;

		case PRIMITIVEENTRY_ID:
			count = database.delete(PrimitiveEntryTable.TABLE_NAME,

			PrimitiveEntryTable.COLUMN_ID
					+ " = "
					+ uri.getLastPathSegment()
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection
							+ ')' : ""), selectionArgs);
			break;

		default:
			throw new IllegalArgumentException("Unsupported URI : " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int count = 0;
		ContentValues contentValues;

		switch (sURIMatcher.match(uri)) {
		case OBJECT:
			values.put(ObjectTable.TIMESTAMP, new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(new Date()));
			count = database.update(ObjectTable.TABLE_NAME, values, selection,
					selectionArgs);
			break;

		case OBJECT_ID:
			values.put(ObjectTable.TIMESTAMP, new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(new Date()));
			count = database.update(ObjectTable.TABLE_NAME, values,
					ObjectTable.COLUMN_ID
							+ " = "
							+ uri.getLastPathSegment()
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selection + ')' : ""), selectionArgs);
			break;

		case OBJECTENTRY:
			count = database.update(ObjectEntryTable.TABLE_NAME, values,
					selection, selectionArgs);
			break;

		case OBJECTENTRY_ID:
			count = database.update(ObjectEntryTable.TABLE_NAME, values,
					ObjectTable.COLUMN_ID
							+ " = "
							+ uri.getLastPathSegment()
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selection + ')' : ""), selectionArgs);
			break;

		case ENTRY:
			count = database.update(EntryTable.TABLE_NAME, values, selection,
					selectionArgs);
			break;

		case ENTRY_ID:
			count = database.update(EntryTable.TABLE_NAME, values,
					EntryTable.COLUMN_ID
							+ " = "
							+ uri.getLastPathSegment()
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selection + ')' : ""), selectionArgs);
			break;

		case OBJECTPRIMITIVE:
			count = database.update(PrimitiveObjectTable.TABLE_NAME, values,
					selection, selectionArgs);
			break;

		case OBJECTPRIMITIVE_ID:
			count = database.update(
					PrimitiveObjectTable.TABLE_NAME,
					values,
					PrimitiveObjectTable.COLUMN_ID
							+ " = "
							+ uri.getLastPathSegment()
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selection + ')' : ""), selectionArgs);
			break;

		case PRIMITIVEENTRY:
			count = database.update(PrimitiveEntryTable.TABLE_NAME, values,
					selection, selectionArgs);
			break;

		case PRIMITIVEENTRY_ID:
			count = database.update(
					PrimitiveEntryTable.TABLE_NAME,
					values,
					PrimitiveEntryTable.COLUMN_ID
							+ " = "
							+ uri.getLastPathSegment()
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selection + ')' : ""), selectionArgs);
			break;

		case DICOOBJENTRY:
			count = database.update(DicObjectEntryTable.TABLE_NAME, values,
					selection, selectionArgs);
			break;

		case DICOBJENTRY_ID:
			count = database.update(
					DicObjectEntryTable.TABLE_NAME,
					values,
					DicObjectEntryTable.COLUMN_ID
							+ " = "
							+ uri.getLastPathSegment()
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selection + ')' : ""), selectionArgs);
			break;

		case DICOOBJ:
			contentValues = new ContentValues();
			contentValues.put(ObjectTable.TIMESTAMP, new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(new Date()));
			database.update(ObjectTable.TABLE_NAME, contentValues, "_id = "
					+ values.getAsString(ObjectTable.COLUMN_ID), null);
			count = database.update(DicoObjectTable.TABLE_NAME, values,
					selection, selectionArgs);
			break;

		case DICOOBJ_ID:
			contentValues = new ContentValues();
			contentValues.put(ObjectTable.TIMESTAMP, new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(new Date()));
			database.update(ObjectTable.TABLE_NAME, contentValues, "_id = "
					+ values.getAsString(ObjectTable.COLUMN_ID), null);
			count = database.update(
					DicoObjectTable.TABLE_NAME,
					values,
					DicoObjectTable.COLUMN_ID
							+ " = "
							+ uri.getLastPathSegment()
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selection + ')' : ""), selectionArgs);
			break;

		default:
			throw new IllegalArgumentException("Unsupported URI : " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}