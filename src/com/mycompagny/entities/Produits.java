package com.mycompagny.entities;

public class Produits {
    private int id;
    private float prix;
    private String nom;
    private String descriptionProduit;
    private String createdAt;
    private String imageProduit;
    private String nomImage;


    public Produits() {
    }
    public Produits(int id, String nom, String descriptionProduit, float prix, String nomImage, String createdAt ) {
        this.id = id;
        this.nom = nom;
        this.descriptionProduit = descriptionProduit;
        this.createdAt = createdAt;
        this.prix = prix;
       // this.imageProduit = imageProduit;
        this.nomImage = nomImage;
    }
    public Produits( String nom, String descriptionProduit, float prix,String imageProduit, String nomImage, String createdAt ) {
        this.nom = nom;
        this.descriptionProduit = descriptionProduit;
        this.createdAt = createdAt;
        this.prix = prix;
        this.imageProduit = imageProduit;
        this.nomImage = nomImage;

    }

    public Produits(float prix, String nom) {
        this.prix = prix;
        this.nom = nom;
    }

    public String getImageProduit() {
        return imageProduit;
    }

    public void setImageProduit(String imageProduit) {
        this.imageProduit = imageProduit;
    }

    public String getNomImage() {
        return nomImage;
    }

    public void setNomImage(String nomImage) {
        this.nomImage = nomImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrix() {
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
