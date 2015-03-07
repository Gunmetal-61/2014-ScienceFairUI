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
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author mitchell
 */

public class IDEExtract {
    //needs the mp3agic library: https://github.com/mpatric/mp3agic
     
    public IDEExtract() {
        
    }
    
    /**
     * Gets the file name for a specific song
     * 
     * @param title Title of song
     * @param artist Artist of song
     * @return 
     */
    public String findFile(String title, String artist) {
        String playThisFile = null;
        System.out.println("'findFile' method (Wavesurfer.java) started.  Retrieving selected song [" + nameIdentifier + "]'s file.");
        try {
            playThisFile = DatabaseAccess.songdir(MyVaadinUI.con, null, title, artist);
            System.out.println("Song file [" + playThisFile + "] retrieved.");
        } catch (SQLException ex) {
            System.out.println("'findFile' method (Wavesurfer.java) failed.");
            Logger.getLogger(SearchResultPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return playThisFile;
    }
    
    /**
     * Gets the album art from an MP3 file
     * 
     * @param playThisFile MP3 file name
     * @return Album art image (Vaadin image, not Java image)
     */
    public Image getAlbumArt(String playThisFile) {
        Mp3File mp3file = null;
        //RandomAccessFile file = null;
        Image img = null;
        try {
//            mp3file = new Mp3File("G:\\dev\\apache-tomcat-8.0.17\\webapps\\ROOT\\Songs\\mp3\\" + playThisFile);
            mp3file = new Mp3File("/usr/local/apache-tomcat-7.0.41/webapps/ROOT/Songs/mp3/" + playThisFile);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("MP3 couldn't be loaded");
            return null;
        }

        if (mp3file.hasId3v2Tag()) { //gets album image
            ID3v2 id3v2Tag = mp3file.getId3v2Tag();
            final byte[] data = id3v2Tag.getAlbumImage(); //byte array of image: convert this to BufferedImage
            if (data != null) {
                try {
                    img = new Image(null, new StreamResource( //save as Vaadin image
                        new StreamResource.StreamSource(){
                            public InputStream getStream(){
                                return new ByteArrayInputStream(data);
                            }
                        }, ""));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return img;
    }
}
