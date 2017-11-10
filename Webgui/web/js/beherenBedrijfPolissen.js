define(['jquery',
        "knockout",
        'model/polissen',
        'commons/block',
        'commons/3rdparty/log',
        'commons/commonFunctions',
        'dataServices'],
    function($, ko, Polissen, block, log, commonFunctions, dataServices) {

    return function(bedrijfId) {
		log.debug("Ophalen polissen bij Bedrijf met id " + bedrijfId);
		block.block();

		dataServices.lijstPolissenBijBedrijf(bedrijfId).done(function(data){
			log.debug("opgehaald : " + JSON.stringify(data));

			dataServices.lijstVerzekeringsmaatschappijen().done(function(maatschappijen){
                $.each(data, function(key, value) {
                    if(value.maatschappij != null) {
                        $.each(maatschappijen, function(keyM, valueM) {
                            if(keyM == value.maatschappij){
                                value.maatschappij = valueM;
                            }
                        });
                    }
                });
                ko.validation.registerExtenders();

                ko.applyBindings(new Polissen(data));
                $.unblockUI();
			});

		}).fail(function(data){
			commonFunctions.nietMeerIngelogd(data);
		});
	};
});