package MyComponent;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;
import elemental.json.JsonArray;

//@JavaScript({"/VAADIN/MyComponent.js", "/VAADIN/MyComponentConnector.js"})
//@JavaScript(value = {"vaadin://MyComponent.js", "vaadin://MyComponentConnector.js"})
@JavaScript(value = {"MyComponent.js", "MyComponentConnector.js"})
public class MyComponent extends AbstractJavaScriptComponent {
	
	public MyComponent() {
		
            this.addFunction("onClick", null);
		// Implements the onClick method that is to be called by client-side
		// Javascript code in MyComponent.js.  It gets the value from 
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
    protected MyComponentState getState() {
        return (MyComponentState) super.getState();
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
    
    public void jsMethod() {
        callFunction("jsMethod");  // the name of the function here has to match with the
    }                              // name that is specified in the connector
}
