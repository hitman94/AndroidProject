package com.example.androidproject.activities;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.example.projetandroid2015.tables.ObjectTable;

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
    private ContentProviderUtil util;
    private AlertDialog dial;
    private ArrayList<Map.Entry<String,String>> propertiesList;
    private HashMap<String,String> parentProperties;
    private HashMap<String,String> properties;
    private PropertiesAdaptaters adaptaters;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_object_layout);
        Intent i = getIntent();
        util = new ContentProviderUtil(this);
        propertiesList = new ArrayList<Map.Entry<String,String>>();
        parentProperties = new HashMap<String, String>();
        properties = new HashMap<String, String>();
        ListView lv = (ListView) findViewById(R.id.listView);
        TextView tvName = (TextView) findViewById(R.id.objName);
        TextView tv = (TextView) findViewById(R.id.InheritsName);


        if(savedInstanceState !=null){
            idObjet = savedInstanceState.getString("idObjet");
        }else {
            idObjet = i.getStringExtra("idObjet");
        }

        if(idObjet != null) {
            properties = util.getProperties(idObjet);
            HashMap<String,String> obj = util.getObject(idObjet);
            parentProperties = util.getParentProperties(idObjet);
            idParent = obj.get(ObjectTable.ANCESTOR);
            //TODO recup le name
            //String name = obj.get(NAME);
            // tvName.setText(name);
            for(Map.Entry<String,String> entry : properties.entrySet()){
                propertiesList.add(entry);
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
        adaptaters = new PropertiesAdaptaters(this, propertiesList);
        tv.setText(idParent);

        lv.setAdapter(adaptaters);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map.Entry<String,String> entry = adaptaters.getItem(position);
                createDialog(entry.getKey(),entry.getValue());

            }
        });
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
        intent.putExtra("id",idObjet);
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
                String name = data.getStringExtra("name");
                String value = data.getStringExtra("value");
                if(name!=null && value !=null){
                    for(Map.Entry<String,String> entry : propertiesList){
                        if(entry.getKey().equals(name)){
                            Toast.makeText(this,getString(R.string.toast_property_already_defined),Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    propertiesList.add(new AbstractMap.SimpleEntry<String, String>(name, value));
                    adaptaters.notifyDataSetChanged();
                }
            }
        }else if(requestCode==666){
            if(resultCode == RESULT_OK){

            }
        }
    }

    private void createDialog(final String name, final String value){
        if(dial==null){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setView(this.getLayoutInflater().inflate(R.layout.dialog_edit_property, null));
            dialog.setTitle(getString(R.string.edit_property_dialog_title));

            dialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //TODO implementer une réponse négative
                }
            });
            final AlertDialog dial = dialog.create();
            dial.show();
            Button b = (Button)dial.findViewById(R.id.button_dialog);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),Galaxy.class);
                    if(!parentProperties.containsKey(name)){
                        intent.putExtra("rootId","root");
                    }else{
                        intent.putExtra("rootId",value);
                    }
                    startActivityForResult(intent, 666);
                    dial.dismiss();
                }
            });
            Button bPositive = dial.getButton(DialogInterface.BUTTON_POSITIVE);


            final TextView propNameView = (TextView )dial.findViewById(R.id.propName);
            final TextView propValueEditView = (TextView )dial.findViewById(R.id.propValue);
            final TextView propValueView = (TextView )dial.findViewById(R.id.propValueText);
            propNameView.setText(name);

            if(parentProperties.containsKey(name) && (value.equals("String") || value.equals("Float") || value.equals("Integer"))){

                propValueEditView.setVisibility(View.VISIBLE);
                b.setVisibility(View.GONE);
                propValueView.setVisibility(View.GONE);

            }else{
                propValueEditView.setVisibility(View.GONE);
                propValueView.setText(value);
                b.setVisibility(View.VISIBLE);
                propValueView.setVisibility(View.VISIBLE);
            }

            //TODO ça bug si on edit une prop qu'on vien d'edité
            bPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(propNameView.getText().toString().isEmpty()){
                        Toast.makeText(v.getContext(),getString(R.string.toastNameField),Toast.LENGTH_LONG).show();
                    }else if(propValueEditView.getText().toString().isEmpty()){
                        Toast.makeText(v.getContext(),getString(R.string.toastValueField),Toast.LENGTH_LONG).show();
                    }else{
                       for(int i =0;i<propertiesList.size();i++){
                           Map.Entry<String,String> entry = propertiesList.get(i);
                           if(entry.getKey() == propNameView.getText()){
                               if(propValueEditView.getVisibility() == View.VISIBLE)
                                entry.setValue(propValueEditView.getText().toString());
                               else
                                 entry.setValue(propValueView.getText().toString());
                               adaptaters.notifyDataSetChanged();
                           }
                       }
                        dial.dismiss();
                    }
                }
            });
        }else{
            dial.show();
        }
    }
}