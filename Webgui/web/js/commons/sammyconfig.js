define(['jquery',
        'sammy'],
//        'StackTrace'],
	function($, Sammy) {//}, StackTrace) {
		return function() {
            window.onerror = function (errorMsg, url, lineNumber, column, errorObj) {
                console.log('Error: ' + errorMsg + ' Script: ' + url + ' Line: ' + lineNumber + ' Column: ' + column + ' StackTrace: ' +  errorObj);

//                StackTrace.fromError(errorObj).then(function(error){
                    var xhr = new XMLHttpRequest();
                    var fd = new FormData();

                    fd.append( 'logger', '' );
                    fd.append( 'timestamp', new Date().getTime() );
                    fd.append( 'level', 'ERROR' );
                    fd.append( 'url', url );
                    fd.append( 'message', errorMsg );
                    fd.append( 'layout', 'HttpPostDataLayout' );

                    xhr.open( 'POST', 'dejonge/rest/authorisatie/log4j/log4javascript', true );
                    xhr.send( fd );
//                });
            }

			var _this = this;

			this.app = new Sammy('body');

			 this.addView = function(hash, view){
				_this.app.route('GET', '#' + hash, function() {
					require([view], function (view) {
						var hashSplit = hash.split('/');
						var paramSplit = window.location.hash.replace('#' + hashSplit[0] + '/', '').split('/');

						var params = {};
						if(hashSplit.length > 1) {
							$.each(paramSplit, function(index, item){
								params[hashSplit[index + 1].replace(':', '')] = paramSplit[index];
							});
						}

						view.init(params);
					});
				});
			},

			this.run = function(initialView) {
				_this.app.raise_errors = true;
				_this.app.run((initialView == undefined) ? window.location.hash : initialView);
			};
	    };
	}
);