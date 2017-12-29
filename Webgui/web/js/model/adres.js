define(['jquery',
         'knockout'],
	function ($, ko) {

	return function(data){
	    var _this = this;

        _this.id = ko.observable();
		_this.straat = ko.observable();
		_this.huisnummer = ko.observable().extend({ number: true});
		_this.toevoeging = ko.observable();
		_this.postcode = ko.observable();
        _this.soortAdres = ko.observable();
		_this.plaats = ko.observable();
		_this.soortEntiteit = ko.observable();
		_this.entiteitId = ko.observable();
		_this.parentIdentificatie = ko.observable();
		_this.identificatie = ko.observable();
    };
});