/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.soundsearch230;

import Database.DBRow;
import Database.DatabaseAccess;
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
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Mitchell
 */
public class SearchResultPage {
    public static AbsoluteLayout drawSearchRPage() throws SQLException{
        
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
        
        
        
//        Panel serp = new Panel();
//        serp.setHeight(160, UNITS_PIXELS);
//        serp.setWidth(760, UNITS_PIXELS);
//        ingrid.addComponent(serp, "left: 200px; top: 160px;");
        
        
        
        
        
////////////////////////////////////////////////////////////////////////////////
//      RESULT TABLE
        int[] anArray;
        
        anArray = new int[7];
        anArray[0] = 1;
        anArray[1] = 2;
        anArray[2] = 3;
        anArray[3] = 4;
        anArray[4] = 5;
        anArray[5] = 6;
        anArray[6] = 7;
        
        
        Connection con = DatabaseAccess.startconnection("orcl"); 
        DBRow[] resultFeed = DatabaseAccess.getSearchResults(con, anArray, 0, "", "", 0);
//        DBRow[] resultFeed(con, int[] mood, int length, String name, String artist) throws SQLException{
        
            

    //        int fdj = mood;
    //        int dfj = length;
    //        String gjd = name;
    //        String gf = artist;

//            artist = null;
//            name = null;
//            mood = 0;
//            length = 0;
            
//            DBRow[] resultFeed = DatabaseAccess.getSearchResults(con, anArray, mood, name, name, mood);
            
            final Table resultTable = new Table();
            resultTable.addContainerProperty("Song Name", String.class, null);
            resultTable.addContainerProperty("Artist", String.class, null);
            resultTable.addContainerProperty("Album", String.class, null);
            resultTable.addContainerProperty("Length", String.class, null);
            resultTable.addContainerProperty("Genre", String.class, null);
            resultTable.addContainerProperty("Mood", int.class, null);


            resultTable.addItem(new Object[]{resultFeed[0].name, resultFeed[0].artist, "Top Hits", "", "", resultFeed[0].mood}, 1);
            resultTable.addItem(new Object[]{resultFeed[1].name, resultFeed[1].artist, "Top Hits", "", "", resultFeed[1].mood}, 2);
            resultTable.addItem(new Object[]{resultFeed[2].name, resultFeed[2].artist, "Top Hits", "", "", resultFeed[2].mood}, 3);
            resultTable.addItem(new Object[]{resultFeed[3].name, resultFeed[3].artist, "Top Hits", "", "", resultFeed[3].mood}, 4);
            resultTable.addItem(new Object[]{resultFeed[4].name, resultFeed[4].artist, "Top Hits", "", "", resultFeed[4].mood}, 5);
            resultTable.addItem(new Object[]{resultFeed[5].name, resultFeed[5].artist, "Top Hits", "", "", resultFeed[5].mood}, 6);
            resultTable.addItem(new Object[]{resultFeed[6].name, resultFeed[6].artist, "Top Hits", "", "", resultFeed[6].mood}, 7);
            resultTable.addItem(new Object[]{resultFeed[7].name, resultFeed[7].artist, "Top Hits", "", "", resultFeed[7].mood}, 8);
            resultTable.addItem(new Object[]{resultFeed[8].name, resultFeed[8].artist, "Top Hits", "", "", resultFeed[8].mood}, 9);
            resultTable.addItem(new Object[]{resultFeed[9].name, resultFeed[9].artist, "Top Hits", "", "", resultFeed[9].mood}, 10);
            resultTable.addItem(new Object[]{resultFeed[10].name, resultFeed[10].artist, "Top Hits", "", "", resultFeed[10].mood}, 11);
            resultTable.addItem(new Object[]{resultFeed[11].name, resultFeed[11].artist, "Top Hits", "", "", resultFeed[11].mood}, 12);
            resultTable.addItem(new Object[]{resultFeed[12].name, resultFeed[12].artist, "Top Hits", "", "", resultFeed[12].mood}, 13);
            resultTable.addItem(new Object[]{resultFeed[13].name, resultFeed[13].artist, "Top Hits", "", "", resultFeed[13].mood}, 14);
            resultTable.addItem(new Object[]{resultFeed[14].name, resultFeed[14].artist, "Top Hits", "", "", resultFeed[14].mood}, 15);
            resultTable.addItem(new Object[]{resultFeed[15].name, resultFeed[15].artist, "Top Hits", "", "", resultFeed[15].mood}, 16);
            resultTable.addItem(new Object[]{resultFeed[16].name, resultFeed[16].artist, "Top Hits", "", "", resultFeed[16].mood}, 17);
            resultTable.addItem(new Object[]{resultFeed[17].name, resultFeed[17].artist, "Top Hits", "", "", resultFeed[17].mood}, 18);
            resultTable.addItem(new Object[]{resultFeed[18].name, resultFeed[18].artist, "Top Hits", "", "", resultFeed[18].mood}, 19);
            resultTable.addItem(new Object[]{resultFeed[19].name, resultFeed[19].artist, "Top Hits", "", "", resultFeed[19].mood}, 20);
            resultTable.addItem(new Object[]{resultFeed[20].name, resultFeed[20].artist, "Top Hits", "", "", resultFeed[20].mood}, 21);
            resultTable.addItem(new Object[]{resultFeed[21].name, resultFeed[21].artist, "Top Hits", "", "", resultFeed[21].mood}, 22);
            resultTable.addItem(new Object[]{resultFeed[22].name, resultFeed[22].artist, "Top Hits", "", "", resultFeed[22].mood}, 23);
            resultTable.addItem(new Object[]{resultFeed[23].name, resultFeed[23].artist, "Top Hits", "", "", resultFeed[23].mood}, 24);
            resultTable.addItem(new Object[]{resultFeed[24].name, resultFeed[24].artist, "Top Hits", "", "", resultFeed[24].mood}, 25);
            resultTable.addItem(new Object[]{"One More Night", "Maroon 5", "Overexposed", "", ""}, 26);
            resultTable.addItem(new Object[]{"Hall of Fame", "The Script", "#3", "", ""}, 27);
            resultTable.addItem(new Object[]{"We are the Champions", "Queen", "Top Hits", "", ""}, 28);

            ingrid.addComponent(resultTable, "left: 200px; top: 0px;");
        
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
