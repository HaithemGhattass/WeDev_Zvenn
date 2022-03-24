/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompagny.gui;

import com.codename1.io.Preferences;
import com.mycompagny.entities.User;

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
    private static String nomImage ;
    private static User user ;


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
