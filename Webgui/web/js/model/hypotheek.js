define(['jquery',
        'knockout',
         "commons/3rdparty/log",
         "commons/validation",
         "commons/opmaak",
         'moment',
         'model/bijlage',
         'model/groepbijlages',
         'commons/commonFunctions',
         'service/hypotheek-service',
         'redirect',
         'fileUpload',
         'opmerkingenModel'],
	function($, ko, log, validation, opmaak, moment, Bijlage, GroepBijlages, commonFunctions, hypotheekService, redirect, fileUpload, opmerkingenModel) {

	return function hypotheek(data) {
		_hypotheek = this;

		_hypotheek.opmerkingenModel = new opmerkingenModel(data.opmerkingen, null, data.id, null, null);
        _hypotheek.readOnly = ko.observable(false);
        _hypotheek.notReadOnly = ko.observable(true);

		_hypotheek.soortenHypotheek = ko.observableArray();

		_hypotheek.soorten = function(){
            var deferred = $.Deferred();

			hypotheekService.lijstSoortenHypotheek().done(function (data) {
				$.each(data, function(i, item) {
					_hypotheek.soortenHypotheek.push(new SoortHypotheek(item));
				});
                return deferred.resolve(data);
			});

            return deferred.promise();
		};
		_hypotheek.soorten();

		_hypotheek.bedrag = function(bedrag){
			return opmaak.maakBedragOp(ko.utils.unwrapObservable(bedrag));
		};

		_hypotheek.veranderDatum = function(datum){
			datum(commonFunctions.zetDatumOm(datum()));
		}
		_hypotheek.id = ko.observable(data.id);
		_hypotheek.soortEntiteit = ko.observable('Hypotheek');
		_hypotheek.bank = ko.observable(data.bank);
		_hypotheek.boxI = ko.observable(data.boxI).extend({number: true});
		_hypotheek.boxIII = ko.observable(data.boxIII).extend({number: true});
		_hypotheek.relatie = ko.observable(_relatieId);
		_hypotheek.hypotheekVorm = ko.observable(data.hypotheekVorm);
		_hypotheek.hypotheekBedrag = ko.observable(data.hypotheekBedrag).extend({required: true, number: true});
		_hypotheek.rente = ko.observable(data.rente).extend({number: true});
		_hypotheek.marktWaarde = ko.observable(data.marktWaarde).extend({number: true});
		_hypotheek.onderpand = ko.observable(data.onderpand);
		_hypotheek.koopsom = ko.observable(data.koopsom).extend({number: true});
		_hypotheek.vrijeVerkoopWaarde = ko.observable(data.vrijeVerkoopWaarde).extend({number: true});
		_hypotheek.taxatieDatum = ko.observable(data.taxatieDatum);
		_hypotheek.wozWaarde = ko.observable(data.wozWaarde).extend({number: true});
		_hypotheek.waardeVoorVerbouwing = ko.observable(data.waardeVoorVerbouwing).extend({number: true});
		_hypotheek.waardeNaVerbouwing = ko.observable(data.waardeNaVerbouwing).extend({number: true});
		_hypotheek.ingangsDatum = ko.observable(data.ingangsDatum).extend({validation: {
	        validator: function (val) {
	        	if(val != undefined){
	        		return validation.valideerDatum(val);
	        	}else{
	        		return true;
	        	}
	        },
	        message: 'Juiste invoerformaat is : dd-mm-eejj'
	    }});
		_hypotheek.eindDatum = ko.observable(data.eindDatum).extend({validation: {
	        validator: function (val) {
	        	if(val != undefined){
	        		return validation.valideerDatum(val);
	        	}else{
	        		return true;
	        	}
	        },
	        message: 'Juiste invoerformaat is : dd-mm-eejj'
	    }});;
		_hypotheek.duur = ko.observable(data.duur).extend({number: true});
		_hypotheek.ingangsDatumRenteVastePeriode = ko.observable(data.ingangsDatumRenteVastePeriode).extend({validation: {
	        validator: function (val) {
	        	if(val != undefined){
	        		return validation.valideerDatum(val);
	        	}else{
	        		return true;
	        	}
	        },
	        message: 'Juiste invoerformaat is : dd-mm-eejj'
	    }});
		_hypotheek.eindDatumRenteVastePeriode = ko.observable(data.eindDatumRenteVastePeriode).extend({validation: {
	        validator: function (val) {
	        	if(val != undefined){
	        		return validation.valideerDatum(val);
	        	}else{
	        		return true;
	        	}
	        },
	        message: 'Juiste invoerformaat is : dd-mm-eejj'
	    }});
		_hypotheek.duurRenteVastePeriode = ko.observable(data.duurRenteVastePeriode).extend({number: true});
		_hypotheek.leningNummer = ko.observable(data.leningNummer);
		_hypotheek.omschrijving = ko.observable(data.omschrijving);
		_hypotheek.idDiv = ko.computed(function() {
	        return "collapsable" + data.id;
		}, this);
		_hypotheek.idDivLink = ko.computed(function() {
	        return "#collapsable" + data.id;
		}, this);
		_hypotheek.maakRenteOp = function(rente) {
			return rente() + '%';
		};
		_hypotheek.className = ko.computed(function() {
			var datum = moment(data.ingangsDatum);
			var tijd = moment(datum).fromNow();
			if(tijd.substr(tijd.length - 3) == "ago"){
				return "panel-title";
			}else{
				return "polisNietActief panel-title";
			}
		}, this);
		_hypotheek.hypotheekVormOpgemaakt = ko.computed(function() {
//		    if(_hypotheek.soortenHypotheek().length === 0) {
//                _hypotheek.soorten().done(bijwerken);
//		    } else {
//		        bijwerken();
//		    }

            _hypotheek.soortenHypotheek([]);
            _hypotheek.soorten().done(function(){
                var hypVorm;

                $.each(_hypotheek.soortenHypotheek(), function(i, soort){
                    if(data.hypotheekVorm == soort.id()){
                        hypVorm = soort.omschrijving();
                    }
                });

                return hypVorm;
            });
		}, this);
		_hypotheek.titel = ko.computed(function() {
			var hypVorm;

			$.each(_hypotheek.soortenHypotheek(), function(i, soort){
				if(data.hypotheekVorm == soort.id()){
					hypVorm = soort.omschrijving();
				}
			});
			var omschrijving = "";
			if(data.leningNummer != null){
				omschrijving += data.leningNummer + " - ";
			}
			omschrijving += hypVorm + " - ";
			if(data.bank != null){
				omschrijving += data.bank + " - ";
			}
			omschrijving += data.rente + "% - ";
			omschrijving += _hypotheek.bedrag(data.hypotheekBedrag);

			return omschrijving;
		}, this);
	    _hypotheek.opmerkingen = ko.observableArray();
		if(data.opmerkingen != null){
			$.each(data.opmerkingen, function(i, item){
				_hypotheek.opmerkingen.push(new Opmerking(item));
			});
		}
		_hypotheek.bijlages = ko.observableArray();
		if(data.bijlages != null){
			$.each(data.bijlages, function(i, item){
				_hypotheek.bijlages.push(new Bijlage(item));
			});
		}
		_hypotheek.gekoppeldeHypotheek = ko.observable();

		_hypotheek.opslaan = function(hypotheek){
	    	var result = ko.validation.group(hypotheek, {deep: true});
	    	if(!hypotheek.isValid()){
	    		result.showAllMessages(true);
	    	}else{
	    		log.debug("Versturen : " + ko.toJSON(hypotheek));

                _hypotheek.soortenHypotheek([]);

	    		hypotheekService.opslaanHypotheek(hypotheek).done(function(data){
					commonFunctions.plaatsMelding("De gegevens zijn opgeslagen");
					redirect.redirect('BEHEREN_RELATIE', hypotheek.relatie(), 'hypotheken');
	    		}).fail(function(data){
					commonFunctions.plaatsFoutmelding(data);
		        });
	    	}
		};

		_hypotheek.groepBijlages = ko.observableArray();
		if(data.groepBijlages != null){
			$.each(data.groepBijlages, function(i, item){
				var groepBijlages = new GroepBijlages(item);
				_hypotheek.groepBijlages().push(groepBijlages);
			});
		};

		_hypotheek.nieuwePolisUpload = function (){
			log.debug("Nieuwe bijlage upload");
			$('uploadprogress').show();

            fileUpload.uploaden().done(function(uploadResultaat){
                log.debug(ko.toJSON(uploadResultaat));

                if(uploadResultaat.bestandsNaam == null) {
                    _hypotheek.groepBijlages().push(uploadResultaat);
                    _hypotheek.groepBijlages.valueHasMutated();
                } else {
                    _hypotheek.bijlages().push(uploadResultaat);
                    _hypotheek.bijlages.valueHasMutated();
                }
            });
		};

	    self.bewerkHypotheek = function(hypotheek){
	    	commonFunctions.verbergMeldingen();
					redirect.redirect('BEHEREN_RELATIE', hypotheek.relatie(), 'hypotheek', ko.utils.unwrapObservable(hypotheek.id));
	    };

		_hypotheek.berekenEinddatumLening = function(hypotheek){
			if(ko.utils.unwrapObservable(_hypotheek.duur()) != null && ko.utils.unwrapObservable(_hypotheek.duur()) != "" && ko.utils.unwrapObservable(_hypotheek.ingangsDatum()) != null && ko.utils.unwrapObservable(_hypotheek.ingangsDatum()) != ""){
				var duur = parseInt(ko.utils.unwrapObservable(_hypotheek.duur()));
				_hypotheek.eindDatum(moment(_hypotheek.ingangsDatum(), "DD-MM-YYYY").add(duur, 'y').format("DD-MM-YYYY"));
			}
		}

		_hypotheek.berekenEinddatumRenteVastePeriode = function(hypotheek){
			if(ko.utils.unwrapObservable(_hypotheek.duurRenteVastePeriode()) != null && ko.utils.unwrapObservable(_hypotheek.duurRenteVastePeriode()) != "" && ko.utils.unwrapObservable(_hypotheek.ingangsDatumRenteVastePeriode()) != null && ko.utils.unwrapObservable(_hypotheek.ingangsDatumRenteVastePeriode()) != ""){
				var duur = parseInt(ko.utils.unwrapObservable(_hypotheek.duurRenteVastePeriode()));
				_hypotheek.eindDatumRenteVastePeriode(moment(_hypotheek.ingangsDatumRenteVastePeriode(), "DD-MM-YYYY").add(duur, 'y').format("DD-MM-YYYY"));
			}
		}

		_hypotheek.annuleren = function(){
			redirect.redirect('BEHEREN_RELATIE', _hypotheek.relatie(), 'hypotheken');
		}

	    _hypotheek.verwijderBijlage = function(bijlage){
			commonFunctions.verbergMeldingen();
			var r=confirm("Weet je zeker dat je deze bijlage wilt verwijderen?");
			if (r === true) {
				_hypotheek.bijlages.remove(bijlage);
				hypotheekService.verwijderBijlage(bijlage.id());
			}
		};

	};

	function SoortHypotheek(data){
		var _hypotheek = this;

		_hypotheek.id = ko.observable(data.id);
		_hypotheek.omschrijving = ko.observable(data.omschrijving);
	}

	function Opmerking(data){
		var self = this;

		self.id = ko.observable(data.id);
		self.opmerking = ko.observable(data.opmerking);
		self.medewerker = ko.observable(data.medewerker);
		self.tijd = ko.observable(data.tijd);
	}
});