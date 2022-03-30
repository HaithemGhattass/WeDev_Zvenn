package com.mycompagny.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompagny.entities.Commande;
import com.mycompagny.services.ServiceCommande;

import java.util.ArrayList;

public class ListeCommandesById extends BaseForm{

    Form current;
    public ListeCommandesById(Resources res){
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

        int id = SessionManager.getId();
        ArrayList<Commande> list = ServiceCommande.getInsance().AffichageCommandesById(id);
        for(Commande cmd:list){

            addButton(cmd,res);

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

    private void addButton(Commande cmd, Resources res) {

        Label adresseTxt = new Label("Adresse : "+cmd.getAdresseLivraison(),"NewsTopLine2");
        Label totalTxt = new Label("Total : "+ SessionManager.getPrix(),"NewsTopLine2");
        Label modeTxt = new Label("Mode Livraison : "+cmd.getModeLivraison(),"NewsTopLine2");
        Label renseignementTxt = new Label("Renseignement : "+cmd.getRenseignement(),"NewsTopLine2");
        Label statusTxt = new Label("Status : "+cmd.getStatus(),"NewsTopLine2" );

        createLineSeparator();

       /* if(cmd.getStatus()== 0 ){
            statusTxt.setText("En cours de traitement");
        }
        else{
            statusTxt.setText("Délivrée");
        }*/
        //delete button
        Label delete = new Label();
        delete.setUIID("NewsTopLine");
        Style deleteStyle= new Style(delete.getUnselectedStyle());
        deleteStyle.setFgColor(0xf21f1f);
        FontImage deleteImg=FontImage.createMaterial(FontImage.MATERIAL_DELETE, deleteStyle);
        delete.setIcon(deleteImg);
        delete.setTextPosition(RIGHT);

        delete.addPointerPressedListener(l->{
            Dialog dialog = new Dialog("Suppression");
            if(dialog.show("SUPPRESSION","VOULEZ-VOUS SUPPRIMER CETTE COMMANDE ?","ANNULER","OK")){
                dialog.dispose();
            }else{
                dialog.dispose();
                if(ServiceCommande.getInsance().deleteCommande(cmd.getId())){
                    new ListeCommandesForm(res).show();
                }

            }

        });

        //update button
        Label update = new Label();
        update.setUIID("NewsTopLine");
        Style updateStyle= new Style(update.getUnselectedStyle());
        updateStyle.setFgColor(0xf7ad02);
        FontImage updateImg=FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, updateStyle);
        update.setIcon(updateImg);
        update.setTextPosition(LEFT);

        update.addPointerPressedListener(l->{
            new ModifierCommandeForm(res, cmd).show();

        });

        add(BoxLayout.encloseY(
                BoxLayout.encloseX(adresseTxt),
                BoxLayout.encloseX(totalTxt),
                BoxLayout.encloseX(modeTxt),
                BoxLayout.encloseX(statusTxt,delete,update)
        ));


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



