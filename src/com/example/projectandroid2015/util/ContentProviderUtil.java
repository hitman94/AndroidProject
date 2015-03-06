package com.example.projectandroid2015.util;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.example.androidproject.database.AndodabContentProvider;
import com.example.projetandroid2015.tables.DicObjectEntryTable;
import com.example.projetandroid2015.tables.DicoObjectTable;
import com.example.projetandroid2015.tables.EntryTable;
import com.example.projetandroid2015.tables.ObjectEntryTable;
import com.example.projetandroid2015.tables.ObjectPPrimitiveTable;
import com.example.projetandroid2015.tables.ObjectTable;
import com.example.projetandroid2015.tables.PrimitiveEntryTable;
import com.example.projetandroid2015.tables.PrimitiveObjectTable;
import com.example.projetandroid2015.tables.RootObjectTable;

public class ContentProviderUtil  {
	private static final int rootValue = 99999999;
	
	private Context context;
	
	public ContentProviderUtil(Context cont) {
		this.context=cont;
	}

	public void addElement(View view) {
		addRoot(view);
		addObject(view);
		addPrimitive(view);
		addEntry(view);
		addDicoE(view);
	}

	public void showElement(View view) {
		showRoot(view);
		showObject(view);
		showDicoObject(view);
		showPPObject(view);
		showPObject(view);
		showEntry(view);
		showOEntry(view);
		showPEntry(view);
		showDOEntry(view);
	}

	public void addRoot(View view) {
		ContentValues values = new ContentValues();
		values.put(RootObjectTable.COLUMN_ID, rootValue);
		context.getContentResolver().insert(AndodabContentProvider.CONTENT_URI_ROOT,
				values);

		Toast.makeText(context, "Andodab : root inserted!",
				Toast.LENGTH_LONG).show();
	}

	public void addObject(View view) {
		ContentValues values = new ContentValues();

		// Dico Objects
		values.put(ObjectTable.COLUMN_ID, "Food");
		values.put(ObjectTable.OBJECT_TYPE, "Object");
		values.put(ObjectTable.ROOT, rootValue);
		values.put(DicoObjectTable.SEALED, "false");
		context.getContentResolver().insert(AndodabContentProvider.CONTENT_URI_OBJECT,
				values);

		values.put(ObjectTable.COLUMN_ID, "Animal");
		values.put(ObjectTable.OBJECT_TYPE, "Object");
		values.put(ObjectTable.ROOT, rootValue);
		values.put(DicoObjectTable.SEALED, "false");
		context.getContentResolver().insert(AndodabContentProvider.CONTENT_URI_OBJECT,
				values);

		values.put(ObjectTable.COLUMN_ID, "Mammal");
		values.put(ObjectTable.OBJECT_TYPE, "Object");
		values.put(ObjectTable.ROOT, rootValue);
		values.put(DicoObjectTable.ANCESTOR, "Animal");
		values.put(DicoObjectTable.SEALED, "false");
		context.getContentResolver().insert(AndodabContentProvider.CONTENT_URI_OBJECT,
				values);

		values.put(ObjectTable.COLUMN_ID, "Eucalyptus");
		values.put(ObjectTable.OBJECT_TYPE, "Object");
		values.put(ObjectTable.ROOT, rootValue);
		values.put(DicoObjectTable.ANCESTOR, "Food");
		values.put(DicoObjectTable.SEALED, "false");
		context.getContentResolver().insert(AndodabContentProvider.CONTENT_URI_OBJECT,
				values);

		values.put(ObjectTable.COLUMN_ID, "Koala");
		values.put(ObjectTable.OBJECT_TYPE, "Object");
		values.put(ObjectTable.ROOT, rootValue);
		values.put(DicoObjectTable.ANCESTOR, "Animal");
		values.put(DicoObjectTable.SEALED, "false");
		context.getContentResolver().insert(AndodabContentProvider.CONTENT_URI_OBJECT,
				values);

		values.clear();

		// PPrimitiveObjects
		values.put(ObjectTable.COLUMN_ID, "String");
		values.put(ObjectTable.OBJECT_TYPE, "Primitive");
		values.put(ObjectTable.ROOT, rootValue);
		context.getContentResolver().insert(AndodabContentProvider.CONTENT_URI_OBJECT,
				values);

		values.put(ObjectTable.COLUMN_ID, "Float");
		values.put(ObjectTable.OBJECT_TYPE, "Primitive");
		values.put(ObjectTable.ROOT, rootValue);
		context.getContentResolver().insert(AndodabContentProvider.CONTENT_URI_OBJECT,
				values);

		values.put(ObjectTable.COLUMN_ID, "Integer");
		values.put(ObjectTable.OBJECT_TYPE, "Primitive");
		values.put(ObjectTable.ROOT, rootValue);
		context.getContentResolver().insert(AndodabContentProvider.CONTENT_URI_OBJECT,
				values);

		Toast.makeText(context, "Andodab : Objects inserted!",
				Toast.LENGTH_LONG).show();
	}

