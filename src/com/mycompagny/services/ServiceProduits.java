package com.mycompagny.services;

import com.codename1.io.*;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.mycompagny.entities.Produits;
import com.mycompagny.entities.Restaurant;
import com.mycompagny.utils.Statics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceProduits {
    public ArrayList<Produits> produits;
    public static boolean resultOK=true;
    private ConnectionRequest req;
    public static ServiceProduits instance = null;

    public static ServiceProduits getInstance() {
        if (instance == null) {
            instance = new ServiceProduits();
        }
        return instance;
    }

    public ServiceProduits() {
        req = new ConnectionRequest();
    }
    public ArrayList<Produits>affichageProduits() {
        ArrayList<Produits> result = new ArrayList <> ();


        String url = Statics.BASE_URL+"displayProds";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                try {
                    Map<String,Object> mapProduits= jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String,Object>> listOfMaps = (List<Map<String,Object>>) mapProduits.get("root");
                    for(Map<String, Object> obj : listOfMaps) {
                        Produits re = new Produits();
                        float id = Float.parseFloat(obj.get("id").toString());
                        String nom = obj.get("nomProduit").toString();
                        String descriptionProduit = obj.get("descriptionProduit").toString();
                        float prix = Float.parseFloat(obj.get("prixProduit").toString());
                        // String date =  obj.get("date").toString();
                        re.setId((int)id);
                        re.setNom(nom);
                        re.setDescriptionProduit(descriptionProduit);
                        //re.setDescription(foodqulaite);
                        re.setPrix((int)prix);

                        //date
                        // String DateConverter = obj.get("date").toString().substring(obj.get("date").toString().indexOf("timestamp") + 10 , obj.get("date").toString().lastIndexOf("}") );
                        // Date currentTime = new Date(Double.valueOf(DateConverter).longValue() * 1000);

                        // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        // String dateString = formatter.format(currentTime);
                        result.add(re);


                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }


        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }

    public void AjoutProduit(int idR,Produits P) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = Statics.BASE_URL+"ajoutproduitapi/" + idR + "?nomProduit=" +P.getNom()
                + "&DescriptionProduit=" + P.getDescriptionProduit()
                + "&prixProduit=" + P.getPrix()+"&image="+P.getImageProduit()+"&nomimageProduit="+P.getNomImage();

        con.setUrl(Url);
        con.setPost(false);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        Dialog.show("Succés", "Produit ajouté", "ok", null);
    }

}
