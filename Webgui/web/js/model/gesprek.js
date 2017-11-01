define(['jquery',
         'knockout'],
	function ($, ko) {

	return function(data){
	    var _this = this;

        _this.bestandsnaam = ko.observable();
		_this.tijdstip = ko.observable();
		_this.telefoonnummer = ko.observable();
		_this.uitgaand = ko.observable();
		_this.inkomend = ko.observable();
    };
});