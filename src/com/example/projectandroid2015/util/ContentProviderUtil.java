package com.example.projectandroid2015.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.androidproject.R;
import com.example.androidproject.database.AndodabContentProvider;
import com.example.projetandroid2015.tables.DicObjectEntryTable;
import com.example.projetandroid2015.tables.DicoObjectTable;
import com.example.projetandroid2015.tables.EntryTable;
import com.example.projetandroid2015.tables.ObjectEntryTable;
import com.example.projetandroid2015.tables.ObjectPPrimitiveTable;
import com.example.projetandroid2015.tables.ObjectTable;
import com.example.projetandroid2015.tables.PrimitiveEntryTable;
import com.example.projetandroid2015.tables.PrimitiveObjectTable;

public class ContentProviderUtil extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void addElement(View view) {
		addRoot(view);
		addObject(view);
		addPrimitive(view);
		addEntry(view);
		addDicoE(view);
	}

	public void showElement(View view) {
		Log.e(STORAGE_SERVICE, getProperties("Koala").toString());
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
		values.put(ObjectTable.COLUMN_ID, "root");
		values.put(ObjectTable.OBJECT_TYPE, "Object");
		values.put(DicoObjectTable.SEALED, "false");
		getContentResolver().insert(AndodabContentProvider.CONTENT_URI_OBJECT,
				values);

		Toast.makeText(getBaseContext(), "Andodab : root inserted!",
				Toast.LENGTH_LONG).show();
	}

	public void addObject(View view) {
		ContentValues values = new ContentValues();

		// Dico Objects
		values.put(ObjectTable.COLUMN_ID, "Food");
		values.put(ObjectTable.OBJECT_TYPE, "Object");
		values.put(DicoObjectTable.SEALED, "false");
		getContentResolver().insert(AndodabContentProvider.CONTENT_URI_OBJECT,
				values);

		values.put(ObjectTable.COLUMN_ID, "Animal");
		values.put(ObjectTable.OBJECT_TYPE, "Object");
		values.put(DicoObjectTable.SEALED, "false");
		getContentResolver().insert(AndodabContentProvider.CONTENT_URI_OBJECT,
				values);

		values.put(ObjectTable.COLUMN_ID, "Mammal");
		values.put(ObjectTable.OBJECT_TYPE, "Object");
		values.put(ObjectTable.ANCESTOR, "Animal");
		values.put(DicoObjectTable.SEALED, "false");
		getContentResolver().insert(AndodabContentProvider.CONTENT_URI_OBJECT,
				values);

		values.put(ObjectTable.COLUMN_ID, "Eucalyptus");
		values.put(ObjectTable.OBJECT_TYPE, "Object");
		values.put(ObjectTable.ANCESTOR, "Food");
		values.put(DicoObjectTable.SEALED, "false");
		getContentResolver().insert(AndodabContentProvider.CONTENT_URI_OBJECT,
				values);

		values.put(ObjectTable.COLUMN_ID, "Koala");
		values.put(ObjectTable.OBJECT_TYPE, "Object");
		values.put(ObjectTable.ANCESTOR, "Animal");
		values.put(DicoObjectTable.SEALED, "false");
		getContentResolver().insert(AndodabContentProvider.CONTENT_URI_OBJECT,
				values);

		values.clear();

		// PPrimitiveObjects
		values.put(ObjectTable.COLUMN_ID, "String");
		values.put(ObjectTable.OBJECT_TYPE, "Primitive");
		getContentResolver().insert(AndodabContentProvider.CONTENT_URI_OBJECT,
				values);

		values.put(ObjectTable.COLUMN_ID, "Float");
		values.put(ObjectTable.OBJECT_TYPE, "Primitive");
		getContentResolver().insert(AndodabContentProvider.CONTENT_URI_OBJECT,
				values);

		values.put(ObjectTable.COLUMN_ID, "Integer");
		values.put(ObjectTable.OBJECT_TYPE, "Primitive");
		getContentResolver().insert(AndodabContentProvider.CONTENT_URI_OBJECT,
				values);

		Toast.makeText(getBaseContext(), "Andodab : Objects inserted!",
				Toast.LENGTH_LONG).show();
	}

	public void addPrimitive(View view) {
		ContentValues values = new ContentValues();

		values.put(PrimitiveObjectTable.COLUMN_ID, "6723");
		values.put(PrimitiveObjectTable.ANCESTOR, "Integer");
		getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_OBJECTPRIMITIVE, values);

		values.put(PrimitiveObjectTable.COLUMN_ID, "100.0");
		values.put(PrimitiveObjectTable.ANCESTOR, "Float");
		getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_OBJECTPRIMITIVE, values);

		values.put(PrimitiveObjectTable.COLUMN_ID, "Fougere");
		values.put(PrimitiveObjectTable.ANCESTOR, "String");
		getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_OBJECTPRIMITIVE, values);

		values.clear();
		values.put(PrimitiveObjectTable.COLUMN_ID, "Banane");
		getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_OBJECTPRIMITIVE, values);

		Toast.makeText(getBaseContext(),
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
		getContentResolver().insert(AndodabContentProvider.CONTENT_URI_ENTRY,
				values);

		values.put(EntryTable.COLUMN_ID, "2");
		values.put(EntryTable.NAME, "food");
		values.put(EntryTable.ENTRYTYPE, "Object");
		values.put(ObjectEntryTable.VALUE, "Food");
		getContentResolver().insert(AndodabContentProvider.CONTENT_URI_ENTRY,
				values);

		values.put(EntryTable.COLUMN_ID, "3");
		values.put(EntryTable.NAME, "food");
		values.put(EntryTable.ENTRYTYPE, "Object");
		values.put(ObjectEntryTable.VALUE, "Eucalyptus");
		getContentResolver().insert(AndodabContentProvider.CONTENT_URI_ENTRY,
				values);

		// PrimitiveEntry
		values.put(EntryTable.COLUMN_ID, "4");
		values.put(EntryTable.NAME, "energyDensity");
		values.put(EntryTable.ENTRYTYPE, "Primitive");
		values.put(ObjectEntryTable.VALUE, "100.0");
		getContentResolver().insert(AndodabContentProvider.CONTENT_URI_ENTRY,
				values);

		Toast.makeText(getBaseContext(), "Andodab : Entry inserted!",
				Toast.LENGTH_LONG).show();
	}

	public void addDicoE(View view) {
		ContentValues values = new ContentValues();

		values.put(DicObjectEntryTable.COLUMN_ID, "1");
		values.put(DicObjectEntryTable.COLUMN_ID2, "Food");
		getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_DICOOBJENTRY, values);

		values.put(DicObjectEntryTable.COLUMN_ID, "2");
		values.put(DicObjectEntryTable.COLUMN_ID2, "Animal");
		getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_DICOOBJENTRY, values);

		values.put(DicObjectEntryTable.COLUMN_ID, "3");
		values.put(DicObjectEntryTable.COLUMN_ID2, "Eucalyptus");
		getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_DICOOBJENTRY, values);

		values.put(DicObjectEntryTable.COLUMN_ID, "4");
		values.put(DicObjectEntryTable.COLUMN_ID2, "Koala");
		getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_DICOOBJENTRY, values);

		values.put(DicObjectEntryTable.COLUMN_ID, "2");
		values.put(DicObjectEntryTable.COLUMN_ID2, "Koala");
		getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_DICOOBJENTRY, values);

		values.put(DicObjectEntryTable.COLUMN_ID, "2");
		values.put(DicObjectEntryTable.COLUMN_ID2, "Mammal");
		getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_DICOOBJENTRY, values);

		Toast.makeText(getBaseContext(), "Andodab : DicoObjectEntry inserted!",
				Toast.LENGTH_LONG).show();
	}

	public void showObject(View view) {
		String URL = "content://com.example.andodab.provider.Andodab/object";
		Uri roots = Uri.parse(URL);

		Cursor c = getContentResolver().query(roots, null, null, null, "_id");
		String result = "Object results : ";
		if (!c.moveToFirst()) {
			Toast.makeText(this, result + " no content yet", Toast.LENGTH_LONG)
					.show();
		} else {
			do {
				result = result
						+ "\n"
						+ c.getString(c.getColumnIndex(ObjectTable.COLUMN_ID))
						+ " "
						+ c.getString(c.getColumnIndex(ObjectTable.OBJECT_TYPE))
						+ " "
						+ c.getString(c.getColumnIndex(ObjectTable.ANCESTOR));
			} while (c.moveToNext());
			Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		}
	}

	public void showDicoObject(View view) {
		String URL = "content://com.example.andodab.provider.Andodab/dicoobj";
		Uri roots = Uri.parse(URL);

		Cursor c = getContentResolver().query(roots, null, null, null, "_id");
		String result = "DicoObject results : ";
		if (!c.moveToFirst()) {
			Toast.makeText(this, result + " no content yet", Toast.LENGTH_LONG)
					.show();
		} else {
			do {
				result = result
						+ "\n"
						+ c.getString(c
								.getColumnIndex(DicoObjectTable.COLUMN_ID))
						+ " "
						+ c.getString(c.getColumnIndex(DicoObjectTable.SEALED));
			} while (c.moveToNext());
			Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		}
	}

	public void showPPObject(View view) {
		String URL = "content://com.example.andodab.provider.Andodab/objectpprimitive";
		Uri roots = Uri.parse(URL);

		Cursor c = getContentResolver().query(roots, null, null, null, "_id");
		String result = "ObjectPPrimitive results : ";
		if (!c.moveToFirst()) {
			Toast.makeText(this, result + " no content yet", Toast.LENGTH_LONG)
					.show();
		} else {
			do {
				result = result
						+ "\n"
						+ c.getString(c
								.getColumnIndex(ObjectPPrimitiveTable.COLUMN_ID));
			} while (c.moveToNext());
			Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		}
	}

	public void showPObject(View view) {
		String URL = "content://com.example.andodab.provider.Andodab/objectprimitive";
		Uri roots = Uri.parse(URL);

		Cursor c = getContentResolver().query(roots, null, null, null, "_id");
		String result = "PrimitiveObject results : ";
		if (!c.moveToFirst()) {
			Toast.makeText(this, result + " no content yet", Toast.LENGTH_LONG)
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
			Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		}
	}

	public void showEntry(View view) {
		String URL = "content://com.example.andodab.provider.Andodab/entry";
		Uri roots = Uri.parse(URL);

		Cursor c = getContentResolver().query(roots, null, null, null, "_id");
		String result = "Entry results : ";
		if (!c.moveToFirst()) {
			Toast.makeText(this, result + " no content yet", Toast.LENGTH_LONG)
					.show();
		} else {
			do {
				result = result + "\n"
						+ c.getString(c.getColumnIndex(EntryTable.COLUMN_ID))
						+ " "
						+ c.getString(c.getColumnIndex(EntryTable.ENTRYTYPE))
						+ " " + c.getString(c.getColumnIndex(EntryTable.NAME));
			} while (c.moveToNext());
			Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		}
	}

	public void showOEntry(View view) {
		String URL = "content://com.example.andodab.provider.Andodab/objectentry";
		Uri roots = Uri.parse(URL);

		Cursor c = getContentResolver().query(roots, null, null, null, "_id");
		String result = "ObjectEntry results : ";
		if (!c.moveToFirst()) {
			Toast.makeText(this, result + " no content yet", Toast.LENGTH_LONG)
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
			Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		}
	}

	public void showPEntry(View view) {
		String URL = "content://com.example.andodab.provider.Andodab/primitiveentry";
		Uri roots = Uri.parse(URL);

		Cursor c = getContentResolver().query(roots, null, null, null, "_id");
		String result = "PrimitiveEntry results : ";
		if (!c.moveToFirst()) {
			Toast.makeText(this, result + " no content yet", Toast.LENGTH_LONG)
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
			Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		}
	}

	public void showDOEntry(View view) {
		String URL = "content://com.example.andodab.provider.Andodab/dicoobjentry";
		Uri roots = Uri.parse(URL);

		Cursor c = getContentResolver().query(roots, null, "_idDO = 'Koala'",
				null, "_id");
		String result = "DicoObjectEntry results : ";
		if (!c.moveToFirst()) {
			Toast.makeText(this, result + " no content yet", Toast.LENGTH_LONG)
					.show();
		} else {
			do {
				Cursor cDO = getContentResolver()
						.query(Uri
								.parse("content://com.example.andodab.provider.Andodab/dicoobj"),
								null,
								"_id = '"
										+ c.getString(c
												.getColumnIndex(DicObjectEntryTable.COLUMN_ID2))
										+ "'", null, "_id");

				if (!cDO.moveToFirst()) {
					Toast.makeText(this, result + " no content yet",
							Toast.LENGTH_LONG).show();
				} else {
					Cursor cE = getContentResolver()
							.query(Uri
									.parse("content://com.example.andodab.provider.Andodab/entry"),
									null,
									"_id = "
											+ c.getString(c
													.getColumnIndex(DicObjectEntryTable.COLUMN_ID)),
									null, "_id");

					if (!cE.moveToFirst()) {
						Toast.makeText(this, result + " no content yet",
								Toast.LENGTH_LONG).show();
					} else {
						Cursor detailEntry;
						if (cE.getString(
								cE.getColumnIndex(EntryTable.ENTRYTYPE))
								.toUpperCase().equals("OBJECT")) {
							detailEntry = getContentResolver()
									.query(Uri
											.parse("content://com.example.andodab.provider.Andodab/objectentry"),
											null,
											"_id = "
													+ cE.getString(cE
															.getColumnIndex(EntryTable.COLUMN_ID)),
											null, "_id");
						} else {
							detailEntry = getContentResolver()
									.query(Uri
											.parse("content://com.example.andodab.provider.Andodab/primitiveentry"),
											null,
											"_id = "
													+ cE.getString(cE
															.getColumnIndex(EntryTable.COLUMN_ID)),
											null, "_id");
						}
						if (!detailEntry.moveToFirst()) {
							Toast.makeText(this, result + " no content yet",
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
			Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		}
	}

	// TODO Méthode pour récupérer un objet directement
	public HashMap<String, String> getObject(String objectID) {
		HashMap<String, String> data = new HashMap<String, String>();

		String URL = "content://com.example.andodab.provider.Andodab/object";
		Uri objects = Uri.parse(URL);

		Cursor c = getContentResolver().query(objects, null,
				"_id = '" + objectID + "'", null, "_id");

		if (!c.moveToFirst()) {
			return data;
		} else {
			data.put(ObjectTable.COLUMN_ID,
					c.getString(c.getColumnIndex(ObjectTable.COLUMN_ID)));
			data.put(ObjectTable.OBJECT_TYPE,
					c.getString(c.getColumnIndex(ObjectTable.OBJECT_TYPE)));
			data.put(ObjectTable.ANCESTOR,
					c.getString(c.getColumnIndex(ObjectTable.ANCESTOR)));
		}

		if (data.get(ObjectTable.OBJECT_TYPE).toUpperCase().equals("OBJECT")) {
			URL = "content://com.example.andodab.provider.Andodab/dicoobj";
		} else {
			return data;
		}

		objects = Uri.parse(URL);
		c = getContentResolver().query(objects, null,
				"_id = '" + objectID + "'", null, "_id");
		if (!c.moveToFirst()) {
			return data;
		} else {
			data.put(DicoObjectTable.SEALED,
					c.getString(c.getColumnIndex(DicoObjectTable.SEALED)));
			return data;
		}
	}

	// TODO
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

	// TODO Méthode avec hashmap pour les électriciens
	public HashMap<String, String> getProperties(String objectID) {
		HashMap<String, String> properties = new HashMap<String, String>();
		String URL = "content://com.example.andodab.provider.Andodab/dicoobjentry";
		Uri doe = Uri.parse(URL);

		Cursor c = getContentResolver().query(doe, null,
				"_idDO = '" + objectID + "'", null, "_id");

		if (!c.moveToFirst()) {
			return properties;
		} else {
			do {
				Cursor cE = getContentResolver()
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
						detailEntry = getContentResolver()
								.query(Uri
										.parse("content://com.example.andodab.provider.Andodab/objectentry"),
										null,
										"_id = "
												+ cE.getString(cE
														.getColumnIndex(EntryTable.COLUMN_ID)),
										null, "_id");
					} else {
						detailEntry = getContentResolver()
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

	public void updateID(String type, String id_e, String new_id) {
		String URL = "content://com.example.andodab.provider.Andodab/entry";
		Uri entry = Uri.parse(URL);

		/* Request to get the fields of the entry chosen */
		Cursor c = getContentResolver().query(entry, null,
				"_id = '" + id_e + "'", null, "_id");

		/* Parcours */
		if (!c.moveToFirst()) {
			return;
		} else {
			Cursor insert;
			ContentValues values = new ContentValues();

			// If the type of the new value is the same as the type of the entry
			// already existing
			if (c.getString(c.getColumnIndex(EntryTable.ENTRYTYPE))
					.toUpperCase().equals(type.toUpperCase())) {
				// If the current type of the entry is Object
				if (c.getString(c.getColumnIndex(EntryTable.ENTRYTYPE))
						.toUpperCase().equals("OBJECT")) {
					values.put(ObjectEntryTable.VALUE, new_id);

					getContentResolver()
							.update(Uri
									.parse("content://com.example.andodab.provider.Andodab/objectentry"),
									values,
									"_id = "
											+ c.getString(c
													.getColumnIndex(EntryTable.COLUMN_ID)),
									null);
					return;

				} else { // If the current type of the entry is Primitive
					values.put(PrimitiveEntryTable.VALUE, new_id);
					getContentResolver()
							.update(Uri
									.parse("content://com.example.andodab.provider.Andodab/primitiveentry"),
									values,
									"_id = "
											+ c.getString(c
													.getColumnIndex(EntryTable.COLUMN_ID)),
									null);
					return;
				}
			}
			// If the type of the new value is different from the type of the
			// current entry, we delete, update and insert
			else {
				// If the type of the current entry is Object
				if (c.getString(c.getColumnIndex(EntryTable.ENTRYTYPE))
						.toUpperCase().equals("OBJECT")) {
					// Deleting the old entryvalue in objectentry
					getContentResolver()
							.delete(Uri
									.parse("content://com.example.andodab.provider.Andodab/objectentry"),
									"_id='" + id_e + "'", null);

					// Updating the type of the entry in Entry
					values.put(EntryTable.ENTRYTYPE, type);
					getContentResolver()
							.update(Uri
									.parse("content://com.example.andodab.provider.Andodab/entry"),
									null, "_id='" + id_e + "'", null);

					values.clear();

					// Inserting the new entryvalue in primitiveentry
					values.put(PrimitiveEntryTable.COLUMN_ID, id_e);
					values.put(PrimitiveEntryTable.VALUE, new_id);
					getContentResolver()
							.insert(Uri
									.parse("content://com.example.andodab.provider.Andodab/primitiveentry"),
									values);
					return;

				} else { // If the type of the current entry is primitive
					// Deleting the old value in PrimitiveEntry
					getContentResolver()
							.delete(Uri
									.parse("content://com.example.andodab.provider.Andodab/primitiveentry"),
									"_id='" + id_e + "'", null);

					// Updating the type in Entry
					values.put(EntryTable.ENTRYTYPE, type);
					getContentResolver()
							.update(Uri
									.parse("content://com.example.andodab.provider.Andodab/entry"),
									null, "_id='" + id_e + "'", null);

					values.clear();

					// Inserting the new entryvalue in objectentry
					values.put(ObjectEntryTable.COLUMN_ID, id_e);
					values.put(ObjectEntryTable.VALUE, new_id);
					getContentResolver()
							.insert(Uri
									.parse("content://com.example.andodab.provider.Andodab/objectentry"),
									values);
					return;
				}
			}
		}
	}
}
