define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'commons/3rdparty/log',
        'redirect',
        'model/polis',
        'mapper/pakket-mapper',
        'service/polis-service',
        'viewmodel/common/opmerking-viewmodel',
        'viewmodel/common/bijlage-viewmodel',
        'moment',
        'service/toggle-service',
        'viewmodel/common/menubalk-viewmodel',
        'viewmodel/common/licentie-viewmodel',
        'viewmodel/common/taken-viewmodel',
        'underscore',
        'knockout.validation',
        'knockoutValidationLocal',
        'blockUI'],
    function ($, commonFunctions, ko, log, redirect, Polis, pakketMapper, polisService, opmerkingViewModel, bijlageViewModel, moment, toggleService, menubalkViewmodel, LicentieViewmodel, TakenViewmodel, _) {

        return function () {
            var _this = this;
            var logger = log.getLogger('beheren-polis-viewmodel');
            var soortEntiteit = 'POLIS';

            this.basisEntiteit = null;
            this.basisId = null;
            this.bijlageModel = null;
            this.pakket = null;
            this.taakModel = null;
            this.menubalkViewmodel = null;
            this.licentieViewmodel = null;

            this.lijst = ko.observableArray();
            this.id = ko.observable();
            this.readOnly = ko.observable();
            this.notReadOnly = ko.observable();

            this.init = function (polisId, basisId, readOnly, basisEntiteit) {
                var deferred = $.Deferred();

                _this.basisId = basisId;

                _this.readOnly(readOnly);
                _this.notReadOnly(!readOnly);
                _this.id(polisId.identificatie);
                $.when(polisService.lees(polisId, basisEntiteit), polisService.lijstVerzekeringsmaatschappijen(), polisService.lijstParticulierePolissen(), polisService.lijstZakelijkePolissen()).then(function (entiteit, maatschappijen, lijstParticulierePolissen, lijstZakelijkePolissen) {
                    if (entiteit.naam != null) {
                        _this.basisEntiteit = "BEDRIJF";
                    } else {
                        _this.basisEntiteit = "RELATIE";
                    }
                    _this.basisId = entiteit.identificatie;

                    var pakket = _.find(entiteit.pakketten, function (pakket) {
                        return pakket.identificatie === polisId.identificatie;
                    });
                    if (pakket == null) {
                        pakket = {
                            'opmerkingen': [],
                            'bijlages': [],
                            'groepBijlages': []
                        }
                    }

                    _this.pakket = pakketMapper.mapPakket(pakket, maatschappijen);

                    if(_this.pakket.polissen().length == 0){
                        var p = new Polis();
                        p.identificatie('__new__' + new Date().getTime());
                        _this.pakket.polissen.push(p);
                        $('#'+p.identificatie()).show();
                        $('#'+p.identificatie()+'open').show();
                        $('#'+p.identificatie()+'dicht').hide();
                    }

                    _.each(_this.pakket.polissen(), function(polis){
                        try {
                            zoekVoertuigGegevens(polis);
                        } catch (err) {
                            logger.info(err.message);
                        }
                    });

                    _this.opmerkingenModel = new opmerkingViewModel(false, soortEntiteit, polisId, pakket.opmerkingen);
                    _this.bijlageModel = new bijlageViewModel(false, soortEntiteit, polisId, pakket.bijlages, pakket.groepBijlages, _this.id() == _this.basisId);
                    _this.menubalkViewmodel = new menubalkViewmodel(entiteit.identificatie, _this.basisEntiteit);
                    _this.licentieViewmodel = new LicentieViewmodel();

                    _this.takenViewmodel = new TakenViewmodel(pakket.taken);

                    var $verzekeringsMaatschappijenSelect = $('#verzekeringsMaatschappijen');
                    $.each(maatschappijen, function (key, value) {
                        $('<option>', {value: key}).text(value).appendTo($verzekeringsMaatschappijenSelect);
                    });

                    var lijst = lijstParticulierePolissen;
                    if (_this.basisEntiteit == 'BEDRIJF') {
                        lijst = lijstZakelijkePolissen;
                    }

                    var $soortVerzekeringSelect = $('#soortVerzekering');
                    $('<option>', {value: 0}).text('Kies een soort polis...').appendTo($soortVerzekeringSelect);
                    $.each(lijst, function (key, value) {
                        $('<option>', {value: key}).text(value).appendTo($soortVerzekeringSelect);
                    });

//                    _this.pakket.premie(commonFunctions.maakBedragOp(_this.pakket.premie()));

//                    try {
//                        zoekVoertuigGegevens(_this);
//                    } catch (err) {
//                        logger.info(err.message);
//                    }

                    return deferred.resolve();
                });

                return deferred.promise();
            };

            this.voegPolisToe = function() {
                var p = new Polis();
                p.identificatie('__new__' + new Date().getTime());
                _this.pakket.polissen.push(p);
                $('#'+p.identificatie()).show();
                $('#'+p.identificatie()+'open').show();
                $('#'+p.identificatie()+'dicht').hide();
            };

            this.verwijderPolis = function(polis) {
                console.log('Verwijderen polis met nummer ' + polis.polisNummer());

                _this.pakket.polissen.remove(polis);
            };

            this.style = function() {
                if(_this.pakket.polissen().length == 1){
                    return 'display:block;';
                } else{
                    return 'display:none;';
                }
            };

            this.wijzigKenmerk = function (polis) {
                zoekVoertuigGegevens(polis);
            };

            this.exitIngangsDatum = function (polis) {
                _this.berekenProlongatieDatum(polis);
            };

            this.formatBedrag = function () {
                return opmaak.maakBedragOp(bedrag());
            };

            this.berekenProlongatieDatum = function (polis) {
                if (polis.ingangsDatum() !== null && polis.ingangsDatum() !== undefined && polis.ingangsDatum() !== "") {
                    polis.prolongatieDatum(moment(polis.ingangsDatum(), "YYYY-MM-DD").add(1, 'y').format("YYYY-MM-DD"));
                }
            };

            this.toonOfVerberg = function(a){
                if ($('#'+a.identificatie()).is(':visible')) {
                    $('#'+a.identificatie()).hide();
                    $('#'+a.identificatie()+'open').hide();
                    $('#'+a.identificatie()+'dicht').show();
                }else{
                    $('#'+a.identificatie()).show();
                    $('#'+a.identificatie()+'open').show();
                    $('#'+a.identificatie()+'dicht').hide();
                }
            };

            this.opslaan = function () {
                logger.debug('opslaan');
                var result = ko.validation.group(_this.pakket, {deep: true});
                if (result().length > 0) {
                    result.showAllMessages(true);
                } else {
                    commonFunctions.verbergMeldingen();
                    var allOk = true;

                    _.each(_this.pakket.polissen(), function(polis){
                        polis.premie(commonFunctions.stripBedrag(polis.premie()));
                        if(polis.identificatie().substr(0, 7) == '__new__'){
                            polis.identificatie(null);
                        }
                    });
                    polisService.opslaan(_this.pakket, _this.opmerkingenModel.opmerkingen, _this.basisId, _this.takenViewmodel.taken).done(function () {
                        commonFunctions.plaatsMelding("De gegevens zijn opgeslagen");

                        redirect.redirect('LIJST_POLISSEN', _this.basisId);
                    }).fail(function (data) {
                        allOk = false;
                        commonFunctions.plaatsFoutmelding(data);
                    });
                    if (allOk) {
                        redirect.redirect('LIJST_POLISSEN', _this.basisId);
                    }
                }
            };

            this.annuleren = function () {
                redirect.redirect('BEHEREN_' + _this.basisEntiteit, _this.basisId, 'polissen');
            };

            this.startBewerkenPremie = function (data, b) {
                _this.polis.premie(commonFunctions.stripBedrag(b.currentTarget.value));
            };

            this.stopBewerkenPremie = function () {
                _this.polis.premie(commonFunctions.maakBedragOp(_this.polis.premie()));
            };
        };

        function zoekVoertuigGegevens(polis) {
            var kenmerk = polis.kenmerk();

            if(kenmerk != null) {
                polis.voertuigImage1('');
                polis.voertuigImage2('');
                polis.voertuigImage3('');
                var plate = GetSidecodeLicenseplate(kenmerk);
                if (plate) {
                    var kenteken = FormatLicenseplate(plate);

                    $.get('https://opendata.rdw.nl/resource/m9d7-ebf2.json?kenteken=' + kenteken.replace(/-/g, ''), function (data) {
                        if (data.length > 0) {
                            polis.voertuiginfo(true);
                            polis.merk(data[0].merk);
                            polis.type(data[0].handelsbenaming);
                            polis.bouwjaar(moment(data[0].datum_eerste_toelating, 'DD/MM/YYYY').format('YYYY'));

                            var extra = '';
                            if(data[0].voertuigsoort != 'Personenauto') {
                                extra = ' ' + data[0].voertuigsoort;
                            }

                            $.ajax({
                                type: "GET",
                                url: 'https://www.googleapis.com/customsearch/v1?searchType=image&key=AIzaSyBWSvctDqh02781O1LFHESwMqBB5US82YE&cx=009856326316060057713:4jypauff-sk&q=' + encodeURIComponent(polis.merk() + ' ' + polis.type() + ' ' + polis.bouwjaar() + extra),
                                contentType: "application/json",
                                data: data,
                                ataType: "json",
                                async: false,
                                success: function (dataImages) {
                                    polis.voertuigImage1(dataImages.items[0].link);
                                    polis.voertuigImage2(dataImages.items[1].link);
                                    polis.voertuigImage3(dataImages.items[2].link);
                                }
                            });


                        } else {
                            polis.voertuiginfo(false);
                        }
                    });
                } else {
                    polis.voertuiginfo(false);
                }
            } else {
                polis.voertuiginfo(false);
            }
        }

        function GetSidecodeLicenseplate(Licenseplate) {

            var arrSC = [];

            Licenseplate = Licenseplate.replace(/-/g, '').toUpperCase();

            arrSC[0] = /[a-zA-Z]{2}[\d]{2}[\d]{2}/ // 1 XX-99-99
            arrSC[1] = /[\d]{2}[\d]{2}[a-zA-Z]{2}/ // 2 99-99-XX
            arrSC[2] = /[\d]{2}[a-zA-Z]{2}[\d]{2}/ // 3 99-XX-99
            arrSC[3] = /[a-zA-Z]{2}[\d]{2}[a-zA-Z]{2}/ // 4 XX-99-XX
            arrSC[4] = /[a-zA-Z]{2}[a-zA-Z]{2}[\d]{2}/ // 5 XX-XX-99
            arrSC[5] = /[\d]{2}[a-zA-Z]{2}[a-zA-Z]{2}/ // 6 99-XX-XX
            arrSC[6] = /[\d]{2}[a-zA-Z]{3}[\d]{1}/ // 7 99-XXX-9
            arrSC[7] = /[\d]{1}[a-zA-Z]{3}[\d]{2}/ // 8 9-XXX-99
            arrSC[8] = /[a-zA-Z]{2}[\d]{3}[a-zA-Z]{1}/ // 9 XX-999-X
            arrSC[9] = /[a-zA-Z]{1}[\d]{3}[a-zA-Z]{2}/ // 10 X-999-XX
            arrSC[10] = /[a-zA-Z]{3}[\d]{2}[a-zA-Z]{1}/ // 11 XXX-99-X
            arrSC[11] = /[a-zA-Z]{1}[\d]{2}[a-zA-Z]{3}/ // 12 X-99-XXX
            arrSC[12] = /[a-zA-Z]{1}[\d]{3}[a-zA-Z]{2}/ // 12 X-999-XX
            arrSC[13] = /[\d]{1}[a-zA-Z]{2}[\d]{3}/ // 13 9-XX-999
            arrSC[14] = /[\d]{3}[a-zA-Z]{2}[\d]{1}/ // 14 999-XX-9

            //except licenseplates for diplomats
            var scUitz = '^CD[ABFJNST][0-9]{1,3}$' //for example: CDB1 of CDJ45

            for (let i = 0; i < arrSC.length; i++) {
                if (arrSC[i].test(Licenseplate)) {
                    return {'i': i + 1, 'r': arrSC[i].exec(Licenseplate), 'o': arrSC[i]};
                }
            }
            if (Licenseplate.match(scUitz)) {
                return 'CD';
            }
            return false;
        }

        function FormatLicenseplate(reg) {
            var Sidecode = reg.i;
            var Licenseplate = reg.r[0];

            Licenseplate = Licenseplate.replace(/-/g, '').toUpperCase();

            if (Sidecode <= 6) {
                return Licenseplate.substr(0, 2) + '-' + Licenseplate.substr(2, 2) + '-' + Licenseplate.substr(4, 2)
            }
            if (Sidecode == 7 || Sidecode == 9) {
                return Licenseplate.substr(0, 2) + '-' + Licenseplate.substr(2, 3) + '-' + Licenseplate.substr(5, 1)
            }
            if (Sidecode == 8 || Sidecode == 10) {
                return Licenseplate.substr(0, 1) + '-' + Licenseplate.substr(1, 3) + '-' + Licenseplate.substr(4, 2)
            }
            if (Sidecode == 11 || Sidecode == 14) {
                return Licenseplate.substr(0, 3) + '-' + Licenseplate.substr(3, 2) + '-' + Licenseplate.substr(5, 1)
            }
            if (Sidecode == 12 || Sidecode == 13) {
                return Licenseplate.substr(0, 1) + '-' + Licenseplate.substr(1, 2) + '-' + Licenseplate.substr(3, 3)
            }
            return null
        }


    });