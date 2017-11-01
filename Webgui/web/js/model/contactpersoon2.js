define(['jquery',
        'commons/commonFunctions',
        'model/telefoonNummer2',
         'knockout',
         'commons/3rdparty/log',
         'viewmodel/common/telefoonnummer-viewmodel'],
	function ($, commonFunctions, TelefoonNummer, ko, log, telefoonnummerViewModel) {

	return function(){
	    var _this = this;

        _this.identificatie = ko.observable();
		_this.voornaam = ko.observable();
		_this.tussenvoegsel = ko.observable();
		_this.achternaam = ko.observable();
		_this.emailadres = ko.observable();
		_this.bedrijf = ko.observable();
		_this.telefoonnummers = ko.observableArray();
		_this.functie = ko.observable();

		_this.verwijderTelefoonNummer = function(telefoon) {
			log.debug("Verwijderen telefoon " + ko.toJSON(telefoon));
			_this.telefoonnummers.remove(function (item) {
			    log.debug(ko.toJSON(item));
				return item.telefoonnummer() === telefoon.telefoonnummer() && item.soort() === telefoon.soort();
			});
			_this.telefoonnummers.valueHasMutated();
		};

		_this.voegTelefoonNummerToe = function() {
			_this.telefoonnummers().push(new TelefoonNummer(""));
			_this.telefoonnummers.valueHasMutated();
		};

        _this.telefoonnummersModel  = new telefoonnummerViewModel(false, 'CONTACTPERSOON', _this.identificatie(), _this.telefoonnummers());

    };
});