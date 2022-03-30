package com.mycompagny.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompagny.entities.Commande;
import com.mycompagny.services.ServiceCommande;

import java.util.Vector;


public class AjouterCommandeForm extends BaseForm{

    Form current;
    public AjouterCommandeForm(Resources res){
        initGuiBuilderComponents(res);
        Toolbar tb =  new Toolbar();
        current=this;
        setToolBar(tb);
        getTitleArea().setUIID("container");
        setTitle("Ajouter Commande");
        getContentPane().setScrollVisible(false);

        tb.addSearchCommand(e->{


        });
        Tabs swipe= new Tabs();
        Label l1= new Label();
        Label l2 = new Label();
        addTab(swipe,l1, res.getImage("livraison.jpg"),"","",res);


        TextField adresseLivraison = new TextField("","Adresse de Livraison");
        adresseLivraison.setUIID("TextFieldBlack");
        addStringValue("Adresse de Livraison",adresseLivraison);

        TextField totalCommande = new TextField("","Total de votre commande");
        totalCommande.setUIID("TextFieldBlack");
        addStringValue("Total de votre commande",totalCommande);

        Vector<String> vectormodeLiv;
        vectormodeLiv = new Vector();
        vectormodeLiv.add("Livraison à domicile");
        vectormodeLiv.add("Retrait en restaurant");
        ComboBox<String>modeLivraison = new ComboBox<>(vectormodeLiv);
        addStringValue("Mode Livraison",modeLivraison);

        TextField renseignement = new TextField("","Renseignement");
        renseignement.setUIID("TextFieldBlack");
        addStringValue("Renseignement",renseignement);

        Button b = new Button("Ajouter");
        addStringValue("", b);
        b.addActionListener((e)->{
            try{
                if(adresseLivraison.getText()==""||  totalCommande.getText()==""||renseignement.getText()==""){
                    Dialog.show("Veuillez vérifier vos données","", "Annuler", "ok");
                }else{
                    //Loading après l'insertion de données
                    InfiniteProgress ip = new InfiniteProgress();
                    final Dialog iDialog =ip.showInfiniteBlocking();

                    Commande cmd = new Commande(String.valueOf(adresseLivraison.getText()).toString(),
                            Float.parseFloat(totalCommande.getText()),
                           modeLivraison.getSelectedItem().toString(),
                            String.valueOf(renseignement.getText().toString()),
                            true,
                            SessionManager.getId());
                    System.out.println("Data="+cmd);


                    ServiceCommande.getInsance().AjouterCommande(cmd);
                    iDialog.dispose();//pour arreter le loading

                    new ListeCommandesById(res).show();
                    refreshTheme();
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        });
    }

    private void addStringValue(String s, Component v) {

        add(BorderLayout.west(new Label(s,"PaddedLabel"))
                .add(BorderLayout.CENTER,v));
        add(createLineSeparator(0xeeeeee));
    }

    public Component createLineSeparator(int color) {
        Label separator = new Label("", "WhiteSeparator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(255);
        separator.setShowEvenIfBlank(true);
        return separator;
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


}
