/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import com.mycompany.soundsearch230.Utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jeffrey
 */
public class DatabaseAccess {
    /**
     * Gets a connection to the database
     * 
     * @param database Name of the database
     * @return Connection
     */
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

    /**
     * Retrieve selected song's subsong mood values to color it's waveform graph appropriately
     * @deprecated 
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
      
    /**
     * Gets search results based on user queries
     * 
     * @param con Database connection
     * @param generalq General query
     * @param mood Array of moods in the output
     * @param length Length of the song in seconds
     * @param name Name of the song
     * @param artist Artist of song
     * @param album Song album
     * @param genre Genre of song
     * @param year Song release year
     * @param moodLevel For specifying full song or subsong mood search (0 is full song, 1 is subsong)
     * @return
     */
    public static DBRow[] getSearchResults(Connection con, String generalq, int[] mood, int length, String name, String artist, String album, String genre, String year, int moodLevel){
        DBRow[] output = null;
        //  Declare Query Strings  
        try {
            String generalQuery = "";//string to create sql query over all types of data
            String namesQuery = "";//string from name of song query field sent to sql command line
            String artistsQuery = "";//string from name of artist query field sent to sql command line
            String albumsQuery = "";
            String lengthsQuery = "";//string from length of song query field (search format: min":"secs) sent to sql command line
            String yearsQuery = "";
            String genresQuery = "";
            String moodsQuery = "";//string from mood query checkboxes sent to sql command line
            String lyricsQuery = "";
            
            generalq = generalq.toLowerCase();
            System.out.println("General Query: " + generalq);
            name = name.toLowerCase();
            artist = artist.toLowerCase();
            album = album.toLowerCase();
            genre = genre.toLowerCase();

            Statement stmt = null;
            ////////////////////////////////////////////////////////////////////
//          SQL QUERY FOR SEARCH ACROSS ALL FIELDS (A GENERAL SEARCH):
            if(!generalq.equals("")){
                generalQuery = "WHERE LOWER(TITLE) LIKE '%" +  generalq
                + "%' OR LOWER(ARTISTNAME) LIKE '%" + generalq + "%' OR LOWER(ALBUMNAME) LIKE '%" 
                + generalq + "%' OR SLENGTH LIKE '%" + generalq 
                + "%' OR ACTUALYEAR LIKE '%" + generalq + "%' OR LOWER(GENRENAME) LIKE '%" 
                + generalq + "%' OR AUDIOMOOD LIKE '%" + generalq 
                + "%'";
                
            }

            ////////////////////////////////////////////////////////////////////
//          SQL QUERY FOR SEARCHING SONGS WITH NAME(aka. TITLE) OF SONG:             
            if(!name.equals("")){
                namesQuery = " AND LOWER(TITLE) LIKE '%"+name+"%'";
                if(moodsQuery.equals("")&&lengthsQuery.equals("")&&generalQuery.equals("")&&yearsQuery.equals("")&&genresQuery.equals("")&&albumsQuery.equals("")&&artistsQuery.equals("")){
                    namesQuery = "WHERE LOWER(TITLE) LIKE '%"+name+"%'";
                }
            }        

            ////////////////////////////////////////////////////////////////////
//          SQL QUERY FOR SEARCHING SONGS WITH ARTIST NAME:        
            if(!artist.equals("")){
                artistsQuery = " AND LOWER(ARTISTNAME) LIKE '%"+artist+"%'";
                if(moodsQuery.equals("")&&lengthsQuery.equals("")&&generalQuery.equals("")&&yearsQuery.equals("")&&genresQuery.equals("")&&albumsQuery.equals("")&&namesQuery.equals("")){
                    artistsQuery = "WHERE LOWER(ARTISTNAME) LIKE '%"+artist+"%'";
                }
            }

             ////////////////////////////////////////////////////////////////////
//          SQL QUERY FOR SEARCHING SONGS WITH ALBUM OF SONG: 
            if(!album.equals("")){
                albumsQuery = " AND LOWER(ALBUMNAME) LIKE '%"+album+"%'";
                if(moodsQuery.equals("")&&lengthsQuery.equals("")&&generalQuery.equals("")&&yearsQuery.equals("")&&genresQuery.equals("")&&namesQuery.equals("")&&artistsQuery.equals("")){
                    albumsQuery = "WHERE LOWER(ALBUMNAME) LIKE '%"+album+"%'";
                }
            }

            ////////////////////////////////////////////////////////////////////
//          SQL QUERY FOR SEARCHING SONGS WITH LENGTH OF SONG:    
            int upperlength = length + 30; //+/- 30 seconds around time
            int lowerlength = length - 30;
            if(lowerlength < 0){//i.e. if [lengthquery] is 20 seconds, if statment sets [lowerlength] leeway to -0 seconds
                lowerlength = 0;
            }       
            if(length != 0&&generalQuery.equals("")){//if the length of a song is specified
                lengthsQuery = "WHERE SLENGTH BETWEEN " + lowerlength + " AND " + upperlength;//[lengthsquery] becomes an SQL statement that searches for with specified song time as center of a field of +30 or -30 seconds
            }

            ////////////////////////////////////////////////////////////////////
//          SQL QUERY FOR SEARCHING SONGS WITH YEAR OF SONG: 
            if(!year.equals("")){
                yearsQuery = " AND ACTUALYEAR LIKE "+year;
                if(moodsQuery.equals("")&&lengthsQuery.equals("")&&generalQuery.equals("")&&genresQuery.equals("")&&albumsQuery.equals("")&&namesQuery.equals("")&&artistsQuery.equals("")){
                    yearsQuery = "WHERE ACTUALYEAR = "+year;
                }
            }

            ////////////////////////////////////////////////////////////////////
//          SQL QUERY FOR SEARCHING SONGS WITH GENRE OF SONG: 
            if(!genre.equals("")){
                genresQuery = " AND LOWER(GENRENAME) LIKE '%"+genre+"%'";
                if(moodsQuery.equals("")&&lengthsQuery.equals("")&&generalQuery.equals("")&&yearsQuery.equals("")&&albumsQuery.equals("")&&namesQuery.equals("")&&artistsQuery.equals("")){
                    genresQuery = "WHERE LOWER(GENRENAME) LIKE '%"+genre+"%'";
                }
            }


            ////////////////////////////////////////////////////////////////////
//          SQL QUERY FOR SEARCHING SONGS WITH MOOD             
            String moods = "";//new string 
            for(int i = 0; i<mood.length; i++){
                moods = moods + mood[i] +  ","; 
            }
            if(!moods.equals("")){//if mood isn't blank
                moods = moods.substring(0, moods.lastIndexOf(","));//get rid of last comma
                moodsQuery = " AND AUDIOMOOD IN ("+moods+")";
                if(genresQuery.equals("")&&lengthsQuery.equals("")&&generalQuery.equals("")&&yearsQuery.equals("")&&albumsQuery.equals("")&&namesQuery.equals("")&&artistsQuery.equals("")){
                    moodsQuery = "WHERE AUDIOMOOD IN ("+moods+")";
                }
            }
            

            ////////////////////////////////////////////////////////////////////
//          ASSEMBLE THE SQL QUERY:
//            The statements generated above, if they have content within them,
//            will be added to a single statement assembled here to address the
//            database for search.
            
            //counts the number results that will be returned
            String countQuery = "SELECT COUNT(*) AS \"ResultLength\" FROM SONGTABLE "
                    + "LEFT JOIN ARTISTS ON SONGTABLE.ARTISTID = ARTISTS.ARTISTID "
                    + "LEFT JOIN YEARS ON SONGTABLE.YEARID = YEARS.YEARID "
                    + "LEFT JOIN ALBUMS ON SONGTABLE.ALBUMID = ALBUMS.ALBUMID "
                    + "LEFT JOIN GENRES ON SONGTABLE.GENREID = GENRES.GENREID " 
                    + generalQuery + namesQuery + artistsQuery + albumsQuery 
                    + lengthsQuery + yearsQuery + genresQuery + moodsQuery + lyricsQuery;
            
            //actual query
            String query =
                    "SELECT DISTINCT SONGTABLE.RANKING, SONGTABLE.TITLE, SONGTABLE.AUDIOMOOD, "
                    + "SONGTABLE.SLENGTH, SONGTABLE.ARTISTID, ARTISTS.ARTISTNAME, "
                    + "YEARS.ACTUALYEAR, ALBUMS.ALBUMNAME, GENRES.GENRENAME FROM SONGTABLE "
                    + "LEFT JOIN ARTISTS ON SONGTABLE.ARTISTID = ARTISTS.ARTISTID "
                    + "LEFT JOIN YEARS ON SONGTABLE.YEARID = YEARS.YEARID "
                    + "LEFT JOIN ALBUMS ON SONGTABLE.ALBUMID = ALBUMS.ALBUMID "
                    + "LEFT JOIN GENRES ON SONGTABLE.GENREID = GENRES.GENREID " 
                    + generalQuery + namesQuery + artistsQuery + albumsQuery 
                    + lengthsQuery + yearsQuery + genresQuery + moodsQuery + lyricsQuery;
            
            if(!moods.equals("0,1,2,3,4,5,6,7")){ //if it isn't a general search
                query = query + " ORDER BY RANKING DESC";
            }
            
            //generate appropriate sections of the SQL statement based on which subsong moods are desired
            String subMoodsRelevant = "";
            String subMoodsWhere = "";
            for(int i = 0; i<mood.length; i++){
                if(i==0){ //for the first part of the string don't add the connectors
                    subMoodsRelevant = "\"" + mood[i] + "\""; 
                    subMoodsWhere = "\"" + mood[i] + "\" >= 1";
                } else {
                    subMoodsRelevant = subMoodsRelevant + "+\"" + mood[i] + "\""; //+ connector
                    subMoodsWhere = subMoodsWhere + " AND \"" + mood[i] + "\" >= 1"; //AND connector
                }
            }
            
            //counts number of results that will be returned by a subsong query
            String subsongCountQuery = 
                    "SELECT COUNT(*) AS \"ResultLength\" FROM SONGTABLE " 
                    + " LEFT JOIN ARTISTS ON SONGTABLE.ARTISTID = ARTISTS.ARTISTID " 
                    + " LEFT JOIN YEARS ON SONGTABLE.YEARID = YEARS.YEARID " 
                    + " LEFT JOIN ALBUMS ON SONGTABLE.ALBUMID = ALBUMS.ALBUMID " 
                    + " LEFT JOIN GENRES ON SONGTABLE.GENREID = GENRES.GENREID " 
                    + " LEFT JOIN (\n" 
                    + " SELECT TITLE, ARTISTID, \"0\", \"1\", \"2\", \"3\", \"4\", \"5\", \"6\", \"7\", (" + subMoodsRelevant + ") \"RELEVANTSECTIONS\" from (" 
                    + " select TITLE, ARTISTID, MOOD" 
                    + " from SUBSONGTABLE" 
                    + "   ) slt" 
                    + "   pivot (" 
                    + "     COUNT(MOOD)" 
                    + "     for MOOD in (0,1,2,3,4,5,6,7)" 
                    + "   ) pvt" 
                    + "   WHERE " + subMoodsWhere 
                    + " ) output " 
                    + " ON SONGTABLE.TITLE = output.TITLE AND SONGTABLE.ARTISTID = output.ARTISTID" 
                    + " WHERE RELEVANTSECTIONS >= 1 " 
                    + generalQuery + namesQuery + artistsQuery + albumsQuery 
                    + lengthsQuery + yearsQuery + genresQuery + lyricsQuery;
            
            //actual subsong query
            String subsongQuery = 
                    "SELECT DISTINCT OUTPUT.RELEVANTSECTIONS, SONGTABLE.RANKING, SONGTABLE.TITLE, SONGTABLE.AUDIOMOOD, "
                    + "SONGTABLE.SLENGTH, SONGTABLE.ARTISTID, ARTISTS.ARTISTNAME, "
                    + "YEARS.ACTUALYEAR, ALBUMS.ALBUMNAME, GENRES.GENRENAME FROM SONGTABLE " 
                    + " LEFT JOIN ARTISTS ON SONGTABLE.ARTISTID = ARTISTS.ARTISTID " 
                    + " LEFT JOIN YEARS ON SONGTABLE.YEARID = YEARS.YEARID " 
                    + " LEFT JOIN ALBUMS ON SONGTABLE.ALBUMID = ALBUMS.ALBUMID " 
                    + " LEFT JOIN GENRES ON SONGTABLE.GENREID = GENRES.GENREID " 
                    + " LEFT JOIN (" //This is the table that retrieves the songs with the correct subsong sections
                    + " SELECT TITLE, ARTISTID, \"0\", \"1\", \"2\", \"3\", \"4\", \"5\", \"6\", \"7\", (" + subMoodsRelevant + ") \"RELEVANTSECTIONS\" from (" 
                    + " select TITLE, ARTISTID, MOOD" 
                    + " from SUBSONGTABLE" 
                    + "   ) slt" 
                    + "   pivot (" //pivot table on moods
                    + "     COUNT(MOOD)" //count how many times a certain mood appears
                    + "     for MOOD in (0,1,2,3,4,5,6,7)" //these are moods values to count over (list how many of each type of mood there are in a song)
                    + "   ) pvt" 
                    + "   WHERE " + subMoodsWhere 
                    + "   ORDER BY RELEVANTSECTIONS DESC" 
                    + " ) output " 
                    + " ON SONGTABLE.TITLE = output.TITLE AND SONGTABLE.ARTISTID = output.ARTISTID" 
                    + " WHERE RELEVANTSECTIONS >= 1" //only get rows where there are relevant sections
                    + " ORDER BY RELEVANTSECTIONS DESC, RANKING DESC " //first order by number of relevant sections, and then by ranking
                    + generalQuery + namesQuery + artistsQuery + albumsQuery 
                    + lengthsQuery + yearsQuery + genresQuery + lyricsQuery;
            
            try {
                stmt = con.createStatement();   
                long startTime = System.nanoTime();
                if(moodLevel == 0){ //if doing a full song search
                    System.out.println(query);
                    ResultSet rs = stmt.executeQuery(countQuery);//find how many results there will be
                    int resultSize = 0;
                    if(rs.next()){
                        resultSize = rs.getInt("ResultLength");
                    }
                    if(resultSize != 0){ //if there will be results
                        rs = stmt.executeQuery(query); //run the actual query
                    } else {
                        return output;
                    }
                    long estimatedTime = System.nanoTime() - startTime; //see how long the process took
                    System.out.println(estimatedTime);

                    output = new DBRow[resultSize];
                    int i = 0;  
                    while(rs.next()&&(i<resultSize)) {
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
                }
                if(moodLevel == 1){ //if doing a subsong search
                    System.out.println(subsongQuery);
                    ResultSet rs = stmt.executeQuery(subsongCountQuery);//find how many results there will be
                    int resultSize = 0;
                    if(rs.next()){
                        resultSize = rs.getInt("ResultLength");
                    }
                    if(resultSize != 0){ //if there will be results
                        rs = stmt.executeQuery(subsongQuery); //run the actual query
                    } else {
                        return output;
                    }
                    long estimatedTime = System.nanoTime() - startTime; //see how long the process took
                    System.out.println(estimatedTime);

                    output = new DBRow[resultSize];
                    int i = 0;  
                    while(rs.next()&&(i<resultSize)) {
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
                }
            } catch (SQLException e) {
                System.err.println(e);
            } finally {
                if (stmt != null) { stmt.close(); } //close connection
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return output;
    }

    /**
     * Retrieve lyrics of selected song to be displayed in GUI box
     * 
     * @param con
     * @param title
     * @param artistname
     * @return
     * @throws SQLException 
     **/
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
      
    /**
     * Recalls filename of selected song from database to bring up for playing in GUI
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
  
    /**
     * RECALLS FILENAME OF SELECTED SONG'S ALBUM IMAGE FOR DISPLAY IN [SearchResultPage] and [SongResultPages]
     * 
     * @param con
     * @param newdir
     * @param title
     * @param artistname
     * @return
     * @throws SQLException 
     */
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

    /**
     * SETUP CODE USED TO PLACE AN ANALYZED SONG'S SUBSONG MOOD VALUES INTO THE ORACLE DATABASE TABLE 
     * NOT ACTIVE IN FINAL GUI PROGRAM  
     * @deprecated
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
     * Gets the subsong details of a song
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
                "SELECT SEQUENCE, MOOD, LENGTH FROM SUBSONGTABLE LEFT JOIN ARTISTS ON SUBSONGTABLE.ARTISTID = ARTISTS.ARTISTID WHERE ARTISTNAME = '" + artistname + "' AND TITLE = '"+ title +"' ORDER BY SEQUENCE";
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
        output[0] = Utilities.convertIntegers(sequence);
        output[1] = Utilities.convertIntegers(mood);
        output[2] = Utilities.convertIntegers(length);
        return output;
    }
    
    /**
     * Updates the rank of a song (and gets the rank of a song)
     * 
     * @param con
     * @param title
     * @param artistname
     * @param newLike 1 is like, -1 is dislike
     * @return  Song rank
     */
    public static float updateRank(Connection con, String title, String artistname, int newLike){
        title = title.replaceAll("'", "''");
        artistname = artistname.replaceAll("'", "''");
        
        double lowerBound = 0;
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT LIKES, DISLIKES FROM SONGTABLE LEFT JOIN ARTISTS ON SONGTABLE.ARTISTID = ARTISTS.ARTISTID "
                    + "WHERE TITLE = '" + title + "' AND ARTISTNAME = '" + artistname + "'");         
            float likes = 0;
            float dislikes = 0;
            while (rs.next()) {
                likes = rs.getInt("LIKES");
                dislikes = rs.getInt("DISLIKES");
            }  
                        
            if(newLike==1){
                ++likes;
            }
            if(newLike==-1){
                ++dislikes;
            }

            float sampleSize = likes+dislikes;
            if(sampleSize>=30){ //only apply ranking if the sample size is greater than 30
                float p = likes/sampleSize;
                lowerBound = p - 1.96*Math.sqrt((p*(1.0-p))/sampleSize); //calculate lower bound of 95% confidence interval
                //double upperBound = p + 1.96*Math.sqrt((p*(1-p))/sampleSize);
                System.out.println(title + ": " + lowerBound);
                System.out.println(p);
            }
            
            stmt.executeQuery("UPDATE SONGTABLE SET RANKING="+lowerBound+", LIKES="+likes+", DISLIKES="+dislikes+" "
                    + "WHERE TITLE = '"+title+"' "
                    + "AND ARTISTID = (SELECT ARTISTID FROM ARTISTS WHERE ARTISTNAME = '"+artistname+"')");
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            if (stmt != null) { try {stmt.close();} catch (SQLException ex) {ex.printStackTrace();}} //close connection
        }
        return (float)lowerBound;
    }
}
