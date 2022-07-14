package com.example.uasmobile.model;

public class ModelDaftarKuliah {

    private String id, nama, kelas, hari, jamAwal, jamAkhir;

    public ModelDaftarKuliah() {
    }

    public ModelDaftarKuliah(String nama, String kelas, String hari, String jamAwal, String jamAkhir) {
        this.nama = nama;
        this.kelas = kelas;
        this.hari = hari;
        this.jamAwal = jamAwal;
        this.jamAkhir = jamAkhir;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getJamAwal() {
        return jamAwal;
    }

    public void setJamAwal(String jamAwal) {
        this.jamAwal = jamAwal;
    }

    public String getJamAkhir() {
        return jamAkhir;
    }

    public void setJamAkhir(String jamAkhir) {
        this.jamAkhir = jamAkhir;
    }
}
