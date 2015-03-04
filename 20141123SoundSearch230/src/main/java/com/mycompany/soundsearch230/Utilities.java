/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.soundsearch230;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jeffrey
 */
public class Utilities {
    /**
     * From http://stackoverflow.com/questions/718554/how-to-convert-an-arraylist-containing-integers-to-primitive-int-array
     * Converts Integer ArrayList to int array
     * 
     * @param integers
     * @return 
     */
    public static int[] convertIntegers(List<Integer> integers){
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++)
        {
            ret[i] = iterator.next().intValue();
        }
        return ret;
    }
    
    /**
     * Convert seconds into M:SS
     * @param input Seconds
     * @return 
     */
    public static String formatTime(int input){
        String seconds = (input%60>9)?String.valueOf(input%60):"0"+String.valueOf(input%60);
        return input/60 + ":" + seconds;
    }
}
