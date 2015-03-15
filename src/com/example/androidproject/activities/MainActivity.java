package com.example.androidproject.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.androidproject.R;
import com.example.projectandroid2015.util.ContentProviderUtil;


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
    	Intent i = new Intent(this,EditObjectActivity.class);
        i.putExtra("idObjet","Koala");
        startActivity(i);
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
