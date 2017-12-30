define(["commons/3rdparty/log2",
        "navRegister",
        'knockout',
        'repository/common/repository',
        'repository/gebruiker-repository',
        'service/common/adres-service',
        'service/common/telefoonnummer-service',
        'service/common/rekeningnummer-service',
        'service/common/opmerking-service',
        'service/common/bijlage-service'],
    function(log, navRegister, ko, repository, gebruikerRepository, adresService, telefoonnummerService, rekeningnummerService, opmerkingService, bijlageService) {
        var logger = log.getLogger('gebruiker-service');

        return {
            opslaan: function(relatie, adressen, telefoonnummers, rekeningnummers, opmerkingen) {
                var deferred = $.Deferred();

                relatie.adressen = adressen;
                relatie.telefoonnummers = telefoonnummers;
                $.each(relatie.telefoonnummers(), function(i, telefoonnummer){
                    telefoonnummer.parentIdentificatie(relatie.id());
                    telefoonnummer.soortEntiteit('RELATIE');
                    if(telefoonnummer.telefoonnummer() != null && telefoonnummer.telefoonnummer() != '') {
                        telefoonnummer.telefoonnummer(telefoonnummer.telefoonnummer().replace(/ /g, "").replace("-", ""));
                    }
                });
                relatie.rekeningNummers = rekeningnummers;
                $.each(relatie.rekeningNummers(), function(i, rekeningnummer){
                    rekeningnummer.parentIdentificatie(relatie.id());
                    rekeningnummer.soortEntiteit('RELATIE');
                    if(rekeningnummer.rekeningnummer()!=null && rekeningnummer.rekeningnummer()!= ''){
                        rekeningnummer.rekeningnummer(rekeningnummer.rekeningnummer().replace(/ /g, ""));
                    }
                });
                relatie.opmerkingen = opmerkingen;

                repository.leesTrackAndTraceId().done(function(trackAndTraceId) {
                    gebruikerRepository.opslaan(relatie, trackAndTraceId).done(function(response) {
                        return deferred.resolve(response);
                    });
                });

                return deferred.promise();
            },

            leesRelatie: function(id) {
                logger.debug('ophalen relatie met id ' + id);

                var deferred = $.Deferred();

                $.when(gebruikerRepository.leesRelatie(id)).then(function(relatie) {
                    return deferred.resolve(relatie);
                });

                return deferred.promise();
            },

            leesMedewerker: function(id) {
                logger.debug('ophalen medewerker met id ' + id);

                var deferred = $.Deferred();

                $.when(gebruikerRepository.leesMedewerker(id)).then(function(medewerker) {
                    return deferred.resolve(medewerker);
                });

                return deferred.promise();
            },

            verwijderRelatie: function(id) {
                var deferred = $.Deferred();

                $.when(repository.leesTrackAndTraceId()).then(function(trackAndTraceId) {
                    gebruikerRepository.verwijderRelatie(id, trackAndTraceId);

                    return deferred.resolve();
                });

                return deferred.promise();
            },

            opslaanOAuthCode: function(code) {
                var deferred = $.Deferred();

                $.when(repository.leesTrackAndTraceId()).then(function(trackAndTraceId) {
                    $.when(gebruikerRepository.opslaanOAuthCode(code, trackAndTraceId)).then(function(){
                        return deferred.resolve();
                    });
                });

                return deferred.promise();
            },

            inloggen: function(data){
                var deferred = $.Deferred();

                $.when(repository.voerUitPost(navRegister.bepaalUrl('INLOGGEN'), ko.toJSON(data), '')).always(function(result){
                    return deferred.resolve(result);
                });

                return deferred.promise();
            },

            wijzigWachtwoord: function(nieuwWachtwoord) {
                return gebruikerRepository.wijzigWachtwoord(nieuwWachtwoord);
            },

            leesOAuthCode: function() {
                return gebruikerRepository.leesOAuthCode();
            },

            haalIngelogdeGebruiker: function(){
                logger.debug("Haal ingelogde gebruiker");
                var deferred = $.Deferred();

                var base64Url = localStorage.getItem('symplexAccessToken').split('.')[1];
                var base64 = base64Url.replace('-', '+').replace('_', '/');
                var token = JSON.parse(window.atob(base64));

                gebruikerRepository.haalIngelogdeGebruiker(token.sub).done(function(response){
                    if(response.kantoor != null){
                        logger.debug("Ingelogde gebruiker : " + response.gebruikersnaam + ", (" + response.kantoor + ")");
//                        $('#ingelogdeGebruiker').html("Ingelogd als : " + response.gebruikersnaam + ", (" + response.kantoor + ")");
                    }else{
                        logger.debug("Ingelogde gebruiker : " + response.gebruikersnaam);
//                        $('#ingelogdeGebruiker').html("Ingelogd als : " + response.gebruikersnaam);
                    }
//                    $('#uitloggen').show();
//                    $('#homeKnop').show();

                    return deferred.resolve(response);
//                }).fail(function(response){
//                    logger.debug("Niet ingelogd, naar de inlogpagina");
//                    $('#ingelogdeGebruiker').html("");
//                    $('#uitloggen').hide();
//                    $('#homeKnop').hide();
//                    location.href = 'inloggen.html';
//
//                    return deferred.resolve();
                });

                return deferred.promise();
            },

            stuurNieuwWachtwoord: function(identificatie) {
                gebruikerRepository.stuurNieuwWachtwoord(identificatie);
            }
        }
    }
);