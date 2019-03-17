package com.example.arpit_pc.unihack.Data;

import android.provider.BaseColumns;

public final class DataContract {

    private DataContract(){}

    public static final class DataEntry implements BaseColumns{

        public final static String TABLE_NAME_P = "tblpurchase";
        public final static String COLUMN_P_ID = "purchaseid";
        public final static String COLUMN_P_TRIPID = "purchasetripid";
        public final static String COLUMN_P_ITEMID = "purchaseitemid";

        public final static String TABLE_NAME = "table_item";
        public final static String COLUMN_ITEMID = "itemid";
        public final static String COLUMN_ITEMCO2 = "itemco2";
        public final static String COLUMN_ITEMPRICE = "itemprice";
        public final static String COLUMN_ITEMNAME = "itemname";
        public final static String COLUMN_ITEMTYPEID = "itemtypeid";

        public final static String TABLE_NAME_TEMP = "table_temp";
        public final static String COLUMN_ITEMID_TEMP = "itemid";
        public final static String COLUMN_TRIPID_TEMP= "tripid";
        public final static String COLUMN_P_DATE ="date";

    }
}
