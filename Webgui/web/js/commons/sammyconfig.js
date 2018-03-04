define(['jquery',
        'sammy'],
	function($, Sammy) {
		return function() {
			var _this = this;

			this.app = new Sammy('body');

			 this.addView = function(hash, view){
				_this.app.route('GET', '#' + hash, function() {
					require([view], function (view) {
						var hashSplit = hash.split('/');
						var paramSplit = window.location.hash.replace('#' + hashSplit[0] + '/', '').split('/');

						var params = {};
						if(hashSplit.length > 1) {
							$.each(paramSplit, function(index){
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