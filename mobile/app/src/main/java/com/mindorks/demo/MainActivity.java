package com.mindorks.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.button)).setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                AttractionsClick(null);
            }
        });

        ((Button)findViewById(R.id.button2)).setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                ChatClick(null);
            }
        });

        Intent msgIntent = new Intent(this, NotificationChecker.class);
    }

    public void AttractionsClick(View v)
    {
        //startActivity(new Intent(MainActivity.this, ActivityTinder.class));

    }

    public void ChatClick(View v)
    {
        startActivity(new Intent(MainActivity.this, ChatActivity.class));
    }
}
