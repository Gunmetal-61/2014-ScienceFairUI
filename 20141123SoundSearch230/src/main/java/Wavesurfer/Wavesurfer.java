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
        
        
//	    this.addFunction("onClitck", new JavaScriptFunction() {
//			@Override
//			public void call(JsonArray arguments) {
//				System.out.println("Invoking server-side onClick() method.");				
//				getState().value = arguments.getString(0);
////	            getState().setValue(arguments.getString(0));
//	            for (ValueChangeListener listener: listeners)
//	                listener.valueChange();				
//			}
//	    });



        this.addFunction("onClitck", null);
            // Implements the onClick method that is to be called by client-side
            // Javascript code in Wavesurfer.js.  It gets the value from 
            // client side and invoke all the value change listeners that have
            // been registered.
        this.addFunction("onClitck", new JavaScriptFunction() {
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
    
   

    
    public void loadPlayFile(String playThisFile) {
        callFunction("fileLoader", playThisFile);  
        callFunction("fileLoader2");
    }      
    
    public void playOrPause() {
        callFunction("pauseOrPlay");
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

    
    public void volumeSetter(double newVolume) {
        callFunction("setterVolume", newVolume);
    }
    
    String rate;
    
    public void playSpeed() {
        callFunction("speedPlay", rate);
        return ;
    }
    
    public void loadRegions(){
        int time = 0; //cumulative time
        int[][] subsong = DatabaseAccess.retrieveSubSong(con, SearchResultPage.nameIdentifier, SearchResultPage.artistIdentifier);
        for(int i = 0; i<subsong[0].length; i++){
            callFunction("addRegion",time,time+subsong[2][i],subsong[1][i]);
            time += subsong[2][i];
            System.out.println(time);
        }
    }
}
