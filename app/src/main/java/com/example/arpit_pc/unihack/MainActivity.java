package com.example.arpit_pc.unihack;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arpit_pc.unihack.Data.DataContract;
import com.example.arpit_pc.unihack.Data.DataHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private int tripID;
    BarChart barChart;
    DataHelper mDbHelper = new DataHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();
        addChart();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabButton);
        final DataAccess da = new DataAccess();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insertData();
                //displayDatabaseInfo();

                int id = getTempId();
                id++;
                //insertData();
                //deleteData();
                Intent intent = new Intent(getApplicationContext(), ShoppingListActivity.class);
                intent.putExtra("tripId",id);
                startActivity(intent);
                //TextView chart = (TextView) findViewById(R.id.chartId);
                //chart.setText(da.doInBackground());

                //displayDatabaseInfo();

            }
        });

        ImageView edit = (ImageView) findViewById(R.id.editId);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = getTempId();
                if (id > 0) {
                    Intent intent = new Intent(getApplicationContext(), ShoppingListActivity.class);
                    intent.putExtra("tripId", id);
                    startActivity(intent);
                }
            }
        });
    }


    /*
    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.


        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        //Cursor cursor = db.rawQuery("SELECT * FROM " + DataContract.DataEntry.TABLE_NAME, null);
        Cursor cursor = db.rawQuery("SELECT * FROM " + DataContract.DataEntry.TABLE_NAME_P, null);
        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            TextView chart = (TextView) findViewById(R.id.chartId);
            chart.setText("Number of rows in pets database table: " + cursor.getCount());
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    private void insertData() {

        SQLiteDatabase db1 = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(DataContract.DataEntry.COLUMN_ITEMCO2,17);
        values.put(DataContract.DataEntry.COLUMN_ITEMID,"90162602");
        values.put(DataContract.DataEntry.COLUMN_ITEMNAME,"Red Bull");
        values.put(DataContract.DataEntry.COLUMN_ITEMPRICE,3.50);
        values.put(DataContract.DataEntry.COLUMN_ITEMTYPEID,"4");

        long newRowId = db1.insert(DataContract.DataEntry.TABLE_NAME, null, values);

        Log.e("val", Long.toString(newRowId));

    }

    private void deleteData(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL("delete from "+ DataContract.DataEntry.TABLE_NAME_TEMP);
    }
    */

    private int getTempId(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select max(tripid) as val from table_temp", null);
        int id = 0;
        if (cursor.moveToFirst()) {

            id = cursor.getInt(cursor.getColumnIndex("val"));
            //id = 0;
            Log.e("cursor val", Integer.toString(id));
        }

        Log.e("cursor",Integer.toString(cursor.getCount()));

        return id;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void addChart(){
        barChart = (BarChart) findViewById(R.id.barGraphId);

        ArrayList<BarEntry> barEntries= new ArrayList<>();

        barEntries.add(new BarEntry(44f,0));
        barEntries.add(new BarEntry(88f,1));
        barEntries.add(new BarEntry(66f,2));
        barEntries.add(new BarEntry(13f,3));
        BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");

        ArrayList<String> theDates = new ArrayList<>();

        theDates.add("October");
        theDates.add("November");
        theDates.add("December");
        theDates.add("January");

        BarData data = new BarData(theDates, barDataSet);
        barChart.setData(data);

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
    }

    private void getData(){
        int id = getTempId();

        DataHelper mDbHelper = new DataHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Float val = 0f;

        Cursor cursor = db.rawQuery("SELECT sum(ITEMCO2) as total FROM TABLE_ITEM" +
                " WHERE ITEMID in (SELECT itemid from table_temp where tripid = " + Integer.toString(id) + ")", null);

        if (cursor.moveToFirst()) {

            val = cursor.getFloat(cursor.getColumnIndex("total"));
        }

        TextView totalVal = (TextView) findViewById(R.id.totalCo2Id);
        totalVal.setText("Total Carbon Footprint - " + Float.toString(val));
    }
}
