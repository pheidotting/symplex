define(['jquery',
        "knockout",
        'model/bedrijf',
        'commons/block',
        'commons/3rdparty/log',
        'commons/commonFunctions',
        'jqueryUI',
        'dataServices',
        'fileUpload',
        'opmerkingenLoader',
        'adressenLoader',
        'telefoonnummersLoader'],
    function($, ko, Bedrijf, block, log, commonFunctions, jqueryUI, dataServices, fileUpload, opmerkingenLoader, adressenLoader, telefoonnummersLoader) {

    return function(bedrijfId) {
		block.block();
		log.debug("ophalen Relatie met id " + bedrijfId);

		dataServices.leesBedrijf(bedrijfId).done(function(data){
			log.debug("opgehaald : " + JSON.stringify(data));
			ko.validation.registerExtenders();

			var bedrijfKO = new Bedrijf(data);

            new fileUpload.init(bedrijfId).done(function(){
                new opmerkingenLoader(bedrijfId).init().done(function(){
                    new adressenLoader(bedrijfId).init().done(function(){
                        new telefoonnummersLoader(bedrijfId).init().done(function(){
                            log.debug("Do the knockout magic");
                            log.debug(JSON.stringify(bedrijfKO));
                            ko.applyBindings(bedrijfKO);
        			        $.unblockUI();
                        });
                    });
                });
            });
		}).fail(function(data){
			commonFunctions.nietMeerIngelogd(data);
		});
	};
});