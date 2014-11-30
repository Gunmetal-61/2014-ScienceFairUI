/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.soundsearch221;

import static com.vaadin.server.Sizeable.UNITS_PIXELS;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;

/**
 *
 * @author Mitchell
 */
public class homepage {
//    public static AbsoluteLayout drawPage(final AbsoluteLayout primary){
    public static void drawPage(final AbsoluteLayout primary){
        AbsoluteLayout grid = new AbsoluteLayout();
        grid.setHeight(1080, UNITS_PIXELS);
        grid.setWidth(1366, UNITS_PIXELS);

        final AbsoluteLayout ingrid = new AbsoluteLayout();
        ingrid.setHeight(200, UNITS_PIXELS);
        ingrid.setWidth(960, UNITS_PIXELS);

//        grid.addComponent(ingrid, "left: 203px; top: 0px;"); 
        primary.addComponent(ingrid, "left: 203px; top: 0px;"); 
        //Universal Menu Bar
        final Button aboutthisprojectbutton = new Button();
        final Button aboutusbutton = new Button();
        final Button advancedsearchpagebutton = new Button("Search Results");
        final Button homepagebutton = new Button();
        final Button searchresultpagebutton = new Button();
        final Button songresultpagebutton = new Button("Song Results");
        final Button nav6 = new Button();
        final Button nav7 = new Button();

        aboutthisprojectbutton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent nav0e) {
//                AbsoluteLayout primary = new AbsoluteLayout();
                aboutthisproject.drawPage(primary);
//                ingrid.addComponent(primary, "left: 0px; top: 600px");
            }
        });

        aboutusbutton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent nav1e) {
//                AbsoluteLayout primary = new AbsoluteLayout();
                searchresultpage.drawPage(primary);
//                ingrid.addComponent(primary, "left: 0px; top: 600px");
            }
        });

        advancedsearchpagebutton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent nav2e) {
//                AbsoluteLayout primary = new AbsoluteLayout();
                searchresultpage.drawPage(primary);
//                ingrid.addComponent(primary, "left: 0px; top: 600px");
            }
        });

        homepagebutton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent nav3e) {
//                AbsoluteLayout primary = new AbsoluteLayout();
                songresultpage.drawPage(primary);
//                ingrid.addComponent(primary, "left: 0px; top: 600px");
            }
        });

        searchresultpagebutton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent nav4e) {
//                AbsoluteLayout primary = new AbsoluteLayout();
                searchresultpage.drawPage(primary);
//                ingrid.addComponent(primary, "left: 0px; top: 600px");
            }
        });

        songresultpagebutton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent nav5e) {
//                AbsoluteLayout primary = new AbsoluteLayout();
                searchresultpage.drawPage(primary);
                ingrid.addComponent(primary, "left: 0px; top: 600px");
            }
        });

        nav6.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent nav6e) {
//                AbsoluteLayout primary = new AbsoluteLayout();
                searchresultpage.drawPage(primary);
                ingrid.addComponent(primary, "left: 0px; top: 600px");
            }
        });

        nav7.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent nav7e) {
//                AbsoluteLayout primary = new AbsoluteLayout();
                searchresultpage.drawPage(primary);
                ingrid.addComponent(primary, "left: 0px; top: 600px");
            }
        });

        ingrid.addComponent(aboutthisprojectbutton, "left: 0px; top: 0px;");
        ingrid.addComponent(aboutusbutton, "left: 0px; top: 0px;");
        ingrid.addComponent(advancedsearchpagebutton, "left: 300px; top: 0px;");
        ingrid.addComponent(homepagebutton, "left: 380px; top: 0px;");
        ingrid.addComponent(searchresultpagebutton, "left: 0px; top: 0px;");
        ingrid.addComponent(songresultpagebutton, "left: 0px; top: 0px;");
        ingrid.addComponent(nav6, "left: 0px; top: 0px;");
        ingrid.addComponent(nav7, "left: 0px; top: 0px;");

  //      return ingrid;
    }
}
