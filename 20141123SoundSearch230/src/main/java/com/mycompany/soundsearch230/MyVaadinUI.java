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
import IDEPull.IDEWrite;
import SearchSystem.MoodCentral;
import Wavesurfer.Wavesurfer;
import static com.mycompany.soundsearch230.SearchResultPage.nameIdentifier;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.TabSheet.CloseHandler;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.Tab;
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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import org.apache.commons.lang3.text.WordUtils;


@Theme("mytheme")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI
{
    public static DBRow[] result = new DBRow[100];
    static DatabaseAccess dba = new DatabaseAccess(); 
    public static Connection con = dba.startconnection("orcl");
    public static SearchResultPage searchResultPage;
         
    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class, widgetset = "com.mycompany.soundsearch230.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout overlord = new VerticalLayout();
        final HorizontalLayout toolbar = new HorizontalLayout();
        final TabSheet tabs = new TabSheet();
        getPage().setTitle("Aura");
        setContent(overlord);
        overlord.addComponent(toolbar);

    
        final TextField generalSearchBox = new TextField();
        generalSearchBox.setDescription("Search for a Song");
        generalSearchBox.setWidth(200, Unit.PIXELS);
             
        final Button commenceSearchButton = new Button("Search");
        Label siteLogo = new Label("Aura");
        toolbar.setMargin(new MarginInfo(false, true, false, true));
        toolbar.addComponent(siteLogo);
        toolbar.setComponentAlignment(siteLogo, Alignment.MIDDLE_LEFT);
        toolbar.addComponent(generalSearchBox);
        toolbar.addComponent(commenceSearchButton);
        

  
        ////////////////////////////////////////////////////////////////////////        
//      TAB 1: Home Page
        HomePage homePage = new HomePage();
        AbsoluteLayout Homage = homePage.drawHomePage();
        
        
        
        
        ////////////////////////////////////////////////////////////////////////
//      TAB 2: Search Results Page        
        searchResultPage = new SearchResultPage(tabs);
        final AbsoluteLayout SeaRPage = searchResultPage.drawSearchRPage();
        
        ////////////////////////////////////////////////////////////////////////
//             
        //enter key handlers from: https://vaadin.com/forum/#!/thread/77601/8315545
        //if enter button is pressed
        generalSearchBox.addFocusListener(new FocusListener() {
            //What will be done when your Text Field is active is Enter Key is pressed
            public void focus(final FocusEvent event) {
                //Whatever you want to do on Enter Key pressed
                commenceSearchButton.setClickShortcut(KeyCode.ENTER);
            }
        });    
        generalSearchBox.addBlurListener(new BlurListener() {
             // To control waht happens when your Text Field looses focus
            @Override
            public void blur(final BlurEvent event) {
                commenceSearchButton.removeClickShortcut();
            }
        });
        
        
        
        //if search button is pressed
        commenceSearchButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                searchResultPage.resultTable.removeAllItems();
                
                
                String generalq = generalSearchBox.getValue();
                int counter = 0; //id of row
                MoodCentral translateMoodWords = new MoodCentral();
                Map theNumberBase = translateMoodWords.MoodKey();
                if (theNumberBase.containsKey(generalq)) { //if there was a mood word found
                    int[] theConvertedKey = {(Integer) theNumberBase.get(generalq)};
                    System.out.println("Integer:" + theConvertedKey[0]);
                    
                    if(!generalq.equals("")){ //if not empty
                        System.out.println("Integeref:" + theConvertedKey[0]);
                        result = dba.getSearchResults(con, "", theConvertedKey, 0, "", "", "", "", ""); //get mood results
                        System.out.println("Length of mood results:" + result.length);
                        for(counter = 0; counter<result.length; counter++){
                            if(result[counter]==null){
                                break;
                            }else{
                                String moodconvert = Integer.toString(result[counter].mood);
                                //searchResultPage.resultTable.addItem(new Object[]{result[i].name, result[i].artist, "Top Hits", "", "", moodconvert, myWavesurfer}, i);
                                searchResultPage.resultTable.addItem(new Object[]{
                                    WordUtils.capitalize(result[counter].name), 
                                    result[counter].artist, 
                                    result[counter].album, 
                                    moodconvert, 
                                    result[counter].genre, 
                                    SongResultPages.formatTime(result[counter].length),
                                    (MyVaadinUI.result[counter].year==0) ? "" : String.valueOf(MyVaadinUI.result[counter].year)}, counter);              
                                System.out.println(counter + ": " + result[counter].name);
                            }                
                        }
                    }
                }
                System.out.println(generalq);
                if(!generalq.equals("")){ //if not empty
                    int[] divertMood = {0,1,2,3,4,5,6,7};
                    result = dba.getSearchResults(con, generalq, divertMood, 0, "", "", "", "", ""); //get normal text results
                    System.out.println(counter);
                    for(int secondaryCounter = 0; secondaryCounter<result.length; secondaryCounter++){
                        if(result[secondaryCounter]==null){
                            break;
                        }else{
                            String moodconvert = Integer.toString(result[secondaryCounter].mood);
                            searchResultPage.resultTable.addItem(new Object[]{
                                WordUtils.capitalize(result[secondaryCounter].name), 
                                result[secondaryCounter].artist, 
                                result[secondaryCounter].album, 
                                moodconvert, 
                                result[secondaryCounter].genre, 
                                SongResultPages.formatTime(result[secondaryCounter].length),
                                (MyVaadinUI.result[secondaryCounter].year==0) ? "" : String.valueOf(MyVaadinUI.result[secondaryCounter].year)}, counter);               
                            System.out.println(counter + ": " + result[secondaryCounter].name);
                        }
                        counter++; //continue to add the other results to the table
                    }
                    tabs.setSelectedTab(SeaRPage);
                }        
            }
        });
 
        
              
        ////////////////////////////////////////////////////////////////////////
//      TAB 3: Advanced Search Page
        AdvancedSearchPage advancedSearchPage = new AdvancedSearchPage(tabs, SeaRPage);
        AbsoluteLayout AdvSPage = advancedSearchPage.drawAdvancedSPage();


        IDEWrite Page = new IDEWrite();
        //File[] instantFiles = Page.listFiles();
        String convert = null;
        
        //Page.fixSubsong(con);
//        System.out.println("Number of files = " + instantFiles.length);
//        
//        for(int i = 0; i<instantFiles.length; i++){
//            System.out.println("1:" + instantFiles[i].toString());
//            convert = instantFiles[i].toString();
////            System.out.println(convert);
//            Page.writeArtist(con, convert);
////            System.out.println("ehgwheoig" + Page.writeAlbum(con, convert));
//        }
 
        ////////////////////////////////////////////////////////////////////////        
//      TAB 4: Song Results Page        
//      Code not here or instantiated here, please see SearchResultPage.java and
//      the bulk of the code which is in SongResultPages.java .
////////////////////////////////////////////////////////////////////////////////


        tabs.addTab(Homage, "Home");
        //tabs.addTab(AdvSPage, "Advanced Search");   
        tabs.addTab(SeaRPage, "Search Results"); 
        
        tabs.setCloseHandler(new CloseHandler(){
            @Override
            public void onTabClose(TabSheet tabsheet, Component tabContent){
                Tab tab = tabsheet.getTab(tabContent);
                tabsheet.removeTab(tab);
            }
        });
        
        tabs.addFocusListener(new FocusListener() {
            @Override
            public void focus(final FocusEvent event) {
                
            }
        });    
        tabs.addBlurListener(new BlurListener() {

            public void blur(final BlurEvent event) {
                
            }
        });
        
        overlord.addComponent(tabs);
   }
}