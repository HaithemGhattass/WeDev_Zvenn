/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompagny.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.*;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompagny.entities.Reclamation;
import com.mycompagny.entities.Restaurant;
import com.mycompagny.services.ServiceReclamation;

import java.util.ArrayList;

/**
 *
 * @author mtar
 */
public class ListReclamationForm extends BaseForm {
     Form current;
     Restaurant ress;
    public ListReclamationForm(Resources res) {
        
Toolbar tb = new Toolbar(true);
current = this;
setToolbar(tb);

    getTitleArea().setUIID("container");
    setTitle("Zven");
    getContentPane().setScrollVisible(false);
    
        tb.addSearchCommand(e -> {});
    
    Tabs swipe = new Tabs();
    
    Label s1 = new Label();
    Label s2 = new Label();
    
    addTab(swipe , s1 , res.getImage("images.jpg") , "" , "" , res);
    
    
    //
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
        
         ListReclamationForm a = new ListReclamationForm (res);
           a.show();
            refreshTheme();
        });
        partage.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
        
         AjouterReclamationForm b = new AjouterReclamationForm(res);
            b.show();
            refreshTheme();
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, liste, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        mesListes.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(mesListes, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
            
        });
      
        ArrayList<Reclamation>  list = ServiceReclamation.getInstance().affichageReclamation();
                
       
        for (Reclamation rec : list) {
            String urlImage ="admin.png"; 
            Image PlaceHolder = Image.createImage(120,90);
            EncodedImage enc = EncodedImage.createFromImage(PlaceHolder, false);
            URLImage Urlim = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);
           // addButton(Urlim,rec.getTitre(),rec.getDescription(),rec);
            addButton(res.getImage("admin.png"), rec,res);
            
            ScaleImageLabel image = new ScaleImageLabel(Urlim);
             Container containerImg = new Container();
             image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
             
        }
       
    
    
    //
  

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
          swipe.addTab("",res.getImage("admin.png"), page1);
          
    }
public void bindButtonSelection(Button btn , Label l ){
    btn.addActionListener(e-> {
            if(btn.isSelected()) {
                updateArrowPosition(btn,l);
            }
        });
    }

    private void updateArrowPosition(Button btn, Label l) {
        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth() /2 - l.getWidth() /2);
        l.getParent().repaint();

    }

    
     private void addButton(Image img,Reclamation rec,Resources res) {
       int height = Display.getInstance().convertToPixels(11.5f);
       int width = Display.getInstance().convertToPixels(14f);
       Button image = new Button(img.fill(width, height));
       image.setUIID("Label");
       Container cnt = BorderLayout.west(image);
       //cnt.setLeadComponent(image);
      /* TextArea ta = new TextArea(titre);
       ta.setUIID("NewsTopLine");
       ta.setEditable(false);
       TextArea t = new TextArea(description);
       t.setUIID("NewsTopLine");
       t.setEditable(false);*/
      Label titretxt= new Label("titre:"+rec.getTitre(),"NewsTopLine");
      Label descriptiontxt= new Label("Description:"+rec.getDescription(),"NewsTopLine");
      Label etattxt= new Label("Etat:"+rec.getEtat(),"NewsTopLine");
      Label datetxt= new Label("Date:"+rec.getDate(),"NewsTopLine");

      //cnt.add(BorderLayout.CENTER, BoxLayout.encloseY(titretxt,descriptiontxt,etattxt,datetxt,supp));
          
          
       
       image.addActionListener(e -> ToastBar.showMessage(rec.getTitre(), FontImage.MATERIAL_INFO));
       //supp icon
       Label supp=new Label("");
       supp.setUIID("NewsTopLine");
       Style supprStyle=new Style(supp.getUnselectedStyle());
       supprStyle.setFgColor(0xf21f1f);
       FontImage supprimerImage= FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprStyle);
       supp.setIcon(supprimerImage);
       supp.setTextPosition(RIGHT);
       supp.addPointerPressedListener(e -> {
             
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
               new ListReclamationForm(res).show();
           }
           
       }
       });
           //update icon
            Label update=new Label("");
       update.setUIID("NewsTopLine");
       Style updateStyle=new Style(update.getUnselectedStyle());
       updateStyle.setFgColor(0xf7ad02);
       FontImage updateImage= FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, updateStyle);
       update.setIcon(updateImage);
       update.setTextPosition(LEFT);
       update.addPointerPressedListener(e -> {
           
           new ModifierReclamationForm(res,rec).show();
               
       });
     
       cnt.add(BorderLayout.CENTER, BoxLayout.encloseY(
               BoxLayout.encloseX(titretxt),
               BoxLayout.encloseX(descriptiontxt),
               BoxLayout.encloseX(etattxt,update,supp)
              
               ));
       
       add(cnt);
          
   }
    
}
