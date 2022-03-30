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

import com.cloudinary.Transformation;
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
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompagny.entities.Restaurant;
import com.mycompagny.services.ServiceRestaurant;
import com.mycompagny.utils.Statics;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * GUI builder created Form
 *
 * @author shai
 */
public class TrendingForm extends BaseForm {
    EncodedImage placeholder ;
    ImageViewer imgv ;
    EncodedImage placeholder1 ;
    ImageViewer imgv1 ;

    public TrendingForm() {
        this(Resources.getGlobalResources());
    }

    public TrendingForm(Resources resourceObjectInstance)  {














        installSidemenu(resourceObjectInstance);

      //  Form hi = new Form("Toolbar", new BoxLayout(BoxLayout.Y_AXIS));
        Button gui_Button_12 = new Button();
        gui_Button_12.setText("search");
        gui_Button_12.setUIID("Label");
        gui_Button_12.setName("Button_12");
        FontImage.setMaterialIcon(gui_Button_12, FontImage.MATERIAL_SEARCH);
        TextField searchField = new TextField("", "Toolbar Search"); // <1>
        searchField.getHintLabel().setUIID("Title");
        searchField.setUIID("Title");
        searchField.getAllStyles().setAlignment(Component.LEFT);
       // hi.getToolbar().setTitleComponent(searchField);
        FontImage searchIcon = FontImage.createMaterial(FontImage.MATERIAL_SEARCH, UIManager.getInstance().getComponentStyle("Restaurants"));
        ArrayList<Restaurant> list1;
        list1 = ServiceRestaurant.getInstance().affichageRestaurant();
        //hi.add(gui_Button_12);
       /* searchField.addDataChangeListener((i1, i2) -> { // <2>
            String t = searchField.getText();

            if(t.length() < 1) {
                for(Component cmp : hi.getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
            } else {
                t = t.toLowerCase();
                for(Component cmp : hi.getContentPane()) {
                    String val = null;
                    //hi.add(gui_Button_12);
                    if(cmp instanceof Label) {
                        val = ((Label)cmp).getText();
                    } else {
                        if(cmp instanceof TextArea) {
                            val = ((TextArea)cmp).getText();
                        } else {
                            val = (String)cmp.getPropertyValue("text");
                        }
                    }
                    boolean show = val != null && val.toLowerCase().indexOf(t) > -1;
                    cmp.setHidden(!show); // <3>
                    cmp.setVisible(show);
                    //hi.add(gui_Button_12);
                }
            }
            hi.getContentPane().animateLayout(250);
            //  hi.add(gui_Button_12);
        });*/
        /*hi.getToolbar().addCommandToRightBar("", searchIcon, (e) -> {
            searchField.startEditingAsync(); // <4>
            //    hi.add(gui_Button_12);
        });*/

       /* for(Restaurant rec : list1){
            Label b = new Label(rec.getNom());
            hi.add(b);
            b.addPointerPressedListener(e -> {

                if (rec.getNom() == b.getText())
                {
                    new  ListRForm(rec.getId(),resourceObjectInstance).show();
                }
            });
        }*/



//SEARCH MINE
        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_SEARCH, e -> {
            new MapForm();
        });
        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_SHOPPING_CART, e -> {
            new ListeCommandesById(resourceObjectInstance).show();
        });
        /*getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_SEARCH, e -> {
             hi.show();
        });*/


        if (SessionManager.isActif()==false){


            getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ACCOUNT_CIRCLE, e -> {
                new SignInForm().show();

            });
        }


        setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        if (SessionManager.isActif()) {
            try {
                imgv1 = new ImageViewer(Image.createImage("/load.png"));

            } catch (IOException e) {
                Dialog.show("error",e.getMessage(),"ok",null);
            }
            try {
                placeholder1 = EncodedImage.create("/load.png");

            } catch (IOException e) {
                Dialog.show("error",e.getMessage(),"ok",null);
            }


            String url= Statics.URL_REP_IMAGES + SessionManager.getNomImage() ;
            String url2="C:/wamp64/www/WeDev_Zvenn/public/images/"+SessionManager.getNomImage();


            URLImage background = URLImage.createToStorage(placeholder1,url,url,URLImage.RESIZE_SCALE);
            imgv1.setImage(background);

            Label label1 = new Label(background);

            int w = background.getWidth();
            int h = background.getHeight();


            Image maskImage = Image.createImage(w, h);
            Graphics g = maskImage.getGraphics();
            g.setAntiAliased(true);
            g.setColor(0x000000);
            g.fillRect(0, 0, w, h);
            g.setColor(0xffffff);
            g.fillArc(0, 0, w, h, 0, 360);
            Label label2 = new Label(maskImage);

            Object mask = maskImage.createMask();
          //  maskImage.scaledWidth(1000);
            Image maskedImage = background.applyMask(mask);
            Label label3 = new Label(maskedImage);





            getToolbar().addCommandToRightBar("",maskedImage.scaledHeight(100) , e -> {
                new ModifierProfilForm().show();

            });
        }

        ArrayList<Restaurant> list = ServiceRestaurant.getInstance().affichageRestaurant();
        for (Restaurant rec : list) {

            Container gui_Container_1 = new Container(new BorderLayout());
            MultiButton gui_Multi_Button_1 = new MultiButton();
            MultiButton gui_LA = new MultiButton();
            Container gui_imageContainer1 = new Container(new BorderLayout());
            Container gui_Container_2 = new Container(new BorderLayout());
            TextArea gui_Text_Area_1 = new TextArea();
            Button gui_Button_1 = new Button();
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
//a changer tansech!!!!
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
            URLImage background = URLImage.createToStorage(placeholder, "C:/wamp64/www/WeDev_Zvenn/public/uploads/"+rec.getNomImage() ,
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
            if (rec.getStatus().equals("verified")) {
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
                gui_Container_2.setName("Container_2");
                gui_Container_2.addComponent(BorderLayout.CENTER, gui_Text_Area_1);
                gui_Container_2.addComponent(BorderLayout.EAST, gui_Button_1);
                gui_Text_Area_1.setText("" + rec.getDescription());
                gui_Text_Area_1.setUIID("SlightlySmallerFontLabelLeft");
                gui_Text_Area_1.setName("Text_Area_1");
                gui_Button_1.setText("");
                gui_Button_1.setUIID("Label");
                gui_Button_1.setName("Button_1");
                FontImage.setMaterialIcon(gui_Button_1, "".charAt(0));


                gui_Button_1.addPointerPressedListener(l -> {
                    //ServiceRestaurant.getInstance().affichageReclamations(rec.getId());
                    //ArrayList<Reclamation>  list = ServiceRestaurant.getInstance().affichageReclamations(2);
                    new ListRForm(rec.getId(), resourceObjectInstance).show();

                });
            }


        }
//RECHERCHE

        //END RECHERCHE
    }


//-- DON'T EDIT BELOW THIS LINE!!!
// <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    // </editor-fold>
//-- DON'T EDIT ABOVE THIS LINE!!!
    @Override
    protected boolean isCurrentTrending() {
        return true;
    }

}
