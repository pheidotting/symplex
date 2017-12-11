define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'commons/3rdparty/log2',
		'redirect',
        'opmerkingenModel',
        'mapper/hypotheek-mapper',
        'service/hypotheek-service',
        'viewmodel/common/opmerking-viewmodel',
        'viewmodel/common/bijlage-viewmodel',
        'viewmodel/common/menubalk-viewmodel',
        'moment',
        'service/toggle-service',
        'viewmodel/common/taak-viewmodel',
        'knockout.validation',
        'knockoutValidationLocal'],
    function($, commonFunctions, ko, log, redirect, opmerkingenModel, hypotheekMapper, hypotheekService, opmerkingViewModel, bijlageViewModel, menubalkViewmodel, moment, toggleService, taakViewModel) {

    return function() {
        var _this = this;
        var logger = log.getLogger('beheren-hypotheek-viewmodel');
        var soortEntiteit = 'HYPOTHEEK';
		this.menubalkViewmodel      = null;

        this.basisEntiteit = null;
        this.basisId = null;
        this.bijlageModel           = null;
		this.hypotheek                = null;

		this.id = ko.observable();
        this.readOnly = ko.observable();
        this.notReadOnly = ko.observable();

		this.veranderDatum = function(datum){
			datum(commonFunctions.zetDatumOm(datum()));
		};

        this.init = function(hypotheekId, basisId, readOnly, basisEntiteit) {
            var deferred = $.Deferred();

            _this.readOnly(readOnly);
            _this.notReadOnly(!readOnly);
            _this.basisEntiteit = basisEntiteit;
            _this.id(hypotheekId);
            $.when(hypotheekService.lees(hypotheekId, basisEntiteit), hypotheekService.lijstSoortenHypotheek()).then(function(data, lijstSoortenHypotheek) {
            _this.basisId = data.identificatie;
                var hypotheek = _.find(data.hypotheken, function(hypotheek) {return hypotheek.identificatie === hypotheekId.identificatie;});
                if(hypotheek == null){
                    hypotheek = {
                        'opmerkingen' : [],
                        'bijlages' : [],
                        'groepBijlages' : []
                    }
                }

                _this.hypotheek = hypotheekMapper.mapHypotheek(hypotheek, lijstSoortenHypotheek);

                _this.menubalkViewmodel     = new menubalkViewmodel(data.identificatie, _this.basisEntiteit);
                _this.opmerkingenModel      = new opmerkingViewModel(false, soortEntiteit, hypotheekId, hypotheek.opmerkingen);
                _this.bijlageModel          = new bijlageViewModel(false, soortEntiteit, hypotheekId, hypotheek.bijlages, hypotheek.groepenBijlages);

//				if(alleHypotheken.length > 0){
//					var $koppelHypotheekSelect = $('#koppelHypotheek');
//					$('<option>', { value : '' }).text('Kies evt. een hypotheek om mee te koppelen...').appendTo($koppelHypotheekSelect);
//					$.each(alleHypotheken, function(key, value) {
//						if(value.id != hypotheekId){
//						    var hypo = hypotheekMapper.mapHypotheek(value, lijstSoortenHypotheek);
//						    var selected = '';
//						    if(hypotheek.hypotheekPakket == value.hypotheekPakket) {
//						        selected = ' selected="selected"';
//						    }
//							$('<option' + selected + ' value="' + parseInt(value.id) + '">').text(hypo.titel()).appendTo($koppelHypotheekSelect);
//						}
//					});
//				}else{
					$('#gekoppeldeHypotheekGroep').hide();
//				}

                var $hypotheekVormSelect = $('#hypotheekVorm');
                $('<option>', { value : '' }).text('Kies een soort hypotheek uit de lijst...').appendTo($hypotheekVormSelect);
                $.each(lijstSoortenHypotheek, function(key, value) {
                    $('<option>', { value : value.id }).text(value.omschrijving).appendTo($hypotheekVormSelect);
                });

                toggleService.isFeatureBeschikbaar('TODOIST').done(function(toggleBeschikbaar){
                    if(toggleBeschikbaar) {
                        var relatieId;
                        var bedrijfId;
                        if(_this.basisEntiteit == 'RELATIE'){
                            relatieId = _this.basisId;
                        } else {
                            bedrijfId = _this.basisId;
                        }

                        _this.taakModel             = new taakViewModel(false, soortEntiteit, hypotheekId, relatieId, bedrijfId);
                    }
                    return deferred.resolve();
                });
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

        this.exitTaxatieDatum = function() {
		    _this.hypotheek.taxatieDatum(zetDatumTijdOm(_this.hypotheek.taxatieDatum()));
        };

        this.berekenEinddatumLening = function() {
        }
        this.berekenEinddatumRenteVastePeriode = function() {
        }

		this.exitIngangsDatum = function() {
		    _this.hypotheek.ingangsDatum(zetDatumOm(_this.hypotheek.ingangsDatum()));
		}

		this.exitEindDatum = function() {
		    _this.hypotheek.eindDatum(zetDatumOm(_this.hypotheek.eindDatum()));
		};

		this.exitIngangsDatumRenteVastePeriode = function() {
		    _this.hypotheek.ingangsDatumRenteVastePeriode(zetDatumOm(_this.hypotheek.ingangsDatumRenteVastePeriode()));
		};

		this.exitEindDatumRenteVastePeriode = function() {
		    _this.hypotheek.eindDatumRenteVastePeriode(zetDatumOm(_this.hypotheek.eindDatumRenteVastePeriode()));
		};

		this.formatBedrag = function(datum) {
            return opmaak.maakBedragOp(bedrag());
		};

		this.berekenProlongatieDatum = function() {
			if(_this.polis.ingangsDatum() !== null && _this.polis.ingangsDatum() !== undefined && _this.polis.ingangsDatum() !== "") {
				_this.polis.prolongatieDatum(moment(_this.polis.ingangsDatum(), "DD-MM-YYYY").add(1, 'y').format("DD-MM-YYYY"));
			}
		};

		this.opslaan = function() {
	    	var result = ko.validation.group(_this.hypotheek, {deep: true});
	    	if(result().length > 0) {
	    		result.showAllMessages(true);
	    	}else{
	    	    _this.hypotheek.parentIdentificatie(_this.basisId);
	    		logger.debug("Versturen : " + ko.toJSON(_this.hypotheek));

                hypotheekService.opslaanHypotheek(_this.hypotheek, _this.opmerkingenModel.opmerkingen).done(function(){
					commonFunctions.plaatsMelding("De gegevens zijn opgeslagen");
                    redirect.redirect('LIJST_HYPOTHEKEN', _this.basisId);
	    		}).fail(function(data){
					commonFunctions.plaatsFoutmelding(data);
	    		});
	    	}
		};

        this.annuleren = function() {
			redirect.redirect('BEHEREN_' + _this.basisEntiteit, _this.basisId, 'hypotheeks');
        };
	};
});