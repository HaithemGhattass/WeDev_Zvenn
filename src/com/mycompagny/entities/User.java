package com.mycompagny.entities;

public class User {

    private int id;
    private String nom,prenom,adresse,numTel,email,dateCreation,pseudo,dateNaissance,password;

    public User(){

    }


    public User(int id, String nom, String prenom, String adresse, String numTel, String email, String dateCreation,String pseudo,String dateNaissance,String password ) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse =adresse;
        this.numTel = numTel;
        this.email = email;
        this.dateCreation =dateCreation;
        this.pseudo = pseudo;
        this.dateNaissance = dateNaissance;
        this.password = password;


    }
    public User( String nom, String prenom, String adresse, String numTel, String email, String dateCreation,String pseudo,String dateNaissance,String password ) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse =adresse;
        this.numTel = numTel;
        this.email = email;
        this.dateCreation =dateCreation;
        this.pseudo = pseudo;
        this.dateNaissance = dateNaissance;
        this.password = password;


    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
}
