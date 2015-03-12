package com.example.androidproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.androidproject.R;
import com.example.projectandroid2015.util.ContentProviderUtil;


public class MainActivity extends Activity {

	public static ContentProviderUtil contentUtils;
	private boolean bddCreated = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!bddCreated) {
        	contentUtils= new ContentProviderUtil(this);
        	//contentUtils.addElement(null);
        	bddCreated=true;
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
