package com.example.androidproject.activities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject.R;
import com.example.androidproject.synchronisation.AndodabClient;

public class Synchronizer extends Activity {

	private final static int REQUEST_BLUETOOTH_ACTIVATION = 100;
	private BluetoothAdapter bluetoothAdapter;
	private ArrayAdapter<BluetoothDevice> deviceListAdapter;
	private List<BluetoothDevice> discoveredDevice;
	private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if(!discoveredDevice.contains(device)) {
					discoveredDevice.add(device);
					deviceListAdapter.notifyDataSetChanged();
				}
			}
			
		}
	};
	
	private class BluetoothDeviceAdapter<T> extends ArrayAdapter<T> {
		
		private final SharedPreferences pref;

		public BluetoothDeviceAdapter(Context context, int resource,
				List<T> objects) {
			super(context, resource, objects);
			pref = context.getSharedPreferences("andodabSyncDevices", Context.MODE_PRIVATE);
		}
		
		
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) getContext()
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.bluetooth_item, parent,false);
			TextView labelView = (TextView) rowView.findViewById(R.id.deviceName);
			labelView.setText(discoveredDevice.get(position).getName());
			Long syncDate;
			if( (syncDate=pref.getLong(discoveredDevice.get(position).getAddress(), -1)) != -1 ) {
				TextView textDate = (TextView) rowView.findViewById(R.id.syncDate);
				textDate.setText("Derniere synchro: "+new Date(syncDate).toString());
			}
			return rowView;
			
		}
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_synchronizer);
		
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		if(bluetoothAdapter == null) {
			Toast.makeText(this, "Votre appareil n'a pas de bluetooth", Toast.LENGTH_SHORT).show();
			finish();
		}
		
		discoveredDevice = new ArrayList<BluetoothDevice>();
		
		
		deviceListAdapter = new BluetoothDeviceAdapter<BluetoothDevice>(this,R.layout.bluetooth_item, discoveredDevice);
		Set<BluetoothDevice> set = bluetoothAdapter.getBondedDevices();
		for(BluetoothDevice device : set)
			discoveredDevice.add(device);
		
		ListView listDevice =  (ListView)findViewById(R.id.deviceList);
		listDevice.setAdapter(deviceListAdapter);		
		
		listDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				AndodabClient client = new AndodabClient(discoveredDevice.get(position),getApplicationContext());
				client.start();
				
			}
		});
		
		
		
		
		registerReceiver(bluetoothReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (!bluetoothAdapter.isEnabled()) {
			   Intent enableBlueTooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			   startActivityForResult(enableBlueTooth, REQUEST_BLUETOOTH_ACTIVATION);
		} else {
			startDiscovering();
			
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		bluetoothAdapter.cancelDiscovery();
	}
	
	private void startDiscovering() {
		bluetoothAdapter.startDiscovery();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.synchronizer, menu);
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
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(bluetoothReceiver);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==REQUEST_BLUETOOTH_ACTIVATION) {
			if(resultCode==RESULT_OK) {
				Toast.makeText(this, "Scanning for device", Toast.LENGTH_SHORT).show();
				startDiscovering();
				
			}
			else {
				finish();
			}
		}
	}
}
