package com.example.androidproject.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject.R;

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
				discoveredDevice.add(device);
				deviceListAdapter.notifyDataSetChanged();
			}
			
		}
	};
	
	private class BluetoothDeviceAdapter<T> extends ArrayAdapter<T> {

		public BluetoothDeviceAdapter(Context context, int resource,
				List<T> objects) {
			super(context, resource, objects);
		}
		
		
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) getContext()
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.bluetooth_item, parent,false);
			TextView labelView = (TextView) rowView.findViewById(R.id.deviceName);
			labelView.setText(discoveredDevice.get(position).getName());
			return rowView;
			
		}
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_synchronizer);
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		discoveredDevice = new ArrayList<BluetoothDevice>();
		
		
		deviceListAdapter = new BluetoothDeviceAdapter<BluetoothDevice>(this,R.layout.bluetooth_item, discoveredDevice);
		
		ListView listDevice =  (ListView)findViewById(R.id.deviceList);
		listDevice.setAdapter(deviceListAdapter);
		
		
		
		
		if(bluetoothAdapter == null) {
			Toast.makeText(this, "Votre appareil n'a pas de bluetooth", Toast.LENGTH_SHORT).show();
			finish();
		}
		if (!bluetoothAdapter.isEnabled()) {
			   Intent enableBlueTooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			   startActivityForResult(enableBlueTooth, REQUEST_BLUETOOTH_ACTIVATION);
		} else {
			startDiscovering();
		}
	}
	
	private void startDiscovering() {
		registerReceiver(bluetoothReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
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
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(bluetoothReceiver);
		bluetoothAdapter.cancelDiscovery();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==REQUEST_BLUETOOTH_ACTIVATION) {
			if(resultCode==RESULT_OK) {
				Toast.makeText(this, "Scanning for device", Toast.LENGTH_SHORT).show();
				startDiscovering();
				
			}
		}
	}
}
