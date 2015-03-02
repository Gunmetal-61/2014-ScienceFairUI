/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.soundsearch230;

import Database.DBRow;
import Database.DatabaseAccess;
import static com.mycompany.soundsearch230.AdvancedSearchPage.ASPartistText;
import static com.mycompany.soundsearch230.AdvancedSearchPage.ASPmood;
import static com.mycompany.soundsearch230.AdvancedSearchPage.ASPseconds;
import static com.mycompany.soundsearch230.AdvancedSearchPage.ASPsongText;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author Mitchell
 */
public class SearchResultPage {
    
    public Table resultTable = new Table("Search Results");
    Connection con = DatabaseAccess.startconnection("orcl"); 
    int[] moodarray;
    public static String nameIdentifier = "";
    public static String artistIdentifier = "";
    public static String genre = "";
    public static String album = "";
    public static int length = 0;
    public static int year = 0;

    Label stConvert;
    Label saConvert;
    AbsoluteLayout SRPingrid;
    TabSheet other;
    public static String ASPsongText = "";
    public static String ASPartistText = "";
    public static int ASPseconds = 0;
    public static int[] ASPmood = {0,1,2,3,4,5,6,7}; 
    AbsoluteLayout alternate;

    
    public SearchResultPage(TabSheet primary) {
        other = primary;
    }
    
    public static int[] convertIntegers(List<Integer> integers){
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++)
        {
            ret[i] = iterator.next().intValue();
        }
        return ret;
    }
    
    
    public AbsoluteLayout drawSearchRPage(){
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
        
        final TextField songTextSearchBox = new TextField("Search By Song Name");
        final TextField artistTextSearchBox = new TextField("Search By Artist");
        final TextField albumSearchBox = new TextField("Search By Album");
        final TextField yearSearchBox = new TextField("Search By Year");
        final TextField timeInSecondsSearchBox = new TextField("Search By Song Length");
        final TextField genreSearchBox = new TextField("Search By Genre");
        final TextField subMoodSearchBox = new TextField("Search By Subsong Mood");
        Label moodNote = new Label("Select an Overall Mood");
        final CheckBox selectMood0 = new CheckBox("0: Inspiration, Desire, Love");
        final CheckBox selectMood1 = new CheckBox("1: Fascination, Admiration, Joyfulness");
        final CheckBox selectMood2 = new CheckBox("2: Satisfaction, Softened, Relaxed");
        final CheckBox selectMood3 = new CheckBox("3: Awaiting, Deferent, Calm");
        final CheckBox selectMood4 = new CheckBox("4: Boredom, Sadness, Isolation");
        final CheckBox selectMood5 = new CheckBox("5: Disappointment, Contempt, Jealousy");
        final CheckBox selectMood6 = new CheckBox("6: Irritation, Disgust, Alarm");
        final CheckBox selectMood7 = new CheckBox("7: Astonishment, Eagerness, Curiousity");
        
        Button startSearchButton = new Button("Filter");
     
        VerticalLayout moodCheck = new VerticalLayout();
        moodCheck.addComponent(moodNote);
        moodCheck.addComponent(selectMood0);
        moodCheck.addComponent(selectMood1);
        moodCheck.addComponent(selectMood2);
        moodCheck.addComponent(selectMood3);
        moodCheck.addComponent(selectMood4);
        moodCheck.addComponent(selectMood5);
        moodCheck.addComponent(selectMood6);
        moodCheck.addComponent(selectMood7);
        moodCheck.addComponent(subMoodSearchBox);
        moodCheck.addComponent(startSearchButton);
        
        sidebar.addComponent(songTextSearchBox);
        sidebar.addComponent(artistTextSearchBox);
        sidebar.addComponent(albumSearchBox);
        sidebar.addComponent(yearSearchBox);

        sidebar.addComponent(moodCheck);
        
        sidebar.setExpandRatio(moodCheck,1);
        
        //when search button button is clicked
        startSearchButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                MyVaadinUI.searchResultPage.resultTable.removeAllItems();
                                
                String generalq = "";
                ASPsongText = songTextSearchBox.getValue();
                ASPartistText = artistTextSearchBox.getValue();
                ArrayList<Integer> moodsSelected = new ArrayList<Integer>();
                
                if(selectMood0.getValue()){
                    moodsSelected.add(0);
                }
                if(selectMood1.getValue()){
                    moodsSelected.add(1);
                }
                if(selectMood2.getValue()){
                    moodsSelected.add(2);
                }
                if(selectMood3.getValue()){
                    moodsSelected.add(3);
                }
                if(selectMood4.getValue()){
                    moodsSelected.add(4);
                }
                if(selectMood5.getValue()){
                    moodsSelected.add(5);
                }
                if(selectMood6.getValue()){
                    moodsSelected.add(6);
                }
                if(selectMood7.getValue()){
                    moodsSelected.add(7);
                }

                ASPmood = convertIntegers(moodsSelected);
               // ASPsubMood = artistTextSearchBox.getValue();
                System.out.println(ASPsongText);
                
                //ASPmood = moodSearchBox.getValue();

                MyVaadinUI.result = MyVaadinUI.dba.getSearchResults(MyVaadinUI.con,generalq,ASPmood,ASPseconds,ASPsongText,ASPartistText,"","","", 0);
                
                for(int i = 0; i<MyVaadinUI.result.length; i++){
                    if(MyVaadinUI.result[i]==null){
                        //songTextSearchBox.setValue("");
                        ASPsongText = "";
                    }else{
                        String moodconverter = Integer.toString(MyVaadinUI.result[i].mood);

                        MyVaadinUI.searchResultPage.resultTable.addItem(new Object[]{WordUtils.capitalize(MyVaadinUI.result[i].name), 
                            MyVaadinUI.result[i].artist, 
                            MyVaadinUI.result[i].album, 
                            moodconverter, 
                            MyVaadinUI.result[i].genre,
                            SongResultPages.formatTime(MyVaadinUI.result[i].length),
                            (MyVaadinUI.result[i].year==0) ? "" : String.valueOf(MyVaadinUI.result[i].year)},
                            i);
                    }
                }
            }
        });
        
        
