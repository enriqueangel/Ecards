package com.woldev.enriq.ecards;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

public class Splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences SP = getSharedPreferences("PIN",MODE_PRIVATE);


                boolean HayUsuario = SP.getBoolean("User",false);

                if (HayUsuario){
                    Intent intent = new Intent(Splash_screen.this, login_pin.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(Splash_screen.this, MainActivity.class);
                    startActivity(intent);
                }

                //Intent intent = new Intent(Splash_screen.this, Dashboard.class);
                //startActivity(intent);

                finish();
            }
        },3000);
    }
}
