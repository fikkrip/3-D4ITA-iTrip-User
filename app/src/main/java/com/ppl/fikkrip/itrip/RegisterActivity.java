package com.ppl.fikkrip.itrip;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activity Register berhubungan dengan apa yang ada pada layout activity_register
 * Untuk mengatur aktivitas Registrasi pada Aplikasi
 *
 * Created by Fikkri Prasetya on 9/24/2017.
 */

public class RegisterActivity extends AppCompatActivity {

    Button buttonRegis;
    EditText etEmail, etUsername, etPassword, etNama, etRePassword;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNama = (EditText) findViewById(R.id.nama);
        etUsername = (EditText) findViewById(R.id.username);
        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);
        etRePassword = (EditText) findViewById(R.id.rePassword);
        buttonRegis = (Button) findViewById(R.id.buttonRegis);

        buttonRegis.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nama = etNama.getText().toString();
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String rePassword = etRePassword.getText().toString();


                if (nama.length() == 0) {
                    //jika form Email belum di isi / masih kosong
                    etNama.setError("Please Enter your Name!");
                    etNama.requestFocus();
                } else if (username.length() == 0) {
                    //jika form Username belum di isi / masih kosong
                    etUsername.setError("Please Enter your Username!");
                    etUsername.requestFocus();
                } else if ((email.length() == 0) || !(Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
                    //jika form Email belum di isi / penulisan email salah
                    etEmail.setError("Please Enter your Valid Email!");
                    etEmail.requestFocus();
                } else if (password.length() == 0) {
                    //jika form Password belum di isi / masih kosong
                    etPassword.requestFocus();
                    Toast.makeText(RegisterActivity.this, "Please Enter your Password!", Toast.LENGTH_SHORT).show();
                } else if (rePassword.length() == 0) {
                    //jika form Password belum di isi / masih kosong
                    etRePassword.requestFocus();
                    Toast.makeText(RegisterActivity.this, "Please Enter your Re-Type Password!", Toast.LENGTH_SHORT).show();
                } else if (!rePassword.equals(password)) {
                    //jika form RePassword tidak sama dengan Password
                    etRePassword.requestFocus();
                    Toast.makeText(RegisterActivity.this, "Please Enter same Password!", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog = new ProgressDialog(RegisterActivity.this);
                    progressDialog.setTitle("Please Wait");
                    progressDialog.show();
                    progressDialog.setMessage("Processing...");

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Registrasi Berhasil!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    RegisterActivity.this.startActivity(intent);
                                } else {
                                    progressDialog.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("Username is Available, change the username!")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    RegisterRequest registerRequest = new RegisterRequest(nama, username, email, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
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