package com.mycompagny.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompagny.entities.Coupon;
import com.mycompagny.services.ServiceCoupon;
import java.util.ArrayList;

public class ListeCouponsForm extends BaseForm{

    Form current;
    public ListeCouponsForm(Resources res){
        initGuiBuilderComponents(res);
        installSidemenu(res);

        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_PUBLIC, e -> {});
        Toolbar tb =  new Toolbar();
        current=this;
        setToolBar(tb);
        getTitleArea().setUIID("container");
        setTitle("Liste des Coupons");
        getContentPane().setScrollVisible(false);

        tb.addSearchCommand(e->{


        });
        Tabs swipe= new Tabs();
        Label l1= new Label();
        addTab(swipe,l1, res.getImage("livraison.jpg"),"","",res);

        ArrayList<Coupon> list = ServiceCoupon.getInsance().affichageCoupon();
        for(Coupon cp:list){
            addButton(cp,res);
        }
    }


    private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
        setLayout(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));

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

    private void addButton(Image img, String adresseLivraison, Coupon cp) {

        TextArea ta= new TextArea(adresseLivraison);
        ta.setUIID("NewsTopline");
        ta.setEditable(false);
        add(ta);
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
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(text, "LargeWhiteText"),
                                        spacer
                                )
                        )
                );

        swipe.addTab("",res.getImage("livraison.jpg"), page1);
    }

    private void addButton(Coupon cp, Resources res) {

        Label code = new Label("Coupon : "+cp.getCode(),"NewsTopLine2");
        Label date = new Label("Date Limite : "+cp.getDateLimite(),"NewsTopLine2");
        Label remise = new Label("Remise : "+cp.getRemise()+"%","NewsTopLine2");

        //delete button
        Label delete = new Label();
        delete.setUIID("NewsTopLine");
        Style deleteStyle= new Style(delete.getUnselectedStyle());
        deleteStyle.setFgColor(0xf21f1f);
        FontImage deleteImg=FontImage.createMaterial(FontImage.MATERIAL_DELETE, deleteStyle);
        delete.setIcon(deleteImg);
        delete.setTextPosition(RIGHT);

        delete.addPointerPressedListener(l->{
            Dialog dialog = new Dialog("Suppression");
            if(dialog.show("SUPPRESSION","VOULEZ-VOUS SUPPRIMER CETTE COMMANDE ?","ANNULER","OK")){
                dialog.dispose();
            }else{
                dialog.dispose();
                if(ServiceCoupon.getInsance().deleteCoupon(cp.getId())){
                    new ListeCouponsForm(res).show();
                }

            }

        });

        //update button
        Label update = new Label();
        update.setUIID("NewsTopLine");
        Style updateStyle= new Style(update.getUnselectedStyle());
        updateStyle.setFgColor(0xf7ad02);
        FontImage updateImg=FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, updateStyle);
        update.setIcon(updateImg);
        update.setTextPosition(LEFT);

        update.addPointerPressedListener(l->{
            new ModifierCouponForm(res, cp).show();

        });

        add(BoxLayout.encloseY(
                BoxLayout.encloseX(code),
                BoxLayout.encloseX(date),
                BoxLayout.encloseX(remise,delete,update)
        ));
    }

    public Component createLineSeparator() {
        Label separator = new Label("", "WhiteSeparator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }

}
