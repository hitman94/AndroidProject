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
            tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("Andodab",java.util.UUID.fromString("AndodabProjectM2android"));
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
            if (socket != null) {
                launchSynchronizeThread(socket);
                try {
                    ServerSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    /** Will cancel the listening socket, and cause the thread to finish */
    public void cancel() {
        try {
            ServerSocket.close();
        } catch (IOException e) { }
    }

    public void launchSynchronizeThread(BluetoothSocket socket){
        //TODO launch
    }
}
