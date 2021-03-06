package com.example.androidproject.synchronisation;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.example.androidproject.activities.MainActivity;
import com.example.projetandroid2015.tables.EntryTable;
import com.example.projetandroid2015.tables.ObjectEntryTable;
import com.example.projetandroid2015.tables.PrimitiveEntryTable;

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
            	BluetoothActivationReceiver.getHandler().sendEmptyMessage(0);
                break;
            }
            
            try {
				launchSynchronizeThread(socket);
			} catch (StreamCorruptedException e1) {
				BluetoothActivationReceiver.getHandler().sendEmptyMessage(1);
				return;
			} catch (IOException e1) {
				BluetoothActivationReceiver.getHandler().sendEmptyMessage(1);
				return;
			} catch (ClassNotFoundException e) {
				BluetoothActivationReceiver.getHandler().sendEmptyMessage(1);
				return;
			}
            
            if (socket != null) {
               
                try {
                    ServerSocket.close();
                } catch (IOException e) {
                   return;
                }
               
            }
        }
    }

    /** Will cancel the listening socket, and cause the thread to finish */
    public void cancel() {
        try {
            ServerSocket.close();
        } catch (IOException e) { }
    }

    public void launchSynchronizeThread(BluetoothSocket socket) throws StreamCorruptedException, IOException, ClassNotFoundException{
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		Long synchroDate;
		List<HashMap<String,String>> objs = MainActivity.contentUtils.getAllObjects();
		SharedPreferences pref = context.getSharedPreferences(
				"andodabSyncDevices", Context.MODE_PRIVATE);
		
		
		out.writeObject(1);
		synchroDate = (Long)in.readObject();
		out.writeObject(1);
		if(pref.getLong(socket.getRemoteDevice().getAddress(), -1) == -1) { // 1ere synchro
			List<HashMap<String, String>> receivedObj = AndodabClient.receiveObjets(out, in);
    		HashMap<String,HashMap<String,String>> receivedProperties =AndodabClient.receiveProperties(out, in);
    		AndodabClient.sendObjects(objs, out, in);
    		AndodabClient.sendProperties(objs, out, in);
    		for(int i =0;i<receivedObj.size();i++) {
    			try {
    				MainActivity.contentUtils.addObjet(receivedObj.get(i));
    			}catch(SQLException e){
    			}
    		}
    		
    		for(Entry<String, HashMap<String, String>> entry : receivedProperties.entrySet()) {
    			if(entry.getValue().size()==0)
    				continue;
    			
    			Log.e("pro", entry.getKey());
    			Log.e("prop", entry.getValue().toString());
    			
    			for(Entry<String,String> propEnt:	entry.getValue().entrySet()) {    			
	    			try {
	    				MainActivity.contentUtils.getName(propEnt.getValue());
	    				MainActivity.contentUtils.addObjectProperty(entry.getKey(), propEnt.getKey(), propEnt.getValue());
	    			}catch(CursorIndexOutOfBoundsException e) {
	    				MainActivity.contentUtils.addPrimitiveProperty(entry.getKey(), propEnt.getKey(), propEnt.getValue());
	    			}
    			}
    		}
    		
    	}else {// Update
    		
    	}

		Editor editor = pref.edit();
		editor.putLong(socket.getRemoteDevice().getAddress(), synchroDate);
		editor.commit();
		BluetoothActivationReceiver.getHandler().sendEmptyMessage(2);
		return;
    }
}
