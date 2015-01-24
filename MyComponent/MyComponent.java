package com.example.vaadin3;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

@JavaScript({"MyComponent.js", "MyComponentConnector.js"})
public class MyComponent extends AbstractJavaScriptComponent {
	
	public MyComponent() {
		
		// Implements the onClick method that is to be called by client-side
		// Javascript code in MyComponent.js.  It gets the value from 
		// client side and invoke all the value change listeners that have
		// been registered.
	    addFunction("onClick", new JavaScriptFunction() {
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
			public void call(JSONArray arguments) throws JSONException {
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
}
