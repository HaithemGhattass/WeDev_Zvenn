package com.mycompagny.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.util.Resources;
import com.mycompagny.entities.User;
import com.mycompagny.gui.SessionManager;
import com.mycompagny.gui.TrendingForm;
import com.mycompagny.utils.Statics;
// import com.mycompany.gui.SessionManager;
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
    public void signup(TextField username,TextField password,TextField email,TextField confirmPassword, ComboBox<String> roles , Resources res ) {



        String url = Statics.BASE_URL+"registerUsermobile?pseudo="+username.getText().toString()+"&email="+email.getText().toString()+
                "&password="+password.getText().toString()+"&nom=haithem&prenom=ghattas&adresse=hr&numTel=92565848&dateNaissanceMon%20Mar%2021%2019:50:24%20WAT%202022";

        req.setUrl(url);

        //Control saisi
        if(username.getText().equals(" ") && password.getText().equals(" ") && email.getText().equals(" ")) {

            Dialog.show("Erreur","Veuillez remplir les champs","OK",null);

        }

        //hethi wa9t tsir execution ta3 url
        req.addResponseListener((e)-> {

                    //njib data ly7atithom fi form
                    byte[]data = (byte[]) e.getMetaData();//lazm awl 7aja n7athrhom ke meta data ya3ni na5o id ta3 kol textField
                    String responseData = new String(data);//ba3dika na5o content

                    System.out.println("data ===>"+responseData);
                }
        );


        //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
        NetworkManager.getInstance().addToQueueAndWait(req);



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

}