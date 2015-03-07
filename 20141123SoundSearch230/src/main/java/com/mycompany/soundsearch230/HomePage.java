/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.soundsearch230;

import com.vaadin.server.FileResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import java.io.File;

/**
 *
 * @author Mitchell
 */
public class HomePage {
    public static VerticalLayout drawHomePage(){
        VerticalLayout grid = new VerticalLayout();
//        Image homeImage = new Image(null,new FileResource(new File("F:\\Jeffrey\\Desktop\\Science Project 2014-2015\\UI\\2014-ScienceFairUI\\20141123SoundSearch230\\src\\main\\webapp\\VAADIN\\themes\\mytheme\\img\\MusicListening.jpg")));
//        grid.addComponent(homeImage);
        grid.setStyleName("homepage");
        return grid;
    }
}
