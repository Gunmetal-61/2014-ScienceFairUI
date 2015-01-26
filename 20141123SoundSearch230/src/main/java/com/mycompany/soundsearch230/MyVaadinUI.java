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
        
        
        Label derp = new Label("sdfhuwhe");
        
        MyComponent myComponent = new MyComponent();
        overlord.addComponent(myComponent);
        myComponent.jsMethod();
        
//        final Wavesurfer myWavesurfer = new Wavesurfer();
//        myWavesurfer.setHeight(130, UNITS_PIXELS);
//        myWavesurfer.setWidth(900, UNITS_PIXELS);
//        overlord.addComponent(myWavesurfer);

        /////////////////////////       
        final String[] anArray;
        
        anArray = new String[10];
        anArray[0] = "";
        anArray[1] = "derper";
        anArray[2] = "derpest";
        anArray[3] = "deep";
        anArray[4] = "deeper";
        anArray[5] = "deepest";
        anArray[6] = "so deep Adele is rolling in it";
        anArray[7] = "I need coffee.";
        anArray[8] = "nnneed cofffeee";
        anArray[9] = "zzzzzzzzzzzzzzz...";
       
        Button button = new Button("Search");
        
        
        final TextField generalSearchBox = new TextField();
        generalSearchBox.setDescription("Search for a Song");
        
        
        
        
        
        /////////////////////////
        setContent(overlord);
        overlord.addComponent(toolbar);
        toolbar.addComponent(generalSearchBox);
       // overlord.addComponent(table);
        toolbar.addComponent(button);
        overlord.addComponent(primary);
        
        ////////////////////////////////////////////////////////////////////////        
//      TAB 1: Home Page
        HomePage homePage = new HomePage();
        AbsoluteLayout Homage = homePage.drawHomePage();
        primary.addTab(Homage, "Home");
        
        
        
        ////////////////////////////////////////////////////////////////////////        
//      Tab #2
        
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        
//        Button button = new Button("Click Me");
//        button.addClickListener(new Button.ClickListener() {
//            public void buttonClick(ClickEvent event) {
//                layout.addComponent(new Label("Thank you for clicking"));
//            }
//        });
//        layout.addComponent(button);

        
        

        
//        CssLayout layout = new CssLayout() {
////            @Override
//            protected String getCss(Component c) {
//                if (c instanceof Label) {
//                    // Color the boxes with random colors
//                    int rgb = (int) (Math.random()*(1<<24));
//                    return "background: #" + Integer.toHexString(rgb);
//                }
//                return null;
//            }
//        };
//        layout.setWidth("400px"); // Causes to wrap the contents
//
//        // Add boxes of various sizes
//        for (int i=0; i<40; i++) {
//            Label box = new Label("&nbsp;", ContentMode.HTML);
//            box.addStyleName("flowbox");
//            box.setWidth((float) Math.random()*50,
//                         Sizeable.UNITS_PIXELS);
//            box.setHeight((float) Math.random()*50,
//                          Sizeable.UNITS_PIXELS);
//            layout.addComponent(box);
//        }
        primary.addTab(layout, "Example");
        
        ////////////////////////////////////////////////////////////////////////        
//      TAB 2: Song Results Page
        
             
//        
//        Button startSearchButton = new Button("test button");
//        SonRPage.addComponent(startSearchButton);
        
        ////////////////////////////////////////////////////////////////////////
//      TAB 3: Search Results Page        
        searchResultPage = new SearchResultPage(primary);
        final AbsoluteLayout SeaRPage = searchResultPage.drawSearchRPage();
        primary.addTab(SeaRPage, "Search Results");  
//        SeaRPage.setVisible(false);
              
        ////////////////////////////////////////////////////////////////////////
//      TAB 4: Advanced Search Page
        AdvancedSearchPage advancedSearchPage = new AdvancedSearchPage(primary, SeaRPage);
        AbsoluteLayout AdvSPage = advancedSearchPage.drawAdvancedSPage();
        primary.addTab(AdvSPage, "Advanced Search");
 
        Button startSearchButton2 = new Button("test button");
        AdvSPage.addComponent(startSearchButton2);

        
////////////////////////////////////////////////////////////////////////////////
        
        
        
        
        
        
        
        
        
//        static File[] allwavfiles;
//        static File songfile = null;
//        Clip clip = null;
//        boolean x = true;
//        Thread thread = new Thread(new thread1());
//        long playloc = 0;
//        static DatabaseAccess dba = new DatabaseAccess();

//        static AudioWaveformCreator awc = new AudioWaveformCreator();
//        DBRow[] result = new DBRow[25];
//        int[] subsong = new int[31];
//        String lyricsr = "";
        

               
        

        
        
        
        
        
        
        
        
        
        
        button.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                searchResultPage.resultTable.removeAllItems();
                String generalq = generalSearchBox.getValue();
                
                if(!generalq.equals("")){
                    result = dba.getSearchResults(con,generalq,AdvancedSearchPage.ASPmood,AdvancedSearchPage.ASPseconds,AdvancedSearchPage.ASPsongText,AdvancedSearchPage.ASPartistText,AdvancedSearchPage.ASPsubMood);

                    for(int i = 0; i<100; i++){
    //                    if(generalq.equals("prayer")){
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
        
        
        
        
   }
}