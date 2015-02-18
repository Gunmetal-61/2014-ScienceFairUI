/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jeffrey
 */
public class DatabaseAccess {
     public static Connection startconnection(String database){ //connection code is from http://www.mkyong.com/jdbc/connect-to-oracle-db-via-jdbc-driver-java/
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
	} catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return null;
	}
 
	Connection connection = null;
 
	try {
            connection = DriverManager.getConnection( //get connection to specified database
			"jdbc:oracle:thin:@localhost:1521:"+database, "sys as sysdba",
			"oracle10g");
	} catch (SQLException e) {
		System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
                return null;
	}
 
	if (connection != null) {
		System.out.println("Connected.");
	} else {
		System.out.println("Failed to make connection.");
	}
        return connection;
    }
    ////////////////////////////////////////////////////////////////////////////
//  RETRIEVE SELECTED SONG'S SUBSONG MOOD VALUES TO COLOR IT'S WAVEFORM GRAPH APPRORPRIATELY
    /**
     * Used last year for subsong colors. Deprecated.
     * 
     * @param con
     * @param title
     * @param artistname
     * @return
     * @throws SQLException 
     */ 
    public static int[] retrievesubsong(Connection con, String title, String artistname) throws SQLException{
        int[] song = new int[31];
        Statement stmt = null;
        String query =
                "SELECT SONGTABLE.S0, SONGTABLE.S1, SONGTABLE.S2, SONGTABLE.S3, SONGTABLE.S4, SONGTABLE.S5, "
                + "SONGTABLE.S6, SONGTABLE.S7, SONGTABLE.S8, SONGTABLE.S9, SONGTABLE.S10, "
                + "SONGTABLE.S11, SONGTABLE.S12, SONGTABLE.S13, SONGTABLE.S14, SONGTABLE.S15, "
                + "SONGTABLE.S16, SONGTABLE.S17, SONGTABLE.S18, SONGTABLE.S19, SONGTABLE.S20, "
                + "SONGTABLE.S21, SONGTABLE.S22, SONGTABLE.S23, SONGTABLE.S24, SONGTABLE.S25, "
                + "SONGTABLE.S26, SONGTABLE.S27, SONGTABLE.S28, SONGTABLE.S29, SONGTABLE.S30 "
                + "FROM SONGTABLE INNER JOIN ARTISTS ON SONGTABLE.ARTISTID = ARTISTS.ARTISTID WHERE ARTISTNAME = '" + artistname + "' AND TITLE = '"+ title +"'";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()) { //get the first song in the resultset (there should only be one)
                for(int i = 0; i < 31; i++){
                    song[i] = rs.getInt("S" + i);
                }
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            if (stmt != null) { stmt.close(); } //close connection
        }
        return song;
    }
    
    ////////////////////////////////////////////////////////////////////////////
