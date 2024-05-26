package com.example.projectreaderapp.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    private int hinh;
    private String tensp;
    private double soluong;
    private double dongia;

    public SanPham(int hinh, String tensp, double soluong, double dongia) {
        this.hinh = hinh;
        this.tensp = tensp;
        this.soluong = soluong;
        this.dongia = dongia;
    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public double getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public double getDongia() {
        return dongia;
    }

    public void setDongia(double dongia) {
        this.dongia = dongia;
    }

    public SanPham() {
    }
}
