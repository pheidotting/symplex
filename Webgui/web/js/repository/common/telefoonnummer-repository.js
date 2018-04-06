define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository'],
    function (log, navRegister, ko, abstractRepository) {

        return {
            opslaan: function (telefoonnummers) {
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('OPSLAAN_TELEFOONNUMMERS'), ko.toJSON(telefoonnummers()));
            },

            verwijder: function (soortEntiteit, id) {
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('VERWIJDER_TELEFOONNUMMERS') + '/' + soortEntiteit + '/' + id, null);
            },

            lijst: function (soortEntiteit, parentid) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LIJST_TELEFOONNUMMERS') + '/' + soortEntiteit + '/' + parentid);
            }
        }
    }
);