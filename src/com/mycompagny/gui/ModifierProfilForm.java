/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.mycompagny.gui;

import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.*;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.*;
import com.mycompagny.entities.User;
import com.mycompagny.services.ServiceUtilisateur;
import com.mycompagny.utils.Statics;

import java.io.IOException;
import java.util.UUID;
import java.util.Vector;

/**
 * GUI builder created Form
 *
 * @author Shai Almog
 */
public class ModifierProfilForm extends com.codename1.ui.Form {
    EncodedImage placeholder ;
    ImageViewer imgv ;
    EncodedImage placeholder1 ;
    ImageViewer imgv1 ;
    private ConnectionRequest req;

    public ModifierProfilForm() {
        this(com.codename1.ui.util.Resources.getGlobalResources());
    }

    public ModifierProfilForm(com.codename1.ui.util.Resources resourceObjectInstance) {
        initGuiBuilderComponents(resourceObjectInstance);
        getTitleArea().setUIID("Container");
        getToolbar().setUIID("Container");

        getToolbar().getTitleComponent().setUIID("SigninTitle");
        FontImage mat = FontImage.createMaterial(FontImage.MATERIAL_CLOSE, "SigninTitle", 3.5f);
        getToolbar().addCommandToLeftBar("", mat, e -> new SplashForm().show());
        getContentPane().setUIID("SignInForm");


    }

    //-- DON'T EDIT BELOW THIS LINE!!!
    private com.codename1.ui.Container gui_Container_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
    private com.codename1.ui.Label gui_Label_1 = new com.codename1.ui.Label();
    private com.codename1.ui.ComponentGroup gui_Component_Group_1 = new com.codename1.ui.ComponentGroup();
   // private com.codename1.ui.TextField email = new com.codename1.ui.TextField(SessionManager.getEmail(), "email", 20, TextField.ANY);
    private com.codename1.ui.TextField password = new com.codename1.ui.TextField(SessionManager.getPassowrd(), "", 20, TextField.ANY);
    private com.codename1.ui.TextField username = new com.codename1.ui.TextField(SessionManager.getUserName(), "", 20, TextField.ANY);
   // private com.codename1.ui.TextField confirmPassword = new com.codename1.ui.TextField("", "comfirm password", 20, TextField.PASSWORD);
    private com.codename1.ui.TextField adresse = new com.codename1.ui.TextField(SessionManager.getAdresse(), "adresse", 20, TextField.ANY);
    private com.codename1.ui.TextField nom = new com.codename1.ui.TextField(SessionManager.getNom(), "nom", 20, TextField.ANY);
    private com.codename1.ui.TextField prenom = new com.codename1.ui.TextField(SessionManager.getPrenom(), "prenom", 20, TextField.ANY);
    private com.codename1.ui.TextField numtel = new com.codename1.ui.TextField(SessionManager.getNumtel(), "num tel", 20, TextField.NUMERIC);
    Picker datenaissance = new Picker();






    // ComboBox<String> sexe = new ComboBox<>("homme","femme");



    private com.codename1.ui.Button gui_Button_2 = new com.codename1.ui.Button();
    private com.codename1.ui.Button gui_Button_3 = new com.codename1.ui.Button();
    private com.codename1.ui.Button gui_Button_1 = new com.codename1.ui.Button();


    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void guiBuilderBindComponentListeners() {
        EventCallbackClass callback = new EventCallbackClass();
        gui_Button_2.addActionListener(e ->
        {
            ServiceUtilisateur.getInstance().modifierprofil(username, password,nom,prenom,numtel,adresse, Resources.getGlobalResources());

            new TrendingForm(Resources.getGlobalResources()).show();


        });
    }

    class EventCallbackClass implements com.codename1.ui.events.ActionListener, com.codename1.ui.events.DataChangedListener {
        private com.codename1.ui.Component cmp;
        public EventCallbackClass(com.codename1.ui.Component cmp) {
            this.cmp = cmp;
        }

        public EventCallbackClass() {
        }

        public void actionPerformed(com.codename1.ui.events.ActionEvent ev) {
            com.codename1.ui.Component sourceComponent = ev.getComponent();
            if(sourceComponent.getParent().getLeadParent() != null) {
                sourceComponent = sourceComponent.getParent().getLeadParent();
            }

            if(sourceComponent == gui_Button_2) {
                onButton_2ActionEvent(ev);
            }
        }

        public void dataChanged(int type, int index) {
        }
    }
    private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
       // datenaissance.setType(Display.PICKER_TYPE_DATE);
       // datenaissance.setDate(SessionManager.getDateNaissance());



        guiBuilderBindComponentListeners();
        setLayout(new com.codename1.ui.layouts.BorderLayout());
        setTitle("modifier votre profil");
        setName("SignInForm");
        addComponent(com.codename1.ui.layouts.BorderLayout.CENTER, gui_Container_1);
        gui_Container_1.setScrollableY(true);
        gui_Container_1.setName("Container_1");
        gui_Container_1.addComponent(gui_Label_1);
        gui_Container_1.addComponent(gui_Component_Group_1);
        gui_Component_Group_1.setName("Component_Group_1");
        Validator validator = new Validator();


