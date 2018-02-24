define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log2',
		'redirect',
        'model/medewerker',
        'viewmodel/common/menubalk-viewmodel',
        'mapper/medewerker-mapper',
        'service/kantoor-service',
        'service/gebruiker-service',
        'moment'],
    function($, commonFunctions, ko, functions, block, log, redirect, Medewerker, menubalkViewmodel, medewerkerMapper, kantoorService, gebruikerService, moment) {

    return function() {
        var _this = this;
        var logger = log.getLogger('medewerkers-viewmodel');
		this.menubalkViewmodel      = null;

        this.medewerker = null;

        this.init = function(identificatie) {
            var deferred = $.Deferred();

            _this.menubalkViewmodel     = new menubalkViewmodel();

            if(identificatie == null) {
                _this.medewerker = new Medewerker();

                return deferred.resolve();
            }else{
                $.when(gebruikerService.leesMedewerker(identificatie)).then(function(medewerker){
                    _this.medewerker = medewerkerMapper.mapMedewerker(medewerker);

                    return deferred.resolve();
                });
            }

            return deferred.promise();
        };

        this.opslaan = function(medewerker) {
            gebruikerService.opslaanMedewerker(medewerker.medewerker);
        };

        this.annuleren = function(medewerker) {
            window.location.hash = 'instellingen/medewerkers';
        };

        this.verwijderen = function() {
        };
	};
});