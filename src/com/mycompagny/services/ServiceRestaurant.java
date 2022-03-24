 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompagny.services;

 import com.codename1.io.*;
 import com.codename1.processing.Result;
 import com.codename1.ui.events.ActionListener;
 import com.mycompagny.entities.Produits;
 import com.mycompagny.entities.Reclamation;
 import com.mycompagny.entities.Restaurant;
 import com.mycompagny.gui.SessionManager;
 import com.mycompagny.utils.Statics;

 import java.io.ByteArrayInputStream;
 import java.io.InputStreamReader;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;

/**
 *
 * @author haithem
 */
public class ServiceRestaurant {
    
     //singleton
    public static ServiceRestaurant instance= null;
    
    public static boolean resultOk = true ;
    //initialisation connction request
    private ConnectionRequest req;
    
    public static ServiceRestaurant getInstance(){
        
        if (instance == null)
            instance = new ServiceRestaurant();
        return instance ;
    }
    public ServiceRestaurant () {
        req = new ConnectionRequest() ;
    }
    //ajout
    
    public void ajoutRestaurant(Restaurant restaurant) {
        String url=Statics.BASE_URL+"RestaurantaddM?nomRestaurant="+restaurant.getNom()+"&description="+restaurant.getDescription()+"&addresse="+restaurant.getAddresse()+"&cite="+restaurant.getCite()+"&codePostal="+restaurant.getCodePostal()+"&heureOuverture="+restaurant.getHeureOuverture()+"&categorieRestaurant="+restaurant.getCategorieRestaurant()+"&heureFermeture="+restaurant.getHeureFermeture()+"&gouvernorat="+restaurant.getGouvernorat()+"&imageRestaurant="+restaurant.getImageRestaurant()+"&nomImage="+restaurant.getNomImage()+"&user="+ SessionManager.getId();
           req.setUrl(url);
           req.addResponseListener((e) -> {
               String str = new String(req.getResponseData());
               System.out.println("data == "+str);
               
           });
    NetworkManager.getInstance().addToQueueAndWait(req);
    }
    //mes restaurants
    public ArrayList<Restaurant>affichagemesRestaurant(int id) {
        ArrayList<com.mycompagny.entities.Restaurant> result = new ArrayList <> ();

        String url =Statics.BASE_URL+"displaymesRestaurants/"+id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                try {
                    Map<String,Object>mapRestaurant= jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String,Object>> listOfMaps = (List<Map<String,Object>>) mapRestaurant.get("root");
                    for(Map<String, Object> obj : listOfMaps) {
                        com.mycompagny.entities.Restaurant re = new com.mycompagny.entities.Restaurant();

                        float id = Float.parseFloat(obj.get("id").toString());
                        //float idUser = Float.parseFloat(obj.get("userId").toString());


                        String addresse = obj.get("addresse").toString();
                        String cite = obj.get("cite").toString();
                        float codePostal = Float.parseFloat(obj.get("codePostal").toString());

                        String nom = obj.get("nomRestaurant").toString();
                        String description = obj.get("description").toString();
                        String categorieRestaurant = obj.get("categorieRestaurant").toString();
                        String gouvernorat = obj.get("gouvernorat").toString();
                        String imagerestaurant = obj.get("imageRestaurant").toString();
                        String nomImage = obj.get("nomImage").toString();
                        String status = obj.get("status").toString();

                        //re.setIdUser((int)idUser);
                        re.setStatus(status);
                        re.setNomImage(nomImage);
                        re.setImageRestaurant(imagerestaurant);
                        re.setAddresse(addresse);
                        re.setCite(cite);
                        re.setCodePostal((int)codePostal);
                        re.setId((int)id);
                        re.setNom(nom);
                        re.setDescription(description);
                        re.setCategorieRestaurant(categorieRestaurant);
                        re.setGouvernorat(gouvernorat);


                        //date
                        //   String DateConverter = obj.get("createdAt").toString().substring(obj.get("createdAt").toString().indexOf("timestamp") + 10 , obj.get("date").toString().lastIndexOf("}") );
                        //   Date currentTime = new Date(Double.valueOf(DateConverter).longValue() * 1000);

                        // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        //    String dateString = formatter.format(currentTime);
                        //  re.setCreatedAt(dateString);
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

    //aff by map
    public ArrayList<Restaurant>affichageRestaurantmap(String c) {
        ArrayList<com.mycompagny.entities.Restaurant> result = new ArrayList <> ();

        String url =Statics.BASE_URL+"map/"+c;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                try {
                    Map<String,Object>mapRestaurant= jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String,Object>> listOfMaps = (List<Map<String,Object>>) mapRestaurant.get("root");
                    for(Map<String, Object> obj : listOfMaps) {
                        com.mycompagny.entities.Restaurant re = new com.mycompagny.entities.Restaurant();

                        float id = Float.parseFloat(obj.get("id").toString());
                        //float idUser = Float.parseFloat(obj.get("userId").toString());


                        String addresse = obj.get("addresse").toString();
                        String cite = obj.get("cite").toString();
                        float codePostal = Float.parseFloat(obj.get("codePostal").toString());

                        String nom = obj.get("nomRestaurant").toString();
                        String description = obj.get("description").toString();
                        String categorieRestaurant = obj.get("categorieRestaurant").toString();
                        String gouvernorat = obj.get("gouvernorat").toString();
                        String imagerestaurant = obj.get("imageRestaurant").toString();
                        String nomImage = obj.get("nomImage").toString();
                        String status = obj.get("status").toString();

                        //re.setIdUser((int)idUser);
                        re.setStatus(status);
                        re.setNomImage(nomImage);
                        re.setImageRestaurant(imagerestaurant);
                        re.setAddresse(addresse);
                        re.setCite(cite);
                        re.setCodePostal((int)codePostal);
                        re.setId((int)id);
                        re.setNom(nom);
                        re.setDescription(description);
                        re.setCategorieRestaurant(categorieRestaurant);
                        re.setGouvernorat(gouvernorat);


                        //date
                        //   String DateConverter = obj.get("createdAt").toString().substring(obj.get("createdAt").toString().indexOf("timestamp") + 10 , obj.get("date").toString().lastIndexOf("}") );
                        //   Date currentTime = new Date(Double.valueOf(DateConverter).longValue() * 1000);

                        // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        //    String dateString = formatter.format(currentTime);
                        //  re.setCreatedAt(dateString);
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
    //affichage
    public ArrayList<Restaurant>affichageRestaurant() {
        ArrayList<Restaurant> result = new ArrayList <> ();
        
        String url =Statics.BASE_URL+"displayRestaurants";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                try {
                Map<String,Object>mapRestaurant= jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                      List<Map<String,Object>> listOfMaps = (List<Map<String,Object>>) mapRestaurant.get("root");
          for(Map<String, Object> obj : listOfMaps) {
              Restaurant re = new Restaurant ();
              
              float id = Float.parseFloat(obj.get("id").toString());
            //  float idUser = Float.parseFloat(obj.get("userId").toString());


              String addresse = obj.get("addresse").toString();
              String cite = obj.get("cite").toString();
              float codePostal = Float.parseFloat(obj.get("codePostal").toString());

              String nom = obj.get("nomRestaurant").toString();
              String description = obj.get("description").toString();
              String categorieRestaurant = obj.get("categorieRestaurant").toString();
              String gouvernorat = obj.get("gouvernorat").toString();
              String imagerestaurant = obj.get("imageRestaurant").toString();
              String nomImage = obj.get("nomImage").toString();
              String status = obj.get("status").toString();

             // re.setIdUser((int)idUser);
              re.setStatus(status);
              re.setNomImage(nomImage);
              re.setImageRestaurant(imagerestaurant);
              re.setAddresse(addresse);
              re.setCite(cite);
              re.setCodePostal((int)codePostal);
              re.setId((int)id);
              re.setNom(nom);
              re.setDescription(description);
              re.setCategorieRestaurant(categorieRestaurant);
              re.setGouvernorat(gouvernorat);
              
              
              //date
           //   String DateConverter = obj.get("createdAt").toString().substring(obj.get("createdAt").toString().indexOf("timestamp") + 10 , obj.get("date").toString().lastIndexOf("}") );
           //   Date currentTime = new Date(Double.valueOf(DateConverter).longValue() * 1000);
              
              // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
           //    String dateString = formatter.format(currentTime);
             //  re.setCreatedAt(dateString);
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
    public ArrayList<Reclamation>affichageReclamations(int id) {
        ArrayList<Reclamation> result = new ArrayList <> ();
      
        String url =Statics.BASE_URL+"displayReclamations/"+id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                try {
                Map<String,Object>mapReclamation= jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                      List<Map<String,Object>> listOfMaps = (List<Map<String,Object>>) mapReclamation.get("root");
          for(Map<String, Object> obj : listOfMaps) {
              Reclamation re = new Reclamation();
              if (obj.get("id")!=null||obj.get("titre")!=null) {
                  float id = Float.parseFloat(obj.get("id").toString());
                  String titre = obj.get("titre").toString();
                  String description = obj.get("description").toString();
                  // String date =  obj.get("date").toString();
                  String etat = obj.get("etat").toString();

                  re.setId((int) id);
                  re.setTitre(titre);
                  re.setDescription(description);
                  //re.setDescription(foodqulaite);
                  re.setEtat(etat);


                  //date
                  // String DateConverter = obj.get("date").toString().substring(obj.get("date").toString().indexOf("timestamp") + 10 , obj.get("date").toString().lastIndexOf("}") );
                  // Date currentTime = new Date(Double.valueOf(DateConverter).longValue() * 1000);

                  // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                  // String dateString = formatter.format(currentTime);


                  re.setDate("02/10/2021");
                  result.add(re);
              }
              
              
          }
                }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        
       
        });
         
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }



    //affichage produits

    public ArrayList<Produits>affichageProds(int id) {
        ArrayList<Produits> result = new ArrayList <> ();

        String url =Statics.BASE_URL+"displayProds/"+id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                try {
                    Map<String,Object>mapProduits= jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String,Object>> listOfMaps = (List<Map<String,Object>>) mapProduits.get("root");
                    for(Map<String, Object> obj : listOfMaps) {
                        Produits re = new Produits();
                        float id = Float.parseFloat(obj.get("id").toString());
                        String nom = obj.get("nomProduit").toString();
                      String descriptionProduit = obj.get("DescriptionProduit").toString();
                        // String date =  obj.get("date").toString();
                        float prix = Float.parseFloat(obj.get("prixProduit").toString());
                        re.setId((int)id);
                        re.setPrix((int)prix);
                        re.setNom(nom);
                        re.setDescriptionProduit(descriptionProduit);
                        //re.setDescription(foodqulaite);

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



    //delete
    public  boolean deleteRestaurant (int id) {
        String url = Statics.BASE_URL +"deleteRestaurant?id="+id;
        
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>(){
            
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                req.removeResponseCodeListener(this);
                
            }
        
        
    });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }
    
    //update 
    public boolean modificationRestaurant(Restaurant restaurant){
        
        String url = Statics.BASE_URL + "updateRestaurant?id="+restaurant.getId()+"&nomRestaurant="+restaurant.getNom()+
                "&description="+restaurant.getDescription()+"&addresse="+restaurant.getAddresse()+
                "&gouvernorat="+restaurant.getGouvernorat()+"&cite="+restaurant.getCite()+"&codePostal="
                +restaurant.getCodePostal()+"&categorieRestaurant="+restaurant.getCategorieRestaurant()
                +"&heureOuverture="+restaurant.getHeureOuverture()+"&heureFermeture="+restaurant.getHeureFermeture();
        req.setUrl(url);
        req.addResponseListener(new ActionListener <NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;// code response http 200
                req.removeResponseListener(this);
                
            }
            
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
                
    }


    public boolean modificationStatus(int id,String status){

        String url = Statics.BASE_URL + "restaurantsapi?id="+id+"&status="+status;
        req.setUrl(url);
        req.addResponseListener(new ActionListener <NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;// code response http 200
                req.removeResponseListener(this);

            }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;

    }





}
