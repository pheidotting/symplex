define(['knockout'],
	function (ko) {

	return function(){
		var _this = this;

		_this.id = ko.observable();
		_this.naam = ko.observable().extend({required: true});
		_this.kvk = ko.observable();
		_this.rechtsvorm = ko.observable();
		_this.email = ko.observable();
		_this.internetadres = ko.observable();
		_this.hoedanigheid = ko.observable();
		_this.cAoVerplichtingen = ko.observable();
		_this.contactpersonen = ko.observableArray();
		_this.soortEntiteit = ko.observable('BEDRIJF');
		_this.identificatie = ko.observable();

		_this.idDiv = ko.computed(function() {
	        return "collapsable" + _this.id();
		}, this);
		_this.idDivLink = ko.computed(function() {
	        return "#collapsable" + _this.id();
		}, this);
    };
});