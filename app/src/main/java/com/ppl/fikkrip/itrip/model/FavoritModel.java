package com.ppl.fikkrip.itrip.model;

/**
 * Created by Pinky Cindy on 12/17/17.
 */

public class FavoritModel {
    private int idWisata;
    private int idProvinsi;
    private String namaProvinsi;
    private String namaWisata;
    private String deskripsiWisata;
    private String kategori;
    private int biayaMasuk;
    private String longtitude;
    private String latitude;
    private String lokasi;
    private String gambar;
    private String status;
    private String idUser;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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

    public String getNamaProvinsi() {
        return namaProvinsi;
    }

    public void setNamaProvinsi(String namaProvinsi) {
        this.namaProvinsi = namaProvinsi;
    }

    public String getNamaWisata() {
        return namaWisata;
    }

    public void setNamaWisata(String namaWisata) {
        this.namaWisata = namaWisata;
    }

    public String getDeskripsiWisata() {
        return deskripsiWisata;
    }

    public void setDeskripsiWisata(String deskripsiWisata) {
        this.deskripsiWisata = deskripsiWisata;
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

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
