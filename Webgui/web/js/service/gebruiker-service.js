define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository',
        'repository/gebruiker-repository'],
    function (log, navRegister, ko, repository, gebruikerRepository) {
        var logger = log.getLogger('gebruiker-service');

        return {
            opslaan: function (relatie, adressen, telefoonnummers, rekeningnummers, opmerkingen) {
                var deferred = $.Deferred();

                relatie.adressen = adressen;
                relatie.telefoonnummers = telefoonnummers;
                $.each(relatie.telefoonnummers(), function (i, telefoonnummer) {
                    telefoonnummer.parentIdentificatie(relatie.id());
                    telefoonnummer.soortEntiteit('RELATIE');
                    if (telefoonnummer.telefoonnummer() != null && telefoonnummer.telefoonnummer() != '') {
                        telefoonnummer.telefoonnummer(telefoonnummer.telefoonnummer().replace(/ /g, "").replace("-", ""));
                    }
                });
                relatie.rekeningNummers = rekeningnummers;
                $.each(relatie.rekeningNummers(), function (i, rekeningnummer) {
                    rekeningnummer.parentIdentificatie(relatie.id());
                    rekeningnummer.soortEntiteit('RELATIE');
                    if (rekeningnummer.rekeningnummer() != null && rekeningnummer.rekeningnummer() != '') {
                        rekeningnummer.rekeningnummer(rekeningnummer.rekeningnummer().replace(/ /g, ""));
                    }
                });
                relatie.opmerkingen = opmerkingen;

                gebruikerRepository.opslaan(relatie).done(function (response) {
                    return deferred.resolve(response);
                });

                return deferred.promise();
            },

            leesRelatie: function (id) {
                logger.debug('ophalen relatie met id ' + id);

                var deferred = $.Deferred();

                $.when(gebruikerRepository.leesRelatie(id)).then(function (relatie) {
                    return deferred.resolve(relatie);
                });

                return deferred.promise();
            },

            leesMedewerker: function (id) {
                logger.debug('ophalen medewerker met id ' + id);

                var deferred = $.Deferred();

                $.when(gebruikerRepository.leesMedewerker(id)).then(function (medewerker) {
                    return deferred.resolve(medewerker);
                });

                return deferred.promise();
            },

            opslaanMedewerker: function (medewerker) {
                return gebruikerRepository.opslaanMedewerker(medewerker);
            },

            verwijderRelatie: function (id) {
                gebruikerRepository.verwijderRelatie(id);
            },

            opslaanOAuthCode: function (code) {
                var deferred = $.Deferred();

                $.when(gebruikerRepository.opslaanOAuthCode(code)).then(function () {
                    return deferred.resolve();
                });

                return deferred.promise();
            },

            inloggen: function (data) {
                var deferred = $.Deferred();

                $.when(repository.voerUitPost(navRegister.bepaalUrl('INLOGGEN'), ko.toJSON(data), '')).always(function (result) {
                    return deferred.resolve(result);
                });

                return deferred.promise();
            },

            wijzigWachtwoord: function (nieuwWachtwoord) {
                return gebruikerRepository.wijzigWachtwoord(nieuwWachtwoord);
            },

            leesOAuthCode: function () {
                return gebruikerRepository.leesOAuthCode();
            },

            haalIngelogdeGebruiker: function () {
                logger.debug("Haal ingelogde gebruiker");
                var deferred = $.Deferred();

                if (localStorage.getItem('symplexAccessToken') != null) {
                    var base64Url = localStorage.getItem('symplexAccessToken').split('.')[1];
                    var base64 = base64Url.replace('-', '+').replace('_', '/');
                    var token = JSON.parse(window.atob(base64));

                    gebruikerRepository.haalIngelogdeGebruiker(token.sub).done(function (response) {
                        if (response.kantoor != null) {
                            logger.debug("Ingelogde gebruiker : " + response.gebruikersnaam + ", (" + response.kantoor + ")");
                        } else {
                            logger.debug("Ingelogde gebruiker : " + response.gebruikersnaam);
                        }

                        return deferred.resolve(response);
                    });
                } else {
                    return deferred.resolve(null);
                }

                return deferred.promise();
            },

            stuurNieuwWachtwoord: function (identificatie) {
                gebruikerRepository.stuurNieuwWachtwoord(identificatie);
            }
        }
    }
);