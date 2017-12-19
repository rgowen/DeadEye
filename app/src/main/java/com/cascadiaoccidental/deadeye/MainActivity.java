package com.cascadiaoccidental.deadeye;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.cascadiaoccidental.deadeye.data.AmazonResultData;
import java.text.DecimalFormat;
import java.util.UUID;
public class MainActivity extends AppCompatActivity {
    final static String SCANNER_ADDRESS = "00:1C:97:11:04:9E";
    final static UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    boolean serviceRunning;
    DataReceiver receiver;
    DecimalFormat fmt;
    /* Bluetooth variables */
    BluetoothAdapter btAdapter;
    /* Misc UI variables */
    Button buttonToggleService;
    /* Amazon data view */
    ImageView imageViewBookImage;
    TextView textViewBookTitle;
    TextView textViewAmazonPrices;
    EditText editTextTarget;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        receiver = new DataReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SEND);
        registerReceiver(receiver, filter);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        /* UI */
        fmt = new DecimalFormat("#,##0.00");
        imageViewBookImage = (ImageView) findViewById(R.id.imageViewBookImage);
        textViewBookTitle = (TextView) findViewById(R.id.textViewBookTitle);
        textViewAmazonPrices = (TextView) findViewById(R.id.textViewAmazonPrices);
        editTextTarget = (EditText) findViewById(R.id.editTextTarget);
        buttonToggleService = (Button) findViewById(R.id.buttonToggleService);
        clearData();
    }
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void toggleService(View view) {
        if(serviceRunning) {
            Log.d("MainActivity", "Stopping service...");
            Intent intent = new Intent(MainActivity.this, BluetoothScannerService.class);
            //intent.putExtra("Target", Double.parseDouble(editTextTarget.getText().toString()));
            stopService(intent);
            serviceRunning = false;
            editTextTarget.setEnabled(true);
            buttonToggleService.setText("Start Service");
        }
        else if(btAdapter.isEnabled()) {
            Log.d("MainActivity", "Starting service...");
            Intent intent = new Intent(MainActivity.this, BluetoothScannerService.class);
            startService(intent);
            serviceRunning = true;
            editTextTarget.setEnabled(false);
            buttonToggleService.setText("Stop Service");
        }
        else makeToast("Enable Bluetooth you asshole");
    }
    public void makeToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
    public void updateUI(AmazonResultData data) {
        if(data.image!= null) {
            imageViewBookImage.setImageBitmap(BitmapFactory.decodeByteArray(data.image, 0, data.image.length));
        }
        textViewBookTitle.setText(data.title);
        textViewAmazonPrices.setText(getResources().getString(R.string.textview_amazon_prices, fmt.format(data.amazonPrice), fmt.format(data.lowestPrice)));
        Log.i("Activity", "UI updated");
    }
    private void clearData() {
        textViewAmazonPrices.setText("");
        textViewBookTitle.setText("");
        imageViewBookImage.setImageBitmap(null);
    }
    public void test(View view) {
        /* AmazonResultData data = new AmazonResultData();
        data.lowestPrice = 0.01;
        data.amazonPrice = 9.18;
        data.title = "Fifty Shades Darker";
        data.type = "Paperback";
        updateUI(data); */
    }
    public class DataReceiver extends BroadcastReceiver {
        public void onReceive(Context arg0, Intent arg1) {
            AmazonResultData data = (AmazonResultData) arg1.getSerializableExtra("result");
            Log.i("Activity", "Attempting to update UI");
            updateUI(data);
        }
    }
 }