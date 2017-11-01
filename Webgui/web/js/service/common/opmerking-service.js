define(['commons/3rdparty/log2',
        'navRegister',
        'knockout',
        'repository/common/repository',
        'repository/common/opmerking-repository'],
    function(log, navRegister, ko, repository, opmerkingRepository) {
        var logger = log.getLogger('opmerking-service');

        return {
            opslaan: function(opmerkingen, trackAndTraceId, soortEntiteit, id) {
                logger.debug('opslaan opmerkingen ' + ko.toJSON(opmerkingen));

                if(opmerkingen != null && opmerkingen() != null && opmerkingen().length > 0) {
                    $.each(opmerkingen(), function(i, opmerking){
                        opmerking.parentIdentificatie(id);
                        opmerking.soortEntiteit(soortEntiteit);
                    });

                    return opmerkingRepository.opslaan(opmerkingen, trackAndTraceId);
                } else {
                    return opmerkingRepository.verwijder(trackAndTraceId, soortEntiteit, id);
                }
            },

            lijst: function(soortEntiteit, parentid){
                return opmerkingRepository.lijst(soortEntiteit, parentid);
            }
        }
    }
);