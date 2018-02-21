define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log2',
		'redirect',
        'viewmodel/common/menubalk-viewmodel',
        'mapper/medewerker-mapper',
        'service/kantoor-service',
        'service/gebruiker-service',
        'moment'],
    function($, commonFunctions, ko, functions, block, log, redirect, menubalkViewmodel, medewerkerMapper, kantoorService, gebruikerService, moment) {

    return function() {
        var _this = this;
        var logger = log.getLogger('medewerkers-viewmodel');
		this.menubalkViewmodel      = null;

        this.medewerker = null;

        this.init = function(identificatie) {
            var deferred = $.Deferred();

            _this.menubalkViewmodel     = new menubalkViewmodel();

            $.when(gebruikerService.leesMedewerker(identificatie)).then(function(medewerker){
                _this.medewerker = medewerkerMapper.mapMedewerker(medewerker);

                return deferred.resolve();
            });

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