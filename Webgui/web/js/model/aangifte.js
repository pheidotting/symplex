define(['jquery',
         'knockout',
         'model/bijlage',
         'model/groepbijlages',
         'commons/3rdparty/log',
         'commons/commonFunctions',
         'dataServices',
         'navRegister',
         'redirect',
         'fileUpload',
         'opmerkingenModel'],
	function ($, ko, Bijlage, GroepBijlages, log, commonFunctions, dataServices, navRegister, redirect, fileUpload, opmerkingenModel) {

	return function aangifteModel (data){
		_aangifte = this;

		_aangifte.opmerkingenModel = new opmerkingenModel(data.opmerkingen, null, null, null, null, null, data.id);

        _aangifte.readOnly = ko.observable(false);
        _aangifte.notReadOnly = ko.observable(true);
		_aangifte.id = ko.observable(data.id);
		_aangifte.soortEntiteit = ko.observable('Aangifte');
		_aangifte.jaar = ko.observable(data.jaar);
		_aangifte.datumAfgerond = ko.observable(data.datumAfgerond);
		_aangifte.uitstelTot = ko.observable(data.uitstelTot);
		_aangifte.afgerondDoor = ko.observable(data.afgerondDoor);
		_aangifte.relatie = ko.observable(data.relatie);
		_aangifte.bijlages = ko.observableArray();
		if(data.bijlages != null){
			var bijlages = [];
			$.each(data.bijlages, function(i, item){
				_aangifte.bijlages.push(new Bijlage(item));
			});
		}

		_aangifte.idDiv = ko.computed(function() {
	        return "collapsable" + data.id;
		}, this);
		_aangifte.idDivLink = ko.computed(function() {
	        return "#collapsable" + data.id;
		}, this);

//		_aangifte.veranderDatum = function(){
//			log.debug("datum1");
//			log.debug(datum);
//			datum(commonFunctions.zetDatumOm(datum()));
//		}

		_aangifte.afronden = function(aangifte){
			dataServices.afrondenAangifte(JSON.stringify(aangifte.id())).done(function(data){
                redirect.redirect('BEHEREN_RELATIE', aangifte.relatie(), 'aangiftes');
			}).fail(function(data){
				commonFunctions.plaatsFoutmelding(data);
			});
	    };

		
		_aangifte.groepBijlages = ko.observableArray();
		if(data.groepBijlages != null){
			$.each(data.groepBijlages, function(i, item){
				var groepBijlages = new GroepBijlages(item);
				_aangifte.groepBijlages().push(groepBijlages);
			});
		};

		_aangifte.nieuwePolisUpload = function (){
			log.debug("Nieuwe bijlage upload");
			$('uploadprogress').show();

            fileUpload.uploaden().done(function(uploadResultaat){
                log.debug(ko.toJSON(uploadResultaat));

                if(uploadResultaat.bestandsNaam == null) {
                    _aangifte.groepBijlages().push(uploadResultaat);
                    _aangifte.groepBijlages.valueHasMutated();
                } else {
                    _aangifte.bijlages().push(uploadResultaat);
                    _aangifte.bijlages.valueHasMutated();
                }
            });
		};

	    _aangifte.opslaan = function(aangifte){
			aangifte.bijlages([]);
    		commonFunctions.verbergMeldingen();
    		log.debug("versturen naar " + navRegister.bepaalUrl('OPSLAAN_AANGIFTE'));
    		log.debug(ko.toJSON(aangifte));

    		dataServices.opslaanAangifte(ko.toJSON(aangifte)).done(function(data){
				aangifte.id(data);
				commonFunctions.plaatsMelding("De gegevens zijn opgeslagen");
				log.debug("aangifte id : " + aangifte.relatie());
                redirect.redirect('BEHEREN_RELATIE', aangifte.relatie());
    		}).fail(function(data){
				commonFunctions.plaatsFoutmelding(data);
    		});
		};

		_aangifte.annuleren = function(){
			redirect.redirect('BEHEREN_RELATIE', _aangifte.relatie(), 'aangiftes');
		}
    };
});