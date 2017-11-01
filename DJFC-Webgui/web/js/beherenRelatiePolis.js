define(['jquery',
        "knockout",
        'model/polis',
        'commons/block',
        'commons/3rdparty/log',
        'commons/commonFunctions',
        'fileUpload',
        'opmerkingenLoader',
        'service/polis-service'],
    function($, ko, Polis, block, log, commonFunctions, fileUpload, opmerkingenLoader, polisService) {

    return function(polisId, relatieId, readOnly) {
		block.block();
		log.debug("Ophalen lijst met verzekeringsmaatschappijen");

		polisService.lijstVerzekeringsmaatschappijen().done(function(data){
			var $select = $('#verzekeringsMaatschappijen');
			$.each(data, function(key, value) {
			    $('<option>', { value : key }).text(value).appendTo($select);
			});
		});

		polisService.lijstParticulierePolissen().done(function(data){
			var $select = $('#soortVerzekering');
			$.each(data, function(key, value) {
			    $('<option>', { value : value }).text(value).appendTo($select);
			});
		});

        $('#bedrijfBijPolisDiv').hide();

        if(polisId != null && polisId != "0"){
            $('#soortVerzekering').prop('disabled', true);
            $('#soortVerzekeringAlles').prop('disabled', true);
            log.debug("Ophalen Polis met id : " + polisId);

            polisService.lees(polisId).done(function(data){
                log.debug(JSON.stringify(data));
                var polis = new Polis(data, readOnly);
                polis.relatie(relatieId);
                fileUpload.init(polisId).done(function(){
                    new opmerkingenLoader(relatieId).init().done(function(){
                        ko.applyBindings(polis);
                        $.unblockUI();
                    });
                });
            });
        }else{
            var polis = new Polis('', readOnly);
            polis.relatie(relatieId);
            new opmerkingenLoader(relatieId).init().done(function(){
                ko.applyBindings(polis);
                $.unblockUI();
            });
        }
	};
});