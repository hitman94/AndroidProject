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

/**
 * Created by Florian on 01/03/2015.
 */
public class EditObjectActivity extends Activity {

    private Long idParent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_object_layout);
        Intent i = getIntent();
        idParent = getIntent().getLongExtra("idParent",-1L);
        if(idParent == -1L){
            //TODO par default on met l'ancetre Obj j'magine, je changerais ça qd j'aurais trouver comment accéder à l'obj ancetre
            idParent = 0L;
        }
        //TODO recuperer le vrai objet de la BDD faut changer la tech aparament, genre un cursor trop cool

        TextView tv = (TextView) findViewById(R.id.InheritsName);

        ListView lv = (ListView) findViewById(R.id.listView);

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