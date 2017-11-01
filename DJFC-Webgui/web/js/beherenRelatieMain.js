define(['jquery',
        "knockout",
        'model/relatie',
        'commons/block',
        'commons/3rdparty/log',
        'commons/commonFunctions',
        'jqueryUI',
        'fileUpload',
        'opmerkingenLoader',
        'service/gebruiker-service'],
    function($, ko, Relatie, block, log, commonFunctions, jqueryUI, fileUpload, opmerkingenLoader, gebruikerService) {

    return function(relatieId) {
		block.block();
		log.debug("ophalen Relatie met id " + relatieId);

		gebruikerService.leesRelatie(relatieId).done(function(data) {
			log.debug("opgehaald : " + JSON.stringify(data));
			ko.validation.registerExtenders();

            var relatie = new Relatie(data);

            new fileUpload.init(relatieId).done(function(){
                new opmerkingenLoader(relatieId).init().done(function() {
                    ko.applyBindings(relatie);
                    $.unblockUI();
                });
            });
		}).fail(function(data){
			commonFunctions.nietMeerIngelogd(data);
		});
	};
});