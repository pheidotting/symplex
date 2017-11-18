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

                repository.leesTrackAndTraceId().done(function(trackAndTraceId) {
                    gebruikerRepository.opslaan(relatie, trackAndTraceId).done(function(response) {
                        var id = response;
                        logger.debug(id);
                        var soortEntiteit = 'RELATIE';

                        $.when(adresService.opslaan(adressen, trackAndTraceId, soortEntiteit, id),
                            telefoonnummerService.opslaan(telefoonnummers, trackAndTraceId, soortEntiteit, id),
                            rekeningnummerService.opslaan(rekeningnummers, trackAndTraceId, soortEntiteit, id),
                            opmerkingService.opslaan(opmerkingen, trackAndTraceId, soortEntiteit, id))
                        .then(function(adresResponse, telefoonnummerResponse, rekeningnummerResponse, opmerkingResponse) {
                            return deferred.resolve(id);
                        });
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

            lijstRelaties: function(zoekTerm, weglaten) {
                logger.debug('ophalen lijst relaties met zoekTerm '+ zoekTerm);
                var deferred = $.Deferred();
                var relatieRelaties;

                gebruikerRepository.lijstRelaties(zoekTerm, weglaten).done(function(relatie) {
                    relatieRelaties = relatie;

                    var ids = _.map(relatie.jsonRelaties, function(relatie){
                        return relatie.id;
                    });

                    $.when(repository.voerUitGet(navRegister.bepaalUrl('ALLE_ADRESSEN_BIJ_ENTITEIT') + '?soortEntiteit=RELATIE&lijst=' + ids.join('&lijst='))).then(function(lijstAdressen){
                        $.each(relatie.jsonRelaties, function(i, item) {
                            item.adressen = _.filter(lijstAdressen, function(adres){
                                return adres.entiteitId == item.id;
                            });
                        });

                        return deferred.resolve(relatieRelaties);
                    });
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
            }
        }
    }
);