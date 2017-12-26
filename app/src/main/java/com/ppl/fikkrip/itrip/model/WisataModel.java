package com.ppl.fikkrip.itrip.model;

/**
 * Created by Pinky Cindy on 12/20/17.
 */

public class WisataModel {
    private int idWisata;
    private int idProvinsi;
    private String nama;
    private String deskripsi;
    private String kategori;
    private int biayaMasuk;
    private String lokasi;
    private String gambar;
    private double latitude;
    private double longtitude;

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

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }
}
