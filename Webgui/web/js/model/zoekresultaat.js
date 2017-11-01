define(['knockout'],
	function (ko) {

	return function(data){
		var _this = this;

		this.id = ko.observable();
		this.naam = ko.observable();
		this.geboortedatum = ko.observable();
		this.adres = ko.observable();
		this.postcode = ko.observable();
		this.plaats = ko.observable();
		this.identificatie = ko.observable();
		this.soortEntiteit = ko.observable();
    };
});