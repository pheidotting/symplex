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
    function(log, navRegister, ko, repository, bedrijfRepository, adresService, telefoonnummerService, rekeningnummerService, opmerkingService, bijlageService, _) {

        return {
            opslaan: function(bedrijf, telefoonnummers) {
                var deferred = $.Deferred();

//                var adressen = bedrijf.adressen;
//                delete bedrijf.adressen;
//                var telefoonnummers = bedrijf.telefoonnummers;
//                delete bedrijf.telefoonnummers;
//                var opmerkingen = bedrijf.opmerkingen;
//                delete bedrijf.opmerkingen;
//                var contactpersonen = bedrijf.contactpersonen;
//                delete bedrijf.contactpersonen;

                bedrijf.telefoonnummers = telefoonnummers;
                $.each(bedrijf.telefoonnummers(), function(i, telefoonnummer){
                    telefoonnummer.parentIdentificatie(bedrijf.id());
                    telefoonnummer.soortEntiteit('BEDRIJF');
                    if(telefoonnummer.telefoonnummer() != null && telefoonnummer.telefoonnummer() != '') {
                        telefoonnummer.telefoonnummer(telefoonnummer.telefoonnummer().replace(/ /g, "").replace("-", ""));
                    }
                });

                    bedrijfRepository.opslaan(bedrijf).done(function(response) {
                            if(contactpersonen().length > 0) {
                                _.each(contactpersonen(), function(cp) {
//                                    cp.telefoonnummers = cp.telefoonnummersModel;
                                    delete cp.telefoonnummersModel;// = undefined;
                                });

                                $.each(contactpersonen(), function(i, contactpersoon){
                                    contactpersoon.bedrijf(id);
                                    var telefoonnummers = contactpersoon.telefoonnummers;
                                    contactpersoon.telefoonnummers = null;
                                    repository.voerUitPost(navRegister.bepaalUrl('OPSLAAN_CONTACTPERSOON'), ko.toJSON(contactpersoon)).done(function(cpId){

                                        _.each(telefoonnummers(), function(telefoonnummer) {
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

            verwijderRelatie: function(id) {
                return this.voerUitGet(navRegister.bepaalUrl('VERWIJDER_RELATIE'), {id : id});
            },

            leesBedrijf: function(id) {
                var deferred = $.Deferred();
                var bedrijf;

                if (id == null) {
                    return deferred.resolve({});
                } else {
                    $.when(
                        bedrijfRepository.leesBedrijf(id)//,
                    ).then(function(bedrijf){
                        return deferred.resolve(bedrijf);
                    });
                }

                return deferred.promise();
            },

            lijstBedrijven: function(zoekTerm) {
                var deferred = $.Deferred();
                var dataRelaties;

                bedrijfRepository.lijstBedrijven(zoekTerm).done(function(data) {
                    dataBedrijven = data;

                    var ids = _.map(dataBedrijven, function(bedrijf){
                        return bedrijf.id;
                    });

                    $.when(repository.voerUitGet(navRegister.bepaalUrl('ALLE_ADRESSEN_BIJ_ENTITEIT') + '?soortEntiteit=BEDRIJF&lijst=' + ids.join('&lijst='))).then(function(lijstAdressen){
                        $.each(dataBedrijven, function(i, item) {
                            item.adressen = _.filter(lijstAdressen, function(adres){
                                return adres.entiteitId == item.id;
                            });
                        });

                        return deferred.resolve(dataBedrijven);
                    });
                });

                function teruggeven(aantalOphalen) {
                    if(aantalOphalen === 0) {
                        return deferred.resolve(dataBedrijven);
                    }
                }

                return deferred.promise();
                }
            }
        }
);