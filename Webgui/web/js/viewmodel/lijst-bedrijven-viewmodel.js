define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'model/bedrijf',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log',
        'redirect',
        'service/bedrijf-service',
        'mapper/bedrijf-mapper'],
    function ($, commonFunctions, ko, Bedrijf, functions, block, log, redirect, bedrijfService, bedrijfMapper) {

        return function (term) {
            var _this = this;
            this.lijst = ko.observableArray([]);
            this.zoekTerm = ko.observable(term);
            this.gezochtMetTonen = ko.observable();
            this.gezochtMet = ko.observable();

            var logger = log.getLogger('lijst-relaties-viewmodel');

            this.init = function () {
                bedrijfService.lijstBedrijven(_this.zoekTerm()).done(function (data) {
                    logger.debug('opgehaald ' + JSON.stringify(data));
                    var lijstje = bedrijfMapper.mapBedrijven(data);
                    $.each(lijstje(), function (i, bedrijf) {
                        _this.lijst().push(bedrijf);
                    });

                    logger.debug('Relaties opgehaald, refresh KO');
                    _this.lijst.valueHasMutated();
                    $.unblockUI();
                });
                if (term != null) {
                    _this.gezochtMetTonen(true);
                    _this.gezochtMet(term);
                } else {
                    _this.gezochtMetTonen(false);
                }

            }

            this.naarDetailScherm = function (relatie) {
                logger.debug('Ga naar Relatie met id ' + ko.utils.unwrapObservable(relatie.id));
                commonFunctions.verbergMeldingen();
                redirect.redirect('BEHEREN_BEDRIJF', ko.utils.unwrapObservable(relatie.id));
            }

            this.toevoegenNieuwBedrijf = function () {
                logger.debug('Toevoegen nieuw bedrijf');
                functions.verbergMeldingen();
                redirect.redirect('BEHEREN_BEDRIJF', '0');
            }

            this.zoeken = function () {
                logger.debug('zoeken met zoekTem ' + _this.zoekTerm());
                redirect.redirect('LIJST_BEDRIJVEN', _this.zoekTerm());
            }

            this.maakAdresOp = function (adressen) {
                logger.debug('maakAdresOp');
                var adres = null;
                $.each(adressen(), function (index, item) {
                    if (item.soortAdres() === 'WOONADRES') {
                        adres = item;
                    }
                    if (adres == null) {
                        if (item.soortAdres() === 'POSTADRES') {
                            adres = item;
                        }
                    }
                });

                if (adres !== null) {
                    return adres.straat() + ' ' + adres.huisnummer() + ', ' + adres.plaats();
                } else {
                    return '';
                }
            }

            $('#zoekTerm').on('keypress', function (e) {
                if (e != null && e.keyCode == 13) {
                    redirect.redirect('LIJST_BEDRIJVEN', $('#zoekTerm').val());
                }
            });

            _this.init();
        };
    });