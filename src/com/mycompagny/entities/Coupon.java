package com.mycompagny.entities;

public class Coupon  {

    private int id;
    private int code;
    private String dateLimite;
    private int remise;
    private int iduser;
    private String user;

    public Coupon() {
    }

    public Coupon(int code, String dateLimite, int remise) {
        this.code = code;
        this.dateLimite = dateLimite;
        this.remise = remise;
    }

    public Coupon(int id,String user, int code, String dateLimite, int remise) {
        this.id = id;
        this.user = user;
        this.code = code;
        this.dateLimite = dateLimite;
        this.remise = remise;
    }

    public Coupon(int code, String dateLimite, int remise, int iduser) {
        this.code = code;
        this.dateLimite = dateLimite;
        this.remise = remise;
        this.iduser = iduser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDateLimite() {
        return dateLimite;
    }

    public void setDateLimite(String dateLimite) {
        this.dateLimite = dateLimite;
    }

    public int getRemise() {
        return remise;
    }

    public void setRemise(int remise) {
        this.remise = remise;
    }

    public int getIduser() {return iduser;}

    public void setIduser(int iduser) {this.iduser = iduser;}

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
