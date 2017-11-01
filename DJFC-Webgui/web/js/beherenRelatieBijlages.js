define(['jquery',
        "knockout",
        'model/bijlages',
        'commons/block',
        'commons/3rdparty/log',
        'commons/commonFunctions'],
    function($, ko, Bijlages, block, log, commonFunctions) {

    return function(relatieId) {
		log.debug("Ophalen bijlages bij Relatie met id " + relatieId);
		block.block();
		$.ajax({
			type: "GET",
			url: "../dejonge/rest/medewerker/bijlage/lijst",
			async: false,
			dataType: "json",
			data: {
				relatieId : relatieId
			},
			context: this,
			success: function(data) {
				log.debug("opgehaald : " + JSON.stringify(data));
				ko.validation.registerExtenders();

				ko.applyBindings(new Bijlages(data));
			},
            error: function (data) {
            	commonFunctions.nietMeerIngelogd(data);
    		}
		});
	};
});