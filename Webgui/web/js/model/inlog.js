define(['jquery',
         'knockout',
         'commons/commonFunctions',
         'commons/block',
         'dataServices',
         'redirect',
         'commons/3rdparty/log',
         'knockoutValidation'],
	function ($, ko, commonFunctions, block, dataServices, redirect, log) {
	var logger = log.getLogger('inlog');

	return function(){
		var inlogModel = ko.validatedObservable({
            identificatie : ko.observable().extend({ required: true }),
			wachtwoord : ko.observable().extend({ required: true }),
			onthouden : ko.observable('false'),

			inloggen : function(){
			    logger.debug('inloggen');
				commonFunctions.verbergMeldingen();
				if(this.isValid()){
					block.block();

                    $.when(dataServices.inloggen(ko.toJSON(this))).then(function(result){
					    if (result == 0) {
                            commonFunctions.haalIngelogdeGebruiker();
                            $.unblockUI();
                            redirect.redirect('DASHBOARD');
                        } else if (result == 1) {
    						commonFunctions.plaatsFoutmeldingString('De ingevoerde gebruikersnaam werd niet gevonden');
                            $.unblockUI();
                        } else {
    						commonFunctions.plaatsFoutmeldingString('Het ingevoerde wachtwoord is onjuist');
                            $.unblockUI();
                        }
					});
				}
			}
		});

		return inlogModel;
    };
});