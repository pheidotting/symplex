define(['knockout',
        'moment'],
	function (ko, moment) {

	return function polisModel () {
		var _this = this;

		_this.identificatie = ko.observable();
		_this.entiteitId = ko.observable();
		_this.soortEntiteit = ko.observable();
		_this.status = ko.observable();
		_this.polisNummer = ko.observable().extend({required: true});
		_this.kenmerk = ko.observable();
        _this.ingangsDatum = ko.observable().extend({required: true});
        _this.eindDatum = ko.observable();
        _this.wijzigingsDatum = ko.observable();
        _this.prolongatieDatum = ko.observable();
		_this.maatschappij = ko.observable();//.extend({required: true});
		_this.soort = ko.observable().extend({required: true});
		_this.premie = ko.observable();
		_this.betaalfrequentie = ko.observable();
		_this.dekking = ko.observable();
		_this.verzekerdeZaak = ko.observable();
		_this.bedrijf = ko.observable();
		_this.bedrijfsId = ko.observable();
		_this.omschrijvingVerzekering = ko.observable();
//		_this.idDiv = ko.computed(function() {
//	        return "collapsable" + _this.id();
//		}, this);
//		_this.idDivLink = ko.computed(function() {
//	        return "#collapsable" + _this.id();
//		}, this);
//		_this.className = ko.computed(function() {
//			var datum = moment(_this.ingangsDatum());
//			var tijd = moment(datum).fromNow();
//			if(tijd.substr(tijd.length - 3) !== "ago") {
//				return "polisNietActief panel-title";
//			}
//			if(_this.eindDatum()) {
//				datum = moment(_this.eindDatum());
//				tijd = moment(datum).fromNow();
//				if(tijd.substr(tijd.length - 3) === "ago") {
//					return "polisBeeindigd panel-title";
//				}else{
//					return "panel-title";
//				}
//			}
//		}, this);
		_this.titel = ko.computed(function () {
			return _this.soort() + " (" + _this.polisNummer() + ")";
		}, this);
    };
});