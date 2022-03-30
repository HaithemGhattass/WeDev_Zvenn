package com.mycompagny.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.*;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompagny.entities.Commande;
import com.mycompagny.services.ServiceCommande;

import java.util.Vector;

public class ModifierCommandeForm extends BaseForm{

    Form current;
    public ModifierCommandeForm(Resources res, Commande cmd){

        initGuiBuilderComponents(res);
        installSidemenu(res);

        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_PUBLIC, e -> {});
        Toolbar tb =  new Toolbar();
        current=this;
        setToolBar(tb);
        getTitleArea().setUIID("container");
        setTitle("Modifier Commande");
        getContentPane().setScrollVisible(false);

        TextField adresseLivraison = new TextField(cmd.getAdresseLivraison(),"adresseLivraison",20,TextField.ANY);
        adresseLivraison.setUIID("NewsTopLine");
        adresseLivraison.setSingleLineTextArea(focusScrolling);

        Vector<String> vectormodeLiv;
        vectormodeLiv = new Vector();
        vectormodeLiv.add("Livraison à domicile");
        vectormodeLiv.add("Retrait en restaurant");
        ComboBox<String>modeLivraison = new ComboBox<>(vectormodeLiv);
        modeLivraison.setUIID("NewsTopLine");

        Button modifier = new Button("UPDATE");
        modifier.addPointerPressedListener(l->{

            cmd.setAdresseLivraison(adresseLivraison.getText());
            cmd.setModeLivraison(modeLivraison.getSelectedItem());


            if(ServiceCommande.getInsance().modifierCommande(cmd)){
                new ListeCommandesForm(res).show();
            }});

        Button annuler = new Button("ANNULER");
        annuler.addPointerPressedListener(l->{
            new ListeCommandesForm(res).show();
        });

        Label l2 = new Label("");

        Label l3 = new Label("");

        Label l4 = new Label("");

        Label l5 = new Label("");

        Label l1 = new Label();

        Container content = BoxLayout.encloseY(
                l1, l2,
                new FloatingHint(adresseLivraison),
                createLineSeparator(),
                new ComboBox(modeLivraison),
                createLineSeparator(),
                modifier,
                annuler


        );

        add(content);
        show();



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
