define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log',
        'redirect',
        'service/polis-service',
        'mapper/polis-mapper',
        'viewmodel/common/menubalk-viewmodel',
        'viewmodel/common/licentie-viewmodel',
        'moment',
        'commons/opmaak'],
    function ($, commonFunctions, ko, functions, block, log, redirect, polisService, polisMapper, menubalkViewmodel, LicentieViewmodel, moment, opmaak) {

        return function () {
            var _this = this;
            var logger = log.getLogger('lijst-polissen-viewmodel');
            this.menubalkViewmodel = null;
            this.licentieViewmodel = null;

            this.basisEntiteit = null;
            this.id = ko.observable();
            this.polissen = ko.observableArray();
            this.identificatie = null;

            this.init = function (id, basisEntiteit) {
                _this.identificatie = id.identificatie;

                var deferred = $.Deferred();

                _this.basisEntiteit = basisEntiteit;

                $.when(polisService.lijstPolissen(_this.identificatie), polisService.lijstVerzekeringsmaatschappijen()).then(function (data, maatschappijen) {
                    if (data.naam != null) {
                        _this.basisEntiteit = "BEDRIJF";
                    } else {
                        _this.basisEntiteit = "RELATIE";
                    }

                    _this.polissen = polisMapper.mapPolissen(data.polissen, maatschappijen);

                    _this.menubalkViewmodel = new menubalkViewmodel(_this.identificatie, _this.basisEntiteit);
                    _this.licentieViewmodel = new LicentieViewmodel();

                    return deferred.resolve();
                });

                return deferred.promise();
            };

            this.bewerk = function (polis) {
                redirect.redirect('BEHEER_POLIS', polis.identificatie());
            }

            this.inzien = function (polis) {
                redirect.redirect('BEHEER_POLIS', polis.identificatie(), true);
            }

            this.verwijder = function (polis) {
                var r = confirm("Weet je zeker dat je deze polis wilt verwijderen?");
                if (r) {
                    _this.polissen.remove(polis);
                    polisService.verwijderPolis(polis.identificatie());
                }
            }

            this.formatDatum = function (datum) {
                return moment(datum(), 'YYYY-MM-DD').format('DD-MM-YYYY');
            };

            this.formatBedrag = function (bedrag) {
                return opmaak.maakBedragOp(bedrag());
            };

            this.polisInzien = function (polis) {
                commonFunctions.verbergMeldingen();
                logger.debug('Polis inzien met id ' + polis.id() + ' en ' + _this.basisEntiteit + ' id : ' + _this.id());
                redirect.redirect('BEHEREN_' + _this.basisEntiteit, _this.id(), 'polisInzien', polis.id());
            };

            this.bewerkPolis = function (polis) {
                commonFunctions.verbergMeldingen();
                logger.debug('Polis bewerken met id ' + polis.id() + ' en ' + _this.basisEntiteit + ' id : ' + _this.id());
                redirect.redirect('BEHEREN_' + _this.basisEntiteit, _this.id(), 'polis', polis.id());
            };

            this.beeindigPolis = function (polis) {
                polisService.beindigPolis(polis.id());
                polis.eindDatum(moment().format("DD-MM-YYYY"));
            };

            this.verwijderPolis = function (polis) {
                commonFunctions.verbergMeldingen();
                var r = confirm("Weet je zeker dat je deze polis wilt verwijderen?");
                if (r) {
                    _this.polissen.remove(polis);
                    polisService.verwijderPolis(ko.utils.unwrapObservable(polis.id));
                }
            };
        };
    });