package com.ppl.fikkrip.itrip.model;

/**
 * Created by Pinky Cindy on 11/21/17.
 */

public class PlanningModel {
    int idPlanning;
    int idUser;
    String judul;
    String status;
    String tglPlanning;
    String note;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTglPlanning() {
        return tglPlanning;
    }

    public void setTglPlanning(String tglPlanning) {
        this.tglPlanning = tglPlanning;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }
}
