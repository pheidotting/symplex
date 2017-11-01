define(['jquery',
        "knockout",
        'model/bedrijf',
        'commons/block',
        'commons/3rdparty/log',
        'dataServices',
        'opmerkingenLoader'],
    function($, ko, Bedrijf, block, log, dataServices, opmerkingenLoader) {

    return function(bedrijfId, relatieId) {
		block.block();
		log.debug("Ophalen Bedrijf met id : " + bedrijfId);

		dataServices.leesBedrijf(subId).done(function(data){
			log.debug(JSON.stringify(data));
			var bedrijf = new Bedrijf(data);
			bedrijf.relatie(relatieId);
			new opmerkingenLoader(relatieId).init().done(function(){
				ko.applyBindings(bedrijf);
			});
	    });
	};
});