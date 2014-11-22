/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.soundsearch220;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Grid;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import static com.vaadin.server.Sizeable.UNITS_PIXELS;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Audio;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.TextField;
import java.io.File;

/**
 *
 * @author Mitchell
 */
public class songresultpage {
    public static AbsoluteLayout drawPage(final AbsoluteLayout primary){
        
        AbsoluteLayout grid = new AbsoluteLayout();
        grid.setHeight(1080, UNITS_PIXELS);
        grid.setWidth(1366, UNITS_PIXELS);
        
        final AbsoluteLayout ingrid = new AbsoluteLayout();
        ingrid.setHeight(1080, UNITS_PIXELS);
        ingrid.setWidth(960, UNITS_PIXELS);
        
        grid.addComponent(ingrid, "left: 203px; top: 0px;");
        
        //Universal Menu Bar
        final Button nav0 = new Button();
        final Button nav1 = new Button();
        final Button nav2 = new Button();
        final Button nav3 = new Button();
        final Button nav4 = new Button();
        final Button nav5 = new Button();
        final Button nav6 = new Button();
        final Button nav7 = new Button();
        
//        MyVaadinUI primary = new AbsoluteLayout(primary);
        nav0.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent nav0e) {
                AbsoluteLayout primary = new AbsoluteLayout();
                primary = searchresultpage.drawPage(primary);

            }
        });
        
        nav1.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent nav0e) {
                AbsoluteLayout primary = new AbsoluteLayout();
                primary = searchresultpage.drawPage(primary);
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
                primary = searchresultpage.drawPage(primary);
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
        
        ingrid.addComponent(nav0, "left: 0px; top: 0px;");
        ingrid.addComponent(nav1, "left: 0px; top: 0px;");
        ingrid.addComponent(nav2, "left: 0px; top: 0px;");
        ingrid.addComponent(nav3, "left: 0px; top: 0px;");
        ingrid.addComponent(nav4, "left: 0px; top: 0px;");
        ingrid.addComponent(nav5, "left: 0px; top: 0px;");
        ingrid.addComponent(nav6, "left: 0px; top: 0px;");
        ingrid.addComponent(nav7, "left: 0px; top: 0px;");
        
        
        
        
        //LABELS
        //Song Title
        Label songtitle = new Label("One More Night");
        //Artist of Song0
        Label songartist = new Label("Maroon 5");
        //Year Song was Released
        Label songyear = new Label("2011");
        //Length of Song
        Label songlength = new Label("3:39");
        //Song Genre
        Label songgenre = new Label("Pop");
        //Song Lyrics
        Label songlyrics = new Label("");        
        //Link to Lyrics
        Link lyricslink = new Link("Song Lyrics",new ExternalResource("http://www.azlyrics.com/lyrics/maroon5/onemorenight.html"));

        

        
        //Album Image
        FileResource resource = new FileResource(new File("C:\\Users\\Mitchell\\Documents\\GitHub\\2014-ScienceFairUI\\20141111SoundSearch210\\src\\main\\webapp\\WEB-INF\\images\\Maroon-5-Overexposed-Cover.jpg"));
        
        Image albumimage = new Image("",resource);
        albumimage.setWidth(200, UNITS_PIXELS);
        albumimage.setHeight(200, UNITS_PIXELS);
        
        //Waveform Graph Image
        FileResource waveformf = new FileResource(new File("C:\\Users\\Mitchell\\Documents\\GitHub\\2014-ScienceFairUI\\20141111SoundSearch210\\src\\main\\webapp\\WEB-INF\\images\\stock graph image jpeg.jpg"));
        
        Image waveform = new Image("",waveformf);
        waveform.setWidth(900, UNITS_PIXELS);
        waveform.setHeight(100, UNITS_PIXELS);
        
        Label waveformtitle = new Label("Waveform Readings");
        
        //Intercept Graph Image
        Image intercept = new Image("",waveformf);
        intercept.setWidth(900, UNITS_PIXELS);
        intercept.setHeight(100, UNITS_PIXELS);
        
        Label intercepttitle = new Label("Intercept Waveform Visuals");
        
        //OLD USELESS STUFF (FOR NOW AT LEAST)
        TextField name = new TextField("Name");
        
//        Button button = new Button("Click Me");
//        button.addClickListener(new Button.ClickListener() {
//            public void buttonClick(Button.ClickEvent event) {
//                ingrid.addComponent(new Label("Thank you for clicking"));
//            }
//        });
        
        //Play the Song (Audio)
        FileResource songfile = new FileResource(new File("C:\\Users\\Mitchell\\Music\\13 Immortal Empire.wav"));
        //FileResource songfile = new FileResource(new File("C:\\Users\\Mitchell\\Documents\\NetBeansProjects\\20141111SoundSearch210\\src\\main\\webapp\\WEB-INF\\audio\\13 Immortal Empire.wav"));
        Audio song = new Audio("",songfile);
        //song.setAutoplay(true);
        song.setShowControls(true);
    //    song.
        
        //ADD TO VERTICAL LAYOUT WITHIN BOARD
//        ingrid.addComponent(name, "left: 260px; top: 0px;");
//       ingrid.addComponent(button, "left: 260px; top: 0px;");
        ingrid.addComponent(albumimage , "left: 30px; top: 30px;");
        ingrid.addComponent(songtitle, "left: 260px; top: 40px;");
        ingrid.addComponent(songartist, "left: 260px; top: 60px;");
        ingrid.addComponent(songyear, "left: 260px; top: 100px;");
        ingrid.addComponent(songlength, "left: 260px; top: 120px;");
        ingrid.addComponent(songgenre, "left: 260px; top: 80px;");
//        ingrid.addComponent(lyricslink, "left: 260px; top: 0px;");
        ingrid.addComponent(waveform, "left: 30px; top: 290px;");

        ingrid.addComponent(waveformtitle, "left: 40px; top: 260px;");
        
        ingrid.addComponent(intercept, "left: 30px; top: 430px");
        ingrid.addComponent(intercepttitle, "left: 40px; top: 400px;");
        ingrid.addComponent(song, "left: 30px; top: 540px;");
        
        return ingrid;
    }
}
