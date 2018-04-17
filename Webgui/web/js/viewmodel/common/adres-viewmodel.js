define(['commons/3rdparty/log',
        'model/adres',
        'service/common/adres-service',
        'knockout',
        'mapper/adres-mapper'],
    function (log, Adres, adresService, ko, adresMapper) {

        return function (readOnly, soortEntiteit, entiteitId, adressen) {
            var logger = log.getLogger('adres-viewmodel');
            var _this = this;

            this.id = ko.observable(entiteitId);
            this.soortEntiteit = ko.observable(soortEntiteit);
            this.adressen = ko.observableArray();
            $.each(adresMapper.mapAdressen(adressen)(), function (i, adres) {
                adres.postcode(adres.postcode().toUpperCase());
                adres.postcode(adres.postcode().substring(0, 4) + " " + adres.postcode().substring(4));

                _this.adressen.push(adres);
            });

            this.verwijderAdres = function (adres) {
                logger.debug('verwijder adres');
                _this.adressen.remove(adres);
            };

            this.voegAdresToe = function () {
                logger.debug('voeg adres toe');
                _this.adressen.push(new Adres());
            };

            this.zetPostcodeOm = function (adres) {
                logger.debug('postcode omzetten');
                if (adres != null && adres.postcode() != null && adres.postcode().length === 6) {
                    adres.postcode(adres.postcode().toUpperCase());
                    adres.postcode(adres.postcode().substring(0, 4) + " " + adres.postcode().substring(4));

                    return adres.postcode();
                }
            };

            this.opzoekenAdres = function (adres) {
                logger.debug('opzoeken adres ' + adres.postcode() + ' ' + adres.huisnummer());

                if (adres.postcode() != null && adres.huisnummer()) {
                    adres.postcode(adres.postcode().toUpperCase().replace(" ", ""));

                    adresService.ophalenAdresOpPostcode(adres.postcode(), adres.huisnummer()).done(function (data) {
                        logger.debug(JSON.stringify(data));
                        adres.straat(data.straat);
                        if (data.plaats != null) {
                            adres.plaats(data.plaats.toUpperCase());
                        }
                        adres.postcode(_this.zetPostcodeOm(adres));
                    }).fail(function (data) {
                        logger.debug(JSON.stringify(data));
                        adres.straat('');
                        adres.plaats('');
                    });
                }
            };

        };
    });