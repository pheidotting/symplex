define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log',
		'redirect',
        'viewmodel/common/menubalk-viewmodel',
        'service/kantoor-service',
        'moment'],
    function($, commonFunctions, ko, functions, block, log, redirect, menubalkViewmodel, kantoorService, moment) {

    return function() {
        commonFunctions.checkNieuweVersie();
        var _this = this;
        var logger = log.getLogger('instellingen-viewmodel');
		this.menubalkViewmodel      = null;

        this.bedrijfsnaam = ko.observable();
        this.emailadres = ko.observable();
        this.kvk = ko.observable();
        this.afkorting = ko.observable();
        this.identificatie = ko.observable();

        this.init = function() {
            logger.info('start');
            var deferred = $.Deferred();

            _this.menubalkViewmodel     = new menubalkViewmodel();

            $.when(kantoorService.lees()).then(function(kantoor){
                _this.bedrijfsnaam(kantoor.naam);
                _this.emailadres(kantoor.emailadres);
                _this.kvk(kantoor.kvk);
                _this.afkorting(kantoor.afkorting);
                _this.identificatie(kantoor.identificatie);

                return deferred.resolve();
            });

            return deferred.promise();
        };

        this.opslaan = function(){
            var deferred = $.Deferred();

            var kantoor = {
                'naam' : _this.bedrijfsnaam(),
                'emailadres' : _this.emailadres(),
                'kvk' : _this.kvk(),
                'afkorting' : _this.afkorting(),
                'identificatie' : _this.identificatie()
            };

            $.when(kantoorService.opslaan(kantoor)).then(function(kantoor){
                return deferred.resolve();
            });

            return deferred.promise();
        };

        this.annuleren = function(){
            window.location = 'zoeken.html';
        };
	};
});