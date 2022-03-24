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
import com.mycompagny.entities.Restaurant;
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
public class StatsForm extends BaseForm {
    public StatsForm(Resources res) {
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

        add(BorderLayout.NORTH, createTopGrid(res));
        add(BorderLayout.SOUTH, createBottomList(res));
        add(BorderLayout.CENTER, circleContent(res));
    }

    Container circleContent(Resources res) {
        ArrayList<Restaurant> list = ServiceRestaurant.getInstance().affichageRestaurant();
        Object[][] rows = new Object[list.size()][];
        for (Restaurant rec : list){

        }
        //Restaurant rec = new Restaurant();
        for(int iter = 0 ; iter < rows.length ; iter++) {
            rows[iter] = new Object[] {
                    list.get(iter).getId(),list.get(iter).getNom(), list.get(iter).getDescription(), list.get(iter).getStatus(), list.get(iter).getAddresse(), list.get(iter).getGouvernorat()/*,list.get(iter).getIdUser()*/

            };
        }

        TableModel model = new DefaultTableModel(new String[]{"id","nom Restaurant", "description", "status", "addresse", "gouvernorat"/*,"vendeur"*/}, rows);


        Table table = new Table(model){

            @Override
            protected Component createCell(Object value, int rows, int column, boolean editable) {
                Component cell;
                if(column == 3) {
                    Picker p = new Picker();
                    p.setType(Display.PICKER_TYPE_STRINGS);
                    p.setStrings("verified", "not verified");
                    p.setSelectedString((String)value);
                    p.setUIID("TableCell");
                    p.addActionListener((e) ->
                    {
                        getModel().setValueAt(rows, column, p.getSelectedString());

                        ServiceRestaurant.getInstance().modificationStatus((Integer) getModel().getValueAt(getSelectedRow(),0),p.getSelectedString());
                        new StatsForm(res).show();
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

    Component createBottomList(Resources res) {
        final int DAY = 24 * 60 * 60000;
        List<Date> dayPicker = new List(new ListModel<Date>() {
            int selection = (int)(System.currentTimeMillis() / DAY);
            @Override
            public Date getItemAt(int index) {
                return new Date(index * DAY);
            }

            @Override
            public int getSize() {
                return 50000;
            }

            @Override
            public int getSelectedIndex() {
                return selection;
            }

            @Override
            public void setSelectedIndex(int index) {
                selection = index;
            }

            @Override
            public void addDataChangedListener(DataChangedListener l) {
            }

            @Override
            public void removeDataChangedListener(DataChangedListener l) {
            }

            @Override
            public void addSelectionListener(SelectionListener l) {
            }

            @Override
            public void removeSelectionListener(SelectionListener l) {
            }

            @Override
            public void addItem(Date item) {
            }

            @Override
            public void removeItem(int index) {
            }
        });
        final String[] WEEKDAYS = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
        dayPicker.setOrientation(List.HORIZONTAL);
        dayPicker.setFixedSelection(List.FIXED_CENTER);
        dayPicker.setRenderingPrototype(new Date());
        dayPicker.setRenderer(new ListCellRenderer() {
            Label focus = new Label();
            Label day;
            Label label;
            Container cnt;
            {
                day = new Label("00", "LargeWhileLabel");
                label = new Label("WED", "StatsLabel");
                cnt =
                        BoxLayout.encloseY(
                                FlowLayout.encloseCenter(day),
                                FlowLayout.encloseCenter(label)
                        );
                cnt.setCellRenderer(true);
            }

            @Override
            public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected) {
                Date d = (Date)value;
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                int dd = c.get(Calendar.DAY_OF_MONTH);
                if(dd < 10) {
                    day.setText("0" + dd);
                } else {
                    day.setText("" + dd);
                }
                label.setText(WEEKDAYS[c.get(Calendar.DAY_OF_WEEK) - 1]);
                return cnt;
            }

            @Override
            public Component getListFocusComponent(List list) {
                return focus;
            }
        });
        return BoxLayout.encloseY(
                new Label(res.getImage("welcome-separator.png"), "CenterNoPadd"),
                dayPicker);
    }

    @Override
    protected boolean isCurrentStats() {
        return true;
    }





}
