define(["commons/3rdparty/log2",
        "navRegister",
        'knockout',
        'repository/common/repository',
        'repository/relatie-repository'],
    function(log, navRegister, ko, repository, relatieRepository) {
        var logger = log.getLogger('gebruiker-service');

        return {
            opslaan: function(relatie, adressen, telefoonnummers, rekeningnummers, opmerkingen) {
                var deferred = $.Deferred();

                    $.when(gebruikerRepository.opslaan(relatie)).then(function(response) {
                        var id = response.entity.foutmelding;
                        var soortEntiteit = 'RELATIE';

                        $.when(adresService.opslaan(adressen, soortEntiteit, id),
                            telefoonnummerService.opslaan(telefoonnummers, soortEntiteit, id),
                            rekeningnummerService.opslaan(rekeningnummers, soortEntiteit, id),
                            opmerkingService.opslaan(opmerkingen, soortEntiteit, id))
                        .then(function(adresResponse, telefoonnummerResponse, rekeningnummerResponse, opmerkingResponse) {
                            return deferred.resolve(id);
                        });
                    });

                return deferred.promise();
            },

            lees: function(id) {
                logger.debug('ophalen relatie met id ' + id);

                var deferred = $.Deferred();

                if (id == null) {
                    return deferred.resolve({});
                } else {
                    $.when(relatieRepository.lees(id)
                            ).then(function(relatie) {
                                return deferred.resolve(relatie);
                    });
                }

                return deferred.promise();
            },

            verwijder: function(id) {
                gebruikerRepository.verwijderRelatie(id);
            }
        }
    }
);