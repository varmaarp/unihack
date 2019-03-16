package com.example.arpit_pc.unihack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ShoppingListActivity extends AppCompatActivity {

    public static TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        resultText = (TextView) findViewById(R.id.resultId);

        ImageView imgCamera = findViewById(R.id.camId);

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScannerActivity.class);
                startActivity(intent);
            }
        });
    }

}
