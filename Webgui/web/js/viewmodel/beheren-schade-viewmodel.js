define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'commons/3rdparty/log',
        'redirect',
        'mapper/schade-mapper',
        'service/schade-service',
        'service/polis-service',
        'viewmodel/common/opmerking-viewmodel',
        'viewmodel/common/bijlage-viewmodel',
        'moment',
        'service/toggle-service',
        'viewmodel/common/menubalk-viewmodel',
        'viewmodel/common/licentie-viewmodel',
        'viewmodel/common/taken-viewmodel',
        'viewmodel/common/breadcrumbs-viewmodel',
        'knockout.validation',
        'knockoutValidationLocal'],
    function ($, commonFunctions, ko, log, redirect, schadeMapper, schadeService, polisService, opmerkingViewModel, bijlageViewModel, moment, toggleService, menubalkViewmodel, LicentieViewmodel, TakenViewmodel, BreadcrumbsViewmodel) {

        return function () {
            var _this = this;
            var logger = log.getLogger('beheren-schade-viewmodel');
            var soortEntiteit = 'SCHADE';

            this.basisEntiteit = null;
            this.basisId = null;
            this.bijlageModel = null;
            this.polis = null;
            this.menubalkViewmodel = null;
            this.licentieViewmodel = null;
            this.breadcrumbsViewmodel = null;

            this.id = ko.observable();
            this.readOnly = ko.observable();
            this.notReadOnly = ko.observable();

            this.veranderDatum = function (datum) {
                datum(commonFunctions.zetDatumOm(datum()));
            };

            this.init = function (schadeId, basisId, readOnly, basisEntiteit) {
                var deferred = $.Deferred();

                _this.readOnly(readOnly);
                _this.notReadOnly(!readOnly);
                _this.basisEntiteit = basisEntiteit;
                _this.basisId = basisId;
                _this.id(schadeId.identificatie);

                $.when(schadeService.lees(_this.id()), schadeService.lijstStatusSchade()).then(function (data, statussenSchade) {
                    _this.basisId = data.identificatie;

                    if (data.naam != null) {
                        _this.basisEntiteit = "BEDRIJF";
                    } else {
                        _this.basisEntiteit = "RELATIE";
                    }
                    var pakketten = data.pakketten;
                    var schade = _.chain(pakketten)
                        .map(function (pakket) {
                            return pakket.polissen;
                        })
                        .flatten()
                        .map(function (polis) {
                            _.each(polis.schades, function (schade) {
                                schade.polis = polis.identificatie;
                            });

                            return polis;
                        })
                        .map('schades')
                        .flatten()
                        .find(function (schade) {
                            return schade.identificatie === _this.id();
                        })
                        .value();
                    if (schade == null) {
                        schade = {
                            'opmerkingen': [],
                            'bijlages': [],
                            'groepBijlages': []
                        }
                    }
                    _this.schade = schadeMapper.mapSchade(schade);

                    _this.opmerkingenModel = new opmerkingViewModel(false, soortEntiteit, schadeId, schade.opmerkingen);
                    _this.bijlageModel = new bijlageViewModel(false, soortEntiteit, schadeId, schade.bijlages, schade.groepBijlages, _this.id() == _this.basisId);
                    _this.menubalkViewmodel = new menubalkViewmodel(data.identificatie, _this.basisEntiteit);
                    _this.licentieViewmodel = new LicentieViewmodel();
                    _this.breadcrumbsViewmodel = new BreadcrumbsViewmodel(data, null, schade, false);

                    _this.takenViewmodel = new TakenViewmodel(schade.taken);

                    var $selectPolis = $('#polisVoorSchademelding');
                    $('<option>', {value: ''}).text('Kies een polis uit de lijst..').appendTo($selectPolis);

                    $.each(pakketten, function (keyPakket, pakket) {
                        $.each(pakket.polissen, function (key, value) {
                            var polisTitel = value.soort + " (" + pakket.polisNummer;
                             if(value.polisNummer != null){
                                polisTitel += ' - ' + value.polisNummer;
                             }
                             polisTitel += ")";

                            $('<option>', {value: value.identificatie}).text(polisTitel).appendTo($selectPolis);
                        });
                    });

                    var $selectStatus = $('#statusSchade');
                    $.each(statussenSchade, function (key, value) {
                        $('<option>', {value: value}).text(value).appendTo($selectStatus);
                    });

                    _this.schade.eigenRisico(commonFunctions.maakBedragOp(_this.schade.eigenRisico()));

                    return deferred.resolve();
                });

                return deferred.promise();
            };

            zetDatumOm = function (d) {
                var datum = moment(d, 'DDMMYYYY').format('DD-MM-YYYY');

                if (datum == 'Invalid date') {
                    return null;
                }

                return datum;
            };

            zetDatumTijdOm = function (d) {
                return commonFunctions.zetDatumTijdOm(d);
            };

            this.formatBedrag = function () {
                return opmaak.maakBedragOp(bedrag());
            };

            this.berekenProlongatieDatum = function () {
                if (_this.polis.ingangsDatum() !== null && _this.polis.ingangsDatum() !== undefined && _this.polis.ingangsDatum() !== "") {
                    _this.polis.prolongatieDatum(moment(_this.polis.ingangsDatum(), "DD-MM-YYYY").add(1, 'y').format("DD-MM-YYYY"));
                }
            };

            this.opslaanOpPaginaBlijven = function () {
                logger.debug('opslaan');
                var result = ko.validation.group(_this.schade, {deep: true});
                if (result().length > 0) {
                    result.showAllMessages(true);
                } else {
                    logger.debug("Versturen : " + ko.toJSON(_this.schade));

                    _this.schade.eigenRisico(commonFunctions.stripBedrag(_this.schade.eigenRisico()));
                    schadeService.opslaan(_this.schade, _this.opmerkingenModel.opmerkingen, _this.takenViewmodel.taken).done(function (data) {
                        _this.id(data);
                        _this.bijlageModel.setId(data);
                        _this.bijlageModel.setSchermTonen(true);
                        _this.schade.identificatie(data);
                        commonFunctions.plaatsMelding("De gegevens zijn opgeslagen");
                    }).fail(function (data) {
                        commonFunctions.plaatsFoutmelding(data);
                    });
                }
            };

            this.startBewerkenEigenRisico = function (data, b) {
                _this.schade.eigenRisico(commonFunctions.stripBedrag(b.currentTarget.value));
            };

            this.stopBewerkenEigenRisico = function () {
                _this.schade.eigenRisico(commonFunctions.maakBedragOp(_this.schade.eigenRisico()));
            };

            this.opslaan = function () {
                logger.debug('opslaan');
                var result = ko.validation.group(_this.schade, {deep: true});
                if (result().length > 0) {
                    result.showAllMessages(true);
                } else {
                    logger.debug("Versturen : " + ko.toJSON(_this.schade));
                    var allOk = true;

                    _this.schade.eigenRisico(commonFunctions.stripBedrag(_this.schade.eigenRisico()));
                    schadeService.opslaan(_this.schade, _this.opmerkingenModel.opmerkingen, _this.takenViewmodel.taken).done(function () {
                        commonFunctions.plaatsMelding("De gegevens zijn opgeslagen");
                    }).fail(function (data) {
                        allOk = false;
                        commonFunctions.plaatsFoutmelding(data);
                    });
                    if (allOk) {
                        redirect.redirect('LIJST_SCHADES', _this.basisId);
                    }
                }
            };

            this.annuleren = function () {
                redirect.redirect('BEHEREN_' + _this.basisEntiteit, _this.basisId, 'schades');
            };
        };
    });