package com.ppl.fikkrip.itrip.model;

import java.io.Serializable;

/**
 * Created by Fikkri Prasetya on 11/7/2017.
 */

public class DataWisata implements Serializable {
    private int idWisata;
    private int idProvinsi;
    private String nama;
    private String deskripsi;
    private String kategori;
    private int biayaMasuk;
    private String lokasi;
    private String gambar;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public int getIdWisata() {
        return idWisata;
    }

    public void setIdWisata(int idWisata) {
        this.idWisata = idWisata;
    }

    public int getIdProvinsi() {
        return idProvinsi;
    }

    public void setIdProvinsi(int idProvinsi) {
        this.idProvinsi = idProvinsi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public int getBiayaMasuk() {
        return biayaMasuk;
    }

    public void setBiayaMasuk(int biayaMasuk) {
        this.biayaMasuk = biayaMasuk;
    }
}
