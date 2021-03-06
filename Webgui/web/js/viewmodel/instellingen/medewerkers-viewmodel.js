define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log',
        'redirect',
        'viewmodel/common/menubalk-viewmodel',
        'viewmodel/common/licentie-viewmodel',
        'mapper/medewerker-mapper',
        'service/kantoor-service',
        'service/gebruiker-service',
        'moment',
        'underscore'],
    function ($, commonFunctions, ko, functions, block, log, redirect, menubalkViewmodel, LicentieViewmodel, medewerkerMapper, kantoorService, gebruikerService, moment, _) {

        return function () {
            commonFunctions.checkNieuweVersie();
            var _this = this;
            var logger = log.getLogger('medewerkers-viewmodel');
            this.menubalkViewmodel = null;
            this.licentieViewmodel = null;

            this.medewerkers = ko.observableArray();

            this.init = function () {
                logger.info('start');
                var deferred = $.Deferred();

                _this.menubalkViewmodel = new menubalkViewmodel();
                _this.licentieViewmodel = new LicentieViewmodel();

                $.when(kantoorService.lees()).then(function (kantoor) {
                    _this.medewerkers = medewerkerMapper.mapMedewerkers(kantoor.medewerkers);

                    _this.medewerkers = _.sortBy(_this.medewerkers(), function (m) {
                        return m.achternaam() + m.voornaam();
                    });

                    return deferred.resolve(true);
                });

                return deferred.promise();
            };

            this.bewerk = function (medewerker) {
                window.location.hash = 'medewerker/' + medewerker.identificatie();
            };

            this.verwijder = function (medewerker) {
                var r = confirm("Weet je zeker dat je " + medewerker.volledigenaam() + " wilt verwijderen?");
                if (r) {
                    gebruikerService.verwijderRelatie(medewerker.identificatie());
                    location.reload();
                }
            };

            this.nieuw = function () {
                window.location.hash = 'medewerker';
            };

            this.magMedewerkerToevoegen = function () {
                return _this.medewerkers.length < _this.licentieViewmodel.maxAantalMedewerkers();
            };
        };
    });