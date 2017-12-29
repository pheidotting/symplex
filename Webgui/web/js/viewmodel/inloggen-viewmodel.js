define(['jquery',
        'knockout',
        'commons/commonFunctions',
        'commons/3rdparty/log2',
		'redirect',
        'service/gebruiker-service',
       'complexify',
        'knockout.validation',
        'knockoutValidationLocal',
        'blockUI'],
    function($, ko, commonFunctions, log, redirect, gebruikerService, complexify) {

    return function() {
        var _this = this;
        var logger = log.getLogger('inloggen-viewmodel');
        ko.validation.locale('nl-NL');
        this.identificatie = ko.observable().extend({ required: true });
		this.wachtwoord = ko.observable().extend({ required: true });
		this.onthouden = ko.observable(false);

		this.onjuisteGebruikersnaam = ko.observable(false);
		this.onjuistWachtwoord = ko.observable(false);

		this.moetWachtwoordUpdaten = ko.observable(false);
		this.nieuwWachtwoord = ko.observable();
		this.nieuwWachtwoordNogmaals = ko.observable();
		this.sterktePercentage = ko.observable('0');
		this.wachtwoordenKomenNietOvereen = ko.observable(false);
		this.sterkgenoeg = ko.observable(false);
		this.wachtwoordSterkgenoegStyling = ko.observable();

		this.inloggen = function() {
            commonFunctions.verbergMeldingen();

            var result = ko.validation.group(_this, {deep: true});
            if(_this.identificatie.isValid() && _this.wachtwoord.isValid()){
                $.blockUI({message: '<span class="fa fa-circle-o-notch fa-spin fa-3x fa-fw"></span>' });

                $.when(gebruikerService.inloggen(this)).then(function(result){
                    if (result.returnCode == 0) {
                        _this.onjuisteGebruikersnaam(false);
                        _this.onjuistWachtwoord(false);
                        if(!!result.moetWachtwoordUpdaten){
                            $.unblockUI();
                            _this.moetWachtwoordUpdaten(true);
                        }else{
    //                        commonFunctions.haalIngelogdeGebruiker();
    //                        $.unblockUI();
                            window.location = 'zoeken.html';
                        }
                    } else if (result.returnCode == 1) {
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

        this.checkWachtwoordSterkte = function(a) {
            $("#nieuwWachtwoord").complexify({}, function(valid, complexity){
                _this.sterktePercentage(Math.round(complexity));
                $("#PassValue").val(complexity);
                if(complexity > 32) {
                    _this.sterkgenoeg(true);
                    _this.wachtwoordSterkgenoegStyling(false);
                }else{
                    _this.wachtwoordSterkgenoegStyling('onjuiste-waarde');
                }
            });
        };

        this.wijzigWachtwoord = function() {
            //minimum treshold = 32%

          if(_this.sterkgenoeg() && (_this.nieuwWachtwoord() == _this.nieuwWachtwoordNogmaals())) {
                _this.wachtwoordenKomenNietOvereen(false);
                $.when(gebruikerService.wijzigWachtwoord(_this.nieuwWachtwoord())).then(function(result){
                    window.location = 'zoeken.html';
                });
            } else {
                _this.wachtwoordenKomenNietOvereen(true);
            }
        };

        this.resetfoutmeldingGebruikersnaam = function(a) {
            _this.onjuisteGebruikersnaam(false);
        };

        this.resetfoutmeldingWachtwoord = function() {
            _this.onjuistWachtwoord(false);
        };
	};
});