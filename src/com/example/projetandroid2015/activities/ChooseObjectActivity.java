package com.example.projetandroid2015.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.projetandroid2015.R;
import com.example.projetandroid2015.objects.AbstractObject;

public class ChooseObjectActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_object);
		Long idObj = getIntent().getLongExtra("id", -1L);
		
		//TODO recuperer le vrai objet de la BDD
		AbstractObject obj = new AbstractObject(idObj, 10L, "Lion", false);
		
		ListView lv = (ListView)findViewById(R.id.listChildren);
		final ListAdapter adapter =new ArrayAdapter<AbstractObject>(this, android.R.layout.simple_list_item_1, obj.getChilds());
		lv.setAdapter(adapter);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getApplicationContext(), ChooseObjectActivity.class);
				i.putExtra("id", ((AbstractObject)adapter.getItem(position)).getId() );
				startActivityForResult(i, 666);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_object, menu);
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
