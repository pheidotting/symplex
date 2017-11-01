define(['jquery',
        'model/bedrijf',
         'knockout',
         'commons/3rdparty/log',
         'commons/commonFunctions'],
	function ($, Bedrijf, ko, log, commonFunctions) {

	return function(data){
		var _thisBedrijven = this;

		_thisBedrijven.bedrijven = ko.observableArray();
		$.each(data, function(i, item) {
			log.debug(JSON.stringify(item));
			_thisBedrijven.bedrijven().push(new Bedrijf(item));
		});

		this.verwijderBedrijf = function(bedrijf){
			log.debug("Verwijderen Bedrijf met id " + bedrijf.id());
			commonFunctions.verbergMeldingen();
			var r=confirm("Weet je zeker dat je dit bedrijf wilt verwijderen?");
			if (r === true) {
				_thisBedrijven.bedrijven.remove(bedrijf);
				$.get( "../dejonge/rest/medewerker/bedrijf/verwijder", {"id" : bedrijf.id()}, function() {});
			}
	    };
    };
});