package com.example.arpit_pc.unihack.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.arpit_pc.unihack.Data.DataContract.DataEntry;

public class DataHelper extends SQLiteOpenHelper {

    /** Name of the database file */
    private static final String DATABASE_NAME = "items.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 2;


    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_ITEM_TABLE =  "CREATE TABLE " + DataEntry.TABLE_NAME+ " ("
                + DataEntry.COLUMN_ITEMID + " INTEGER PRIMARY KEY, "
                + DataEntry.COLUMN_ITEMCO2 + " NUMBER , "
                + DataEntry.COLUMN_ITEMPRICE + " NUMBER, "
                + DataEntry.COLUMN_ITEMNAME + " TEXT , "
                + DataEntry.COLUMN_ITEMTYPEID + " TEXT );";

        String SQL_CREATE_TEMP_TABLE =  "CREATE TABLE " + DataEntry.TABLE_NAME_TEMP+ " ("
                + DataEntry.COLUMN_ITEMID_TEMP + " INTEGER, "
                + DataEntry.COLUMN_TRIPID_TEMP + " INTEGER );";


        String SQL_CREATE_P_TABLE =  "CREATE TABLE " + DataEntry.TABLE_NAME_P+ " ("
                + DataEntry.COLUMN_P_ID + " INTEGER, "
                + DataEntry.COLUMN_P_TRIPID + " INTEGER, "
                + DataEntry.COLUMN_P_ITEMID + " INTEGER,"
                + DataEntry.COLUMN_P_DATE + ");";


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_ITEM_TABLE);
        db.execSQL(SQL_CREATE_TEMP_TABLE);
        db.execSQL(SQL_CREATE_P_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
        db.execSQL("DROP TABLE IF EXISTS " + DataEntry.TABLE_NAME +";" );
        db.execSQL("DROP TABLE IF EXISTS " + DataEntry.TABLE_NAME_TEMP +";" );
        db.execSQL("DROP TABLE IF EXISTS " + DataEntry.TABLE_NAME_P +";" );
        onCreate(db);
    }
}
