define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'model/relatie',
        'model/zoekvelden',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log',
        'redirect',
        'service/toggle-service',
        'service/zoeken-service',
        'service/gebruiker-service',
        'service/kantoor-service',
        'mapper/medewerker-mapper',
        'mapper/taak-mapper',
        'viewmodel/common/menubalk-viewmodel',
        'viewmodel/common/licentie-viewmodel',
        'repository/common/repository',
        'navRegister'],
    function ($, commonFunctions, ko, Relatie, zoekvelden, functions, block, log, redirect, toggleService, zoekenService, gebruikerService, kantoorService, medewerkerMapper, taakMapper, menubalkViewmodel, LicentieViewmodel, repository, navRegister) {

        return function () {
            commonFunctions.checkNieuweVersie();
            var _this = this;
            var logger = log.getLogger('taken-viewmodel');

//            this.zoekvelden = new zoekvelden();

            this.menubalkViewmodel = null;
            this.licentieViewmodel = null;

            this.taken = ko.observableArray([]);

            this.init = function () {
                var deferred = $.Deferred();

                _this.menubalkViewmodel = new menubalkViewmodel();
                _this.licentieViewmodel = new LicentieViewmodel();

                $.when(kantoorService.lees(), repository.voerUitGet(navRegister.bepaalUrl('DASHBOARD'))).then(function (kantoor, result) {
                     var medewerkers = medewerkerMapper.mapMedewerkers(kantoor.medewerkers);

                    _this.taken = taakMapper.mapTaken(result.taken);

                    _.map(_this.taken(), function(taak){
                        taak.berekenToegewezenAanEnStatus();
                        var medewerker = _.chain(medewerkers())
                        .filter(function(medewerker){
                             return medewerker.identificatie() == taak.toegewezenAan();
                         })
                         .last()
                         .value();

                        if(medewerker != null){
                            taak.toegewezenAan(medewerker.volledigenaam());
                        }
                    });

                    return deferred.resolve();
                });

                return deferred.promise();
            };
        };
    }
);
