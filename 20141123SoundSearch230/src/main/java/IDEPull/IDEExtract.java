/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package IDEPull;

import Database.DatabaseAccess;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;
import com.mycompany.soundsearch230.MyVaadinUI;
import com.mycompany.soundsearch230.SearchResultPage;
import static com.mycompany.soundsearch230.SearchResultPage.nameIdentifier;
import java.io.File;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mitchell
 */

public class IDEExtract {
    //needs the mp3agic library: https://github.com/mpatric/mp3agic
     
    public IDEExtract() {
        
    }
    
    public String findFile() {
        String playThisFile = null;
        System.out.println("'findFile' method (Wavesurfer.java) started.  Retrieving selected song [" + nameIdentifier + "]'s file.");
        try {
            playThisFile = DatabaseAccess.songdir(MyVaadinUI.con, null, SearchResultPage.nameIdentifier, SearchResultPage.artistIdentifier);
            System.out.println("Song file [" + playThisFile + "] retrieved.");
        } catch (SQLException ex) {
            System.out.println("'findFile' method (Wavesurfer.java) failed.");
            Logger.getLogger(SearchResultPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return playThisFile;
    }
      
    
    public RandomAccessFile getAlbumArt(String playThisFile) {
        Mp3File mp3file = null;
        RandomAccessFile file = null;
        try {
            mp3file = new Mp3File("G:\\dev\\apache-tomcat-8.0.17\\webapps\\ROOT\\Songs\\mp3\\" + playThisFile);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("wuioegbefghweoufgiwerufiowehguiweypgiow");
        }

        if (mp3file.hasId3v2Tag()) { //gets album image
            ID3v2 id3v2Tag = mp3file.getId3v2Tag();
            byte[] data = id3v2Tag.getAlbumImage(); //byte array of image: convert this to BufferedImage
            if (data != null) {
              String mimeType = id3v2Tag.getAlbumImageMimeType();
              // Write image to file - can determine appropriate file extension from the mime type

                try {
                    file = new RandomAccessFile("F:\\Jeffrey\\Desktop\\Science Project 2014-2015\\UI\\2014-ScienceFairUI\\20141123SoundSearch230\\album-artwork", "rw");
                    file.write(data);
                    file.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return file;
    }
    
}
