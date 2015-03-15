package com.example.androidproject.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
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
import com.example.projectandroid2015.util.ContentProviderUtil;
import com.example.projetandroid2015.tables.EntryTable;
import com.example.projetandroid2015.tables.ObjectEntryTable;
import com.example.projetandroid2015.tables.ObjectTable;

/**
 * Created by Florian on 04/03/2015.
 */
public class ChooseParentPropertyActivity extends Activity implements SearchView.OnQueryTextListener {

    private ArrayList<String> properties;
    private PropertiesAdaptaters adaptaters;
    private Context mContext;
    private AlertDialog dial;
    private  String idObjet;
    private String nameClic;
    private ContentProviderUtil util;
    private Boolean sealed;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        util = new ContentProviderUtil(this);
        Intent i =   getIntent();
        idObjet = i.getStringExtra("id");
        sealed = i.getBooleanExtra("sealed",false);

        properties = (ArrayList<String>) i.getSerializableExtra("parents");

        setContentView(R.layout.choose_parent_property);
        ListView lv = (ListView) findViewById(R.id.listproperties);
        adaptaters = new PropertiesAdaptaters(this,properties);
        lv.setAdapter(adaptaters);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idProp = adaptaters.getItem(position);
                Map<String,String> entry = util.getEntry(idProp);
                String valueName = util.getObject(entry.get(ObjectEntryTable.VALUE)).get(ObjectTable.NAME);
                if(!entry.get(EntryTable.ENTRYTYPE).equals("Object")){

                }else {
                    if (valueName.equals("String") || valueName.equals("Integer") || valueName.equals("Float")) {
                        //TODO alertbox puis création de la prop avec type primitive
                        System.out.println("test");
                    } else {
                        nameClic = entry.get(EntryTable.NAME);
                        Toast.makeText(mContext, "Choose new value", Toast.LENGTH_LONG).show();

                        Intent i = new Intent(mContext, Galaxy.class);
                        HashMap<String, String> objValue = util.getObject(entry.get(ObjectEntryTable.VALUE));
                        if (objValue.get(ObjectTable.NAME).equals("root")) {
                            i.putExtra("idRoot", objValue.get(ObjectTable.COLUMN_ID));

                        } else {
                            i.putExtra("idRoot", objValue.get(ObjectTable.ANCESTOR).toString());
                        }
                        startActivityForResult(i, 555);
                    }
                }
            }
        });
        if(sealed) {
            Button newPropButton = (Button) findViewById(R.id.button);
            newPropButton.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==666){
            if(resultCode==RESULT_OK){

                    newPropertyDialog(data.getStringExtra("id"));


            }
        }else  if(requestCode==555){

            String idValue = data.getStringExtra("id");
            String idProp=util.addObjectProperty(idObjet,nameClic,idValue);
            Intent i = new Intent();
            i.putExtra("idProperty",idProp);
            setResult(RESULT_OK, i);
            finish();
            //TODO recup id et fait ta vie(crée la prop et la renvoyé)
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.choose_parent_property_menu, menu);
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        SearchManager searchManager = (SearchManager)
                getSystemService(this.SEARCH_SERVICE);
        MenuItem svItem = menu.findItem(R.id.search);
        SearchView sv = (SearchView) svItem.getActionView();
        sv.setIconifiedByDefault(false);
        sv.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        sv.setOnQueryTextListener(this);
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adaptaters.getFilter().filter(newText);
        return true;
    }


    private void newPropertyDialog(final String idValue){

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setView(this.getLayoutInflater().inflate(R.layout.dialog_new_property, null));
            dialog.setTitle(getString(R.string.new_property_title));

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


            final TextView nameEdit = (TextView )dial.findViewById(R.id.propName);
            final TextView valueEdit = (TextView )dial.findViewById(R.id.propValue);
            final TextView valueText = (TextView )dial.findViewById(R.id.propValueText);
            final Button b = (Button)dial.findViewById(R.id.button_dialog);
            final RadioButton radioButton = (RadioButton) dial.findViewById(R.id.radioButton);
            if(idValue!=null){
                HashMap<String,String> entry = util.getObject(idValue);
                valueText.setText(entry.get(EntryTable.NAME));
            }
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        valueEdit.setVisibility(View.VISIBLE);
                        valueText.setVisibility(View.GONE);
                        b.setVisibility(View.GONE);
                    }else {
                        valueEdit.setVisibility(View.GONE);
                        valueText.setVisibility(View.VISIBLE);
                        b.setVisibility(View.VISIBLE);
                    }
                }
            });
            if(radioButton.isChecked()){
                valueEdit.setVisibility(View.VISIBLE);
                valueText.setVisibility(View.GONE);
                b.setVisibility(View.GONE);
            }else {
                valueEdit.setVisibility(View.GONE);
                valueText.setVisibility(View.VISIBLE);
                b.setVisibility(View.VISIBLE);
            }

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext,Galaxy.class);
                    startActivityForResult(intent,666);
                    dial.dismiss();
                }
            });
            Button bPositive = dial.getButton(DialogInterface.BUTTON_POSITIVE);

            bPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String value;
                    String name = nameEdit.getText().toString();
                    if(radioButton.isChecked()){
                        value = valueEdit.getText().toString();
                    }else{
                        value = idValue;
                    }
                    if(name.isEmpty()) {
                        Toast.makeText(mContext,R.string.toastNameField,Toast.LENGTH_LONG).show();
                    }else if(value == null) {
                        Toast.makeText(mContext,R.string.toastNameField,Toast.LENGTH_LONG).show();
                    }



                    String idProp;
                    if(radioButton.isChecked()){


                        idProp =util.addPrimitiveProperty(idObjet,name,value);
                    }else{

                        idProp=util.addObjectProperty(idObjet,name,value);
                    }

                    Intent i = new Intent();
                    i.putExtra("idProperty",idProp);
                    setResult(RESULT_OK, i);
                    dial.dismiss();
                    finish();
                }

        });

}

    public void newProperty(final View view){
        newPropertyDialog(null);
    }
}