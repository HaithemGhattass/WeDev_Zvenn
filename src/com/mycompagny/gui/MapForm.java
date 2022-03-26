/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompagny.gui;

import com.codename1.components.ToastBar;
import com.codename1.googlemaps.MapContainer;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.maps.Coord;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompagny.entities.Restaurant;
import com.mycompagny.services.ServiceRestaurant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Chadi
 */
public class MapForm {
    Form f = new Form();
    MapContainer cnt = null;

    public MapForm() {

        try{
            cnt = new MapContainer("AIzaSyAYz4FigVGlFUwCHveAFUwOsl1OeXrSzsQ");
           // cnt.zoom(new Coord(20.640086, -103.432207), 17);

        }catch(Exception ex) {
            ex.printStackTrace();
        }

        Button btnMoveCamera = new Button("Restaurants");
        btnMoveCamera.addActionListener(e->{
            cnt.zoom(new Coord(36.8189700, 10.1657900),11);
        });
        Style s = new Style();
        s.setFgColor(0xff0000);
        s.setBgTransparency(0);
        FontImage markerImg = FontImage.createMaterial(FontImage.MATERIAL_PLACE, s, Math.round(Display.getInstance().convertToPixels(1)/2));


        cnt.addMarker(EncodedImage.createFromImage(markerImg, false),
                new Coord(36.725890, 9.187340),
                "beja"+cnt.getCameraPosition().toString(),
                "",
                new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String beja = "beja";
                    new restaurantMAP(Resources.getGlobalResources(),beja).show();

            }
        });
        cnt.addMarker(EncodedImage.createFromImage(markerImg, false),
                new Coord(36.8189700, 10.1657900),
                "ariana"+cnt.getCameraPosition().toString(),
                "",
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        String ariana = "ariana";
                        new restaurantMAP(Resources.getGlobalResources(),ariana).show();

                    }
                });
        ArrayList<Restaurant> list = ServiceRestaurant.getInstance().affichageRestaurant();
        String tunis = "tunis";
        int somme=0;
        for (Restaurant rec : list) {
            if (rec.getGouvernorat().equals(tunis)&&rec.getStatus().equals("verified")) {
                somme = somme + 1;
            }
        }
            cnt.addMarker(EncodedImage.createFromImage(markerImg, false),
                    new Coord(36.800070, 10.187060),
                    "tunis" + "\n en "+tunis+" il y a \n"+somme+" restaurant partenaires avec zvenn",
                    "",
                    new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            new restaurantMAP(Resources.getGlobalResources(), tunis).show();

                        }
                    });


        cnt.addLongPressListener(e->{
            cnt.zoom(new Coord(cnt.getCoordAtPosition(e.getX(), e.getY())), (int) (cnt.getZoom()-1));



            ConnectionRequest r = new ConnectionRequest();
            r.setPost(false);
            r.setUrl("https://maps.google.com/maps/api/geocode/json?latlng="+cnt.getCameraPosition().getLatitude()+","+cnt.getCameraPosition().getLongitude()+"&oe=utf8&sensor=false");
            NetworkManager.getInstance().addToQueueAndWait(r);

        });
        cnt.addTapListener(e->{


            cnt.zoom(new Coord(cnt.getCoordAtPosition(e.getX(), e.getY())), (int) (cnt.getZoom()+1));



            ConnectionRequest r = new ConnectionRequest();
            r.setPost(false);
            r.setUrl("https://maps.google.com/maps/api/geocode/json?latlng="+cnt.getCameraPosition().getLatitude()+","+cnt.getCameraPosition().getLongitude()+"&oe=utf8&sensor=false");
            NetworkManager.getInstance().addToQueueAndWait(r);

            JSONParser jsonp = new JSONParser();
            try {
                java.util.Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(r.getResponseData()).toCharArray()));
                System.out.println("roooooot:" +tasks.get("results"));
                List<java.util.Map<String, Object>> list1 = (List<java.util.Map<String, Object>>)tasks.get("results");
//                              java.util.Map<String, Object> list = (java.util.Map<String, Object>) list1.get(0);

                //                             List<java.util.Map<String, Object>> listf = (List<java.util.Map<String, Object>>) list.get("address_components");
//String ch="";
                //                       for (java.util.Map<String, Object> obj : listf) {
                //             ch=ch+obj.get("long_name").toString();
                //                   }
                //
                // b.setAdresse(ch);



            } catch (IOException ex) {
            }



        });


        Container root = new Container();
        f.setLayout(new BorderLayout());
        f.addComponent(BorderLayout.CENTER, cnt);
        f.addComponent(BorderLayout.SOUTH, btnMoveCamera);
        f.show();
        //f.getToolbar().addCommandToRightBar("back", null, (ev)->{ new AjoutReclamationForm(f).show()});

    }







}