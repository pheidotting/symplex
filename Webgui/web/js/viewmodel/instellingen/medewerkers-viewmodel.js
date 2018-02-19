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
        'moment'],
    function($, commonFunctions, ko, functions, block, log, redirect, menubalkViewmodel, medewerkerMapper, kantoorService, moment) {

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

        this.bewerk = function() {
        };

        this.verwijder = function() {
        };

	};
});