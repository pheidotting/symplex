define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'commons/3rdparty/log2',
		'redirect',
        'opmerkingenModel',
        'mapper/hypotheek-mapper',
        'service/gebruiker-service',
        'viewmodel/common/opmerking-viewmodel',
        'viewmodel/common/bijlage-viewmodel',
        'viewmodel/common/menubalk-viewmodel',
        'moment',
        'service/toggle-service',
        'viewmodel/common/taak-viewmodel',
        'mapper/bijlage-mapper',
        'mapper/groepbijlage-mapper',
        'knockout.validation',
        'knockoutValidationLocal'],
    function($, commonFunctions, ko, log, redirect, opmerkingenModel, hypotheekMapper, gebruikerService, opmerkingViewModel, bijlageViewModel, menubalkViewmodel, moment, toggleService, taakViewModel, bijlageMapper, groepbijlageMapper) {

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

        this.contracten = ko.observable({});

        this.init = function(id, basisId, readOnly, basisEntiteit) {
            var deferred = $.Deferred();

            _this.readOnly(readOnly);
            _this.notReadOnly(!readOnly);
            _this.basisEntiteit = basisEntiteit;
            _this.id(id.identificatie);
            $.when(gebruikerService.leesRelatie(_this.id(), basisEntiteit)).then(function(data) {
                _this.basisId = data.identificatie;

                _this.contracten().bijlages = ko.observableArray();
                $.each(bijlageMapper.mapBijlages(data.belastingzaken.contracten.bijlages)(), function(i, bijlage){
                    _this.contracten().bijlages.push(bijlage);
                });

                _this.contracten().groepBijlages = ko.observableArray();
                $.each(groepbijlageMapper.mapGroepbijlages(data.belastingzaken.contracten.groepBijlages)(), function(i, groepbijlage){
                    _this.contracten().groepBijlages.push(groepbijlage);
                });




//                var bijlages
//                var groepenBijlages
//                Jaarrekening
//
//                var bijlages
//                var groepenBijlages
//                IB
//
//                var bijlages
//                var groepenBijlages
//                Btw
//
//                var bijlages
//                var groepenBijlages
//                Loonbelasting
//
//                var bijlages
//                var groepenBijlages
//                Overig

//                var hypotheek = _.find(data.hypotheken, function(hypotheek) {return hypotheek.identificatie === hypotheekId.identificatie;});
//                if(hypotheek == null){
//                    hypotheek = {
//                        'opmerkingen' : [],
//                        'bijlages' : [],
//                        'groepBijlages' : []
//                    }
//                }
//
//                _this.hypotheek = hypotheekMapper.mapHypotheek(hypotheek, lijstSoortenHypotheek);
//
                _this.menubalkViewmodel     = new menubalkViewmodel(data.identificatie, "RELATIE");
//                _this.opmerkingenModel      = new opmerkingViewModel(false, soortEntiteit, hypotheekId, hypotheek.opmerkingen);
//                _this.bijlageModel          = new bijlageViewModel(false, soortEntiteit, hypotheekId, hypotheek.bijlages, hypotheek.groepenBijlages);

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
//					$('#gekoppeldeHypotheekGroep').hide();
//				}

//                var $hypotheekVormSelect = $('#hypotheekVorm');
//                $('<option>', { value : '' }).text('Kies een soort hypotheek uit de lijst...').appendTo($hypotheekVormSelect);
//                $.each(lijstSoortenHypotheek, function(key, value) {
//                    $('<option>', { value : value.id }).text(value.omschrijving).appendTo($hypotheekVormSelect);
//                });
//
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
//
//                    _this.hypotheek.hypotheekBedrag(commonFunctions.maakBedragOp(_this.hypotheek.hypotheekBedrag()));
//                    _this.hypotheek.boxI(commonFunctions.maakBedragOp(_this.hypotheek.boxI()));
//                    _this.hypotheek.boxIII(commonFunctions.maakBedragOp(_this.hypotheek.boxIII()));
//                    _this.hypotheek.marktWaarde(commonFunctions.maakBedragOp(_this.hypotheek.marktWaarde()));
//                    _this.hypotheek.koopsom(commonFunctions.maakBedragOp(_this.hypotheek.koopsom()));
//                    _this.hypotheek.vrijeVerkoopWaarde(commonFunctions.maakBedragOp(_this.hypotheek.vrijeVerkoopWaarde()));
//                    _this.hypotheek.wozWaarde(commonFunctions.maakBedragOp(_this.hypotheek.wozWaarde()));
//                    _this.hypotheek.waardeVoorVerbouwing(commonFunctions.maakBedragOp(_this.hypotheek.waardeVoorVerbouwing()));
//                    _this.hypotheek.waardeNaVerbouwing(commonFunctions.maakBedragOp(_this.hypotheek.waardeNaVerbouwing()));
//
                    return deferred.resolve();
//                });
            });

            return deferred.promise();
        };
    };
});