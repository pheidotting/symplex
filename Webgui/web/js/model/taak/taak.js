define([ "commons/3rdparty/log",
         "commons/validation",
         "commons/opmaak",
         "knockout",
         'dataServices',
         'redirect'],
	function(logger, validation, opmaak, ko, dataServices, redirect) {

	return function taak(data) {
		_this = this;

		_this.id = ko.observable(data.id);
		_this.soortTaak = ko.observable(data.soortTaak);
		_this.datumTijdCreatie = ko.observable(data.datumTijdCreatie);
		_this.datumTijdOppakken = ko.observable(data.datumTijdOppakken);
		_this.datumTijdAfgerond = ko.observable(data.datumTijdAfgerond);
		_this.eindDatum = ko.observable(data.eindDatum);
		_this.aangemaaktDoor = ko.observable(data.aangemaaktDoor);
		_this.opgepaktDoor = ko.observable(data.opgepaktDoor);
		_this.gerelateerdAan = ko.observable(data.gerelateerdAan);
		_this.omschrijving = ko.observable(data.omschrijving);
		_this.status = ko.observable(data.status);
		_this.mijnTaak = ko.observable(data.mijnTaak);

		_this.vrijgeven = function(taak){
			logger.debug("vrijgeven taak met id " + taak.id());
			dataServices.vrijgevenTaak(taak.id());
		};
		_this.oppakken = function(taak){
			logger.debug("oppakken taak met id " + taak.id());
			dataServices.oppakkenTaak(taak.id());
			redirect.redirect('TAAK', taak.id());
		};
	};
});