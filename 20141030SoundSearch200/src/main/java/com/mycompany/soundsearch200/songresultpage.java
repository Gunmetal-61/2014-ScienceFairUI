/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.soundsearch200;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import static com.vaadin.server.Sizeable.UNITS_PIXELS;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Audio;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.io.File;

/**
 *
 * @author Mitchell
 */
public class songresultpage {
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);
        Panel board = new Panel();
        board.setWidth(960, UNITS_PIXELS);
        board.setHeight(1080, UNITS_PIXELS);
        layout.addComponent(board);
        //LABELS
        //Song Title
        Label songtitle = new Label("One More Night");
        //Artist of Song
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

        
        //
        //Vertical Layout Setting for Panel board
        final VerticalLayout boardlayout = new VerticalLayout();
        board.setContent(boardlayout);
        
        //Album Image
        FileResource resource = new FileResource(new File("C:\\Users\\Mitchell\\Documents\\NetBeansProjects\\20141030SoundSearch200\\src\\main\\webapp\\WEB-INF\\images\\Maroon-5-Overexposed-Cover.jpg"));
        
        Image albumimage = new Image("",resource);
        albumimage.setWidth(200, UNITS_PIXELS);
        albumimage.setHeight(200, UNITS_PIXELS);
        
        //Waveform Graph Image
        FileResource waveformf = new FileResource(new File("C:\\Users\\Mitchell\\Documents\\NetBeansProjects\\20141030SoundSearch200\\src\\main\\webapp\\WEB-INF\\images\\stock graph image jpeg.jpg"));
        
        Image waveform = new Image("",waveformf);
        waveform.setWidth(900, UNITS_PIXELS);
        waveform.setHeight(100, UNITS_PIXELS);
        
        //OLD USELESS STUFF (FOR NOW AT LEAST)
        TextField name = new TextField("Name");
        
        Button button = new Button("Click Me");
        button.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                boardlayout.addComponent(new Label("Thank you for clicking"));
            }
        });
        
        //Play the Song (Audio)
        FileResource songfile = new FileResource(new File("C:\\Users\\Mitchell\\Documents\\NetBeansProjects\\20141030SoundSearch200\\src\\main\\webapp\\WEB-INF\\audio\\13 Immortal Empire.wav"));
        Audio song = new Audio("",songfile);
        //song.setAutoplay(true);
        //song.setShowControls(true);
    //    song.
        
        //ADD TO VERTICAL LAYOUT WITHIN BOARD
        boardlayout.addComponent(name);
        boardlayout.addComponent(button);
        boardlayout.addComponent(albumimage);
        boardlayout.addComponent(songtitle);
        boardlayout.addComponent(songartist);
        boardlayout.addComponent(songyear);
        boardlayout.addComponent(songlength);
        boardlayout.addComponent(songgenre);
        boardlayout.addComponent(lyricslink);
        boardlayout.addComponent(waveform);
        boardlayout.addComponent(song);
        
    }

    private void setContent(VerticalLayout layout) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
