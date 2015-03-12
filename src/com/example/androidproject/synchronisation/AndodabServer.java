package com.example.androidproject.synchronisation;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;

/**
 * Created by Florian on 12/03/2015.
 */
public class AndodabServer extends Thread {
    private final BluetoothServerSocket ServerSocket;

    public AndodabServer() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothServerSocket tmp = null;
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
            
            launchSynchronizeThread(socket);
            
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

    public void launchSynchronizeThread(BluetoothSocket socket){
       System.err.println("Connexion serveur OK");
       return;
    }
}
