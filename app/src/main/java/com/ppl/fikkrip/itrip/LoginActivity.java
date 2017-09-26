package com.ppl.fikkrip.itrip;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity Login berhubungan dengan apa yang ada pada layout activity_login
 * Untuk mengatur aktivitas Login pada Aplikasi
 *
 * Created by Fikkri Prasetya on 9/24/2017.
 */

public class LoginActivity extends Activity implements View.OnClickListener{

    Button buttonLogin;
    EditText etUsername, etPassword;
    TextView toRegister;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.username);
        etPassword = (EditText) findViewById(R.id.password);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        toRegister = (TextView) findViewById(R.id.toRegister);

        buttonLogin.setOnClickListener(this);
        toRegister.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.buttonLogin:

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                User user = new User(username, password);

                authenticate(user);
                break;
            case R.id.toRegister:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    private void authenticate(User user){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.fetchUserDataInBackground(user, new GetUserCallBack() {
            @Override
            public void done(User returnedUser) {
                if(returnedUser == null){
                    showErrorMessage();
                } else{
                    System.out.println(returnedUser);
                    logUserIn(returnedUser);
                }
            }
        });
    }

    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        dialogBuilder.setMessage("Incorrect User Details!");
        dialogBuilder.setPositiveButton("Oke", null);
        dialogBuilder.show();
    }

    private void logUserIn(User returnedUser){
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);

        startActivity(new Intent(this, MainActivity.class));
    }


}
