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

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.views.PieChart;
import com.codename1.components.SpanLabel;
import com.codename1.messaging.Message;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.events.SelectionListener;
import com.codename1.ui.layouts.*;
import com.codename1.ui.list.ListCellRenderer;
import com.codename1.ui.list.ListModel;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.table.DefaultTableModel;
import com.codename1.ui.table.Table;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.table.TableModel;
import com.codename1.ui.util.Resources;
import com.mycompagny.entities.Reclamation;
import com.mycompagny.entities.Restaurant;
import com.mycompagny.services.ServiceReclamation;
import com.mycompagny.services.ServiceRestaurant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Simple form for the stats, hand coded
 *
 * @author Shai Almog
 */
public class reclamationDashboardForm extends BaseForm {
    public reclamationDashboardForm(Resources res) {

        setLayout(new BorderLayout());
        setUIID("StatsForm");
        installSidemenu(res);

        getToolbar().addCommandToRightBar("", res.getImage("toolbar-profile-pic.png"), e -> {});

        Button toggle = new Button("");
        toggle.setUIID("CenterWhite");
        FontImage.setMaterialIcon(toggle, FontImage.MATERIAL_ACCESS_TIME);
        toggle.getAllStyles().setMargin(0, 0, 0, 0);
        toggle.getAllStyles().setBorder(RoundBorder.create().
                rectangle(true).
                color(0x9b4c3f));
        Button placeholder = new Button("");
        placeholder.setUIID("CenterWhite");
        Container buttonGrid = GridLayout.encloseIn(2, toggle, placeholder);
        Label leftLabel = new Label("", "CenterWhite");
        FontImage.setMaterialIcon(leftLabel, FontImage.MATERIAL_ACCESS_TIME);
        Label rightLabel = new Label("", "CenterWhite");
        FontImage.setMaterialIcon(rightLabel, FontImage.MATERIAL_DIRECTIONS_RUN);
        Container labelGrid = GridLayout.encloseIn(2, leftLabel, rightLabel);
        labelGrid.getAllStyles().setBorder(RoundBorder.create().
                rectangle(true).
                color(0xd27d61));

        getToolbar().setTitleComponent(
                FlowLayout.encloseCenterMiddle(
                        LayeredLayout.encloseIn(labelGrid, buttonGrid)
                )
        );

        ActionListener al = e -> {
            if(buttonGrid.getComponentAt(0) == toggle) {
                toggle.remove();
                buttonGrid.add(toggle);
                buttonGrid.animateLayoutAndWait(150);
                FontImage.setMaterialIcon(toggle, FontImage.MATERIAL_DIRECTIONS_RUN);
            } else {
                placeholder.remove();
                buttonGrid.add(placeholder);
                buttonGrid.animateLayoutAndWait(150);
                FontImage.setMaterialIcon(toggle, FontImage.MATERIAL_ACCESS_TIME);
            }
        };
        toggle.addActionListener(al);
        placeholder.addActionListener(al);

        add(BorderLayout.NORTH, circleContent(res));
        add(BorderLayout.SOUTH, createBottomList(res));
        add(BorderLayout.CENTER, piechart(res));



    }

