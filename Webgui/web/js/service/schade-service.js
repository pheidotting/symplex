define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository',
        'repository/schade-repository',
        'repository/gebruiker-repository',
        'repository/bedrijf-repository',
        'service/common/opmerking-service',
        'service/common/bijlage-service',
        'underscore',
        'moment'],
    function (log, navRegister, ko, repository, schadeRepository, gebruikerRepository, bedrijfRepository, opmerkingService, bijlageService, _, moment) {

        return {
            opslaan: function (schade, opmerkingen) {
                var deferred = $.Deferred();

                schade.opmerkingen = opmerkingen;
                schade.parentIdentificatie = schade.polis;

                if (schade.datumSchade().indexOf('-') == 2) {
                    schade.datumSchade(moment(schade.datumSchade(), 'DD-MM-YYYY').format('YYYY-MM-DD'));
                    schade.datumMelding(moment(schade.datumMelding(), 'DD-MM-YYYY').format('YYYY-MM-DD'));
                }

                $.when(schadeRepository.opslaan(schade)).then(function (response) {
                    return deferred.resolve(response);
                });

                return deferred.promise();
            },

            lees: function (id) {
                var identificatie = id;
                var deferred = $.Deferred();

                $.when(gebruikerRepository.leesRelatie(identificatie, true)).then(function (data) {
                    if (data.identificatie != null) {
                        return deferred.resolve(data);
                    } else {
                        $.when(bedrijfRepository.leesBedrijf(identificatie)).then(function (data) {
                            return deferred.resolve(data);
                        })
                    }
                });

                return deferred.promise();
            },

            lijstStatusSchade: function () {
                return schadeRepository.lijstStatusSchade();
            },

            lijstOpenSchades: function () {
                return schadeRepository.lijstOpenSchades();
            },

            lijstSchades: function (identificatie) {
                var deferred = $.Deferred();

                $.when(gebruikerRepository.leesRelatie(identificatie, true)).then(function (data) {
                    if (data.identificatie != null) {
                        return deferred.resolve(data);
                    } else {
                        $.when(bedrijfRepository.leesBedrijf(identificatie)).then(function (data) {
                            return deferred.resolve(data);
                        })
                    }
                });

                return deferred.promise();
            },

            verwijderSchade: function (id) {
                return schadeRepository.verwijderSchade(id);
            }
        }
    }
);