package com.example.androidproject.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.example.androidproject.R;
import com.example.androidproject.adapters.PropertiesAdaptaters;
import com.example.androidproject.database.AndodabContentProvider;
import com.example.projectandroid2015.util.ContentProviderUtil;
import com.example.projetandroid2015.tables.DicoObjectTable;
import com.example.projetandroid2015.tables.EntryTable;
import com.example.projetandroid2015.tables.ObjectEntryTable;
import com.example.projetandroid2015.tables.ObjectTable;
import org.w3c.dom.Text;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Florian on 01/03/2015.
 */
public class EditObjectActivity extends Activity {

    private String idParent;
    private String idObjet;
    private Context mContext;
    private ContentProviderUtil util;
    private AlertDialog dial;
    private ArrayList<String> parentProperties;
    private ArrayList<String> properties;
    private PropertiesAdaptaters adaptaters;
    private HashMap<String,String> objetParent;
    private HashMap<String,String> objet;
    private String lastId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        setContentView(R.layout.edit_object_layout);
        Intent i = getIntent();
        util = new ContentProviderUtil(this);
        parentProperties = new  ArrayList<String>();
        properties = new  ArrayList<String>();



        ListView lv = (ListView) findViewById(R.id.listView);
        TextView nameView = (TextView) findViewById(R.id.objName);
        TextView inheriteNameView = (TextView) findViewById(R.id.InheritsName);


        if(savedInstanceState !=null){
            idObjet = savedInstanceState.getString("idObjet");
        }else {
            idObjet = i.getStringExtra("idObjet");
        }

