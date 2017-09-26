package com.ppl.fikkrip.itrip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * Activity Register berhubungan dengan apa yang ada pada layout activity_register
 * Untuk mengatur aktivitas Registrasi pada Aplikasi
 *
 * Created by Fikkri Prasetya on 9/24/2017.
 */

public class RegisterActivity extends Activity implements View.OnClickListener{

    Button buttonRegis;
    EditText etEmail, etUsername, etPassword, etNama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        etNama = (EditText) findViewById(R.id.nama);
        etUsername = (EditText) findViewById(R.id.username);
        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);
        buttonRegis = (Button) findViewById(R.id.buttonRegis);

        buttonRegis.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.buttonRegis:

                String nama = etNama.getText().toString();
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                User user = new User(nama, username, email, password);

                registerUser(user);
                break;
        }
    }

    public void registerUser(User user){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.storeUserDataInBackground(user, new GetUserCallBack() {
            @Override
            public void done(User returnedUser) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}
