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

//import com.codename1.progress.ArcProgress;
//import com.codename1.progress.CircleProgress;
import com.codename1.components.*;
import com.codename1.io.Preferences;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.events.SelectionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.list.ListCellRenderer;
import com.codename1.ui.list.ListModel;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompagny.entities.Produits;
import com.mycompagny.entities.Reclamation;
import com.mycompagny.entities.Reply;
import com.mycompagny.services.ServiceReclamation;
import com.mycompagny.services.ServiceReply;
import com.mycompagny.services.ServiceRestaurant;
import com.mycompagny.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Simple form for the stats, hand coded
 *
 * @author Shai Almog
 */
public class ListRForm extends BaseForm {
    EncodedImage placeholder ;
    ImageViewer imgv ;
    EncodedImage placeholder1 ;
    ImageViewer imgv1 ;
    Image imgs;
    public static Preferences pref ;
    float total=0;
    public ListRForm() {
    }

    @Override
    protected boolean isCurrentTrending() {
        return true;
    }

    public ListRForm(int id, com.codename1.ui.util.Resources res) {
        installSidemenu(res);
         ArrayList<Reclamation> list = ServiceRestaurant.getInstance().affichageReclamations(id);
         ArrayList<Produits> listP = ServiceRestaurant.getInstance().affichageProds(id);




        getToolbar().addCommandToRightBar("", res.getImage("toolbar-profile-pic.png"), e -> {
        });
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));


        Button toggle = new Button("");
        toggle.setUIID("CenterWhite");
        FontImage.setMaterialIcon(toggle, FontImage.MATERIAL_SHOPPING_CART);
        toggle.getAllStyles().setMargin(0, 0, 0, 0);
        toggle.getAllStyles().setBorder(RoundBorder.create().
                rectangle(true).
                color(0x9b4c3f));
        Button placeholder = new Button("");
        placeholder.setUIID("CenterWhite");
        Container buttonGrid = GridLayout.encloseIn(2, toggle, placeholder);
        Label leftLabel = new Label("", "CenterWhite");
        FontImage.setMaterialIcon(leftLabel, FontImage.MATERIAL_SHOPPING_CART);
        Label rightLabel = new Label("", "CenterWhite");
        FontImage.setMaterialIcon(rightLabel, FontImage.MATERIAL_MAIL);
        Container labelGrid = GridLayout.encloseIn(2, leftLabel, rightLabel);
        labelGrid.getAllStyles().setBorder(RoundBorder.create().
                rectangle(true).
                color(0xd27d61));

        getToolbar().setTitleComponent(
                FlowLayout.encloseCenterMiddle(
                        LayeredLayout.encloseIn(labelGrid, buttonGrid)
                )
        );
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        RoundBorder rb = (RoundBorder) fab.getUnselectedStyle().getBorder();
        rb.uiid(true);
        fab.bindFabToContainer(getContentPane());
        fab.addActionListener(e -> {
            if (buttonGrid.getComponentAt(1) == toggle) {

                new AjouterReclamationForm(id,res).show();




                }


        });
        for (Produits rec : listP) {


            Container gui_Container_1_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
            Container gui_Container_2_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
            Label gui_Label_1_1 = new com.codename1.ui.Label();
            Container gui_Container_4_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
            Label gui_Label_4_1 = new com.codename1.ui.Label();
            Container gui_Container_3_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
            Label gui_Label_3_1 = new com.codename1.ui.Label();
            Label gui_Label_2_1 = new com.codename1.ui.Label();
            TextArea gui_Text_Area_1_1 = new com.codename1.ui.TextArea();
            Button shop = new Button(FontImage.MATERIAL_SHOPPING_CART);
            shop.setUIID("Label");



            gui_Text_Area_1_1.setRows(2);
            gui_Text_Area_1_1.setColumns(100);
            gui_Text_Area_1_1.setEditable(false);

            addComponent(gui_Container_1_1);
            gui_Container_1_1.setName("Container_1_1");
            gui_Container_1_1.addComponent(com.codename1.ui.layouts.BorderLayout.EAST, gui_Container_2_1);
            gui_Container_2_1.setName("Container_2_1");
            gui_Container_2_1.addComponent(gui_Label_1_1);

            gui_Label_1_1.setText("" + rec.getPrix()+" dt");
            gui_Label_1_1.setUIID("SmallFontLabel");
            gui_Label_1_1.setName("Label_1_1");
            gui_Container_1_1.addComponent(com.codename1.ui.layouts.BorderLayout.WEST, gui_Container_4_1);
            gui_Container_4_1.setName("Container_4_1");
            ((com.codename1.ui.layouts.FlowLayout) gui_Container_4_1.getLayout()).setAlign(com.codename1.ui.Component.CENTER);
            gui_Container_4_1.addComponent(gui_Label_4_1);
            gui_Label_4_1.setUIID("Padding2");
            gui_Label_4_1.setName("Label_4_1");

            try {
                imgv1 = new ImageViewer(Image.createImage("/load.png"));

            } catch (IOException exception) {
                Dialog.show("error",exception.getMessage(),"ok",null);
            }
            try {
                placeholder1 = EncodedImage.create("/load.png");

            } catch (IOException exception) {
                Dialog.show("error",exception.getMessage(),"ok",null);
            }

            String url= Statics.URL_REP_IMAGES + rec.getNomImage() +".jpg";
            String url2="C:/wamp64/www/WeDev_Zvenn/public/images/"+SessionManager.getNomImage();
            //EncodedImage placeholder1 =EncodedImage.createFromImage(Image.createImage((Math.round(Display.getInstance().getDisplayWidth()))/10, (Math.round(Display.getInstance().getDisplayWidth()))/10, 0xffff0000), true);


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
            Image maskedImage = background;
            Label label3 = new Label(maskedImage);
            // Image sl = Image.createImage(background);
            // imgv.setImage(background);
            gui_Label_4_1.setIcon(maskedImage);

            // gui_Label_4_1.setIcon(res.getImage("label_round-selected.png"));
            gui_Container_1_1.addComponent(com.codename1.ui.layouts.BorderLayout.CENTER, gui_Container_3_1);
            gui_Container_3_1.setName("Container_3_1");
            gui_Container_3_1.addComponent(gui_Label_3_1);
            gui_Container_3_1.addComponent(shop);

            gui_Container_3_1.addComponent(gui_Label_2_1);
            gui_Container_3_1.addComponent(gui_Text_Area_1_1);
            gui_Label_3_1.setText(""+ rec.getNom());

            gui_Label_3_1.setName("Label_3_1");
            gui_Label_2_1.setText("" + rec.getDescriptionProduit());

            gui_Label_2_1.setUIID("RedLabel");
            gui_Label_2_1.setName("Label_2_1");
            //gui_Text_Area_1_1.setText("" + rec.getDescriptionProduit());
            gui_Text_Area_1_1.setUIID("SmallFontLabel");
            gui_Text_Area_1_1.setName("Text_Area_1_1");
            gui_Container_2_1.setName("Container_2_1");
            gui_Container_4_1.setName("Container_4_1");
            refreshTheme();
            shop.addActionListener(e -> {
              //  rec.getPrix();


               //   SessionManager.setPrix(rec.getPrix());


                total+=rec.getPrix();

                SessionManager.ajouter(rec.getNom());
               //SessionManager.setNom(rec.getNom());
                SessionManager.setPrix(total);
                for(String prods : SessionManager.getAr()) {
                    System.out.println(prods);
                }

                System.out.println(total);
             //   System.out.println(rec.getNom());







            });

        }
        ActionListener al = e -> {
            if (buttonGrid.getComponentAt(0) == toggle) {

                removeAll();
                toggle.remove();
                buttonGrid.add(toggle);
                buttonGrid.animateLayoutAndWait(150);
                FontImage.setMaterialIcon(toggle, FontImage.MATERIAL_MAIL);
                refreshTheme();


                for (Reclamation rec : list) {

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
                    Label gui_Label_1 = new Label("");
                    Label gui_Label_2 = new Label("");
                    Label gui_Label_3 = new Label();

                    gui_separator1.setShowEvenIfBlank(true);
                    gui_Label_1_1_1.setShowEvenIfBlank(true);

                    EncodedImage placeholderr = EncodedImage.createFromImage(Image.createImage((Math.round(Display.getInstance().getDisplayWidth())), (Math.round(Display.getInstance().getDisplayWidth())) / 3, 0xffff0000), true);

                    URLImage background = URLImage.createToStorage(placeholderr, Statics.URL_REP_IMAGES + rec.getNomimage(),
                            Statics.URL_REP_IMAGES + rec.getNomimage());
                    ;
                    background.fetch();

                    ScaleImageLabel sl = new ScaleImageLabel(background);
                    // sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
                    gui_imageContainer1.add(BorderLayout.CENTER, sl);
                    sl = new ScaleImageLabel(res.getImage("bridge.jpg"));
                    sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
                    gui_imageContainer2.add(BorderLayout.CENTER, sl);

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
                    gui_Multi_Button_1.setPropertyValue("line1", "" + rec.getTitre());
                    gui_Multi_Button_1.setPropertyValue("line2", "" + rec.getDescription());
                    gui_Multi_Button_1.setPropertyValue("uiid1", "Label");
                    gui_Multi_Button_1.setPropertyValue("uiid2", "RedLabel");
                    gui_LA.setUIID("Label");
                    gui_LA.setName("xxx");



                    try {
                        imgv1 = new ImageViewer(Image.createImage("/load.png"));

                    } catch (IOException exception) {
                        Dialog.show("error",exception.getMessage(),"ok",null);
                    }
                    try {
                        placeholder1 = EncodedImage.create("/load.png");

                    } catch (IOException exception) {
                        Dialog.show("error",exception.getMessage(),"ok",null);
                    }
                    String NomImage = rec.getUser().substring(rec.getUser().lastIndexOf("nomImage=")+9, rec.getUser().lastIndexOf("dateCreation")-2);
                    String url= Statics.URL_REP_IMAGES + NomImage ;
                    String url2="C:/wamp64/www/WeDev_Zvenn/public/uploads/"+NomImage;


                    URLImage background2 = URLImage.createToStorage(placeholder1,url,url,URLImage.RESIZE_SCALE);
                    imgv1.setImage(background2);

                    Label label1 = new Label(background2);

                    int w = background2.getWidth();
                    int h = background2.getHeight();

                    Image maskImage = Image.createImage(w, h);
                    Graphics g = maskImage.getGraphics();
                    g.setAntiAliased(true);
                    g.setColor(0x000000);
                    g.fillRect(0, 0, w, h);
                    g.setColor(0xffffff);
                    g.fillArc(0, 0, w, h, 0, 360);

                    Object mask = maskImage.createMask();
                    // maskImage.scaledWidth(1000);
                    Image maskedImage = background2.applyMask(mask);

                    gui_LA.setIcon(maskedImage.scaled(100,100));
                    String pseudo = rec.getUser().substring(rec.getUser().lastIndexOf("pseudo=")+7, rec.getUser().lastIndexOf("nomImage")-2);
                    String idUser = rec.getUser().substring(rec.getUser().lastIndexOf("id=")+3, rec.getUser().lastIndexOf("email")-4);

                    gui_LA.setPropertyValue("line1", ""+pseudo );
                    gui_LA.setPropertyValue("line2", "" + rec.getEtat());
                    gui_LA.setPropertyValue("uiid1", "SlightlySmallerFontLabel");
                    gui_LA.setPropertyValue("uiid2", "RedLabelRight");
                    addComponent(gui_imageContainer1);
                    gui_imageContainer1.setName("imageContainer1");
                    gui_imageContainer1.addComponent(BorderLayout.SOUTH, gui_Container_2);
                    gui_Container_2.setName("Container_2");

                    String actifuser = String.valueOf(SessionManager.getId());
                    if(actifuser.equals(idUser) && SessionManager.isActif()) {
                        gui_Container_2.addComponent(BorderLayout.CENTER, gui_Label_1);
                        gui_Container_2.addComponent(BorderLayout.WEST, gui_Label_2);
                    }
                        gui_Container_2.addComponent(BorderLayout.EAST, gui_Button_1);

                    Style supprStyle=new Style(gui_Button_1.getUnselectedStyle());
                    supprStyle.setFgColor(0xf21f1f);
                    FontImage supprimerImage= FontImage.createMaterial(FontImage.MATERIAL_DELETE,supprStyle);
                    gui_Label_1.setIcon(supprimerImage);
                    gui_Label_1.setTextPosition(Component.LEFT);
                    Style updateStyle=new Style(gui_Label_2.getUnselectedStyle());
                    updateStyle.setFgColor(0xf7ad02);
                    FontImage updateImage= FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, updateStyle);
                    gui_Label_2.setIcon(updateImage);
                    gui_Label_2.setTextPosition(LEFT);
                    gui_Label_2.setIcon(updateImage);
                    gui_Button_1.setText("");
                    gui_Button_1.setUIID("Label");
                    gui_Button_1.setName("Button_1");
                    FontImage.setMaterialIcon(gui_Button_1, "".charAt(0));
                    ShareButton bt =new ShareButton();
                    add(bt);

                    gui_Button_1.addPointerPressedListener(l -> {
                        if(SessionManager.isActif()) {
                            new AjoutReplyForm(rec.getId(), res).show();
                        }else
                            new SignInForm(res).show();


                    });
                    gui_Label_1.addPointerPressedListener(l -> {

                        Dialog dig =new Dialog("suppression");
                        if(Dialog.show("suppression","voulez-vous supprimer cette reclamation?", "Annuler","Ok"))
                        {
                            dig.dispose();

                        }
                        else
                        {
                            dig.dispose();
                            if(ServiceReclamation.getInstance().deleteReclamation(rec.getId()))
                            {    Dialog.show("succès!!!", "", new Command("OK"));
                                new ListRForm(id,res).show();

                            }

                        }

                    });


                    gui_Label_2.addPointerPressedListener(ee -> {

                        new ModifierReclamationForm(res,rec).show();

                    });
                    ArrayList<Reply> listR = ServiceReply.getInstance().affichageReply(rec.getId());
                    for (Reply rep : listR) {

                        Container gui_Container_1_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
                        Container gui_Container_2_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
                        Label gui_Label_1_1 = new com.codename1.ui.Label();
                        Container gui_Container_4_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
                        Label gui_Label_4_1 = new com.codename1.ui.Label();
                        Container gui_Container_3_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
                        Label gui_Label_3_1 = new com.codename1.ui.Label();
                        Label gui_Label_2_1 = new com.codename1.ui.Label();
                        TextArea gui_Text_Area_1_1 = new com.codename1.ui.TextArea();
                        gui_Text_Area_1_1.setRows(2);
                        gui_Text_Area_1_1.setColumns(100);
                        gui_Text_Area_1_1.setEditable(false);
                        addComponent(gui_Container_1_1);
                        gui_Container_1_1.setName("Container_1_1");
                        gui_Container_1_1.addComponent(com.codename1.ui.layouts.BorderLayout.EAST, gui_Container_2_1);
                        gui_Container_2_1.setName("Container_2_1");
                        gui_Container_2_1.addComponent(gui_Label_1_1);
                        Style suppStyle=new Style(gui_Label_1_1.getUnselectedStyle());
                        supprStyle.setFgColor(0xf21f1f);
                        FontImage suppImage= FontImage.createMaterial(FontImage.MATERIAL_DELETE,suppStyle);
                        gui_Label_1_1.setIcon(suppImage);

                        gui_Label_1_1.setUIID("SmallFontLabel");
                        gui_Label_1_1.setName("Label_1_1");
                        gui_Container_1_1.addComponent(com.codename1.ui.layouts.BorderLayout.WEST, gui_Container_4_1);
                        gui_Container_4_1.setName("Container_4_1");
                        ((com.codename1.ui.layouts.FlowLayout) gui_Container_4_1.getLayout()).setAlign(com.codename1.ui.Component.CENTER);
                        gui_Container_4_1.addComponent(gui_Label_4_1);
                        gui_Label_4_1.setUIID("Padding2");
                        gui_Label_4_1.setName("Label_4_1");
                        gui_Label_4_1.setIcon(res.getImage("toolbar-profile-pic.png"));
                        gui_Container_1_1.addComponent(com.codename1.ui.layouts.BorderLayout.CENTER, gui_Container_3_1);
                        gui_Container_3_1.setName("Container_3_1");
                        gui_Container_3_1.addComponent(gui_Label_3_1);
                        gui_Container_3_1.addComponent(gui_Label_2_1);
                        gui_Container_3_1.addComponent(gui_Text_Area_1_1);
                        gui_Label_3_1.setText("" + rep.getTitre());
                        gui_Label_3_1.setName("Label_3_1");
                        gui_Label_2_1.setText("" + rep.getDescription());
                        gui_Label_2_1.setUIID("RedLabel");
                        gui_Label_2_1.setName("Label_2_1");

                        gui_Text_Area_1_1.setUIID("SmallFontLabel");
                        gui_Text_Area_1_1.setName("Text_Area_1_1");
                        gui_Container_2_1.setName("Container_2_1");
                        gui_Container_4_1.setName("Container_4_1");
                        gui_Label_1_1.addPointerPressedListener(l -> {

                            Dialog dig =new Dialog("suppression");
                            if(Dialog.show("suppression","voulez-vous supprimer cette reclamation?", "Annuler","Ok"))
                            {
                                dig.dispose();
                            }
                            else
                            {
                                dig.dispose();
                                if(ServiceReply.getInstance().deleteR(rep.getId()))
                                {    Dialog.show("succès!!!", "", new Command("OK"));
                                    new ListRForm(id,res).show();
                                    buttonGrid.getComponentAt(1) ;

                                }

                            }

                        });


                    }

                }


            } else {
                removeAll();
                placeholder.remove();
                buttonGrid.add(placeholder);
                buttonGrid.animateLayoutAndWait(150);
                FontImage.setMaterialIcon(toggle, FontImage.MATERIAL_SHOPPING_CART);

                for (Produits rec : listP) {


                    Container gui_Container_1_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
                    Container gui_Container_2_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
                    Label gui_Label_1_1 = new com.codename1.ui.Label();
                    Container gui_Container_4_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
                    Label gui_Label_4_1 = new com.codename1.ui.Label();
                    Container gui_Container_3_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
                    Label gui_Label_3_1 = new com.codename1.ui.Label();
                    Label gui_Label_2_1 = new com.codename1.ui.Label();
                    TextArea gui_Text_Area_1_1 = new com.codename1.ui.TextArea();

                    gui_Text_Area_1_1.setRows(2);
                    gui_Text_Area_1_1.setColumns(100);
                    gui_Text_Area_1_1.setEditable(false);

                    addComponent(gui_Container_1_1);
                    gui_Container_1_1.setName("Container_1_1");
                    gui_Container_1_1.addComponent(com.codename1.ui.layouts.BorderLayout.EAST, gui_Container_2_1);
                    gui_Container_2_1.setName("Container_2_1");

                    gui_Container_1_1.addComponent(com.codename1.ui.layouts.BorderLayout.WEST, gui_Container_4_1);
                    gui_Container_4_1.setName("Container_4_1");
                    ((com.codename1.ui.layouts.FlowLayout) gui_Container_4_1.getLayout()).setAlign(com.codename1.ui.Component.CENTER);
                    gui_Container_4_1.addComponent(gui_Label_4_1);
                    gui_Label_4_1.setUIID("Padding2");
                    gui_Label_4_1.setName("Label_4_1");

                    try {
                        imgv1 = new ImageViewer(Image.createImage("/load.png"));

                    } catch (IOException exception) {
                        Dialog.show("error",exception.getMessage(),"ok",null);
                    }
                    try {
                        placeholder1 = EncodedImage.create("/load.png");

                    } catch (IOException exception) {
                        Dialog.show("error",exception.getMessage(),"ok",null);
                    }

                    String url= Statics.URL_REP_IMAGES + rec.getNomImage() +".jpg";
                    String url2="C:/wamp64/www/WeDev_Zvenn/public/images/"+SessionManager.getNomImage();
                    //EncodedImage placeholder1 =EncodedImage.createFromImage(Image.createImage((Math.round(Display.getInstance().getDisplayWidth()))/10, (Math.round(Display.getInstance().getDisplayWidth()))/10, 0xffff0000), true);


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
                    Image maskedImage = background;
                    Label label3 = new Label(maskedImage);
                  // Image sl = Image.createImage(background);
                    // imgv.setImage(background);
                    gui_Label_4_1.setIcon(maskedImage);

                   // gui_Label_4_1.setIcon(res.getImage("label_round-selected.png"));
                    gui_Container_1_1.addComponent(com.codename1.ui.layouts.BorderLayout.CENTER, gui_Container_3_1);
                    gui_Container_3_1.setName("Container_3_1");
                    gui_Container_3_1.addComponent(gui_Label_3_1);
                    gui_Container_3_1.addComponent(gui_Label_2_1);
                    gui_Container_3_1.addComponent(gui_Text_Area_1_1);
                    gui_Label_3_1.setText(""+ rec.getNom());
                    gui_Label_3_1.setName("Label_3_1");
                    gui_Label_2_1.setText("" + rec.getPrix()+" dt");
                    gui_Container_3_1.addComponent(gui_Label_1_1);
                    gui_Label_1_1.setText("" + rec.getDescriptionProduit());
                  //  gui_Label_1_1.setUIID("Blacklabel");
                    gui_Label_1_1.setName("Label_1_1");
                    gui_Label_2_1.setUIID("RedLabel");
                    gui_Label_2_1.setName("Label_2_1");
                    //gui_Text_Area_1_1.setText("" + rec.getDescriptionProduit());
                    gui_Text_Area_1_1.setUIID("SmallFontLabel");
                    gui_Text_Area_1_1.setName("Text_Area_1_1");
                    gui_Container_2_1.setName("Container_2_1");
                    gui_Container_4_1.setName("Container_4_1");
                  //  refreshTheme();

                }
                getToolbar().addSearchCommand(ee -> {
                    String text = (String)ee.getSource();
                    if(text == null || text.length() == 0) {
                        // clear search
                        for(Produits cmp : listP) {
                            Container cnnt = new Container();
                            cnnt.setHidden(false);
                            cnnt.setVisible(true);
                        }
                        getContentPane().animateLayout(150);
                    } else {
                        text = text.toLowerCase();
                        for(Produits cmp : listP) {
                            Container cnnt = new Container();

                            String line1 = cmp.getDescriptionProduit();
                            String line2 = cmp.getNom();
                            boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1 ||
                                    line2 != null && line2.toLowerCase().indexOf(text) > -1;
                            cnnt.setHidden(!show);
                            cnnt.setVisible(show);
                        }
                        getContentPane().animateLayout(150);
                    }
                }, 4);


            }
        };
        toggle.addActionListener(al);
        placeholder.addActionListener(al);

    }


            // ArcProgress ap = new ArcProgress();
            //ap.setProgress(70);
            //ap.setRenderPercentageOnTop(false);









}