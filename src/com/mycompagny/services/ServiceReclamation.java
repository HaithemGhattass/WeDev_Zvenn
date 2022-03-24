/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompagny.services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.mycompagny.entities.Reclamation;
import com.mycompagny.utils.Statics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author mtar
 */
public class ServiceReclamation {
public ArrayList<Reclamation> reclamation;
    public static boolean resultOK=true;
    private ConnectionRequest req;
    public static ServiceReclamation instance = null;

    public static ServiceReclamation getInstance() {
        if (instance == null) {
            instance = new ServiceReclamation();
        }
        return instance;
    }

    public ServiceReclamation() {
        req = new ConnectionRequest();
    }
     
    public void ajoutReclamation(Reclamation reclamation) {

        String url = Statics.BASE_URL +"addReclamation?titre="+ reclamation.getTitre() + "&description=" + reclamation.getDescription()+"&foodqulaite="+reclamation.getFoodqulaite()+"&service="+reclamation.getService()+"&price="+reclamation.getPrice();
        req.setUrl(url);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("data==" +str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

   public ArrayList<Reclamation>affichageReclamation() {
        ArrayList<Reclamation> result = new ArrayList <> ();
        
         
        String url =Statics.BASE_URL+"displayReclamations";
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
              float id = Float.parseFloat(obj.get("id").toString());
              String titre = obj.get("titre").toString();
              String description = obj.get("description").toString();
             // String date =  obj.get("date").toString();
              String etat= obj.get("etat").toString();
              re.setId((int)id);
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
                }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        
       
        });
         
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }
   public boolean deleteReclamation(int id)
   {
       String url = Statics.BASE_URL +"deleteReclamation/"+id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req.removeResponseCodeListener(this);
        }
         
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    return resultOK;
    }
   public boolean modifierReclamation(Reclamation reclamation)
   {
        String url = Statics.BASE_URL +"updateReclamation?id="+reclamation.getId()+"&titre="+ reclamation.getTitre() + "&description=" + reclamation.getDescription();
        req.setUrl(url);
      req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK= req.getResponseCode()==200;
                req.removeResponseCodeListener(this);
        }
         
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    return resultOK;
   }



   }
     
    

