define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log',
        'redirect',
        'viewmodel/common/menubalk-viewmodel',
        'viewmodel/common/licentie-viewmodel',
        'service/kantoor-service',
        'moment',
        'service/instellingen/licentie-service'],
    function ($, commonFunctions, ko, functions, block, log, redirect, menubalkViewmodel, LicentieViewmodel, kantoorService, moment, licentieService) {

        return function () {
            commonFunctions.checkNieuweVersie();
            var _this = this;
            var logger = log.getLogger('licenties-kopen-viewmodel');
            this.menubalkViewmodel = null;
            this.licentieViewmodel = null;

            this.licentie = ko.observable();
            this.gekocht = ko.observable(false);

            this.bedrijfsnaam = ko.observable();
            this.emailadres = ko.observable();
            this.kvk = ko.observable();
            this.afkorting = ko.observable();
            this.identificatie = ko.observable();

            _this.menubalkViewmodel = new menubalkViewmodel();
            _this.licentieViewmodel = new LicentieViewmodel();

            _this.licentieKopen = function () {
                logger.info('Licentie kopen ' + data);
                $.when(licentieService.licentieKopen(_this.licentie())).done(function () {
                    logger.info('Licentie gekocht');
                    _this.gekocht(true);
                });
            };
        };
    });