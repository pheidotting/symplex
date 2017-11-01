define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'model/relatie',
        'model/zoekvelden',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log2',
		'redirect',
        'service/toggle-service',
        'service/zoeken-service',
        'service/gebruiker-service',
        'mapper/zoekresultaat-mapper',
        'moment'],
    function($, commonFunctions, ko, Relatie, zoekvelden, functions, block, log, redirect, toggleService, zoekenService, gebruikerService, zoekresultaatMapper, moment) {

    return function() {
        var _this = this;
        var logger = log.getLogger('zoeken-viewmodel');
        var soortEntiteit = '';

		this.lijst = ko.observableArray();

        this.zoekTerm = ko.observable('');
        this.zoekResultaat = ko.observableArray([]);
        this.zoekvelden = new zoekvelden();

		this.veranderDatum = function(datum){
			datum(commonFunctions.zetDatumOm(datum()));
		};

        this.init = function() {
            var deferred = $.Deferred();

            $.when(zoekenService.zoeken(), gebruikerService.haalIngelogdeGebruiker()).then(function(zoekResultaat){
                $.each(zoekresultaatMapper.mapZoekresultaten(zoekResultaat.bedrijfOfRelatieList)(), function(i, gemapt) {
                    _this.zoekResultaat.push(gemapt);
                    _this.zoekResultaat.valueHasMutated();
                });

                return deferred.resolve();
            });

            return deferred.promise();
        };

        this.zoeken = function() {
            _this.zoekResultaat([]);
            if(_this.zoekvelden.geboortedatum() != null) {
                if(_this.zoekvelden.geboortedatum() == '') {
                    _this.zoekvelden.geboortedatum(undefined);
                }
            }
            $.when(zoekenService.zoeken(btoa(ko.toJSON(_this.zoekvelden)))).then(function(zoekResultaat){
                $.each(zoekresultaatMapper.mapZoekresultaten(zoekResultaat.bedrijfOfRelatieList)(), function(i, gemapt) {
                    _this.zoekResultaat.push(gemapt);
                    _this.zoekResultaat.valueHasMutated();
                });
            });
        };

        this.maaklink = function(index, se) {
            var postLink = '';
            var soortEntiteit = se;
            if(index) {
                var entiteit = _this.zoekResultaat()[index()];
                soortEntiteit = entiteit.soortEntiteit().toLowerCase();

                postLink = '/' + entiteit.identificatie();
            }
            return 'beheren.html#' + soortEntiteit + postLink;
        };

        this.nieuweRelatie = function(){
            window.location = _this.maaklink(0, 'relatie');
        };

        this.nieuwBedrijf = function(){
            window.location = _this.maaklink(0, 'bedrijf');
        };
	};
});