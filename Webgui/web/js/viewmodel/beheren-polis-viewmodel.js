define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'commons/3rdparty/log2',
		'redirect',
        'opmerkingenModel',
        'mapper/polis-mapper',
        'service/polis-service',
        'viewmodel/common/opmerking-viewmodel',
        'viewmodel/common/bijlage-viewmodel',
        'moment',
        'service/toggle-service',
        'viewmodel/common/menubalk-viewmodel',
        'viewmodel/common/taak-viewmodel',
        'knockout.validation',
        'knockoutValidationLocal',
        'blockUI'],
    function($, commonFunctions, ko, log, redirect, opmerkingenModel, polisMapper, polisService, opmerkingViewModel, bijlageViewModel, moment, toggleService, menubalkViewmodel, taakViewModel) {

    return function() {
        var _this = this;
        var logger = log.getLogger('beheren-polis-viewmodel');
        var soortEntiteit = 'POLIS';

        this.basisEntiteit = null;
        this.basisId = null;
        this.bijlageModel           = null;
		this.polis                = null;
		this.taakModel              = null;
		this.menubalkViewmodel      = null;

		this.lijst = ko.observableArray();
		this.id = ko.observable();
        this.readOnly = ko.observable();
        this.notReadOnly = ko.observable();

        this.init = function(polisId, basisId, readOnly, basisEntiteit) {
            var deferred = $.Deferred();

            _this.readOnly(readOnly);
            _this.notReadOnly(!readOnly);
            _this.id(polisId.identificatie);
            $.when(polisService.lees(polisId, basisEntiteit), polisService.lijstVerzekeringsmaatschappijen(), polisService.lijstParticulierePolissen(), polisService.lijstZakelijkePolissen()).then(function(entiteit, maatschappijen, lijstParticulierePolissen, lijstZakelijkePolissen) {
                _this.basisId = entiteit.identificatie;;
                if(entiteit.naam != null) {
                    _this.basisEntiteit = "BEDRIJF";
                } else {
                    _this.basisEntiteit = "RELATIE";
                }

                var polis = _.find(entiteit.polissen, function(polis) {return polis.identificatie === polisId.identificatie;});
                if(polis == null){
                    polis = {
                        'opmerkingen' : [],
                        'bijlages' : [],
                        'groepBijlages' : []
                    }
                }

                _this.polis = polisMapper.mapPolis(polis, maatschappijen);

                _this.opmerkingenModel      = new opmerkingViewModel(false, soortEntiteit, polisId, polis.opmerkingen);
                _this.bijlageModel          = new bijlageViewModel(false, soortEntiteit, polisId, polis.bijlages, polis.groepBijlages, _this.id() == _this.basisId);
                _this.menubalkViewmodel     = new menubalkViewmodel(entiteit.identificatie, _this.basisEntiteit);

                var $verzekeringsMaatschappijenSelect = $('#verzekeringsMaatschappijen');
                $.each(maatschappijen, function(key, value) {
                    $('<option>', { value : key }).text(value).appendTo($verzekeringsMaatschappijenSelect);
                });

                var lijst = lijstParticulierePolissen;
                if(_this.basisEntiteit == 'BEDRIJF') {
                    lijst = lijstZakelijkePolissen;
                }

                var $soortVerzekeringSelect = $('#soortVerzekering');
                    $('<option>', { value : 0 }).text('Kies een soort polis...').appendTo($soortVerzekeringSelect);
                $.each(lijst, function(key, value) {
                    $('<option>', { value : value }).text(value).appendTo($soortVerzekeringSelect);
                });

                var relatieId;
                var bedrijfId;
                if(_this.basisEntiteit == 'RELATIE'){
                    relatieId = _this.basisId;
                } else {
                    bedrijfId = _this.basisId;
                }
                _this.taakModel             = new taakViewModel(false, soortEntiteit, polisId, relatieId, bedrijfId);
                _this.polis.premie(commonFunctions.maakBedragOp(_this.polis.premie()));

                return deferred.resolve();
            });

            return deferred.promise();
        };

		this.exitIngangsDatum = function() {
            _this.berekenProlongatieDatum();
		}

		this.formatBedrag = function(datum) {
            return opmaak.maakBedragOp(bedrag());
		};

		this.berekenProlongatieDatum = function() {
			if(_this.polis.ingangsDatum() !== null && _this.polis.ingangsDatum() !== undefined && _this.polis.ingangsDatum() !== "") {
				_this.polis.prolongatieDatum(moment(_this.polis.ingangsDatum(), "YYYY-MM-DD").add(1, 'y').format("YYYY-MM-DD"));
			}
		};

		this.opslaan = function() {
		    logger.debug('opslaan');
	    	var result = ko.validation.group(_this.polis, {deep: true});
	    	if(result().length > 0) {
	    		result.showAllMessages(true);
	    	}else{
	    		commonFunctions.verbergMeldingen();
	    		var allOk = true;

                _this.polis.premie(commonFunctions.stripBedrag(_this.polis.premie()));
	    		polisService.opslaan(_this.polis, _this.opmerkingenModel.opmerkingen, _this.basisId).done(function() {
					commonFunctions.plaatsMelding("De gegevens zijn opgeslagen");
                    redirect.redirect('BEHEREN_RELATIE', _this.basisId);
	    		}).fail(function(data) {
    	    		allOk = false;
					commonFunctions.plaatsFoutmelding(data);
	    		});
	    		if(allOk){
                    redirect.redirect('LIJST_POLISSEN', _this.basisId);
	    		}
	    	}
		};

        this.annuleren = function() {
			redirect.redirect('BEHEREN_' + _this.basisEntiteit, _this.basisId, 'polissen');
        };

        this.startBewerkenPremie = function(data, b) {
            _this.polis.premie(commonFunctions.stripBedrag(b.currentTarget.value));
        };

        this.stopBewerkenPremie = function() {
            _this.polis.premie(commonFunctions.maakBedragOp(_this.polis.premie()));
        };
	};
});