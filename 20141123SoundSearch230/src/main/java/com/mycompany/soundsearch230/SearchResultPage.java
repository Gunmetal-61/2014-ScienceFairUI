/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.soundsearch230;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import static com.vaadin.server.Sizeable.UNITS_PIXELS;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Audio;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import java.io.File;

/**
 *
 * @author Mitchell
 */
public class SearchResultPage {
    public static AbsoluteLayout drawSearchRPage(){

////////////////////////////////////////////////////////////////////////////////
        //BASE PANELS/LAYOUTS: 
//        CssLayout grid = new AbsoluteLayout();
        AbsoluteLayout grid = new AbsoluteLayout();
        grid.setHeight(1080, UNITS_PIXELS);
        grid.setWidth(1366, UNITS_PIXELS);
        
        AbsoluteLayout ingrid = new AbsoluteLayout();
        ingrid.setHeight(1080, UNITS_PIXELS);
        ingrid.setWidth(960, UNITS_PIXELS);
        
        //primary.addComponent(grid);
        grid.addComponent(ingrid, "left: 203px; top: 0px;");

////////////////////////////////////////////////////////////////////////////////        
        //SIDEBAR
        Panel sidebar = new Panel();
        sidebar.setHeight(1080, UNITS_PIXELS);
        sidebar.setWidth(200, UNITS_PIXELS);
 //       sidebar.addStyleName(sidebur);
        ingrid.addComponent(sidebar, "left: 0px; top: 0px;");
        
        
        
        Panel serp = new Panel();
        serp.setHeight(160, UNITS_PIXELS);
        serp.setWidth(760, UNITS_PIXELS);
        ingrid.addComponent(serp, "left: 200px; top: 160px;");
        
        
        
        
        
////////////////////////////////////////////////////////////////////////////////
//      RESULT TABLE
        final Table table = new Table();
        table.addContainerProperty("Song Name", String.class, null);
        table.addContainerProperty("Artist", String.class, null);
        table.addContainerProperty("Album", String.class, null);
        table.addContainerProperty("Length", String.class, null);
        table.addContainerProperty("Genre", String.class, null);
        table.addItem(new Object[]{"One More Night", "Maroon 5", "Overexposed", "", ""}, 1);
        table.addItem(new Object[]{"Hall of Fame", "The Script", "#3", "", ""}, 2);
        table.addItem(new Object[]{"We are the Champions", "Queen", "Top Hits", "", ""}, 3);
        
        ingrid.addComponent(table,"left: 200px; top: 0px;");
        
        
        
        
        
        
//////////////////////////////////////////////////////////////////////////////////    
//        //MODULAR RESULT PANEL A
//        VerticalLayout modularA = new VerticalLayout();
//        modularA.setHeight(160, UNITS_PIXELS);
//        modularA.setWidth(760, UNITS_PIXELS);
//        ingrid.addComponent(modularA, "left: 200px; top: 0px;");        
//        //LABELS
//        Label songtitle = new Label("One More Night");//Song Title
//        Label songartist = new Label("Maroon 5");//Artist of Song
//        Label songyear = new Label("2011");//Year Song was Released
//        Label songlength = new Label("3:39");//Length of Song
//        Label songgenre = new Label("Pop");//Song Genre
//        Label songlyrics = new Label("");//Song Lyrics        
//        Link lyricslink = new Link("Song Lyrics",new ExternalResource("http://www.azlyrics.com/lyrics/maroon5/onemorenight.html"));//Link to Lyrics
//
//        //Album Image
//        FileResource resource = new FileResource(new File("C:\\Common Directory\\SoundSearch\\Images\\Maroon-5-Overexposed-Cover.jpg"));
//        
//        Image albumimage = new Image("",resource);
//        albumimage.setWidth(40, UNITS_PIXELS);
//        albumimage.setHeight(40, UNITS_PIXELS);        
//        
//        //Waveform Graph Image
//        FileResource waveformf = new FileResource(new File("C:\\Common Directory\\SoundSearch\\Images\\stock graph image jpeg.jpg"));
//        
//        Image waveform = new Image("",waveformf);
//        waveform.setWidth(600, UNITS_PIXELS);
//        waveform.setHeight(67, UNITS_PIXELS);
//        
//        Label waveformtitle = new Label("Waveform Readings");
//        
//
////       ingrid.addComponent(button, "left: 260px; top: 0px;");
//        modularA.addComponent(albumimage);
//        modularA.addComponent(songtitle);
//        modularA.addComponent(songartist);
//        modularA.addComponent(songyear);
//        modularA.addComponent(songlength);
//        modularA.addComponent(songgenre);
////        ingrid.addComponent(lyricslink, "left: 260px; top: 0px;");
//        modularA.addComponent(waveform);
////
////        ingrid.addComponent(waveformtitle, "left: 40px; top: 260px;");
//        
//////////////////////////////////////////////////////////////////////////////////        
//        
//        
//        
//        
//        //Intercept Graph Image
//        Image intercept = new Image("",waveformf);
//        intercept.setWidth(600, UNITS_PIXELS);
//        intercept.setHeight(67, UNITS_PIXELS);
//        
//        Label intercepttitle = new Label("Intercept Waveform Visuals");
//        
//        //OLD USELESS STUFF (FOR NOW AT LEAST)
//        TextField name = new TextField("Name");
//        
////        Button button = new Button("Click Me");
////        button.addClickListener(new Button.ClickListener() {
////            public void buttonClick(Button.ClickEvent event) {
////                ingrid.addComponent(new Label("Thank you for clicking"));
////            }
////        });
//        
//        //Play the Song (Audio)
//        FileResource songfile = new FileResource(new File("C:\\Users\\Mitchell\\Music\\13 Immortal Empire.wav"));
//        //FileResource songfile = new FileResource(new File("C:\\Users\\Mitchell\\Documents\\NetBeansProjects\\20141111SoundSearch210\\src\\main\\webapp\\WEB-INF\\audio\\13 Immortal Empire.wav"));
//        Audio song = new Audio("",songfile);
//        //song.setAutoplay(true);
//        song.setShowControls(true);
//    //    song.
//        
//        //ADD TO VERTICAL LAYOUT WITHIN BOARD
//////      
////        
////          ingrid.addComponent(name, "left: 260px; top: 0px;");
//        ingrid.addComponent(intercept, "left: 30px; top: 430px");
////        ingrid.addComponent(intercepttitle, "left: 40px; top: 400px;");
////        ingrid.addComponent(song, "left: 30px; top: 540px;");
        
        return grid;
    }
}
