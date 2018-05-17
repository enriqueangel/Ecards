package com.woldev.enriq.ecards;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ActivitySplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences SP = getSharedPreferences("PIN",MODE_PRIVATE);


                boolean HayUsuario = SP.getBoolean("User",false);

                if (HayUsuario){
                    Intent intent = new Intent(ActivitySplashScreen.this, ActivityLoginPin.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(ActivitySplashScreen.this, ActivityPrincipal.class);
                    startActivity(intent);
                }

                //Intent intent = new Intent(ActivitySplashScreen.this, Dashboard.class);
                //startActivity(intent);

                finish();
            }
        },3000);
    }
}
