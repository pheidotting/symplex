define(["commons/3rdparty/log",
        "navRegister",
        'repository/common/repository',
        'repository/gebruiker-repository'],
    function (log, navRegister, repository, gebruikerRepository) {
        var logger = log.getLogger('mijn-gegevens-service');

        return {
            opslaan: function (medewerker) {
                var deferred = $.Deferred();

                $.when(gebruikerRepository.opslaanMedewerker(medewerker)).then(function (response) {
                    logger.debug('opslaan medewerker');

                    return deferred.resolve();
                });

                return deferred.promise();
            }
        }
    }
);