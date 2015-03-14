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
            System.err.println("connexion invalide");
            return;
        }

        try {
			launchSynchronizeThread(socket);
		} catch (IOException e) {
			System.err.println("erreur lors de lenvoie de données");
			return;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            socket.close();
        } catch (IOException e) { }
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
    		System.err.println("Envoi des objs cote client");
    		sendObjects(objs, out, in);
    		System.err.println("Envoi des prop cote client");
    		sendProperties(objs, out, in);
    		System.err.println("ajout des objs cote client");
    		List<HashMap<String, String>> receivedObj = receiveObjets(out, in);
    		System.err.println("ajout des objs cote client");
    		HashMap<String,HashMap<String,String>> receivedProperties =receiveProperties(out, in);
    		
    		for(int i =0;i<receivedObj.size();i++) {
    	    	MainActivity.contentUtils.addObjet(receivedObj.get(i));
    		}
    		System.err.println("ajout des objs cote client");
    		for(Entry<String, HashMap<String, String>> entry : receivedProperties.entrySet()) {
    			try {
    				MainActivity.contentUtils.getName(entry.getValue().get(ObjectEntryTable.VALUE));
    				MainActivity.contentUtils.addProperty(entry.getKey(), entry.getValue().get(EntryTable.NAME), "Object", entry.getValue().get(ObjectEntryTable.VALUE));
    			}catch(CursorIndexOutOfBoundsException e) {
    				MainActivity.contentUtils.addProperty(entry.getKey(), entry.getValue().get(EntryTable.NAME), "Primitive", entry.getValue().get(PrimitiveEntryTable.VALUE));
    			}
    		}
    		
    	}else {// Update
    		
    	}
    	    	
    	out.close();
    	in.close();
    	
    	
    	Editor editor = pref.edit();
    	editor.putLong(socket.getRemoteDevice().getAddress(), synchroDate);
    	editor.commit();
    	
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
