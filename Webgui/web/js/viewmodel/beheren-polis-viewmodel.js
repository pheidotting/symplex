define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'commons/3rdparty/log',
        'redirect',
        'mapper/polis-mapper',
        'service/polis-service',
        'viewmodel/common/opmerking-viewmodel',
        'viewmodel/common/bijlage-viewmodel',
        'moment',
        'service/toggle-service',
        'viewmodel/common/menubalk-viewmodel',
        'viewmodel/common/licentie-viewmodel',
        'underscore',
        'knockout.validation',
        'knockoutValidationLocal',
        'blockUI'],
    function ($, commonFunctions, ko, log, redirect, polisMapper, polisService, opmerkingViewModel, bijlageViewModel, moment, toggleService, menubalkViewmodel, LicentieViewmodel, _) {

        return function () {
            var _this = this;
            var logger = log.getLogger('beheren-polis-viewmodel');
            var soortEntiteit = 'POLIS';

            this.basisEntiteit = null;
            this.basisId = null;
            this.bijlageModel = null;
            this.polis = null;
            this.taakModel = null;
            this.menubalkViewmodel = null;
            this.licentieViewmodel = null;

            this.lijst = ko.observableArray();
            this.id = ko.observable();
            this.readOnly = ko.observable();
            this.notReadOnly = ko.observable();

            this.voertuiginfo = ko.observable(false);
            this.merk = ko.observable();
            this.type = ko.observable();
            this.bouwjaar = ko.observable();
            this.voertuigImage1 = ko.observable();
            this.voertuigImage2 = ko.observable();
            this.voertuigImage3 = ko.observable();

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

                    var polis = _.find(entiteit.polissen, function (polis) {
                        return polis.identificatie === polisId.identificatie;
                    });
                    if (polis == null) {
                        polis = {
                            'opmerkingen': [],
                            'bijlages': [],
                            'groepBijlages': []
                        }
                    }

                    _this.polis = polisMapper.mapPolis(polis, maatschappijen);

                    _this.opmerkingenModel = new opmerkingViewModel(false, soortEntiteit, polisId, polis.opmerkingen);
                    _this.bijlageModel = new bijlageViewModel(false, soortEntiteit, polisId, polis.bijlages, polis.groepBijlages, _this.id() == _this.basisId);
                    _this.menubalkViewmodel = new menubalkViewmodel(entiteit.identificatie, _this.basisEntiteit);
                    _this.licentieViewmodel = new LicentieViewmodel();

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
                        $('<option>', {value: value}).text(value).appendTo($soortVerzekeringSelect);
                    });

                    _this.polis.premie(commonFunctions.maakBedragOp(_this.polis.premie()));

                    try {
                        zoekVoertuigGegevens(_this);
                    } catch (err) {
                        logger.info(err.message);
                    }

                    return deferred.resolve();
                });

                return deferred.promise();
            };

            this.wijzigKenmerk = function () {
                zoekVoertuigGegevens(_this);
            };

            this.exitIngangsDatum = function () {
                _this.berekenProlongatieDatum();
            };

            this.formatBedrag = function () {
                return opmaak.maakBedragOp(bedrag());
            };

            this.berekenProlongatieDatum = function () {
                if (_this.polis.ingangsDatum() !== null && _this.polis.ingangsDatum() !== undefined && _this.polis.ingangsDatum() !== "") {
                    _this.polis.prolongatieDatum(moment(_this.polis.ingangsDatum(), "YYYY-MM-DD").add(1, 'y').format("YYYY-MM-DD"));
                }
            };

            this.opslaan = function () {
                logger.debug('opslaan');
                var result = ko.validation.group(_this.polis, {deep: true});
                if (result().length > 0) {
                    result.showAllMessages(true);
                } else {
                    commonFunctions.verbergMeldingen();
                    var allOk = true;

                    _this.polis.premie(commonFunctions.stripBedrag(_this.polis.premie()));
                    polisService.opslaan(_this.polis, _this.opmerkingenModel.opmerkingen, _this.basisId).done(function () {
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

        function zoekVoertuigGegevens(_this) {
            //proberen het kenteken op te zoeken in het geval van een autoverzekering
            if (_this.polis.soort() === 'Auto' || _this.polis.soort() === 'Motor' || _this.polis.soort() === 'MotorRijtuigen' || _this.polis.soort() === 'BromSnorfiets') {
                var kenmerk = _this.polis.kenmerk();

                if(kenmerk != null) {
                    _this.voertuigImage1('');
                    _this.voertuigImage2('');
                    _this.voertuigImage3('');
                    var plate = GetSidecodeLicenseplate(kenmerk);
                    if (plate) {
                        var kenteken = FormatLicenseplate(plate);

                        $.get('https://opendata.rdw.nl/resource/m9d7-ebf2.json?kenteken=' + kenteken.replace(/-/g, ''), function (data) {
                            if (data.length > 0) {
                                _this.voertuiginfo(true);
                                _this.merk(data[0].merk);
                                _this.type(data[0].handelsbenaming);
                                _this.bouwjaar(moment(data[0].datum_eerste_toelating, 'DD/MM/YYYY').format('YYYY'));

                                $.ajax({
                                    type: "GET",
                                    url: 'https://www.googleapis.com/customsearch/v1?searchType=image&key=AIzaSyBWSvctDqh02781O1LFHESwMqBB5US82YE&cx=009856326316060057713:4jypauff-sk&q=' + encodeURIComponent(_this.merk() + ' ' + _this.type() + ' ' + _this.bouwjaar()),
                                    contentType: "application/json",
                                    data: data,
                                    ataType: "json",
                                    async: false,
                                    success: function (dataImages) {
                                        _this.voertuigImage1(dataImages.items[0].link);
                                        _this.voertuigImage2(dataImages.items[1].link);
                                        _this.voertuigImage3(dataImages.items[2].link);
                                    }
                                });


                            } else {
                                _this.voertuiginfo(false);
                            }
                        });
                    } else {
                        _this.voertuiginfo(false);
                    }
                } else {
                    _this.voertuiginfo(false);
                }
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
                    return {'i': i + 1, 'r': arrSC[i].exec(Licenseplate)};
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