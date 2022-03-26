package com.mycompagny.services;

import com.codename1.io.*;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompagny.entities.Coupon;
import com.mycompagny.gui.SessionManager;
import com.mycompagny.utils.Statics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ServiceCoupon {

    public static ServiceCoupon instance = null;
    public static boolean result=true;
    private ConnectionRequest request;

    public static ServiceCoupon getInsance(){
        if(instance==null)
            instance=new ServiceCoupon();
        return instance;
    }

    public ServiceCoupon(){

        request= new ConnectionRequest();
    }

                 /*METHODE D'AJOUT */

    public void ajouterCoupon(Coupon coupon){
        String url= Statics.BASE_URL+"cAjoutjson?code="+coupon.getCode()+"&dateLimite="+coupon.getDateLimite()+"&remise="+coupon.getRemise()+"&user="+ SessionManager.getId();
        request.setUrl(url);
        request.addResponseListener((e)->{

            String str= new String (request.getResponseData());
            System.out.println("data="+str);

        });

        NetworkManager.getInstance().addToQueueAndWait(request);
    }

                 /*METHODE DE MODIFICATION */

    public boolean modifierCoupon(Coupon coupon){
        String url=Statics.BASE_URL+"cModifierjson?id="+coupon.getId()+"&code="+coupon.getCode()+"&dateLimite="+coupon.getDateLimite()+"&remise="+coupon.getRemise();
        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                result=request.getResponseCode()==200;
                request.removeResponseCodeListener(this);

                request.addResponseListener((e)->{
                    //json response
                    String str= new String (request.getResponseData());
                    System.out.println("data="+str);
                });
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(request);
        return result;
    }

                /*METHODE DE SUPPRESSION */

    public boolean deleteCoupon(int id){
        String url=Statics.BASE_URL+"cSupprimerjson?id="+id;
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

               /*METHODE D'AFFICHAGE */

    public ArrayList<Coupon> affichageCoupon(){

        ArrayList<Coupon> liste = new ArrayList<>();
        String url=Statics.BASE_URL+"couponJsonList";
        request.setUrl(url);
        request.addResponseListener( new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp= new JSONParser();
                try{
                    Map<String, Object> mapCommandes = jsonp.parseJSON(new CharArrayReader(new String (request.getResponseData()).toCharArray()));
                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapCommandes.get("root");

                    for(Map<String, Object> obj : listOfMaps){

                        Coupon cp= new Coupon();
                        float id = Float.parseFloat(obj.get("id").toString());
                        float code = Float.parseFloat(obj.get("code").toString());
                        float remise = Float.parseFloat(obj.get("remise").toString());

                        String DateConverter =  obj.get("dateLimite").toString().substring(obj.get("dateLimite").toString().indexOf("timestamp") + 10 , obj.get("dateLimite").toString().lastIndexOf("}"));

                        Date currentTime = new Date(Double.valueOf(DateConverter).longValue() * 1000);

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String dateString = formatter.format(currentTime);

                        cp.setId((int)id);
                        cp.setCode((int)code);
                        cp.setRemise((int)remise);
                        cp.setDateLimite(dateString);

                        liste.add(cp);

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

}
