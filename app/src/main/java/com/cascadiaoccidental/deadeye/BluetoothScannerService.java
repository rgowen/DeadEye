package com.cascadiaoccidental.deadeye;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.cascadiaoccidental.deadeye.data.AmazonResultData;

public class BluetoothScannerService extends Service {
    private static final int ONGOING_NOTIFICATION = 1;
    Thread thread;
    GenericCallback c;
    Vibrator v;
    double target;

    public int onStartCommand(Intent intent, int flags, int startId) {
        v = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        target = intent.getDoubleExtra("Target", 0.0);
        Log.i("Service", Double.toString(target));
        c = new GenericCallback() {
            @Override
            public void callback(AmazonResultData data) {
                Intent callbackIntent = new Intent();
                callbackIntent.putExtra("result", data);
                callbackIntent.setAction(Intent.ACTION_SEND);
                Log.i("Service", "Attempting to broadcast");
                sendBroadcast(callbackIntent);
            }
        };
        thread = new Thread(new ScannerThread(c, v, target));
        thread.start();
        Notification notification = new NotificationCompat.Builder(this).setContentTitle("DeadEye").setContentText("Running...").build();
        startForeground(ONGOING_NOTIFICATION, notification);
        return START_NOT_STICKY;
    }
    public void onDestroy() {
        thread.interrupt();
    }
    public IBinder onBind(Intent intent) {
        return null;
    }
}
