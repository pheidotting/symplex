define(['commons/3rdparty/log',
        'navRegister',
        'knockout',
        'repository/common/repository',
        'repository/common/telefoonnummer-repository'],
    function(log, navRegister, ko, repository, telefoonnummerRepository) {
        var logger = log.getLogger('telefoonnummer-service');

        return {
            opslaan: function(telefoonnummers, soortEntiteit, id) {
                logger.debug('opslaan telefoonnummers ' + telefoonnummers);


                if(telefoonnummers() != null && telefoonnummers().length > 0) {
                    $.each(telefoonnummers(), function(i, telefoonnummer){
                        telefoonnummer.parentIdentificatie(id);
                        telefoonnummer.soortEntiteit(soortEntiteit);
                        if(telefoonnummer.telefoonnummer() != null && telefoonnummer.telefoonnummer() != '') {
            			    telefoonnummer.telefoonnummer(telefoonnummer.telefoonnummer().replace(/ /g, "").replace("-", ""));
                        }
                    });

                    return telefoonnummerRepository.opslaan(telefoonnummers);
                } else {
                    return telefoonnummerRepository.verwijder(soortEntiteit, id);
                }
            },

            lijst: function(soortEntiteit, parentid){
                return telefoonnummerRepository.lijst(soortEntiteit, parentid);
            }
        }
    }
);