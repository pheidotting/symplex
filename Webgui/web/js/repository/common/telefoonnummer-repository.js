define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository'],
    function(log, navRegister, ko, abstractRepository) {

        return {
            opslaan: function(telefoonnummers, trackAndTraceId) {
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('OPSLAAN_TELEFOONNUMMERS'), ko.toJSON(telefoonnummers()), trackAndTraceId);
            },

            verwijder: function(trackAndTraceId, soortEntiteit, id) {
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('VERWIJDER_TELEFOONNUMMERS') + '/' + soortEntiteit + '/' + id, null, trackAndTraceId);
            },

                lijst: function(soortEntiteit, parentid){
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LIJST_TELEFOONNUMMERS') + '/' + soortEntiteit + '/' + parentid);
            }
        }
    }
);