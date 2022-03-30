/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompagny.gui;

import com.codename1.io.Preferences;
import com.mycompagny.entities.Produits;
import com.mycompagny.entities.User;

import java.util.*;
import java.util.Arrays;
import java.util.Date;

/**
 *
 * @author Lenovo
 */
public class SessionManager {

    public static Preferences pref ; // 3ibara memoire sghira nsajlo fiha data



    // hethom données ta3 user lyt7b tsajlhom fi session  ba3d login
    private static boolean actif;

    static {
        actif = false;
    }

    private static int id ;
    private static String userName ;
    private static String email;
    private static String passowrd ;
    private static String photo;
    private static String nomImage,prenom,adresse,numtel,nom ;
    private static String dateNaissance;
    private static User user ;
    private static ArrayList role;
    private static float prix;
    private static String nomProduits;
    // private static String nomprod[];
    private static ArrayList<String> ar = new ArrayList<String>();

    public static float getPrix() {
        return prix;
    }

    public static ArrayList<String> getAr() {
        return ar;
    }

    public static void ajouter(String nom) {
        SessionManager.ar.add(nom);
    }
    /*public static String[] getNomprod() {
        return nomprod;
    }

    public static void setNomprod(String nomprod) {
        int n = nomprod.length();
       SessionManager.nomprod[n]=nomprod;
    }

    public static float getPrix() {
        return prix;
    }*/

    public static void setPrix(float prix) {
        SessionManager.prix = prix;
    }

    public static String getNomProduits() {
        return nomProduits;
    }

    public static void setNomProduits(String nomProduits) {
        SessionManager.nomProduits = nomProduits;
    }

    public static ArrayList getRole() {
        return role;
    }

    public static void setRole(ArrayList role) {
        SessionManager.role = role;
    }

    public static String getNom() {
        return nom;
    }

    public static void setNom(String nom) {
        SessionManager.nom = nom;
    }

    public static String getAdresse() {
        return adresse;
    }

    public static void setAdresse(String adresse) {
        SessionManager.adresse = adresse;
    }

    public static String getNumtel() {
        return numtel;
    }

    public static void setNumtel(String numtel) {
        SessionManager.numtel = numtel;
    }

    public static String getPrenom() {
        return prenom;
    }

    public static void setPrenom(String prenom) {
        SessionManager.prenom = prenom;
    }

    public static String getDateNaissance() {
        return dateNaissance;
    }

    public static void setDateNaissance(String dateNaissance) {
        SessionManager.dateNaissance = dateNaissance;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        SessionManager.user = user;
    }

    public static String getNomImage() {
        return nomImage;
    }

    public static void setNomImage(String nomImage) {
        SessionManager.nomImage = nomImage;
    }

    public static Preferences getPref() {
        return pref;
    }

    public static void setPref(Preferences pref) {
        SessionManager.pref = pref;
    }

    public static boolean isActif() {
        return actif;
    }

    public static void setActif(boolean actif) {
        SessionManager.actif = actif;
    }

    public static int getId() {
        return pref.get("id",id);// kif nheb njib id user connecté apres njibha men pref
    }

    public static void setId(int id) {
        pref.set("id",id);//nsajl id user connecté  w na3tiha identifiant "id";
    }

    public static String getUserName() {
        return pref.get("pseudo",userName);
    }

    public static void setUserName(String userName) {
        pref.set("pseudo",userName);
    }

    public static String getEmail() {
        return pref.get("email",email);
    }

    public static void setEmail(String email) {
        pref.set("email",email);
    }

    public static String getPassowrd() {
        return pref.get("passowrd",passowrd);
    }

    public static void setPassowrd(String passowrd) {
        pref.set("passowrd",passowrd);
    }

    public static String getPhoto() {
        return pref.get("photo",photo);
    }

    public static void setPhoto(String photo) {
        pref.set("photo",photo);
    }



}
