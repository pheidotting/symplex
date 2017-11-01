define(['jquery',
        "knockout",
        'model/schades',
        'commons/block',
        'commons/3rdparty/log',
        'commons/commonFunctions',
        'service/schade-service'],
    function($, ko, Schades, block, log, commonFunctions, schadeService) {

    return function(relatieId) {
		log.debug("Ophalen schades bij Relatie met id " + relatieId);
		block.block();

		schadeService.lijstSchades(relatieId).done(function(data){
            log.debug("opgehaald : " + JSON.stringify(data));
            ko.validation.registerExtenders();

            ko.applyBindings(new Schades(data, relatieId));
            $.unblockUI();
		}).fail(function(data){
            commonFunctions.nietMeerIngelogd(data);
            $.unblockUI();
		});
	};
});