define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository',
        'repository/bedrijf-repository',
        'service/common/adres-service',
        'service/common/telefoonnummer-service',
        'service/common/rekeningnummer-service',
        'service/common/opmerking-service',
        'service/common/bijlage-service',
        'underscore'],
    function (log, navRegister, ko, repository, bedrijfRepository, adresService, telefoonnummerService, rekeningnummerService, opmerkingService, bijlageService, _) {

        return {
            opslaan: function (bedrijf, telefoonnummers, taken) {
                var deferred = $.Deferred();

                bedrijf.telefoonnummers = telefoonnummers;
                bedrijf.taken = taken;
                $.each(bedrijf.telefoonnummers(), function (i, telefoonnummer) {
                    telefoonnummer.parentIdentificatie(bedrijf.id());
                    telefoonnummer.soortEntiteit('BEDRIJF');
                    if (telefoonnummer.telefoonnummer() != null && telefoonnummer.telefoonnummer() != '') {
                        telefoonnummer.telefoonnummer(telefoonnummer.telefoonnummer().replace(/ /g, "").replace("-", ""));
                    }
                });

                bedrijfRepository.opslaan(bedrijf).done(function () {
                    if (contactpersonen().length > 0) {
                        _.each(contactpersonen(), function (cp) {
                            delete cp.telefoonnummersModel;
                        });

                        $.each(contactpersonen(), function (i, contactpersoon) {
                            contactpersoon.bedrijf(id);
                            var telefoonnummers = contactpersoon.telefoonnummers;
                            contactpersoon.telefoonnummers = null;
                            repository.voerUitPost(navRegister.bepaalUrl('OPSLAAN_CONTACTPERSOON'), ko.toJSON(contactpersoon)).done(function (cpId) {

                                _.each(telefoonnummers(), function (telefoonnummer) {
                                    telefoonnummer.parentIdentificatie(id);
                                    telefoonnummer.soortEntiteit("CONTACTPERSOON");
                                });


                                telefoonnummerService.opslaan(telefoonnummers, 'CONTACTPERSOON', cpId);
                            });
                        });
                    }

                    return deferred.resolve(id);
                });

                return deferred.promise();
            },

            verwijderRelatie: function (id) {
                return this.voerUitGet(navRegister.bepaalUrl('VERWIJDER_RELATIE'), {id: id});
            },

            leesBedrijf: function (id) {
                var deferred = $.Deferred();

                if (id == null) {
                    return deferred.resolve({});
                } else {
                    $.when(
                        bedrijfRepository.leesBedrijf(id)//,
                    ).then(function (bedrijf) {
                        return deferred.resolve(bedrijf);
                    });
                }

                return deferred.promise();
            },

            lijstBedrijven: function (zoekTerm) {
                var deferred = $.Deferred();

                bedrijfRepository.lijstBedrijven(zoekTerm).done(function (data) {
                    var dataBedrijven = data;

                    var ids = _.map(dataBedrijven, function (bedrijf) {
                        return bedrijf.id;
                    });

                    $.when(repository.voerUitGet(navRegister.bepaalUrl('ALLE_ADRESSEN_BIJ_ENTITEIT') + '?soortEntiteit=BEDRIJF&lijst=' + ids.join('&lijst='))).then(function (lijstAdressen) {
                        $.each(dataBedrijven, function (i, item) {
                            item.adressen = _.filter(lijstAdressen, function (adres) {
                                return adres.entiteitId == item.id;
                            });
                        });

                        return deferred.resolve(dataBedrijven);
                    });
                });

                return deferred.promise();
            }
        }
    }
);