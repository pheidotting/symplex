define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository'],
    function(log, navRegister, ko, abstractRepository) {

        return {
            opslaan: function(rekeningnummers, trackAndTraceId) {
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('OPSLAAN_REKENINGNUMMERS'), ko.toJSON(rekeningnummers()), trackAndTraceId);
            },

            verwijder: function(trackAndTraceId, soortEntiteit, id) {
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('VERWIJDER_REKENINGNUMMERS') + '/' + soortEntiteit + '/' + id, null, trackAndTraceId);
            },

            lijst: function(soortEntiteit, parentid){
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LIJST_REKENINGNUMMERS') + '/' + soortEntiteit + '/' + parentid);
            }
        }
    }
);