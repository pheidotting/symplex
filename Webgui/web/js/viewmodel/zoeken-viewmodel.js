define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'model/relatie',
        'model/zoekvelden',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log',
        'redirect',
        'service/toggle-service',
        'service/zoeken-service',
        'service/gebruiker-service',
        'mapper/zoekresultaat-mapper',
        'viewmodel/common/menubalk-viewmodel',
        'viewmodel/common/licentie-viewmodel'],
    function ($, commonFunctions, ko, Relatie, zoekvelden, functions, block, log, redirect, toggleService, zoekenService, gebruikerService, zoekresultaatMapper, menubalkViewmodel, LicentieViewmodel) {

        return function () {
            commonFunctions.checkNieuweVersie();
            var _this = this;
            var logger = log.getLogger('zoeken-viewmodel');

            this.lijst = ko.observableArray();

            this.zoekTerm = ko.observable('');
            this.zoekResultaat = ko.observableArray([]);
            this.zoekvelden = new zoekvelden();
            this.menubalkViewmodel = null;
            this.licentieViewmodel = null;

            this.veranderDatum = function (datum) {
                datum(commonFunctions.zetDatumOm(datum()));
            };

            this.init = function () {
                var deferred = $.Deferred();

                _this.menubalkViewmodel = new menubalkViewmodel();
                _this.licentieViewmodel = new LicentieViewmodel();

                $.when(zoekenService.zoeken(), gebruikerService.haalIngelogdeGebruiker()).then(function (zoekResultaat) {
                    $.each(zoekresultaatMapper.mapZoekresultaten(zoekResultaat.bedrijfOfRelatieList)(), function (i, gemapt) {
                        _this.zoekResultaat.push(gemapt);
                        _this.zoekResultaat.valueHasMutated();
                    });

                    return deferred.resolve();
                });

                return deferred.promise();
            };

            this.zoeken = function () {
                logger.debug('we gaan zoeken');
                _this.zoekResultaat([]);
                if (_this.zoekvelden.geboortedatum() != null) {
                    if (_this.zoekvelden.geboortedatum() == '') {
                        _this.zoekvelden.geboortedatum(undefined);
                    }
                }
                $.when(zoekenService.zoeken(btoa(ko.toJSON(_this.zoekvelden)))).then(function (zoekResultaat) {
                    $.each(zoekresultaatMapper.mapZoekresultaten(zoekResultaat.bedrijfOfRelatieList)(), function (i, gemapt) {
                        _this.zoekResultaat.push(gemapt);
                        _this.zoekResultaat.valueHasMutated();
                    });
                });
            };

            this.maaklink = function (index, se) {
                var postLink = '';
                var soortEntiteit = se;
                if (index) {
                    var entiteit = _this.zoekResultaat()[index()];
                    soortEntiteit = entiteit.soortEntiteit().toLowerCase();

                    postLink = '/' + entiteit.identificatie();
                }
                return 'beheren.html#' + soortEntiteit + postLink;
            };

            this.nieuweRelatie = function () {
                window.location = _this.maaklink(0, 'relatie');
            };

            this.nieuwBedrijf = function () {
                window.location = _this.maaklink(0, 'bedrijf');
            };
        };
    });