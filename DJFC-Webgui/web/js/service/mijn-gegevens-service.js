define(["commons/3rdparty/log2",
        "navRegister",
        'repository/common/repository',
        'repository/gebruiker-repository'],
    function(log, navRegister, repository, gebruikerRepository) {
        var logger = log.getLogger('mijn-gegevens-service');

        return {
            opslaan: function(medewerker){
                var deferred = $.Deferred();

                $.when(repository.leesTrackAndTraceId()).then(function(trackAndTraceId){
                    $.when(gebruikerRepository.opslaanMedewerker(medewerker, trackAndTraceId)).then(function(response){
                        logger.debug('opslaan medewerker');

                        return deferred.resolve();
                    });
                });

                return deferred.promise();
            }
        }
    }
);