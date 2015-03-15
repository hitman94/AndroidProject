package com.example.androidproject.synchronisation;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.CursorIndexOutOfBoundsException;
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
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

    public void launchSynchronizeThread(BluetoothSocket socket) throws StreamCorruptedException, IOException, ClassNotFoundException{
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		System.err.println("demarrage de l'envoi");
		Long synchroDate;
		List<HashMap<String,String>> objs = MainActivity.contentUtils.getAllObjects();
		SharedPreferences pref = context.getSharedPreferences(
				"andodabSyncDevices", Context.MODE_PRIVATE);
		
		
		out.writeObject(1);
		synchroDate = (Long)in.readObject();
		out.writeObject(1);
		if(pref.getLong(socket.getRemoteDevice().getAddress(), -1) == -1) { // 1ere synchro
			List<HashMap<String, String>> receivedObj = AndodabClient.receiveObjets(out, in);
			System.err.println("Recepetion des objs cote serveur");
    		HashMap<String,HashMap<String,String>> receivedProperties =AndodabClient.receiveProperties(out, in);
    		System.err.println("Recepetion des prop cote serveur");
    		AndodabClient.sendObjects(objs, out, in);
    		System.err.println("Envoi des objs cote serveur");
    		AndodabClient.sendProperties(objs, out, in);
    		System.err.println("Ebnvoi  des prop cote serveur");
    		for(int i =0;i<receivedObj.size()-1;i++) {
    	    	//MainActivity.contentUtils.addObjet(receivedObj.get(i));
    			Log.e("objet", receivedObj.get(i).toString());
    		}
    		System.err.println("ajout des objs cote serveur");
    		
    		for(Entry<String, HashMap<String, String>> entry : receivedProperties.entrySet()) {
    			if(entry.getValue().size()==0)
    				continue;
    			Log.e("pro", entry.getKey());
    			Log.e("prop", entry.getValue().toString());
    			
//    			try {
//    				MainActivity.contentUtils.getName(entry.getValue().get(ObjectEntryTable.VALUE));
//    				MainActivity.contentUtils.addProperty(entry.getKey(), entry.getValue().get(EntryTable.NAME), "Object", entry.getValue().get(ObjectEntryTable.VALUE));
//    			}catch(CursorIndexOutOfBoundsException e) {
//    				MainActivity.contentUtils.addProperty(entry.getKey(), entry.getValue().get(EntryTable.NAME), "Primitive", entry.getValue().get(PrimitiveEntryTable.VALUE));
//    			}
    		}
    		
    	}else {// Update
    		
    	}

		
		Editor editor = pref.edit();
		editor.putLong(socket.getRemoteDevice().getAddress(), synchroDate);
		editor.commit();
		return;
    }
}
