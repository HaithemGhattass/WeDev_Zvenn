/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompagny.entities;

/**
 *
 * @author mtar
 */
public class Reclamation {
    private int id,foodqulaite,price,service;
    private String titre,description,etat;
    private String date,image,nomimage;
    private int idres;
    String user;
    private int idUser;


    public Reclamation(String user,int idUser,int foodqulaite, int price, int service, String titre, String description, String etat, String date, int idres) {
        this.foodqulaite = foodqulaite;
        this.price = price;
        this.service = service;
        this.titre = titre;
        this.description = description;
        this.etat = etat;
        this.date = date;
        this.idres = idres;
        this.user = user;
        this.idUser=idUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getIdres() {
        return idres;
    }

    public void setIdres(int idres) {
        this.idres = idres;
    }

    public Reclamation() {

    }

    public Reclamation( String titre, String description, String etat, String date) {

        this.titre = titre;
        this.description = description;
        this.etat = etat;
        this.date = date;
    }

    public Reclamation(String titre, String description, String etat, String date, String image, String nomimage) {
        this.titre = titre;
        this.description = description;
        this.foodqulaite = foodqulaite;
        this.price = price;
        this.service = service;
        this.etat = etat;
        this.date = date;
        this.image=image;
        this.nomimage=nomimage;
    }

    public int getFoodqulaite() {
        return foodqulaite;
    }

    public void setFoodqulaite(int foodqulaite) {
        this.foodqulaite = foodqulaite;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getDate() {
        return date;
    }


    public void setDate(String date) {
        this.date = date;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNomimage() {
        return nomimage;
    }

    public void setNomimage(String nomimage) {
        this.nomimage = nomimage;
    }



}
