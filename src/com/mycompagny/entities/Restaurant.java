/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompagny.entities;

import java.util.Date;

/**
 *
 * @author haithem
 */
public class Restaurant {

    private int id;
    private String nom,description,addresse,cite,heureOuverture,categorieRestaurant,heureFermeture,gouvernorat;
    private int codePostal;
    private String createdAt;
    private String imageRestaurant;
    private String nomImage;
    private String status;
    private int idUser;

    public Restaurant(){

    }


    public Restaurant(int id, String nom, String description, String addresse, String cite, String heureOuverture, String categorieRestaurant, String heureFermeture, String gouvernorat, int codePostal, String createdAt, String imageRestaurant, String nomImage, String status) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.addresse = addresse;
        this.cite = cite;
        this.heureOuverture = heureOuverture;
        this.categorieRestaurant = categorieRestaurant;
        this.heureFermeture = heureFermeture;
        this.gouvernorat = gouvernorat;
        this.codePostal = codePostal;
        this.createdAt = createdAt;
        this.imageRestaurant = imageRestaurant;
        this.nomImage = nomImage;
        this.status = status;
    }

    public Restaurant(int idUser, String nom, String categorieRestaurant, String description, String gouvernorat, String addresse, String cite, int codePostal, String heureOuverture, String heureFermeture, String imageRestaurant, String nomImage, String status, String createdAt) {
        this.nom = nom;
        this.description = description;
        this.createdAt = createdAt;
        this.addresse = addresse;
        this.cite = cite;
        this.codePostal = codePostal;
        this.heureOuverture = heureOuverture;
        this.categorieRestaurant = categorieRestaurant;
        this.heureFermeture = heureFermeture ;
        this.gouvernorat = gouvernorat ;
        this.imageRestaurant = imageRestaurant;
        this.nomImage = nomImage;
        this.status= status;
        this.idUser= idUser;


    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }



    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getAddresse() {
        return addresse;
    }
    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }
    public String getCite() {
        return cite;
    }
    public void setCite(String cite) {
        this.cite = cite;
    }
    public int getCodePostal() {
        return codePostal;
    }
    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }
    public String getHeureOuverture() {
        return heureOuverture;
    }
    public void setHeureOuverture(String heureOuverture) {
        this.heureOuverture = heureOuverture;
    }
    public String getCategorieRestaurant() {
        return categorieRestaurant;
    }
    public void setCategorieRestaurant(String categorieRestaurant) {
        this.categorieRestaurant = categorieRestaurant;
    }

    public String getHeureFermeture() {
        return heureFermeture;
    }

    public void setHeureFermeture(String heureFermeture) {
        this.heureFermeture = heureFermeture;
    }

    public String getGouvernorat() {
        return gouvernorat;
    }

    public void setGouvernorat(String gouvernorat) {
        this.gouvernorat = gouvernorat;
    }

    public String getImageRestaurant() {
        return imageRestaurant;
    }

    public void setImageRestaurant(String imageRestaurant) {
        this.imageRestaurant = imageRestaurant;
    }

    public String getNomImage() {
        return nomImage;
    }

    public void setNomImage(String nomImage) {
        this.nomImage = nomImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
