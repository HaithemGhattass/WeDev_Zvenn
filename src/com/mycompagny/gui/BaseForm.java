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
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompagny.entities.Restaurant;

/**
 * Utility methods common to forms e.g. for binding the side menu
 *
 * @author Shai Almog
 */
public class BaseForm extends Form {
    public void installSidemenu(Resources res) {
        Image selection = res.getImage("selection-in-sidemenu.png");
        
        Image inboxImage = null;
        if(isCurrentInbox()) inboxImage = selection;

        Image trendingImage = null;
        if(isCurrentTrending()) trendingImage = selection;
        
        Image calendarImage = null;
        if(isCurrentCalendar()) calendarImage = selection;
        
        Image statsImage = null;
        if(isCurrentStats()) statsImage = selection;

        Image AjoutRestaurantImage = null;
        if(isCurrentAjoutRestaurant()) AjoutRestaurantImage = selection;
        Image mesRestaurantImage = null;
        if(isCurrentmeRestaurant()) mesRestaurantImage = selection;


        
        Button inboxButton = new Button("Inbox", inboxImage);
        inboxButton.setUIID("SideCommand");
        inboxButton.getAllStyles().setPaddingBottom(0);
        Container inbox = FlowLayout.encloseMiddle(inboxButton,
                new Label("18", "SideCommandNumber"));
       // inbox.setLeadComponent(inboxButton);
        inbox.setUIID("SideCommand");
        inboxButton.addActionListener(e -> new InboxForm().show());

        getToolbar().addCommandToSideMenu("Home", trendingImage, e -> new TrendingForm(res).show());
if (SessionManager.isActif()) {
    getToolbar().addComponentToSideMenu(inbox);

    getToolbar().addCommandToSideMenu("Ajouter Restaurant", AjoutRestaurantImage, e -> new AjoutRestaurantForm(res).show());
    getToolbar().addCommandToSideMenu("mes Restaurant", mesRestaurantImage, e -> new ToggleForm(res).show());
    getToolbar().addCommandToSideMenu("Stats", statsImage, e -> new StatsForm(res).show());
    getToolbar().addCommandToSideMenu("Calendar", calendarImage, e -> new CalendarForm(res).show());
    getToolbar().addCommandToSideMenu("Map", null, e -> {
    });
    getToolbar().addCommandToSideMenu("Settings", null, e -> {
    });

        getToolbar().addCommandToSideMenu("logout", null, e -> {
                    SessionManager.setActif(false);
                    new TrendingForm(res).show();
                }
        );

    // spacer
    getToolbar().addComponentToSideMenu(new Label(" ", "SideCommand"));
    getToolbar().addComponentToSideMenu(new Label(res.getImage("profile_image.png"), "Container"));
    getToolbar().addComponentToSideMenu(new Label(SessionManager.getEmail(), "SideCommandNoPad"));
    getToolbar().addComponentToSideMenu(new Label("Long Beach, CA", "SideCommandSmall"));
}else {
    getToolbar().addCommandToSideMenu("SignIn", AjoutRestaurantImage, e -> new SignInForm(res).show());
    getToolbar().addCommandToSideMenu("SignUp", AjoutRestaurantImage, e -> new signin(res).show());

}
    }




    protected boolean isCurrentInbox() {
        return false;
    }
    
    protected boolean isCurrentTrending() {
        return false;
    }

    protected boolean isCurrentCalendar() {
        return false;
    }

    protected boolean isCurrentStats() {
        return false;
    }
    protected boolean isCurrentAjoutRestaurant() {
        return false;
    }
    protected boolean isCurrentmeRestaurant() {
        return false;
    }
}
