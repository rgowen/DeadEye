package com.cascadiaoccidental.deadeye;

import com.cascadiaoccidental.deadeye.data.AmazonResultData;
import com.cascadiaoccidental.deadeye.util.JsonParser;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Vibrator;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;
import java.util.UUID;

public class ScannerThread extends Thread implements Runnable {
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final long[] VIBRATE_EXCITED =  { 0, 50, 100, 50, 100, 50, 100, 50, 100, 50, 100, 50 };
    private static final long[] VIBRATE_NEGATIVE = { 0, 350, 500, 350 };
    private BluetoothDevice device;
    private BluetoothAdapter adapter;
    private BluetoothSocket socket;
    private InputStream inStream;
    byte[] readBuffer;
    int readBufferPosition;
    private boolean isConnected;
    GenericCallback callback;
    Vibrator vibrator;
    double target;

    public ScannerThread(GenericCallback c, Vibrator v, double t) {
        this.callback = c;
        this.vibrator = v;
        this.target = t;
    }
    public void interrupt() {
        super.interrupt();
        if(isConnected);
        try {
            Log.i("ScannerThread", "Closing scanner...");
            close();
        }
        catch (Exception e) {
            Log.e("ScannerThread", "Problem closing connection", e);
        }
    }
    public void run() {
        while(!isConnected && !currentThread().isInterrupted()) {
            try {
                connect();
                isConnected = true;
            }
            catch (IOException e) {
                Log.e("ScannerThread", "Problem connecting", e);
            }
        }
        if(isConnected) {
            try {
                listen();
            } catch (IOException e) {
                Log.e("ScannerThread", "Problem reading input", e);
            }
            try {
                Log.i("ScannerThread", "Closing scanner...");
                close();
            }
            catch (IOException e) {
                Log.e("ScannerThread", "Problem closing connection", e);
            }
        }
    }
    private void connect() throws IOException {
        adapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
        if(pairedDevices.size() > 0)
        {
            for(BluetoothDevice device : pairedDevices)
            {
                if(device.getName().equals("Wireless Scanner"))
                {
                    this.device = device;
                    break;
                }
            }
        }
        socket = device.createRfcommSocketToServiceRecord(MY_UUID);
        adapter.cancelDiscovery();
        socket.connect();
        inStream = socket.getInputStream();
    }
    private void close() throws IOException {
        inStream.close();
        socket.close();
    }
    private void listen() throws IOException {
        final byte delimiter = 13; //This is the ASCII code for a newline character
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        while(!currentThread().isInterrupted()) {
            int bytesAvailable = inStream.available();
            if (bytesAvailable > 0) {
                byte[] packetBytes = new byte[bytesAvailable];
                inStream.read(packetBytes);
                for (int i = 0; i < bytesAvailable; i++) {
                    byte b = packetBytes[i];
                    if (b == delimiter) {
                        byte[] encodedBytes = new byte[readBufferPosition];
                        System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                        String data = new String(encodedBytes, "UTF-8");
                        readBufferPosition = 0;
                        getAndPost(data);
                        Log.i("Data read", data);
                    } else {
                        readBuffer[readBufferPosition++] = b;
                    }
                }
            }
        }
    }
    private void getAndPost(String isbn) {
        /*
        AmazonResultData data;
        try {
            URL apiRequestUrl = new URL("http://cascadiaoccidental.com/api/AmazonBookData/" + isbn);
            HttpURLConnection conn = (HttpURLConnection)apiRequestUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoInput(true);
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.connect();
            BufferedReader rdr = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sbr = new StringBuilder();
            String line;
            while ((line = rdr.readLine()) != null) {
                sbr.append(line + "\n");
            }
            data = JsonParser.parse(sbr.toString());
            conn.disconnect();
        }
        catch (Exception e) {
            data = null;
            Log.e("Thread", "problem getting or parsing data", e);
        }
        if (data != null) {
            callback.callback(data);
            Log.i("Thread", "Lowest" + " " + data.lowestPrice + " Target:" + target);
            if(data.lowestPrice > target) {
                vibrator.vibrate(VIBRATE_EXCITED, -1);
            }
            else vibrator.vibrate(VIBRATE_NEGATIVE, -1);
        }
        */
        vibrator.vibrate(VIBRATE_EXCITED, -1);
    }
}
