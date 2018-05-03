define(['jquery',
        'knockout',
        'commons/commonFunctions',
        'commons/3rdparty/log',
        'redirect',
        'service/gebruiker-service',
        'service/toggle-service',
        'complexify',
        'knockout.validation',
        'knockoutValidationLocal',
        'blockUI'],
    function ($, ko, commonFunctions, log, redirect, gebruikerService, toggleService, complexify) {

        return function () {
            var _this = this;
            var logger = log.getLogger('inloggen-viewmodel');

            ko.validation.locale('nl-NL');
            this.identificatie = ko.observable().extend({required: true});
            this.wachtwoord = ko.observable().extend({required: true});
            this.onthouden = ko.observable(false);

            this.onjuisteGebruikersnaam = ko.observable(false);
            this.onjuistWachtwoord = ko.observable(false);
            this.teveelFoutieveInlogpogingen = ko.observable(false);

            this.moetWachtwoordUpdaten = ko.observable(false);
            this.nieuwWachtwoord = ko.observable();
            this.nieuwWachtwoordNogmaals = ko.observable();
            this.sterktePercentage = ko.observable('0');
            this.wachtwoordenKomenNietOvereen = ko.observable(false);
            this.sterkgenoeg = ko.observable(false);
            this.wachtwoordSterkgenoegStyling = ko.observable();

            this.isWachtwoordVergeten = ko.observable(false);

            this.inloggen = function () {
                logger.info('poging tot inloggen');
                commonFunctions.verbergMeldingen();

                var result = ko.validation.group(_this, {deep: true});
                if (_this.identificatie.isValid() && _this.wachtwoord.isValid()) {
                    $.blockUI({message: '<span class="fa fa-circle-o-notch fa-spin fa-3x fa-fw"></span>'});

                    $.when(gebruikerService.inloggen(this), toggleService.isFeatureBeschikbaar('DASHBOARD')).then(function (result, dashboardToggle) {
                        if (result.returnCode == 0) {
                            _this.onjuisteGebruikersnaam(false);
                            _this.onjuistWachtwoord(false);
                            if (!!result.moetWachtwoordUpdaten) {
                                $.unblockUI();
                                _this.moetWachtwoordUpdaten(true);
                            } else {
                                if (localStorage.getItem("symplexPreviousLocation") == null) {
                                    if (dashboardToggle) {
                                        window.location = 'dashboard.html';
                                    } else {
                                        window.location = 'zoeken.html';
                                    }
                                } else {
                                    if (dashboardToggle) {
                                        window.location = localStorage.getItem("symplexPreviousLocation");
                                    } else {
                                        var previousLoc = localStorage.getItem("symplexPreviousLocation");
                                        if (previousLoc.indexOf("dashboard") > 0) {
                                            window.location = 'zoeken.html';
                                        }
                                    }
                                }
                            }
                        } else if (result.returnCode == 1) {
                            $.unblockUI();
                            _this.onjuisteGebruikersnaam('onjuiste-waarde');
                            _this.onjuistWachtwoord(false);
                            _this.teveelFoutieveInlogpogingen(false);
                            commonFunctions.plaatsFoutmeldingString('De ingevoerde gebruikersnaam werd niet gevonden');
                        } else if (result.returnCode == 2) {
                            $.unblockUI();
                            _this.onjuisteGebruikersnaam(false);
                            _this.onjuistWachtwoord('onjuiste-waarde');
                            _this.teveelFoutieveInlogpogingen(false);
                            commonFunctions.plaatsFoutmeldingString('Het ingevoerde wachtwoord is onjuist');
                        } else {
                            $.unblockUI();
                            _this.onjuisteGebruikersnaam(false);
                            _this.onjuistWachtwoord(false);
                            _this.teveelFoutieveInlogpogingen('teveel');
                            commonFunctions.plaatsFoutmeldingString('Teveel foutieve inlogpogingen binnen 5 minuten');
                        }
                    });
                } else {
                    result.showAllMessages(true);
                }
            };

            this.checkWachtwoordSterkte = function () {
                $("#nieuwWachtwoord").complexify({}, function (valid, complexity) {
                    _this.sterktePercentage(Math.round(complexity));
                    $("#PassValue").val(complexity);
                    if (complexity > 32) {
                        _this.sterkgenoeg(true);
                        _this.wachtwoordSterkgenoegStyling(false);
                    } else {
                        _this.wachtwoordSterkgenoegStyling('onjuiste-waarde');
                    }
                });
            };

            this.wijzigWachtwoord = function () {
                logger.info('wachtwoord wijzigen');
                //minimum treshold = 32%

                if (_this.sterkgenoeg() && (_this.nieuwWachtwoord() == _this.nieuwWachtwoordNogmaals())) {
                    _this.wachtwoordenKomenNietOvereen(false);
                    $.when(gebruikerService.wijzigWachtwoord(_this.nieuwWachtwoord())).then(function () {
                        logger.info('wachtwoord gewijzigd');
                        window.location = 'dashboard.html';
                    });
                    window.location = 'dashboard.html';
                } else {
                    _this.wachtwoordenKomenNietOvereen(true);
                }
            };

            this.resetfoutmeldingGebruikersnaam = function () {
                _this.onjuisteGebruikersnaam(false);
            };

            this.resetfoutmeldingWachtwoord = function () {
                _this.onjuistWachtwoord(false);
            };

            this.wachtwoordVergeten = function () {
                _this.isWachtwoordVergeten(true);
            };

            this.stuurNieuwWachtwoord = function () {
                gebruikerService.stuurNieuwWachtwoord(_this.identificatie());
                _this.identificatie('');
                location.reload();
            };

            this.wilInloggen = function () {
                return !_this.isWachtwoordVergeten() && !_this.moetWachtwoordUpdaten();
            };
        };
    });