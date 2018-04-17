define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository'],
    function (log, navRegister, ko, abstractRepository) {
        var logger = log.getLogger('gebruiker-repository');

        return {

            opslaan: function (data) {
                var url = navRegister.bepaalUrl('OPSLAAN_RELATIE');
                logger.debug("Versturen naar " + url + " : ");
                logger.debug(ko.toJSON(data));

                return abstractRepository.voerUitPost(url, ko.toJSON(data));
            },

            verwijder: function (id) {
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('VERWIJDER_RELATIE') + '/' + id, null);
            },

            lees: function (id) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LEES_RELATIE_NW') + '/' + id);
            }
        }
    }
);