package com.example.androidproject.activities;

import java.util.ArrayList;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject.R;
import com.example.androidproject.adapters.PropertiesAdaptaters;
import com.example.projectandroid2015.util.ContentProviderUtil;

/**
 * Created by Florian on 04/03/2015.
 */
public class ChooseParentPropertyActivity extends Activity implements SearchView.OnQueryTextListener {

    private ArrayList<Map.Entry<String,String>> properties;
    private PropertiesAdaptaters adaptaters;
    private Context mContext;
    private AlertDialog dial;
    private ContentProviderUtil util;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        util = new ContentProviderUtil(this);
        Intent i =   getIntent();
        String idObjet = i.getStringExtra("id");
        properties = new ArrayList<Map.Entry<String, String>>();
        if(idObjet!=null){
            for( Map.Entry<String,String> entry : util.getParentProperties(idObjet).entrySet()){
                properties.add(entry);
            }
        }
        setContentView(R.layout.choose_parent_property);
        //TODO init le content resolver
        //TODO load les properties depuis le content, tmp des ajout à la main

   /*     properties.add(new AbstractMap.SimpleEntry<String, String>("test","value"));
        properties.add(new AbstractMap.SimpleEntry<String, String>("type","value"));
        properties.add(new AbstractMap.SimpleEntry<String, String>("food","value"));
        properties.add(new AbstractMap.SimpleEntry<String, String>("Frite","value"));
        properties.add(new AbstractMap.SimpleEntry<String, String>("Bogosssssssssssss","value"));
        properties.add(new AbstractMap.SimpleEntry<String, String>("Race","value"));
        properties.add(new AbstractMap.SimpleEntry<String, String>("test","value"));
        properties.add(new AbstractMap.SimpleEntry<String, String>("type","value"));
        properties.add(new AbstractMap.SimpleEntry<String, String>("food","value"));
        properties.add(new AbstractMap.SimpleEntry<String, String>("Frite","value"));
        properties.add(new AbstractMap.SimpleEntry<String, String>("Bogosssssssssssss","value"));
        properties.add(new AbstractMap.SimpleEntry<String, String>("Race","value")); */

        ListView lv = (ListView) findViewById(R.id.listproperties);
        adaptaters = new PropertiesAdaptaters(this,properties);
        lv.setAdapter(adaptaters);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Map.Entry<String,String> entry = adaptaters.getItem(position);
                Intent i = new Intent();
                i.putExtra("name",entry.getKey());
                i.putExtra("value",entry.getValue());
                setResult(RESULT_OK, i);
                finish();
            }
        });

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


    private void newPropertyDialog(){
        if(dial==null){
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
                    //TODO implementer une réponse négative
                }
            });
            final AlertDialog dial = dialog.create();
            dial.show();
            Button b = (Button)dial.findViewById(R.id.button_dialog);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO appel de l'activité de guerin
                    System.out.println("prout");
                }
            });
            Button bPositive = dial.getButton(DialogInterface.BUTTON_POSITIVE);
            final TextView nameT = (TextView )dial.findViewById(R.id.propName);
            final TextView valueT = (TextView )dial.findViewById(R.id.propValue);
            bPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(nameT.getText().toString().isEmpty()){
                        //TODO vérifier que la prop n'est pas déja dans les prop des parents
                        Toast.makeText(v.getContext(),getString(R.string.toastNameField),Toast.LENGTH_LONG).show();
                    }else if(valueT.getText().toString().isEmpty()){
                        Toast.makeText(v.getContext(),getString(R.string.toastValueField),Toast.LENGTH_LONG).show();
                    }else{
                        Intent i = new Intent();
                        i.putExtra("name",nameT.getText().toString());
                        i.putExtra("value",valueT.getText().toString());
                        setResult(RESULT_OK, i);
                        dial.dismiss();
                        finish();
                    }
                }
            });
        }else{
            dial.show();
        }
    }

    public void newProperty(final View view){
       newPropertyDialog();
    }
}