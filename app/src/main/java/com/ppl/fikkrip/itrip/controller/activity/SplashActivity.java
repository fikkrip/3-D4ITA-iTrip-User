package com.ppl.fikkrip.itrip.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ppl.fikkrip.itrip.R;
import com.ppl.fikkrip.itrip.sharedpreference.SessionManager;

/**
 * Activity Splash berhubungan dengan apa yang ada pada layout activity_login
 * Untuk mengatur tampilan awal saat masuk Aplikasi
 *
 * Created by Fikkri Prasetya on 9/24/2017.
 */

public class SplashActivity extends AppCompatActivity {

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread = new Thread() {
            public void run(){
                try{
                    sleep(2500);

                }catch(InterruptedException e){
                    e.printStackTrace();
                } finally {
                    session = new SessionManager(SplashActivity.this);
                    if(session.isLoggedIn()) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }else{
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            }
        };
        thread.start();
    }
}
