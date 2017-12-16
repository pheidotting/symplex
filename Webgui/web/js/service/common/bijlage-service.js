define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository',
        'repository/common/bijlage-repository'],
    function(log, navRegister, ko, repository, bijlageRepository) {

        return {
            opslaan: function(bijlages, soortEntiteit, id) {

                if(bijlages != null && bijlages() != null && bijlages().length > 0) {
                    $.each(bijlages(), function(i, bijlage) {
                        bijlage.parentIdentificatie(id);
                        bijlage.soortEntiteit(soortEntiteit);
                    });

                    return bijlageRepository.opslaan(bijlages);
                } else {
                    return bijlageRepository.verwijder(trackAndTraceId, soortEntiteit, id);
                }
            },

            lijst: function(soortEntiteit, parentid) {
                return bijlageRepository.lijst(soortEntiteit, parentid);
            },

            lijstGroep: function(soortEntiteit, parentid) {
                return bijlageRepository.lijstGroep(soortEntiteit, parentid);
            },

            verwijderBijlage: function(id) {
                return bijlageRepository.verwijderBijlage(id);
            },
            
            wijzigOmschrijvingBijlage: function(id, nieuweOmschrijving) {
                return bijlageRepository.wijzigOmschrijvingBijlage(id, nieuweOmschrijving);
            }
        }
    }
);