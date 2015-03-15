package com.example.androidproject.synchronisation;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class BluetoothActivationReceiver extends BroadcastReceiver{

	private AndodabServer server=null;
	
	private static Handler handler = null;
	public static class MyHandler extends Handler {
		
		final Context c;
		public MyHandler(Context c) {
			super();
			this.c=c;
		}
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String s=null;
			switch(msg.what) {
			case 0:
				s="Impossible de se connecter à cet appareil";
				break;
				
			case 1:
				s="Erreur lors de la synchronisation, opération arretée";
				break;
				
			case 2:
				s="Andodab vient de synchroniser votre BDD avec un appareil distant";
				
			}
			Toast.makeText(c, s, Toast.LENGTH_SHORT).show();
		}
	}
	
	public static Handler getHandler() {
		return handler;
	}
	
	@Override
	public void onReceive(final Context context, Intent intent) {
		if(handler==null)
			handler = new MyHandler(context);
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		if(adapter.isEnabled()) {
			server = new AndodabServer(context);
			Toast.makeText(context, "Waiting for connexion", Toast.LENGTH_SHORT).show();
			server.start();		
		}			
		else if (!adapter.isEnabled()) {
			if(server!=null) {
				server.cancel();
			}
		}
	}

}
