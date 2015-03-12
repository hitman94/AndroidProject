package com.example.androidproject.synchronisation;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;

/**
 * Created by Florian on 12/03/2015.
 */
public class AndodabClient extends Thread{
    private final BluetoothSocket socket;
    private final BluetoothDevice device;


    public AndodabClient(BluetoothDevice device) {

        BluetoothSocket tmp = null;
        this.device = device;

        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = device.createRfcommSocketToServiceRecord(java.util.UUID.fromString("AndodabProjectM2android"));
        } catch (IOException e) { }
        socket = tmp;
    }

    public void run() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.cancelDiscovery();

        try {
            socket.connect();
        } catch (IOException connectException) {
            try {
                socket.close();
            } catch (IOException closeException) { }
            return;
        }

        launchSynchronizeThread(socket);
    }

    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            socket.close();
        } catch (IOException e) { }
    }



    public void launchSynchronizeThread(BluetoothSocket socket){
        //TODO launch
    }
}
