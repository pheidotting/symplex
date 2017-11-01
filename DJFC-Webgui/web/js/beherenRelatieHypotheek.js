define(['jquery',
        'knockout',
        "commons/3rdparty/log",
         "js/model/hypotheek",
         'service/hypotheek-service',
        'fileUpload',
        'opmerkingenLoader'],
    function($, ko, logger, hypotheek, hypotheekService, fileUpload, opmerkingenLoader){

    return function(hypotheekId, relatieId) {
		logger.debug("aanmaken nieuw hypotheek model");

		hypotheekService.lijstSoortenHypotheek().done(function(data){
			var $select = $('#hypotheekVorm');
		    $('<option>', { value : '' }).text('Kies een soort hypotheek uit de lijst...').appendTo($select);
			$.each(data, function(key, value) {
			    $('<option>', { value : value.id }).text(value.omschrijving).appendTo($select);
			});
			hypotheekService.lijstHypothekenInclDePakketten(relatieId).done(function(data) {
				if(data.length > 0){
					var $select = $('#koppelHypotheek');
					$('<option>', { value : '' }).text('Kies evt. een hypotheek om mee te koppelen...').appendTo($select);
					$.each(data, function(key, value) {
						var h = new hypotheek(value);
						if(h.id() != hypotheekId){
							$('<option>', { value : value.id }).text(h.titel()).appendTo($select);
						}
					});
				}else{
					$('#gekoppeldeHypotheekGroep').hide();
				}
				hypotheekService.leesHypotheek(hypotheekId).done(function(data) {
					logger.debug("Gegevens opgehaald voor hypotheek, applyBindings");
					logger.debug(data);
                    fileUpload.init(relatieId).done(function(){
						new opmerkingenLoader(relatieId).init().done(function(){
	    					ko.applyBindings(new hypotheek(data));
						});
                    });
				});
			});
		});
	};
});