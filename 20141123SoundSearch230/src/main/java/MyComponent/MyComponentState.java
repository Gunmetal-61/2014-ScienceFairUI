package MyComponent;

import com.vaadin.shared.ui.JavaScriptComponentState;

public class MyComponentState extends JavaScriptComponentState {
	
	// Places all the variables that are to be shared between client-side Javascript
	// component code and server-side Java component code here.
    public String value;
    public int value2;
    public boolean value3;
    
/*	No need for getter/setter since the class is used purely for carrying state info.
    public void setValue(String string) {
		value = string;
	}*/  
    
}