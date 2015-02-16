/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package IDEPull;

import Database.DBRow;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author mitchell
 */
public class IDEWrite {
    
    public IDEWrite() {
        
    }
    
    
    
    public static File[] listFiles() {
        File songFolder = new File("/home/mitchell/Music/Scan Songs");
        
        File[] retrievedFiles = songFolder.listFiles();
        
        
        for(int i = 0; i<retrievedFiles.length; i++){            
//            System.out.println("1:" + retrievedFiles[i]);
        }
        
        
        
        
        return retrievedFiles;
    }
    
    public static String songdir(Connection con, String newdir, String title, String artistname) throws SQLException{
        String dir = "";
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                   ResultSet.CONCUR_UPDATABLE);
            ResultSet uprs = stmt.executeQuery( //get the results of the query
            "SELECT DIR FROM SONGTABLE INNER JOIN ARTISTS ON SONGTABLE.ARTISTID = ARTISTS.ARTISTID WHERE ARTISTNAME = '" + artistname + "' AND TITLE = '"+ title +"'");
            if (uprs.next()) {
                if(newdir == null){
                    dir = uprs.getString("DIR");
                } else{
                    uprs.updateString("DIR",newdir);
                    uprs.updateRow(); //update database
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); } //close connection
        }
        return dir;
    }
    

    public static void writeSongtable(){
        
    }
    
    public static String writeArtist(String retrievedIndividualFiles){
        Mp3File mp3file = null;
        String theArtist = "";
        try {
            mp3file = new Mp3File(retrievedIndividualFiles);
        } catch (IOException ex) {
            Logger.getLogger(IDEWrite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedTagException ex) {
            Logger.getLogger(IDEWrite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidDataException ex) {
            Logger.getLogger(IDEWrite.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (mp3file.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mp3file.getId3v2Tag();
         //   System.out.println("Artist: " + id3v2Tag.getArtist());
            theArtist = id3v2Tag.getArtist();
        }
        
        return theArtist;
    }
    
    
    
//    SELECT ARTIST FROM SONGTABLE INNER JOIN ARTISTS ON SONGTABLE.ARTISTID = ARTISTS.ARTISTID WHERE ARTISTNAME =    
//    "SELECT DISTINCT SONGTABLE.TITLE, SONGTABLE.AUDIOMOOD, SONGTABLE.SLENGTH, SONGTABLE.ARTISTID, ARTISTS.ARTISTNAME FROM SONGTABLE INNER JOIN ARTISTS ON SONGTABLE.ARTISTID = ARTISTS.ARTISTID " + generalquery + lengthsquery + moodsquery + namesquery + artistsquery;

    
    public static String writeAlbum(Connection con, String retrievedIndividualFiles){
        Mp3File mp3file = null;
        String theAlbum = "";
        String nameVerifier = "";
        String artistVerifier = "";
        String dirFileDirectory = retrievedIndividualFiles;
        dirFileDirectory = FilenameUtils.getName(dirFileDirectory);
        
        try {
            mp3file = new Mp3File(retrievedIndividualFiles);
        } catch (IOException ex) {
            Logger.getLogger(IDEWrite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedTagException ex) {
            Logger.getLogger(IDEWrite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidDataException ex) {
            Logger.getLogger(IDEWrite.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (mp3file.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mp3file.getId3v2Tag();
            System.out.println("Album: " + id3v2Tag.getAlbum());
            nameVerifier = id3v2Tag.getArtist();
            artistVerifier = id3v2Tag.getTitle();
            theAlbum = id3v2Tag.getAlbum();
        }
   
        
        Statement sendStatement = null;
        String textPresence = "";
        String albumIDPresent = "";
        int returnedAlbumID = 0;
        int addAlbumID = 0;
        try {
            sendStatement = con.createStatement();
            sendStatement = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                   ResultSet.CONCUR_UPDATABLE);
            
            if (theAlbum == null) {
            
            } else{
                ResultSet searchAlbumID = sendStatement.executeQuery(
                "SELECT ALBUMID FROM ALBUMS WHERE ALBUMNAME LIKE '" + theAlbum.replaceAll("'", "''") + "'");
                if (searchAlbumID.next()) { //if the artist isn't found the artistid variable will remain 0
                    returnedAlbumID = searchAlbumID.getInt("ALBUMID");
                }
            }
            
            
            
            
            PreparedStatement addAlbumInfo;
            if(returnedAlbumID == 0){ //if artist was already registered
                ResultSet addID = sendStatement.executeQuery("SELECT MAX(ALBUMID) \"MAXid\" FROM ALBUMS");
                addID.next();
                addAlbumID = addID.getInt("MAXid") + 1;
                
                addAlbumInfo = con.prepareStatement("INSERT INTO ALBUMS (ALBUMNAME, ALBUMID) VALUES (?,?)"); //all analyzedflags are initialized as zero
                addAlbumInfo.setString(1, theAlbum); //only insert the song and artist id into the maintable
                addAlbumInfo.setInt(2, addAlbumID);
                addAlbumInfo.addBatch();
                addAlbumInfo.executeBatch();
                addAlbumInfo = con.prepareStatement("UPDATE SONGTABLE SET ALBUMID ='" + addAlbumID + "' WHERE DIR = '" + dirFileDirectory.replaceAll("'", "''") + "'");
                addAlbumInfo.addBatch();
                addAlbumInfo.executeBatch();
            } else{
                addAlbumInfo = con.prepareStatement("UPDATE SONGTABLE SET ALBUMID ='" + returnedAlbumID + "' WHERE DIR = '" + dirFileDirectory.replaceAll("'", "''") + "'");
                addAlbumInfo.addBatch();
                addAlbumInfo.executeBatch();
            }
            
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sendStatement != null) {
                try {
                sendStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(IDEWrite.class.getName()).log(Level.SEVERE, null, ex);
                }
            }        
        }
        
        
        
        return theAlbum;
    }
    

    
      
//        WHERE TITLE LIKE 
//        SELECT  FROM SONGTABLE INNER JOIN ARTISTS ON SONGTABLE.ARTISTID = ARTISTS.ARTISTID WHERE ARTISTNAME =    
    
    public static String writeYear(String retrievedIndividualFiles){
        Mp3File mp3file = null;
        String theYear = "";
        try {
            mp3file = new Mp3File(retrievedIndividualFiles);
        } catch (IOException ex) {
            Logger.getLogger(IDEWrite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedTagException ex) {
            Logger.getLogger(IDEWrite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidDataException ex) {
            Logger.getLogger(IDEWrite.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (mp3file.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mp3file.getId3v2Tag();
         //   System.out.println("Artist: " + id3v2Tag.getArtist());
            theYear = id3v2Tag.getYear();
        }
        
        return theYear;
    }
    
    public static String writeGenre(String retrievedIndividualFiles){
        Mp3File mp3file = null;
        String theGenre = "";
        try {
            mp3file = new Mp3File(retrievedIndividualFiles);
        } catch (IOException ex) {
            Logger.getLogger(IDEWrite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedTagException ex) {
            Logger.getLogger(IDEWrite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidDataException ex) {
            Logger.getLogger(IDEWrite.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (mp3file.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mp3file.getId3v2Tag();
         //   System.out.println("Artist: " + id3v2Tag.getArtist());
            theGenre = id3v2Tag.getGenreDescription();
        }
        
        return theGenre;
    }
    
}
