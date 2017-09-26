package com.ppl.fikkrip.itrip;

/**
 * Class User digunakan untuk menginisialisasi data user
 *
 * Created by Fikkri Prasetya on 9/24/2017.
 */

public class User {
    String nama, username, email, password;

    public User(String nama, String username, String email, String password) {
        this.nama = nama;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
