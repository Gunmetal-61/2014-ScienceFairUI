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
    public static AbsoluteLayout drawPage(final AbsoluteLayout primary){
        AbsoluteLayout grid = new AbsoluteLayout();
        grid.setHeight(1080, UNITS_PIXELS);
        grid.setWidth(1366, UNITS_PIXELS);

        final AbsoluteLayout ingrid = new AbsoluteLayout();
        ingrid.setHeight(200, UNITS_PIXELS);
        ingrid.setWidth(960, UNITS_PIXELS);

        grid.addComponent(ingrid, "left: 203px; top: 0px;"); 
        //Universal Menu Bar
        final Button nav0 = new Button();
        final Button nav1 = new Button();
        final Button nav2 = new Button("Search Results");
        final Button nav3 = new Button("Song Results");
        final Button nav4 = new Button();
        final Button nav5 = new Button();
        final Button nav6 = new Button();
        final Button nav7 = new Button();

    //        MyVaadinUI primary = new AbsoluteLayout(primary);
//        nav0.addClickListener(new Button.ClickListener() {
//            public void buttonClick(Button.ClickEvent nav0e) {
//                AbsoluteLayout primary = new AbsoluteLayout();
//                primary = aboutthisproject.drawPage(primary);
//
//            }
//        });

        nav1.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent nav0e) {
                AbsoluteLayout primary = new AbsoluteLayout();
                primary = aboutthisproject.drawPage(primary);
                ingrid.addComponent(primary, "left: 0px; top: 600px");
            }
        });

        nav2.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent nav0e) {
                AbsoluteLayout primary = new AbsoluteLayout();
                primary = searchresultpage.drawPage(primary);
                ingrid.addComponent(primary, "left: 0px; top: 600px");
            }
        });

        nav3.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent nav0e) {
                AbsoluteLayout primary = new AbsoluteLayout();
                primary = songresultpage.drawPage(primary);
                ingrid.addComponent(primary, "left: 0px; top: 600px");
            }
        });

        nav4.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent nav0e) {
                AbsoluteLayout primary = new AbsoluteLayout();
                primary = searchresultpage.drawPage(primary);
                ingrid.addComponent(primary, "left: 0px; top: 600px");
            }
        });

        nav5.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent nav0e) {
                AbsoluteLayout primary = new AbsoluteLayout();
                primary = searchresultpage.drawPage(primary);
                ingrid.addComponent(primary, "left: 0px; top: 600px");
            }
        });

        nav6.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent nav0e) {
                AbsoluteLayout primary = new AbsoluteLayout();
                primary = searchresultpage.drawPage(primary);
                ingrid.addComponent(primary, "left: 0px; top: 600px");
            }
        });

        nav7.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent nav0e) {
                AbsoluteLayout primary = new AbsoluteLayout();
                primary = searchresultpage.drawPage(primary);
                ingrid.addComponent(primary, "left: 0px; top: 600px");
            }
        });

        //ingrid.addComponent(nav0, "left: 0px; top: 0px;");
        //ingrid.addComponent(nav1, "left: 0px; top: 0px;");
        ingrid.addComponent(nav2, "left: 300px; top: 0px;");
        ingrid.addComponent(nav3, "left: 380px; top: 0px;");
        //ingrid.addComponent(nav4, "left: 0px; top: 0px;");
        //ingrid.addComponent(nav5, "left: 0px; top: 0px;");
        //ingrid.addComponent(nav6, "left: 0px; top: 0px;");
        //ingrid.addComponent(nav7, "left: 0px; top: 0px;");

        return ingrid;
    }
}
