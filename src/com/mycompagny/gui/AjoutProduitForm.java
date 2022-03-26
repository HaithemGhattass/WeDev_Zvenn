package com.mycompagny.gui;

import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.messaging.Message;
import com.codename1.ui.*;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.validation.Constraint;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.NumericConstraint;
import com.codename1.ui.validation.Validator;
import com.mycompagny.entities.Produits;
import com.mycompagny.services.ServiceProduits;
import com.mycompagny.utils.Statics;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class AjoutProduitForm extends BaseForm {
    public AjoutProduitForm() {
    }

    @Override
    protected boolean isCurrentTrending() {
        return true;
    }

    public AjoutProduitForm(int id, com.codename1.ui.util.Resources resourceObjectInstance) {
        installSidemenu(resourceObjectInstance);
        initGuiBuilderComponents(resourceObjectInstance);
        getTitleArea().setUIID("Container");
        getToolbar().setUIID("Container");
        getToolbar().getTitleComponent().setUIID("SigninTitle");
        getContentPane().setUIID("SignInForm");


        gui_Button_2.addActionListener((e) -> {

            try {
                if (gui_nom_Produit.getText() == "" || gui_description.getText() == "" ) {
                    Dialog.show("veuiller remplir les champs", "", "Annuler", "OK");

                } else {
                    InfiniteProgress ip = new InfiniteProgress();
                    final Dialog iDialog = ip.showInfiniteBlocking();

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date d2 = new Date();

                    Produits r = new Produits(String.valueOf(gui_nom_Produit.getText()
                    ).toString(),
                            String.valueOf(gui_description.getText()).toString(),
                            Integer.parseInt(gui_prix.getText()),
                            UUID.randomUUID().toString(),
                            String.valueOf(gui_nom_Produit.getText()).toString(),


                            format.format(new Date())

                            );

                    System.out.println("data Produit ==" + r);

                    //appelle methode ajoutRestaurant bch nzidou donnees fel base
                    ServiceProduits.getInstance().AjoutProduit(id,r);

                    iDialog.dispose(); //for no loading apres ajout



                    new TrendingForm(resourceObjectInstance).show();

                    refreshTheme();//actualisation

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

        gui_Button_1.addActionListener((e) -> {

            new TrendingForm(resourceObjectInstance).show();
            refreshTheme();//actualisation

        });



    }

    //-- DON'T EDIT BELOW THIS LINE!!!
    private com.codename1.ui.Container gui_Container_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
    private com.codename1.ui.Label gui_Label_1 = new com.codename1.ui.Label();
    private com.codename1.ui.ComponentGroup gui_Component_Group_4 = new com.codename1.ui.ComponentGroup();
    private com.codename1.ui.ComponentGroup gui_Component_Group_3 = new com.codename1.ui.ComponentGroup();
    private com.codename1.ui.ComponentGroup gui_Component_Group_2 = new com.codename1.ui.ComponentGroup();
    private com.codename1.ui.ComponentGroup gui_Component_Group_1 = new com.codename1.ui.ComponentGroup();
    private com.codename1.ui.TextField gui_nom_Produit = new com.codename1.ui.TextField("", "nom Produit");
    private com.codename1.ui.TextField gui_prix = new com.codename1.ui.TextField("","prix");
    private com.codename1.ui.TextArea gui_description = new com.codename1.ui.TextField("","description");
    private com.codename1.ui.Button gui_Button_2 = new com.codename1.ui.Button();
    private com.codename1.ui.Button gui_Button_3 = new com.codename1.ui.Button();
    private com.codename1.ui.Button gui_Button_1 = new com.codename1.ui.Button();



    Validator val = new Validator();
    Button btnUpload = new Button("Upload");

    //TextModeLayout tl = new TextModeLayout(3, 2);
    //Form f = new Form("Pixel Perfect", tl);









    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void guiBuilderBindComponentListeners() {
        AjoutProduitForm.EventCallbackClass callback = new AjoutProduitForm.EventCallbackClass();
        gui_Button_2.addActionListener(callback);
        gui_Button_1.addActionListener(callback);
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

           /* if(sourceComponent == gui_Button_2) {
                onButton_2ActionEvent(ev);
            }*/
        }

        public void dataChanged(int type, int index) {
        }
    }
    private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
        guiBuilderBindComponentListeners();
        setLayout(new com.codename1.ui.layouts.BorderLayout());
        setTitle("Ajouter Produit");
        setName("AjoutProduitForm");
        addComponent(com.codename1.ui.layouts.BorderLayout.CENTER, gui_Container_1);


        gui_Container_1.setScrollableY(true);
        gui_Container_1.setName("Container_1");
        gui_Container_1.addComponent(gui_Label_1);
        gui_Container_1.addComponent(gui_Component_Group_1);
        gui_Container_1.addComponent(gui_Component_Group_2);
        gui_Container_1.addComponent(gui_Component_Group_3);
        gui_Container_1.addComponent(gui_Component_Group_4);



        gui_Component_Group_1.setName("Component_Group_1");
        gui_Component_Group_1.addComponent(gui_nom_Produit);
        gui_Component_Group_1.addComponent( gui_description);
        gui_Component_Group_1.addComponent(gui_prix);
        gui_Component_Group_1.addComponent(btnUpload);





        val.addConstraint(gui_nom_Produit, new LengthConstraint(3,"inssert"));
        val.addConstraint(gui_description, new LengthConstraint(10));

        val.addSubmitButtons(gui_Button_2);


        //f.add(tl.createConstraint().horizontalSpan(2), gui_description);

        //TextField num1 = new TextField("", "1234", 4, TextArea.NUMERIC);

        gui_Container_1.addComponent(gui_Button_2);
        gui_Container_1.addComponent(gui_Button_1);

        gui_Component_Group_1.setName("Component_Group_1");
        gui_Button_2.setText("Ajouter Produit");
        gui_Button_2.setName("Button_2");

        gui_Button_1.setText("Annuler");
        gui_Button_1.setUIID("CenterLabel");
        gui_Button_1.setName("Button_1");

        gui_Container_1.setScrollableY(true);
        gui_Container_1.setName("Container_1");


        btnUpload.addActionListener((evt) -> {
            if (!"".equals(gui_nom_Produit.getText())) {
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
                cr.setFilename("file", gui_nom_Produit.getText() + ".jpg");//any unique name you want

                InfiniteProgress prog = new InfiniteProgress();
                Dialog dlg = prog.showInifiniteBlocking();
                cr.setDisposeOnCompletion(dlg);
                NetworkManager.getInstance().addToQueueAndWait(cr);
                Dialog.show("Success", "Image uploaded", "OK", null);
            } else {
                Dialog.show("Error", "Invalid image name", "Ok", null);
            }
        });


    }// </editor-fold>

    public Component createLineSeparator(int color) {
        Label separator = new Label("", "WhiteSeparator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(255);
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    //-- DON'T EDIT ABOVE THIS LINE!!!
   /* public void onButton_2ActionEvent(com.codename1.ui.events.ActionEvent ev) {
        new InboxForm().show();
    }*/


    @Override
    protected boolean isCurrentAjoutRestaurant() {
        return true;
    }

}
