package com.mycompagny.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.*;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompagny.entities.Coupon;
import com.mycompagny.services.ServiceCoupon;
import java.util.ArrayList;
import java.util.Date;


public class ModifierCouponForm extends BaseForm{

    Form current;
    public ModifierCouponForm(Resources res, Coupon cp){

        initGuiBuilderComponents(res);
        installSidemenu(res);

        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_PUBLIC, e -> {});
        Toolbar tb =  new Toolbar();
        current=this;
        setToolBar(tb);
        getTitleArea().setUIID("container");
        setTitle("Modifier Commande");
        getContentPane().setScrollVisible(false);

        TextField code = new TextField(String.valueOf(cp.getCode()),"code",20,TextField.ANY);
        code.setUIID("NewsTopLine");
        code.setSingleLineTextArea(focusScrolling);

        //String dateLimite=cp.getDateLimite();
        Picker dateLimite = new Picker();
        dateLimite.setType(Display.PICKER_TYPE_DATE);
        dateLimite.setDate(new Date());
        //addStringValue("Date Limite du Code",dateLimite);

        TextField remise = new TextField(String.valueOf(cp.getRemise()),"remise",20,TextField.ANY);
        remise.setUIID("NewsTopLine");
        remise.setSingleLineTextArea(focusScrolling);

        Button modifier = new Button("UPDATE");
        modifier.addPointerPressedListener(l->{

            cp.setCode(Integer.parseInt(code.getText()));
            cp.setDateLimite(dateLimite.getText());
            cp.setRemise(Integer.parseInt(remise.getText()));


            if(ServiceCoupon.getInsance().modifierCoupon(cp)){
                new ListeCouponsForm(res).show();
            }});
        Button annuler = new Button("ANNULER");
        annuler.addPointerPressedListener(l->{
            new ListeCouponsForm(res).show();
        });

        Label l2 = new Label("");

        Label l3 = new Label("");

        Label l4 = new Label("");

        Label l5 = new Label("");

        Label l1 = new Label();

        Container content = BoxLayout.encloseY(
                l1, l2,
                new FloatingHint(code),
                createLineSeparator(),
                dateLimite,
                createLineSeparator(),
                new FloatingHint(remise),
                createLineSeparator(),
                modifier,
                annuler


        );

        add(content);
        show();


    }

    private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
        setLayout(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));

    }

    public Component createLineSeparator() {
        Label separator = new Label("", "WhiteSeparator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }
}
