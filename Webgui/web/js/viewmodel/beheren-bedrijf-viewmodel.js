define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'model/relatie',
        'model/contactpersoon',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log',
        'redirect',
        'mapper/bedrijf-mapper',
        'mapper/contactpersoon-mapper',
        'service/bedrijf-service',
        'viewmodel/common/adres-viewmodel',
        'viewmodel/common/rekeningnummer-viewmodel',
        'viewmodel/common/telefoonnummer-viewmodel',
        'viewmodel/common/opmerking-viewmodel',
        'viewmodel/common/bijlage-viewmodel',
        'viewmodel/common/telefonie-viewmodel',
        'viewmodel/common/taken-viewmodel',
        'service/toggle-service',
        'viewmodel/common/menubalk-viewmodel',
        'viewmodel/common/licentie-viewmodel',
        'viewmodel/common/breadcrumbs-viewmodel',
        'knockout.validation',
        'knockoutValidationLocal'],
    function ($, commonFunctions, ko, Relatie, Contactpersoon, functions, block, log, redirect, bedrijfMapper, contactpersoonMapper, bedrijfService, adresViewModel,
              rekeningnummerViewModel, telefoonnummerViewModel, opmerkingViewModel, bijlageViewModel, telefonieViewModel, TakenViewmodel, toggleService, menubalkViewmodel, LicentieViewmodel, BreadcrumbsViewmodel) {

        return function () {
            var _this = this;
            var soortEntiteit = 'BEDRIJF';

            this.adressenModel = null;
            this.rekeningnummerModel = null;
            this.telefoonnummersModel = null;
            this.opmerkingenModel = null;
            this.bijlageModel = null;
            this.telefonie = null;
            this.bedrijf = null;
            this.conactpersonen = null;
            this.licentieViewmodel = null;
            this.breadcrumbsViewmodel = null;

            this.onderlingeRelaties = ko.observableArray();
            this.lijst = ko.observableArray();

            this.veranderDatum = function (datum) {
                datum(commonFunctions.zetDatumOm(datum()));
                zo
            };

            this.init = function (id) {
                var deferred = $.Deferred();

                $.when(bedrijfService.leesBedrijf(id.identificatie)).then(function (bedrijf) {
                    _this.bedrijf = bedrijfMapper.mapBedrijf(bedrijf);

                    _this.telefoonnummersModel = new telefoonnummerViewModel(false, soortEntiteit, id, bedrijf.telefoonnummers);
                    _this.adressenModel = new adresViewModel(false, soortEntiteit, id, bedrijf.adressen);
                    _this.opmerkingenModel = new opmerkingViewModel(false, soortEntiteit, id, bedrijf.opmerkingen);
                    _this.bijlageModel = new bijlageViewModel(false, soortEntiteit, id, bedrijf.bijlages, bedrijf.groepBijlages, id.identificatie == null);
                    _this.telefonie = new telefonieViewModel(bedrijf.telefoonnummerMetGesprekkens);

                    _this.contactpersonen = contactpersoonMapper.mapContactpersonen(bedrijf.contactPersoons);

                    _this.menubalkViewmodel = new menubalkViewmodel(id.identificatie, 'Bedrijf');
                    _this.licentieViewmodel = new LicentieViewmodel();

                    _this.takenViewmodel = new TakenViewmodel(bedrijf.taken);

                    _this.breadcrumbsViewmodel = new BreadcrumbsViewmodel(bedrijf, null, null, false, false, null, false, false);

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
                _this.bedrijf.adressen = _this.adressenModel.adressen;
                _this.bedrijf.telefoonnummers = _this.telefoonnummersModel.telefoonnummers;
                _this.bedrijf.opmerkingen = _this.opmerkingenModel.opmerkingen;
                _this.bedrijf.contactpersonen = _this.contactpersonen;

                var result = ko.validation.group(_this.bedrijf, {deep: true});
                if (result().length > 0) {
                    result.showAllMessages(true);
                } else {
                    var foutmelding;
                    bedrijfService.opslaan(_this.bedrijf, _this.telefoonnummersModel.telefoonnummers, _this.takenViewmodel.taken).done(function () {
                        document.location.href = 'dashboard.html';
                        commonFunctions.plaatsMelding("De gegevens zijn opgeslagen");
                    }).fail(function (response) {
                        commonFunctions.plaatsFoutmelding(response);
                        foutmelding = true;
                    });
                    if (foutmelding === undefined || foutmelding === null) {
                        document.location.href = 'dashboard.html';
                        commonFunctions.plaatsMelding("De gegevens zijn opgeslagen");
                    }
                }
            };

            this.verwijderenBedrijf = function (bedrijf) {
                dataServices.verwijderRelatie(ko.utils.unwrapObservable(bedrijf.id));
                redirect.redirect('LIJST_BEDRIJVEN');
            };

            this.annuleren = function () {
                redirect.redirect('LIJST_BEDRIJVEN');
            };

            this.voegContactpersoonToe = function () {
                _this.contactpersonen().push(new Contactpersoon(''));
                _this.contactpersonen.valueHasMutated();
            };

            this.verwijderContactpersoon = function (contactpersoon) {
                _this.contactpersonen.remove(function (item) {
                    return item.voornaam() === contactpersoon.voornaam() && item.achternaam() === contactpersoon.achternaam();
                });
                _this.contactpersonen.valueHasMutated();
            };
        };
    });