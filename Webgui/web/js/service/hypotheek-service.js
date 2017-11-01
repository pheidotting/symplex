define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository',
        'repository/hypotheek-repository',
        'service/common/opmerking-service',
        'service/common/bijlage-service'],
    function(log, navRegister, ko, repository, hypotheekRepository, opmerkingService, bijlageService) {

        return {
            lijstSoortenHypotheek: function(id) {
                return hypotheekRepository.lijstSoortenHypotheek(id);
            },

            lijstHypothekenInclDePakketten: function(relatieId) {
                return hypotheekRepository.lijstHypothekenInclDePakketten(relatieId);
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
                return hypotheekRepository.lijstHypotheken(relatieId);
            },

            lijstHypotheekPakketten: function(relatieId) {
                return hypotheekRepository.lijstHypotheekPakketten(relatieId);
            },

            opslaanHypotheek: function(hypotheek, opmerkingen) {
                var deferred = $.Deferred();

                hypotheek.hypotheekVorm = ko.observable(hypotheek.hypotheekVorm.id);

                $.when(repository.leesTrackAndTraceId()).then(function(trackAndTraceId) {
                    hypotheekRepository.opslaanHypotheek(hypotheek, trackAndTraceId).done(function(response) {
                        var id = response.entity.foutmelding;
                        var soortEntiteit = 'HYPOTHEEK';

                        opmerkingService.opslaan(opmerkingen, trackAndTraceId, soortEntiteit, id).done(function() {
                            return deferred.resolve(response);
                        });
                    });
                });

                return deferred.promise();
            },

            verwijderHypotheek: function(id) {
                var deferred = $.Deferred();

                $.when(repository.leesTrackAndTraceId()).then(function(trackAndTraceId) {
                    hypotheekRepository.verwijderHypotheek(id, trackAndTraceId).done(function(response) {
                        return deferred.resolve(response);
                    });
                });

                return deferred.promise();
            }
        }
    }
);