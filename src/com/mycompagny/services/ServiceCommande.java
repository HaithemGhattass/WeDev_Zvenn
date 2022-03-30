package com.mycompagny.services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.mycompagny.entities.Commande;
import com.mycompagny.gui.SessionManager;
import com.mycompagny.utils.Statics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ServiceCommande {


    public static ServiceCommande instance = null;
    public static boolean result=true;

    //initialisation connection request
    private ConnectionRequest request;

    public static ServiceCommande getInsance(){

        if(instance==null)
            instance=new ServiceCommande();
        return instance;
    }

    public ServiceCommande(){

        request= new ConnectionRequest();
    }

                        /*METHODE D'AJOUT */
    public void AjouterCommande(Commande commande){

        String url= Statics.BASE_URL+"ajoutjson?adresseLivraison="+commande.getAdresseLivraison()+"&totalCommande="+commande.getTotalCommande()+"&modeLivraison="+commande.getModeLivraison()+"&renseignement="+commande.getRenseignement()+"&user="+ SessionManager.getId();
        request.setUrl(url);
        request.addResponseListener((e)->{
            //json response
            String str= new String (request.getResponseData());
            System.out.println("data="+str);
        });

        NetworkManager.getInstance().addToQueueAndWait(request);

    }

                        /*METHODE D'AFFICHAGE */
    public ArrayList<Commande> AffichageCommandes(){

        ArrayList<Commande> liste = new ArrayList<>();
        String url=Statics.BASE_URL+"jsonlist";
        request.setUrl(url);
        request.addResponseListener( new ActionListener<NetworkEvent>(){

            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp= new JSONParser();

                try{
                    Map<String, Object>mapCommandes = jsonp.parseJSON(new CharArrayReader(new String (request.getResponseData()).toCharArray()));
                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapCommandes.get("root");

                    for(Map<String, Object> obj : listOfMaps){

                        Commande cmd= new Commande();
                        float id = Float.parseFloat(obj.get("id").toString());
                        String adresse = obj.get("adresseLivraison").toString();
                        float total = Float.parseFloat(obj.get("totalCommande").toString());
                        String mode = obj.get("modeLivraison").toString();
                        String renseignement = obj.get("renseignement").toString();
                       // String status = obj.get("status").toString();
                        String status =obj.get("status").toString();
                        String user = obj.get("User").toString();
                        cmd.setUser(user);
                        cmd.setId((int)id);
                        cmd.setAdresseLivraison(adresse);
                        cmd.setTotalCommande(total);
                        cmd.setModeLivraison(mode);
                        cmd.setRenseignement(renseignement);
                       /* if (status.equals(true)) {
                            cmd.setStatus(true);
                        }else {
                            cmd.setStatus(false);}*/
                        cmd.setStatus(Boolean.parseBoolean(status));

                      //  System.out.println(status);

                        // insert data into liste
                        liste.add(cmd);

                    }
                }
                catch(Exception e){

                    e.printStackTrace();
                }
            }

        });

        NetworkManager.getInstance().addToQueueAndWait(request);
        return liste;
    }

                        /*METHODE D'AFFICHAGE PAR ID */
    public ArrayList<Commande> AffichageCommandesById(int id){

        ArrayList<Commande> liste = new ArrayList<>();

            String url =Statics.BASE_URL+"commandesById/"+id;
            request.setUrl(url);
            request.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    JSONParser jsonp;
                    jsonp = new JSONParser();
                    try {
                        Map<String,Object>mapCommandes= jsonp.parseJSON(new CharArrayReader(new String(request.getResponseData()).toCharArray()));
                        List<Map<String,Object>> listOfMaps = (List<Map<String,Object>>) mapCommandes.get("root");
                        for(Map<String, Object> obj : listOfMaps) {
                            Commande cmd= new Commande();
                            float id = Float.parseFloat(obj.get("id").toString());
                            String adresse = obj.get("adresseLivraison").toString();
                            float total = Float.parseFloat(obj.get("totalCommande").toString());
                            String mode = obj.get("modeLivraison").toString();
                            String renseignement = obj.get("renseignement").toString();


                            cmd.setId((int)id);
                            cmd.setAdresseLivraison(adresse);
                            cmd.setTotalCommande(total);
                            cmd.setModeLivraison(mode);
                            cmd.setRenseignement(renseignement);
                            cmd.setStatus(true);

                            // insert data into liste
                            liste.add(cmd);
                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }


            });

            NetworkManager.getInstance().addToQueueAndWait(request);
            return liste;
        }

                        /*METHODE DE SUPPRESSION */
    public boolean deleteCommande(int id){
        String url=Statics.BASE_URL+"supprimerjson?id="+id;
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                request.removeResponseCodeListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return result;
    }

                        /*METHODE DE MODIFICATION */
    public boolean modifierCommande(Commande commande){
        String url=Statics.BASE_URL+"modifierjson?id="+commande.getId()+"&adresseLivraison="+commande.getAdresseLivraison()+"&modeLivraison="+commande.getModeLivraison();
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                result=request.getResponseCode()==200;
                request.removeResponseCodeListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(request);
        return result;
    }


                        /*METHODE SET STATUS */
    public boolean setStatus(int id,String status){

        String url = Statics.BASE_URL + "valider?id="+id+"&status="+status;
        request.setUrl(url);
        request.addResponseListener(new ActionListener <NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
                result = request.getResponseCode() == 200 ;
                request.removeResponseListener(this); }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return result;

    }

  }
