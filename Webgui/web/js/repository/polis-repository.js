define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository'],
    function(log, navRegister, ko, abstractRepository) {

        return {
            opslaan: function(data, trackAndTraceId) {
                var url = navRegister.bepaalUrl('OPSLAAN_POLIS');
                log.debug("Versturen naar " + url + " : ");
                log.debug(ko.toJSON(data));

                return abstractRepository.voerUitPost(url, ko.toJSON(data), trackAndTraceId);
            },

            verwijder: function(id) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('VERWIJDER_POLIS'), {id : id});
            },

            lees: function(id) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LEES_POLIS'), {id : id});
            },

            lijstVerzekeringsmaatschappijen: function(){
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LIJST_VERZEKERINGSMAATSCHAPPIJEN'));
            },

            lijstParticulierePolissen: function(){
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LIJST_PARTICULIEREPOLISSEN'));
            },

            lijstZakelijkePolissen: function(){
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LIJST_ZAKELIJKEPOLISSEN'));
            },

            lijstPolissen: function(relatieId, bedrijfId){
                if(relatieId != null) {
                    return abstractRepository.voerUitGet(navRegister.bepaalUrl('LIJST_POLISSEN'), {relatieId : relatieId});
                } else {
                    return abstractRepository.voerUitGet(navRegister.bepaalUrl('LIJST_POLISSEN_BIJ_BEDRIJF'), {bedrijfId : bedrijfId});
                }
            },

            beindigPolis: function(id, trackAndTraceId){
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('BEEINDIG_POLIS') + '/' + id, trackAndTraceId);
            },

            verwijderPolis: function(id, trackAndTraceId){
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('VERWIJDER_POLIS') + '/' + id, trackAndTraceId);
            }
        }
    }
);