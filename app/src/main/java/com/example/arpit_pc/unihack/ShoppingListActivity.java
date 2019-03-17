package com.example.arpit_pc.unihack;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.arpit_pc.unihack.Data.DataContract;
import com.example.arpit_pc.unihack.Data.DataHelper;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {

    public static TextView resultText;
    //String tripId= getIntent().getStringExtra("tripId");
    //int tripId = 0;
    int tripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        tripId= getIntent().getIntExtra("tripId",0);
        //resultText = (TextView) findViewById(R.id.resultId);

        ImageView imgCamera = findViewById(R.id.camId);

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScannerActivity.class);
                intent.putExtra("tripId",tripId);
                startActivity(intent);
            }
        });

        GetData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetData();
        GetTotal();
    }

    public void GetTotal(){
        DataHelper mDbHelper = new DataHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Float val = 0f;

        Cursor cursor = db.rawQuery("SELECT sum(ITEMCO2) as total FROM TABLE_ITEM" +
                " WHERE ITEMID in (SELECT itemid from table_temp where tripid = " + Integer.toString(tripId) + ")", null);

        if (cursor.moveToFirst()) {

            val = cursor.getFloat(cursor.getColumnIndex("total"));
        }

        TextView totalVal = (TextView) findViewById(R.id.totalId);
        totalVal.setText("Total Carbon Footprint - " + Float.toString(val) + " Kg");
    }

    private void GetData(){
        DataHelper mDbHelper = new DataHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        //Cursor cursor = db.rawQuery("SELECT * FROM " + DataContract.DataEntry.TABLE_NAME_TEMP, null);
        Cursor cursor = db.rawQuery("SELECT DISTINCT ITEMID,ITEMCO2,ITEMNAME FROM TABLE_ITEM" +
                " WHERE ITEMID in (SELECT itemid from table_temp where tripid = " + Integer.toString(tripId) + ")", null);
        try {

            int idColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_ITEMID);
            int nameColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_ITEMNAME);
            int carbonColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_ITEMCO2);
            ArrayList<Item> items = new ArrayList<Item>();

            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                String itemID = cursor.getString(idColumnIndex);
                //String itemName = "test";
                //Float itemCarbonValue = 25.0f;
                String itemName = cursor.getString(nameColumnIndex);
                Float itemCarbonValue = cursor.getFloat(carbonColumnIndex);
                items.add(new Item(itemName, itemID, itemCarbonValue));
            }

            ItemAdapter adapter = new ItemAdapter(this, items);

            ListView listView = (ListView) findViewById(R.id.listId);

            listView.setAdapter(adapter);

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

}
