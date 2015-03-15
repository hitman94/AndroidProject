package com.example.androidproject.synchronisation;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Florian on 12/03/2015.
 */
public class ManageDataExchange extends Thread{
    private final BluetoothSocket socket;
    private final InputStream inStream;
    private final OutputStream OutStream;

    public ManageDataExchange(BluetoothSocket socket) {
        this.socket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }

        inStream = tmpIn;
        OutStream = tmpOut;
    }

    @Override
    public void run() {
        super.run();
    }
}
