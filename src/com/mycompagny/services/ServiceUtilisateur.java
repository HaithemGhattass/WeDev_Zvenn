package com.mycompagny.services;

import com.cloudinary.codename1.api.Response;
import com.codename1.io.*;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompagny.entities.Restaurant;
import com.mycompagny.entities.User;
import com.mycompagny.gui.SessionManager;
import com.mycompagny.gui.SignInForm;
import com.mycompagny.gui.TrendingForm;
import com.mycompagny.utils.Statics;
// import com.mycompany.gui.SessionManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author yassine
 */
public class ServiceUtilisateur {


    //singleton
    public static ServiceUtilisateur instance = null;

    public static boolean resultOk = true;
    String json;

    //initilisation connection request
    private ConnectionRequest req;


    public static ServiceUtilisateur getInstance() {
        if (instance == null)
            instance = new ServiceUtilisateur();
        return instance;
    }


    public ServiceUtilisateur() {
        req = new ConnectionRequest();

    }

    //Signup
    public void signup(TextField username, TextField password, TextField email, TextField confirmPassword, Picker roles , Picker sexe, TextField nom, TextField prenom, TextField numtel, Picker dateNaissance,TextField adresse, Resources res ) {



        String url = Statics.BASE_URL+"registerUsermobile?pseudo="+username.getText().toString()+"&email="+email.getText().toString()+
                "&password="+password.getText().toString()+"&nom="+nom.getText()+"&prenom="+prenom.getText()+"&adresse="+adresse.getText()+"&numTel="+numtel.getText()+"&dateNaissance="+dateNaissance.getDate()+"&sexe="+sexe.getSelectedString().toString()+"&roles="+roles.getSelectedString();

        req.setUrl(url);

            req.addResponseListener((e) -> {

                        //njib data ly7atithom fi form
                        byte[] data = (byte[]) e.getMetaData();//lazm awl 7aja n7athrhom ke meta data ya3ni na5o id ta3 kol textField
                        String responseData = new String(data);//ba3dika na5o content
                Dialog.show("success", responseData, "ok", null);

                    }
            );
            if (username.getText().equals(" ") && password.getText().equals(" ") && email.getText().equals(" ") ) {

                Dialog.show("Erreur", "Veuillez remplir les champs", "OK", null);

            }

            //hethi wa9t tsir execution ta3 url


            //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
            NetworkManager.getInstance().addToQueueAndWait(req);
            new SignInForm(Resources.getGlobalResources()).show();



    }

//SignIn

   public void signin(TextField username,TextField password, Resources rs ) {


        String url = Statics.BASE_URL+"loginmobile?email="+username.getText().toString()+"&password="+password.getText().toString();
        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server
        req.setUrl(url);

        req.addResponseListener((e) ->{

            JSONParser j = new JSONParser();

            String json = new String(req.getResponseData()) + "";


            try {

                if(json.equals("failed")) {
                    Dialog.show("Echec d'authentification","Username ou mot de passe éronné","OK",null);
                }
                else {
                    System.out.println("data =="+json);

                    Map<String,Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));



                    //Session
                    float id = Float.parseFloat(user.get("id").toString());
                    SessionManager.setId((int)id);//jibt id ta3 user ly3ml login w sajltha fi session ta3i

                    SessionManager.setPassowrd(user.get("password").toString());
                    SessionManager.setUserName(user.get("pseudo").toString());
                    SessionManager.setEmail(user.get("email").toString());
                    SessionManager.setNomImage(user.get("nomImage").toString());
                    SessionManager.setNom(user.get("nom").toString());
                    SessionManager.setPrenom(user.get("prenom").toString());
                    SessionManager.setDateNaissance(user.get("dateNaissance").toString());
                    SessionManager.setNumtel(user.get("numTel").toString());
                    SessionManager.setAdresse(user.get("adresse").toString());
                    SessionManager.setRole((ArrayList) user.get("roles"));

                    SessionManager.setActif(true);



                    //photo

                    if(user.get("photo") != null)
                        SessionManager.setPhoto(user.get("photo").toString());


                    if(user.size() >0 ) // l9a user
                        // new ListReclamationForm(rs).show();//yemchi lel list reclamation
                        new TrendingForm(rs).show();

                }

            }catch(Exception ex) {
                ex.printStackTrace();
            }



        });

        //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
    }


    //heki 5dmtha taw nhabtha ala description
    public String getPasswordByEmail(String email, Resources rs ) {


        String url = Statics.BASE_URL+"/user/getPasswordByEmail?email="+email;
        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server
        req.setUrl(url);

        req.addResponseListener((e) ->{

            JSONParser j = new JSONParser();

            json = new String(req.getResponseData()) + "";


            try {


                System.out.println("data =="+json);

                Map<String,Object> password = j.parseJSON(new CharArrayReader(json.toCharArray()));




            }catch(Exception ex) {
                ex.printStackTrace();
            }



        });

        //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);
        return json;
    }

    public boolean modifierprofil(TextField username, TextField password , TextField nom, TextField prenom, TextField numtel,TextField adresse, Resources res){

        String url = Statics.BASE_URL + "editUserapi?id="+SessionManager.getId()+"&pseudo="+username.getText().toString()+
                "&password="+password.getText().toString()+"&nom="+nom.getText()+"&prenom="+prenom.getText()+"&adresse="+adresse.getText()+"&numTel="+numtel.getText();

        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;// code response http 200
                req.removeResponseListener(this);

            }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);

        SessionManager.setPassowrd(password.getText().toString());
        SessionManager.setUserName(username.getText().toString());
//        SessionManager.setNomImage(user.get("nomImage").toString());
        SessionManager.setNom(nom.getText().toString());
        SessionManager.setPrenom(prenom.getText().toString());
        SessionManager.setNumtel(numtel.getText().toString());
        SessionManager.setAdresse(adresse.getText().toString());
        return resultOk;

    }


}