package com.mycompagny.gui;

import com.codename1.capture.Capture;
import com.codename1.components.ClearableTextField;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.CSVParser;
import com.codename1.io.Log;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.messaging.Message;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.list.DefaultListCellRenderer;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;

import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.validation.*;
import com.mycompagny.entities.Reclamation;
import com.mycompagny.entities.Restaurant;
import com.mycompagny.services.ServiceReclamation;
import com.mycompagny.services.ServiceRestaurant;
import com.mycompagny.utils.Statics;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AjouterReclamationForm extends BaseForm {

    public AjouterReclamationForm() {

    }



    public AjouterReclamationForm(int id, Resources resourceObjectInstance) {


        installSidemenu(resourceObjectInstance);
        Container textFields = new Container();
        TableLayout tl;
        if (Display.getInstance().isTablet()) {
            tl = new TableLayout(7, 2);
        } else {
            tl = new TableLayout(14, 1);
        }

        TextField titre = new TextField("", "name");
        titre.setUIID("DemoTextArea");

        TextArea description = new TextArea("", 7, 20, TextArea.ANY);
        description.setUIID("DemoTextArea");
        Button btnUpload = new Button("Upload");
        btnUpload.addActionListener((evt) -> {
            if (!"".equals(titre.getText())) {
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
                cr.setFilename("file", titre.getText() + ".jpg");//any unique name you want

                InfiniteProgress prog = new InfiniteProgress();
                Dialog dlg = prog.showInifiniteBlocking();
                cr.setDisposeOnCompletion(dlg);
                NetworkManager.getInstance().addToQueueAndWait(cr);
                Dialog.show("Success", "Image uploaded", "OK", null);
            } else {
                Dialog.show("Error", "Invalid image name", "Ok", null);
            }
        });

        Button contactUsButton = new Button("Contact Us");

        contactUsButton.addActionListener((e) -> {

            try {
                if (titre.getText() == "" || description.getText() == "") {
                    Dialog.show("veuiller remplir les champs", "", "Annuler", "OK");

                } else {
                    InfiniteProgress ip = new InfiniteProgress();
                    final Dialog iDialog = ip.showInfiniteBlocking();

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Restaurant r = new Restaurant();
                    Reclamation re = new Reclamation(String.valueOf(titre.getText()).toString(),
                            String.valueOf(description.getText()).toString(),

                            "nontraité",
                            format.format(new Date()), String.valueOf(titre.getText().toString() + ".jpg"), String.valueOf(titre.getText()).toString() + ".jpg");
                    System.out.println("data reclamation ==" + re);

                    //appelle methode ajoutReclamation bch nzidou donnees fel base
                    ServiceReclamation.getInstance().ajoutReclamation(re, id);

                    iDialog.dispose(); //for no loading apres ajout



                   // new ListRForm(id,resourceObjectInstance).show();
                    ToastBar.showInfoMessage("Your Message has sent");
                    new ListRForm(id,resourceObjectInstance).showBack();

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }


        });
        Container c =new Container();
        tl.setGrowHorizontally(true);
        textFields.setLayout(tl);
        textFields.add(new Label("Your Name*", "DemoLabel")).
                add(titre).
                add(new SpanLabel("Your Message", "DemoLabel")).
                add(description);



        textFields.add(BorderLayout.SOUTH, contactUsButton);


        textFields.setUIID("Wrapper");

        Container cnt = BoxLayout.encloseY(textFields,btnUpload);

        cnt.setScrollableY(true);
        add(cnt);

    }






}


