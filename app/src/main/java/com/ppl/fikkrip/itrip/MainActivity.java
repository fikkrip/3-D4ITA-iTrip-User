package com.ppl.fikkrip.itrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button buttonLogout;
    TextView loginSuccess;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        loginSuccess = (TextView) findViewById(R.id.loginSuccess);

        buttonLogout.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(authenticate()==true){
            loginSuccess.setText("Login Berhasil");
        }else{
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

    }

    private boolean authenticate(){
        return userLocalStore.getUserLoggedIn();
    }


    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.buttonLogout:
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);

                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}