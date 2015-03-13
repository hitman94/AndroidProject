package com.example.androidproject.synchronisation;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

/**
 * Created by Florian on 12/03/2015.
 */
public class AndodabServer extends Thread {
    private final BluetoothServerSocket ServerSocket;
    private final Context context;
    public AndodabServer(Context context) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothServerSocket tmp = null;
        this.context=context;
        try {
            // MY_UUID is the app's UUID string, also used by the client code
            tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("Andodab",java.util.UUID.fromString("83818610-c8bf-11e4-8830-0800200c9a66"));
        } catch (IOException e) { }
        ServerSocket = tmp;
    }

    public void run() {
        BluetoothSocket socket = null;
        while (true) {
            try {
                socket = ServerSocket.accept();
            } catch (IOException e) {
                break;
            }
            
            try {
				launchSynchronizeThread(socket);
			} catch (StreamCorruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
            if (socket != null) {
               
                try {
                    ServerSocket.close();
                } catch (IOException e) {
                   return;
                }
               
            }
            break;
        }
    }

    /** Will cancel the listening socket, and cause the thread to finish */
    public void cancel() {
        try {
            ServerSocket.close();
        } catch (IOException e) { }
    }

    public void launchSynchronizeThread(BluetoothSocket socket) throws StreamCorruptedException, IOException{
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		Long synchroDate = in.readLong();

		SharedPreferences pref = context.getSharedPreferences(
				"andodabSyncDevices", Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putLong(socket.getRemoteDevice().getAddress(), synchroDate);
		editor.commit();
		return;
    }
}
