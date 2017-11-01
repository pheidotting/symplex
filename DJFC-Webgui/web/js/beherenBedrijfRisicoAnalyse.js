define(['jquery',
        "knockout",
        'model/risicoanalyse',
        'commons/block',
        'commons/3rdparty/log',
        'commons/commonFunctions',
        'dataServices',
        'fileUpload',
        'opmerkingenLoader'],
    function($, ko, risicoanalyse, block, log, commonFunctions, dataServices, fileUpload, opmerkingenLoader) {

    return function(bedrijfsId) {
		block.block();
		log.debug("Ophalen RisicoAnalyse");

		dataServices.ophalenRisicoAnalyse(bedrijfsId).done(function(data){
			log.debug("Opgehaald " + JSON.stringify(data));

            fileUpload.init(bedrijfsId).done(function(){
                new opmerkingenLoader(bedrijfsId).init().done(function(){
                    ko.applyBindings(new risicoanalyse(data));
                    $.unblockUI();
                });
            });
		}).fail(function(data){
		    log.debug(JSON.stringify(data));
		});
	};
});