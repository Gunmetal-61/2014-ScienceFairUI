package Wavesurfer;

import Database.DatabaseAccess;
import MyComponent.*;
import com.mycompany.soundsearch230.SearchResultPage;
import static com.mycompany.soundsearch230.SearchResultPage.artistIdentifier;
import static com.mycompany.soundsearch230.SearchResultPage.nameIdentifier;
import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;
import elemental.json.JsonArray;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

//@JavaScript({"/VAADIN/MyComponent.js", "/VAADIN/MyComponentConnector.js"})
//@JavaScript(value = {"vaadin://MyComponent.js", "vaadin://MyComponentConnector.js"})
@JavaScript(value = {"wavesurfer.js", "drawer.js", "drawer.canvas.js", "wavesurfer.regions.js", "audioelement.js", "webaudio.js", "WavesurferConnector.js", "wavesurfer.timeline.js"})
public class Wavesurfer extends AbstractJavaScriptComponent {
	
    Connection con = DatabaseAccess.startconnection("orcl"); 
    public Wavesurfer() {
        
        this.addFunction("onClick", null);
            // Implements the onClick method that is to be called by client-side
            // Javascript code in Wavesurfer.js.  It gets the value from 
            // client side and invoke all the value change listeners that have
            // been registered.
        this.addFunction("onClick", new JavaScriptFunction() {
                    @Override
                    public void call(JSONArray arguments) throws JSONException {
                            System.out.println("Invoking server-side onClick() method.");				
                            getState().value = arguments.getString(0);
//	            getState().setValue(arguments.getString(0));
                for (ValueChangeListener listener: listeners)
                    listener.valueChange();				
                    }
        });

        addFunction("newFunc1", new JavaScriptFunction() {
                    @Override
                    public void call(JSONArray arguments) {
                            System.out.println("Invoking server-side newFunc1() method.");				
                    }	    	
        });
    }
	
    // Helper method to access the state object.  This function is needed in
    // all Vaadin Javascript component wrapper code.
    @Override
    protected WavesurferState getState() {
        return (WavesurferState) super.getState();
    }

    public interface ValueChangeListener extends Serializable {
        void valueChange();
    }
    
    ArrayList<ValueChangeListener> listeners = new ArrayList<ValueChangeListener>();
    public void addValueChangeListener(ValueChangeListener listener) {
        listeners.add(listener);
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////
    //
    // Add additional component methods below.
    //
    ///////////////////////////////////////////////////////////////////////////////////////
        
    public void setValue(String value) {
        getState().value = value;
    }
    
    public String getValue() {
        return getState().value;
    }
    
   

    /**
     * Load song to play
     * @param playThisFile 
     */
    public void loadPlayFile(String playThisFile) {
        callFunction("fileLoader", playThisFile);  
        callFunction("fileLoader2");
    }      
    
    public void playOrPause(boolean play) {
        if(play){
            callFunction("play");
        } else {
            callFunction("pause");
        }
    }
    
//    public void timeTracker() {
//        callFunction("trackerTime", );
//        return ;
//    }
    public void muteSilence() {
        callFunction("silenceMute");
    }
    public void stopReset() {
        callFunction("resetStop");
    }

    /**
     * Change playback volume
     * 0 is mute, 1 is max
     * @param newVolume 
     */
    public void volumeSetter(double newVolume) {
        callFunction("setterVolume", newVolume);
    }
    
    /**
     * Change playback rate
     * 0.5 is half speed, 2 is double
     * @param rate 
     */
    public void playSpeed(double rate) {
        callFunction("speedPlay", rate);
        return ;
    }
    
    /**
     * Load Subsong Regions
     * @param title Title of song
     * @param artist Artist of song
     */
    public void loadRegions(String title, String artist){
        int time = 0; //cumulative time
        int[][] subsong = DatabaseAccess.retrieveSubSong(con, title, artist);
        for(int i = 0; i<subsong[0].length; i++){
            callFunction("addRegion",time,time+subsong[2][i],subsong[1][i]);
            time += subsong[2][i];
            //System.out.println(time);
        }
    }
}
