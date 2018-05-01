define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'model/relatie',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log',
        'redirect',
        'mapper/gebruiker-mapper',
        'service/gebruiker-service',
        'service/relatie-service',
        'service/toggle-service',
        'viewmodel/common/adres-viewmodel',
        'viewmodel/common/rekeningnummer-viewmodel',
        'viewmodel/common/telefoonnummer-viewmodel',
        'viewmodel/common/opmerking-viewmodel',
        'viewmodel/common/bijlage-viewmodel',
        'viewmodel/common/telefonie-viewmodel',
        'viewmodel/common/menubalk-viewmodel',
        'viewmodel/common/licentie-viewmodel',
        'knockout.validation',
        'knockoutValidationLocal',
        'blockUI'],
    function ($, commonFunctions, ko, Relatie, functions, block, log, redirect, relatieMapper, gebruikerService, relatieService, toggleService, adresViewModel, rekeningnummerViewModel, telefoonnummerViewModel, opmerkingViewModel, bijlageViewModel, telefonieViewModel, menubalkViewmodel, LicentieViewmodel) {

        return function () {
            var _this = this;
            var logger = log.getLogger('beheren-relatie-viewmodel');
            var soortEntiteit = 'RELATIE';

            this.adressenModel = null;
            this.rekeningnummerModel = null;
            this.telefoonnummersModel = null;
            this.opmerkingenModel = null;
            this.bijlageModel = null;
            this.taakModel = null;
            this.relatie = null;
            this.telefonie = null;
            this.menubalkViewmodel = null;
            this.licentieViewmodel = null;

            this.onderlingeRelaties = ko.observableArray();
            this.lijst = ko.observableArray();

            this.veranderDatum = function (datum) {
                datum(commonFunctions.zetDatumOm(datum()));
            };

            this.init = function (id) {
                _this.identificatie = id.identificatie;
                var deferred = $.Deferred();

                $.when(relatieService.lees(id.identificatie)).then(function (relatie) {
                    _this.relatie = relatieMapper.mapRelatie(relatie);

                    _this.telefoonnummersModel = new telefoonnummerViewModel(false, soortEntiteit, id, relatie.telefoonnummers);
                    _this.telefonie = new telefonieViewModel(relatie.telefoonnummerMetGesprekkens);
                    _this.rekeningnummerModel = new rekeningnummerViewModel(false, soortEntiteit, id, relatie.rekeningNummers);
                    _this.adressenModel = new adresViewModel(false, soortEntiteit, id, relatie.adressen);
                    _this.opmerkingenModel = new opmerkingViewModel(false, soortEntiteit, id, relatie.opmerkingen);
                    _this.bijlageModel = new bijlageViewModel(false, soortEntiteit, id, relatie.bijlages, relatie.groepBijlages, _this.identificatie == null);

                    _this.menubalkViewmodel = new menubalkViewmodel(_this.identificatie, 'Relatie');
                    _this.licentieViewmodel = new LicentieViewmodel();

                    return deferred.resolve();
                });

                return deferred.promise();
            };


            this.schermTonen = ko.computed(function () {
                return true;
            });
            this.readOnly = ko.observable(false);
            this.notReadOnly = ko.observable(true);

            this.veranderDatum = function (datum) {
                if (datum != null) {
                    datum(commonFunctions.zetDatumOm(datum()));
                }
            };

            this.zetPostcodeOm = function () {
                var postcode = _this.postcode();
                if (postcode !== null && postcode.length === 6) {
                    postcode = postcode.toUpperCase();
                    postcode = postcode.substring(0, 4) + " " + postcode.substring(4);
                    _this.postcode(postcode);
                }
            };

            this.naarTaak = function (taak) {
                redirect.redirect('TAAK', taak.id());
            };

            this.opslaan = function () {
                var result = ko.validation.group(_this, {deep: true});
                if (result().length > 0) {
                    result.showAllMessages(true);
                } else {
                    var foutmelding;
                    gebruikerService.opslaan(_this.relatie, _this.adressenModel.adressen, _this.telefoonnummersModel.telefoonnummers, _this.rekeningnummerModel.rekeningnummers, _this.opmerkingenModel.opmerkingen).done(function () {
                        document.location.href = 'zoeken.html';
                        commonFunctions.plaatsMelding("De gegevens zijn opgeslagen");
                    }).fail(function (response) {
                        commonFunctions.plaatsFoutmelding(response);
                        foutmelding = true;
                    });
                    if (foutmelding === undefined || foutmelding === null) {
                        document.location.href = 'zoeken.html';
                        commonFunctions.plaatsMelding("De gegevens zijn opgeslagen");
                    }
                }
            };

            this.verwijderenRelatie = function () {
                logger.debug("verwijderen Relatie met id " + _this.relatie.id());

                gebruikerService.verwijderRelatie(_this.identificatie).done(function () {

                    redirect.redirect('LIJST_RELATIES');
                });
            };

            this.annuleren = function () {
                redirect.redirect('LIJST_RELATIES');
            };

            this.naarPolissen = function () {
                redirect.redirect('LIJST_POLISSEN', _this.identificatie);
            }
        };
    });