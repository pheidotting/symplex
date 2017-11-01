define(['knockout'],
	function (ko) {

	return function bijlageModel (){
		_this = this;

		_this.id = ko.observable();
		_this.identificatie = ko.observable();
		_this.url = ko.computed(function() {
			return "../dejonge/rest/medewerker/bijlage/download?id=" + _this.identificatie();
		}, this);
		_this.bestandsNaam = ko.observable();
		_this.omschrijving = ko.observable();
		_this.datumUpload = ko.observable();
		_this.soortEntiteit = ko.observable();
		_this.entiteitId = ko.observable();
		_this.omschrijvingOfBestandsNaam = ko.observable();
		_this.parentIdentificatie = ko.observable();
    };
});