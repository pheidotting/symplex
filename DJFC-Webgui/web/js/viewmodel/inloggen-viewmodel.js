define(['jquery',
        'knockout',
        'commons/commonFunctions',
        'commons/3rdparty/log2',
		'redirect',
        'service/gebruiker-service',
        'knockout.validation',
        'knockoutValidationLocal',
        'blockUI'],
    function($, ko, commonFunctions, log, redirect, gebruikerService, redirect) {

    return function() {
        var _this = this;
        var logger = log.getLogger('inloggen-viewmodel');
        ko.validation.locale('nl-NL');
        this.identificatie = ko.observable().extend({ required: true });
		this.wachtwoord = ko.observable().extend({ required: true });
		this.onthouden = ko.observable(false);

		this.onjuisteGebruikersnaam = ko.observable(false);
		this.onjuistWachtwoord = ko.observable(false);

		this.inloggen = function() {
            commonFunctions.verbergMeldingen();

            var result = ko.validation.group(_this, {deep: true});
            if(_this.identificatie.isValid() && _this.wachtwoord.isValid()){
                $.blockUI({message: '<span class="fa fa-circle-o-notch fa-spin fa-3x fa-fw"></span>' });

                $.when(gebruikerService.inloggen(this)).then(function(result){
                    if (result == 0) {
                        _this.onjuisteGebruikersnaam(false);
                        _this.onjuistWachtwoord(false);
//                        commonFunctions.haalIngelogdeGebruiker();
//                        $.unblockUI();
                        window.location = 'zoeken.html';
                    } else if (result == 1) {
                        $.unblockUI();
                        _this.onjuisteGebruikersnaam('onjuiste-waarde');
                        _this.onjuistWachtwoord(false);
                        commonFunctions.plaatsFoutmeldingString('De ingevoerde gebruikersnaam werd niet gevonden');
                    } else {
                        $.unblockUI();
                        _this.onjuisteGebruikersnaam(false);
                        _this.onjuistWachtwoord('onjuiste-waarde');
                        commonFunctions.plaatsFoutmeldingString('Het ingevoerde wachtwoord is onjuist');
                    }
                });
            } else {
                result.showAllMessages(true);
            }
        };

        this.resetfoutmeldingGebruikersnaam = function(a) {
            logger.debug('resetfoutmeldingGebruikersnaam');
            logger.debug(a);
            logger.debug('# resetfoutmeldingGebruikersnaam');
            _this.onjuisteGebruikersnaam(false);
        };

        this.resetfoutmeldingWachtwoord = function() {
            _this.onjuistWachtwoord(false);
        };
	};
});