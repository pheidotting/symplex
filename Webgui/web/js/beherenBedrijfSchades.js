define(['jquery',
        "knockout",
        'model/schades',
        'commons/block',
        'commons/3rdparty/log',
        'commons/commonFunctions',
        'dataServices'],
    function($, ko, Schades, block, log, commonFunctions, dataServices) {

    return function(bedrijfId) {
		log.debug("Ophalen schades bij Bedrijf met id " + bedrijfId);
		block.block();

		dataServices.lijstSchadesBijBedrijf(bedrijfId).done(function(data){
            log.debug("opgehaald : " + JSON.stringify(data));
            ko.validation.registerExtenders();

            ko.applyBindings(new Schades(data, bedrijfId));
			$.unblockUI();
		}).fail(function(data){
            commonFunctions.nietMeerIngelogd(data);
		});
	};
});