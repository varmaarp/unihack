package com.example.arpit_pc.unihack;

import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataAccess {
    Connection connect;
    String ConnectionResult = "";
    Boolean isSuccess = false;

    //public List<Map<String,String>> doInBackground() {
    public String doInBackground() {

        List<Map<String, String>> data = null;
        data = new ArrayList<Map<String, String>>();
        try
        {
            ConnectionHelper conStr=new ConnectionHelper();
            connect =conStr.connectionclasss();        // Connect to database
            if (connect == null)
            {
                ConnectionResult = "Check Your Internet Access!";
            }
            else
            {
                // Change below query according to your own database.
                /*
                String query = "select * from countries";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()){
                    Map<String,String> datanum=new HashMap<String,String>();
                    datanum.put("ID",rs.getString("CountryId"));
                    datanum.put("Country",rs.getString("CountryName"));
                    datanum.put("Capital",rs.getString("CapitalCity"));
                    data.add(datanum);
                }
                */

                ConnectionResult = " successful";
                isSuccess=true;
                connect.close();
            }
            Log.e("test",ConnectionResult);
        }
        catch (Exception ex)
        {
            isSuccess = false;
            ConnectionResult = ex.getMessage();
        }

        return ConnectionResult;
    }
}
