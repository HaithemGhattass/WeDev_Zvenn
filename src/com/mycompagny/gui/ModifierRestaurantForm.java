package com.mycompagny.gui;

import com.codename1.components.ImageViewer;
import com.mycompagny.services.ServiceRestaurant;
import com.codename1.ui.*;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompagny.entities.Restaurant;


public class ModifierRestaurantForm extends BaseForm {
    ModifierRestaurantForm current;
    //BlogArticle article;

    public ModifierRestaurantForm(Resources resourceObjectInstance, Restaurant r) {

        EncodedImage enc;
        Image imgs;
        ImageViewer imgv;
        String image = "";
        Button btnUpload;


        //       super("Upload Image", BoxLayout.y());
        /*    btnUpload = new Button("Upload");
            try{
                imgv = new ImageViewer(Image.createImage("/load.png"));
            }catch(IOException ex){
                Dialog.show("Error",ex.getMessage(),"ok",null);
            }
            btnUpload.addActionListener((evt) -> {
                MultipartRequest cr = new MultipartRequest();
                String filePath = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);

                cr.setUrl(Statics.URL_UPLOAD);
                cr.setPost(true);
                String mime = "image/*";
                try {
                    cr.addData("file", filePath, mime);
                } catch (IOException ex) {
                    Dialog.show("Error", ex.getMessage(), "OK", null);
                }
                String uniqueID = UUID.randomUUID().toString();
                String extension = "";

                int i = filePath.lastIndexOf('.');
                if (i > 0) {
                    extension = filePath.substring(i+1);
                }
                cr.setFilename("file", uniqueID+"."+extension);//any unique name you want
                System.out.println(uniqueID+"."+extension);
                image = uniqueID+"."+extension;
                InfiniteProgress prog = new InfiniteProgress();
                Dialog dlg = prog.showInifiniteBlocking();
                cr.setDisposeOnCompletion(dlg);
                NetworkManager.getInstance().addToQueueAndWait(cr);
                Dialog.show("Success", "Image uploaded", "OK", null);
                try{
                    enc = EncodedImage.create("/load.png");
                }catch(IOException ex){
                    Dialog.show("Error",ex.getMessage(),"ok",null);
                }
                String url = Statics.URL_REP_IMAGES + image;
                imgs = URLImage.createToStorage(enc,url,url,URLImage.RESIZE_SCALE);
                imgv.setImage(imgs);
            });*/
        //  add(imgv);
        //add(btnUpload);
        installSidemenu(resourceObjectInstance);
        //   initGuiBuilderComponents(resourceObjectInstance);
        getTitleArea().setUIID("Container");
        getToolbar().setUIID("Container");
        getToolbar().getTitleComponent().setUIID("SigninTitle");
        FontImage mat = FontImage.createMaterial(FontImage.MATERIAL_CLOSE, "SigninTitle", 3.5f);
        getToolbar().addCommandToLeftBar("", mat, e -> new SplashForm().show());
        getContentPane().setUIID("SignInForm");

        TextField nomRestaurant = new TextField(r.getNom(),"nom", 20 , TextField.ANY);
        TextField description = new TextField(r.getDescription(),"description", 20 , TextField.ANY);

        Picker gouvernorat = new Picker();
        gouvernorat.setType(Display.PICKER_TYPE_STRINGS);
        gouvernorat.setStrings("TUNIS", "ARIANA", "BEJA", "A Feast For Crows",
                "A Dance With Dragons", "The Winds of Winter", "A Dream of Spring");
        gouvernorat.setSelectedString(r.getGouvernorat());



        TextField adresse = new TextField(r.getAddresse(),"adresse", 20 , TextField.ANY);
        TextField cite = new TextField(r.getCite(),"cite", 20 , TextField.ANY);
        TextField codePostal = new TextField(Float.valueOf(r.getCodePostal()).toString(),"code postal",4,TextField.NUMERIC);
        Picker categorie = new Picker();
        categorie.setType(Display.PICKER_TYPE_STRINGS);
        categorie.setStrings("A Game of Thrones", "A Clash Of Kings", "A Storm Of Swords", "A Feast For Crows",
                "A Dance With Dragons", "The Winds of Winter", "A Dream of Spring");
        categorie.setSelectedString(r.getCategorieRestaurant());

        Picker timePicker = new Picker();
        Picker timePicker2 = new Picker();

        timePicker.setType(Display.PICKER_TYPE_TIME);
        timePicker.setTime(8 * 60);


        timePicker2.setType(Display.PICKER_TYPE_TIME);
        timePicker2.setTime(20 * 60);



        initGuiBuilderComponents(resourceObjectInstance,nomRestaurant,description,adresse,gouvernorat,cite,codePostal,categorie,timePicker,timePicker2);

        // gui_Button_2.addActionListener((e) -> {
          /*  gui_Button_2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if (gui_title.getText().length() == 0)
                        Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                    else {
                        try {
                            BlogArticle t = new BlogArticle(gui_title.getText().toString(),gui_contents.getText().toString(),category.getText().toString(),gui_author.getText().toString());
                            t.setImage(image);
                            // BlogArticle t = new BlogArticle(Float.parseFloat(gui_title.getText().toString()));
                            if (servicesblogs.getInstance().ajouterArticle(t)) {
                                Dialog.show("Success", "Connection accepted", new Command("OK"));
                            } else
                                Dialog.show("ERROR", "Server error", new Command("OK"));
                        } catch (NumberFormatException e) {
                            Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                        }

                    }

                }

            });




        }*/
        gui_Button_2.addPointerPressedListener(l -> {

            r.setNom(nomRestaurant.getText());
            r.setDescription(description.getText());
            r.setAddresse(adresse.getText());
            r.setGouvernorat(gouvernorat.getSelectedString());
            r.setCite(cite.getText());
            r.setCodePostal(Integer.parseInt(codePostal.getText()));
            r.setCategorieRestaurant(categorie.getText());
            r.setHeureOuverture(String.valueOf(timePicker.getTime() / 60).toString() + ":" + String.valueOf(timePicker.getTime() % 60).toString());

            r.setHeureFermeture(String.valueOf(timePicker2.getTime() / 60).toString() + ":" + String.valueOf(timePicker2.getTime() % 60).toString());





            //appel fct modifier m service

            if(ServiceRestaurant.getInstance().modificationRestaurant(r)){
                new TrendingForm(resourceObjectInstance).show();

            }
        });
        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(e -> {

            new TrendingForm(resourceObjectInstance).show();

        });

    }

