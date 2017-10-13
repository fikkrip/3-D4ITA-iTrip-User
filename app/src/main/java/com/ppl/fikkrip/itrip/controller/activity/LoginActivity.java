package com.ppl.fikkrip.itrip.controller.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.ppl.fikkrip.itrip.request.LoginRequest;
import com.ppl.fikkrip.itrip.R;
import com.ppl.fikkrip.itrip.sharedpreference.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activity Login berhubungan dengan apa yang ada pada layout activity_login
 * Untuk mengatur aktivitas Login pada Aplikasi
 *
 * Created by Fikkri Prasetya on 9/24/2017.
 */

public class LoginActivity extends AppCompatActivity{

    Button buttonLogin;
    EditText etUsername, etPassword;
    TextView toRegister;
    SessionManager session;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.username);
        etPassword = (EditText) findViewById(R.id.password);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        toRegister = (TextView) findViewById(R.id.toRegister);
        session = new SessionManager(getApplicationContext());

        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                if (username.length() == 0) {
                    //jika form Username belum di isi / masih kosong
                    etUsername.setError("Please Enter your Username!");
                    etUsername.requestFocus();
                } else if (password.length() == 0) {
                    //jika form Passwrod belum di isi / masih kosong
                    etPassword.requestFocus();
                    Toast.makeText(LoginActivity.this, "Please Enter your Password!", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setTitle("Please Wait");
                    progressDialog.setMessage("Processing...");
                    progressDialog.show();

                    // Response received from the server
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    progressDialog.dismiss();
                                    String nama = jsonResponse.getString("nama");
                                    String email = jsonResponse.getString("email");
                                    String username = etUsername.getText().toString();

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("nama", nama);
                                    intent.putExtra("email", email);
                                    intent.putExtra("username", username);
                                    LoginActivity.this.startActivity(intent);
                                    session.createLoginSession(nama, username, email);
                                    finish();
                                } else {
                                    progressDialog.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("Login Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    LoginRequest loginRequest = new LoginRequest(username, password, getString(R.string.api)+"Login.php", responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}
