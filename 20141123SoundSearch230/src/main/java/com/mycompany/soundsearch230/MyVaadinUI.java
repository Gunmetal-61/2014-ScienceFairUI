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
import Wavesurfer.Wavesurfer;
import static com.mycompany.soundsearch230.SearchResultPage.nameIdentifier;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
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
        final TabSheet primary = new TabSheet();
        getPage().setTitle("Aura");
        setContent(overlord);
        overlord.addComponent(toolbar);

    
        final TextField generalSearchBox = new TextField();
        generalSearchBox.setDescription("Search for a Song");
             
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
        searchResultPage = new SearchResultPage(primary);
        final AbsoluteLayout SeaRPage = searchResultPage.drawSearchRPage();
        
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
                
                if(!generalq.equals("")){ //if not empty
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
                }
            }
        });
 
        
              
        ////////////////////////////////////////////////////////////////////////
//      TAB 3: Advanced Search Page
        AdvancedSearchPage advancedSearchPage = new AdvancedSearchPage(primary, SeaRPage);
        AbsoluteLayout AdvSPage = advancedSearchPage.drawAdvancedSPage();


        IDEWrite Page = new IDEWrite();
        File[] instantFiles = Page.listFiles();
        String convert = null;
        
        System.out.println("Number of files = " + instantFiles.length);
        
        for(int i = 0; i<instantFiles.length; i++){
            System.out.println("1:" + instantFiles[i].toString());
            convert = instantFiles[i].toString();
//            System.out.println(convert);

            Page.writeArtist(con, convert);
            
//            System.out.println("ehgwheoig" + Page.writeAlbum(con, convert));
        }
        
//        String der = Page.writeAlbum(con, )


                
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