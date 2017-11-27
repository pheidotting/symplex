define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'redirect'],
    function(log, navRegister, ko, redirect) {

        return {
            voerUitGet: function(url, data){
                var deferred = $.Deferred();

                $.get(url, data).done(function(response) {
                    return deferred.resolve(response);
                }).fail(function(response){
//                    if (response.status === 401) {
//                        redirect.redirect('INLOGGEN');
//                    }
                    return deferred.reject();
                });

                return deferred.promise();
            },

            voerUitPost: function(url, data, trackAndTraceId){
                var deferred = $.Deferred();

                $.ajax({
                    type: "POST",
                    url: url,
                    contentType: "application/json",
                    data: data,
                    beforeSend: function(xhr){xhr.setRequestHeader('trackAndTraceId', trackAndTraceId);},
                    success: function (response) {
                        return deferred.resolve(response);
                    },
                    error: function (data) {
                        return deferred.reject(data);
                    }
                });

                return deferred.promise();
            },

            inloggen: function(data){
                var deferred = $.Deferred();
                var _this = this;

                _this.voerUitPost(navRegister.bepaalUrl('INLOGGEN'), data, '').always(function(result){
                    return deferred.resolve(result);
                });

                return deferred.promise();
            },

            haalIngelogdeGebruiker: function(){
                return this.voerUitGet(navRegister.bepaalUrl('INGELOGDE_GEBRUIKER'));
            },

            uitloggen: function(){
                return this.voerUitGet(navRegister.bepaalUrl('UITLOGGEN'));
            },

            verwijderRelatie: function(id){
                return this.voerUitGet(navRegister.bepaalUrl('VERWIJDER_RELATIE'), {id : id});
            },

            koppelOnderlingeRelatie: function(relatie, relatieMet, soortRelatie){
                var data = {};
                data.relatie = relatie;
                data.relatieMet = relatieMet;
                data.soortRelatie = soortRelatie;

                return this.voerUitPost(navRegister.bepaalUrl('KOPPELEN_ONDERLINGE_RELATIE'), JSON.stringify(data));
            },

            opslaanAdresBijRelatie: function(adres){
                var data = JSON.stringify(adres);
                log.debug(data);
                return this.voerUitPost(navRegister.bepaalUrl('OPSLAAN_ADRES_BIJ_RELATIE'), data);
            },

            lijstBedrijven: function(zoekTerm){
                var deferred = $.Deferred();
                var _this = this;
                var aantal = 0;
                var dataRelaties;

                this.voerUitGet(navRegister.bepaalUrl('LIJST_BEDRIJVEN_BIJ_RELATIE'), {zoekTerm : zoekTerm}).done(function(data){
                    aantal = data.length;
                    dataBedrijven = data;
                    $.each(data, function(i, item) {
                        _this.voerUitGet(navRegister.bepaalUrl('LIJST_ADRESSEN') + '/BEDRIJF/' + item.id).done(function(adressen){
                            item.adressen = adressen;
                            teruggeven(--aantal);
                        });
                    });
                });

                function teruggeven(aantalOphalen){
                    if(aantalOphalen === 0) {
                        return deferred.resolve(dataBedrijven);
                    }
                }

                return deferred.promise();
            },

//            opslaanBedrijf: function(data){
//                log.debug(ko.toJSON(data));
//                var deferred = $.Deferred();
//                var _this = this;
//
//                $.when(this.voerUitGet(navRegister.bepaalUrl('TRACKANDTRACEID'))).then(function(trackAndTraceId){
//                    _this.voerUitPost(navRegister.bepaalUrl('OPSLAAN_BEDRIJF'), ko.toJSON(data), trackAndTraceId).done(function(responseBedrijf){
//                        var result = responseBedrijf.entity;
//                        if(data.adressen.length > 0) {
//                            $.each(data.adressen, function(i, adres){
//                                adres.entiteitId(result);
//                            });
//                            _this.voerUitPost(navRegister.bepaalUrl('OPSLAAN_ADRESSEN'), ko.toJSON(data.adressen), trackAndTraceId);
//                        } else {
//                            _this.voerUitPost(navRegister.bepaalUrl('VERWIJDER_ADRESSEN') + '/BEDRIJF/' + result,null, trackAndTraceId);
//                        }
//                        if(data.telefoonnummers.length > 0) {
//                            $.each(data.telefoonnummers, function(i, telefoonnummer){
//                                telefoonnummer.entiteitId(result);
//                            });
//                            _this.voerUitPost(navRegister.bepaalUrl('OPSLAAN_TELEFOONNUMMERS'), ko.toJSON(data.telefoonnummers), trackAndTraceId);
//                        } else {
//                            _this.voerUitPost(navRegister.bepaalUrl('VERWIJDER_TELEFOONNUMMERS') + '/BEDRIJF/' + result, null, trackAndTraceId);
//                        }
//
//                        if(data.contactpersonen().length > 0) {
//                            $.each(data.contactpersonen(), function(i, contactpersoon){
//                                contactpersoon.bedrijf(result);
//                                var telefoonnummers = contactpersoon.telefoonnummers;
//                                contactpersoon.telefoonnummers = null;
//                                _this.voerUitPost(navRegister.bepaalUrl('OPSLAAN_CONTACTPERSOON'), ko.toJSON(contactpersoon), trackAndTraceId).done(function(cpId){
//                                    if(telefoonnummers().length > 0) {
//                                        $.each(telefoonnummers(), function(i, item){
//                                            item.entiteitId(cpId);
//                                            item.soortEntiteit('CONTACTPERSOON');
//                                        });
//                                        _this.voerUitPost(navRegister.bepaalUrl('OPSLAAN_TELEFOONNUMMERS'), ko.toJSON(telefoonnummers()), trackAndTraceId);
//                                    } else {
//                                        _this.voerUitPost(navRegister.bepaalUrl('VERWIJDER_TELEFOONNUMMERS') + '/CONTACTPERSOON/' + result, null, trackAndTraceId);
//                                    }
//                                });
//                            });
//                        } else {
//                        }
//
//                        return deferred.resolve(result);
//                    });
//                });
//
//                return deferred.promise();
//            },
//
//            leesBedrijf: function(id){
//                var deferred = $.Deferred();
//                var _this = this;
//                var bedrijf;
//                var aantalOphalen = 5;
//
//                this.voerUitGet(navRegister.bepaalUrl('LEES_BEDRIJF'), {id : id}).done(function(data){
//                    bedrijf = data;
//                    //ophalen bijlages
//                    _this.lijstBijlages('BEDRIJF', id).done(function(bijlages){
//                        bedrijf.bijlages = bijlages;
//                        if(--aantalOphalen === 0) {
//                            teruggeven();
//                        }
//                    });
//
//                    //ophalen opmerkingen
//                    _this.lijstOpmerkingen('BEDRIJF', id).done(function(opmerkingen){
//                        bedrijf.opmerkingen = opmerkingen;
//                        if(--aantalOphalen === 0) {
//                            teruggeven();
//                        }
//                    });
//
//                    //ophalen adresssen
//                    _this.voerUitGet(navRegister.bepaalUrl('LIJST_ADRESSEN') + '/BEDRIJF/' + id).done(function(adressen){
//                        bedrijf.adressen = adressen;
//                        if(--aantalOphalen === 0) {
//                            teruggeven();
//                        }
//                    });
//
//                    //ophalen telefoonnummers
//                    _this.voerUitGet(navRegister.bepaalUrl('LIJST_TELEFOONNUMMERS') + '/BEDRIJF/' + id).done(function(telefoonnummers){
//                        bedrijf.telefoonnummers = telefoonnummers;
//                        if(--aantalOphalen === 0) {
//                            teruggeven();
//                        }
//                    });
//
//                    //ophalen contactpersonen
//                    _this.voerUitGet(navRegister.bepaalUrl('LIJST_CONTACTPERSONEN'), {"bedrijfsId" : id}).done(function(contactpersonen){
//                        aantalOphalen = aantalOphalen + contactpersonen.length - 1;
//                        bedrijf.contactpersonen = [];
//                        $.each(contactpersonen, function(index, contactpersoon){
//                            _this.voerUitGet(navRegister.bepaalUrl('LIJST_TELEFOONNUMMERS') + '/CONTACTPERSOON/' + contactpersoon.id).done(function(telefoonnummers){
//                                contactpersoon.telefoonnummers = telefoonnummers;
//                                bedrijf.contactpersonen.push(contactpersoon);
//                                if(--aantalOphalen === 0) {
//                                    teruggeven();
//                                }
//                            });
//                        });
//                    });
//                });
//
//                function teruggeven(){
//                    return deferred.resolve(bedrijf);
//                }
//
//                return deferred.promise();
//            },

//            lijstVerzekeringsmaatschappijen: function(){
//                return this.voerUitGet(navRegister.bepaalUrl('LIJST_VERZEKERINGSMAATSCHAPPIJEN'));
//            },
//
//            lijstParticulierePolissen: function(){
//                return this.voerUitGet(navRegister.bepaalUrl('LIJST_PARTICULIEREPOLISSEN'));
//            },

            lijstZakelijkePolissen: function(){
                return this.voerUitGet(navRegister.bepaalUrl('LIJST_ZAKELIJKEPOLISSEN'));
            },

//            leesPolis: function(polisId){
//                var deferred = $.Deferred();
//                var _this = this;
//
//                $.when(_this.voerUitGet(navRegister.bepaalUrl('LEES_POLIS'), {"id" : polisId}),
//                    _this.lijstBijlages('POLIS', polisId),
//                    _this.lijstOpmerkingen('POLIS', polisId),
//                    _this.voerUitGet(navRegister.bepaalUrl('LIJST_GROEP_BIJLAGES') + '/POLIS/' + polisId)).then(function(data, bijlages, opmerkingen, groepenBijlages) {
//                        data.bijlages = bijlages;
//                        data.opmerkingen = opmerkingen;
//                        data.groepenBijlages = groepenBijlages;
//
//                        return deferred.resolve(data);
//                });
//
//                return deferred.promise();
//            },
//
//            lijstPolissen: function(relatieId){
//                return this.voerUitGet(navRegister.bepaalUrl('LIJST_POLISSEN'), {relatieId : relatieId});
//            },

            lijstPolissenBijBedrijf: function(bedrijfId){
                return this.voerUitGet(navRegister.bepaalUrl('LIJST_POLISSEN_BIJ_BEDRIJF'), {bedrijfId : bedrijfId});
            },

            verwijderBijlage: function(id){
                return this.voerUitGet(navRegister.bepaalUrl('VERWIJDER_BIJLAGE'), {"id" : id});
            },

//            lijstBijlages: function(soortentiteit, parentid){
//                return this.voerUitGet(navRegister.bepaalUrl('LIJST_BIJLAGES'), {"soortentiteit" : soortentiteit, "parentid" : parentid});
//            },

            wijzigOmschrijvingBijlage: function(id, nieuweOmschrijving){
                var data = {};
                data.bijlageId = id;
                data.nieuweOmschrijving = nieuweOmschrijving;

                return this.voerUitPost(navRegister.bepaalUrl('WIJZIG_OMSCHRIJVING_BIJLAGE'), JSON.stringify(data));
            },

            beindigPolis: function(id){
                return this.voerUitGet(navRegister.bepaalUrl('BEEINDIG_POLIS'), {"id" : id});
            },

            opslaanPolis: function(data){
                var _this = this;
                var deferred = $.Deferred();

                $.when(this.voerUitGet(navRegister.bepaalUrl('TRACKANDTRACEID'))).then(function(trackAndTraceId){
                    _this.voerUitPost(navRegister.bepaalUrl('OPSLAAN_POLIS'), data, trackAndTraceId).done(function(response){
                        return deferred.resolve(response);
                    });
                });

                return deferred.promise();
            },

            verwijderPolis: function(id){
                var _this = this;
                var deferred = $.Deferred();

                $.when(this.voerUitGet(navRegister.bepaalUrl('TRACKANDTRACEID'))).then(function(trackAndTraceId){
                    _this.voerUitPost(navRegister.bepaalUrl('VERWIJDER_POLIS') + '/' + id, null, trackAndTraceId).done(function(response){
                        return deferred.resolve(response);
                    });
                });

                return deferred.promise();
            },

            opslaanSchade: function(data){
                var _this = this;
                var deferred = $.Deferred();

                $.when(this.voerUitGet(navRegister.bepaalUrl('TRACKANDTRACEID'))).then(function(trackAndTraceId){
                    _this.voerUitPost(navRegister.bepaalUrl('OPSLAAN_SCHADE'), data, trackAndTraceId).done(function(response){
                        return deferred.resolve(response);
                    });
                });

                return deferred.promise();
            },

            verwijderSchade: function(id){
                var _this = this;
                var deferred = $.Deferred();

                $.when(this.voerUitGet(navRegister.bepaalUrl('TRACKANDTRACEID'))).then(function(trackAndTraceId){
                    _this.voerUitPost(navRegister.bepaalUrl('VERWIJDER_SCHADE') + '/' + id, null, trackAndTraceId).done(function(response){
                        return deferred.resolve(response);
                    });
                });

                return deferred.promise();
            },

            lijstStatusSchade: function(){
                return this.voerUitGet(navRegister.bepaalUrl('LIJST_STATUS_SCHADE'), null);
            },

//            leesSchade: function(id){
//                return this.voerUitGet(navRegister.bepaalUrl('LEES_SCHADE'), {"id" : id});
//            },
//
//            lijstSchades: function(relatieId){
//                return this.voerUitGet(navRegister.bepaalUrl('LIJST_SCHADES'), {relatieId : relatieId});
//            },

            lijstSchadesBijBedrijf: function(bedrijfId){
                return this.voerUitGet(navRegister.bepaalUrl('LIJST_SCHADES_BIJ_BEDRIJF'), {bedrijfId : bedrijfId});
            },

//            lijstSoortenHypotheek: function(id){
//                return this.voerUitGet(navRegister.bepaalUrl('LIJST_SOORTEN_HYPOTHEEK'), {"id" : id});
//            },
//
//            lijstHypothekenInclDePakketten: function(relatieId){
//                return this.voerUitGet(navRegister.bepaalUrl('LIJST_HYPOTHEKEN_INCL_PAKKETTEN'), {relatieId : relatieId});
//            },
//
//            leesHypotheek: function(id){
//                var deferred = $.Deferred();
//                var _this = this;
//
//                this.voerUitGet(navRegister.bepaalUrl('LEES_HYPOTHEEK'), {"id" : id}).done(function(data){
//                    _this.lijstBijlages('HYPOTHEEK', id).done(function(bijlages){
//                        data.bijlages = bijlages;
//                        _this.lijstOpmerkingen('HYPOTHEEK', id).done(function(opmerkingen){
//                            data.opmerkingen = opmerkingen;
//
//                            return deferred.resolve(data);
//                        });
//                    });
//                });
//
//                return deferred.promise();
//            },
//
//            lijstHypotheken: function(relatieId){
//                return this.voerUitGet(navRegister.bepaalUrl('LIJST_HYPOTHEKEN'), {relatieId : relatieId});
//            },
//
//            lijstHypotheekPakketten: function(relatieId){
//                return this.voerUitGet(navRegister.bepaalUrl('LIJST_HYPOTHEEKPAKKETTEN'), {relatieId : relatieId});
//            },
//
//            opslaanHypotheek: function(data){
//                var _this = this;
//                var deferred = $.Deferred();
//
//                $.when(this.voerUitGet(navRegister.bepaalUrl('TRACKANDTRACEID'))).then(function(trackAndTraceId){
//                    _this.voerUitPost(navRegister.bepaalUrl('OPSLAAN_HYPOTHEEK'), data, trackAndTraceId).done(function(response){
//                        return deferred.resolve(response);
//                    });
//                });
//
//                return deferred.promise();
//            },
//
//            verwijderHypotheek: function(id){
//                var _this = this;
//                var deferred = $.Deferred();
//
//                $.when(this.voerUitGet(navRegister.bepaalUrl('TRACKANDTRACEID'))).then(function(trackAndTraceId){
//                    _this.voerUitPost(navRegister.bepaalUrl('VERWIJDER_HYPOTHEEK') + '/' + id, null, trackAndTraceId).done(function(response){
//                        return deferred.resolve(response);
//                    });
//                });
//
//                return deferred.promise();
//            },

            lijstOpenAangiftes: function(relatie){
                var _this = this;
                var deferred = $.Deferred();

                var aantalOphalen = 1;
                var aangifteData = [];

                this.voerUitGet(navRegister.bepaalUrl('LIJST_OPEN_AANGIFTES'), {relatie : relatie}).done(function(data){
                    aantalOphalen = aantalOphalen + data.length - 1;

                    $.each(data, function(index, aangifte){
                        _this.lijstBijlages('AANGIFTES', jaarCijfers.id).done(function(bijlages){
                            aangifte.bijlages = bijlages;
                            _this.lijstOpmerkingen('AANGIFTES', jaarCijfers.id).done(function(opmerkingen){
                                aangifte.opmerkingen = opmerkingen;
                                aangifteData.push(aangifte);

                                if(--aantalOphalen === 0) {
                                    teruggeven();
                                }
                            });
                        });
                    });
                });

                function teruggeven(){
                    return deferred.resolve(aangifteData);
                }

                return deferred.promise();
            },

            lijstGeslotenAangiftes: function(relatie){
                return this.voerUitGet(navRegister.bepaalUrl('LIJST_GESLOTEN_AANGIFTES'), {relatie : relatie});
            },

            afrondenAangifte: function(id){
                var _this = this;
                var deferred = $.Deferred();

                $.when(this.voerUitGet(navRegister.bepaalUrl('TRACKANDTRACEID'))).then(function(trackAndTraceId){
                    _this.voerUitPost(navRegister.bepaalUrl('AFRONDEN_AANGIFTE') + '/' + id, null, trackAndTraceId).done(function(response){
                        return deferred.resolve(response);
                    });
                });

                return deferred.promise();
            },

            opslaanAangifte: function(data){
                var _this = this;
                var deferred = $.Deferred();

                $.when(this.voerUitGet(navRegister.bepaalUrl('TRACKANDTRACEID'))).then(function(trackAndTraceId){
                    _this.voerUitPost(navRegister.bepaalUrl('OPSLAAN_AANGIFTE'), data, trackAndTraceId).done(function(response){
                        return deferred.resolve(response);
                    });
                });

                return deferred.promise();
            },

            leesTaak: function(id){
                return this.voerUitGet(navRegister.bepaalUrl('LEES_TAAK'), {"id" : id});
            },

            lijstTaken: function(){
                return this.voerUitGet(navRegister.bepaalUrl('LIJST_TAKEN'));
            },

//            openTakenBijRelatie: function(relatieId){
//                return this.voerUitGet(navRegister.bepaalUrl('OPEN_TAKEN_BIJ_RELATIE'), {"relatieId" : relatieId});
//            },

            afhandelenTaak: function(data){
                return this.voerUitPost(navRegister.bepaalUrl('AFHANDELEN_TAAK'), data);
            },

            oppakkenTaak: function(id){
                return this.voerUitGet(navRegister.bepaalUrl('OPPAKKEN_TAAK'), {"id" : id});
            },

            vrijgevenTaak: function(id){
                return this.voerUitGet(navRegister.bepaalUrl('VRIJGEVEN_TAAK'), {"id" : id});
            },

            opslaanOpmerking: function(opmerking, trackAndTraceId){
                return this.voerUitPost(navRegister.bepaalUrl('OPSLAAN_OPMERKING'), opmerking, trackAndTraceId);
            },

            verwijderOpmerking: function(id){
                var _this = this;
                var deferred = $.Deferred();

                $.when(_this.voerUitGet(navRegister.bepaalUrl('TRACKANDTRACEID'))).then(function(trackAndTraceId){
                    _this.voerUitPost(navRegister.bepaalUrl('VERWIJDER_OPMERKING') + '/' + id, null, trackAndTraceId);
                    return deferred.resolve();
                });

                return deferred.promise();
            },

            lijstOpmerkingen: function(soortentiteit, parentid){
                return this.voerUitGet(navRegister.bepaalUrl('LIJST_OPMERKINGEN') + '/' + soortentiteit + '/' + parentid);
            },

            lijstBijlages: function(soortentiteit, parentid){
                return this.voerUitGet(navRegister.bepaalUrl('LIJST_BIJLAGES') + '/' + soortentiteit + '/' + parentid);
            },

//            ophalenAdresOpPostcode: function(postcode, huisnummer){
//                return this.voerUitGet(navRegister.bepaalUrl('OPHALEN_ADRES_OP_POSTCODE'), {"postcode" : postcode, "huisnummer" : huisnummer});
//            },

            ophalenJaarCijfers: function(bedrijfsId){
                var deferred = $.Deferred();
                var _this = this;
                var aantalOphalen = 1;
                var jaarCijfersData = [];

                this.voerUitGet(navRegister.bepaalUrl('JAARCIJFERS_LIJST'), {'bedrijfsId' : bedrijfsId}).done(function(data){
                    aantalOphalen = aantalOphalen + data.length - 1;

                    $.each(data, function(index, jaarCijfers){
                        _this.lijstBijlages('JAARCIJFERS', jaarCijfers.id).done(function(bijlages){
                            jaarCijfers.bijlages = bijlages;
                            _this.lijstOpmerkingen('JAARCIJFERS', jaarCijfers.id).done(function(opmerkingen){
                                jaarCijfers.opmerkingen = opmerkingen;
                                jaarCijfersData.push(jaarCijfers);

                                if(--aantalOphalen === 0) {
                                    teruggeven();
                                }
                            });
                        });
                    });
                });

                function teruggeven(){
                    return deferred.resolve(jaarCijfersData);
                }

                return deferred.promise();
            },

            ophalenRisicoAnalyse: function(bedrijfsId){
                var deferred = $.Deferred();
                var _this = this;

                this.voerUitGet(navRegister.bepaalUrl('RISICOANALYSE_LEES'), {"bedrijfsId" : bedrijfsId}).done(function(data){
                    _this.lijstBijlages('RISICOANALYSE', bedrijfsId).done(function(bijlages){
                        data.bijlages = bijlages;
                        _this.lijstOpmerkingen('RISICOANALYSE', bedrijfsId).done(function(opmerkingen){
                            data.opmerkingen = opmerkingen;

                            return deferred.resolve(data);
                        });
                    });
                });

                return deferred.promise();
            },

            lijstCommunicatieProducten: function(entiteitId, soortEntiteit){
                var deferred = $.Deferred();
                var _this = this;

                this.voerUitGet(navRegister.bepaalUrl('LIJST_COMMUNICATIEPRODUCTEN') + '/' + soortEntiteit + '/' + entiteitId).done(function(data){
                    //ophalen bijlages
                    var togo = data.length;
                    var nr = 1;
                    var result = [];
            		$.each(data, function(i, item) {
                        _this.lijstBijlages('COMMUNICATIEPRODUCT', item.id).done(function(bijlages){
                            item.bijlages = bijlages;

                            result.push(item);

                            if(nr++ === togo) {
                               return deferred.resolve(result);
                            }
                        });
                    });
                });

                return deferred.promise();
            },

            markeerAlsGelezen: function(id) {
                this.voerUitPost(navRegister.bepaalUrl('MARKEER_ALS_GELEZEN') + '/' + id);
            },

            opslaanCommunciatieProduct: function(communicatieproduct) {
                var deferred = $.Deferred();

                var cm = {};
                cm.id = communicatieproduct.id();
                cm.soortCommunicatieProduct = communicatieproduct.type();
                cm.soortentiteit = communicatieproduct.soortEntiteit();
                cm.parentid = communicatieproduct.entiteitId();
                cm.onderwerp = communicatieproduct.onderwerp();
                cm.tekst = communicatieproduct.tekst();

                if (cm.soortCommunicatieProduct === '') {
                    cm.soortCommunicatieProduct = undefined;
                }

                log.debug(JSON.stringify(cm));

                this.voerUitPost(navRegister.bepaalUrl('OPSLAAN_COMMUNICATIEPRODUCT'), JSON.stringify(cm)).done(function(data){
                   return deferred.resolve(data);
                });

                return deferred.promise();
            },

            verzendenCommunciatieProduct: function(id) {
                var deferred = $.Deferred();

                this.voerUitPost(navRegister.bepaalUrl('VERSTUREN_COMMUNICATIEPRODUCT') + '/' + id).done(function(){
                   return deferred.resolve();
                });

                return deferred.promise();
            },

            leesCommunicatieProduct: function(id) {
                var deferred = $.Deferred();
                var _this = this;

                _this.voerUitGet(navRegister.bepaalUrl('LEES_COMMUNICATIEPRODUCT') + '/' + id).done(function(data){
                    _this.lijstBijlages('COMMUNICATIEPRODUCT', data.id).done(function(bijlages){
                        data.bijlages = bijlages;
                       return deferred.resolve(data);
                    });
                });

                return deferred.promise();
            }
        }
    }
);