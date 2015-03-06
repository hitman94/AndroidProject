package com.example.androidproject.activities;

import views.CustomLayout;
import views.ObjectView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.androidproject.R;
import com.example.projectandroid2015.util.ContentProviderUtil;

public class Galaxy extends Activity {

	private Menu menu;
	private CustomLayout rl;
	public static ContentProviderUtil contentUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        contentUtils= new ContentProviderUtil(this);
        contentUtils.addElement(null);

        
        rl = new CustomLayout(this);
        rl.setBackgroundColor(Color.GRAY);
        ObjectView root=new ObjectView(this,0,"Animal",null);
        rl.addView(root);
//        rl.addView(new ObjectView(this,1,"1566666666666666666666654",root));
//        rl.addView(new ObjectView(this,1,"3",root));
        setContentView(rl);
       
    }
    
    public void objectSelected() {
    	getMenuInflater().inflate(R.menu.galaxy, menu);
    }

    public void objectUnselected() {
    	menu.clear();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       this.menu=menu;
        return true;
    }

    @Override   
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.tick) {
        	Toast.makeText(this, ((ObjectView)rl.getSelectedObject()).getObjectId(), Toast.LENGTH_LONG).show();  
        	Intent i = new Intent();
        	i.putExtra("id", ((ObjectView)rl.getSelectedObject()).getObjectId());
        	setResult(RESULT_OK, i);
        	finish();
        	
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
