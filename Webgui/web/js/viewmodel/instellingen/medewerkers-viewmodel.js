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
        commonFunctions.checkNieuweVersie();
        var _this = this;
        var logger = log.getLogger('medewerkers-viewmodel');
		this.menubalkViewmodel      = null;

        this.medewerkers = ko.observableArray();

        this.init = function() {
            var deferred = $.Deferred();

            _this.menubalkViewmodel     = new menubalkViewmodel();

            $.when(kantoorService.lees()).then(function(kantoor){
                _this.medewerkers = medewerkerMapper.mapMedewerkers(kantoor.medewerkers);

                return deferred.resolve();
            });

            return deferred.promise();
        };

        this.bewerk = function(medewerker) {
            window.location.hash = 'medewerker/' + medewerker.identificatie();
        };

        this.verwijder = function(medewerker) {
            var r=confirm("Weet je zeker dat je "+medewerker.volledigenaam()+" wilt verwijderen?");
            if (r==true) {
                _this.medewerkers.remove(medewerker);
                gebruikerService.verwijderRelatie(medewerker.identificatie());
            }
        };

        this.nieuw = function() {
            window.location.hash = 'medewerker';
        };
	};
});