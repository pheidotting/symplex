define(['jquery',
        "knockout",
        'model/aangifte',
        'model/aangiftes',
        'commons/block',
        'commons/3rdparty/log',
        'dataServices'],
    function($, ko, Aangifte, Aangiftes, block, log, dataServices) {

    return function(relatieId) {
		block.block();
		log.debug("Ophalen openstaande aangiftes voor Relatie met id : " + relatieId);

		dataServices.lijstGeslotenAangiftes(relatieId).done(function(data) {
			ko.applyBindings(new Aangiftes(data));
	    });
	};
});