define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository'],
    function(log, navRegister, ko, abstractRepository) {

        return {
            opslaan: function(bijlages, trackAndTraceId) {
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('OPSLAAN_BIJLAGES'), ko.toJSON(bijlages()), trackAndTraceId);
            },

            verwijder: function(trackAndTraceId, soortEntiteit, id) {
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('VERWIJDER_BIJLAGES') + '/' + soortEntiteit + '/' + id, null, trackAndTraceId);
            },

            lijst: function(soortEntiteit, parentid){
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LIJST_BIJLAGES') + '/' + soortEntiteit + '/' + parentid);
            },

            lijstGroep: function(soortEntiteit, parentid) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LIJST_GROEP_BIJLAGES') + '/' + soortEntiteit + '/' + parentid);
            },

            verwijderBijlage: function(id) {
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('VERWIJDER_BIJLAGE') + '/' + id);
            },

            wijzigOmschrijvingBijlage: function(id, nieuweOmschrijving) {
                var data = {};
                data.bijlageId = id;
                data.nieuweOmschrijving = nieuweOmschrijving;

                return abstractRepository.voerUitPost(navRegister.bepaalUrl('WIJZIG_OMSCHRIJVING_BIJLAGE'), JSON.stringify(data));
            }
        }
    }
);