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

//            this.aantalOpenSchades = ko.observable();
//            this.aantalRelaties = ko.observable();
            this.taken = ko.observableArray([]);

            this.toewijzenaanNw = ko.observable();
            this.statusNw = ko.observable();
            this.opmerkingNw = ko.observable();
            this.taakId = ko.observable();

            this.init = function (identificatie) {
                var deferred = $.Deferred();

                _this.menubalkViewmodel = new menubalkViewmodel();
                _this.licentieViewmodel = new LicentieViewmodel();
                _this.taakId(identificatie.identificatie);

                $.when(kantoorService.lees(), repository.voerUitGet(navRegister.bepaalUrl('LEES_TAAK')+'/'+identificatie.identificatie)).then(function (kantoor, taak) {
                     var medewerkers = medewerkerMapper.mapMedewerkers(kantoor.medewerkers);

                    var $toewijzing = $('#toewijzenaanNw');
                    $('<option>', {value: ''}).text('Kies (evt.) iemand om de taak aan toe te wijzen...').appendTo($toewijzing);
                    $.each(medewerkers(), function (key, value) {
                        $('<option>', {value: value.identificatie()}).text(value.volledigenaam()).appendTo($toewijzing);
                    });

                    _this.taak = taakMapper.mapTaak(taak);

                        _this.taak.berekenToegewezenAanEnStatus();
                        var medewerker = _.chain(medewerkers())
                        .filter(function(medewerker){
                             return medewerker.identificatie() == _this.taak.toegewezenAan();
                         })
                         .last()
                         .value();

                        if(medewerker != null){
                            _this.taak.toegewezenAan(medewerker.volledigenaam());
                        }

                        _.each(_this.taak.wijzigingTaaks(), function(wt) {
                            var medewerker = _.chain(medewerkers())
                            .filter(function(medewerker){
                                 return medewerker.identificatie() == wt.toegewezenAan();
                             })
                             .last()
                             .value();

                            if(medewerker != null){
                                wt.toegewezenAan(medewerker.volledigenaam());
                            }
                        });

                    return deferred.resolve();
                });

                return deferred.promise();
            };

            this.opslaan = function(){
                var data = {'toegewezenAan':_this.toewijzenaanNw(),
                                           'taakStatus':_this.statusNw(),
                                           'opmerking':_this.opmerkingNw(),
                                           'taak':_this.taakId()};

                repository.voerUitPost(navRegister.bepaalUrl('OPSLAAN_TAAK'), ko.toJSON(data));

            };
        };
    }
);
