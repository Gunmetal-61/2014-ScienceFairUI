/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.soundsearch230;

import static com.vaadin.server.Sizeable.UNITS_PIXELS;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author Mitchell
 */
public class AdvancedSearchPage {
    public static String ASPsongText = "";
    public static String ASPartistText = "";
    public static int ASPseconds = 0;
    public static int[] ASPmood = {0,1,2,3,4,5,6,7}; 
    public static int ASPsubMood = 0;
    
    TabSheet other;
    AbsoluteLayout alternate;
    public AdvancedSearchPage(TabSheet primary, AbsoluteLayout SeaRPage) {
        other = primary;
        alternate = SeaRPage;
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
    
    public AbsoluteLayout drawAdvancedSPage(){
        AbsoluteLayout grid = new AbsoluteLayout();
        grid.setHeight(1200, UNITS_PIXELS);
        grid.setWidth(1600, UNITS_PIXELS);
        
        final TextField songTextSearchBox = new TextField("Search By Song Name");
        final TextField artistTextSearchBox = new TextField("Search By Artist");
        final TextField albumSearchBox = new TextField("Search By Album");
        final TextField yearSearchBox = new TextField("Search By Year");
        final TextField secondSearchBox = new TextField("Search By Song Length");
        final TextField subMoodSearchBox = new TextField("Search By Subsong Mood");
        Label moodNote = new Label("Select an Overall Mood");
        final CheckBox selectMood0 = new CheckBox("0");
        final CheckBox selectMood1 = new CheckBox("1");
        final CheckBox selectMood2 = new CheckBox("2");
        final CheckBox selectMood3 = new CheckBox("3");
        final CheckBox selectMood4 = new CheckBox("4");
        final CheckBox selectMood5 = new CheckBox("5");
        final CheckBox selectMood6 = new CheckBox("6");
        final CheckBox selectMood7 = new CheckBox("7");
        
        
        VerticalLayout ingrid = new VerticalLayout();
//        ingrid.setHeight(1080, UNITS_PIXELS);
//        ingrid.setWidth(960, UNITS_PIXELS);
        
        
        
        //primary.addComponent(grid);
        grid.addComponent(ingrid, "left: 320px; top: 0px;");
        
        ingrid.addComponent(songTextSearchBox);
        ingrid.addComponent(artistTextSearchBox);
        ingrid.addComponent(albumSearchBox);
        ingrid.addComponent(yearSearchBox);
        ingrid.addComponent(secondSearchBox);
        ingrid.addComponent(moodNote);
        ingrid.addComponent(selectMood0);
        ingrid.addComponent(selectMood1);
        ingrid.addComponent(selectMood2);
        ingrid.addComponent(selectMood3);
        ingrid.addComponent(selectMood4);
        ingrid.addComponent(selectMood5);
        ingrid.addComponent(selectMood6);
        ingrid.addComponent(selectMood7);
        ingrid.addComponent(subMoodSearchBox);
        
        

        Button startSearchButton = new Button("Search");
        ingrid.addComponent(startSearchButton);
        
        startSearchButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
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
                System.out.println(ASPmood);
                System.out.println("1:" + selectMood1);
               // ASPsubMood = artistTextSearchBox.getValue();
                System.out.println(ASPsongText);
                
                //ASPmood = moodSearchBox.getValue();

                MyVaadinUI.result = MyVaadinUI.dba.getSearchResults(MyVaadinUI.con,generalq,ASPmood,ASPseconds,ASPsongText,ASPartistText,ASPsubMood);
                
                for(int i = 0; i<100; i++){
//                    if(generalq.equals("prayer")){
                    if(MyVaadinUI.result[i]==null){
                        //songTextSearchBox.setValue("");
                        ASPsongText = "";
                    }else{
                        String moodconverter = Integer.toString(MyVaadinUI.result[i].mood);
                        MyVaadinUI.searchResultPage.resultTable.addItem(new Object[]{WordUtils.capitalize(MyVaadinUI.result[i].name), 
                            MyVaadinUI.result[i].artist, 
                            MyVaadinUI.result[i].album, 
                            SongResultPages.time(MyVaadinUI.result[i].length), 
                            MyVaadinUI.result[i].genre, 
                            moodconverter}, i);
                    }    
                }
                other.setSelectedTab(alternate);
            }
        });
        return grid;   
    }
}