	public void addPrimitive(View view) {
		ContentValues values = new ContentValues();

		values.put(PrimitiveObjectTable.COLUMN_ID, "6723");
		values.put(PrimitiveObjectTable.ANCESTOR, "Integer");
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_OBJECTPRIMITIVE, values);

		values.put(PrimitiveObjectTable.COLUMN_ID, "100.0");
		values.put(PrimitiveObjectTable.ANCESTOR, "Float");
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_OBJECTPRIMITIVE, values);

		values.put(PrimitiveObjectTable.COLUMN_ID, "Fougere");
		values.put(PrimitiveObjectTable.ANCESTOR, "String");
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_OBJECTPRIMITIVE, values);

		Toast.makeText(context,
				"Andodab : Primitive Objects inserted!", Toast.LENGTH_LONG)
				.show();
	}

	public void addEntry(View view) {
		ContentValues values = new ContentValues();

		// ObjectEntry
		values.put(EntryTable.COLUMN_ID, "1");
		values.put(EntryTable.NAME, "energyDensity");
		values.put(EntryTable.ENTRYTYPE, "Object");
		values.put(ObjectEntryTable.VALUE, "Float");
		context.getContentResolver().insert(AndodabContentProvider.CONTENT_URI_ENTRY,
				values);

		values.put(EntryTable.COLUMN_ID, "2");
		values.put(EntryTable.NAME, "food");
		values.put(EntryTable.ENTRYTYPE, "Object");
		values.put(ObjectEntryTable.VALUE, "Food");
		context.getContentResolver().insert(AndodabContentProvider.CONTENT_URI_ENTRY,
				values);

		values.put(EntryTable.COLUMN_ID, "3");
		values.put(EntryTable.NAME, "food");
		values.put(EntryTable.ENTRYTYPE, "Object");
		values.put(ObjectEntryTable.VALUE, "Eucalyptus");
		context.getContentResolver().insert(AndodabContentProvider.CONTENT_URI_ENTRY,
				values);

		// PrimitiveEntry
		values.put(EntryTable.COLUMN_ID, "4");
		values.put(EntryTable.NAME, "energyDensity");
		values.put(EntryTable.ENTRYTYPE, "Primitive");
		values.put(ObjectEntryTable.VALUE, "100.0");
		context.getContentResolver().insert(AndodabContentProvider.CONTENT_URI_ENTRY,
				values);

		Toast.makeText(context, "Andodab : Entry inserted!",
				Toast.LENGTH_LONG).show();
	}

	public void addDicoE(View view) {
		ContentValues values = new ContentValues();

		values.put(DicObjectEntryTable.COLUMN_ID, "1");
		values.put(DicObjectEntryTable.COLUMN_ID2, "Food");
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_DICOOBJENTRY, values);

		values.put(DicObjectEntryTable.COLUMN_ID, "2");
		values.put(DicObjectEntryTable.COLUMN_ID2, "Animal");
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_DICOOBJENTRY, values);

		values.put(DicObjectEntryTable.COLUMN_ID, "3");
		values.put(DicObjectEntryTable.COLUMN_ID2, "Eucalyptus");
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_DICOOBJENTRY, values);

		values.put(DicObjectEntryTable.COLUMN_ID, "4");
		values.put(DicObjectEntryTable.COLUMN_ID2, "Koala");
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_DICOOBJENTRY, values);

		values.put(DicObjectEntryTable.COLUMN_ID, "2");
		values.put(DicObjectEntryTable.COLUMN_ID2, "Koala");
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_DICOOBJENTRY, values);

		values.put(DicObjectEntryTable.COLUMN_ID, "2");
		values.put(DicObjectEntryTable.COLUMN_ID2, "Mammal");
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_DICOOBJENTRY, values);

		Toast.makeText(context, "Andodab : DicoObjectEntry inserted!",
				Toast.LENGTH_LONG).show();
	}

	public void showRoot(View view) {
		// Show all the roots sorted by ids
		String URL = "content://com.example.andodab.provider.Andodab/root";
		Uri roots = Uri.parse(URL);

		Cursor c = context.getContentResolver().query(roots, null, null, null, "_id");
		String result = "Root results : ";
		if (!c.moveToFirst()) {
			Toast.makeText(context, result + " no content yet", Toast.LENGTH_LONG)
					.show();
		} else {
			do {
				result = result + "\n"
						+ c.getString(c.getColumnIndex(ObjectTable.COLUMN_ID));
			} while (c.moveToNext());
			Toast.makeText(context, result, Toast.LENGTH_LONG).show();
		}
	}

	public void showObject(View view) {
		String URL = "content://com.example.andodab.provider.Andodab/object";
		Uri roots = Uri.parse(URL);

		Cursor c = context.getContentResolver().query(roots, null, null, null, "_id");
		String result = "Object results : ";
		if (!c.moveToFirst()) {
			Toast.makeText(context, result + " no content yet", Toast.LENGTH_LONG)
					.show();
		} else {
			do {
				result = result
						+ "\n"
						+ c.getString(c.getColumnIndex(ObjectTable.COLUMN_ID))
						+ " "
						+ c.getString(c.getColumnIndex(ObjectTable.OBJECT_TYPE))
						+ " " + c.getString(c.getColumnIndex(ObjectTable.ROOT));
			} while (c.moveToNext());
			Toast.makeText(context, result, Toast.LENGTH_LONG).show();
		}
	}

	public void showDicoObject(View view) {
		String URL = "content://com.example.andodab.provider.Andodab/dicoobj";
		Uri roots = Uri.parse(URL);

		Cursor c = context.getContentResolver().query(roots, null, null, null, "_id");
		String result = "DicoObject results : ";
		if (!c.moveToFirst()) {
			Toast.makeText(context, result + " no content yet", Toast.LENGTH_LONG)
					.show();
		} else {
			do {
				result = result
						+ "\n"
						+ c.getString(c
								.getColumnIndex(DicoObjectTable.COLUMN_ID))
						+ " "
						+ c.getString(c
								.getColumnIndex(DicoObjectTable.ANCESTOR))
						+ " "
						+ c.getString(c.getColumnIndex(DicoObjectTable.SEALED));
			} while (c.moveToNext());
			Toast.makeText(context, result, Toast.LENGTH_LONG).show();
		}
	}

	public void showPPObject(View view) {
		String URL = "content://com.example.andodab.provider.Andodab/objectpprimitive";
		Uri roots = Uri.parse(URL);

		Cursor c = context.getContentResolver().query(roots, null, null, null, "_id");
		String result = "ObjectPPrimitive results : ";
		if (!c.moveToFirst()) {
			Toast.makeText(context, result + " no content yet", Toast.LENGTH_LONG)
					.show();
		} else {
			do {
				result = result
						+ "\n"
						+ c.getString(c
								.getColumnIndex(ObjectPPrimitiveTable.COLUMN_ID));
			} while (c.moveToNext());
			Toast.makeText(context, result, Toast.LENGTH_LONG).show();
		}
	}

	public void showPObject(View view) {
		String URL = "content://com.example.andodab.provider.Andodab/objectprimitive";
		Uri roots = Uri.parse(URL);

		Cursor c = context.getContentResolver().query(roots, null, null, null, "_id");
		String result = "PrimitiveObject results : ";
		if (!c.moveToFirst()) {
			Toast.makeText(context, result + " no content yet", Toast.LENGTH_LONG)
					.show();
		} else {
			do {
				result = result
						+ "\n"
						+ c.getString(c
								.getColumnIndex(PrimitiveObjectTable.COLUMN_ID))
						+ " "
						+ c.getString(c
								.getColumnIndex(PrimitiveObjectTable.ANCESTOR));
			} while (c.moveToNext());
			Toast.makeText(context, result, Toast.LENGTH_LONG).show();
		}
	}

	public void showEntry(View view) {
		String URL = "content://com.example.andodab.provider.Andodab/entry";
		Uri roots = Uri.parse(URL);

		Cursor c = context.getContentResolver().query(roots, null, null, null, "_id");
		String result = "Entry results : ";
		if (!c.moveToFirst()) {
			Toast.makeText(context, result + " no content yet", Toast.LENGTH_LONG)
					.show();
		} else {
			do {
				result = result + "\n"
						+ c.getString(c.getColumnIndex(EntryTable.COLUMN_ID))
						+ " "
						+ c.getString(c.getColumnIndex(EntryTable.ENTRYTYPE))
						+ " " + c.getString(c.getColumnIndex(EntryTable.NAME));
			} while (c.moveToNext());
			Toast.makeText(context, result, Toast.LENGTH_LONG).show();
		}
	}

	public void showOEntry(View view) {
		String URL = "content://com.example.andodab.provider.Andodab/objectentry";
		Uri roots = Uri.parse(URL);

		Cursor c = context.getContentResolver().query(roots, null, null, null, "_id");
		String result = "ObjectEntry results : ";
		if (!c.moveToFirst()) {
			Toast.makeText(context, result + " no content yet", Toast.LENGTH_LONG)
					.show();
		} else {
			do {
				result = result
						+ "\n"
						+ c.getString(c
								.getColumnIndex(ObjectEntryTable.COLUMN_ID))
						+ " "
						+ c.getString(c.getColumnIndex(ObjectEntryTable.VALUE));
			} while (c.moveToNext());
			Toast.makeText(context, result, Toast.LENGTH_LONG).show();
		}
	}

	public void showPEntry(View view) {
		String URL = "content://com.example.andodab.provider.Andodab/primitiveentry";
		Uri roots = Uri.parse(URL);

		Cursor c = context.getContentResolver().query(roots, null, null, null, "_id");
		String result = "PrimitiveEntry results : ";
		if (!c.moveToFirst()) {
			Toast.makeText(context, result + " no content yet", Toast.LENGTH_LONG)
					.show();
		} else {
			do {
				result = result
						+ "\n"
						+ c.getString(c
								.getColumnIndex(PrimitiveEntryTable.COLUMN_ID))
						+ " "
						+ c.getString(c
								.getColumnIndex(PrimitiveEntryTable.VALUE));
			} while (c.moveToNext());
			Toast.makeText(context, result, Toast.LENGTH_LONG).show();
		}
	}

	public void showDOEntry(View view) {
		String URL = "content://com.example.andodab.provider.Andodab/dicoobjentry";
		Uri roots = Uri.parse(URL);

		Cursor c = context.getContentResolver().query(roots, null, "_idDO = 'Koala'",
				null, "_id");
		String result = "DicoObjectEntry results : ";
		if (!c.moveToFirst()) {
			Toast.makeText(context, result + " no content yet", Toast.LENGTH_LONG)
					.show();
		} else {
			do {
				String ancestor = null;
				Cursor cDO = context.getContentResolver()
						.query(Uri
								.parse("content://com.example.andodab.provider.Andodab/dicoobj"),
								null,
								"_id = '"
										+ c.getString(c
												.getColumnIndex(DicObjectEntryTable.COLUMN_ID2))
										+ "'", null, "_id");

				if (!cDO.moveToFirst()) {
					Toast.makeText(context, result + " no content yet",
							Toast.LENGTH_LONG).show();
				} else {
					Cursor cE = context.getContentResolver()
							.query(Uri
									.parse("content://com.example.andodab.provider.Andodab/entry"),
									null,
									"_id = "
											+ c.getString(c
													.getColumnIndex(DicObjectEntryTable.COLUMN_ID)),
									null, "_id");

					if (!cE.moveToFirst()) {
						Toast.makeText(context, result + " no content yet",
								Toast.LENGTH_LONG).show();
					} else {
						Cursor detailEntry;
						if (cE.getString(
								cE.getColumnIndex(EntryTable.ENTRYTYPE))
								.toUpperCase().equals("OBJECT")) {
							detailEntry = context.getContentResolver()
									.query(Uri
											.parse("content://com.example.andodab.provider.Andodab/objectentry"),
											null,
											"_id = "
													+ cE.getString(cE
															.getColumnIndex(EntryTable.COLUMN_ID)),
											null, "_id");
						} else {
							detailEntry = context.getContentResolver()
									.query(Uri
											.parse("content://com.example.andodab.provider.Andodab/primitiveentry"),
											null,
											"_id = "
													+ cE.getString(cE
															.getColumnIndex(EntryTable.COLUMN_ID)),
											null, "_id");
						}
						if (!detailEntry.moveToFirst()) {
							Toast.makeText(context, result + " no content yet",
									Toast.LENGTH_LONG).show();
						} else {
							result = result
									+ "\n"
									+ cE.getString(cE
											.getColumnIndex(EntryTable.NAME))
									+ "="
									+ detailEntry
											.getString(detailEntry
													.getColumnIndex(ObjectEntryTable.VALUE));
						}
					}
				}
			} while (c.moveToNext());
			Toast.makeText(context, result, Toast.LENGTH_LONG).show();
		}
	}

	// TODO M�thode pour r�cup�rer un objet directement
	public HashMap<String, String> getObject(String objectID) {
		HashMap<String, String> data = new HashMap<String, String>();

		String URL = "content://com.example.andodab.provider.Andodab/object";
		Uri objects = Uri.parse(URL);

		Cursor c = context.getContentResolver().query(objects, null,
				"_id = '" + objectID + "'", null, "_id");

		if (!c.moveToFirst()) {
			return data;
		} else {
			data.put(ObjectTable.COLUMN_ID,
					c.getString(c.getColumnIndex(ObjectTable.COLUMN_ID)));
			data.put(ObjectTable.OBJECT_TYPE,
					c.getString(c.getColumnIndex(ObjectTable.OBJECT_TYPE)));
			data.put(ObjectTable.ROOT,
					c.getString(c.getColumnIndex(ObjectTable.ROOT)));
		}

		if (data.get(ObjectTable.OBJECT_TYPE).toUpperCase().equals("OBJECT")) {
			URL = "content://com.example.andodab.provider.Andodab/dicoobj";
		} else {
			return data;
		}

		objects = Uri.parse(URL);
		c = context.getContentResolver().query(objects, null,
				"_id = '" + objectID + "'", null, "_id");
		if (!c.moveToFirst()) {
			return data;
		} else {
			data.put(DicoObjectTable.ANCESTOR,
					c.getString(c.getColumnIndex(DicoObjectTable.ANCESTOR)));
			data.put(DicoObjectTable.SEALED,
					c.getString(c.getColumnIndex(DicoObjectTable.SEALED)));
			return data;
		}
	}

	// TODO m�thode pour r�cup�rer les enfants d'un dicoobject
	public ArrayList<String> getChildren(String objectID) {
		String URL = "content://com.example.andodab.provider.Andodab/dicoobject";
		Uri roots = Uri.parse(URL);

		ArrayList<String> children = new ArrayList<String>();

		Cursor c = getContentResolver().query(roots, null,
				"ancestor = '" + objectID + "'", null, "_id");

		if (!c.moveToFirst()) {
			return null;
		} else {
			do {
				children.add(c.getString(c
						.getColumnIndex(DicoObjectTable.COLUMN_ID)));

			} while (c.moveToNext());
		}
		return children;
	}

	// TODO M�thode avec hashmap pour les �lectriciens
	public HashMap<String, String> getProperties(String objectID) {
		HashMap<String, String> properties = new HashMap<String, String>();
		String URL = "content://com.example.andodab.provider.Andodab/dicoobjentry";
		Uri doe = Uri.parse(URL);

		Cursor c = context.getContentResolver().query(doe, null,
				"_idDO = '" + objectID + "'", null, "_id");

		if (!c.moveToFirst()) {
			return properties;
		} else {
			do {
				Cursor cE = context.getContentResolver()
						.query(Uri
								.parse("content://com.example.andodab.provider.Andodab/entry"),
								null,
								"_id = "
										+ c.getString(c
												.getColumnIndex(DicObjectEntryTable.COLUMN_ID)),
								null, "_id");

				if (!cE.moveToFirst()) {
					return properties;
				} else {
					Cursor detailEntry;
					if (cE.getString(cE.getColumnIndex(EntryTable.ENTRYTYPE))
							.toUpperCase().equals("OBJECT")) {
						detailEntry = context.getContentResolver()
								.query(Uri
										.parse("content://com.example.andodab.provider.Andodab/objectentry"),
										null,
										"_id = "
												+ cE.getString(cE
														.getColumnIndex(EntryTable.COLUMN_ID)),
										null, "_id");
					} else {
						detailEntry = context.getContentResolver()
								.query(Uri
										.parse("content://com.example.andodab.provider.Andodab/primitiveentry"),
										null,
										"_id = "
												+ cE.getString(cE
														.getColumnIndex(EntryTable.COLUMN_ID)),
										null, "_id");
					}
					if (!detailEntry.moveToFirst()) {
						return properties;
					} else {
						properties
								.put(cE.getString(cE
										.getColumnIndex(EntryTable.NAME)),
										detailEntry.getString(detailEntry
												.getColumnIndex(ObjectEntryTable.VALUE)));
					}
				}
			} while (c.moveToNext());
			return properties;
		}
	}
}
