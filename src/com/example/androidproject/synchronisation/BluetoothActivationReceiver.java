package com.example.androidproject.synchronisation;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BluetoothActivationReceiver extends BroadcastReceiver{

	private AndodabServer server=null;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		if(adapter.isEnabled()) {
			server = new AndodabServer(context);
			Toast.makeText(context, "Waiting for connexion", Toast.LENGTH_SHORT).show();
			server.start();			
		}			
		else if (!adapter.isEnabled()) {
			if(server!=null)
				server.cancel();
		}
	}

}
