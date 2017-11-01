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

            opslaanMedewerker: function(data, trackAndTraceId) {
                var url = navRegister.bepaalUrl('OPSLAAN_MEDEWERKER');
                logger.debug("Versturen naar " + url + " : ");
                logger.debug(ko.toJSON(data));

                return abstractRepository.voerUitPost(url, ko.toJSON(data), trackAndTraceId);
            },

            verwijderRelatie: function(id, trackAndTraceId) {
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('VERWIJDER_RELATIE') + '/' + id, null, trackAndTraceId);
            },

            leesRelatie: function(id) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LEES_RELATIE_NW') + '/' + id);
            },

            leesMedewerker: function(id) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LEES_MEDEWERKER'), {id : id});
            },

            lijstRelaties: function(zoekTerm, weglaten){
                logger.debug('ophalen lijst relaties met zoekTerm '+ zoekTerm);
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LIJST_RELATIES'), {"zoekTerm" : zoekTerm, "weglaten" : weglaten});
            },

            opslaanOAuthCode: function(code, trackAndTraceId) {
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('OPSLAAN_OAUTH_CODE'), code, trackAndTraceId);
            },

            haalIngelogdeGebruiker: function(){
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('INGELOGDE_GEBRUIKER'));
            },

            leesOAuthCode: function() {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LEES_OAUTH_CODE'));
            }
        }
    }
);