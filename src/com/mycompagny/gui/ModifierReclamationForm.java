package com.mycompagny.gui;

import com.codename1.capture.Capture;
import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import com.mycompagny.entities.Reclamation;
import com.mycompagny.entities.Restaurant;
import com.mycompagny.services.ServiceReclamation;
import com.mycompagny.utils.Statics;

import java.io.IOException;
import java.util.Date;

public class ModifierReclamationForm extends BaseForm{
    public ModifierReclamationForm () {

    }
    public ModifierReclamationForm(com.codename1.ui.util.Resources res, Reclamation r){
        installSidemenu(res);
        Container textFields = new Container();
        TableLayout tl;
        if (Display.getInstance().isTablet()) {
            tl = new TableLayout(7, 2);
        } else {
            tl = new TableLayout(14, 1);
        }
        TextField description = new TextField(r.getDescription(),"Description",20,TextField.ANY);
        TextField titre = new TextField(r.getTitre(),"Titre",20,TextField.ANY);

        TextField etat = new TextField(r.getEtat(),"Etat",20,TextField.ANY);
        description.setUIID("TextFieldBlack");
        titre.setUIID("TextFieldBlack");

        etat.setUIID("TextFieldBlack");
        titre.setSingleLineTextArea(true);
        description.setSingleLineTextArea(true);
        etat.setSingleLineTextArea(true);
        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");
        btnModifier.addPointerPressedListener(l ->{

            r.setTitre(titre.getText());
            r.setDescription(description.getText());
            if(ServiceReclamation.getInstance().modifierReclamation(r))
            {
                new TrendingForm(res).show();
            }
        });

        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(l ->{

            new TrendingForm(res).show();
        });

        Label l2=new Label("");
        Label l3=new Label("");
        Label l4=new Label("");
        Label l5=new Label("");
        Label l1=new Label("");
        Container content = BoxLayout.encloseY(
                l1,l2,
                new FloatingHint(titre),

                new FloatingHint(description),

                l4,l5,
                btnModifier,
                btnAnnuler


        );
        add(content);
        show();

    }
}
