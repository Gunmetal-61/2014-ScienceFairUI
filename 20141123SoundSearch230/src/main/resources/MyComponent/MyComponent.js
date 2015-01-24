// Define the namespace
var mylibrary = mylibrary || {};

mylibrary.MyComponent = function (element) {
	
	// This block of HTML code defines a text edit box with the name "value".
	element.innerHTML =
		"<div class='caption'>Hello, world!</div>" +
		"<div class='textinput'>Enter a value: " +
		"<input type='text' name='value'/>" +
		"<input type='button' value='Click'/>" +
		"</div>";

	// Style it
	element.style.border = "thin solid red";
	element.style.display = "inline-block";

	// Getter and setter for the value property in the text edit box defined in
	// HTML code above.
	this.getValue = function () {
		return element.getElementsByTagName("input")[0].value;
	};
	
	this.setValue = function (value) {
		element.getElementsByTagName("input")[0].value = value;
	};

	// Default implementation of the click handler
	this.click = function () {
		alert("Error: Must implement click() method");
	};

	// Set up button click.
	//
	// When a button is clicked on the browser, this function would call
	// the mycomponent.click() method in MyComponentConnector.js, which
	// in turns call server side code.
	var button = element.getElementsByTagName("input")[1];
	var self = this; // Assign "this" to "self" as "this" cannot be used
	                 // inside the function.
	button.onclick = function () {
		self.click();
	};
};


