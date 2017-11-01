define(['commons/3rdparty/log2',
        'navRegister',
        'knockout',
        'repository/common/repository',
        'repository/common/adres-repository'],
    function(log, navRegister, ko, repository, adresRepository) {
        var logger = log.getLogger('opmerking-service');

        return {
            opslaan: function(adressen, trackAndTraceId, soortEntiteit, id) {
                logger.debug('opslaan adressen ' + adressen);

                if(adressen() != null && adressen().length > 0) {
                    $.each(adressen(), function(i, adres){
                        adres.parentIdentificatie(id);
                        adres.soortEntiteit(soortEntiteit);
                        adres.plaats(adres.plaats().toUpperCase());
                    });

                    return adresRepository.opslaan(adressen, trackAndTraceId);
                } else {
                    return adresRepository.verwijder(trackAndTraceId, soortEntiteit, id);
                }
            },

            lijst: function(soortEntiteit, parentid){
                return adresRepository.lijst(soortEntiteit, parentid);
            },

            ophalenAdresOpPostcode: function(postcode, huisnummer){
                return adresRepository.ophalenAdresOpPostcode(postcode, huisnummer);
            }
        }
    }
);