define(['jquery',
        "knockout",
        'model/schade',
        'commons/block',
        'commons/3rdparty/log',
        'commons/commonFunctions',
        'fileUpload',
        'opmerkingenLoader',
        'navRegister',
        'service/schade-service',
        'service/polis-service'],
    function($, ko, Schade, block, log, commonFunctions, fileUpload, opmerkingenLoader, navRegister, schadeService, polisService) {

    return function(schadeId, relatieId) {
		block.block();

		polisService.lijstPolissen(relatieId).done(function(data) {
			var $select = $('#polisVoorSchademelding');
			$.each(data, function(key, value) {
				var polisTitel = value.soort + " (" + value.polisNummer + ")";

			    $('<option>', { value : value.id }).text(polisTitel).appendTo($select);
			});

            schadeService.lijstStatusSchade().done(function(data) {
				var $select = $('#statusSchade');
				$.each(data, function(key, value) {
				    $('<option>', { value : value }).text(value).appendTo($select);
				});
				if(schadeId != null && schadeId != "0"){
					log.debug("ophalen Schade met id " + schadeId);

					$.when(schadeService.lees(schadeId), fileUpload.init(relatieId), new opmerkingenLoader(relatieId).init()).then(function(data){
                        var schade = new Schade(data);
                        schade.relatie(relatieId);
                        ko.applyBindings(schade);
                        $.unblockUI();
					});
				}else{
					log.debug("applybindings met een nieuw Schade object");
					var schade = new Schade('');
					schade.relatie(relatieId);
                    new opmerkingenLoader(relatieId).init().done(function(){
                        log.debug('uhgyggygyg');
                        ko.applyBindings(schade);
                        $.unblockUI();
                    });
				}
			});
		});

//			var soortenSchade = new Bloodhound({
//				datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
//				queryTokenizer: Bloodhound.tokenizers.whitespace,
//				remote: '../dejonge/rest/medewerker/overig/soortenSchade?query=%QUERY'
//			});
//
//			soortenSchade.initialize();
//
//			$('#soortSchade').typeahead({
//				hint: true,
//				highlight: true,
//				minLength: 1
//			},
//			{
//				name: 'states',
//				displayKey: 'value',
//				source: soortenSchade.ttAdapter()
//			});
	};
});