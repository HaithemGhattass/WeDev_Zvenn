package com.mycompagny.entities;


public class Commande {

    private int id;
    private String adresseLivraison;
    private Float totalCommande;
    private String modeLivraison;
    private String renseignement;
    private boolean status;
    private int iduser;
    private String user;

    public Commande() {
    }

    public Commande(String adresseLivraison, Float totalCommande, String modeLivraison, String renseignement, boolean status) {
        this.adresseLivraison = adresseLivraison;
        this.totalCommande = totalCommande;
        this.modeLivraison = modeLivraison;
        this.renseignement = renseignement;
        this.status = status;
    }

    public Commande(int id, String user,String adresseLivraison, Float totalCommande, String modeLivraison, String renseignement, boolean status) {
        this.id = id;
        this.user = user;
        this.adresseLivraison = adresseLivraison;
        this.totalCommande = totalCommande;
        this.modeLivraison = modeLivraison;
        this.renseignement = renseignement;
        this.status = status;
    }

    public Commande(String adresseLivraison, Float totalCommande, String modeLivraison, String renseignement, boolean status, int iduser) {
        this.adresseLivraison = adresseLivraison;
        this.totalCommande = totalCommande;
        this.modeLivraison = modeLivraison;
        this.renseignement = renseignement;
        this.status = status;
        this.iduser = iduser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdresseLivraison() {
        return adresseLivraison;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
    }

    public Float getTotalCommande() {
        return totalCommande;
    }

    public void setTotalCommande(Float totalCommande) {
        this.totalCommande = totalCommande;
    }

    public String getModeLivraison() {
        return modeLivraison;
    }

    public void setModeLivraison(String modeLivraison) {
        this.modeLivraison = modeLivraison;
    }

    public String getRenseignement() {
        return renseignement;
    }

    public void setRenseignement(String renseignement) {
        this.renseignement = renseignement;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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