    //-- DON'T EDIT BELOW THIS LINE!!!
    com.codename1.ui.Container gui_Container_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
    com.codename1.ui.Label gui_Label_1 = new com.codename1.ui.Label();
    com.codename1.ui.ComponentGroup gui_Component_Group_1 = new com.codename1.ui.ComponentGroup();

    //    com.codename1.ui.TextField gui_title = new com.codename1.ui.TextField(r.getTitle());
    //  com.codename1.ui.TextField gui_author = new com.codename1.ui.TextField(r.getAuthor());
    //  com.codename1.ui.TextArea gui_contents = new com.codename1.ui.TextField(r.getContents());
    com.codename1.ui.Button gui_Button_2 = new com.codename1.ui.Button();
    com.codename1.ui.Button gui_Button_3 = new com.codename1.ui.Button();
    com.codename1.ui.Button gui_Button_1 = new com.codename1.ui.Button();


    // Picker stringPicker2 = new Picker();
   // Picker category = new Picker();

   // Picker date = new Picker();
    //Picker timePicker2 = new Picker();






    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void guiBuilderBindComponentListeners() {
        ModifierRestaurantForm.EventCallbackClass callback = new ModifierRestaurantForm.EventCallbackClass();
        gui_Button_2.addActionListener(callback);
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


        }

        public void dataChanged(int type, int index) {
        }
    }
    private void initGuiBuilderComponents(Resources resourceObjectInstance, TextField nomRestaurant, TextField description, TextField adresse, Picker gouvernorat, TextField cite, TextField codePostal, Picker categorie,Picker timePicker,Picker timePicker2) {
        guiBuilderBindComponentListeners();
        setLayout(new com.codename1.ui.layouts.BorderLayout());
        setTitle("update");
        setName("update this article");
        addComponent(com.codename1.ui.layouts.BorderLayout.CENTER, gui_Container_1);
        gui_Container_1.setScrollableY(true);
        gui_Container_1.setName("Container_1");
        gui_Container_1.addComponent(gui_Label_1);
        gui_Container_1.addComponent(gui_Component_Group_1);
        gui_Component_Group_1.setName("Component_Group_1");
        gui_Component_Group_1.addComponent(nomRestaurant);
        gui_Component_Group_1.addComponent(description);
        gui_Component_Group_1.addComponent(gouvernorat);

        // gui_Component_Group_1.addComponent(stringPicker2);
        gui_Component_Group_1.addComponent(adresse);
        gui_Component_Group_1.addComponent(cite);
        gui_Component_Group_1.addComponent(codePostal);


        //gui_Component_Group_1.addComponent(gui_cite);
        //gui_Component_Group_1.addComponent(gui_code_postal);
        gui_Component_Group_1.addComponent(categorie);
        gui_Component_Group_1.addComponent(timePicker);
        gui_Component_Group_1.addComponent(timePicker2);

        // gui_Component_Group_1.addComponent(date);
        //gui_Component_Group_1.addComponent(timePicker2);
        //  gui_Component_Group_1.addComponent(imgv);
        // gui_Component_Group_1.addComponent(btnUpload);





     /*   category.setType(Display.PICKER_TYPE_STRINGS);
        category.setStrings("Art", "Memes", "Crypto", "photography",
                "Music", "Games");



        date.setType(Display.PICKER_TYPE_DATE);*/


        gui_Container_1.addComponent(gui_Button_2);
        gui_Container_1.addComponent(gui_Button_3);
        gui_Label_1.setUIID("CenterLabel");
        gui_Label_1.setName("Label_1");
        gui_Label_1.setIcon(resourceObjectInstance.getImage("profile_image.png"));
        gui_Component_Group_1.setName("Component_Group_1");
        gui_Button_2.setText("Modifier Restaurant");
        gui_Button_2.setName("Button_2");
        gui_Button_3.setText("Forgot Your Password");
        gui_Button_3.setUIID("CenterLabelSmall");
        gui_Button_3.setName("Button_3");
        addComponent(com.codename1.ui.layouts.BorderLayout.SOUTH, gui_Button_1);
        gui_Container_1.setScrollableY(true);
        gui_Container_1.setName("Container_1");
        gui_Button_1.setText("Create New Account");
        gui_Button_1.setUIID("CenterLabel");
        gui_Button_1.setName("Button_1");



    }
}