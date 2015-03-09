package com.example.androidproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

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
    private Boolean edition;
    private ContentProviderUtil util;
    private ArrayList<Map.Entry<String,String>>properties;
    private PropertiesAdaptaters adaptaters;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_object_layout);
        Intent i = getIntent();
        util = new ContentProviderUtil(this);
        idObjet = getIntent().getStringExtra("idObjet");
        properties = new ArrayList<Map.Entry<String,String>>();

        TextView tv = (TextView) findViewById(R.id.InheritsName);

        if(idObjet == null) {
            edition = false;

            properties.add(new AbstractMap.SimpleEntry<String, String>("test","value"));
            idParent = getIntent().getStringExtra("idParent");
            if (idParent == null) {
                idParent = "root";
            }

        }else{
            edition = true;
            HashMap<String,String> map = util.getProperties(idObjet);
            HashMap<String,String> obj = util.getObject(idObjet);
            idParent = obj.get(ObjectTable.ANCESTOR);
            for(Map.Entry<String,String> entry : map.entrySet()){
                properties.add(entry);
            }
            TextView tvName = (TextView) findViewById(R.id.objName);
            tvName.setText(idObjet);
        }
        adaptaters = new PropertiesAdaptaters(this,properties);
        tv.setText(idParent);
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adaptaters);


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
        startActivity(intent);
    }
}