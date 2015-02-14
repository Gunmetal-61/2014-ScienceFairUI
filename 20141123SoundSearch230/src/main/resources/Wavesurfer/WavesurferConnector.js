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
                waveColor: 'LightSkyBlue',
                progressColor: 'RoyalBlue',
                scrollParent: false,
                hideScrollbar: true
            });
            
            waveContainer = this.getElement();
            
            wavesurfer.on('ready', function() {
                var timeline = Object.create(WaveSurfer.Timeline);
                
                console.log("Element = " + waveContainer);

                timeline.init({
                    wavesurfer: wavesurfer,
                    container: waveContainer
                });
            });
            
            this.fileLoader2 = function() {
                console.log("fileLoader2() starts.");
            };
            
            this.fileLoader = function(playThisFile) {    
                console.log("ERIOHGOIERHGOERHGLERHGIOHRGHEROIGHOSDNVNEFGHLSZJLNVHKLDSJGKLSNGOWEHIOWRJGJFKLHGILFDJBKLDFJBKLXCVHL;BJDNGOAWHTIOEWJGLKDFNBGJIOERHGOJEFKLGHWRIOAGJLDFKBHIOERHGKLANGEFIGJKLWRGNMKLFBHIOREJBVK.CXHBILXDJBKLEFJGHERPIOGJHERIOGLERJGERHGIOERJGKLFJLOER" + playThisFile);
                wavesurfer.load ('/Songs/mp3/' + playThisFile);
            };
            
            this.pauseOrPlay = function () {
                wavesurfer.playPause();
            };
            
            this.play = function () {
                wavesurfer.play();
            };
            
            this.pause = function() {
                wavesurfer.pause();
            };
            
            this.trackerTime = function (timeProgress) {
                wavesurfer.getCurrentTime(timeProgress);
            }
            
            this.silenceMute = function () {
                wavesurfer.toggleMute();
            };
            
            this.resetStop = function () {
                wavesurfer.stop();
            };
            
            this.setterVolume = function (newVolume) {
                wavesurfer.setVolume(newVolume);
            };
            
            
            this.speedPlay = function (rate) {
                wavesurfer.setPlaybackRate(rate);
            }
            
            this.addRegion = function (start, end, mood) {
                console.log("entered function");
                console.log(start);
                console.log(end);
                console.log(mood);
                var color = "rgba(0,0,0,0.1)";
                switch(mood){
                    case 0: //love, inspiration
                        color = "rgba(25,181,254,0.3)";
                    break;
                    case 1: //fascination, admiration
                        color = "rgba(248,148,6,0.3)";  
                    break;
                    case 2: //satisfaction, relaxed
                        color = "rgba(253,227,167,0.3)";
                    break;
                    case 3: //calm, awaiting
                        color = "rgba(200,147,197,0.3)";
                    break;
                    case 4: //boredom, sadness
                        color = "rgba(191,191,191,0.3)";
                    break;
                    case 5: //disappointment, jealousy
                        color = "rgba(154,18,179,0.3)";
                    break;
                    case 6: //irritation, disgust
                        color = "rgba(242,38,19,0.3)"; //red
                    break;
                    case 7: //astonishment, curiosity
                        color = "rgba(246,71,71,0.3)"; 
                    break;
                }
                console.log(color);
                wavesurfer.on('ready', function() {
                    wavesurfer.addRegion({
                        start: start,
                        end: end,
                        drag: false,
                        resize: false,
                        color: color
                    });
                });
            }
            
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
	