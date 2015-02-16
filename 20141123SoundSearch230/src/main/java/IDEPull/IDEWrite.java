/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package IDEPull;

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
    
    public static void writeSongtable(){
        
    }
    
    public static String writeArtist(Connection con, String retrievedIndividualFiles){
        Mp3File mp3file = null;
        String theTrackNumber = "";
        String theArtist = "";
        String theYear = "";
        String theTitle = "";
        String theAlbum = "";
        int theGenreID = 0;
        String theGenreInText = "";
        String theComment = "";
        String theComposer = "";
        String thePublisher = "";
        String theOriginalArtist = "";
        String theAlbumArtist = "";
        String theCopyright = "";
        String theURL = "";
        String theEncoder = "";
        
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
            
            
            System.out.println("Retrieving Song Metadata...");
            System.out.println("");
            theTrackNumber = id3v2Tag.getTrack();
            theArtist = id3v2Tag.getArtist();
            theTitle = id3v2Tag.getTitle();
            theAlbum = id3v2Tag.getAlbum();
            theYear = id3v2Tag.getYear();
            theGenreID = id3v2Tag.getGenre();
            theGenreInText = id3v2Tag.getGenreDescription();
            theComment = id3v2Tag.getComment();
            theComposer = id3v2Tag.getComposer();
            thePublisher = id3v2Tag.getPublisher();
            theOriginalArtist = id3v2Tag.getOriginalArtist();
            theAlbumArtist = id3v2Tag.getAlbumArtist();
            theCopyright = id3v2Tag.getCopyright();
            theURL = id3v2Tag.getUrl();
            theEncoder = id3v2Tag.getEncoder();
            
            System.out.println("Track: " + theTrackNumber);
            System.out.println("Artist: " + theArtist);
            System.out.println("Title: " + theTitle);
            System.out.println("Album: " + theAlbum);
            System.out.println("Year: " + theYear);
            System.out.println("Genre: " + theGenreInText);
            System.out.println("Genre Number/Code:" + theGenreID);
            System.out.println("Comments: " + theComment);
            System.out.println("Composer: " + theComposer);
            System.out.println("Publisher: " + thePublisher);
            System.out.println("Original artist: " + theOriginalArtist);
            System.out.println("Album artist: " + theAlbumArtist);
            System.out.println("Copyright: " + theCopyright);
            System.out.println("URL: " + theURL);
            System.out.println("Encoder: " + theEncoder);
            System.out.println("");
            System.out.println("Done.");
        }
   
        
        Statement sendStatement = null;
        int returnedArtistID = 0;
        int addArtistID = 0;
        int returnedAlbumID = 0;
        int addAlbumID = 0;
        int returnedYearID = 0;
        int addYearID = 0;
        int returnedGenreID = 0;
        int addGenreID = 0;
        
        try {
            sendStatement = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                   ResultSet.CONCUR_UPDATABLE);
            
            if (theArtist == null) {
            
            } else{
                ResultSet searchArtistID = sendStatement.executeQuery(
                "SELECT ARTISTID FROM ARTISTS WHERE ARTISTNAME LIKE '" + theArtist.replaceAll("'", "''") + "'");
                if (searchArtistID.next()) { //if the artist isn't found the artistid variable will remain 0
                    returnedArtistID = searchArtistID.getInt("ARTISTID");
                }
                searchArtistID.close();   
            }
            
            if (theAlbum == null) {
            
            } else{
                ResultSet searchAlbumID = sendStatement.executeQuery(
                "SELECT ALBUMID FROM ALBUMS WHERE ALBUMNAME LIKE '" + theAlbum.replaceAll("'", "''") + "'");
                if (searchAlbumID.next()) { //if the album isn't found the albumid variable will remain 0
                    returnedAlbumID = searchAlbumID.getInt("ALBUMID");
                }
                searchAlbumID.close();
            }
            
            if (theYear == null) {
            
            } else{
                ResultSet searchYearID = sendStatement.executeQuery(
                "SELECT YEARID FROM YEARS WHERE ACTUALYEAR LIKE '" + theYear + "'");
                if (searchYearID.next()) { //if the year isn't found the yearid variable will remain 0
                    returnedYearID = searchYearID.getInt("YEARID");
                }
                searchYearID.close();
            }
            
            if (theGenreInText == null) {
            
            } else{
                ResultSet searchGenreID = sendStatement.executeQuery(
                "SELECT GENREID FROM GENRES WHERE GENRENAME LIKE '" + theGenreInText.replaceAll("'", "''") + "'");
                if (searchGenreID.next()) { //if the genre isn't found the genreid variable will remain 0
                    returnedGenreID = searchGenreID.getInt("GENREID");
                }
                searchGenreID.close();
            }
            
            
            
        PreparedStatement addMetaDataInfo;
        if(returnedArtistID == 0){ //if artist was not already registered
            ResultSet maxArtistID = sendStatement.executeQuery("SELECT MAX(ARTISTID) \"ArtistMAXid\" FROM ARTISTS");
            maxArtistID.next();
            addArtistID = maxArtistID.getInt("ArtistMAXid") + 1;
            
            addMetaDataInfo = con.prepareStatement("INSERT INTO ARTISTS (ARTISTNAME, ARTISTID) VALUES (?,?)"); //all analyzedflags are initialized as zero
            addMetaDataInfo.setString(1, theArtist); //only insert the song and artist id into the maintable
            addMetaDataInfo.setInt(2, addArtistID);
            addMetaDataInfo.addBatch();
            addMetaDataInfo.executeBatch();
            addMetaDataInfo = con.prepareStatement("UPDATE SONGTABLE SET ARTISTID ='" + addArtistID + "' WHERE DIR = '" + dirFileDirectory.replaceAll("'", "''") + "'");
            addMetaDataInfo.addBatch();
            addMetaDataInfo.executeBatch();
            addMetaDataInfo.close();
            
            maxArtistID.close();
        } else{
            addMetaDataInfo = con.prepareStatement("UPDATE SONGTABLE SET ARTISTID ='" + returnedArtistID + "' WHERE DIR = '" + dirFileDirectory.replaceAll("'", "''") + "'");
            addMetaDataInfo.addBatch();
            addMetaDataInfo.executeBatch();
            addMetaDataInfo.close();
        }
        
        if(returnedAlbumID == 0){ //if album was not already registered
            ResultSet maxAlbumID = sendStatement.executeQuery("SELECT MAX(ALBUMID) \"albumMAXid\" FROM ALBUMS");
            maxAlbumID.next();
            addAlbumID = maxAlbumID.getInt("AlbumMAXid") + 1;
            
            addMetaDataInfo = con.prepareStatement("INSERT INTO ALBUMS (ALBUMNAME, ALBUMID) VALUES (?,?)"); //all analyzedflags are initialized as zero
            addMetaDataInfo.setString(1, theAlbum); //only insert the song and artist id into the maintable
            addMetaDataInfo.setInt(2, addAlbumID);
            addMetaDataInfo.addBatch();
            addMetaDataInfo.executeBatch();
            addMetaDataInfo = con.prepareStatement("UPDATE SONGTABLE SET ALBUMID ='" + addAlbumID + "' WHERE DIR = '" + dirFileDirectory.replaceAll("'", "''") + "'");
            addMetaDataInfo.addBatch();
            addMetaDataInfo.executeBatch();
            addMetaDataInfo.close();
            
            maxAlbumID.close();
        } else{
            addMetaDataInfo = con.prepareStatement("UPDATE SONGTABLE SET ALBUMID ='" + returnedAlbumID + "' WHERE DIR = '" + dirFileDirectory.replaceAll("'", "''") + "'");
            addMetaDataInfo.addBatch();
            addMetaDataInfo.executeBatch();
            addMetaDataInfo.close();
        }
        
        if(returnedYearID == 0){ //if year was not already registered
            ResultSet maxYearID = sendStatement.executeQuery("SELECT MAX(YEARID) \"yearMAXid\" FROM YEARS");
            maxYearID.next();
            addYearID = maxYearID.getInt("YearMAXid") + 1;
            
            addMetaDataInfo = con.prepareStatement("INSERT INTO YEARS (ACTUALYEAR, YEARID) VALUES (?,?)"); //all analyzedflags are initialized as zero
            addMetaDataInfo.setString(1, theYear); //only insert the song and artist id into the maintable
            addMetaDataInfo.setInt(2, addYearID);
            addMetaDataInfo.addBatch();
            addMetaDataInfo.executeBatch();
            addMetaDataInfo = con.prepareStatement("UPDATE SONGTABLE SET YEARID ='" + addYearID + "' WHERE DIR = '" + dirFileDirectory.replaceAll("'", "''") + "'");
            addMetaDataInfo.addBatch();
            addMetaDataInfo.executeBatch();
            addMetaDataInfo.close();
            
            maxYearID.close();
        } else{
            addMetaDataInfo = con.prepareStatement("UPDATE SONGTABLE SET YEARID ='" + returnedYearID + "' WHERE DIR = '" + dirFileDirectory.replaceAll("'", "''") + "'");
            addMetaDataInfo.addBatch();
            addMetaDataInfo.executeBatch();
            addMetaDataInfo.close();
        }
        
        if(returnedGenreID == 0){ //if genre was not already registered
            ResultSet maxGenreID = sendStatement.executeQuery("SELECT MAX(GENREID) \"GenreMAXid\" FROM GENRES");
            maxGenreID.next();
            addGenreID = maxGenreID.getInt("GenreMAXid") + 1;
            
            addMetaDataInfo = con.prepareStatement("INSERT INTO GENRES (GENRENAME, GENREID) VALUES (?,?)"); //all analyzedflags are initialized as zero
            addMetaDataInfo.setString(1, theGenreInText); //only insert the song and artist id into the maintable
            addMetaDataInfo.setInt(2, addGenreID);
            addMetaDataInfo.addBatch();
            addMetaDataInfo.executeBatch();
            addMetaDataInfo = con.prepareStatement("UPDATE SONGTABLE SET GENREID ='" + addGenreID + "' WHERE DIR = '" + dirFileDirectory.replaceAll("'", "''") + "'");
            addMetaDataInfo.addBatch();
            addMetaDataInfo.executeBatch();
            addMetaDataInfo.close();
            
            maxGenreID.close();
        } else{
            addMetaDataInfo = con.prepareStatement("UPDATE SONGTABLE SET GENREID ='" + returnedGenreID + "' WHERE DIR = '" + dirFileDirectory.replaceAll("'", "''") + "'");
            addMetaDataInfo.addBatch();
            addMetaDataInfo.executeBatch();
            addMetaDataInfo.close();
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
        
        
        
        return theArtist;
    }
    
}
