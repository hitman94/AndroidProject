package com.example.androidproject.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;
import com.example.androidproject.R;
import com.example.androidproject.database.AndodabContentProvider;
import com.example.projectandroid2015.util.ContentProviderUtil;
import com.example.projetandroid2015.tables.DicoObjectTable;
import com.example.projetandroid2015.tables.ObjectTable;

import java.util.HashMap;
import java.util.Random;

public class MainActivity extends Activity {

	public static ContentProviderUtil contentUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences pref = getSharedPreferences("andodabCreate", Context.MODE_PRIVATE);
        contentUtils= new ContentProviderUtil(this);
        if(pref.getInt("init", -1) == -1) {
        	contentUtils.addElement(null);
        	Editor edit=pref.edit();
        	edit.putInt("init", 1);
        	edit.commit();
        }
    }
    
    public void viewBase(View v) {
    	Intent i = new Intent(this, Galaxy.class);
    	i.putExtra("allowChoose", false);
    	startActivity(i);
    }
    
    public void createNewObject(View v) {
    	Intent i = new Intent(this,Galaxy.class);
        Toast.makeText(this,"Choose parent Object",Toast.LENGTH_LONG).show();
        startActivityForResult(i, 777);
    }
    
    public void synchronize(View v){
    	Intent i = new Intent(this, Synchronizer.class);
    	startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 777){
            if(resultCode == RESULT_OK){

               String idParent =  data.getStringExtra("id");
                //TODO crée un objet avec pour parent idParent puis start editObjet avec la nouvelle id de l'objet

                //pour le moment j'appel avec l'id parent donc ça edit le parent, mais c'est pour tester
                HashMap<String,String> map = contentUtils.getObject(idParent);
                Long idObjet = new Random().nextLong();

                ContentValues values = new ContentValues();
                values.put(ObjectTable.COLUMN_ID,idObjet);
                values.put(ObjectTable.NAME, "Name not defined");
                values.put(ObjectTable.OBJECT_TYPE, "Object");
                values.put(ObjectTable.ANCESTOR, idParent);
                String sealed = map.get(DicoObjectTable.SEALED);
                if(sealed == null || sealed.equals("false"))
                  values.put(DicoObjectTable.SEALED, "false");
                else
                    values.put(DicoObjectTable.SEALED, "true");
                this.getContentResolver().insert(
                       AndodabContentProvider.CONTENT_URI_OBJECT, values);

                Intent i = new Intent(this, EditObjectActivity.class);
                i.putExtra("idObjet",values.get(ObjectTable.COLUMN_ID).toString());
                startActivity(i);
            }
        }
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
}
