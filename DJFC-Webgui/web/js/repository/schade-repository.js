define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository'],
    function(log, navRegister, ko, abstractRepository) {

        return {
            opslaan: function(data, trackAndTraceId) {
                var url = navRegister.bepaalUrl('OPSLAAN_SCHADE');
                log.debug("Versturen naar " + url + " : ");
                log.debug(ko.toJSON(data));

                return abstractRepository.voerUitPost(url, ko.toJSON(data), trackAndTraceId);
            },

            verwijder: function(id) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('VERWIJDER_SCHADE'), {id : id});
            },

            lees: function(id) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LEES_SCHADE'), {"id" : id});
            },

            lijstStatusSchade: function() {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LIJST_STATUS_SCHADE'), null);
            },

            lijstSchades: function(entiteitId, soortEntiteit) {
                if(soortEntiteit == 'RELATIE') {
                    return abstractRepository.voerUitGet(navRegister.bepaalUrl('LIJST_SCHADES'), {relatieId : entiteitId});
                } else {
                    return abstractRepository.voerUitGet(navRegister.bepaalUrl('LIJST_SCHADES_BIJ_BEDRIJF'), {bedrijfId : entiteitId});
                }
            },

            verwijderSchade: function(id) {
                var deferred = $.Deferred();

                $.when(abstractRepository.voerUitGet(navRegister.bepaalUrl('TRACKANDTRACEID'))).then(function(trackAndTraceId) {
                    abstractRepository.voerUitPost(navRegister.bepaalUrl('VERWIJDER_SCHADE') + '/' + id, null, trackAndTraceId).done(function(response) {
                        return deferred.resolve(response);
                    });
                });

                return deferred.promise();
            }
        }
    }
);