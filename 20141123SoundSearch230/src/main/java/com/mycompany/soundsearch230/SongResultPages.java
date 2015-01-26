/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.soundsearch230;

import Database.DatabaseAccess;
import Wavesurfer.Wavesurfer;
import com.google.gwt.user.client.ui.UIObject;
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
import com.vaadin.ui.TextField;
import java.io.File;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mitchell
 */
public class SongResultPages {
//    public File loadsong(String name, String artist){
        
//    }
    
    public AbsoluteLayout drawSongRPage() {
        
        AbsoluteLayout grid = new AbsoluteLayout();
        grid.setHeight(1200, UNITS_PIXELS);
        grid.setWidth(1600, UNITS_PIXELS);

//        final AbsoluteLayout ingrid = new AbsoluteLayout();
//        ingrid.setHeight(1080, UNITS_PIXELS);
//        ingrid.setWidth(960, UNITS_PIXELS);
        
        final VerticalLayout inNonGrid = new VerticalLayout();

//        grid.addComponent(ingrid, "left: 203px; top: 0px;");
        grid.addComponent(inNonGrid, "left: 320px; top: 0px;");
        
        
        ////////////////////////////////////////////////////////////////////////
//      LABELS
        //Song Title
        Label songtitle = new Label(SearchResultPage.artistIdentifier);
        //Artist of Song
        Label songartist = new Label(SearchResultPage.nameIdentifier);
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
        
        




        ////////////////////////////////////////////////////////////////////////
//      Album Image
        FileResource resource = new FileResource(new File("C:\\Common Directory\\SoundSearch\\Images\\Maroon-5-Overexposed-Cover.jpg"));

        Image albumimage = new Image("",resource);
        albumimage.setWidth(200, UNITS_PIXELS);
        albumimage.setHeight(200, UNITS_PIXELS);

        ////////////////////////////////////////////////////////////////////////        
//      Waveform Graph Image
        FileResource waveformf = new FileResource(new File("C:\\Common Directory\\SoundSearch\\Images\\stock graph image jpeg.jpg"));

        Image waveform = new Image("",waveformf);
//        waveform.
        waveform.setWidth(900, UNITS_PIXELS);
        waveform.setHeight(100, UNITS_PIXELS);

        Label waveformtitle = new Label("Waveform Readings");

        
        final Wavesurfer myWavesurfer = new Wavesurfer();
        myWavesurfer.setHeight(130, UNITS_PIXELS);
        myWavesurfer.setWidth(900, UNITS_PIXELS);
        myWavesurfer.loadFile();
        
        Button D = new Button("fdfe");
        D.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                myWavesurfer.playOrPause();
            }
        });
        
        
        //Intercept Graph Image
        Image intercept = new Image("",waveformf);
        intercept.setWidth(900, UNITS_PIXELS);
        intercept.setHeight(100, UNITS_PIXELS);

        Label intercepttitle = new Label("Intercept Waveform Visuals");

        ////////////////////////////////////////////////////////////////////////
//      Play the Song (Audio)
        

        
        String out = null;
        try {
            out = new String(MyVaadinUI.dba.songdir(MyVaadinUI.con, null, SearchResultPage.nameIdentifier, SearchResultPage.artistIdentifier));
        } catch (SQLException ex) {
            Logger.getLogger(SongResultPages.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Returned File Directory: " + out);
        
//        FileResource songfile = new FileResource(new File("C:\\Users\\Mitchell\\Music\\13 Immortal Empire.wav"));
        FileResource songfile = new FileResource(new File(out));
        final Audio song = new Audio("",songfile);
        System.out.println(songfile.toString());
        
//        song.setVisible(false);
        song.setWidth("800");

        
//        song.setSizeFull();

        
        song.setAutoplay(true);
        song.setShowControls(true);
        

        ////////////////////////////////////////////////////////////////////////
//      ADD TO VERTICAL LAYOUT WITHIN BOARD
//    //        ingrid.addComponent(name, "left: 260px; top: 0px;");
//    //       ingrid.addComponent(button, "left: 260px; top: 0px;");
//        ingrid.addComponent(albumimage , "left: 30px; top: 30px;");
//        ingrid.addComponent(songtitle, "left: 260px; top: 40px;");
//        ingrid.addComponent(songartist, "left: 260px; top: 60px;");
//        ingrid.addComponent(songyear, "left: 260px; top: 100px;");
//        ingrid.addComponent(songlength, "left: 260px; top: 120px;");
//        ingrid.addComponent(songgenre, "left: 260px; top: 80px;");
//    //        ingrid.addComponent(lyricslink, "left: 260px; top: 0px;");
//        ingrid.addComponent(waveform, "left: 30px; top: 290px;");
//
//        ingrid.addComponent(waveformtitle, "left: 40px; top: 260px;");
//
//        ingrid.addComponent(intercept, "left: 30px; top: 430px");
//        ingrid.addComponent(intercepttitle, "left: 40px; top: 400px;");
//        ingrid.addComponent(song, "left: 30px; top: 540px;");
//
//        Button playButton = new Button("Play");
//        ingrid.addComponent(playButton, "left: 30px; top: 600px;");
//        playButton.addClickListener(new Button.ClickListener() {
//            public void buttonClick(ClickEvent event) {
//                song.play();
//            }
//        });
//
//        Button pauseButton = new Button("Pause");
//        ingrid.addComponent(pauseButton, "left: 30px; top: 650px;");
//        pauseButton.addClickListener(new Button.ClickListener() {
//            public void buttonClick(ClickEvent event) {
//                song.pause();
//            }
//        });

        
        
        
    //    ingrid.addComponent(name);
    //       ingrid.addComponent(button);
        inNonGrid.addComponent(albumimage);
        inNonGrid.addComponent(songtitle);
        inNonGrid.addComponent(songartist);
        inNonGrid.addComponent(songyear);
        inNonGrid.addComponent(songlength);
        inNonGrid.addComponent(songgenre);
    //        ingrid.addComponent(lyricslink);
//        inNonGrid.addComponent(waveform);
        inNonGrid.addComponent(myWavesurfer);
        inNonGrid.addComponent(D);

        inNonGrid.addComponent(waveformtitle);

        inNonGrid.addComponent(intercept);
        inNonGrid.addComponent(intercepttitle);
        inNonGrid.addComponent(song);

        Button playButton = new Button("Play");
        inNonGrid.addComponent(playButton);
        playButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                song.play();
            }
        });

        Button pauseButton = new Button("Pause");
        inNonGrid.addComponent(pauseButton);
        pauseButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                song.pause();
            }
        });
        

        
        return grid;
    }
}
