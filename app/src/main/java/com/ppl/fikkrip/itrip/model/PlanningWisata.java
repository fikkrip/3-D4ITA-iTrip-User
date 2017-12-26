package com.ppl.fikkrip.itrip.model;

/**
 * Created by Fikkri Prasetya on 11/8/2017.
 */

public class PlanningWisata {
    private int idPlanning;
    private int idUser;
    private String judul;
    private String status;
    private String tglPlanning;

    public String getTglPlanning() {
        return tglPlanning;
    }

    public void setTglPlanning(String tglPlanning) {
        this.tglPlanning = tglPlanning;
    }

    private String note;

    public int getIdPlanning() {
        return idPlanning;
    }

    public void setIdPlanning(int idPlanning) {
        this.idPlanning = idPlanning;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
