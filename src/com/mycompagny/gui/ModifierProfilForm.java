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

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.TextField;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.*;
import com.mycompagny.services.ServiceUtilisateur;

import java.util.Vector;

/**
 * GUI builder created Form
 *
 * @author Shai Almog
 */
public class ModifierProfilForm extends com.codename1.ui.Form {
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




        /*gui_Text_Field_2.setText("TextField");
        gui_Text_Field_2.setName("Text_Field_2");
        gui_Text_Field_1.setText("TextField");
        gui_Text_Field_1.setName("Text_Field_1");*/
        gui_Container_1.addComponent(gui_Button_2);
        gui_Container_1.addComponent(gui_Button_3);
        gui_Label_1.setUIID("CenterLabel");
        gui_Label_1.setName("Label_1");
        gui_Label_1.setIcon(resourceObjectInstance.getImage("profile_image.png"));
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
