define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository'],
    function(log, navRegister, ko, abstractRepository) {

        return {
            opslaan: function(opmerkingen) {
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('OPSLAAN_OPMERKINGEN'), ko.toJSON(opmerkingen));
            },

            verwijder: function(trackAndTraceId, soortEntiteit, id) {
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('VERWIJDER_OPMERKINGEN') + '/' + soortEntiteit + '/' + id, null);
            },

            lijst: function(soortEntiteit, parentid){
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LIJST_OPMERKINGEN') + '/' + soortEntiteit + '/' + parentid);
            }
        }
    }
);