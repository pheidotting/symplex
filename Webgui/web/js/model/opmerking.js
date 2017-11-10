define(['knockout'],
	function (ko) {

	return function(){
		var _this = this;

		_this.id = ko.observable();
		_this.identificatie = ko.observable();
		_this.opmerking = ko.observable();
		_this.tijd = ko.observable();
		_this.medewerker = ko.observable();
		_this.medewerkerId = ko.observable();
		_this.soortEntiteit = ko.observable();
		_this.entiteitId = ko.observable();
		_this.parentIdentificatie = ko.observable();

		_this.idIsNull = ko.computed(function(){
		    return _this.identificatie() == null;
		})
		_this.idIsNietNull = ko.computed(function(){
		    return _this.identificatie() != null;
		})
    };
});