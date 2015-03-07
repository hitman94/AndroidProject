package com.example.androidproject.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import com.example.projetandroid2015.R;
import com.example.androidproject.adapters.PropertiesAdaptaters;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Florian on 04/03/2015.
 */
public class ChooseParentPropertyActivity extends Activity implements SearchView.OnQueryTextListener {

    private ArrayList<Map.Entry<String,String>> properties;
    private ContentResolver contentResolver;
    private PropertiesAdaptaters adaptaters;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.choose_parent_property);
        //TODO init le content resolver
        //TODO load les properties depuis le content, tmp des ajout à la main
        properties = new ArrayList<Map.Entry<String, String>>();
        properties.add(new AbstractMap.SimpleEntry<String, String>("test","value"));
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
        properties.add(new AbstractMap.SimpleEntry<String, String>("Race","value"));
        ListView lv = (ListView) findViewById(R.id.listproperties);
        adaptaters = new PropertiesAdaptaters(this,properties);
        lv.setAdapter(adaptaters);

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

    public void newProperty(View view){
         AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setView(this.getLayoutInflater().inflate(R.layout.dialog_new_property, null));
        dialog.setTitle(getString(R.string.new_property_title));

        dialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO implementer une réponse positive
            }
        }).setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO implementer une réponse négative
            }
        });
        Dialog dial = dialog.create();
        dial.show();
        Button b = (Button)dial.findViewById(R.id.button_dialog);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO appel de l'activité de guerin
                System.out.println("prout");
            }
        });


    }
}