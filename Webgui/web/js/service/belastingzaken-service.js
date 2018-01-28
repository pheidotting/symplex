define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository',
        'repository/gebruiker-repository',
        'repository/bedrijf-repository',
        'service/common/opmerking-service',
        'service/common/bijlage-service'],
    function(log, navRegister, ko, repository, gebruikerRepository, bedrijfRepository, opmerkingService, bijlageService) {

        return {
            lees: function(identificatie){
                var deferred = $.Deferred();

                var result = {};
                $.when(gebruikerRepository.leesRelatie(identificatie, true)).then(function(data) {
                    if(data.identificatie != null){
                        result.soort = 'RELATIE';
                        result.data = data;
                        return deferred.resolve(result);
                    }else{
                        $.when(bedrijfRepository.leesBedrijf(identificatie)).then(function(data) {
                            result.soort = 'BEDRIJF';
                            result.data = data;
                            return deferred.resolve(result);
                        })
                    }
                });

                return deferred.promise();
            }
        }
    }
);