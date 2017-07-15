package com.sprinjinc.bestfriend;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LogoActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        mHandler.postDelayed(mUpdateTimeTask, 3000);
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            Intent homeIntent = new Intent(LogoActivity.this, HomeActivity.class);
            startActivity(homeIntent);
        }
    };
}
