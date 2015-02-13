/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.soundsearch230;

import Database.DBRow;
import Database.DatabaseAccess;
import Wavesurfer.Wavesurfer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import static com.vaadin.server.Sizeable.UNITS_PIXELS;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Audio;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author Mitchell
 */
public class SearchResultPage {
    
    public Table resultTable = new Table();
    Connection con = DatabaseAccess.startconnection("orcl"); 
    int[] moodarray;
    public static String nameIdentifier = "";
    public static String artistIdentifier = "";
    public static int length = 0;

    public DBRow[] resultFeed = DatabaseAccess.getSearchResults(con, "", moodarray, 0, "", "", 0);
    Label stConvert;
    Label saConvert;
    AbsoluteLayout SRPingrid;
    TabSheet other;
    //AbsoluteLayout alternate;
    public SearchResultPage(TabSheet primary) {
        other = primary;
    }
    
    
    public AbsoluteLayout drawSearchRPage(){
        

////////////////////////////////////////////////////////////////////////////////
        //BASE PANELS/LAYOUTS: 
//        CssLayout grid = new AbsoluteLayout();
        AbsoluteLayout grid = new AbsoluteLayout();
        grid.setHeight(1200, UNITS_PIXELS);
        grid.setWidth(1600, UNITS_PIXELS);
        
        AbsoluteLayout ingrid = new AbsoluteLayout();
        ingrid.setHeight(1200, UNITS_PIXELS);
        ingrid.setWidth(960, UNITS_PIXELS);
        
        //primary.addComponent(grid);
        grid.addComponent(ingrid, "left: 320px; top: 0px;");

////////////////////////////////////////////////////////////////////////////////        
        //SIDEBAR
        Panel sidebar = new Panel();
        sidebar.setHeight(1200, UNITS_PIXELS);
        sidebar.setWidth(200, UNITS_PIXELS);
 //       sidebar.addStyleName(sidebur);
        ingrid.addComponent(sidebar, "left: 0px; top: 0px;");
        
        
        
//        Panel serp = new Panel();
//        serp.setHeight(160, UNITS_PIXELS);
//        serp.setWidth(760, UNITS_PIXELS);
//        ingrid.addComponent(serp, "left: 200px; top: 160px;");
        
        
        
        
        
////////////////////////////////////////////////////////////////////////////////
//      RESULT TABLE
        
        
        moodarray = new int[7];
        moodarray[0] = 1;
        moodarray[1] = 2;
        moodarray[2] = 3;
        moodarray[3] = 4;
        moodarray[4] = 5;
        moodarray[5] = 6;
        moodarray[6] = 7;
        

        
        resultTable.addContainerProperty("Song Name", String.class, null);
        resultTable.addContainerProperty("Artist", String.class, null);
        resultTable.addContainerProperty("Album", String.class, null);
        resultTable.addContainerProperty("Length", String.class, null);
        resultTable.addContainerProperty("Genre", String.class, null);
        resultTable.addContainerProperty("Mood", java.lang.String.class, null);
 //       resultTable.addContainerProperty("Waveform", Wavesurfer.class, null);
        

        final String generalq = "";
        
        
        resultTable.addItemClickListener(new ItemClickEvent.ItemClickListener () {
            @Override
            public void itemClick(ItemClickEvent event) {
//                if()
                
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                System.out.println("Song: " + MyVaadinUI.result[Integer.valueOf(event.getItemId().toString())].name);
                nameIdentifier = MyVaadinUI.result[Integer.valueOf(event.getItemId().toString())].name;
                System.out.println("nameidentifier: " + nameIdentifier);
                artistIdentifier = MyVaadinUI.result[Integer.valueOf(event.getItemId().toString())].artist;
                length = MyVaadinUI.result[Integer.valueOf(event.getItemId().toString())].length;
//                stConvert = new Label(nameIdentifier);
//                saConvert = new Label(artistIdentifier);
//                SRPingrid.addComponent(stConvert, "left: 260px; top: 40px;");
//                SRPingrid.addComponent(saConvert, "left: 260px; top: 60px;");
                
//                other.getTab(SonRPage);
//                other.removeTab(SonRPage);
                SongResultPages songResultPage2 = new SongResultPages();
                AbsoluteLayout SonRPage = songResultPage2.drawSongRPage();
                other.addTab(SonRPage);
                other.getTab(SonRPage).setCaption(WordUtils.capitalize(nameIdentifier)); //label tab with song name
                other.getTab(SonRPage).setClosable(true); //allow user to close tabs
                other.setSelectedTab(SonRPage);
                 
            }
        });
        
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

        return grid;
    }
    
}
