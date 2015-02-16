/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.soundsearch230;

import Database.DatabaseAccess;
import IDEPull.IDEExtract;
import Wavesurfer.Wavesurfer;
import com.google.gwt.user.client.ui.UIObject;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import static com.vaadin.server.Sizeable.UNITS_PIXELS;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Audio;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Field.ValueChangeEvent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Slider;
import com.vaadin.ui.Slider.ValueOutOfBoundsException;
import com.vaadin.ui.TextField;
import java.io.File;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.data.Property;
import com.vaadin.shared.ui.MarginInfo;
//import com.vaadin.data.Property.ValueChangeEvent;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Slider;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Slider.ValueOutOfBoundsException;
import com.vaadin.ui.TextArea;
import java.io.RandomAccessFile;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author Mitchell
 */
public class SongResultPages {
    String artist;
    String title;
    int length;
    
    public SongResultPages(String title, String artist, int length){
        this.title =  title;
        this.artist = artist;
        this.length = length;
    }
    
    public AbsoluteLayout drawSongRPage() {      
        AbsoluteLayout grid = new AbsoluteLayout();
        grid.setHeight(1200, UNITS_PIXELS);
        grid.setWidth(1600, UNITS_PIXELS);
        
        final VerticalLayout inNonGrid = new VerticalLayout();
//        inNonGrid.setSpacing(true);
        HorizontalLayout generalInfoContainer = new HorizontalLayout();
        generalInfoContainer.setSpacing(true);
        HorizontalLayout albumArtContainer = new HorizontalLayout();
        VerticalLayout generalSongDataContainer = new VerticalLayout();
        VerticalLayout detailedInfoContainer = new VerticalLayout();
        HorizontalLayout mediaControlContainer = new HorizontalLayout();

        grid.addComponent(inNonGrid, "left: 320px; top: 0px;");
        inNonGrid.addComponent(generalInfoContainer);
        generalInfoContainer.addComponent(albumArtContainer);
        generalInfoContainer.addComponent(generalSongDataContainer);
        inNonGrid.addComponent(detailedInfoContainer);

        
        ////////////////////////////////////////////////////////////////////////
//      LABELS
        //Song Title
        Label songtitle = new Label(WordUtils.capitalize(artist));
        //Artist of Song
        Label songartist = new Label(WordUtils.capitalize(title));
        //Year Song was Released
        Label songyear = new Label("2011");
        //Length of Song
        Label songlength = new Label(time(length));
        //Song Genre
        Label songgenre = new Label("Pop");
        //Song Lyrics
        Label songlyrics = new Label("");        
        //Link to Lyrics
        Link lyricslink = new Link("Song Lyrics",new ExternalResource("http://www.azlyrics.com/lyrics/maroon5/onemorenight.html"));
        
   


        ////////////////////////////////////////////////////////////////////////        
//      Waveform Graph Image


        Label waveformtitle = new Label("Waveform Readings");
        
        IDEExtract ideExtract = new IDEExtract();
        String playThisFile = ideExtract.findFile(title, artist);
        RandomAccessFile coverArt = ideExtract.getAlbumArt(playThisFile);
        
        
        
        final Wavesurfer myWavesurfer = new Wavesurfer();
        myWavesurfer.setHeight(180, UNITS_PIXELS);
        myWavesurfer.setWidth(900, UNITS_PIXELS);
        myWavesurfer.loadPlayFile(playThisFile);
        myWavesurfer.loadRegions(title, artist);
      
        ////////////////////////////////////////////////////////////////////////
//      Album Image
        FileResource resource = new FileResource(new File("F:\\Jeffrey\\Desktop\\Science Project 2014-2015\\UI\\2014-ScienceFairUI\\20141123SoundSearch230\\album-artwork"));

        Image albumimage = new Image("",resource);
        albumimage.setWidth(200, UNITS_PIXELS);
        albumimage.setHeight(200, UNITS_PIXELS);
        
        ////////////////////////////////////////////////////////////////////////

        final Button toggleMute = new Button("Mute");
        final Button toggleStopReset = new Button("Reset");
        final Button togglePlayPause = new Button("Play");
        
        //mute listener
        toggleMute.addClickListener(new Button.ClickListener() {
            int muteBinaryIndicator = 0;
            public void buttonClick(ClickEvent event) {
                if (muteBinaryIndicator == 0) {
                    myWavesurfer.muteSilence();
                    toggleMute.setCaption("Unmute");
                    muteBinaryIndicator = 1;
                } else {
                    myWavesurfer.muteSilence();
                    toggleMute.setCaption("Mute");
                    muteBinaryIndicator = 0;
                }
                
            }
        });
        
        //reset listener
        toggleStopReset.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                    myWavesurfer.stopReset();
                    toggleStopReset.setCaption("-----");
                    togglePlayPause.setCaption("Play");
            }
        });
        
        //play/pause listener
        togglePlayPause.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                toggleStopReset.setCaption("Reset");
                if (togglePlayPause.getCaption().equals("Play")) { //if paused
                    myWavesurfer.playOrPause(true); //play
                    togglePlayPause.setCaption("Pause");
                } else { //if playing
                    myWavesurfer.playOrPause(false); //pause
                    togglePlayPause.setCaption("Play");
                }  
            }
        });
        
        Label volume = new Label("Volume");
        
        final Slider volumeLevelSlider = new Slider(0, 100);
        try {
            volumeLevelSlider.setValue(100.0);
        } catch (ValueOutOfBoundsException e) {
        }
        
        Label speed = new Label("Playback Speed");
        
        final Slider speedLevelSlider = new Slider(0,2);
        try {
            speedLevelSlider.setValue(1.0);
        } catch (ValueOutOfBoundsException e) {
        }
       
        // Handle changes in slider value.
        volumeLevelSlider.addValueChangeListener(
            new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    double value = volumeLevelSlider.getValue() / 100;
                    myWavesurfer.volumeSetter(value);
                }
        });
        
        speedLevelSlider.addValueChangeListener(
            new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    double value = speedLevelSlider.getValue();
                    myWavesurfer.playSpeed(value);
                }
        });
        
        
        String lyricsText = null;
        try {
            lyricsText = DatabaseAccess.retrievelyrics(MyVaadinUI.con, title, artist);
        } catch (SQLException ex) {
            Logger.getLogger(SongResultPages.class.getName()).log(Level.SEVERE, null, ex);
        }
        TextArea lyricsDisplayed = new TextArea("Song Lyrics");
        lyricsDisplayed.setValue(lyricsText);
        lyricsDisplayed.setHeight(300, UNITS_PIXELS);
        lyricsDisplayed.setWidth(600, UNITS_PIXELS);
        

        albumArtContainer.addComponent(albumimage);
        generalSongDataContainer.addComponent(songtitle);
        generalSongDataContainer.addComponent(songartist);
        generalSongDataContainer.addComponent(songyear);
        generalSongDataContainer.addComponent(songlength);
        generalSongDataContainer.addComponent(songgenre);
        detailedInfoContainer.addComponent(waveformtitle);
        detailedInfoContainer.addComponent(myWavesurfer);   
        detailedInfoContainer.addComponent(mediaControlContainer);
        mediaControlContainer.addComponent(togglePlayPause);
        mediaControlContainer.addComponent(toggleMute);
        mediaControlContainer.addComponent(toggleStopReset);
        mediaControlContainer.addComponent(volume);
        mediaControlContainer.addComponent(volumeLevelSlider);
        mediaControlContainer.addComponent(speed);
        mediaControlContainer.addComponent(speedLevelSlider);
        mediaControlContainer.setComponentAlignment(volume, Alignment.MIDDLE_LEFT);
        mediaControlContainer.setComponentAlignment(speed, Alignment.MIDDLE_LEFT);
        detailedInfoContainer.addComponent(lyricsDisplayed);
        

        return grid;
    }
    
    private void load(){
        
    }
    
    /**
     * Convert seconds into M:SS
     * @param input Seconds
     * @return 
     */
    private static String time(int input){
        String seconds = (input%60>9)?String.valueOf(input%60):"0"+String.valueOf(input%60);
        return input/60 + ":" + seconds;
    }
}
