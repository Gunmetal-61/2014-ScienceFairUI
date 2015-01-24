com_example_vaadin3_MyComponent =
	function() {
	    // Create the component
	    var mycomponent = new mylibrary.MyComponent(this.getElement());
	    
	    // Handle changes from the server-side.
	    //
	    // It looks like when state change happens, this is the function that handles
	    // the synchronization of server-side state implemented in MyComponentState.java
	    // with the Javascript client side state.  In other words, this function copies
	    // the value of the server state object (MyComponentState) to the client side
	    // Javascript object.
	    this.onStateChange = function() {
	        mycomponent.setValue(this.getState().value);
	    };

	    // Handle event from client side.
	    //
	    // Pass user interaction to the server-side by invoking the onClick
	    // function that is declared in MyComponent.java's class constructor.
	    var self = this;
	    mycomponent.click = function() {
	        self.onClick(mycomponent.getValue());
	        self.newFunc1();
	    };
	};
	