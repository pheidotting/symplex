define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository',
        'repository/schade-repository',
        'repository/gebruiker-repository',
        'repository/bedrijf-repository',
        'service/common/opmerking-service',
        'service/common/bijlage-service',
        'underscore',
        'moment'],
    function(log, navRegister, ko, repository, schadeRepository, gebruikerRepository, bedrijfRepository, opmerkingService, bijlageService, _, moment) {

        return {
            opslaan: function(schade, opmerkingen) {
                var deferred = $.Deferred();

                    schade.opmerkingen = opmerkingen;
                    schade.parentIdentificatie = schade.polis;

                    if(schade.datumTijdSchade().indexOf('-') == 2){
                        schade.datumTijdSchade(moment(schade.datumTijdSchade(), 'DD-MM-YYYY HH:mm').format('YYYY-MM-DDTHH:mm'));
                        schade.datumTijdMelding(moment(schade.datumTijdMelding(), 'DD-MM-YYYY HH:mm').format('YYYY-MM-DDTHH:mm'));
                    }

                    $.when(schadeRepository.opslaan(schade)).then(function(response) {
                        return deferred.resolve(response);
                    });

                return deferred.promise();
            },

            lees: function(id) {
                var identificatie = id;
                var deferred = $.Deferred();

                $.when(gebruikerRepository.leesRelatie(identificatie, true)).then(function(data) {
                    return deferred.resolve(data);
                }).fail(function() {
                    $.when(bedrijfRepository.leesBedrijf(identificatie)).then(function(data) {
                        return deferred.resolve(data);
                    })
                });

                return deferred.promise();
            },

            lijstStatusSchade: function() {
                return schadeRepository.lijstStatusSchade();
            },

            lijstSchades: function(identificatie) {
                var deferred = $.Deferred();

                $.when(gebruikerRepository.leesRelatie(identificatie, true)).then(function(data) {
                    return deferred.resolve(data);
                }).fail(function() {
                    $.when(bedrijfRepository.leesBedrijf(identificatie)).then(function(data) {
                        return deferred.resolve(data);
                    })
                });

                return deferred.promise();
            },

            verwijderSchade: function(id) {
                return schadeRepository.verwijderSchade(id);
            }
        }
    }
);