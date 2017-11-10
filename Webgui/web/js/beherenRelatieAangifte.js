define(['jquery',
        "knockout",
        'model/aangifte',
        'model/aangiftes',
        'commons/block',
        'commons/3rdparty/log',
        'dataServices',
        'fileUpload',
        'opmerkingenLoader'],
    function($, ko, Aangifte, Aangiftes, block, log, dataServices, fileUpload, opmerkingenLoader) {

    return function(relatieId) {
		block.block();
		log.debug("Ophalen openstaande aangiftes voor Relatie met id : " + relatieId);

		dataServices.lijstOpenAangiftes(relatieId).done(function(data) {
            fileUpload.init(relatieId).done(function(){
                new opmerkingenLoader(relatieId).init().done(function(){
                        ko.applyBindings(new Aangiftes(data));
                });
            });
	    });
	};
});