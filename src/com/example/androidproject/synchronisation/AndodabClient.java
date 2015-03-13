package com.example.androidproject.synchronisation;

import java.io.IOException;
import java.io.ObjectOutputStream;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by Florian on 12/03/2015.
 */
public class AndodabClient extends Thread{
    private final BluetoothSocket socket;
    private final BluetoothDevice device;
    private final Context context;

    public AndodabClient(BluetoothDevice device,Context context) {

        BluetoothSocket tmp = null;
        this.device = device;
        this.context=context;
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = device.createRfcommSocketToServiceRecord(java.util.UUID.fromString("83818610-c8bf-11e4-8830-0800200c9a66"));
        } catch (IOException e) { }
        socket = tmp;
    }

    public void run() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.cancelDiscovery();

        try {
            socket.connect();
        } catch (IOException connectException) {
            cancel();
            //Toast.makeText(context, "Impossible d'etablir la connexion", Toast.LENGTH_SHORT).show();
            System.err.println("connexion invalide");
            return;
        }

        try {
			launchSynchronizeThread(socket);
		} catch (IOException e) {
			System.err.println("erreur lors de lenvoie de données");
			return;
		}
    }

    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            socket.close();
        } catch (IOException e) { }
    }



    public void launchSynchronizeThread(BluetoothSocket socket) throws IOException{
    	ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
    	
    	Long synchroDate = System.currentTimeMillis();
    	
    	out.writeObject(synchroDate);
    	System.err.println("connexion OK");
    	
    	SharedPreferences pref = context.getSharedPreferences("andodabSyncDevices", Context.MODE_PRIVATE);
    	Editor editor = pref.edit();
    	editor.putLong(socket.getRemoteDevice().getAddress(), synchroDate);
    	editor.commit();
    	
        return;
    }
}