    Container circleContent(Resources res) {
        ArrayList<Reclamation> list = ServiceReclamation.getInstance().affichageReclamation();
        Object[][] rows = new Object[list.size()][];
        for (Reclamation rec : list){

        }
        //Restaurant rec = new Restaurant();
        for(int iter = 0 ; iter < rows.length ; iter++) {
            rows[iter] = new Object[] {
                    list.get(iter).getId(), list.get(iter).getTitre(), list.get(iter).getDescription(), list.get(iter).getEtat(),     list.get(iter).getUser().substring( list.get(iter).getUser().lastIndexOf("email=")+6, list.get(iter).getUser().lastIndexOf("prenom")-2)


            };
        }

        TableModel model = new DefaultTableModel(new String[]{"id","Titre", "description", "etat", "email"}, rows);


        Table table = new Table(model){

            @Override
            protected Component createCell(Object value, int rows, int column, boolean editable) {
                Component cell;








                //app

                if(column == 3) {
                    Picker p = new Picker();
                    p.setType(Display.PICKER_TYPE_STRINGS);
                    p.setStrings("traite", "non traite");
                    p.setSelectedString((String)value);
                    p.setUIID("TableCell");
                    p.addActionListener((e) ->
                    {
                        getModel().setValueAt(rows, column, p.getSelectedString());

                        ServiceReclamation.getInstance().modifierstatus((Integer) getModel().getValueAt(getSelectedRow(),0),p.getSelectedString());
                        String valeure="";
                        String titre="";
                        if (p.getSelectedString().equals("traite")) {
                            valeure = " traite";
                            titre = "votre reclamation est traité";

                            Message m = new Message("Votre reclamation " + getModel().getValueAt(getSelectedRow(), 1) + " est " + valeure + "\"");
                            String textAttachmentUri = "zvenn";
                            m.getAttachments().put(textAttachmentUri, "text/plain");
                            Display.getInstance().sendMessage(new String[]{(String) getModel().getValueAt(getSelectedRow(), 4)}, titre, m);

                          new reclamationDashboardForm(res).show();
                        }else {
                            new reclamationDashboardForm(res).show();
                        }
                    });
                    //   p.addActionListener((e) -> list.get(rows).setStatus("verified"));




                    cell = p;
                } else {
                    cell = super.createCell(value, rows, column, editable);
                }
                if(rows == -1) {
                    // pinstripe effect
                    cell.getAllStyles().setBgColor(0xeeeeee);
                    cell.getAllStyles().setBgTransparency(255);
                }

                return cell;

            }

            @Override
            protected TableLayout.Constraint createCellConstraint(Object value, int row, int column) {
                TableLayout.Constraint con =  super.createCellConstraint(value, row, column);
                if(row == rows.length ) {
                    con.setVerticalSpan(2);
                }
                return con;
            }
        };
        refreshTheme();


        table.setScrollableX(true);
        return table;

    }


    Container gridElement(Resources res, String nb, String label, boolean last) {
        Container c = BorderLayout.centerAbsolute(
                BoxLayout.encloseY(
                        FlowLayout.encloseCenter(new Label(nb, "LargeWhileLabel"),
                                new Label("", "SmallWhileLabel")
                        ),
                        new Label(res.getImage("welcome-separator.png"), "CenterNoPadd"),
                        new Label(label, "StatsLabel")
                )
        );
        if(last) {
            c.getAllStyles().setBorder(Border.createCompoundBorder(null,
                    Border.createLineBorder(2, 0x5b636b), null, null));
        } else {
            c.getAllStyles().setBorder(Border.createCompoundBorder(null,
                    Border.createLineBorder(2, 0x5b636b), null,
                    Border.createLineBorder(2, 0x5b636b)));
        }
        return c;
    }

    Container createTopGrid(Resources res) {
        int s = 0;
        int v=0;
        int x=0;
        String status="verified";
        String status2="not verified";

        ArrayList<Restaurant> list = ServiceRestaurant.getInstance().affichageRestaurant();
        for (Restaurant rec : list) {
            s=s+1;
            if (rec.getStatus().equals(status)) {
                v=v+1;
            }
            else if (rec.getStatus().equals(status2)){
                x=x+1;
            }



        }
        return GridLayout.encloseIn(3,
                gridElement(res, String.valueOf(s), "Restaurants", false),
                gridElement(res, String.valueOf(v), "Restaurants verifié", false),
                gridElement(res, String.valueOf(x), "Restaurants non verifié", true));
    }
    Container gridElement2(Resources res, String nb, Label labeL, boolean last) {
        Container c = BorderLayout.centerAbsolute(
                BoxLayout.encloseY(
                        FlowLayout.encloseCenter(new Label(nb, "LargeWhileLabel"),
                                new Label("", "SmallWhileLabel")
                        ),
                        labeL,
                        new Label(res.getImage("welcome-separator.png"), "CenterNoPadd")

                )
        );
        if(last) {
            c.getAllStyles().setBorder(Border.createCompoundBorder(null,
                    Border.createLineBorder(2, 0x5b636b), null, null));
        } else {
            c.getAllStyles().setBorder(Border.createCompoundBorder(null,
                    Border.createLineBorder(2, 0x5b636b), null,
                    Border.createLineBorder(2, 0x5b636b)));
        }
        return c;
    }