//  EXECUTE QUERIES BY THE USER   
    /**
     * 
     * @param con
     * @param generalq
     * @param mood
     * @param length
     * @param name
     * @param artist
     * @param moodlevel
     * @return
     */
    public static DBRow[] getSearchResults(Connection con, String generalq, int[] mood, int length, String name, String artist, int moodlevel){
    //  Declare Query Strings  
        DBRow[] output = new DBRow[100];//creates an array of 25 rows in the song result table
        
        try{
        String generalquery = "";//string to create sql query over all types of data
        String moodsquery = "";//string from mood query checkboxes sent to sql command line
        String lengthsquery = "";//string from length of song query field (search format: min":"secs) sent to sql command line
        String namesquery = "";//string from name of song query field sent to sql command line
        String artistsquery = "";//string from name of artist query field sent to sql command line
        
        
        Statement stmt = null;
        int upperlength = length + 30;/*for [lengthsquery]; sets a leeway of 30 
        seconds above the specified time when results are returned (i.e. query
        for length of 2:30, song is 2:50 long, this will allow it to show up in the results)
        */
        ////////////////////////////////////////////////////////////////////////
//      SQL QUERY FOR SEARCH ACROSS ALL FIELDS
//        for(){
        if(!generalq.equals("")){
            generalquery = "WHERE TITLE LIKE '%" +  generalq + "%' OR ARTISTNAME LIKE '%" + generalq + "%'";
        }
            
//        } 
//        if(!name.equals("")&&!artist.equals("")){
//            
//        }
        
        ////////////////////////////////////////////////////////////////////////
//      SQL QUERY FOR SEARCHING SONGS WITH LENGTH OF SONG             
        int lowerlength = length - 30;//like [upperlength], sets leeway of -30 seconds
        if(lowerlength < 0){//i.e. if [lengthquery] is 20 seconds, if statment sets [lowerlength] leeway to -0 seconds
            lowerlength = 0;
        }       
        if(length != 0&&generalquery.equals("")){//if the length of a song is specified
            lengthsquery = "WHERE SLENGTH BETWEEN " + lowerlength + " AND " + upperlength;//[lengthsquery] becomes an SQL statement that searches for with specified song time as center of a field of +30 or -30 seconds
        }
        
        ////////////////////////////////////////////////////////////////////////
//      SQL QUERY FOR SEARCHING SONGS WITH MOOD             
        String moods = "";//new string
        for(int i = 0; i<mood.length; i++){//new {int} [i] declared as 0, the as long as it is less than the length of the song, +1 value of [i] per cycle
            moods = moods + mood[i] +  ",";//[moods] 
        }
        if(!moods.equals("")){//if mood is blank
            if(moodlevel == 0){//if the mood level is 0
                moods = moods.substring(0, moods.lastIndexOf(","));//moods will then equal
                moodsquery = " AND AUDIOMOOD IN ("+moods+")";
                if(lengthsquery.equals("")&&generalquery.equals("")){
                    moodsquery = "WHERE AUDIOMOOD IN ("+moods+")";
                }
            }
            if(moodlevel == 1){
                
            }
        }
        
        ////////////////////////////////////////////////////////////////////////
//      SQL QUERY FOR SEARCHING SONGS WITH NAME OF SONG             
        if(!name.equals("")){
            namesquery = " AND TITLE LIKE '%"+name.toLowerCase()+"%'";
            if(moodsquery.equals("")&&lengthsquery.equals("")&&generalquery.equals("")){
                namesquery = "WHERE TITLE LIKE '%"+name.toLowerCase()+"%'";
            }
        }

        ////////////////////////////////////////////////////////////////////////
//      SQL QUERY FOR SEARCHING SONGS WITH ARTIST NAME        
        if(!artist.equals("")){
            artistsquery = " AND ARTISTNAME LIKE '%"+artist.toLowerCase()+"%'";
            if(namesquery.equals("")&&moodsquery.equals("")&&lengthsquery.equals("")&&generalquery.equals("")){
                artistsquery = "WHERE ARTISTNAME LIKE '%"+artist.toLowerCase()+"%'";
            }
        }
        
        String query =
                "SELECT DISTINCT SONGTABLE.TITLE, SONGTABLE.AUDIOMOOD, SONGTABLE.SLENGTH, SONGTABLE.ARTISTID, ARTISTS.ARTISTNAME, YEARS.ACTUALYEAR, ALBUMS.ALBUMNAME, GENRES.GENRENAME FROM SONGTABLE "
                + "LEFT JOIN ARTISTS ON SONGTABLE.ARTISTID = ARTISTS.ARTISTID "
                + "LEFT JOIN YEARS ON SONGTABLE.YEARID = YEARS.YEARID "
                + "lEFT JOIN ALBUMS ON SONGTABLE.ALBUMID = ALBUMS.ALBUMID "
                + "lEFT JOIN GENRES ON SONGTABLE.GENREID = GENRES.GENREID " + generalquery + lengthsquery + moodsquery + namesquery + artistsquery;
        System.out.println(query);
        try {
            stmt = con.createStatement();
            long startTime = System.nanoTime();  
            ResultSet rs = stmt.executeQuery(query);
            long estimatedTime = System.nanoTime() - startTime;
            System.out.println(estimatedTime);
            int i = 0;
            while(rs.next()&&(i<100)) {
             //   System.out.println(i+"Hello");
                output[i] = new DBRow();
                output[i].name = rs.getString("TITLE");
                output[i].artist = rs.getString("ARTISTNAME");
                output[i].mood = rs.getInt("AUDIOMOOD");
                output[i].length = rs.getInt("SLENGTH");
                output[i].album = rs.getString("ALBUMNAME");
                output[i].year = rs.getInt("ACTUALYEAR");
                output[i].genre = rs.getString("GENRENAME");
                i++;
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            if (stmt != null) { stmt.close(); } //close connection
        }
        }catch(Exception e){
            
        }
        return output;
    }

    ////////////////////////////////////////////////////////////////////////////
//  RETRIEVE LYRICS OF SELECTED SONG TO BE DISPLAYED IN GUI BOX        
    /**
     * 
     * @param con
     * @param title
     * @param artistname
     * @return
     * @throws SQLException 
     */
    public static String retrievelyrics(Connection con, String title, String artistname) throws SQLException{
        String lyrics = "";
        Statement stmt = null;
        String query =
                "SELECT SONGTABLE.LYRICS FROM SONGTABLE INNER JOIN ARTISTS ON SONGTABLE.ARTISTID = ARTISTS.ARTISTID WHERE ARTISTNAME = '" + artistname + "' AND TITLE = '"+ title +"'";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()) { //get the first song in the resultset (there should only be one)
                lyrics = rs.getString("LYRICS");
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            if (stmt != null) { stmt.close(); } //close connection
        }
        return lyrics;
    }
    
    ////////////////////////////////////////////////////////////////////////////
