define(["commons/3rdparty/log2",
        "navRegister",
        'knockout',
        'repository/common/repository'],
    function(log, navRegister, ko, abstractRepository) {
        var logger = log.getLogger('adres-repository');

        return {
            opslaan: function(adressen) {
                var request = ko.toJSON(adressen());

                logger.debug('verzend request : ' + request);

                return abstractRepository.voerUitPost(navRegister.bepaalUrl('OPSLAAN_ADRESSEN'), request);
            },

            verwijder: function(trackAndTraceId, soortEntiteit, id) {
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('VERWIJDER_ADRESSEN') + '/' + soortEntiteit + '/' + id, null);
            },

            lijst: function(soortEntiteit, parentid){
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LIJST_ADRESSEN') + '/' + soortEntiteit + '/' + parentid);
            },

            ophalenAdresOpPostcode: function(postcode, huisnummer){
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('OPHALEN_ADRES_OP_POSTCODE') + '/' +  postcode + '/' +  huisnummer);
            }
        }
    }
);