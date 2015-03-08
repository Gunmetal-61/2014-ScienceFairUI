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
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.ThemeResource;
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
    Image albumImage = new Image();
    
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
    }
    
    public VerticalLayout drawSongRPage() {
        ////////////////////////////////////////////////////////////////////////
//      GENERAL LAYOUTS
        VerticalLayout mainVerticalLayout = new VerticalLayout();
        final VerticalLayout centeredVerticalLayout = new VerticalLayout();
        
        
        //[generalInfoContainer] LAYOUTS
        HorizontalLayout generalInfoContainer = new HorizontalLayout();
        
        VerticalLayout albumArtContainer = new VerticalLayout();
        
        VerticalLayout generalSongDataContainerHead = new VerticalLayout();
        VerticalLayout generalSongDataContainerA = new VerticalLayout();
        HorizontalLayout generalSongDataContainerBC = new HorizontalLayout();
        VerticalLayout generalSongDataContainerB = new VerticalLayout();
        VerticalLayout generalSongDataContainerC = new VerticalLayout();
        
        generalInfoContainer.addStyleName("gensongcontainer-margin");
        generalSongDataContainerA.setWidth(700, Unit.PIXELS);
        generalSongDataContainerB.setWidth(150, Unit.PIXELS);
        generalSongDataContainerC.setWidth(150, Unit.PIXELS);
        
        
        //[detailedInfoContainer] LAYOUTS
        VerticalLayout detailedInfoContainer = new VerticalLayout();
        HorizontalLayout mediaControlContainer = new HorizontalLayout();
        mediaControlContainer.addStyleName("medconcontainer");

        
        
        mainVerticalLayout.addComponent(centeredVerticalLayout);
        mainVerticalLayout.setComponentAlignment(centeredVerticalLayout, Alignment.TOP_CENTER);
        centeredVerticalLayout.setStyleName("overlaybackground");
        centeredVerticalLayout.setWidth(980, Unit.PIXELS);
        centeredVerticalLayout.addComponent(generalInfoContainer);
        generalInfoContainer.addComponent(albumArtContainer);
        generalInfoContainer.addComponent(generalSongDataContainerHead);
        generalSongDataContainerHead.addComponent(generalSongDataContainerA);
        generalSongDataContainerHead.addComponent(generalSongDataContainerBC);
        generalSongDataContainerBC.addComponent(generalSongDataContainerB);
        generalSongDataContainerBC.addComponent(generalSongDataContainerC);
        centeredVerticalLayout.addComponent(detailedInfoContainer);
        
        VerticalLayout whiteContainerPanel = new VerticalLayout();
        whiteContainerPanel.setStyleName("whitebackground");
        
        
        HorizontalLayout compoundInfoContainer = new HorizontalLayout();
        VerticalLayout lyricsInfo = new VerticalLayout();
        lyricsInfo.setStyleName("generalmargin");
        VerticalLayout moodKeyInfo = new VerticalLayout();
        moodKeyInfo.setStyleName("generalmargin");
        HorizontalLayout colorRow0 = new HorizontalLayout();
        HorizontalLayout colorRow1 = new HorizontalLayout();
        HorizontalLayout colorRow2 = new HorizontalLayout();
        HorizontalLayout colorRow3 = new HorizontalLayout();
        HorizontalLayout colorRow4 = new HorizontalLayout();
        HorizontalLayout colorRow5 = new HorizontalLayout();
        HorizontalLayout colorRow6 = new HorizontalLayout();
        HorizontalLayout colorRow7 = new HorizontalLayout();
        
        
        lyricsInfo.setWidth(600, Unit.PIXELS);
        moodKeyInfo.setWidth(318, Unit.PIXELS);
        
        

        
        ////////////////////////////////////////////////////////////////////////
//      LABELS
        //Song Title
        Label songtitle = new Label(WordUtils.capitalize(title));
        //Artist of Song
        Label artistMarker = new Label("ARTIST");
        Label songartist = new Label(WordUtils.capitalize(artist));
        //Year Song was Released
        Label yearMarker = new Label("YEAR");
        Label songyear = new Label(String.valueOf(year));
        //Length of Song
        Label lengthMarker = new Label("LENGTH");
        Label songlength = new Label(Utilities.formatTime(length));
        //Song Genre
        Label genreMarker = new Label("GENRE");
        Label songgenre = new Label(genre);    
        //Link to Lyrics
        Link lyricslink = new Link("Song Lyrics",new ExternalResource("http://www.azlyrics.com/lyrics/maroon5/onemorenight.html"));
        
        songtitle.addStyleName("geninfocontainer-generalmargin");
        songtitle.addStyleName("geninfocontainer-songnametext");
        artistMarker.addStyleName("geninfocontainer-generalmargin");
        artistMarker.addStyleName("geninfocontainer-generalmarkers");
        
        songartist.addStyleName("geninfocontainer-generalmargin");
        songartist.addStyleName("geninfocontainer-generaltext");
        
        yearMarker.addStyleName("geninfocontainer-generalmargin");
        yearMarker.addStyleName("geninfocontainer-generalmarkers");
        
        songyear.addStyleName("geninfocontainer-generalmargin");
        songyear.addStyleName("geninfocontainer-generaltext");
        
        lengthMarker.addStyleName("geninfocontainer-generalmargin");
        lengthMarker.addStyleName("geninfocontainer-generalmarkers");
        
        songlength.addStyleName("geninfocontainer-generalmargin");
        songlength.addStyleName("geninfocontainer-generaltext");
        
        genreMarker.addStyleName("geninfocontainer-generalmargin");
        genreMarker.addStyleName("geninfocontainer-generalmarkers");
        
        songgenre.addStyleName("geninfocontainer-generalmargin");
        songgenre.addStyleName("geninfocontainer-generaltext");
        

        

        ////////////////////////////////////////////////////////////////////////        
//      Waveform Graph Image
        Label waveformTitle = new Label("WAVEFORM PLAYER");
        
        waveformTitle.addStyleName("geninfocontainer-generalmarkers");
        waveformTitle.addStyleName("geninfocontainer-generalmargin");
        
        final Wavesurfer myWavesurfer = new Wavesurfer();
        myWavesurfer.setHeight(200, Unit.PIXELS);
        myWavesurfer.setWidth(980, Unit.PIXELS);
        myWavesurfer.loadPlayFile(playThisFile);
        myWavesurfer.loadRegions(title, artist);
      
        ////////////////////////////////////////////////////////////////////////
//      Album Image
        //Image albumimage = new Image("",resource);
        if(albumImage!=null){
            albumImage.setWidth(200, Unit.PIXELS);
            albumImage.setHeight(200, Unit.PIXELS);
            albumArtContainer.addComponent(albumImage);
        } else{
//            albumImage = albumImage.
        }
        
        ////////////////////////////////////////////////////////////////////////

        final Button toggleMute = new Button("MUTE");
        final Button toggleStopReset = new Button("RESET");
        final Button togglePlayPause = new Button("PLAY");
        
        final Button likeButton = new Button("LIKE");
        final Button dislikeButton = new Button("DISLIKE");
        
        toggleMute.addStyleName("coloredblackbuttoncaption");
        toggleStopReset.addStyleName("coloredblackbuttoncaption");
        togglePlayPause.addStyleName("coloredblackbuttoncaption");
        likeButton.addStyleName("coloredblackbuttoncaption");
        dislikeButton.addStyleName("coloredblackbuttoncaption");
        
        //mute listener
        toggleMute.addClickListener(new Button.ClickListener() {
            int muteBinaryIndicator = 0;
            public void buttonClick(ClickEvent event) {
                if (muteBinaryIndicator == 0) {
                    myWavesurfer.muteSilence();
                    toggleMute.setCaption("UNMUTE");
                    muteBinaryIndicator = 1;
                } else {
                    myWavesurfer.muteSilence();
                    toggleMute.setCaption("MUTE");
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
                toggleStopReset.setCaption("RESET");
                if (togglePlayPause.getCaption().equals("PLAY")) { //if paused
                    myWavesurfer.playOrPause(true); //play
                    togglePlayPause.setCaption("PAUSE");
                } else { //if playing
                    myWavesurfer.playOrPause(false); //pause
                    togglePlayPause.setCaption("PLAY");
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
        
        Label volume = new Label("VOLUME"); 
        volume.setStyleName("geninfocontainer-generalmarkers");
        
        final Slider volumeLevelSlider = new Slider(0, 100);
        try {
            volumeLevelSlider.setValue(100.0);
        } catch (ValueOutOfBoundsException e) {
        }
        
        Label speed = new Label("PLAYBACK SPEED");
        
        speed.setStyleName("geninfocontainer-generalmarkers");
        
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
        
        TextArea lyricsDisplayed = new TextArea();
        
        lyricsDisplayed.setValue(lyricsText);
        lyricsDisplayed.setHeight(300, Unit.PIXELS);
        lyricsDisplayed.setWidth(600, Unit.PIXELS);
        lyricsDisplayed.addStyleName("lyricsbackground");
        lyricsDisplayed.addStyleName("lyricslabel");
        Label lyricsMarker = new Label("SONG LYRICS");
        lyricsMarker.setStyleName("geninfocontainer-generalmarkers");
    
        Label moodKeyMarker = new Label("MOOD COLOR KEY");
        Resource orangeKey = new ThemeResource("img/moodcolors/Burnt_Orange.jpg");
        Resource yellowKey = new ThemeResource("img/moodcolors/Ripe_Lemon.jpg");
        Resource greenKey = new ThemeResource("img/moodcolors/Eucalyptus.jpg");
        Resource aquaKey = new ThemeResource("img/moodcolors/Light_Sea_Green.jpg");
        Resource blueKey = new ThemeResource("img/moodcolors/Royal_Blue.jpg");
        Resource purpleKey = new ThemeResource("img/moodcolors/Studio.jpg");
        Resource redKey = new ThemeResource("img/moodcolors/Thunderbird.jpg");
        Resource greyKey = new ThemeResource("img/moodcolors/Cascade.jpg");
        
        Image color0 = new Image(null, orangeKey);
        Image color1 = new Image(null, yellowKey);
        Image color2 = new Image(null, greenKey);
        Image color3 = new Image(null, aquaKey);
        Image color4 = new Image(null, blueKey);
        Image color5 = new Image(null, purpleKey);
        Image color6 = new Image(null, redKey);
        Image color7 = new Image(null, greyKey);
        
        color0.setHeight(10, Unit.PIXELS);
        color0.setWidth(10, Unit.PIXELS);
        color1.setHeight(10, Unit.PIXELS);
        color1.setWidth(10, Unit.PIXELS);
        color2.setHeight(10, Unit.PIXELS);
        color2.setWidth(10, Unit.PIXELS);
        color3.setHeight(10, Unit.PIXELS);
        color3.setWidth(10, Unit.PIXELS);
        color4.setHeight(10, Unit.PIXELS);
        color4.setWidth(10, Unit.PIXELS);
        color5.setHeight(10, Unit.PIXELS);
        color5.setWidth(10, Unit.PIXELS);
        color6.setHeight(10, Unit.PIXELS);
        color6.setWidth(10, Unit.PIXELS);
        color7.setHeight(10, Unit.PIXELS);
        color7.setWidth(10, Unit.PIXELS);
        
        Label c0Caption = new Label("0: INSPIRATION, DESIRE, LOVE");
        Label c1Caption = new Label("1: FASCINATION, ADMIRATION, JOYFULNESS");
        Label c2Caption = new Label("2: SATISFACTION, SOFTENED, RELAXED");
        Label c3Caption = new Label("3: AWAITING, DEFERENT, CALM");
        Label c4Caption = new Label("4: BOREDOM, SADNESS, ISOLATION");
        Label c5Caption = new Label("5: DISAPPOINTMENT, CONTEMPT, JEALOUSY");
        Label c6Caption = new Label("6: IRRITATION, DISGUST, ALARM");
        Label c7Caption = new Label("7: ASTONISHMENT, EAGERNESS, CURIOUSITY");
        
        moodKeyMarker.setStyleName("geninfocontainer-generalmarkers");
        c0Caption.setStyleName("geninfocontainer-generalmarkers");
        c1Caption.setStyleName("geninfocontainer-generalmarkers");
        c2Caption.setStyleName("geninfocontainer-generalmarkers");
        c3Caption.setStyleName("geninfocontainer-generalmarkers");
        c4Caption.setStyleName("geninfocontainer-generalmarkers");
        c5Caption.setStyleName("geninfocontainer-generalmarkers");
        c6Caption.setStyleName("geninfocontainer-generalmarkers");
        c7Caption.setStyleName("geninfocontainer-generalmarkers");
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        generalSongDataContainerA.addComponent(songtitle);     
        generalSongDataContainerB.addComponent(artistMarker);
        generalSongDataContainerB.addComponent(songartist);
        generalSongDataContainerB.addComponent(yearMarker);
        generalSongDataContainerB.addComponent(songyear);
        generalSongDataContainerC.addComponent(lengthMarker);
        generalSongDataContainerC.addComponent(songlength);
        generalSongDataContainerC.addComponent(genreMarker);
        generalSongDataContainerC.addComponent(songgenre);
        detailedInfoContainer.addComponent(waveformTitle);
        detailedInfoContainer.addComponent(whiteContainerPanel);
        detailedInfoContainer.setComponentAlignment(whiteContainerPanel, Alignment.TOP_CENTER);
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
        mediaControlContainer.setComponentAlignment(volume, Alignment.MIDDLE_CENTER);
        mediaControlContainer.setComponentAlignment(speed, Alignment.MIDDLE_CENTER);
        mediaControlContainer.setSpacing(true);
        detailedInfoContainer.addComponent(compoundInfoContainer);
        compoundInfoContainer.addComponent(lyricsInfo);
        lyricsInfo.addComponent(lyricsMarker);
        lyricsInfo.addComponent(lyricsDisplayed);
        compoundInfoContainer.addComponent(moodKeyInfo);
        
        moodKeyInfo.addComponent(moodKeyMarker);
        moodKeyInfo.addComponent(colorRow0);
        colorRow0.addComponent(color0);
        colorRow0.addComponent(c0Caption);
        moodKeyInfo.addComponent(colorRow1);
        colorRow1.addComponent(color1);
        colorRow1.addComponent(c1Caption);
        moodKeyInfo.addComponent(colorRow2);
        colorRow2.addComponent(color2);
        colorRow2.addComponent(c2Caption);
        moodKeyInfo.addComponent(colorRow3);
        colorRow3.addComponent(color3);
        colorRow3.addComponent(c3Caption);
        moodKeyInfo.addComponent(colorRow4);
        colorRow4.addComponent(color4);
        colorRow4.addComponent(c4Caption);
        moodKeyInfo.addComponent(colorRow5);
        colorRow5.addComponent(color5);
        colorRow5.addComponent(c5Caption);
        moodKeyInfo.addComponent(colorRow6);
        colorRow6.addComponent(color6);
        colorRow6.addComponent(c6Caption);
        moodKeyInfo.addComponent(colorRow7);
        colorRow7.addComponent(color7);
        colorRow7.addComponent(c7Caption);
        
        colorRow0.setComponentAlignment(color0, Alignment.MIDDLE_LEFT);
        colorRow0.setComponentAlignment(c0Caption, Alignment.MIDDLE_LEFT);
        colorRow1.setComponentAlignment(color1, Alignment.MIDDLE_LEFT);
        colorRow1.setComponentAlignment(c1Caption, Alignment.MIDDLE_LEFT);
        colorRow2.setComponentAlignment(color2, Alignment.MIDDLE_LEFT);
        colorRow2.setComponentAlignment(c2Caption, Alignment.MIDDLE_LEFT);
        colorRow3.setComponentAlignment(color3, Alignment.MIDDLE_LEFT);
        colorRow3.setComponentAlignment(c3Caption, Alignment.MIDDLE_LEFT);
        colorRow4.setComponentAlignment(color4, Alignment.MIDDLE_LEFT);
        colorRow4.setComponentAlignment(c4Caption, Alignment.MIDDLE_LEFT);
        colorRow5.setComponentAlignment(color5, Alignment.MIDDLE_LEFT);
        colorRow5.setComponentAlignment(c5Caption, Alignment.MIDDLE_LEFT);
        colorRow6.setComponentAlignment(color6, Alignment.MIDDLE_LEFT);
        colorRow6.setComponentAlignment(c6Caption, Alignment.MIDDLE_LEFT);
        colorRow7.setComponentAlignment(color7, Alignment.MIDDLE_LEFT);
        colorRow7.setComponentAlignment(c7Caption, Alignment.MIDDLE_LEFT);
        
        colorRow0.setSpacing(true);
        colorRow1.setSpacing(true);
        colorRow2.setSpacing(true);
        colorRow3.setSpacing(true);
        colorRow4.setSpacing(true);
        colorRow5.setSpacing(true);
        colorRow6.setSpacing(true);
        colorRow7.setSpacing(true);
        
        
        
        
        return mainVerticalLayout;
    }
}
