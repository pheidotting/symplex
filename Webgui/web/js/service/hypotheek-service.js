define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository',
        'repository/hypotheek-repository',
        'repository/gebruiker-repository',
        'service/common/opmerking-service',
        'service/common/bijlage-service'],
    function(log, navRegister, ko, repository, hypotheekRepository, gebruikerRepository, opmerkingService, bijlageService) {

        return {
            lijstSoortenHypotheek: function(id) {
                return hypotheekRepository.lijstSoortenHypotheek(id);
            },

            lijstHypothekenInclDePakketten: function(relatieId) {
                return hypotheekRepository.lijstHypothekenInclDePakketten(relatieId);
            },

            lees: function(id){
                var identificatie = id.identificatie;
                var deferred = $.Deferred();

                $.when(gebruikerRepository.leesRelatie(identificatie, true)).then(function(data) {
                    return deferred.resolve(data);
                });

                return deferred.promise();
            },

            leesHypotheek: function(id) {
                var deferred = $.Deferred();

                $.when(
                    hypotheekRepository.leesHypotheek(id),
                    bijlageService.lijst('HYPOTHEEK', id),
                    bijlageService.lijstGroep('HYPOTHEEK', id),
                    opmerkingService.lijst('HYPOTHEEK', id)
                ).then(function(data, bijlages, groepenBijlages, opmerkingen){
                        data.bijlages = bijlages;
                        data.groepenBijlages = groepenBijlages;
                        data.opmerkingen = opmerkingen;

                        return deferred.resolve(data);
                });

                return deferred.promise();
            },

            lijstHypotheken: function(relatieId) {
                var deferred = $.Deferred();

                $.when(gebruikerRepository.leesRelatie(relatieId, true)).then(function(data) {
                    return deferred.resolve(data);
                });

                return deferred.promise();
            },

            lijstHypotheekPakketten: function(relatieId) {
                var deferred = $.Deferred();

                $.when(gebruikerRepository.leesRelatie(relatieId, true)).then(function(data) {
                    return deferred.resolve(data);
                });

                return deferred.promise();
            },

            opslaanHypotheek: function(hypotheek, opmerkingen) {
                var deferred = $.Deferred();

                hypotheek.hypotheekVorm = ko.observable(hypotheek.hypotheekVorm.id);
                hypotheek.opmerkingen = opmerkingen;

                hypotheekRepository.opslaanHypotheek(hypotheek).done(function(response) {
                    return deferred.resolve(response);
                });

                return deferred.promise();
            },

            verwijderHypotheek: function(id) {
                var deferred = $.Deferred();

                    hypotheekRepository.verwijderHypotheek(id).done(function(response) {
                        return deferred.resolve(response);
                    });

                return deferred.promise();
            }
        }
    }
);