package com.mycompagny.entities;

public class Produits {
    private int id,prix;
    private String nom;
    private String descriptionProduit;
    private String createdAt;


    public Produits() {
    }
    public Produits(int id, String nom, String descriptionProduit, int prix, String createdAt ) {
        this.id = id;
        this.nom = nom;
        this.descriptionProduit = descriptionProduit;
        this.createdAt = createdAt;
        this.prix = prix;
    }
    public Produits( String nom, String descriptionProduit, int prix, String createdAt ) {
        this.nom = nom;
        this.descriptionProduit = descriptionProduit;
        this.createdAt = createdAt;
        this.prix = prix;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescriptionProduit() {
        return descriptionProduit;
    }

    public void setDescriptionProduit(String descriptionProduit) {
        this.descriptionProduit = descriptionProduit;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
