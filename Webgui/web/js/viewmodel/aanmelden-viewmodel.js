define(['jquery',
        'knockout',
        'commons/commonFunctions',
        'commons/3rdparty/log',
        'redirect',
        'service/kantoor-service',
        'knockout.validation',
        'knockoutValidationLocal',
        'blockUI'],
    function ($, ko, commonFunctions, log, redirect, kantoorService) {

        return function () {
            var _this = this;
            var logger = log.getLogger('aanmelden-viewmodel');

            ko.validation.locale('nl-NL');
            this.bedrijfsnaam = ko.observable().extend({required: true});
            this.afkorting = ko.observable().extend({required: true});
            this.inlognaam = ko.observable();
            this.voornaam = ko.observable().extend({required: true});
            this.achternaam = ko.observable().extend({required: true});
            this.emailadres = ko.observable().extend({required: true});

            this.afkortingKomtVoor = ko.observable(false);

            this.genereerAfkorting = function () {
                _this.bedrijfsnaam(_this.bedrijfsnaam().trim());
                var woorden = _this.bedrijfsnaam().split(' ');

                if (woorden.length > 1) {
                    var afkorting = '';
                    for (var i = 0; i < woorden.length; i++) {
                        afkorting += woorden[i].substring(0, 1).toLowerCase();
                    }

                    _this.afkorting(afkorting);
                } else {
                    _this.afkorting(_this.bedrijfsnaam().toLowerCase());
                }

                _this.komtAfkortingVoor(_this);
            };

            this.komtAfkortingVoor = function (afkorting) {
                if (afkorting.afkorting() == null || afkorting.afkorting() == '') {
                    _this.genereerAfkorting();
                    _this.komtAfkortingVoor(_this);
                } else {
                    $.get('dejonge/rest/authorisatie/komtAfkortingAlVoor/' + afkorting.afkorting()).done(function (result) {
                        _this.afkortingKomtVoor(result);
                        _this.genereerInlognaam();

                        if (result) {
                            _this.afkorting(_this.afkorting() + 1);
                            _this.komtAfkortingVoor(afkorting);
                        }
                    });
                }
            };

            this.genereerInlognaam = function () {
                if (_this.voornaam() != null && !_this.afkortingKomtVoor()) {
                    _this.inlognaam(_this.afkorting() + '.' + _this.voornaam().trim().toLowerCase())
                }
            };

            this.aanmelden = function () {
                logger.info('aanmelden nieuw kantoor');
                commonFunctions.verbergMeldingen();

                var result = ko.validation.group(_this, {deep: true});
                if (result().length > 0) {
                    result.showAllMessages(true);
                } else {
                    kantoorService.aanmelden(this).done(function () {
                        window.location = 'inloggen.html';
                    });
                }
            };
        };
    });