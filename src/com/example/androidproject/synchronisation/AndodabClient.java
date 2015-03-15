package com.example.androidproject.synchronisation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;

import com.example.androidproject.activities.MainActivity;
import com.example.projetandroid2015.tables.EntryTable;
import com.example.projetandroid2015.tables.ObjectEntryTable;
import com.example.projetandroid2015.tables.ObjectTable;
import com.example.projetandroid2015.tables.PrimitiveEntryTable;

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
            BluetoothActivationReceiver.getHandler().sendEmptyMessage(0);
            return;
        }

        try {
			launchSynchronizeThread(socket);
		} catch (IOException e) {
			BluetoothActivationReceiver.getHandler().sendEmptyMessage(1);
			return;
		} catch (ClassNotFoundException e) {
			BluetoothActivationReceiver.getHandler().sendEmptyMessage(1);
			return;
		}
    }

    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            socket.close();
        } catch (IOException e) {
			return;
        }
    }



    public void launchSynchronizeThread(BluetoothSocket socket) throws IOException, ClassNotFoundException{
    	ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
    	ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
    	Long synchroDate = System.currentTimeMillis();
    	List<HashMap<String,String>> objs = MainActivity.contentUtils.getAllObjects();
    	SharedPreferences pref = context.getSharedPreferences("andodabSyncDevices", Context.MODE_PRIVATE);


    	in.readObject();
    	out.writeObject(synchroDate);
    	in.readObject();
    	
    	if(pref.getLong(socket.getRemoteDevice().getAddress(), -1) == -1) { // 1ere synchro
    		sendObjects(objs, out, in);
    		sendProperties(objs, out, in);
    		List<HashMap<String, String>> receivedObj = receiveObjets(out, in);
    		HashMap<String,HashMap<String,String>> receivedProperties =receiveProperties(out, in);
    		
    		for(int i =0;i<receivedObj.size();i++) {
    			try {
    	    	MainActivity.contentUtils.addObjet(receivedObj.get(i));
    			} catch(SQLException e) {}
    		}
    		for(Entry<String, HashMap<String, String>> entry : receivedProperties.entrySet()) {
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
    	    	
    	out.close();
    	in.close();
    	
    	
    	Editor editor = pref.edit();
    	editor.putLong(socket.getRemoteDevice().getAddress(), synchroDate);
    	editor.commit();
    	BluetoothActivationReceiver.getHandler().sendEmptyMessage(2);
        return;
    }
    
    
    public static List<HashMap<String,String>> receiveObjets(ObjectOutputStream out, ObjectInputStream in) throws OptionalDataException, ClassNotFoundException, IOException {
    	List<HashMap<String,String>> toReturn = new ArrayList<HashMap<String,String>>(); 	
    	do {
    		toReturn.add((HashMap<String,String>)in.readObject());
    	}while( ((Integer)in.readObject()) == 1);
    	
    	return toReturn;
    }
    
    public static HashMap<String,HashMap<String,String>> receiveProperties(ObjectOutputStream out, ObjectInputStream in) throws OptionalDataException, ClassNotFoundException, IOException {
    	HashMap<String, HashMap<String,String>> toReturn;
    	toReturn = (HashMap<String, HashMap<String,String>>)in.readObject();    	
    	return toReturn;
    }
    
    public static void sendProperties(List<HashMap<String,String>> objs,ObjectOutputStream out, ObjectInputStream in) throws IOException {
    	System.err.println("prop");
    	HashMap<String, HashMap<String,String>> sendProperties = new HashMap<String, HashMap<String,String>>();
    	for(int i =0;i<objs.size();i++) {
			HashMap<String, String> properties = MainActivity.contentUtils.getProperties(objs.get(i).get(ObjectTable.COLUMN_ID));
			if (properties == null)
				continue;
			else {
				sendProperties.put(objs.get(i).get(ObjectTable.COLUMN_ID), properties);
			}
    	}
    	out.writeObject(sendProperties);
    }
    
    public static void sendObjects(List<HashMap<String,String>> objs,ObjectOutputStream out, ObjectInputStream in) throws IOException {
    	for(int i =0;i<objs.size();i++) {
    		out.writeObject(objs.get(i));
    		if(i==objs.size()-1)
    			out.writeObject(0);
    		else
    			out.writeObject(1);
    	}
    }
}
