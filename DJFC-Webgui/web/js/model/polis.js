define(['jquery',
         'knockout',
         'commons/3rdparty/log',
         'commons/commonFunctions',
         'moment',
         'model/bijlage',
         'model/groepbijlages',
         "commons/opmaak",
         'dataServices',
         'navRegister',
         'redirect',
         'fileUpload',
         'opmerkingenModel',
        'service/polis-service'],
	function ($, ko, log, commonFunctions, moment, Bijlage, Groepbijlages, opmaak, dataServices, navRegister, redirect, fileUpload, opmerkingenModel, polisService) {

	return function polisModel (data, readOnly) {
		var _polis = this;

		_polis.opmerkingenModel = new opmerkingenModel(data.opmerkingen, null, null, data.id, null);

        _polis.readOnly = ko.observable(readOnly);
        _polis.notReadOnly = ko.observable(!readOnly);
		_polis.veranderDatum = function(datum) {
			datum(commonFunctions.zetDatumOm(datum()));
		};
		_polis.berekenProlongatieDatum = function() {
			if(_polis.ingangsDatum() !== null && _polis.ingangsDatum() !== undefined && _polis.ingangsDatum() !== "") {
				_polis.prolongatieDatum(moment(_polis.ingangsDatum(), "DD-MM-YYYY").add(1, 'y').format("DD-MM-YYYY"));
			}
		};
		_polis.bedrag = function(bedrag) {
			return opmaak.maakBedragOp(ko.utils.unwrapObservable(bedrag));
		};
		_polis.relatie = ko.observable(data.relatie);
		_polis.id = ko.observable(data.id);
		_polis.soortEntiteit = ko.observable('Polis');
		_polis.status = ko.observable(data.status);
		_polis.polisNummer = ko.observable(data.polisNummer).extend({required: true});
		_polis.kenmerk = ko.observable(data.kenmerk);
		if(data.ingangsDatum !== undefined && data.ingangsDatum !== null) {
			_polis.ingangsDatum = ko.observable(moment(data.ingangsDatum).format("DD-MM-YYYY")).extend({required: true});
		}else{
			_polis.ingangsDatum = ko.observable().extend({required: true});
		}
		if(data.eindDatum !== undefined && data.eindDatum !== null) {
			_polis.eindDatum = ko.observable(moment(data.eindDatum).format("DD-MM-YYYY"));
		}else{
			_polis.eindDatum = ko.observable();
		}
		if(data.wijzigingsDatum !== undefined && data.wijzigingsDatum !== null) {
			_polis.wijzigingsDatum = ko.observable(moment(data.wijzigingsDatum).format("DD-MM-YYYY"));
		}else{
			_polis.wijzigingsDatum = ko.observable();
		}
		if(data.prolongatieDatum !== undefined && data.prolongatieDatum !== null) {
			_polis.prolongatieDatum = ko.observable(moment(data.prolongatieDatum).format("DD-MM-YYYY"));
		}else{
			_polis.prolongatieDatum = ko.observable();
		}
		_polis.maatschappij = ko.observable(data.maatschappij).extend({required: true});
		_polis.soort = ko.observable(data.soort).extend({required: true});
		_polis.premie = ko.observable(data.premie);
		_polis.betaalfrequentie = ko.observable(data.betaalfrequentie);
		_polis.dekking = ko.observable(data.dekking);
		_polis.verzekerdeZaak = ko.observable(data.verzekerdeZaak);
		_polis.bedrijf = ko.observable(data.bedrijf);
		_polis.bedrijfsId = ko.observable(data.bedrijf);
		_polis.omschrijvingVerzekering = ko.observable(data.omschrijvingVerzekering);
		_polis.idDiv = ko.computed(function() {
	        return "collapsable" + data.id;
		}, this);
		_polis.idDivLink = ko.computed(function() {
	        return "#collapsable" + data.id;
		}, this);
		_polis.className = ko.computed(function() {
			var datum = moment(data.ingangsDatum);
			var tijd = moment(datum).fromNow();
			if(tijd.substr(tijd.length - 3) !== "ago") {
				return "polisNietActief panel-title";
			}
			if(data.eindDatum) {
				datum = moment(data.eindDatum);
				tijd = moment(datum).fromNow();
				if(tijd.substr(tijd.length - 3) === "ago") {
					return "polisBeeindigd panel-title";
				}else{
					return "panel-title";
				}
			}
		}, this);
		_polis.titel = ko.computed(function () {
			return data.soort + " (" + data.polisNummer + ")";
		}, this);
		_polis.bijlages = ko.observableArray();
		if(data.bijlages != null) {
			$.each(data.bijlages, function(i, item) {
				var bijlage = new Bijlage(item);
				_polis.bijlages().push(bijlage);
			});
		};
		_polis.groepBijlages = ko.observableArray();
		if(data.groepenBijlages != null) {
			$.each(data.groepenBijlages, function(i, item) {
				var groepenBijlages = new Groepbijlages(item);
				_polis.groepBijlages().push(groepenBijlages);
			});
		}

	    _polis.schadeMeldenBijPolis = function(polis) {
			log.debug(ko.utils.unwrapObservable(polis.id));
			log.debug($('#polisVoorSchademelding').val());
	    };

	    _polis.bewerkPolis = function(polis) {
			commonFunctions.verbergMeldingen();
			log.debug("Polis bewerken met id " + polis.id() + " en Relatie id : " + polis.relatie());
			redirect.redirect('BEHEREN_RELATIE', polis.relatie(), 'polis', polis.id());
	    };

	    _polis.polisInzien = function(polis) {
			commonFunctions.verbergMeldingen();
			log.debug("Polis inzien met id " + polis.id() + " en Relatie id : " + polis.relatie());
			redirect.redirect('BEHEREN_RELATIE', polis.relatie(), 'polisInzien', polis.id());
	    };

	    _polis.beeindigPolis = function(polis) {
	    	dataServices.beindigPolis(polis.id());
			_polis.eindDatum(moment().format("DD-MM-YYYY"));
	    };

	    _polis.bewerkPolisBedrijf = function(polis) {
			commonFunctions.verbergMeldingen();
			log.debug("Polis bewerken met id " + polis.id() + " en Bedrijf id : " + polis.bedrijf());
			redirect.redirect('BEHEREN_BEDRIJF', polis.bedrijfsId(), 'polis', polis.id());
	    };

	    _polis.polisInzienBedrijf = function(polis) {
			commonFunctions.verbergMeldingen();
			log.debug("Polis inzien met id " + polis.id() + " en Bedrijf id : " + polis.bedrijf());
			redirect.redirect('BEHEREN_BEDRIJF', polis.bedrijfsId(), 'polisInzien', polis.id());
	    };

	    _polis.verwijderBijlage = function(bijlage) {
			commonFunctions.verbergMeldingen();
			var r=confirm("Weet je zeker dat je deze bijlage wilt verwijderen?");
			if (r === true) {
				_polis.bijlages.remove(bijlage);
				dataServices.verwijderBijlage(bijlage.id());
			}
		};

		_polis.nieuwePolisUpload = function (){
			log.debug("Nieuwe bijlage upload");
			$('uploadprogress').show();

            fileUpload.uploaden().done(function(uploadResultaat){
                log.debug(ko.toJSON(uploadResultaat));

                if(uploadResultaat.bestandsNaam == null) {
                    _polis.groepBijlages().push(uploadResultaat);
                    _polis.groepBijlages.valueHasMutated();
                } else {
                    _polis.bijlages().push(uploadResultaat);
                    _polis.bijlages.valueHasMutated();
                }
            });
		};

	    _polis.opslaan = function(polis) {
	    	var result = ko.validation.group(polis, {deep: true});
	    	if(!polis.isValid()) {
	    		result.showAllMessages(true);
	    	}else{
	    		commonFunctions.verbergMeldingen();
	    		polisService.opslaan(polis).done(function() {
					commonFunctions.plaatsMelding("De gegevens zijn opgeslagen");
					if(polis.relatie() !== undefined && polis.relatie() !== null && polis.relatie() !== '0') {
						redirect.redirect('BEHEREN_RELATIE', polis.relatie(), 'polissen');
					} else {
						redirect.redirect('BEHEREN_BEDRIJF', polis.bedrijfsId(), 'polissen');
					}
	    		}).fail(function(data) {
					commonFunctions.plaatsFoutmelding(data);
	    		});
	    	}
		};

		_polis.annuleren = function() {
			redirect.redirect('BEHEREN_RELATIE', _polis.relatie(), 'polissen');
		};
    };
});