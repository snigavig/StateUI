package com.goodcodeforfun.stateuidemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.simpleExampleButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SimpleActivity.class);
                ContextCompat.startActivities(MainActivity.this, new Intent[]{intent});
            }
        });
        findViewById(R.id.customProgressExampleButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CustomProgressActivity.class);
                ContextCompat.startActivities(MainActivity.this, new Intent[]{intent});
            }
        });
    }
}
