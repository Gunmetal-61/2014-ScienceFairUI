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
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.text.WordUtils;


@Theme("mytheme")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI
{
    public static DBRow[] result = null;
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
                result = null; //clear the results
                searchResultPage.resultTable.removeAllItems(); //clear the table
                
                String generalq = generalSearchBox.getValue();
                int counter = 0; //id of row
                MoodCentral translateMoodWords = new MoodCentral();
                Map theNumberBase = translateMoodWords.MoodKey();
                
                System.out.println("Query: " + generalq);
                String[] splitQuery = generalq.split("\\s+"); //split up the general query into separate words (that were separated by spaces)
                
                boolean moodSearch = false;
                ArrayList<Integer> moods = new ArrayList<Integer>();
                for(String word : splitQuery){ //iterate through all words
                    if(theNumberBase.containsKey(word)){ //if the word is a mood word
                        System.out.println("Mood word: " + word);
                        if(!moods.contains((Integer) theNumberBase.get(word))){ //and if the mood isn't already in the list
                            moods.add((Integer) theNumberBase.get(word)); //add it
                        }
                        moodSearch = true;
                    }
                }
                int[] allMoods = DatabaseAccess.convertIntegers(moods); //convert from ArrayList to integer array
                
                if(!generalq.isEmpty()){ //if query isn't empty
                    if(moodSearch) { //if there was a mood word found                    
                        result = dba.getSearchResults(con, "", allMoods, 0, "", "", "", "", "", 0); //get mood results
                        System.out.println("Number of mood results:" + result.length);
                        if(result!=null){
                            for(counter = 0; counter<result.length; counter++){
                                String moodconvert = Integer.toString(result[counter].mood);
                                searchResultPage.resultTable.addItem(new Object[]{
                                    WordUtils.capitalize(result[counter].name), 
                                    result[counter].artist, 
                                    result[counter].album, 
                                    moodconvert, 
                                    result[counter].genre, 
                                    SongResultPages.formatTime(result[counter].length),
                                    (MyVaadinUI.result[counter].year==0) ? "" : String.valueOf(MyVaadinUI.result[counter].year)}, 
                                    counter);              
                                System.out.println(counter + ": " + result[counter].name);
                            }
                        }
                    }

                    int[] divertMood = {0,1,2,3,4,5,6,7};
                    //get normal text results and add it on to the mood results
                    result = ArrayUtils.addAll(result,dba.getSearchResults(con, generalq, divertMood, 0, "", "", "", "", "", 0));
                    System.out.println("Counter: " + counter);
                    System.out.println(result.length);
                    if(result!=null){
                        for(counter = counter; counter<result.length; counter++){ //continue counting from where the mood search left off
                            String moodconvert = Integer.toString(result[counter].mood);
                            searchResultPage.resultTable.addItem(new Object[]{
                                WordUtils.capitalize(result[counter].name), 
                                result[counter].artist, 
                                result[counter].album, 
                                moodconvert, 
                                result[counter].genre, 
                                SongResultPages.formatTime(result[counter].length),
                                (MyVaadinUI.result[counter].year==0) ? "" : String.valueOf(MyVaadinUI.result[counter].year)}, //if the year is zero display nothing
                                counter);               
                            System.out.println(counter + ": " + result[counter].name);
                        }
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
        
        //tab change listeners
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