        if(idObjet != null) {
            properties = util.getPropertiesId(idObjet);
            parentProperties = util.getParentProperties(idObjet);

            objet = util.getObject(idObjet);
            idParent = objet.get(ObjectTable.ANCESTOR);
            objetParent = util.getObject(idParent);



            String nameObjet = objet.get(ObjectTable.NAME);
            nameView.setText(nameObjet);
            inheriteNameView.setText(objetParent.get(ObjectTable.NAME));

            adaptaters = new PropertiesAdaptaters(this, properties);
            lv.setAdapter(adaptaters);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String idEntry = adaptaters.getItem(position);
                    createDialog(idEntry,null);

                }
            });
            //Logique button Switch SEALED
            Switch buttonSwitch =  (Switch) findViewById(R.id.switch1);
            TextView trueByInheritance = (TextView) findViewById(R.id.sealedHeritage);
            String sealedParent = objetParent.get(DicoObjectTable.SEALED);
            String sealed = objet.get(DicoObjectTable.SEALED);
            if((sealedParent != null && sealedParent.equals("true"))){
                buttonSwitch.setVisibility(View.GONE);
                buttonSwitch.setChecked(true);
                trueByInheritance.setVisibility(View.VISIBLE);
            }else if( sealed.equals("true")){
                buttonSwitch.setChecked(true);
                trueByInheritance.setVisibility(View.GONE);
            }else {
                buttonSwitch.setChecked(false);
                trueByInheritance.setVisibility(View.GONE);
            }
        }else{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Error");
            dialog.setMessage("No object id").setPositiveButton("ok",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            dialog.create().show();
        }




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToSelectParentProperty(View view){
        Intent intent = new Intent(this,ChooseParentPropertyActivity.class);
        Switch buttonSwitch =  (Switch) findViewById(R.id.switch1);
        String sealed = objet.get(DicoObjectTable.SEALED);
        if(buttonSwitch.isChecked())
            intent.putExtra("sealed",true);
        intent.putExtra("id",idObjet);
        intent.putExtra("parents",parentProperties);
        startActivityForResult(intent, 777);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("idObjet",idObjet);
        outState.putString("idParent",idParent);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 777){
            if(resultCode == RESULT_OK){
                String idEntry = data.getStringExtra("idProperty");
                Map<String,String> entry = util.getEntry(idEntry);
                    properties.add(idEntry);
                    adaptaters.notifyDataSetChanged();
                }

        }else if(requestCode==666){
            if(resultCode == RESULT_OK){
                String idEntry = data.getStringExtra("id");
                createDialog(lastId,idEntry);
            }
        }
    }

    private void createDialog(final String idEntry, final String chooseID){

           AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setView(this.getLayoutInflater().inflate(R.layout.dialog_edit_property, null));
            dialog.setTitle(getString(R.string.edit_property_dialog_title));
            final HashMap<String,String> entry = util.getEntry(idEntry);
            dialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            final AlertDialog dial = dialog.create();
            dial.show();
            Button b = (Button)dial.findViewById(R.id.button_dialog);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),Galaxy.class);
                    HashMap<String,String> objValue = util.getObject(entry.get(ObjectEntryTable.VALUE));
                    if(isRedefine(idEntry)){
                        if (objValue.get(ObjectTable.NAME).equals("root")) {
                            intent.putExtra("rootId", objValue.get(ObjectTable.COLUMN_ID));

                        } else {
                            intent.putExtra("rootId", objValue.get(ObjectTable.ANCESTOR).toString());
                        }
                    }
                    lastId = idEntry;
                    startActivityForResult(intent, 666);
                    dial.dismiss();
                }
            });
            Button bPositive = dial.getButton(DialogInterface.BUTTON_POSITIVE);


            final TextView propNameView = (TextView )dial.findViewById(R.id.propName);
            final TextView propValueEditView = (TextView )dial.findViewById(R.id.propValue);
            final TextView propValueView = (TextView )dial.findViewById(R.id.propValueText);
            String valueName = util.getObject(entry.get(ObjectEntryTable.VALUE)).get(ObjectTable.NAME);

        propNameView.setText(entry.get(EntryTable.NAME));

            if(!entry.get(EntryTable.ENTRYTYPE).equalsIgnoreCase("Object")) {
                propValueEditView.setVisibility(View.VISIBLE);
                b.setVisibility(View.GONE);
                propValueView.setVisibility(View.GONE);

            }else {
                propValueEditView.setVisibility(View.GONE);
                propValueView.setText(valueName);
                b.setVisibility(View.VISIBLE);
                propValueView.setVisibility(View.VISIBLE);
                if(chooseID!=null){
                    propValueView.setText(util.getObject(chooseID).get(ObjectTable.NAME));
                }
            }







            //TODO ça bug si on edit une prop qu'on vien d'edité
            bPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        if(entry.get(EntryTable.ENTRYTYPE).equalsIgnoreCase("Object")){
                               if(propValueView.getText() == null || propNameView.getText().toString().isEmpty()) {

                                   Toast.makeText(mContext, R.string.toastValueField, Toast.LENGTH_LONG).show();
                               }
                            util.updateID(idObjet,entry.get(EntryTable.ENTRYTYPE),idEntry,chooseID);
                        }else {
                            if(propValueEditView.getText() == null || propNameView.getText().toString().isEmpty()) {
                                Toast.makeText(mContext, R.string.toastValueField, Toast.LENGTH_LONG).show();
                            }
                            util.deleteDOET(idEntry,idObjet);
                            String id = util.addPrimitiveProperty(idObjet,propNameView.getText().toString(),propValueEditView.getText().toString());
                            properties.add(id);
                            properties.remove(idEntry);
                        }
                    adaptaters.notifyDataSetChanged();
                    dial.dismiss();
                    }

            });
    }


    public void saveObject(View view){
        TextView tvName = (TextView) findViewById(R.id.objName);
        Switch buttonSwitch =  (Switch) findViewById(R.id.switch1);
        ContentValues values = new ContentValues();
        ContentValues valuesSeald = new ContentValues();
        if(buttonSwitch.isChecked()){

            valuesSeald.put(DicoObjectTable.SEALED, "true");
        }else {
            valuesSeald.put(DicoObjectTable.SEALED, "false");
        }
        values.put(ObjectTable.NAME,tvName.getText().toString());
        //TODO sealed truc

        this.getContentResolver()
                .update(AndodabContentProvider.CONTENT_URI_OBJECT,
                        values,"_id = "+ idObjet,null);
        this.getContentResolver().update(AndodabContentProvider.CONTENT_URI_DICOOBJ,valuesSeald,"_id = "+ idObjet,null);

        Toast.makeText(this,"Success",Toast.LENGTH_LONG).show();
    }

    public boolean isRedefine(String idEntry) {
        HashMap<String,String> entry = util.getEntry(idEntry);
        String entryName = entry.get(EntryTable.NAME);
        for(String id : parentProperties) {
            HashMap<String,String> tmp = util.getEntry(id);
            if(entryName.equals(tmp.get(EntryTable.NAME))) {
                return true;
            }
        }
        return false;
    }

    public void delete(View view){
        util.deleteObject(idObjet);
        finish();
    }

}