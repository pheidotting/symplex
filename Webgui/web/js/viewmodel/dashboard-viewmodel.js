define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'model/relatie',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log2',
		'redirect',
		'repository/common/repository',
        'service/gebruiker-service',
		'service/taak/taak-service',
        'service/toggle-service',
		'underscore'],
    function($, commonFunctions, ko, Relatie, functions, block, log, redirect, repository, gebruikerService, taakService, toggleService, _) {

    return function() {
        var _this = this;
        this.aantalOpenstaandeTaken = ko.observable();
        var logger = log.getLogger('dahsboard-viewmodel');
        this.beheerZichtbaar = ko.observable();

        this.init = function() {
            $.when(toggleService.isFeatureBeschikbaar('TODOIST'), toggleService.isFeatureBeschikbaar('BEHEERPAGINA')).then(function(toggleBeschikbaar, toggleBeheerBeschikbaar) {
                logger.debug('ophalen aantal open taken');

                _this.beheerZichtbaar(toggleBeheerBeschikbaar);
                $.when(taakService.haalAfgerondeTaken()).then(function(taken) {
                    console.log(taken);

                    var opslaan = _.chain(taken)
                    .filter(function(taak) {
                        return taak.labels.length == 2;
                    })
                    .map(function(taak) {
                        var t = {};
                        t.omschrijving = taak.content;
                        t.afgerond = true;
                        if($.isNumeric(taak.labels[0])) {
                            t.entiteitId = taak.labels[0];
                            t.soortEntiteit = taak.labels[1];
                        } else {
                            t.entiteitId = taak.labels[1];
                            t.soortEntiteit = taak.labels[0];
                        }
                        t.todoistId = taak.id;
                        t.notities = taak.notities;

                        return t;
                    }).value();

                    $.when(repository.leesTrackAndTraceId()).then(function(trackAndTraceId){
                        repository.voerUitPost('../dejonge/rest/medewerker/taak/opslaanAfgerondeTaken', JSON.stringify(opslaan), trackAndTraceId);
                    });

                    logger.debug(JSON.stringify(opslaan));
                });

                if(toggleBeschikbaar) {
                    $.when(taakService.aantalOpenTaken()).then(function(aantal) {
                        _this.aantalOpenstaandeTaken(aantal);
                        _this.aantalOpenstaandeTaken.valueHasMutated();
                    });
                }
            });
        };

        this.aantalOpenstaandeTakenTonen = ko.computed(function() {
            return parseInt(_this.aantalOpenstaandeTaken()) > 0;
        });

        this.naarParticulier = function() {
            logger.debug('lijst relaties');
            redirect.redirect('LIJST_RELATIES');
        };

        this.naarZakelijk = function() {
            logger.debug('lijst bedrijven');
            redirect.redirect('LIJST_BEDRIJVEN');
        };

        this.naarBeheer = function() {
            logger.debug('naar beheer');
            location.href = 'beheer.html';
        };
	};
});