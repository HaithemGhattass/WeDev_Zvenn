/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.mycompagny.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.contacts.Contact;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompagny.entities.Restaurant;
import com.mycompagny.services.ServiceRestaurant;
import com.mycompagny.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;

/**
 * GUI builder created Form
 *
 * @author shai
 */
public class ToggleForm extends BaseForm {


    public ToggleForm(Resources resourceObjectInstance)  {

        ImageViewer imgv ;












        installSidemenu(resourceObjectInstance);
        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_PUBLIC, e -> {
        });
        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD,e -> {
            new AjoutRestaurantForm(Resources.getGlobalResources()).show();
        });

        setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        getToolbar().addCommandToRightBar("", resourceObjectInstance.getImage("toolbar-profile-pic.png"), e -> {});


        ArrayList<Restaurant> list = ServiceRestaurant.getInstance().affichagemesRestaurant(SessionManager.getId());
        for (Restaurant rec : list) {

            Container gui_Container_1 = new Container(new BorderLayout());
            MultiButton gui_Multi_Button_1 = new MultiButton();
            MultiButton gui_LA = new MultiButton();
            Container gui_imageContainer1 = new Container(new BorderLayout());
            Container gui_Container_2 = new Container(new BorderLayout());
            TextArea gui_Text_Area_1 = new TextArea();
            Button gui_Button_1 = new Button();
            Button gui_delete = new Button();
            Button gui_edit = new Button();
            Button gui_add_event = new Button();

            Label gui_separator1 = new Label();
            //  private com.codename1.ui.Container gui_null_1_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
            // private com.codename1.components.MultiButton gui_null_1_1_1 = new com.codename1.components.MultiButton();
            MultiButton gui_newYork = new MultiButton();
            Container gui_imageContainer2 = new Container(new BorderLayout());
            Container gui_Container_3 = new Container(new BorderLayout());
            TextArea gui_Text_Area_2 = new TextArea();
            Button gui_Button_2 = new Button();
            Label gui_Label_1_1_1 = new Label();

            gui_separator1.setShowEvenIfBlank(true);
            gui_Label_1_1_1.setShowEvenIfBlank(true);

            EncodedImage placeholder =EncodedImage.createFromImage(Image.createImage((Math.round(Display.getInstance().getDisplayWidth())), (Math.round(Display.getInstance().getDisplayWidth()))/3, 0xffff0000), true);
            try {
                imgv = new ImageViewer(Image.createImage("/load.png"));

            } catch (IOException e) {
                Dialog.show("error",e.getMessage(),"ok",null);
            }
            try {
                placeholder = EncodedImage.create("/load.png");

            } catch (IOException e) {
                Dialog.show("error",e.getMessage(),"ok",null);
            }
            URLImage background = URLImage.createToStorage(placeholder, "C:/wamp64/www/WeDev_Zvenn/public/images/"+rec.getNomImage() ,
                    Statics.URL_REP_IMAGES+rec.getNomImage());
            //  String url= Statics.URL_REP_IMAGES + rec.getNomImage();

            background.fetch();
            //  URLImage background = URLImage.createToStorage(placeholder,url,url,URLImage.RESIZE_SCALE);

            ScaleImageLabel sl = new ScaleImageLabel(background);
            // imgv.setImage(background);
            sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
            gui_imageContainer1.add(BorderLayout.CENTER, sl);
            //sl = new ScaleImageLabel(resourceObjectInstance.getImage("bridge.jpg"));
            // sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
            // gui_imageContainer2.add(BorderLayout.CENTER, sl);



            FontImage.setMaterialIcon(gui_LA, FontImage.MATERIAL_LOCATION_ON);
            gui_LA.setIconPosition(BorderLayout.EAST);

            FontImage.setMaterialIcon(gui_newYork, FontImage.MATERIAL_LOCATION_ON);
            gui_newYork.setIconPosition(BorderLayout.EAST);

            gui_Text_Area_2.setRows(2);
            gui_Text_Area_2.setColumns(100);
            gui_Text_Area_2.setGrowByContent(false);
            gui_Text_Area_2.setEditable(false);
            gui_Text_Area_1.setRows(2);
            gui_Text_Area_1.setColumns(100);
            gui_Text_Area_1.setGrowByContent(false);
            gui_Text_Area_1.setEditable(false);
            addComponent(gui_Container_1);
            gui_Container_1.setName("Container_1");
            gui_Container_1.addComponent(BorderLayout.CENTER, gui_Multi_Button_1);
            gui_Container_1.addComponent(BorderLayout.EAST, gui_LA);
            gui_Multi_Button_1.setUIID("Label");
            gui_Multi_Button_1.setName("Multi_Button_1");
            gui_Multi_Button_1.setPropertyValue("line1", "" + rec.getNom());
            gui_Multi_Button_1.setPropertyValue("line2", "" + rec.getCategorieRestaurant());
            gui_Multi_Button_1.setPropertyValue("uiid1", "Label");
            gui_Multi_Button_1.setPropertyValue("uiid2", "RedLabel");
            gui_LA.setUIID("Label");
            gui_LA.setName("xxx");
            gui_LA.setPropertyValue("line1", "" + rec.getGouvernorat());
            gui_LA.setPropertyValue("line2", "" + rec.getAddresse());
            gui_LA.setPropertyValue("uiid1", "SlightlySmallerFontLabel");
            gui_LA.setPropertyValue("uiid2", "RedLabelRight");
            addComponent(gui_imageContainer1);
            gui_imageContainer1.setName("imageContainer1");
            gui_imageContainer1.addComponent(BorderLayout.SOUTH, gui_Container_2);
            gui_Container_2.addComponent(BorderLayout.SOUTH,gui_Container_3);
            gui_Container_2.setName("Container_2");
            gui_Container_2.addComponent(BorderLayout.WEST, gui_Text_Area_1);
            gui_Container_2.addComponent(BorderLayout.EAST, gui_Button_1);
            gui_Container_3.addComponent(BorderLayout.EAST,gui_delete);
            gui_Container_3.addComponent(BorderLayout.WEST,gui_edit);
            gui_Container_3.addComponent(BorderLayout.CENTER,gui_add_event);
            gui_Text_Area_1.setText("" + rec.getDescription());
            gui_Text_Area_1.setUIID("SlightlySmallerFontLabelLeft");
            gui_Text_Area_1.setName("Text_Area_1");
            gui_Button_1.setText("");
            gui_Button_1.setUIID("Label");
            gui_Button_1.setName("Button_1");
            gui_delete.setText("");
            gui_delete.setUIID("Label");
            gui_delete.setName("Button_1");
            gui_edit.setText("");
            gui_edit.setUIID("Label");
            gui_add_event.setUIID("Label");
            gui_edit.setName("Button_1");
            FontImage.setMaterialIcon(gui_Button_1, "".charAt(0));
            FontImage.setMaterialIcon(gui_delete, FontImage.MATERIAL_DELETE);
            FontImage.setMaterialIcon(gui_edit, FontImage.MATERIAL_EDIT);
            FontImage.setMaterialIcon(gui_add_event, FontImage.MATERIAL_EVENT);



            gui_Button_1.addPointerPressedListener(l -> {
                //ServiceRestaurant.getInstance().affichageReclamations(rec.getId());
                //ArrayList<Reclamation>  list = ServiceRestaurant.getInstance().affichageReclamations(2);
                new AjoutProduitForm(rec.getId(), resourceObjectInstance).show();

            });
            gui_add_event.addPointerPressedListener(l -> {
                //ServiceRestaurant.getInstance().affichageReclamations(rec.getId());
                //ArrayList<Reclamation>  list = ServiceRestaurant.getInstance().affichageReclamations(2);
                new AddEvennementForm(rec.getId(),resourceObjectInstance).show();

            });

            gui_edit.addPointerPressedListener(l -> {
                //ServiceRestaurant.getInstance().affichageReclamations(rec.getId());
                //ArrayList<Reclamation>  list = ServiceRestaurant.getInstance().affichageReclamations(2);
                new ModifierRestaurantForm(resourceObjectInstance,rec).show();

            });

            gui_delete.addActionListener(l->{

                Dialog dig = new Dialog("Suppression");
                if (dig.show("Suppression","Vous voulez supprimer votre restauran?t","Annuler","Oui")){
                    dig.dispose();
                }
                else {
                    dig.dispose();

                    if (ServiceRestaurant.getInstance().deleteRestaurant(rec.getId())) {
                        new ToggleForm(resourceObjectInstance).show();


                    }
                }




            });



        }
//RECHERCHE
        getToolbar().addSearchCommand(e -> {
            String text = (String)e.getSource();
            if(text == null || text.length() == 0) {
                // clear search
                for(Component cmp : getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                getContentPane().animateLayout(150);
            } else {
                text = text.toLowerCase();
                for(Component cmp : getContentPane()) {
                    MultiButton mb = (MultiButton)cmp;
                    String line1 = mb.getTextLine1();
                    String line2 = mb.getTextLine2();
                    boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1 ||
                            line2 != null && line2.toLowerCase().indexOf(text) > -1;
                    mb.setHidden(!show);
                    mb.setVisible(show);
                }
                getContentPane().animateLayout(150);
            }
        }, 4);
        //END RECHERCHE
    }

    //-- DON'T EDIT BELOW THIS LINE!!!
// <editor-fold defaultstate="collapsed" desc="Generated Code">
    // </editor-fold>
//-- DON'T EDIT ABOVE THIS LINE!!!
    @Override
    protected boolean isCurrentmeRestaurant() {
        return true;
    }

}
