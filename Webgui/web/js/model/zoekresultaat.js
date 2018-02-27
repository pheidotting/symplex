define(['knockout'],
	function (ko) {

	return function(data){
		var _this = this;

		_this.id = ko.observable();
		_this.naam = ko.observable();
		_this.geboortedatum = ko.observable();
		_this.adres = ko.observable();
		_this.postcode = ko.observable();
		_this.plaats = ko.observable();
		_this.identificatie = ko.observable();
		_this.soortEntiteit = ko.observable();
    };
});