////////////////////////////////////////////////////////////////////////////////
//      RESULT TABLE
        moodarray = new int[7];
        moodarray[0] = 1;
        moodarray[1] = 2;
        moodarray[2] = 3;
        moodarray[3] = 4;
        moodarray[4] = 5;
        moodarray[5] = 6;
        moodarray[6] = 7;
        
        //expand table
        resultTable.setSizeFull();
        
        //add columns
        resultTable.addContainerProperty("Song Name", String.class, null);
        resultTable.addContainerProperty("Artist", String.class, null);
        resultTable.addContainerProperty("Album", String.class, null);
        resultTable.addContainerProperty("Mood", String.class, null);
        resultTable.addContainerProperty("Genre", String.class, null);
        resultTable.addContainerProperty("Length", String.class, null);
        resultTable.addContainerProperty("Year", String.class, null);
        
        //adjust their relative widths
        resultTable.setColumnExpandRatio("Song Name",3);
        resultTable.setColumnExpandRatio("Artist",3);
        resultTable.setColumnExpandRatio("Album",3);
        resultTable.setColumnExpandRatio("Mood",1);
        resultTable.setColumnExpandRatio("Genre",2);
        resultTable.setColumnExpandRatio("Length",1);
        resultTable.setColumnExpandRatio("Year",1);

        final String generalq = "";
        
        //when a row in the table is clicked on
        resultTable.addItemClickListener(new ItemClickEvent.ItemClickListener () {
            @Override
            public void itemClick(ItemClickEvent event) {
                int selectedRow = Integer.valueOf(event.getItemId().toString());
                
                nameIdentifier = MyVaadinUI.result[selectedRow].name;
                artistIdentifier = MyVaadinUI.result[selectedRow].artist;
                length = MyVaadinUI.result[selectedRow].length;
                year = MyVaadinUI.result[selectedRow].year;
                album = MyVaadinUI.result[selectedRow].album;
                genre = MyVaadinUI.result[selectedRow].genre;
//                stConvert = new Label(nameIdentifier);
//                saConvert = new Label(artistIdentifier);
//                SRPingrid.addComponent(stConvert, "left: 260px; top: 40px;");
//                SRPingrid.addComponent(saConvert, "left: 260px; top: 60px;");
                
//                other.getTab(SonRPage);
//                other.removeTab(SonRPage);
                SongResultPages songResultPage2 = new SongResultPages(nameIdentifier,artistIdentifier,album,genre,length,year);
                AbsoluteLayout SonRPage = songResultPage2.drawSongRPage();
                other.addTab(SonRPage);
                other.getTab(SonRPage).setCaption(WordUtils.capitalize(nameIdentifier)); //label tab with song name
                other.getTab(SonRPage).setClosable(true); //allow user to close tabs
                other.setSelectedTab(SonRPage);
                 
            }
        });
        
        ingrid.addComponent(resultTable, "left: 200px; top: 0px;");

        return grid;
    }
    
}