    Component createBottomList(Resources res) {
        Label reclamation = new Label("button");
        reclamation.addPointerPressedListener(e -> {
            new reclamationDashboardForm(res).show();


        });
        Label resturant = new Label("button");
        resturant.addPointerPressedListener(e -> {
            new StatsForm(res).show();


        });
        Label btn3 = new Label("button");
       /* btn.addPointerPressedListener(e -> {
            new TrendingForm(res).show();


        });*/
        Label btn4 = new Label("button");
      /*  btn.addPointerPressedListener(e -> {
            new TrendingForm(res).show();


        });*/
        Label btn5 = new Label("button");
       /* btn.addPointerPressedListener(e -> {
            new TrendingForm(res).show();


        });*/
        setScrollableX(true);

        return GridLayout.encloseIn(3,
                gridElement2(res, "", resturant, true),
                gridElement2(res, "", reclamation, true),
                gridElement2(res, "", btn3, true)

        );


    }
    public DefaultRenderer buildCatRendrer(int []colors) {

        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setMargins(new int[] {20, 30, 15, 0});

        for(int color : colors) {
            SimpleSeriesRenderer simpleSeriesRenderer = new SimpleSeriesRenderer();

            simpleSeriesRenderer.setColor(color);
            renderer.addSeriesRenderer(simpleSeriesRenderer);
        }
        return renderer;
    }

    Component piechart(Resources res) {
        //chna3ml stat feedback par rapport l reclamation
        ArrayList<Reclamation> list = ServiceReclamation.getInstance().affichageReclamation();
        double nbr_feedback = 0;
        double nbr_reclamation = 0;
        for (Reclamation rec : list){
            if(rec.getEtat().equals("traite")){
                nbr_feedback = nbr_feedback + 1;
            }else{
                nbr_reclamation = nbr_reclamation + 1 ;
            }

        }
        double total = nbr_feedback + nbr_reclamation;

        //values
        double prcntFeed = (nbr_feedback *100)/total;

        double prcntRec = (nbr_reclamation * 100)/total;

        //colors set:
        int[]colors = new int[]{0xf4b342, 0x52b29a};

        DefaultRenderer renderer = buildCatRendrer(colors);
        renderer.setLabelsColor(0x000000); // black color for labels.

        renderer.setZoomButtonsVisible(true);//zoom
        renderer.setLabelsTextSize(40);
        renderer.setZoomEnabled(true);
        renderer.setChartTitleTextSize(20);
        renderer.setDisplayValues(true);
        renderer.setShowLabels(true);
        SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
        r.setHighlighted(true);

        //CREATe the chart ...
        PieChart chart = new PieChart(buildDataset("title",Math.round(prcntFeed),Math.round(prcntRec)), renderer);

        // n7oto chart fi component
        ChartComponent c  = new ChartComponent(chart);

        return c;
    }

    private CategorySeries buildDataset(String title, double prcntFeed, double prcntRec) {

        CategorySeries series = new CategorySeries(title);

        series.add("non traité",prcntRec);
        series.add("traité",prcntFeed);

        return series;
    }

    @Override
    protected boolean isCurrentStats() {
        return true;
    }





}
