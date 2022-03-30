package com.mycompagny.services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.mycompagny.entities.Reclamation;
import com.mycompagny.entities.Reply;
import com.mycompagny.utils.Statics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceReply {
    public ArrayList<Reply> reply;
    public static boolean resultOK=true;
    private ConnectionRequest req;
    public static ServiceReply instance = null;

    public static ServiceReply getInstance() {
        if (instance == null) {
            instance = new ServiceReply();
        }
        return instance;
    }
    public ServiceReply() {
        req = new ConnectionRequest();
    }

    public void ajoutReply(Reply reply, int id) {

        String url = Statics.BASE_URL +"addRe/"+id+"?titre="+ reply.getTitre() + "&description=" + reply.getDescription();
        req.setUrl(url);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("data==" +str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    public ArrayList<Reply>affichageReply(int id) {
        ArrayList<Reply> result = new ArrayList <> ();


        String url =Statics.BASE_URL+"displayReply/"+id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                try {
                    Map<String,Object> mapReply= jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String,Object>> listOfMaps = (List<Map<String,Object>>) mapReply.get("root");
                    for(Map<String, Object> obj : listOfMaps) {
                        Reply re = new Reply();
                        float id = Float.parseFloat(obj.get("id").toString());
                        String titre = obj.get("titre").toString();
                        String description = obj.get("description").toString();


                        re.setId((int)id);
                        re.setTitre(titre);
                        re.setDescription(description);

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
    public boolean deleteR(int id)
    {
        String url = Statics.BASE_URL +"deleteRep/"+id;
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
}
