define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log2',
		'redirect',
        'viewmodel/common/menubalk-viewmodel',
        'moment'],
    function($, commonFunctions, ko, functions, block, log, redirect, menubalkViewmodel, ) {

    return function() {
        commonFunctions.checkNieuweVersie();
        var _this = this;
        var logger = log.getLogger('instellingen-viewmodel');
		this.menubalkViewmodel      = null;

        this.init = function() {
            var deferred = $.Deferred();

            _this.menubalkViewmodel     = new menubalkViewmodel();

//            $.when(instellingenService.instellingen(), gebruikerService.haalIngelogdeGebruiker()).then(function(zoekResultaat){
//                $.each(zoekresultaatMapper.mapZoekresultaten(zoekResultaat.bedrijfOfRelatieList)(), function(i, gemapt) {
//                    _this.zoekResultaat.push(gemapt);
//                    _this.zoekResultaat.valueHasMutated();
//                });
//
//                return deferred.resolve();
//            });

            return deferred.promise();
        };

	};
});