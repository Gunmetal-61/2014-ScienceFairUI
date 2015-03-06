/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.soundsearch230;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author Jeffrey
 */
public class LikedResultPage {
    public static String nameIdentifier = "";
    public static String artistIdentifier = "";
    public static String genre = "";
    public static String album = "";
    public static int length = 0;
    public static int year = 0;
    public static int mood = -1;
    
    public Table likedTable = new Table("Search Results");
    
    TabSheet other;
    
    public LikedResultPage(TabSheet primary) {
        other = primary;
    }
    
    public AbsoluteLayout drawLikedPage(){
////////////////////////////////////////////////////////////////////////////////
        //BASE PANELS/LAYOUTS: 
//        CssLayout grid = new AbsoluteLayout();
        AbsoluteLayout grid = new AbsoluteLayout();
        grid.setHeight(1200, Unit.PIXELS);
        grid.setWidth("100%");
        
        AbsoluteLayout ingrid = new AbsoluteLayout();
        ingrid.setHeight(1200, Unit.PIXELS);
        ingrid.setWidth("100%");
        
        //primary.addComponent(grid);

        grid.addComponent(ingrid, "left: 15px; top: 0px;");

////////////////////////////////////////////////////////////////////////////////        
        //SIDEBAR
        VerticalLayout sidebar = new VerticalLayout();
        sidebar.setHeight(1200, Unit.PIXELS);
        sidebar.setWidth(200, Unit.PIXELS);
        sidebar.setSpacing(true);
        //sidebar.addStyleName(sidebur);
        
        ingrid.addComponent(sidebar, "left: 0px; top: 0px;");

////////////////////////////////////////////////////////////////////////////////
//      Liked Results Table

        //expand table
        likedTable.setSizeFull();
        
        //add columns
        likedTable.addContainerProperty("Song Name", String.class, null);
        likedTable.addContainerProperty("Artist", String.class, null);
        likedTable.addContainerProperty("Album", String.class, null);
        likedTable.addContainerProperty("Mood", String.class, null);
        likedTable.addContainerProperty("Genre", String.class, null);
        likedTable.addContainerProperty("Length", String.class, null);
        likedTable.addContainerProperty("Year", String.class, null);
        
        //adjust their relative widths
        likedTable.setColumnExpandRatio("Song Name",3);
        likedTable.setColumnExpandRatio("Artist",3);
        likedTable.setColumnExpandRatio("Album",3);
        likedTable.setColumnExpandRatio("Mood",1);
        likedTable.setColumnExpandRatio("Genre",2);
        likedTable.setColumnExpandRatio("Length",1);
        likedTable.setColumnExpandRatio("Year",1);
        
        //when a row in the table is clicked on
        likedTable.addItemClickListener(new ItemClickEvent.ItemClickListener () {
            @Override
            public void itemClick(ItemClickEvent event) {
                Object currentItemId = event.getItemId();
                
                nameIdentifier = likedTable.getItem(currentItemId).getItemProperty("Song Name").getValue().toString().toLowerCase(); //make title lowercase again
                artistIdentifier = likedTable.getItem(currentItemId).getItemProperty("Artist").getValue().toString();
                //unconvert the formatted time [MM:SS] back to seconds
                String formattedLength = likedTable.getItem(currentItemId).getItemProperty("Length").getValue().toString();  
                int index = formattedLength.indexOf(":");
                int seconds = Integer.parseInt(formattedLength.substring(index+1)) + Integer.parseInt(formattedLength.substring(0,index))*60;
                length = seconds;      
                year = Integer.valueOf(likedTable.getItem(currentItemId).getItemProperty("Year").getValue().toString());
                album = likedTable.getItem(currentItemId).getItemProperty("Artist").getValue().toString();
                genre = likedTable.getItem(currentItemId).getItemProperty("Genre").getValue().toString();
                mood = Integer.valueOf(likedTable.getItem(currentItemId).getItemProperty("Mood").getValue().toString());

                SongResultPages songResultPage2 = new SongResultPages(nameIdentifier,artistIdentifier,album,genre,length,year,mood);
                VerticalLayout SonRPage = songResultPage2.drawSongRPage();
                other.addTab(SonRPage);
                other.getTab(SonRPage).setCaption(WordUtils.capitalize(nameIdentifier)); //label tab with song name
                other.getTab(SonRPage).setClosable(true); //allow user to close tabs
                other.setSelectedTab(SonRPage);
            }
        });
        
        ingrid.addComponent(likedTable, "left: 200px; top: 0px;");

        return grid;
    }
    
    /**
     * Add a liked song to the liked table
     * 
     * @param name
     * @param artist
     * @param album
     * @param genre
     * @param length
     * @param year
     * @param mood 
     */
    public void addEntry(String name, String artist, String album, String genre, int length, int year, int mood){
        likedTable.addItem(new Object[]{
            WordUtils.capitalize(name), 
            artist, 
            album, 
            Integer.toString(mood), 
            genre,
            Utilities.formatTime(length),
            (year==0) ? "" : String.valueOf(year)},
            (name+artist).hashCode()); //make id pretty much unique to each song
    }
}