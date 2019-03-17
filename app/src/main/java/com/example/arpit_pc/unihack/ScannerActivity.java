package com.example.arpit_pc.unihack;

import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Camera;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.arpit_pc.unihack.Data.DataContract;
import com.example.arpit_pc.unihack.Data.DataHelper;
import com.google.zxing.Result;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import static android.Manifest.permission.CAMERA;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, AlternateDialog.ExampleDialogListener {

    ZXingScannerView scannerView;
    private static final int REQUEST_CAMERA = 1;
    private static int camId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private String Id;
    //int tripId= Integer.parseInt(getIntent().getStringExtra("tripId"));
    int tripId;
    DataHelper mDbHelper = new DataHelper(this);
    private ArrayList<String> itemIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        int currentApiVersion = Build.VERSION.SDK_INT;
        tripId = getIntent().getIntExtra("tripId",0);
        if(currentApiVersion >=  Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(getApplicationContext(), "Permission already granted!", Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }
        }


        if (itemIds.size() == 0) {
            SQLiteDatabase db = mDbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT DISTINCT ITEMID FROM TABLE_ITEM", null);

            try {
                int idColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_ITEMID);
                while (cursor.moveToNext()) {
                    String itemID = cursor.getString(idColumnIndex);
                    itemIds.add(itemID);
                }
            } finally {
                cursor.close();
            }
        }
    }

    private boolean checkPermission()
    {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission(){
            ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @Override
    public void handleResult(Result result) {
        Id = result.getText();
        if (itemIds.contains(Id)){
            openDialog();
        }
        else{
            Toast.makeText(getApplicationContext(), "Data not present for this item!", Toast.LENGTH_LONG).show();
            onBackPressed();
        }

    }

    public void addItem(){

        SQLiteDatabase db1 = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataContract.DataEntry.COLUMN_ITEMID_TEMP,Id);
        values.put(DataContract.DataEntry.COLUMN_TRIPID_TEMP,tripId);
        long newRowId = db1.insert(DataContract.DataEntry.TABLE_NAME_TEMP, null, values);
        if (newRowId > 0){
            Toast.makeText(getApplicationContext(), "Item added", Toast.LENGTH_LONG).show();
        }


    }

    public void openDialog(){
        AlternateDialog dialog = new AlternateDialog();
        dialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void sendResult(int result) {
        if (result == 1){
            addItem();
            onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if(scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }
}
