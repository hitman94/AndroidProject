package com.example.projectandroid2015.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.util.Log;
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

public class ContentProviderUtil {
	public static final String idroot = "999999999999999999";

	private Context context;

	public ContentProviderUtil(Context cont) {
		context = cont;
	}

	public void addElement(View view) {
		addRoot(view);
		addObject(view);
		addPrimitive(view);
		addEntry(view);
		addDicoE(view);
	}

	public void showElement(View view) {
		Log.e("SQL", "properties" + getProperties(getID("Koala")).toString());
		showObject(view);
		showDicoObject(view);
		showPPObject(view);
		showPObject(view);
		showEntry(view);
		showOEntry(view);
		showPEntry(view);
		showDOEntry(view);
	}

	public void updateIDTest(View view) {
		updateID(getID("Food"), "Primitive",
				getEntryID("energyDensity", "100.0"), "100.0");
		// deleteDOET("2", "Koala");
	}

	public void addRoot(View view) {
		ContentValues values = new ContentValues();
		values.put(ObjectTable.COLUMN_ID, idroot);
		values.put(ObjectTable.OBJECT_TYPE, "Object");
		values.put(DicoObjectTable.SEALED, "false");
		values.put(ObjectTable.NAME, "root");
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_OBJECT, values);

		Toast.makeText(context, "Andodab : root inserted!", Toast.LENGTH_LONG)
				.show();
	}

	public void addObject(View view) {
		ContentValues values = new ContentValues();

		// Dico Objects
		values.put(ObjectTable.NAME, "Food");
		values.put(ObjectTable.OBJECT_TYPE, "Object");
		values.put(DicoObjectTable.SEALED, "false");
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_OBJECT, values);

		values.put(ObjectTable.NAME, "Animal");
		values.put(ObjectTable.OBJECT_TYPE, "Object");
		values.put(DicoObjectTable.SEALED, "false");
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_OBJECT, values);

		values.put(ObjectTable.NAME, "Mammal");
		values.put(ObjectTable.OBJECT_TYPE, "Object");
		values.put(ObjectTable.ANCESTOR, getID("Animal"));
		values.put(DicoObjectTable.SEALED, "false");
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_OBJECT, values);

		values.put(ObjectTable.NAME, "Eucalyptus");
		values.put(ObjectTable.OBJECT_TYPE, "Object");
		values.put(ObjectTable.ANCESTOR, getID("Food"));
		values.put(DicoObjectTable.SEALED, "false");
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_OBJECT, values);

		values.put(ObjectTable.NAME, "Feuille");
		values.put(ObjectTable.OBJECT_TYPE, "ObJeCt");
		values.put(ObjectTable.ANCESTOR, getID("Food"));
		values.put(DicoObjectTable.SEALED, "false");
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_OBJECT, values);

		values.put(ObjectTable.NAME, "Koala");
		values.put(ObjectTable.OBJECT_TYPE, "Object");
		values.put(ObjectTable.ANCESTOR, getID("Animal"));
		values.put(DicoObjectTable.SEALED, "false");
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_OBJECT, values);

		values.clear();

		// PPrimitiveObjects
		values.put(ObjectTable.NAME, "String");
		values.put(ObjectTable.OBJECT_TYPE, "Primitive");
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_OBJECT, values);

		values.put(ObjectTable.NAME, "Float");
		values.put(ObjectTable.OBJECT_TYPE, "Primitive");
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_OBJECT, values);

		values.put(ObjectTable.NAME, "Integer");
		values.put(ObjectTable.OBJECT_TYPE, "Primitive");
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_OBJECT, values);

		Toast.makeText(context, "Andodab : Objects inserted!",
				Toast.LENGTH_LONG).show();
	}

	public void addPrimitive(View view) {
		ContentValues values = new ContentValues();

		values.put(PrimitiveObjectTable.COLUMN_ID, "6723");
		values.put(PrimitiveObjectTable.ANCESTOR, getID("Integer"));
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_OBJECTPRIMITIVE, values);

		values.put(PrimitiveObjectTable.COLUMN_ID, "100.0");
		values.put(PrimitiveObjectTable.ANCESTOR, getID("Float"));
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_OBJECTPRIMITIVE, values);

		values.put(PrimitiveObjectTable.COLUMN_ID, "Fougere");
		values.put(PrimitiveObjectTable.ANCESTOR, getID("String"));
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_OBJECTPRIMITIVE, values);

		values.clear();
		values.put(PrimitiveObjectTable.COLUMN_ID, "Banane");
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_OBJECTPRIMITIVE, values);

		Toast.makeText(context, "Andodab : Primitive Objects inserted!",
				Toast.LENGTH_LONG).show();
	}

	public void addEntry(View view) {
		ContentValues values = new ContentValues();

		values.put(EntryTable.NAME, "energyDensity");
		values.put(EntryTable.ENTRYTYPE, "Object");
		values.put(ObjectEntryTable.VALUE, getID("Float"));
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_ENTRY, values);

		values.put(EntryTable.NAME, "food");
		values.put(EntryTable.ENTRYTYPE, "Object");
		values.put(ObjectEntryTable.VALUE, getID("Food"));
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_ENTRY, values);

		values.put(EntryTable.NAME, "food");
		values.put(EntryTable.ENTRYTYPE, "Object");
		values.put(ObjectEntryTable.VALUE, getID("Eucalyptus"));
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_ENTRY, values);

		// PrimitiveEntry
		values.put(EntryTable.NAME, "energyDensity");
		values.put(EntryTable.ENTRYTYPE, "Primitive");
		values.put(PrimitiveEntryTable.VALUE, "100.0");
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_ENTRY, values);

		Toast.makeText(context, "Andodab : Entry inserted!", Toast.LENGTH_LONG)
				.show();
	}

	public void addDicoE(View view) {
		ContentValues values = new ContentValues();

		values.put(DicObjectEntryTable.COLUMN_ID,
				getEntryID("energyDensity", "100.0"));
		values.put(DicObjectEntryTable.COLUMN_ID2, getID("Food"));
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_DICOOBJENTRY, values);

		values.put(DicObjectEntryTable.COLUMN_ID,
				getEntryID("food", getID("Food")));
		values.put(DicObjectEntryTable.COLUMN_ID2, getID("Animal"));
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_DICOOBJENTRY, values);

		values.put(DicObjectEntryTable.COLUMN_ID,
				getEntryID("food", getID("Eucalyptus")));
		values.put(DicObjectEntryTable.COLUMN_ID2, getID("Eucalyptus"));
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_DICOOBJENTRY, values);

		values.put(DicObjectEntryTable.COLUMN_ID,
				getEntryID("energyDensity", getID("Float")));
		values.put(DicObjectEntryTable.COLUMN_ID2, getID("Koala"));
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_DICOOBJENTRY, values);

		values.put(DicObjectEntryTable.COLUMN_ID,
				getEntryID("food", getID("Eucalyptus")));
		values.put(DicObjectEntryTable.COLUMN_ID2, getID("Koala"));
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_DICOOBJENTRY, values);

		values.put(DicObjectEntryTable.COLUMN_ID,
				getEntryID("food", getID("Food")));
		values.put(DicObjectEntryTable.COLUMN_ID2, getID("Mammal"));
		context.getContentResolver().insert(
				AndodabContentProvider.CONTENT_URI_DICOOBJENTRY, values);

		Toast.makeText(context, "Andodab : DicoObjectEntry inserted!",
				Toast.LENGTH_LONG).show();
	}

	public void showObject(View view) {

		Cursor c = context.getContentResolver().query(
				AndodabContentProvider.CONTENT_URI_OBJECT, null, null, null,
				"_id");
		String result = "Object results : ";
		if (!c.moveToFirst()) {
			Toast.makeText(context, result + " no content yet",
					Toast.LENGTH_LONG).show();
		} else {
			do {
				result = result
						+ "\n"
						+ c.getString(c.getColumnIndex(ObjectTable.COLUMN_ID))
						+ " "
						+ c.getString(c.getColumnIndex(ObjectTable.OBJECT_TYPE))
						+ " "
						+ c.getString(c.getColumnIndex(ObjectTable.ANCESTOR))
						+ " " + c.getString(c.getColumnIndex(ObjectTable.NAME))
						+ " "
						+ c.getString(c.getColumnIndex(ObjectTable.TIMESTAMP));
			} while (c.moveToNext());
			Log.e("SQL", result);
			Toast.makeText(context, result, Toast.LENGTH_LONG).show();
		}
	}

	public void showDicoObject(View view) {

		Cursor c = context.getContentResolver().query(
				AndodabContentProvider.CONTENT_URI_DICOOBJ, null, null, null,
				"_id");
		String result = "DicoObject results : ";
		if (!c.moveToFirst()) {
			Toast.makeText(context, result + " no content yet",
					Toast.LENGTH_LONG).show();
		} else {
			do {
				result = result
						+ "\n"
						+ c.getString(c
								.getColumnIndex(DicoObjectTable.COLUMN_ID))
						+ " "
						+ c.getString(c.getColumnIndex(DicoObjectTable.SEALED));
			} while (c.moveToNext());
			Toast.makeText(context, result, Toast.LENGTH_LONG).show();
		}
	}

	public void showPPObject(View view) {

		Cursor c = context.getContentResolver().query(
				AndodabContentProvider.CONTENT_URI_OBJECTPPRIMITIVE, null,
				null, null, "_id");
		String result = "ObjectPPrimitive results : ";
		if (!c.moveToFirst()) {
			Toast.makeText(context, result + " no content yet",
					Toast.LENGTH_LONG).show();
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

		Cursor c = context.getContentResolver().query(
				AndodabContentProvider.CONTENT_URI_OBJECTPRIMITIVE, null, null,
				null, "_id");
		String result = "PrimitiveObject results : ";
		if (!c.moveToFirst()) {
			Toast.makeText(context, result + " no content yet",
					Toast.LENGTH_LONG).show();
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

		Cursor c = context.getContentResolver().query(
				AndodabContentProvider.CONTENT_URI_ENTRY, null, null, null,
				"_id");
		String result = "Entry results : ";
		if (!c.moveToFirst()) {
			Toast.makeText(context, result + " no content yet",
					Toast.LENGTH_LONG).show();
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

		Cursor c = context.getContentResolver().query(
				AndodabContentProvider.CONTENT_URI_OBJECTENTRY, null, null,
				null, "_id");
		String result = "ObjectEntry results : ";
		if (!c.moveToFirst()) {
			Toast.makeText(context, result + " no content yet",
					Toast.LENGTH_LONG).show();
		} else {
			do {
				result = result
						+ "\n"
						+ c.getString(c
								.getColumnIndex(ObjectEntryTable.COLUMN_ID))
						+ " "
						+ getName(c.getString(c
								.getColumnIndex(ObjectEntryTable.VALUE)));
			} while (c.moveToNext());
			Toast.makeText(context, result, Toast.LENGTH_LONG).show();
		}
	}

	public void showPEntry(View view) {

		Cursor c = context.getContentResolver().query(
				AndodabContentProvider.CONTENT_URI_PRIMITIVEENTRY, null, null,
				null, "_id");
		String result = "PrimitiveEntry results : ";
		if (!c.moveToFirst()) {
			Toast.makeText(context, result + " no content yet",
					Toast.LENGTH_LONG).show();
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
		Cursor c = context.getContentResolver().query(
				AndodabContentProvider.CONTENT_URI_DICOOBJENTRY, null, null,
				null, null);
		Cursor forentrytype;
		Cursor forvalueentry;

		if (!c.moveToFirst()) {
			return;
		} else {
			do {
				forentrytype = context
						.getContentResolver()
						.query(AndodabContentProvider.CONTENT_URI_ENTRY,
								null,
								EntryTable.COLUMN_ID
										+ " = "
										+ c.getString(c
												.getColumnIndex(DicObjectEntryTable.COLUMN_ID)),
								null, null);

				if (!forentrytype.moveToFirst()) {
					continue;
				} else {
					if (forentrytype
							.getString(
									forentrytype
											.getColumnIndex(EntryTable.ENTRYTYPE))
							.toUpperCase().equals("OBJECT")) {
						forvalueentry = context
								.getContentResolver()
								.query(AndodabContentProvider.CONTENT_URI_OBJECTENTRY,
										null,
										ObjectEntryTable.COLUMN_ID
												+ " = "
												+ c.getString(c
														.getColumnIndex(DicObjectEntryTable.COLUMN_ID)),
										null, null);
						if (!forvalueentry.moveToFirst()) {
							continue;
						} else {
							Log.e("SQL",
									getName(c.getString(c
											.getColumnIndex(DicObjectEntryTable.COLUMN_ID2)))
											+ " {"
											+ getEntryName(c.getString(c
													.getColumnIndex(DicObjectEntryTable.COLUMN_ID)))
											+ " : "
											+ getName(forvalueentry.getString(forvalueentry
													.getColumnIndex(ObjectEntryTable.VALUE)))
											+ "}");
						}
					} else {
						forvalueentry = context
								.getContentResolver()
								.query(AndodabContentProvider.CONTENT_URI_PRIMITIVEENTRY,
										null,
										PrimitiveEntryTable.COLUMN_ID
												+ " = "
												+ c.getString(c
														.getColumnIndex(DicObjectEntryTable.COLUMN_ID)),
										null, null);
						if (!forvalueentry.moveToFirst()) {
							continue;
						} else {
							Log.e("SQL",
									getName(c.getString(c
											.getColumnIndex(DicObjectEntryTable.COLUMN_ID2)))
											+ " {"
											+ getEntryName(c.getString(c
													.getColumnIndex(DicObjectEntryTable.COLUMN_ID)))
											+ " : "
											+ forvalueentry.getString(forvalueentry
													.getColumnIndex(ObjectEntryTable.VALUE))
											+ "}");
						}
					}
				}
			} while (c.moveToNext());
		}
	}

	// TODO Méthode pour récupérer un objet directement
	public HashMap<String, String> getObject(String objectID) {
		HashMap<String, String> data = new HashMap<String, String>();

		String URL = "content://com.example.andodab.provider.Andodab/object";
		Uri objects = Uri.parse(URL);

		Cursor c = context.getContentResolver().query(
				AndodabContentProvider.CONTENT_URI_OBJECT, null,
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
		c = context.getContentResolver().query(objects, null,
				"_id = '" + objectID + "'", null, "_id");
		if (!c.moveToFirst()) {
			return data;
		} else {
			data.put(DicoObjectTable.SEALED,
					c.getString(c.getColumnIndex(DicoObjectTable.SEALED)));
			return data;
		}
	}

	public ArrayList<String> getChildren(String objectID) {
		ArrayList<String> children = new ArrayList<String>();
		Cursor c = context.getContentResolver().query(
				AndodabContentProvider.CONTENT_URI_OBJECT, null,
				"ancestor = '" + objectID + "'", null, "_id");
		if (!c.moveToFirst()) {
			return null;
		} else {
			do {
				children.add(c.getString(c
						.getColumnIndex(ObjectTable.COLUMN_ID)));

			} while (c.moveToNext());
		}
		return children;
	}

	// TODO Méthode avec hashmap pour les électriciens
	public HashMap<String, String> getProperties(String objectID) {
		HashMap<String, String> properties = new HashMap<String, String>();

		Cursor c = context.getContentResolver().query(
				AndodabContentProvider.CONTENT_URI_DICOOBJENTRY, null,
				"_idDO = '" + objectID + "'", null, "_id");

		if (!c.moveToFirst()) {
			return properties;
		} else {
			do {
				Cursor cE = context
						.getContentResolver()
						.query(AndodabContentProvider.CONTENT_URI_ENTRY,
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
						detailEntry = context
								.getContentResolver()
								.query(AndodabContentProvider.CONTENT_URI_OBJECTENTRY,
										null,
										"_id = "
												+ cE.getString(cE
														.getColumnIndex(EntryTable.COLUMN_ID)),
										null, "_id");
					} else {
						detailEntry = context
								.getContentResolver()
								.query(AndodabContentProvider.CONTENT_URI_PRIMITIVEENTRY,
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

	public boolean entryExist(String id_entry, String new_value) {
		String URL = "content://com.example.andodab.provider.Andodab/entry";
		Uri entry = Uri.parse(URL);
		// Recupere toute la table entry
		Cursor c = context.getContentResolver().query(entry, null, null, null,
				null);
		if (!c.moveToFirst()) {

		} else {
			Cursor request = context.getContentResolver().query(
					AndodabContentProvider.CONTENT_URI_ENTRY, null,
					"_id='" + id_entry + "'", null, null);
			request.moveToFirst();
			String name = request.getString(request
					.getColumnIndex(EntryTable.NAME));
			// parcours de la table entry pour chercher une ligne avec le meme
			// name
			do {
				if (name.equals(c.getString(c.getColumnIndex(EntryTable.NAME)))) {
					// test pour definir le type et savoir dans quelle table
					// chercher la valeur
					if (c.getString(c.getColumnIndex(EntryTable.ENTRYTYPE))
							.toUpperCase().equals("OBJECT")) {
						request = context.getContentResolver().query(
								AndodabContentProvider.CONTENT_URI_OBJECTENTRY,
								null, "_id='" + id_entry + "'", null, null);
						if (!request.moveToFirst()) {

							continue;
						}
						if (request
								.getString(
										request.getColumnIndex(ObjectEntryTable.VALUE))
								.toUpperCase().equals(new_value.toUpperCase())) {
							Toast.makeText(context, "Properties already exist",
									Toast.LENGTH_LONG).show();
							return false;
						}
					} else {
						request = context
								.getContentResolver()
								.query(Uri
										.parse("content://com.example.andodab.provider.Andodab/primitiveentry"),
										null, "_id='" + id_entry + "'", null,
										null);
						if (!request.moveToFirst()) {
							continue;
						}
						if (request
								.getString(
										request.getColumnIndex(PrimitiveEntryTable.VALUE))
								.toUpperCase().equals(new_value.toUpperCase())) {
							Toast.makeText(context, "Properties already exist",
									Toast.LENGTH_LONG).show();
							return false;
						}
					}
				}

			} while (c.moveToNext());
			Toast.makeText(context, "OK", Toast.LENGTH_LONG).show();
		}
		return true;
	}

	// TODO Update DOET
	public void updateID(String idObject, String type, String id_entry,
			String new_value) {
		try {
			ContentValues values = new ContentValues();
			ContentValues contentValues = new ContentValues();

			if (!entryExist(id_entry, new_value)) {
				return;
			}
			// Getting the right entry
			Cursor c = context.getContentResolver().query(
					AndodabContentProvider.CONTENT_URI_ENTRY, null,
					"_id = '" + id_entry + "'", null, "_id");

			// Getting the properties
			Cursor numberof_id_entry = context.getContentResolver().query(
					AndodabContentProvider.CONTENT_URI_DICOOBJENTRY, null,
					"_id='" + id_entry + "'", null, null);
			numberof_id_entry.moveToFirst();

			// If the entry is used by more than one object
			if (numberof_id_entry.getCount() != 1) {

				// Creation of a new entry for the object, so getting the
				// already existing entries
				numberof_id_entry = context.getContentResolver().query(
						AndodabContentProvider.CONTENT_URI_ENTRY, null, null,
						null, null);
				numberof_id_entry.moveToLast();

				// Getting the id and the type, which is chosen, of the new
				// entry
				int p = numberof_id_entry.getInt(numberof_id_entry
						.getColumnIndex(EntryTable.COLUMN_ID)) + 1;
				values.put(EntryTable.COLUMN_ID, p);
				values.put(EntryTable.ENTRYTYPE, type);

				// Getting the name of the entry, the same as before
				c.moveToFirst();
				values.put(EntryTable.NAME,
						c.getString(c.getColumnIndex(EntryTable.NAME)));

				// Setting the new value, depending on the type of the object
				if (type.toUpperCase().equals("OBJECT")) {
					values.put(ObjectEntryTable.VALUE, new_value);
				} else {
					values.put(PrimitiveEntryTable.VALUE, new_value);
				}

				// Inserting in the table
				context.getContentResolver().insert(
						AndodabContentProvider.CONTENT_URI_ENTRY, values);

				// Deleting the property
				context.getContentResolver().delete(
						AndodabContentProvider.CONTENT_URI_DICOOBJENTRY,
						"_id='" + id_entry + "' and _iddo='" + idObject + "'",
						null);
				values.clear();

				// Creating the new property
				values.put(DicObjectEntryTable.COLUMN_ID, String.valueOf(p));
				values.put(DicObjectEntryTable.COLUMN_ID2, idObject);
				context.getContentResolver()
						.insert(AndodabContentProvider.CONTENT_URI_DICOOBJENTRY,
								values);
			} else { // If there is only one object using context entry
				if (!c.moveToFirst()) {
					return;
				} else {
					// Updating the value if the type of the new value is the
					// same as the current one
					if (c.getString(c.getColumnIndex(EntryTable.ENTRYTYPE))
							.toUpperCase().equals(type.toUpperCase())) {

						contentValues = new ContentValues();
						contentValues.put(ObjectTable.TIMESTAMP,
								new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.format(new Date()));
						context.getContentResolver().update(
								AndodabContentProvider.CONTENT_URI_OBJECT,
								contentValues, "_id = " + idObject, null);

						// Putting the right value, depending on the type
						if (c.getString(c.getColumnIndex(EntryTable.ENTRYTYPE))
								.toUpperCase().equals("OBJECT")) {
							values.put(ObjectEntryTable.VALUE, new_value);
							context.getContentResolver()
									.update(AndodabContentProvider.CONTENT_URI_OBJECTENTRY,
											values,
											"_id = "
													+ c.getString(c
															.getColumnIndex(EntryTable.COLUMN_ID)),
											null);
							return;

						} else {
							values.put(PrimitiveEntryTable.VALUE, new_value);
							context.getContentResolver()
									.update(AndodabContentProvider.CONTENT_URI_PRIMITIVEENTRY,
											values,
											"_id = "
													+ c.getString(c
															.getColumnIndex(EntryTable.COLUMN_ID)),
											null);
							return;
						}
					}
					// the type of the new value is not the same than the
					// current one
					else {
						contentValues = new ContentValues();
						contentValues.put(ObjectTable.TIMESTAMP,
								new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.format(new Date()));
						context.getContentResolver().update(
								AndodabContentProvider.CONTENT_URI_OBJECT,
								contentValues, "_id = " + idObject, null);

						// Deleting the current entry in the current table
						if (c.getString(c.getColumnIndex(EntryTable.ENTRYTYPE))
								.toUpperCase().equals("OBJECT")) {

							context.getContentResolver()
									.delete(AndodabContentProvider.CONTENT_URI_OBJECTENTRY,
											"_id='" + id_entry + "'", null);

							// Updating the type
							values.put(EntryTable.ENTRYTYPE, type);
							context.getContentResolver().update(
									AndodabContentProvider.CONTENT_URI_ENTRY,
									values, "_id='" + id_entry + "'", null);
							values.clear();

							// Creation in the new table
							values.put(PrimitiveEntryTable.COLUMN_ID, id_entry);
							values.put(PrimitiveEntryTable.VALUE, new_value);
							context.getContentResolver()
									.insert(AndodabContentProvider.CONTENT_URI_PRIMITIVEENTRY,
											values);
							return;

						} else {
							// Deleting the current entry in the current table
							context.getContentResolver()
									.delete(AndodabContentProvider.CONTENT_URI_PRIMITIVEENTRY,
											"_id='" + id_entry + "'", null);

							// Updating the type
							values.put(EntryTable.ENTRYTYPE, type);
							context.getContentResolver().update(
									AndodabContentProvider.CONTENT_URI_ENTRY,
									values, "_id='" + id_entry + "'", null);
							values.clear();

							// Creation in the new table
							values.put(ObjectEntryTable.COLUMN_ID, id_entry);
							values.put(ObjectEntryTable.VALUE, new_value);
							context.getContentResolver()
									.insert(AndodabContentProvider.CONTENT_URI_OBJECTENTRY,
											values);
							return;
						}
					}
				}
			}
		} catch (SQLiteConstraintException constraintException) {
			Toast.makeText(context, "Bad value", Toast.LENGTH_LONG).show();
		}
	}

	public void deleteDOET(String id, String iddo) {
		context.getContentResolver().delete(
				AndodabContentProvider.CONTENT_URI_DICOOBJENTRY,
				"_id='" + id + "' and _iddo='" + iddo + "'", null);
	}

	// TODO Delete an object
	public void deleteObject(String object) {
		// Deleting all the entries with the value "object" from the table entry
		// & objectentry

		Cursor c = context.getContentResolver().query(
				AndodabContentProvider.CONTENT_URI_OBJECTENTRY, null,
				ObjectEntryTable.VALUE + " = '" + object + "'", null, "_id");

		if (!c.moveToFirst()) {
			return;
		} else {
			do {
				context.getContentResolver()
						.delete(AndodabContentProvider.CONTENT_URI_DICOOBJENTRY,
								DicObjectEntryTable.COLUMN_ID
										+ " = "
										+ c.getString(c
												.getColumnIndex(ObjectEntryTable.COLUMN_ID)),
								null);
				context.getContentResolver()
						.delete(AndodabContentProvider.CONTENT_URI_OBJECTENTRY,
								ObjectEntryTable.COLUMN_ID
										+ " = "
										+ c.getString(c
												.getColumnIndex(ObjectEntryTable.COLUMN_ID)),
								null);
				context.getContentResolver()
						.delete(AndodabContentProvider.CONTENT_URI_ENTRY,
								EntryTable.COLUMN_ID
										+ " = "
										+ c.getString(c
												.getColumnIndex(ObjectEntryTable.COLUMN_ID)),
								null);
			} while (c.moveToNext());
		}
		c.close();

		// Deleting all the properties of the object to delete in DOET
		context.getContentResolver().delete(
				AndodabContentProvider.CONTENT_URI_DICOOBJENTRY,
				DicObjectEntryTable.COLUMN_ID2 + " = '" + object + "'", null);

		// Getting the ancestor of the object to delete
		c = context.getContentResolver().query(
				AndodabContentProvider.CONTENT_URI_OBJECT, null,
				ObjectTable.COLUMN_ID + " = '" + object + "'", null, "_id");
		String ancestor = null;
		if (!c.moveToFirst()) {
			return;
		} else {
			ancestor = c.getString(c.getColumnIndex(ObjectTable.ANCESTOR));
		}
		c.close();

		// Getting all the objects which have the object to delete as their
		// ancestor
		c = context.getContentResolver().query(
				AndodabContentProvider.CONTENT_URI_OBJECT, null,
				ObjectTable.ANCESTOR + " = '" + object + "'", null, "_id");

		// For each child of the object to delete update the ancestor to the
		// ancestor of the object to delete
		if (!c.moveToFirst()) {
			return;
		} else {
			do {
				ContentValues contentValues = new ContentValues();
				contentValues.put(ObjectTable.ANCESTOR, ancestor);
				context.getContentResolver().update(
						AndodabContentProvider.CONTENT_URI_OBJECT,
						contentValues,
						ObjectTable.COLUMN_ID
								+ " = '"
								+ c.getString(c
										.getColumnIndex(ObjectTable.COLUMN_ID))
								+ "'", null);
			} while (c.moveToNext());
		}
		c.close();

		// Deleting the object from the table DOT
		context.getContentResolver().delete(
				AndodabContentProvider.CONTENT_URI_DICOOBJ,
				DicoObjectTable.COLUMN_ID + " = '" + object + "'", null);

		// Deleting the object from the table OT
		context.getContentResolver().delete(
				AndodabContentProvider.CONTENT_URI_OBJECT,
				ObjectTable.COLUMN_ID + " = '" + object + "'", null);
	}

	public String getID(String objectname) {
		Cursor c = context.getContentResolver().query(
				AndodabContentProvider.CONTENT_URI_OBJECT, null,
				ObjectTable.NAME + " = '" + objectname + "'", null,
				ObjectTable.COLUMN_ID);
		c.moveToFirst();
		return c.getString(c.getColumnIndex(ObjectTable.COLUMN_ID));
	}

	public String getEntryID(String propertyname, String idvalue) {
		String id = null;
		String query = null;

		Cursor c = context.getContentResolver().query(
				AndodabContentProvider.CONTENT_URI_ENTRY, null,
				EntryTable.NAME + " = '" + propertyname + "'", null,
				EntryTable.COLUMN_ID);
		c.moveToFirst();
		Cursor res = null;

		do {
			if (c.getString(c.getColumnIndex(EntryTable.ENTRYTYPE)).equals(
					"Object")) {
				res = context.getContentResolver().query(
						AndodabContentProvider.CONTENT_URI_OBJECTENTRY, null,
						ObjectEntryTable.VALUE + " = '" + idvalue + "'", null,
						null);
			} else {
				res = context.getContentResolver().query(
						AndodabContentProvider.CONTENT_URI_PRIMITIVEENTRY,
						null, ObjectEntryTable.VALUE + " = '" + idvalue + "'",
						null, null);
			}
			if (!res.moveToFirst()) {
				continue;
			} else {
				break;
			}
		} while (c.moveToNext());

		return res.getString(res.getColumnIndex(EntryTable.COLUMN_ID));
	}

	public String getName(String idobject) {
		Cursor c = context.getContentResolver().query(
				AndodabContentProvider.CONTENT_URI_OBJECT, null,
				ObjectTable.COLUMN_ID + " = '" + idobject + "'", null,
				ObjectTable.NAME);
		c.moveToFirst();
		return c.getString(c.getColumnIndex(ObjectTable.NAME));
	}

	public String getEntryName(String propertyID) {
		Cursor c = context.getContentResolver().query(
				AndodabContentProvider.CONTENT_URI_ENTRY, null,
				EntryTable.COLUMN_ID + " = " + propertyID, null,
				EntryTable.NAME);
		c.moveToFirst();
		return c.getString(c.getColumnIndex(EntryTable.NAME));
	}

	public HashMap<String, String> getParentProperties(String objectID) {
		HashMap<String, String> properties = new HashMap<String, String>();
		HashMap<String, String> tmpProp;
		HashMap<String, String> map = getObject(objectID);
		String ancestor = map.get(ObjectTable.ANCESTOR);
		while (ancestor != null) {
			map = getObject(ancestor);
			tmpProp = getProperties(ancestor);
			for (Map.Entry<String, String> entry : tmpProp.entrySet()) {
				if (!properties.containsKey(entry.getKey())) {
					properties.put(entry.getKey(), entry.getValue());
				}
			}
			ancestor = map.get(ObjectTable.ANCESTOR);
		}
		return properties;
	}
}
