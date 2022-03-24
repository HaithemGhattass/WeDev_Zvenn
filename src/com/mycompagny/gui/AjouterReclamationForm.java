/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompagny.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.*;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompagny.entities.Reclamation;
import com.mycompagny.services.ServiceReclamation;

import java.util.Date;



/**
 *
 * @author mtar
 */
public class AjouterReclamationForm extends BaseForm {
    Form current;
    
    public AjouterReclamationForm(Resources res){
Toolbar tb = new Toolbar(true);
current = this;
setToolbar(tb);

    getTitleArea().setUIID("containrer");
    //setTitle("Zven");
    getContentPane().setScrollVisible(false);
        tb.addSearchCommand(e -> {});
    Tabs swipe = new Tabs();
    Label s1 = new Label();
    Label s2 = new Label();

    addTab(swipe , s1 , res.getImage("images.jpg") , "" , "" , res);

        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Mes Reclamation", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Autres", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Reclamer", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");


        mesListes.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
        
         ListReclamationForm a = new ListReclamationForm(res);
            a.show();
            refreshTheme();
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, liste, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(partage, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
    
    
        TextField titre = new TextField("","titre");
        titre.setUIID("TextFieldBlack");
        titre.setUIID("TextFieldBlack");
        addStringValue("titre",titre);
        TextField description = new TextField("","description");
        description.setUIID("TextFieldBlack");
        addStringValue("description",description);
        TextField food = new TextField("","rate us /10");
        food.setUIID("TextFieldBlack");
        addStringValue("food qualité",food);
        TextField price = new TextField("","rate us /10");
        price.setUIID("TextFieldBlack");
        addStringValue("price qualité",price);
        TextField service = new TextField("","   rate us /10 ");
        service.setUIID("TextFieldBlack");
        addStringValue("service",service);
        
        
        Button btnAjouter = new Button("Ajouter");
        addStringValue("", btnAjouter);
        
        //on click btn event
        
        
       btnAjouter.addActionListener((e) -> {
           
           try {
               if (titre.getText()==""|| description.getText()=="") {
                   Dialog.show("veuiller remplir les champs", "", "Annuler", "OK");
                   
               }
               else {
                   InfiniteProgress ip = new InfiniteProgress();
                   final Dialog iDialog = ip.showInfiniteBlocking();
                  
                   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                   
                   Reclamation re = new Reclamation(String.valueOf(titre.getText()).toString(),
                           String.valueOf(description.getText()).toString(),Integer.parseInt(food.getText()),
                           Integer.parseInt(service.getText()),
                           Integer.parseInt(price.getText()),
                           "nontraite",
                           format.format(new Date()));
                     System.out.println("data reclamation =="+re);
               
               //appelle methode ajoutReclamation bch nzidou donnees fel base
               ServiceReclamation.getInstance().ajoutReclamation(re);
               
               iDialog.dispose(); //for no loading apres ajout
               
              // createStarRankSlider();
              // showStarPickingForm();
               refreshTheme();
               new ListReclamationForm(res).show();
               //actualisation
           
       }
   }catch(Exception ex) {
       ex.printStackTrace();
   }
   
   
    });
    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
                .add(BorderLayout.CENTER,v));
        

    }
    private void addTab(Tabs swipe,Label spacer,  Image image,String string, String text, Resources res) {
         int size = Math.min(Display.getInstance().getDisplayWidth() , Display.getInstance().getDisplayHeight());
          if (image.getHeight() < size) {
              image = image.scaledHeight(size);
          }
          if(image.getHeight() > Display.getInstance().getDisplayHeight() /2 ){
              image = image.scaledHeight(Display.getInstance().getDisplayHeight() /2 );
              
                      
          }
          ScaleImageLabel imageScale = new ScaleImageLabel (image);
          imageScale.setUIID("Container");
          imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
          
          Label overLay = new Label("","ImageOverlay");
          
          Container page1 =
                  LayeredLayout.encloseIn(
                  imageScale,
                          overLay,
                          BorderLayout.south(
                          BoxLayout.encloseY(
                          new SpanLabel(text,"LargeWhiteText"),
                                 //FlowLayout.encloseIn(null),
                                  spacer
                                 
                          )
                          )
                  );
          swipe.addTab("",res.getImage("logo_zven.png"), page1);
          
    }
 private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if(b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }

   private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();
        
        
    }

    private void initStarRankStyle(Style s, Image star) {
    s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
    s.setBorder(Border.createEmpty());
    s.setBgImage(star);
    s.setBgTransparency(0);
}

private Slider createStarRankSlider() {
    Slider starRank = new Slider();
    starRank.setEditable(true);
    starRank.setMinValue(0);
    starRank.setMaxValue(10);
    Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
            derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
    Style s = new Style(0xffff33, 0, fnt, (byte)0);
    Image fullStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
    s.setOpacity(100);
    s.setFgColor(0);
    Image emptyStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
    initStarRankStyle(starRank.getSliderEmptySelectedStyle(), emptyStar);
    initStarRankStyle(starRank.getSliderEmptyUnselectedStyle(), emptyStar);
    initStarRankStyle(starRank.getSliderFullSelectedStyle(), fullStar);
    initStarRankStyle(starRank.getSliderFullUnselectedStyle(), fullStar);
    starRank.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));
    return starRank;
}
private void showStarPickingForm() {
    Form hi = new Form("Star Slider", new BoxLayout(BoxLayout.Y_AXIS));
    hi.add(FlowLayout.encloseCenter(createStarRankSlider()));
    hi.show();
}
}