//  RECALLS FILENAME OF SELECTED SONG FROM DATABASE TO BRING UP FOR PLAYING IN GUI    
    /**
     * 
     * @param con
     * @param newdir if null, then we want to get the directory stored in the database; else fill database with new directory
     * @param title
     * @param artistname
     * @return
     * @throws SQLException 
     */
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
    ////////////////////////////////////////////////////////////////////////////
//  RECALLS FILENAME OF SELECTED SONG'S ALBUM IMAGE FOR DISPLAY IN [SearchResultPage] and [SongResultPages]
    public static String albumPNGDir(Connection con, String newdir, String title, String artistname) throws SQLException{
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
        return "\\home\\mitchell\\Music\\Album Art\\"+dir;
    }
    ////////////////////////////////////////////////////////////////////////////
//  SETUP CODE USED TO PLACE AN ANALYZED SONG'S SUBSONG MOOD VALUES INTO THE ORACLE DATABASE TABLE 
//  NOT ACTIVE IN FINAL GUI PROGRAM  
    /**
     * 
     * @param con
     * @param title
     * @param artistid
     * @param values
     * @throws SQLException 
     */
    public static void setsubsong(Connection con, String title, int artistid, int[] values) throws SQLException{
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                   ResultSet.CONCUR_UPDATABLE);
            ResultSet uprs = stmt.executeQuery(//[uprs] = updateableresultset
            "SELECT S0, S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S14, S15, S16, S17, S18, S19, S20, S21, S22, S23, S24, S25, S26, S27, S28, S29, S30 FROM SONGTABLE WHERE TITLE = '" + title + "' AND ARTISTID = '" + artistid + "'");
            while (uprs.next()) {
                if(values.length <= 20){//if the number of subsong values is less than 20 to fit array size,
                    for(int i = 0; i < values.length; i++){
                        uprs.updateInt("S" + i, values[i]);//then update or populate empty cells with subsong values
                    }
                }
                else{
                    for(int i = 0; i < 20; i++){
                        uprs.updateInt("S" + i, values[i]);
                    }
                }
                uprs.updateRow(); //update database
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            if (stmt != null) { stmt.close(); } //close connection
        }
    }
    
    /**
     * 
     * @param con Database connection
     * @param title Title of the song
     * @param artistname Song artist name
     * @return Subsong segments int[i][j] where i is the detail of each section (0=sequence,1=mood,2=length) and j is the number of sections
     */
    public static int[][] retrieveSubSong(Connection con, String title, String artistname){
        ArrayList<Integer> sequence = new ArrayList<Integer>();
        ArrayList<Integer> mood = new ArrayList<Integer>();
        ArrayList<Integer> length = new ArrayList<Integer>();
        
        Statement stmt = null;
        String query =
                "SELECT SEQUENCE, MOOD, LENGTH FROM SUBSONGTABLE INNER JOIN ARTISTS ON SUBSONGTABLE.ARTISTID = ARTISTS.ARTISTID WHERE ARTISTNAME = '" + artistname + "' AND TITLE = '"+ title +"' ORDER BY SEQUENCE";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                sequence.add(rs.getInt("SEQUENCE"));
                mood.add(rs.getInt("MOOD"));
                length.add((int)rs.getFloat("LENGTH"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            if (stmt != null) { try {stmt.close();} catch (SQLException ex) {ex.printStackTrace();}} //close connection
        }
        
        int[][] output = new int[3][sequence.size()];
        //convert arraylist to
        output[0]=convertIntegers(sequence);
        output[1]=convertIntegers(mood);
        output[2]=convertIntegers(length);
        return output;
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
}
