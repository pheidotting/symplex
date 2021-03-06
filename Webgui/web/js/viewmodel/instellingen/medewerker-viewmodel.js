define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log',
        'redirect',
        'model/medewerker',
        'viewmodel/common/menubalk-viewmodel',
        'viewmodel/common/licentie-viewmodel',
        'mapper/medewerker-mapper',
        'service/kantoor-service',
        'service/gebruiker-service',
        'knockout.validation',
        'knockoutValidationLocal'],
    function ($, commonFunctions, ko, functions, block, log, redirect, Medewerker, menubalkViewmodel, LicentieViewmodel, medewerkerMapper, kantoorService, gebruikerService) {

        return function () {
            var _this = this;
            var logger = log.getLogger('medewerkers-viewmodel');
            this.menubalkViewmodel = null;
            this.licentieViewmodel = null;

            this.medewerker = null;
            this.nieuweMedewerker = ko.observable(false);

            this.init = function (identificatie) {
                logger.info('start voor identificatie ' + identificatie);
                var deferred = $.Deferred();

                _this.menubalkViewmodel = new menubalkViewmodel();
                _this.licentieViewmodel = new LicentieViewmodel();

                if (identificatie == null) {
                    _this.medewerker = new Medewerker();
                    _this.nieuweMedewerker(true);

                    return deferred.resolve();
                } else {
                    $.when(gebruikerService.leesMedewerker(identificatie)).then(function (medewerker) {
                        _this.medewerker = medewerkerMapper.mapMedewerker(medewerker);
                        _this.medewerker.licentieType = ko.observable();
                        _this.nieuweMedewerker(false);

                        return deferred.resolve();
                    });
                }

                return deferred.promise();
            };

            this.opslaan = function () {
                var result = ko.validation.group(_this, {deep: true});
                if (result().length > 0) {
                    result.showAllMessages(true);
                } else {
                    gebruikerService.opslaanMedewerker(_this.medewerker);
                    window.location.hash = 'medewerkers';
                }
            };

            this.annuleren = function () {
                window.location.hash = 'medewerkers';
            };
        };
    });