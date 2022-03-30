package com.mycompagny.entities;

public class Reply {
    private String titre,description;
    private int id,idrec;
    private String date;

    public int getIdrec() {
        return idrec;
    }

    public void setIdrec(int idrec) {
        this.idrec = idrec;
    }




    public Reply(String titre, String description,  String date) {
        this.titre = titre;
        this.description = description;

        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Reply() {
        this.titre = titre;
        this.description = description;
        this.date = date;
    }
}
