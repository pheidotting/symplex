define(['knockout',
        'moment'],
        function(ko, moment) {

	return function hypotheekPakket() {
		var _this = this;

		_this.id = ko.observable();
		_this.totaalBedrag = ko.observable();
		_this.titel = ko.observableArray();
		_this.hypotheken = ko.observableArray();

		_this.idDiv = ko.computed(function() {
	        return "collapsableP" + _this.id();
		}, this);
		_this.idDivLink = ko.computed(function() {
	        return "#collapsableP" + _this.id();
		}, this);
		_this.className = ko.computed(function() {
		    if(_this.ingangsDatum != null) {
                var datum = moment(_this.ingangsDatum());
                var tijd = moment(datum).fromNow();
                if(tijd.substr(tijd.length - 3) == "ago"){
                    return "panel-title";
                }else{
                    return "polisNietActief panel-title";
                }
			}
		}, this);
	};
});