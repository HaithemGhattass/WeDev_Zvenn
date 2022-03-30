package com.mycompagny.gui;

import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.messaging.Message;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.TableLayout;
import com.mycompagny.entities.Reclamation;
import com.mycompagny.entities.Reply;
import com.mycompagny.entities.Restaurant;
import com.mycompagny.services.ServiceReclamation;
import com.mycompagny.services.ServiceReply;
import com.mycompagny.utils.Statics;

import java.io.IOException;
import java.util.Date;

public class AjoutReplyForm extends BaseForm{
    public AjoutReplyForm(int id, com.codename1.ui.util.Resources resourceObjectInstance) {

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


        Button contactUsButton = new Button("Reply");
        contactUsButton.addActionListener((e) -> {

            try {
                if (titre.getText() == "" || description.getText() == "") {
                    Dialog.show("veuiller remplir les champs", "", "Annuler", "OK");

                } else {
                    InfiniteProgress ip = new InfiniteProgress();
                    final Dialog iDialog = ip.showInfiniteBlocking();

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    Reply re = new Reply(String.valueOf(titre.getText()).toString(),
                            String.valueOf(description.getText()).toString(),
                            format.format(new Date()));
                    System.out.println("data reply ==" + re);
                    ServiceReply.getInstance().ajoutReply(re, id);
                    iDialog.dispose(); //for no loading apres ajout
                    Message m = new Message("Votre reclamation " + re.getTitre() + " est ajouté avec sucées :)\""+re.getDescription());
                    String textAttachmentUri = "zvenn";
                    m.getAttachments().put(textAttachmentUri, "text/plain");
                    Display.getInstance().sendMessage(new String[]{/*mail l user a faire*/"mtar287@gmail.com"}, "felicitation", m);



                    new TrendingForm(resourceObjectInstance).show();
                    ToastBar.showInfoMessage("Your Message has sent");


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

        Container cnt = BoxLayout.encloseY(textFields);

        cnt.setScrollableY(true);
        add(cnt);

    }
}
