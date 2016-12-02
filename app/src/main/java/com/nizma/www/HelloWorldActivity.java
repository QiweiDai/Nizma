package com.nizma.www;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HelloWorldActivity extends Activity {
    private final int SPLASH_DISPLAY_LENGHT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_world);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent mainIntent = new Intent("com.nizma.www.LoginActivity");
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }
}
