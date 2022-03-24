package com.mycompagny.gui;
import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.SpanLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.messaging.Message;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;

import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.validation.*;
import com.mycompagny.entities.Restaurant;
import com.mycompagny.services.ServiceRestaurant;
import com.mycompagny.utils.Statics;


import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.UUID;

public class AjoutRestaurantForm extends BaseForm {

    public AjoutRestaurantForm() {
        this(Resources.getGlobalResources());
    }

    public AjoutRestaurantForm(Resources resourceObjectInstance) {

        installSidemenu(resourceObjectInstance);
        initGuiBuilderComponents(resourceObjectInstance);
        getTitleArea().setUIID("Container");
        getToolbar().setUIID("Container");
        getToolbar().getTitleComponent().setUIID("SigninTitle");
        getContentPane().setUIID("SignInForm");


        gui_Button_2.addActionListener((e) -> {

            try {
                if (gui_nom_restaurant.getText() == "" || gui_description.getText() == "" || gui_addresse.getText() == "" || gui_cite.getText() == "" || gui_code_postal.getText() == "") {
                    Dialog.show("veuiller remplir les champs", "", "Annuler", "OK");

                } else {
                    InfiniteProgress ip = new InfiniteProgress();
                    final Dialog iDialog = ip.showInfiniteBlocking();

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm");
                    // SimpleDateFormat formatT = new SimpleDateFormat("HH:mm");
                    Date d2 = new Date();

                    Restaurant r = new Restaurant(SessionManager.getId(),
                            String.valueOf(gui_nom_restaurant.getText()
                    ).toString(),
                            String.valueOf(stringPicker.getText()).toString(),
                            String.valueOf(gui_description.getText()).toString(),
                            String.valueOf(stringPicker2.getText()).toString(),
                            String.valueOf(gui_addresse.getText()).toString(),
                            String.valueOf(gui_cite.getText()).toString(),
                            Integer.parseInt(gui_code_postal.getText()),
                            //  timeformat.format(new Date()),
                            //   timeformat.format(new Date(timePicker.getTime())),
                            String.valueOf(timePicker.getTime() / 60).toString() + ":" + String.valueOf(timePicker.getTime() % 60).toString(),
                            //   timePicker.setOnTimeChangedListener( new timePicker.OnTimeChangedListener() {
                            String.valueOf(timePicker2.getTime() / 60).toString() + ":" + String.valueOf(timePicker2.getTime() % 60).toString(),
                            //String.valueOf(gui_nom_restaurant.getText()).toString() , // tansech bch tbadel el imageee
                            UUID.randomUUID().toString(),
                            String.valueOf(gui_nom_restaurant.getText().toString() + ".jpg"),
                            "not verified",
                            format.format(new Date()));

                    System.out.println("data restaurant ==" + r);

                    //appelle methode ajoutRestaurant bch nzidou donnees fel base
                    ServiceRestaurant.getInstance().ajoutRestaurant(r);

                    iDialog.dispose(); //for no loading apres ajout

                    Message m = new Message("Votre restaurant " + r.getNom() + " est ajouté avec sucées :)\"");
                    String textAttachmentUri = "zvenn";
                    m.getAttachments().put(textAttachmentUri, "text/plain");
                    Display.getInstance().sendMessage(new String[]{/*mail l user a faire*/"haithemghattas@gmail.com"}, "felicitation", m);


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
    private com.codename1.ui.TextField gui_code_postal = new com.codename1.ui.TextField("", "1234", 4, TextArea.NUMERIC);
    private com.codename1.ui.TextField gui_cite = new com.codename1.ui.TextField("", "cite");
    private com.codename1.ui.TextField gui_nom_restaurant = new com.codename1.ui.TextField("", "nom Restaurant");
    private com.codename1.ui.TextField gui_addresse = new com.codename1.ui.TextField("", "adresse");
    private com.codename1.ui.TextArea gui_description = new com.codename1.ui.TextField("", "description");
    private com.codename1.ui.Button gui_Button_2 = new com.codename1.ui.Button();
    private com.codename1.ui.Button gui_Button_3 = new com.codename1.ui.Button();
    private com.codename1.ui.Button gui_Button_1 = new com.codename1.ui.Button();
    private com.codename1.ui.Button img1 = new com.codename1.ui.Button();
   // TextField tfImageName = new TextField("", "Image name");
    Button btnUpload = new Button("Upload");
    Picker stringPicker2 = new Picker();
    Picker stringPicker = new Picker();

    Picker timePicker = new Picker();
    Picker timePicker2 = new Picker();
    Validator val = new Validator();
    Validator val2 = new Validator();

    //TextModeLayout tl = new TextModeLayout(3, 2);
    //Form f = new Form("Pixel Perfect", tl);


    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void guiBuilderBindComponentListeners() {
        EventCallbackClass callback = new EventCallbackClass();
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
            if (sourceComponent.getParent().getLeadParent() != null) {
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
        setTitle("Ajouter Restaurant");
        setName("AjoutRestaurantForm");
        addComponent(com.codename1.ui.layouts.BorderLayout.CENTER, gui_Container_1);


        gui_Container_1.setScrollableY(true);
        gui_Container_1.setName("Container_1");
        gui_Container_1.addComponent(gui_Label_1);
        gui_Container_1.addComponent(gui_Component_Group_1);
        gui_Container_1.addComponent(gui_Component_Group_2);
        gui_Container_1.addComponent(gui_Component_Group_3);
        gui_Container_1.addComponent(gui_Component_Group_4);


        gui_Component_Group_1.setName("Component_Group_1");
        gui_Component_Group_1.addComponent(gui_nom_restaurant);
        gui_Component_Group_1.addComponent(gui_description);
       // gui_Component_Group_1.addComponent(tfImageName);
        gui_Component_Group_1.addComponent(btnUpload);

        gui_Component_Group_2.addComponent(stringPicker2);
        gui_Component_Group_2.addComponent(gui_addresse);
        gui_Component_Group_2.addComponent(gui_cite);

        gui_Component_Group_2.addComponent(gui_code_postal);

        gui_Component_Group_3.addComponent(stringPicker);

        gui_Component_Group_4.addComponent(timePicker);
        gui_Component_Group_4.addComponent(timePicker2);
        val.addConstraint(gui_cite, new Constraint() {
            @Override
            public boolean isValid(Object value) {

                if (gui_cite.getText().length() < 4 || gui_cite.getText().length() > 10) return false;
                else
                    return true;
            }

            @Override
            public String getDefaultFailMessage() {
                return "baddeeeeeeeeeel";
            }
        });
        val.addConstraint(gui_addresse, new LengthConstraint(5));
        val.addConstraint(gui_nom_restaurant, new LengthConstraint(3, "inssert"));
        val.addConstraint(gui_description, new LengthConstraint(10));
        val.addConstraint(gui_code_postal, new NumericConstraint(true), new Constraint() {
            @Override
            public boolean isValid(Object value) {
                if (gui_code_postal.getText().length() < 4 || gui_code_postal.getText().length() > 4) return false;
                else
                    return true;
            }

            @Override
            public String getDefaultFailMessage() {
                return null;
            }
        });
        val.addSubmitButtons(gui_Button_2);
        val2.addSubmitButtons(gui_Button_1);


        //f.add(tl.createConstraint().horizontalSpan(2), gui_description);



        btnUpload.addActionListener((evt) -> {
            if (!"".equals(gui_nom_restaurant.getText())) {
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
                cr.setFilename("file", gui_nom_restaurant.getText() + ".jpg");//any unique name you want

                InfiniteProgress prog = new InfiniteProgress();
                Dialog dlg = prog.showInifiniteBlocking();
                cr.setDisposeOnCompletion(dlg);
                NetworkManager.getInstance().addToQueueAndWait(cr);
                Dialog.show("Success", "Image uploaded", "OK", null);
            } else {
                Dialog.show("Error", "Invalid image name", "Ok", null);
            }
        });










        stringPicker.setType(Display.PICKER_TYPE_STRINGS);
        stringPicker.setStrings("A Game of Thrones", "A Clash Of Kings", "A Storm Of Swords", "A Feast For Crows",
                "A Dance With Dragons", "The Winds of Winter", "A Dream of Spring");
        stringPicker.setSelectedString("A Game of Thrones");


        stringPicker2.setType(Display.PICKER_TYPE_STRINGS);
        stringPicker2.setStrings("TUNIS", "ARIANA", "BEJA", "A Feast For Crows",
                "A Dance With Dragons", "The Winds of Winter", "A Dream of Spring");
        stringPicker2.setSelectedString("Tunis");


        timePicker.setType(Display.PICKER_TYPE_TIME);
        timePicker.setTime(8 * 60);


        timePicker2.setType(Display.PICKER_TYPE_TIME);
        timePicker2.setTime(20 * 60);


        //TextField num1 = new TextField("", "1234", 4, TextArea.NUMERIC);

        gui_Container_1.addComponent(gui_Button_2);
        gui_Container_1.addComponent(gui_Button_1);

        gui_Component_Group_1.setName("Component_Group_1");
        gui_Button_2.setText("Ajouter Restaurant");
        gui_Button_2.setName("Button_2");

        gui_Button_1.setText("Annuler");
        gui_Button_1.setUIID("CenterLabel");
        gui_Button_1.setName("Button_1");

        gui_Container_1.setScrollableY(true);
        gui_Container_1.setName("Container_1");





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

