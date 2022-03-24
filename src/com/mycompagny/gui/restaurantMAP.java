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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * GUI builder created Form
 *
 * @author shai
 */
public class restaurantMAP extends BaseForm {
    EncodedImage placeholder ;
    ImageViewer imgv ;



    public restaurantMAP(Resources resourceObjectInstance,String c)  {














        installSidemenu(resourceObjectInstance);
        if (SessionManager.isActif()==false){

            getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ACCOUNT_CIRCLE, e -> {
                new SignInForm().show();

            });
        }


        setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        if (SessionManager.isActif()) {

            Image placeholder = Image.createImage(getWidth()/100, getHeight()/100);

            EncodedImage encImage = EncodedImage.createFromImage(placeholder, false);

            // EncodedImage placeholder =EncodedImage.createFromImage(Image.createImage(200, 200, 0xffffffff), false);

            URLImage background = URLImage.createToStorage(encImage, "C:/wamp64/www/WeDev_Zvenn/public/images/"+SessionManager.getNomImage() ,
                    Statics.URL_REP_IMAGES+SessionManager.getNomImage());

            background.fetch();


            getToolbar().addCommandToRightBar("", background, e -> {
            });
        }

        ArrayList<Restaurant> list = ServiceRestaurant.getInstance().affichageRestaurantmap(c);
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
            // EncodedImage placeholder =EncodedImage.createFromImage(Image.createImage((Math.round(Display.getInstance().getDisplayWidth())), (Math.round(Display.getInstance().getDisplayWidth()))/3, 0xffff0000), true);
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
            //  URLImage background = URLImage.createToStorage(placeholder, "C:/wamp64/www/WeDev_Zvenn/public/images/"+rec.getNomImage() ,
            //   Statics.URL_REP_IMAGES+rec.getNomImage());
            String url= Statics.URL_REP_IMAGES + rec.getNomImage();

            // background.fetch();
            URLImage background = URLImage.createToStorage(placeholder,url,url,URLImage.RESIZE_SCALE);

            // ScaleImageLabel sl = new ScaleImageLabel(background);
            imgv.setImage(background);
            // sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
            gui_imageContainer1.add(BorderLayout.CENTER, imgv);
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
    protected boolean isCurrentTrending() {
        return true;
    }

}
