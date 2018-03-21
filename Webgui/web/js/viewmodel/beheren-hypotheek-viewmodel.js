define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'commons/3rdparty/log',
		'redirect',
        'mapper/hypotheek-mapper',
        'service/hypotheek-service',
        'viewmodel/common/opmerking-viewmodel',
        'viewmodel/common/bijlage-viewmodel',
        'viewmodel/common/menubalk-viewmodel',
        'viewmodel/common/licentie-viewmodel',
        'moment',
        'service/toggle-service',
        'knockout.validation',
        'knockoutValidationLocal'],
    function($, commonFunctions, ko, log, redirect, hypotheekMapper, hypotheekService, opmerkingViewModel, bijlageViewModel, menubalkViewmodel, LicentieViewmodel, moment, toggleService) {

    return function() {
        var _this = this;
        var logger = log.getLogger('beheren-hypotheek-viewmodel');
        var soortEntiteit = 'HYPOTHEEK';
		this.menubalkViewmodel      = null;
		this.licentieViewmodel      = null;

        this.basisEntiteit = null;
        this.basisId = null;
        this.bijlageModel           = null;
		this.hypotheek                = null;

		this.id = ko.observable();
        this.readOnly = ko.observable();
        this.notReadOnly = ko.observable();

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

                _this.menubalkViewmodel     = new menubalkViewmodel(data.identificatie, "RELATIE");
                _this.opmerkingenModel      = new opmerkingViewModel(false, soortEntiteit, hypotheekId, hypotheek.opmerkingen);
                _this.bijlageModel          = new bijlageViewModel(false, soortEntiteit, hypotheekId, hypotheek.bijlages, hypotheek.groepenBijlages);
                _this.licentieViewmodel     = new LicentieViewmodel();

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

//                toggleService.isFeatureBeschikbaar('TODOIST').done(function(toggleBeschikbaar){
//                    if(toggleBeschikbaar) {
//                        var relatieId;
//                        var bedrijfId;
//                        if(_this.basisEntiteit == 'RELATIE'){
//                            relatieId = _this.basisId;
//                        } else {
//                            bedrijfId = _this.basisId;
//                        }
//
//                        _this.taakModel             = new taakViewModel(false, soortEntiteit, hypotheekId, relatieId, bedrijfId);
//                    }

                    _this.hypotheek.hypotheekBedrag(commonFunctions.maakBedragOp(_this.hypotheek.hypotheekBedrag()));
                    _this.hypotheek.boxI(commonFunctions.maakBedragOp(_this.hypotheek.boxI()));
                    _this.hypotheek.boxIII(commonFunctions.maakBedragOp(_this.hypotheek.boxIII()));
                    _this.hypotheek.marktWaarde(commonFunctions.maakBedragOp(_this.hypotheek.marktWaarde()));
                    _this.hypotheek.koopsom(commonFunctions.maakBedragOp(_this.hypotheek.koopsom()));
                    _this.hypotheek.vrijeVerkoopWaarde(commonFunctions.maakBedragOp(_this.hypotheek.vrijeVerkoopWaarde()));
                    _this.hypotheek.wozWaarde(commonFunctions.maakBedragOp(_this.hypotheek.wozWaarde()));
                    _this.hypotheek.waardeVoorVerbouwing(commonFunctions.maakBedragOp(_this.hypotheek.waardeVoorVerbouwing()));
                    _this.hypotheek.waardeNaVerbouwing(commonFunctions.maakBedragOp(_this.hypotheek.waardeNaVerbouwing()));

                    return deferred.resolve();
//                });
            });

            return deferred.promise();
        };

        this.berekenEinddatumLening = function() {
            _this.hypotheek.eindDatum(moment(_this.hypotheek.ingangsDatum()).add(_this.hypotheek.duur(), 'years').format('YYYY-MM-DD'));
        };

        this.berekenEinddatumRenteVastePeriode = function() {
            _this.hypotheek.eindDatumRenteVastePeriode(moment(_this.hypotheek.ingangsDatumRenteVastePeriode()).add(_this.hypotheek.duurRenteVastePeriode(), 'years').format('YYYY-MM-DD'));
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

                _this.hypotheek.hypotheekBedrag(commonFunctions.stripBedrag(_this.hypotheek.hypotheekBedrag()));
                _this.hypotheek.boxI(commonFunctions.stripBedrag(_this.hypotheek.boxI()));
                _this.hypotheek.boxIII(commonFunctions.stripBedrag(_this.hypotheek.boxIII()));
                _this.hypotheek.marktWaarde(commonFunctions.stripBedrag(_this.hypotheek.marktWaarde()));
                _this.hypotheek.koopsom(commonFunctions.stripBedrag(_this.hypotheek.koopsom()));
                _this.hypotheek.vrijeVerkoopWaarde(commonFunctions.stripBedrag(_this.hypotheek.vrijeVerkoopWaarde()));
                _this.hypotheek.wozWaarde(commonFunctions.stripBedrag(_this.hypotheek.wozWaarde()));
                _this.hypotheek.waardeVoorVerbouwing(commonFunctions.stripBedrag(_this.hypotheek.waardeVoorVerbouwing()));
                _this.hypotheek.waardeNaVerbouwing(commonFunctions.stripBedrag(_this.hypotheek.waardeNaVerbouwing()));

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

        this.startBewerkenHypotheekBedrag = function(data, b) {
            _this.hypotheek.hypotheekBedrag(commonFunctions.stripBedrag(b.currentTarget.value));
        };

        this.stopBewerkenHypotheekBedrag = function() {
            _this.hypotheek.hypotheekBedrag(commonFunctions.maakBedragOp(_this.hypotheek.hypotheekBedrag()));
        };

        this.startBewerkenBoxI = function(data, b) {
            _this.hypotheek.boxI(commonFunctions.stripBedrag(b.currentTarget.value));
        };

        this.stopBewerkenBoxI = function() {
            _this.hypotheek.boxI(commonFunctions.maakBedragOp(_this.hypotheek.boxI()));
        };

        this.startBewerkenBoxIII = function(data, b) {
            _this.hypotheek.boxIII(commonFunctions.stripBedrag(b.currentTarget.value));
        };

        this.stopBewerkenBoxIII = function() {
            _this.hypotheek.boxIII(commonFunctions.maakBedragOp(_this.hypotheek.boxIII()));
        };

        this.startBewerkenMarktwaarde = function(data, b) {
            _this.hypotheek.marktWaarde(commonFunctions.stripBedrag(b.currentTarget.value));
        };

        this.stopBewerkenMarktwaarde = function() {
            _this.hypotheek.marktWaarde(commonFunctions.maakBedragOp(_this.hypotheek.marktWaarde()));
        };

        this.startBewerkenKoopsom = function(data, b) {
            _this.hypotheek.koopsom(commonFunctions.stripBedrag(b.currentTarget.value));
        };

        this.stopBewerkenKoopsom = function() {
            _this.hypotheek.koopsom(commonFunctions.maakBedragOp(_this.hypotheek.koopsom()));
        };

        this.startBewerkenVrijeVerkoopWaarde = function(data, b) {
            _this.hypotheek.vrijeVerkoopWaarde(commonFunctions.stripBedrag(b.currentTarget.value));
        };

        this.stopBewerkenVrijeVerkoopWaarde = function() {
            _this.hypotheek.vrijeVerkoopWaarde(commonFunctions.maakBedragOp(_this.hypotheek.vrijeVerkoopWaarde()));
        };

        this.startBewerkenWozWaarde = function(data, b) {
            _this.hypotheek.wozWaarde(commonFunctions.stripBedrag(b.currentTarget.value));
        };

        this.stopBewerkenWozWaarde = function() {
            _this.hypotheek.wozWaarde(commonFunctions.maakBedragOp(_this.hypotheek.wozWaarde()));
        };

        this.startBewerkenWaardeVoorVerbouwing = function(data, b) {
            _this.hypotheek.waardeVoorVerbouwing(commonFunctions.stripBedrag(b.currentTarget.value));
        };

        this.stopBewerkenWaardeVoorVerbouwing = function() {
            _this.hypotheek.waardeVoorVerbouwing(commonFunctions.maakBedragOp(_this.hypotheek.waardeVoorVerbouwing()));
        };

        this.startBewerkenWaardeNaVerbouwing = function(data, b) {
            _this.hypotheek.waardeNaVerbouwing(commonFunctions.stripBedrag(b.currentTarget.value));
        };

        this.stopBewerkenWaardeNaVerbouwing = function() {
            _this.hypotheek.waardeNaVerbouwing(commonFunctions.maakBedragOp(_this.hypotheek.waardeNaVerbouwing()));
        };
    };
});