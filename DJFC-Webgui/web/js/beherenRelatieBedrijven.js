define(['jquery',
        "knockout",
        'model/bedrijven',
        'commons/block',
        'commons/3rdparty/log',
        'commons/commonFunctions',
        'dataServices'],
    function($, ko, Bedrijven, block, log, commonFunctions, dataServices) {

    return function(relatieId) {
		log.debug("Ophalen bedrijven bij Relatie met id " + relatieId);
		block.block();

		dataServices.lijstBedrijven(relatieId).done(function(data){
			log.debug("opgehaald : " + JSON.stringify(data));
			ko.validation.registerExtenders();

			ko.applyBindings(new Bedrijven(data));
		}).fail(function(data){
			commonFunctions.nietMeerIngelogd(data);
		});
	};
});