define(['commons/3rdparty/log2',
        'navRegister',
        'knockout',
        'repository/common/repository',
        'repository/common/rekeningnummer-repository'],
    function(log, navRegister, ko, repository, rekeningnummerRepository) {
        var logger = log.getLogger('rekeningnummer-service');

        return {
            opslaan: function(rekeningnummers, trackAndTraceId, soortEntiteit, id) {
                logger.debug('opslaan rekeningnummers ' + rekeningnummers);

                if(rekeningnummers() != null && rekeningnummers().length > 0) {
                    $.each(rekeningnummers(), function(i, rekeningnummer){
                        rekeningnummer.parentIdentificatie(id);
                        rekeningnummer.soortEntiteit(soortEntiteit);
        			    rekeningnummer.rekeningnummer(rekeningnummer.rekeningnummer().replace(/ /g, ""));
                    });

                    return rekeningnummerRepository.opslaan(rekeningnummers, trackAndTraceId);
                } else {
                    return rekeningnummerRepository.verwijder(trackAndTraceId, soortEntiteit, id);
                }
            },

            lijst: function(soortEntiteit, parentid){
                return rekeningnummerRepository.lijst(soortEntiteit, parentid);
            }
        }
    }
);