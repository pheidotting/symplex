define(['jquery',
         'knockout',
         'commons/3rdparty/log',
         'commons/commonFunctions',
         'moment',
         'model/bijlage',
         'model/groepbijlages',
         'model/opmerking',
         "commons/opmaak",
         'dataServices',
         'redirect',
         'fileUpload',
         'opmerkingenModel',
         'service/schade-service'],
	function ($, ko, log, commonFunctions, moment, Bijlage, GroepBijlages, Opmerking, opmaak, dataServices, redirect, fileUpload, opmerkingenModel, schadeService) {

	return function schadeModel (data, relatieId){
		_this = this;

		_this.opmerkingenModel = new opmerkingenModel(data.opmerkingen, data.id, null, null, null);

        _this.readOnly = ko.observable(false);
        _this.notReadOnly = ko.observable(true);
		_this.veranderDatum = function(datum){
			datum(commonFunctions.zetDatumOm(datum()));
		};
		_this.veranderDatumTijd = function(datum){
			datum(commonFunctions.zetDatumTijdOm(datum()));
		};
		_this.bedrag = function(bedrag){
			log.debug("opmaken bedrag " + bedrag());
			return opmaak.maakBedragOp(bedrag());
		};
	    _this.id = ko.observable(data.id);
		_this.soortEntiteit = ko.observable('Schade');
	    _this.relatie = ko.observable(relatieId);
	    _this.bedrijf = ko.observable(data.bedrijf);
	    _this.polis = ko.observable(data.polis).extend({validation: {
	        validator: function (val) {
	        	if(ko.utils.unwrapObservable(_this.polis) == "Kies een polis uit de lijst.."){
					if(_this.schadeNummerMaatschappij.isValid()){
	        			return false;
	        		}else{
	        			return true;
	        		}
	    		}else{
	    			return true;
	    		}
	        },
	        message: 'Dit veld is verplicht.'
	    }});
	    _this.schadeNummerMaatschappij = ko.observable(data.schadeNummerMaatschappij).extend({required: true});
	    _this.schadeNummerTussenPersoon = ko.observable(data.schadeNummerTussenPersoon);
	    _this.soortSchade = ko.observable(data.soortSchade).extend({required: true});
	    _this.locatie = ko.observable(data.locatie);
	    _this.statusSchade = ko.observable(data.statusSchade).extend({validation: {
	        validator: function (val) {
	        	if(ko.utils.unwrapObservable(_this.statusSchade) == "Kies een status uit de lijst.."){
					if(_this.schadeNummerMaatschappij.isValid()){
	        			return false;
	        		}else{
	        			return true;
	        		}
	    		}else{
	    			return true;
	    		}
	        },
	        message: 'Dit veld is verplicht.'
	    }});
	    _this.datumTijdSchade = ko.observable(data.datumTijdSchade).extend({required: true, validation: {
	        validator: function (val) {
	        	if(moment(val, "DD-MM-YYYY HH:mm").format("DD-MM-YYYY HH:mm") == "Invalid date"){
	    			return false;
	    		}else{
	    			return true;
	    		}
	        },
	        message: 'Juiste invoerformaat is : dd-mm-eejj uu:mm'
	    }});
	    _this.datumTijdMelding = ko.observable(data.datumTijdMelding).extend({required: true, validation: {
	        validator: function (val) {
	        	if(moment(val, "DD-MM-YYYY HH:mm").format("DD-MM-YYYY HH:mm") == "Invalid date"){
	    			return false;
	    		}else{
	    			return true;
	    		}
	        },
	        message: 'Juiste invoerformaat is : dd-mm-eejj uu:mm'
	    }});
	    _this.datumAfgehandeld = ko.observable(data.datumAfgehandeld).extend({validation: {
	        validator: function (val) {
	        	if(val != undefined && val != ""){
		        	if(moment(val, "DD-MM-YYYY").format("DD-MM-YYYY") == "Invalid date"){
		    			return false;
		    		}else{
		    			return true;
		    		}
	        	}else{
	        		return true;
	        	}
	        },
	        message: 'Juiste invoerformaat is : dd-mm-eejj'
	    }});;
	    _this.eigenRisico = ko.observable(data.eigenRisico).extend({number: true});
	    _this.omschrijving = ko.observable(data.omschrijving);

	    _this.titel = ko.computed(function() {
	    	return data.soortSchade + " (" + data.schadeNummerMaatschappij + ")";
	    }, this);

		_this.idDiv = ko.computed(function() {
	        return "collapsableSchade" + data.id;
		}, this);
		_this.idDivLink = ko.computed(function() {
	        return "#collapsableSchade" + data.id;
		}, this);

	    this.opmerkingen = ko.observableArray();
			if(data.opmerkingen != null){
			$.each(data.opmerkingen, function(i, item){
				_this.opmerkingen.push(new Opmerking(item));
			});
		}

		_this.bijlages = ko.observableArray();
		if(data.bijlages != null){
			$.each(data.bijlages, function(i, item){
				_this.bijlages.push(new Bijlage(item));
			});
		}

		_this.groepBijlages = ko.observableArray();
		if(data.groepBijlages != null){
			$.each(data.groepBijlages, function(i, item){
				var groepBijlages = new GroepBijlages(item);
				_this.groepBijlages().push(groepBijlages);
			});
		};

		_this.nieuwePolisUpload = function (){
			log.debug("Nieuwe bijlage upload");
			$('uploadprogress').show();

            fileUpload.uploaden().done(function(uploadResultaat){
                log.debug(ko.toJSON(uploadResultaat));

                if(uploadResultaat.bestandsNaam == null) {
                    _this.groepBijlages().push(uploadResultaat);
                    _this.groepBijlages.valueHasMutated();
                } else {
                    _this.bijlages().push(uploadResultaat);
                    _this.bijlages.valueHasMutated();
                }
            });
		};

		_this.opslaan = function(schade){
	    	var result = ko.validation.group(schade, {deep: true});
	    	if(!schade.isValid()){
	    		result.showAllMessages(true);
	    	}else{
//				schade.opmerkingenModel = new opmerkingenModel(data.opmerkingen, null, null, data.id, null);
				schade.bijlages = ko.observableArray();
	    		log.debug("Versturen : " + ko.toJSON(schade));

                schadeService.opslaan(schade).done(function(){
					commonFunctions.plaatsMelding("De gegevens zijn opgeslagen");
					if(schade.relatie() != undefined){
						redirect.redirect('BEHEREN_RELATIE', schade.relatie(), 'schades');
					} else {
						redirect.redirect('BEHEREN_BEDRIJF', schade.bedrijf(), 'schades');
					}
	    		}).fail(function(data){
					commonFunctions.plaatsFoutmelding(data);
	    		});
	    	}
		};

	    _this.verwijderBijlage = function(bijlage){
			commonFunctions.verbergMeldingen();
			var r=confirm("Weet je zeker dat je deze bijlage wilt verwijderen?");
			if (r === true) {
				_this.bijlages.remove(bijlage);
				dataServices.verwijderBijlage(bijlage.id());
			}
		};

	    _this.bewerkSchade = function(schade){
			redirect.redirect('BEHEREN_RELATIE', schade.relatie(), 'schade', schade.id());
	    };

	    _this.bewerkSchadeBedrijf = function(schade){
			redirect.redirect('BEHEREN_BEDRIJF', schade.bedrijf(), 'schade', schade.id());
	    };

	    _this.plaatsOpmerking = function(schade){
			redirect.redirect('BEHEREN_RELATIE', schade.relatie(), 'opmerking', 's' + schade.id());
	    };

		_this.annuleren = function(){
			redirect.redirect('BEHEREN_RELATIE', self.relatie(), 'schades');
		}

	};
});