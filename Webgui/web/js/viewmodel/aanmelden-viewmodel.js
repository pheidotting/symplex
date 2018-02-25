define(['jquery',
        'knockout',
        'commons/commonFunctions',
        'commons/3rdparty/log',
		'redirect',
        'service/kantoor-service',
       'complexify',
        'knockout.validation',
        'knockoutValidationLocal',
        'blockUI'],
    function($, ko, commonFunctions, log, redirect, kantoorService, complexify) {

    return function() {
        var _this = this;
        var logger = log.getLogger('aanmelden-viewmodel');

        ko.validation.locale('nl-NL');
        this.bedrijfsnaam = ko.observable().extend({ required: true });
        this.afkorting = ko.observable().extend({ required: true });
        this.inlognaam = ko.observable();
        this.voornaam = ko.observable().extend({ required: true });
        this.achternaam = ko.observable().extend({ required: true });
        this.emailadres = ko.observable().extend({ required: true });
		this.onjuistWachtwoord = ko.observable(false);
		this.nieuwWachtwoord = ko.observable();
		this.nieuwWachtwoordNogmaals = ko.observable();
		this.sterktePercentage = ko.observable('0');
		this.sterkgenoeg = ko.observable(false);
		this.wachtwoordSterkgenoegStyling = ko.observable();

		this.afkortingKomtVoor = ko.observable(false);

        this.genereerAfkorting = function(){
            _this.bedrijfsnaam(_this.bedrijfsnaam().trim());
            var woorden = _this.bedrijfsnaam().split(' ');

            if(woorden.length > 1) {
                var afkorting = '';
                for(i = 0; i < woorden.length; i++){
                    afkorting += woorden[i].substring(0, 1).toLowerCase();
                }

                _this.afkorting(afkorting);
            } else {
                _this.afkorting(_this.bedrijfsnaam().toLowerCase());
            }
            _this.komtAfkortingVoor(_this);
        };

        this.komtAfkortingVoor = function(afkorting){
            $.get('dejonge/rest/authorisatie/komtAfkortingAlVoor/'+afkorting.afkorting()).done(function(result){
                _this.afkortingKomtVoor(result);
                _this.genereerInlognaam();
            });
        };

        this.genereerInlognaam = function() {
            if(_this.voornaam() != null && !_this.afkortingKomtVoor()) {
                _this.inlognaam(_this.afkorting() + '.' + _this.voornaam().trim().toLowerCase())
            }
        };

		this.aanmelden = function() {
		    logger.info('aanmelden nieuw kantoor');
            commonFunctions.verbergMeldingen();

            var result = ko.validation.group(_this, {deep: true});
//            if(_this.isValid()){
                $.blockUI({message: '<span class="fa fa-circle-o-notch fa-spin fa-3x fa-fw"></span>' });

                $.when(kantoorService.aanmelden(this)).then(function(result){
                    window.location = 'zoeken.html';
//                    if (result.returnCode == 0) {
//                        _this.onjuisteGebruikersnaam(false);
//                        _this.onjuistWachtwoord(false);
//                        if(!!result.moetWachtwoordUpdaten){
//                            $.unblockUI();
//                            _this.moetWachtwoordUpdaten(true);
//                        }else{
//    //                        commonFunctions.haalIngelogdeGebruiker();
//    //                        $.unblockUI();
//                            window.location = 'zoeken.html';
//                        }
//                    } else if (result.returnCode == 1) {
//                        $.unblockUI();
//                        _this.onjuisteGebruikersnaam('onjuiste-waarde');
//                        _this.onjuistWachtwoord(false);
//                        _this.teveelFoutieveInlogpogingen(false);
//                        commonFunctions.plaatsFoutmeldingString('De ingevoerde gebruikersnaam werd niet gevonden');
//                    } else if (result.returnCode == 2) {
//                        $.unblockUI();
//                        _this.onjuisteGebruikersnaam(false);
//                        _this.onjuistWachtwoord('onjuiste-waarde');
//                        _this.teveelFoutieveInlogpogingen(false);
//                        commonFunctions.plaatsFoutmeldingString('Het ingevoerde wachtwoord is onjuist');
//                    } else {
//                        $.unblockUI();
//                        _this.onjuisteGebruikersnaam(false);
//                        _this.onjuistWachtwoord(false);
//                        _this.teveelFoutieveInlogpogingen('teveel');
//                        commonFunctions.plaatsFoutmeldingString('Teveel foutieve inlogpogingen binnen 5 minuten');
//                    }
                });
//            } else {
//                result.showAllMessages(true);
//            }
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

        this.resetfoutmeldingWachtwoord = function() {
            _this.onjuistWachtwoord(false);
        };
	};
});