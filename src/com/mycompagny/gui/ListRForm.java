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
import com.codename1.components.MultiButton;
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
import com.mycompagny.services.ServiceRestaurant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Simple form for the stats, hand coded
 *
 * @author Shai Almog
 */
public class ListRForm extends BaseForm {

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
            gui_Container_2_1.addComponent(gui_Label_1_1);
            gui_Label_1_1.setText("" + rec.getPrix());
            gui_Label_1_1.setUIID("SmallFontLabel");
            gui_Label_1_1.setName("Label_1_1");
            gui_Container_1_1.addComponent(com.codename1.ui.layouts.BorderLayout.WEST, gui_Container_4_1);
            gui_Container_4_1.setName("Container_4_1");
            ((com.codename1.ui.layouts.FlowLayout) gui_Container_4_1.getLayout()).setAlign(com.codename1.ui.Component.CENTER);
            gui_Container_4_1.addComponent(gui_Label_4_1);
            gui_Label_4_1.setUIID("Padding2");
            gui_Label_4_1.setName("Label_4_1");
            gui_Label_4_1.setIcon(res.getImage("label_round-selected.png"));
            gui_Container_1_1.addComponent(com.codename1.ui.layouts.BorderLayout.CENTER, gui_Container_3_1);
            gui_Container_3_1.setName("Container_3_1");
            gui_Container_3_1.addComponent(gui_Label_3_1);
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
                    gui_Label_1_1.setText("" + rec.getDate());
                    gui_Label_1_1.setUIID("SmallFontLabel");
                    gui_Label_1_1.setName("Label_1_1");
                    gui_Container_1_1.addComponent(com.codename1.ui.layouts.BorderLayout.WEST, gui_Container_4_1);
                    gui_Container_4_1.setName("Container_4_1");
                    ((com.codename1.ui.layouts.FlowLayout) gui_Container_4_1.getLayout()).setAlign(com.codename1.ui.Component.CENTER);
                    gui_Container_4_1.addComponent(gui_Label_4_1);
                    gui_Label_4_1.setUIID("Padding2");
                    gui_Label_4_1.setName("Label_4_1");
                    gui_Label_4_1.setIcon(res.getImage("label_round-selected.png"));
                    gui_Container_1_1.addComponent(com.codename1.ui.layouts.BorderLayout.CENTER, gui_Container_3_1);
                    gui_Container_3_1.setName("Container_3_1");
                    gui_Container_3_1.addComponent(gui_Label_3_1);
                    gui_Container_3_1.addComponent(gui_Label_2_1);
                    gui_Container_3_1.addComponent(gui_Text_Area_1_1);
                    gui_Label_3_1.setText("esm el user");
                    gui_Label_3_1.setName("Label_3_1");
                    gui_Label_2_1.setText("" + rec.getTitre());
                    gui_Label_2_1.setUIID("RedLabel");
                    gui_Label_2_1.setName("Label_2_1");
                    gui_Text_Area_1_1.setText("" + rec.getDescription());
                    gui_Text_Area_1_1.setUIID("SmallFontLabel");
                    gui_Text_Area_1_1.setName("Text_Area_1_1");
                    gui_Container_2_1.setName("Container_2_1");
                    gui_Container_4_1.setName("Container_4_1");
                    refreshTheme();

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
                    gui_Container_2_1.addComponent(gui_Label_1_1);
                    gui_Label_1_1.setText("" + rec.getPrix());
                    gui_Label_1_1.setUIID("SmallFontLabel");
                    gui_Label_1_1.setName("Label_1_1");
                    gui_Container_1_1.addComponent(com.codename1.ui.layouts.BorderLayout.WEST, gui_Container_4_1);
                    gui_Container_4_1.setName("Container_4_1");
                    ((com.codename1.ui.layouts.FlowLayout) gui_Container_4_1.getLayout()).setAlign(com.codename1.ui.Component.CENTER);
                    gui_Container_4_1.addComponent(gui_Label_4_1);
                    gui_Label_4_1.setUIID("Padding2");
                    gui_Label_4_1.setName("Label_4_1");
                    gui_Label_4_1.setIcon(res.getImage("label_round-selected.png"));
                    gui_Container_1_1.addComponent(com.codename1.ui.layouts.BorderLayout.CENTER, gui_Container_3_1);
                    gui_Container_3_1.setName("Container_3_1");
                    gui_Container_3_1.addComponent(gui_Label_3_1);
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