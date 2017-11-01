define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository',
        'repository/polis-repository',
        'repository/gebruiker-repository',
        'repository/bedrijf-repository',
        'service/common/opmerking-service',
        'service/common/bijlage-service'],
    function(log, navRegister, ko, repository, polisRepository, gebruikerRepository, bedrijfRepository, opmerkingService, bijlageService) {

        return {
            opslaan: function(polis, opmerkingen, basisId){
                var deferred = $.Deferred();

                polis.maatschappij = ko.observable(polis.maatschappij.id);

                $.when(repository.leesTrackAndTraceId()).then(function(trackAndTraceId){
                    polis.opmerkingen = opmerkingen;
                    polis.parentIdentificatie = basisId;

                    console.log(polis);

                    $.when(polisRepository.opslaan(polis, trackAndTraceId)).then(function(id){
                        if(id != null && id != '') {
                            var soortEntiteit = 'POLIS';

                            $.when(opmerkingService.opslaan(opmerkingen, trackAndTraceId, soortEntiteit, id))
                            .then(function(opmerkingResponse){
                                return deferred.resolve(id);
                            });
                        }
                    });
                });

                return deferred.promise();
            },

            verwijder: function(id){
                return repository.voerUitGet(navRegister.bepaalUrl('VERWIJDER_POLIS'), {id : id});
            },

            lees: function(id, basisEntiteit){
                var identificatie = id.identificatie;
                var deferred = $.Deferred();

                $.when(gebruikerRepository.leesRelatie(identificatie)).then(function(data) {
                    return deferred.resolve(data);
                }).fail(function() {
                    $.when(bedrijfRepository.leesBedrijf(identificatie)).then(function(data) {
                        return deferred.resolve(data);
                    })
                });

                return deferred.promise();
            },

            lijstVerzekeringsmaatschappijen: function(){
                return polisRepository.lijstVerzekeringsmaatschappijen();
            },

            lijstParticulierePolissen: function(){
                return polisRepository.lijstParticulierePolissen();
            },

            lijstZakelijkePolissen: function(){
                return polisRepository.lijstZakelijkePolissen();
            },

            lijstPolissen: function(relatieId, bedrijfId){
                var deferred = $.Deferred();

                $.when(gebruikerRepository.leesRelatie(relatieId)).then(function(data) {
                    return deferred.resolve(data);
                }).fail(function() {
                    $.when(bedrijfRepository.leesBedrijf(relatieId)).then(function(data) {
                        return deferred.resolve(data);
                    })
                });

                return deferred.promise();
            },

            beindigPolis: function(id){
                $.when(repository.leesTrackAndTraceId()).then(function(trackAndTraceId){
                    polisRepository.beindigPolis(id, trackAndTraceId);
                });
            },

            verwijderPolis: function(id){
                $.when(repository.leesTrackAndTraceId()).then(function(trackAndTraceId){
                    polisRepository.verwijderPolis(id, trackAndTraceId);
                });
            }
        }
    }
);