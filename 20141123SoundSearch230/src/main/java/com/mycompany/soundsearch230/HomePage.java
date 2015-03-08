/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.soundsearch230;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Mitchell
 */
public class HomePage {
    public static VerticalLayout drawHomePage(){
        VerticalLayout grid = new VerticalLayout();
        grid.setSizeFull();
        grid.setHeight(1200, Unit.PIXELS);
        grid.setStyleName("homepage");
        return grid;
    }
}
