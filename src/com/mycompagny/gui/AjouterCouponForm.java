package com.mycompagny.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.*;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.*;

import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompagny.entities.Coupon;
import com.mycompagny.services.ServiceCoupon;

import java.awt.*;

public class AjouterCouponForm extends BaseForm{

    public AjouterCouponForm(){
        this(com.codename1.ui.util.Resources.getGlobalResources());
    }

    Form current;
    public AjouterCouponForm(Resources res){
        initGuiBuilderComponents(res);
        installSidemenu(res);

        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_PUBLIC, e -> {});
        Toolbar tb =  new Toolbar();
        current=this;
        setToolBar(tb);
        getTitleArea().setUIID("container");
        setTitle("Ajouter Coupon");
        getContentPane().setScrollVisible(false);

        tb.addSearchCommand(e->{


        });
        Tabs swipe= new Tabs();
        Label l1= new Label();
        Label l2 = new Label();
        addTab(swipe,l1, res.getImage("livraison.jpg"),"","",res);

        //
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg= new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g= unselectedWalkthru.getGraphics();
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

        Component.setSameSize(radioContainer, l1, l2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Liste des Coupons", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Ajouter Code", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.pg"), "Container");


        mesListes.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

            //  ListReclamationForm a = new ListReclamationForm(res);
            //  a.show();
            refreshTheme();
        });

        add(LayeredLayout.encloseIn(GridLayout.encloseIn(3,mesListes,partage), FlowLayout.encloseBottom(arrow)));

        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(partage, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        //



        TextField code= new TextField("","code");
        code.setUIID("TextFieldBlack");
        addStringValue("Code Coupon",code);

        Picker dateLimite = new Picker();
        dateLimite.setType(Display.PICKER_TYPE_DATE);
        code.setUIID("TextFieldBlack");
        addStringValue("Date Limite du Code",dateLimite);

        TextField remise= new TextField("","remise");
        code.setUIID("TextFieldBlack");
        addStringValue("Remise",remise);

        Button b = new Button("Ajouter");
        addStringValue("", b);
        b.addActionListener((e)->{
            try{
                if(code.getText()==""|| remise.getText()==""){
                    Dialog.show("Veuillez vérifier vos données","", "Annuler", "ok");
                }else{
                    //Loading après l'insertion de données
                    InfiniteProgress ip = new InfiniteProgress();
                    final Dialog iDialog =ip.showInfiniteBlocking();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Coupon cp = new Coupon(Integer.parseInt(code.getText()),
                            format.format(dateLimite.getDate()),
                            Integer.parseInt(remise.getText()),
                            SessionManager.getId());
                    System.out.println("Data="+cp);

                    //appel de la methode d'ajout du service pour faire lajout dans la bd
                    ServiceCoupon.getInsance().ajouterCoupon(cp);
                    iDialog.dispose();//pour arreter le loading

                    //new ListeCouponForm(res).show();
                    refreshTheme();
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        });
    }


    public void bindButtonSelection(Button btn , Label l ) {

        btn.addActionListener(e-> {
            if(btn.isSelected()) {
                updateArrowPosition(btn,l);
            }
        });
    }

    private void updateArrowPosition(Button btn, Label l) {

        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth()  / 2  - l.getWidth() / 2 );
        l.getParent().repaint();
    }

    private void addTab(Tabs swipe, Label spacer, Image image, String text, String string1, Resources res) {

        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label overLay = new Label("","ImageOverlay");


        Container page1 =
                LayeredLayout.encloseIn(
                        imageScale,
                        overLay,
                                BoxLayout.encloseY(
                                        new SpanLabel(text, "LargeWhiteText"),
                                        spacer
                                )

                );

        swipe.addTab("",res.getImage("livraison.jpg"), page1);

    }
    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
                .add(BorderLayout.CENTER,v));
        add(createLineSeparator(0xeeeeee));
    }
    public Component createLineSeparator(int color) {
        Label separator = new Label("", "WhiteSeparator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(255);
        separator.setShowEvenIfBlank(true);
        return separator;
    }




    private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
        setLayout(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));

    }
}
