define(['jquery',
         'knockout',
         'commons/3rdparty/log',
         'commons/commonFunctions',
         'model/adres',
         'model/bijlage',
         'model/groepbijlages',
         'model/contactpersoon',
		 'dataServices',
		 'redirect',
         'fileUpload',
         'opmerkingenModel',
         'adressenModel',
         'telefoonnummersModel'],
	function ($, ko, log, commonFunctions, Adres, Bijlage, GroepBijlages, Contactpersoon, dataServices, redirect, fileUpload, opmerkingenModel, adressenModel, telefoonnummersModel) {

	return function(data){
		var _bedrijf = this;

		_bedrijf.opmerkingenModel = new opmerkingenModel(data.opmerkingen, null, null, null, null, data.id, null);
		_bedrijf.adressenModel = new adressenModel(data.adressen, data.id);
		_bedrijf.telefoonnummersModel = new telefoonnummersModel(data.telefoonnummers, data.id);

        _bedrijf.readOnly = ko.observable(false);
        _bedrijf.notReadOnly = ko.observable(true);

		_bedrijf.verwijderAdres = function(adres) {
			log.debug("Verwijderen adres " + ko.toJSON(adres));
			_bedrijf.adressenModel.adressen.remove(function (item) {
			    log.debug(ko.toJSON(item));
				return item.postcode() === adres.postcode() && item.huisnummer() === adres.huisnummer();
			});
			_bedrijf.adressenModel.adressen.valueHasMutated();
		};

		_bedrijf.id = ko.observable(data.id);
		_bedrijf.naam = ko.observable(data.naam).extend({required: true});
		_bedrijf.kvk = ko.observable(data.kvk);
		_bedrijf.rechtsvorm = ko.observable(data.rechtsvorm);
		_bedrijf.email = ko.observable(data.email);
		_bedrijf.internetadres = ko.observable(data.internetadres);
		_bedrijf.hoedanigheid = ko.observable(data.hoedanigheid);
		_bedrijf.cAoVerplichtingen = ko.observable(data.cAoVerplichtingen);
		_bedrijf.contactpersonen = ko.observableArray();
		if(data.contactpersonen != null){
			$.each(data.contactpersonen, function(i, item){
				var contactpersoon = new Contactpersoon(item);
				_bedrijf.contactpersonen().push(contactpersoon);
			});
		};
		_bedrijf.voegContactpersoonToe = function(){
            _bedrijf.contactpersonen().push(new Contactpersoon(''));
            _bedrijf.contactpersonen.valueHasMutated();
		};
		_bedrijf.verwijderContactpersoon = function(contactpersoon) {
			log.debug("Verwijderen contactpersoon " + ko.toJSON(contactpersoon));
			_bedrijf.contactpersonen.remove(function (item) {
			    log.debug(ko.toJSON(item));
				return item.voornaam() === contactpersoon.voornaam() && item.achternaam() === contactpersoon.achternaam();
			});
			_bedrijf.contactpersonen.valueHasMutated();
		};
		_bedrijf.soortEntiteit = ko.observable('Bedrijf');
		_bedrijf.bijlages = ko.observableArray();
		if(data.bijlages != null){
			$.each(data.bijlages, function(i, item){
				var bijlage = new Bijlage(item);
				_bedrijf.bijlages().push(bijlage);
			});
		};
		_bedrijf.soortEntiteit = ko.observable('Bedrijf');

		_bedrijf.idDiv = ko.computed(function() {
	        return "collapsable" + data.id;
		}, this);
		_bedrijf.idDivLink = ko.computed(function() {
	        return "#collapsable" + data.id;
		}, this);

		_bedrijf.bewerkBedrijf = function(bedrijf){
			log.debug("Bewerk Bedrijf : " + ko.toJSON(bedrijf));
			commonFunctions.verbergMeldingen();
			redirect.redirect('BEHEREN_RELATIE', bedrijf.relatie(), 'bedrijf', bedrijf.id());
	    };

        _bedrijf.adressen = ko.observableArray();
		if(data.adressen != null){
			$.each(data.adressen, function(i, item) {
				_bedrijf.adressen().push(new Adres(item));
			});
		};
        _bedrijf.telefoonnummers = ko.observableArray();
		_bedrijf.verwijderTelefoonNummer = function(telefoon) {
			log.debug("Verwijderen telefoon " + ko.toJSON(telefoon));
			_bedrijf.telefoonnummersModel.telefoonnummers.remove(function (item) {
			    log.debug(ko.toJSON(item));
				return item.telefoonnummer() === telefoon.telefoonnummer() && item.soort() === telefoon.soort();
			});
			_bedrijf.telefoonnummersModel.telefoonnummers.valueHasMutated();
		};


		_bedrijf.opslaan = function(bedrijf){
	    	var result = ko.validation.group(bedrijf, {deep: true});
	    	if(!bedrijf.isValid()){
	    		result.showAllMessages(true);
	    	}else{
				commonFunctions.verbergMeldingen();

                _bedrijf.opmerkingenModel.opmerkingen = null;
                _bedrijf.bijlages = null;
                _bedrijf.adressen = _bedrijf.adressenModel.adressen();
                _bedrijf.telefoonnummers = _bedrijf.telefoonnummersModel.telefoonnummers();

    			$.each(_bedrijf.telefoonnummers, function(i, item){
    			    item.telefoonnummer(item.telefoonnummer().replace(/ /g, "").replace("-", ""));
    			    item.soortEntiteit('BEDRIJF');
    			    item.entiteitId(_bedrijf.id());
    			});
    			$.each(_bedrijf.adressen, function(i, item){
    			    item.soortEntiteit('BEDRIJF');
    			    item.entiteitId(_bedrijf.id());
    			});

                _bedrijf.adressenModel = undefined;
                _bedrijf.telefoonnummersModel = undefined;
                _bedrijf.opmerkingen = undefined;
                _bedrijf.bijlages = undefined;
                _bedrijf.opmerkingenModel = undefined;

    			$.each(_bedrijf.contactpersonen(), function(i, contactpersoon){
    			    contactpersoon.bedrijf(_bedrijf.id());
                    $.each(contactpersoon.telefoonnummers(), function(i, item){
                        item.telefoonnummer(item.telefoonnummer().replace(/ /g, "").replace("-", ""));
                        item.soortEntiteit('CONTACTPERSOON');
                        item.entiteitId(contactpersoon.id());
                    });
    			});

				dataServices.opslaanBedrijf(bedrijf).done(function(){
					commonFunctions.plaatsMelding("De gegevens zijn opgeslagen");
					redirect.redirect('LIJST_BEDRIJVEN');
				}).fail(function(data){
					commonFunctions.plaatsFoutmelding(data);
				});
			}
		};

	    _bedrijf.verwijderBijlage = function(bijlage){
			commonFunctions.verbergMeldingen();
			var r=confirm("Weet je zeker dat je deze bijlage wilt verwijderen?");
			if (r==true) {
				_bedrijf.bijlages.remove(bijlage);
				dataServices.verwijderBijlage(bijlage.id());
			}
		};

		_bedrijf.adresOpgemaakt = ko.computed(function() {
		    log.debug('adresOpgemaakt');

		    var adres = null;
		    $.each(_bedrijf.adressen(), function(index, item){
		        log.debug(item);
                adres = item;
		    });

		    if(adres !== null) {
		        return adres.straat() + ' ' + adres.huisnummer() + ', ' + adres.plaats();
		    } else {
		        return '';
		    }
		});


		_bedrijf.groepBijlages = ko.observableArray();
		if(data.groepBijlages != null){
			$.each(data.groepBijlages, function(i, item){
				var groepBijlages = new GroepBijlages(item);
				_bedrijf.groepBijlages().push(groepBijlages);
			});
		};

		_bedrijf.nieuwePolisUpload = function (){
			log.debug("Nieuwe bijlage upload");
			$('uploadprogress').show();

            fileUpload.uploaden().done(function(uploadResultaat){
                log.debug(ko.toJSON(uploadResultaat));

                if(uploadResultaat.bestandsNaam == null) {
                    _bedrijf.groepBijlages().push(uploadResultaat);
                    _bedrijf.groepBijlages.valueHasMutated();
                } else {
                    _bedrijf.bijlages().push(uploadResultaat);
                    _bedrijf.bijlages.valueHasMutated();
                }
            });
		};

		_bedrijf.annuleren = function(){
			redirect.redirect('BEHEREN_RELATIE', _bedrijf.relatie(), 'bedrijven');
		}
    };
});