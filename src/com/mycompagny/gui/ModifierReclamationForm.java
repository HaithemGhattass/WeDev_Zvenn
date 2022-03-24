/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompagny.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompagny.entities.Reclamation;
import com.mycompagny.services.ServiceReclamation;

/**
 *
 * @author mtar
 */
public class ModifierReclamationForm extends BaseForm {
    Form current;
      public ModifierReclamationForm(Resources res,Reclamation r){
Toolbar tb = new Toolbar(true);
current = this;
setToolbar(tb);

    getTitleArea().setUIID("containrer");
    setTitle("Zven");
    getContentPane().setScrollVisible(false);
    
     TextField titre = new TextField(r.getTitre(),"Titre",20,TextField.ANY);
     TextField description = new TextField(r.getDescription(),"Description",20,TextField.ANY);
     TextField etat = new TextField(r.getEtat(),"Etat",20,TextField.ANY);
     titre.setUIID("TextFieldBlack");
     description.setUIID("TextFieldBlack");
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
       new ListReclamationForm(res).show();
   } 
   });
   
      Button btnAnnuler = new Button("Annuler");          
   btnAnnuler.addActionListener(l ->{
       
        new ListReclamationForm(res).show();
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
