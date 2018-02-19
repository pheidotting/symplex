define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log2',
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
        this.btwnummer = ko.observable();
        this.datumoprichting = ko.observable();
        this.emailadres = ko.observable();
        this.kvk = ko.observable();
        this.rechtsvorm = ko.observable();
        this.afkorting = ko.observable();
        this.identificatie = ko.observable();

        this.init = function() {
            var deferred = $.Deferred();

            _this.menubalkViewmodel     = new menubalkViewmodel();

            $.when(kantoorService.lees()).then(function(kantoor){
                _this.bedrijfsnaam(kantoor.naam);
                _this.btwnummer(kantoor.btwNummer);
                _this.datumoprichting(kantoor.datumOprichting);
                _this.emailadres(kantoor.emailadres);
                _this.kvk(kantoor.kvk);
                _this.rechtsvorm(kantoor.rechtsvorm);
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
                'btwNummer' : _this.btwnummer(),
                'datumOprichting' : _this.datumoprichting(),
                'vemailadres' : _this.emailadres(),
                'kvk' : _this.kvk(),
                'rechtsvorm' : _this.rechtsvorm(),
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