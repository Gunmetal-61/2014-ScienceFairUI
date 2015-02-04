package com.mycompany.soundsearch230;

import Wavesurfer.Wavesurfer;
import MyComponent.MyComponent;
import Database.DBRow;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import static com.vaadin.server.Sizeable.UNITS_PIXELS;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Audio;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import java.io.File;
import java.sql.SQLException;
import Database.DatabaseAccess;
import Wavesurfer.Wavesurfer;
import java.io.BufferedInputStream;  
import java.io.File;
import java.io.FileInputStream;  
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


@Theme("mytheme")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI
{
    public static DBRow[] result = new DBRow[100];
    static DatabaseAccess dba = new DatabaseAccess(); 
    static Connection con = dba.startconnection("orcl");
    public static SearchResultPage searchResultPage;
         
    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class, widgetset = "com.mycompany.soundsearch230.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout overlord = new VerticalLayout();
        final HorizontalLayout toolbar = new HorizontalLayout();
        final TabSheet primary = new TabSheet();
        setContent(overlord);
        overlord.addComponent(toolbar);

        
        
        
        final TextField generalSearchBox = new TextField();
        generalSearchBox.setDescription("Search for a Song");
                
        Button commenceSearchButton = new Button("Search");
        Label siteLogo = new Label("SoundSearch");
        

        toolbar.addComponent(siteLogo);
        toolbar.addComponent(generalSearchBox);
        toolbar.addComponent(commenceSearchButton);
        

        
        
        ////////////////////////////////////////////////////////////////////////        
//      TAB 1: Home Page
        HomePage homePage = new HomePage();
        AbsoluteLayout Homage = homePage.drawHomePage();
        
        
        
        
        ////////////////////////////////////////////////////////////////////////
//      TAB 2: Search Results Page        
        searchResultPage = new SearchResultPage(primary);
        final AbsoluteLayout SeaRPage = searchResultPage.drawSearchRPage();
        

        commenceSearchButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                searchResultPage.resultTable.removeAllItems();
                String generalq = generalSearchBox.getValue();
                
                if(!generalq.equals("")){
                    result = dba.getSearchResults(con,generalq,AdvancedSearchPage.ASPmood,AdvancedSearchPage.ASPseconds,AdvancedSearchPage.ASPsongText,AdvancedSearchPage.ASPartistText,AdvancedSearchPage.ASPsubMood);

                    for(int i = 0; i<100; i++){
                        if(result[i]==null){
                        }else{
                            String moodconvert = Integer.toString(result[i].mood);
//                            searchResultPage.resultTable.addItem(new Object[]{result[i].name, result[i].artist, "Top Hits", "", "", moodconvert, myWavesurfer}, i);
                            searchResultPage.resultTable.addItem(new Object[]{result[i].name, result[i].artist, "Top Hits", "", "", moodconvert}, i);

                            
                            System.out.println(i + ": " + result[i].name);
                        }    
                        
                    }
                    primary.setSelectedTab(SeaRPage);
                }else{
                    
                }
            }
        });
 

              
        ////////////////////////////////////////////////////////////////////////
//      TAB 3: Advanced Search Page
        AdvancedSearchPage advancedSearchPage = new AdvancedSearchPage(primary, SeaRPage);
        AbsoluteLayout AdvSPage = advancedSearchPage.drawAdvancedSPage();

        
                
        ////////////////////////////////////////////////////////////////////////        
//      TAB 4: Song Results Page        
//      Code not here or instantiated here, please see SearchResultPage.java and
//      the bulk of the code which is in SongResultPages.java .
////////////////////////////////////////////////////////////////////////////////

        
   
        
        
        

        
        primary.addTab(Homage, "Home");

        primary.addTab(AdvSPage, "Advanced Search");
        
        primary.addTab(SeaRPage);
        overlord.addComponent(primary);

        
   }
}