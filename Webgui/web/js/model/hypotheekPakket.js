define(['jquery',
        'knockout',
         "commons/3rdparty/log",
         "commons/validation",
         "commons/opmaak",
         'moment',
         "js/model/hypotheek"],
         function($, ko, logger, validation, opmaak, moment, Hypotheek) {

	return function hypotheekPakket(data) {
		var _this = this;

		_this.bedrag = function(bedrag){
			return opmaak.maakBedragOp(ko.utils.unwrapObservable(bedrag));
		}

		_this.id = ko.observable(data.id);
		_this.totaalBedrag = ko.observable(data.totaalBedrag);
		_this.titel = ko.observableArray();

		_this.hypotheken = ko.observableArray();
		$.each(data.hypotheken, function(i, item){
			var hypotheek = new Hypotheek(item);

			_this.hypotheken.push(hypotheek);
		});

		_this.idDiv = ko.computed(function() {
	        return "collapsableP" + data.id;
		}, this);
		_this.idDivLink = ko.computed(function() {
	        return "#collapsableP" + data.id;
		}, this);
		_this.className = ko.computed(function() {
			var datum = moment(data.ingangsDatum);
			var tijd = moment(datum).fromNow();
			if(tijd.substr(tijd.length - 3) == "ago"){
				return "panel-title";
			}else{
				return "polisNietActief panel-title";
			}
		}, this);
	};
});