        //gui_Component_Group_1.addComponent(email);
      //  validator.addConstraint(email, RegexConstraint.validEmail());
        validator.addSubmitButtons(gui_Button_2);
        gui_Component_Group_1.addComponent(prenom);
        gui_Component_Group_1.addComponent(nom);
        gui_Component_Group_1.addComponent(username);
        gui_Component_Group_1.addComponent(datenaissance);
        gui_Component_Group_1.addComponent(password);
       // gui_Component_Group_1.addComponent(confirmPassword);
        gui_Component_Group_1.addComponent(adresse);
        gui_Component_Group_1.addComponent(numtel);
        validator.addConstraint(nom, new LengthConstraint(3, "inssert"));
        validator.addConstraint(prenom, new LengthConstraint(3, "inssert"));
        validator.addConstraint(username, new LengthConstraint(3, "inssert"));
        validator.addConstraint(adresse, new LengthConstraint(3, "inssert"));
        validator.addConstraint(numtel, new NumericConstraint(true), new Constraint() {
            @Override
            public boolean isValid(Object value) {
                if (numtel.getText().length()!=8) return false;
                else
                    return true;
            }

            @Override
            public String getDefaultFailMessage() {
                return null;
            }
        });

        validator.addConstraint(password,  new LengthConstraint(7, "inssert"));


        try {
            imgv1 = new ImageViewer(Image.createImage("/load.png"));

        } catch (IOException e) {
            Dialog.show("error",e.getMessage(),"ok",null);
        }
        try {
            placeholder1 = EncodedImage.create("/load.png");

        } catch (IOException e) {
            Dialog.show("error",e.getMessage(),"ok",null);
        }

        String url= Statics.URL_REP_IMAGES + SessionManager.getNomImage() ;
        String url2="C:/wamp64/www/WeDev_Zvenn/public/images/"+SessionManager.getNomImage();


        URLImage background = URLImage.createToStorage(placeholder1,url,url,URLImage.RESIZE_SCALE);
        imgv1.setImage(background);

        Label label1 = new Label(background);

        int w = background.getWidth();
        int h = background.getHeight();


        Image maskImage = Image.createImage(w, h);
        Graphics g = maskImage.getGraphics();
        g.setAntiAliased(true);
        g.setColor(0x000000);
        g.fillRect(0, 0, w, h);
        g.setColor(0xffffff);
        g.fillArc(0, 0, w, h, 0, 360);
        Label label2 = new Label(maskImage);

        Object mask = maskImage.createMask();
        //  maskImage.scaledWidth(1000);
        Image maskedImage = background.applyMask(mask);
        Label label3 = new Label(maskedImage);



        /*gui_Text_Field_2.setText("TextField");
        gui_Text_Field_2.setName("Text_Field_2");
        gui_Text_Field_1.setText("TextField");
        gui_Text_Field_1.setName("Text_Field_1");*/
        gui_Container_1.addComponent(gui_Button_2);
        gui_Container_1.addComponent(gui_Button_3);
        gui_Label_1.setUIID("CenterLabel");
        gui_Label_1.setName("Label_1");
        gui_Label_1.setIcon(maskedImage.scaledHeight(100));
        gui_Label_1.addPointerPressedListener(e -> {
            if (!"".equals(username.getText())) {
                MultipartRequest cr = new MultipartRequest();
                String filePath = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);

                cr.setUrl(Statics.URL_UPLOAD);
                cr.setPost(true);
                String mime = "image/jpeg";
                try {
                    cr.addData("file", filePath, mime);
                } catch (IOException ex) {
                    Dialog.show("Error", ex.getMessage(), "OK", null);
                }
                String random = UUID.randomUUID().toString();
                cr.setFilename("file", random+ ".jpg");//any unique name you want

                InfiniteProgress prog = new InfiniteProgress();
                Dialog dlg = prog.showInifiniteBlocking();
                cr.setDisposeOnCompletion(dlg);
                NetworkManager.getInstance().addToQueueAndWait(cr);
                ServiceUtilisateur.getInstance().modifierImage(random,resourceObjectInstance);
                Dialog.show("Success", "Image uploaded", "OK", null);

                SessionManager.setNomImage(random + ".jpg");

                gui_Label_1.setIcon(maskedImage.scaledHeight(100));


            } else {
                Dialog.show("Error", "Invalid image name", "Ok", null);
            }

        });
        gui_Component_Group_1.setName("Component_Group_1");
        gui_Button_2.setText("Modifier");
        gui_Button_2.setName("Button_2");
        gui_Button_3.setText("Forgot Your Password");
        gui_Button_3.setUIID("CenterLabelSmall");
        gui_Button_3.setName("Button_3");
        addComponent(com.codename1.ui.layouts.BorderLayout.SOUTH, gui_Button_1);
        gui_Container_1.setScrollableY(true);
        gui_Container_1.setName("Container_1");
       // gui_Button_1.setText("Create New Account");
        gui_Button_1.setUIID("CenterLabel");
        gui_Button_1.setName("Button_1");
    }// </editor-fold>

    //-- DON'T EDIT ABOVE THIS LINE!!!
    public void onButton_2ActionEvent(com.codename1.ui.events.ActionEvent ev) {
        new InboxForm().show();
    }

}
