define(["commons/3rdparty/log2",
        "navRegister",
        'knockout',
        'repository/common/repository'],
    function(log, navRegister, ko, abstractRepository) {
        var logger = log.getLogger('gebruiker-repository');

        return {

            opslaan: function(data, trackAndTraceId) {
                var url = navRegister.bepaalUrl('OPSLAAN_RELATIE');
                logger.debug("Versturen naar " + url + " : ");
                logger.debug(ko.toJSON(data));

                return abstractRepository.voerUitPost(url, ko.toJSON(data), trackAndTraceId);
            },

            verwijder: function(id, trackAndTraceId) {
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('VERWIJDER_RELATIE') + '/' + id, null, trackAndTraceId);
            },

            lees: function(id) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LEES_RELATIE_NW') + '/' + id);
            }
        }
    }
);