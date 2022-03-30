package com.mycompagny.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.messaging.Message;
import com.codename1.ui.*;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompagny.entities.Commande;
import com.mycompagny.services.ServiceCommande;

import java.util.ArrayList;


public class ListeCommandesForm extends BaseForm{

    Form current;
    public ListeCommandesForm(Resources res){
        initGuiBuilderComponents(res);
        Toolbar tb =  new Toolbar();
        current=this;
        setToolBar(tb);
        getTitleArea().setUIID("container");
        setTitle("Liste Commandes");
        getContentPane().setScrollVisible(false);

        tb.addSearchCommand(e->{


        });
        Tabs swipe= new Tabs();
        Label l1= new Label();
        Label l2 = new Label();
        addTab(swipe,l1, res.getImage("livraison.jpg"),"","",res);


        ArrayList<Commande> list = ServiceCommande.getInsance().AffichageCommandes();
        for(Commande cmd:list){


            Label adresseTxt = new Label("Adresse : "+cmd.getAdresseLivraison(),"NewsTopLine2");
            Label totalTxt = new Label("Total : "+cmd.getTotalCommande(),"NewsTopLine2");
            Label modeTxt = new Label("Mode Livraison : "+cmd.getModeLivraison(),"NewsTopLine2");
            Label renseignementTxt = new Label("Renseignement : ","NewsTopLine2");
            String v="";
           if (cmd.getStatus() == true) {
                v="Délivrée";

            }else {v="En cours de traitement";}
                Label statusTxt = new Label(""+v);

            statusTxt.setUIID("NewsTopLine2");

            createLineSeparator();

            /*if(cmd.getStatus()!= 0 ){

                statusTxt.setText(" traitement");
            }
            if(cmd.getStatus()== 0 ){

                statusTxt.setText(" delivree");
                //statusTxt.setText("Délivrée");
            }*/
            //staus button
            Label status = new Label();
            status.setUIID("NewsTopLine");
            Style statusStyle= new Style(status.getUnselectedStyle());
            statusStyle.setFgColor(0xf7ad02);
            FontImage statusImg=FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, statusStyle);
            status.setIcon(statusImg);
            status.setTextPosition(LEFT);

            status.addPointerPressedListener(l->{
                int id=cmd.getId();
                cmd.getStatus();
                cmd.setStatus(true);
                if(ServiceCommande.getInsance().setStatus(id, String.valueOf(cmd))){
                    new ListeCommandesForm(res).show();
                }

            });

            //mail button
            Label mail = new Label();
            mail.setUIID("NewsTopLine");
            Style mailStyle= new Style(mail.getUnselectedStyle());
            mailStyle.setFgColor(0xf21f1f);
            FontImage mailImg=FontImage.createMaterial(FontImage.MATERIAL_MAIL, mailStyle);
            mail.setIcon(mailImg);
            mail.setTextPosition(LEFT);

            mail.addPointerPressedListener(l->{
                Message m = new Message("VOTRE COMMANDE DE MONTANT"+" "+cmd.getTotalCommande()+" "+" EST MAINTENANT DÉLIVRÉE !"+"\nÀ LA PROCHAINE.\nZVENDEN \"");
                String textAttachmentUri = "zvenn";
                m.getAttachments().put(textAttachmentUri, "text/plain");
                //Display.getInstance().sendMessage(new String[]{/*mail l user a faire*/ SessionManager.getEmail()}, "FELICITATION !", m);
                Display.getInstance().sendMessage(new String[]{/*mail l user a faire*/ "yassmin.mouaddeb@esprit.tn"}, "FELICITATION !", m);
            });

            add(BoxLayout.encloseY(
                    BoxLayout.encloseX(adresseTxt),
                    BoxLayout.encloseX(totalTxt),
                    BoxLayout.encloseX(statusTxt,status,mail)
            ));




        }
    }


    private void addTab(Tabs swipe, Label spacer, Image image, String text, String string1, Resources res) {

        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label overLay = new Label("","ImageOverlay");


        Container page1 =
                LayeredLayout.encloseIn(
                        imageScale,
                        overLay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(text, "LargeWhiteText"),
                                        spacer
                                )
                        )
                );

        swipe.addTab("",res.getImage("livraison.jpg"), page1);




    }

    public void bindButtonSelection(Button btn , Label l ) {

        btn.addActionListener(e-> {
            if(btn.isSelected()) {
                updateArrowPosition(btn,l);
            }
        });
    }

    private void updateArrowPosition(Button btn, Label l) {

        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth()  / 2  - l.getWidth() / 2 );
        l.getParent().repaint();
    }



    private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
        setLayout(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));

    }

    public Component createLineSeparator() {
        Label separator = new Label("", "WhiteSeparator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }

}
