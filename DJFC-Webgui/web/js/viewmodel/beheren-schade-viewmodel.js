define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'commons/3rdparty/log2',
		'redirect',
        'opmerkingenModel',
        'mapper/schade-mapper',
        'service/schade-service',
        'service/polis-service',
        'viewmodel/common/opmerking-viewmodel',
        'viewmodel/common/bijlage-viewmodel',
        'moment',
        'service/toggle-service',
        'viewmodel/common/menubalk-viewmodel',
        'viewmodel/common/taak-viewmodel',
        'knockout.validation',
        'knockoutValidationLocal'],
    function($, commonFunctions, ko, log, redirect, opmerkingenModel, schadeMapper, schadeService, polisService, opmerkingViewModel, bijlageViewModel, moment, toggleService, menubalkViewmodel, taakViewModel) {

    return function() {
        var _this = this;
        var logger = log.getLogger('beheren-schade-viewmodel');
        var soortEntiteit = 'SCHADE';

        this.basisEntiteit = null;
        this.basisId = null;
        this.bijlageModel           = null;
		this.polis                = null;
		this.menubalkViewmodel      = null;

		this.id = ko.observable();
        this.readOnly = ko.observable();
        this.notReadOnly = ko.observable();

		this.veranderDatum = function(datum){
			datum(commonFunctions.zetDatumOm(datum()));
		};

        this.init = function(schadeId, basisId, readOnly, basisEntiteit) {
            var deferred = $.Deferred();

            _this.readOnly(readOnly);
            _this.notReadOnly(!readOnly);
            _this.basisEntiteit = basisEntiteit;
            _this.basisId = basisId;
            _this.id(schadeId.identificatie);

            var relatieId = null;
            var bedrijfId = null;
            if(basisEntiteit == 'RELATIE') {
                relatieId = basisId;
            } else {
                bedrijfId = basisId;
            }

            $.when(schadeService.lees(_this.id()), schadeService.lijstStatusSchade()).then(function(data, statussenSchade) {
                _this.basisId = data.identificatie;
                if(data.naam != null) {
                    _this.basisEntiteit = "BEDRIJF";
                } else {
                    _this.basisEntiteit = "RELATIE";
                }
                var polissen = data.polissen;
                var schade = _.chain(polissen)
                    .map(function(polis) {
                        _.each(polis.schades, function(schade) {
                            schade.polis = polis.identificatie;
                        });

                        return polis;
                    })
                    .map('schades')
                    .flatten()
                    .find(function(schade) {
                        return schade.identificatie === _this.id();
                    })
                    .value();
                if(schade == null){
                    schade = {
                        'opmerkingen' : [],
                        'bijlages' : [],
                        'groepBijlages' : []
                    }
                }
                _this.schade = schadeMapper.mapSchade(schade);

                _this.opmerkingenModel      = new opmerkingViewModel(false, soortEntiteit, schadeId, schade.opmerkingen);
                _this.bijlageModel          = new bijlageViewModel(false, soortEntiteit, schadeId, schade.bijlages, schade.groepBijlages);
                _this.menubalkViewmodel     = new menubalkViewmodel(data.identificatie, _this.basisEntiteit);

                var $selectPolis = $('#polisVoorSchademelding');
                $('<option>', { value : '' }).text('Kies een polis uit de lijst..').appendTo($selectPolis);

                $.each(polissen, function(key, value) {
                    var polisTitel = value.soort + " (" + value.polisNummer + ")";

                    $('<option>', { value : value.identificatie }).text(polisTitel).appendTo($selectPolis);
                });

                var $selectStatus = $('#statusSchade');
                $.each(statussenSchade, function(key, value) {
                    $('<option>', { value : value }).text(value).appendTo($selectStatus);
                });

                _this.taakModel             = new taakViewModel(false, soortEntiteit, schadeId, relatieId, bedrijfId);
                return deferred.resolve();
            });

            return deferred.promise();
        };

        zetDatumOm = function(d) {
            var datum = moment(d, 'DDMMYYYY').format('DD-MM-YYYY');

            if(datum == 'Invalid date') {
                return null;
            }

            return datum;
        };

        zetDatumTijdOm = function(d) {
            return commonFunctions.zetDatumTijdOm(d);
        };

//        this.exitDatumTijdSchade = function() {
//		    _this.schade.datumTijdSchade(zetDatumTijdOm(_this.schade.datumTijdSchade()));
//        };
//
//		this.exitDatumTijdMelding = function() {
//		    _this.schade.datumTijdMelding(zetDatumTijdOm(_this.schade.datumTijdMelding()));
//		}
//
//		this.exitDatumAfgehandeld = function() {
//		    _this.schade.datumAfgehandeld(zetDatumOm(_this.schade.datumAfgehandeld()));
//		};

		this.formatBedrag = function(datum) {
            return opmaak.maakBedragOp(bedrag());
		};

		this.berekenProlongatieDatum = function() {
			if(_this.polis.ingangsDatum() !== null && _this.polis.ingangsDatum() !== undefined && _this.polis.ingangsDatum() !== "") {
				_this.polis.prolongatieDatum(moment(_this.polis.ingangsDatum(), "DD-MM-YYYY").add(1, 'y').format("DD-MM-YYYY"));
			}
		};

		this.opslaan = function() {
		    logger.debug('opslaan');
	    	var result = ko.validation.group(_this.schade, {deep: true});
	    	if(result().length > 0) {
	    		result.showAllMessages(true);
	    	}else{
	    		logger.debug("Versturen : " + ko.toJSON(_this.schade));

                schadeService.opslaan(_this.schade, _this.opmerkingenModel.opmerkingen).done(function(){
					commonFunctions.plaatsMelding("De gegevens zijn opgeslagen");
//                    redirect.redirect('BEHEREN_' + _this.basisEntiteit, _this.basisId, 'schades');
	    		}).fail(function(data){
					commonFunctions.plaatsFoutmelding(data);
	    		});
	    	}
		};

        this.annuleren = function() {
			redirect.redirect('BEHEREN_' + _this.basisEntiteit, _this.basisId, 'schades');
        };
	};
});