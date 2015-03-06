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
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.server.Sizeable.Unit;
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
    String album;
    String genre;
    int length;
    int year;
    int mood;
    
    String playThisFile;
    Image albumImage;
    
    int likeState = 0;
    /**
     * Instantiate a song result page
     * 
     * @param title
     * @param artist
     * @param album
     * @param genre
     * @param length
     * @param year 
     * @param mood 
     */
    public SongResultPages(String title, String artist, String album, String genre, int length, int year, int mood){
        this.title =  title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.length = length;
        this.year = year;
        this.mood = mood;
        
        //Get the album art
        IDEExtract ideExtract = new IDEExtract();
        this.playThisFile = ideExtract.findFile(title, artist);
        albumImage = ideExtract.getAlbumArt(playThisFile);
        //this.resource = new FileResource(new File("F:\\Jeffrey\\Desktop\\Science Project 2014-2015\\UI\\2014-ScienceFairUI\\20141123SoundSearch230\\album-artwork"));
        //FileResource resource = new FileResource(new File("/home/mitchell/Documents/album-artwork"));
    }
    
    public VerticalLayout drawSongRPage() {
        VerticalLayout grid = new VerticalLayout();
        grid.setHeight(100, Unit.PERCENTAGE);

        final VerticalLayout inNonGrid = new VerticalLayout();
        inNonGrid.setHeight(100, Unit.PERCENTAGE);
//        inNonGrid.setSpacing(true);
        HorizontalLayout generalInfoContainer = new HorizontalLayout();
        generalInfoContainer.setSpacing(true);
        
        HorizontalLayout albumArtContainer = new HorizontalLayout();
        VerticalLayout generalSongDataContainer = new VerticalLayout();
        VerticalLayout detailedInfoContainer = new VerticalLayout();
        HorizontalLayout mediaControlContainer = new HorizontalLayout();

        grid.addComponent(inNonGrid);
        grid.setComponentAlignment(inNonGrid, Alignment.TOP_CENTER);
        inNonGrid.setStyleName("overlaybackground");
        inNonGrid.setWidth(1024, Unit.PIXELS);
        inNonGrid.addComponent(generalInfoContainer);
        generalInfoContainer.addComponent(albumArtContainer);
        generalInfoContainer.addComponent(generalSongDataContainer);
        inNonGrid.addComponent(detailedInfoContainer);
        
        VerticalLayout whiteContainerPanel = new VerticalLayout();
        whiteContainerPanel.setStyleName("whitebackground");
        detailedInfoContainer.addComponent(whiteContainerPanel);
        detailedInfoContainer.setComponentAlignment(whiteContainerPanel, Alignment.TOP_CENTER);

        
        ////////////////////////////////////////////////////////////////////////
//      LABELS
        //Song Title
        Label songtitle = new Label(WordUtils.capitalize(artist));
        //Artist of Song
        Label songartist = new Label(WordUtils.capitalize(title));
        //Year Song was Released
        Label songyear = new Label(String.valueOf(year));
        //Length of Song
        Label songlength = new Label(Utilities.formatTime(length));
        //Song Genre
        Label songgenre = new Label(genre);    
        //Link to Lyrics
        Link lyricslink = new Link("Song Lyrics",new ExternalResource("http://www.azlyrics.com/lyrics/maroon5/onemorenight.html"));
        
        songtitle.setStyleName("coloredwhitefontlabel");
        songartist.setStyleName("coloredwhitefontlabel");
        songyear.setStyleName("coloredwhitefontlabel");
        songlength.setStyleName("coloredwhitefontlabel");
        songgenre.setStyleName("coloredwhitefontlabel");
        

        ////////////////////////////////////////////////////////////////////////        
//      Waveform Graph Image
        Label waveformtitle = new Label("Waveform Readings");
        
        waveformtitle.setStyleName("coloredwhitefontlabel");
        
        final Wavesurfer myWavesurfer = new Wavesurfer();
        myWavesurfer.setHeight(200, Unit.PIXELS);
        myWavesurfer.setWidth(1024, Unit.PIXELS);
        myWavesurfer.loadPlayFile(playThisFile);
        myWavesurfer.loadRegions(title, artist);
      
        ////////////////////////////////////////////////////////////////////////
//      Album Image
        //Image albumimage = new Image("",resource);
        if(albumImage!=null){
            albumImage.setWidth(200, Unit.PIXELS);
            albumImage.setHeight(200, Unit.PIXELS);
            albumArtContainer.addComponent(albumImage);
            albumImage.setStyleName("standardmarginleft");
            albumImage.setStyleName("standardmarginright");
            albumImage.setStyleName("standardmargintop");
            albumImage.setStyleName("standardmarginbottom");
        }
        
        ////////////////////////////////////////////////////////////////////////

        final Button toggleMute = new Button("Mute");
        final Button toggleStopReset = new Button("Reset");
        final Button togglePlayPause = new Button("Play");
        
        final Button likeButton = new Button("Like");
        final Button dislikeButton = new Button("Dislike");
        
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
        
        //if the like button is pressed
        likeButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                DatabaseAccess.updateRank(MyVaadinUI.con,title,artist,1);
                MyVaadinUI.likedResultPage.addEntry(title, artist, album, genre, length, year, mood);
                likeButton.setEnabled(false);
                dislikeButton.setEnabled(false);
            }
        });
        
        //if the dislike button is pressed
        dislikeButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                DatabaseAccess.updateRank(MyVaadinUI.con,title,artist,-1);
                likeButton.setEnabled(false);
                dislikeButton.setEnabled(false);
            }
        });
        
        Label volume = new Label("Volume"); 
        volume.setStyleName("coloredwhitefontlabel");
        
        final Slider volumeLevelSlider = new Slider(0, 100);
        try {
            volumeLevelSlider.setValue(100.0);
        } catch (ValueOutOfBoundsException e) {
        }
        
        Label speed = new Label("Playback Speed");
        
        speed.setStyleName("coloredwhitefontlabel");
        
        final Slider speedLevelSlider = new Slider(-99,100); //can't go to -100 otherwise playback drop to 0% speed (but the player will go back to normal speed)
        try {
            speedLevelSlider.setValue(0.0);
        } catch (ValueOutOfBoundsException e) {
        }
       
        // Handles volume slider changes
        volumeLevelSlider.addValueChangeListener(
            new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    double value = volumeLevelSlider.getValue() / 100;
                    myWavesurfer.volumeSetter(value);
                }
        });
        
        //Handles speed slider changes
        speedLevelSlider.addValueChangeListener(
            new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    double value = (speedLevelSlider.getValue()+100)/100;
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
        
        lyricsDisplayed.setStyleName("coloredwhitefontcaption");
        lyricsDisplayed.setValue(lyricsText);
        lyricsDisplayed.setHeight(300, Unit.PIXELS);
        lyricsDisplayed.setWidth(600, Unit.PIXELS);
        
        generalSongDataContainer.addComponent(songtitle);
        generalSongDataContainer.addComponent(songartist);
        generalSongDataContainer.addComponent(songyear);
        generalSongDataContainer.addComponent(songlength);
        generalSongDataContainer.addComponent(songgenre);
        detailedInfoContainer.addComponent(waveformtitle);
        whiteContainerPanel.addComponent(myWavesurfer);   
        detailedInfoContainer.addComponent(mediaControlContainer);
        mediaControlContainer.addComponent(togglePlayPause);
        mediaControlContainer.addComponent(toggleMute);
        mediaControlContainer.addComponent(toggleStopReset);
        mediaControlContainer.addComponent(volume);
        mediaControlContainer.addComponent(volumeLevelSlider);
        mediaControlContainer.addComponent(speed);
        mediaControlContainer.addComponent(speedLevelSlider);
        mediaControlContainer.addComponent(likeButton);
        mediaControlContainer.addComponent(dislikeButton);
        mediaControlContainer.setComponentAlignment(volume, Alignment.MIDDLE_LEFT);
        mediaControlContainer.setComponentAlignment(speed, Alignment.MIDDLE_LEFT);
        mediaControlContainer.setSpacing(true);
        detailedInfoContainer.addComponent(lyricsDisplayed);
        
        return grid;
    }
}
