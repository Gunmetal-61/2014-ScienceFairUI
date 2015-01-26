Wavesurfer_Wavesurfer =
	function() {
	    // Create the component
            
            var wavesurfer = Object.create(WaveSurfer);
      //      var element = $(this.getElement());
      //      var wavesurfer = element.create();
            
//            wavesurfer = new WaveSurfer.MyComponent(this.getElement());
//	    var mycomponent = new mylibrary.MyComponent(this.getElement());
	    wavesurfer.init({
                container: this.getElement(),
                waveColor: 'violet',
                progressColor: 'purple',
                scrollParent: false,
                hideScrollbar: true
            });
            
            console.log("Call wavesurfer.load() to load music file.");
//            wavesurfer.load('/mp3/1-01LetthePraiseBegin.m4a');

//            var songDirector = new String()


//            wavesurfer.load('/abc.mp3');
//            wavesurfer.load('/117.wav');
//            wavesurfer.load('/mp3/12 117.m4a');
            
            this.fileLoader2 = function() {
                console.log("fileLoader2() starts.");
            }
            
            this.fileLoader = function(fileDirector) {
                console.log("ERIOHGOIERHGOERHGLERHGIOHRGHEROIGHOSDNVNEFGHLSZJLNVHKLDSJGKLSNGOWEHIOWRJGJFKLHGILFDJBKLDFJBKLXCVHL;BJDNGOAWHTIOEWJGLKDFNBGJIOERHGOJEFKLGHWRIOAGJLDFKBHIOERHGKLANGEFIGJKLWRGNMKLFBHIOREJBVK.CXHBILXDJBKLEFJGHERPIOGJHERIOGLERJGERHGIOERJGKLFJLOER" + fileDirector);
                wavesurfer.load ('/Songs/wav/' + fileDirector);
                wavesurfer.on('ready', function() {
                    wavesurfer.play();
                });
            };
            
	    // Handle changes from the server-side.
	    //
	    // It looks like when state change happens, this is the function that handles
	    // the synchronization of server-side state implemented in MyComponentState.java
	    // with the Javascript client side state.  In other words, this function copies
	    // the value of the server state object (MyComponentState) to the client side
	    // Javascript object.
	    this.onStateChange = function() {
//	        mycomponent.setValue(this.getState().value);
	    };
            wavesurfer.click 
	};
	