define(['jquery',
        "knockout",
        'model/jaarcijferses',
        'commons/block',
        'commons/3rdparty/log',
        'commons/commonFunctions',
        'dataServices',
        'fileUpload',
        'opmerkingenLoader'],
    function($, ko, JaarCijferses, block, log, commonFunctions, dataServices, fileUpload, opmerkingenLoader) {

    return function(bedrijfsId) {
		block.block();
		log.debug("Ophalen jaarcijfers");

		dataServices.ophalenJaarCijfers(bedrijfsId).done(function(data){
			log.debug("Opgehaald " + data);

            fileUpload.init().done(function(){
                new opmerkingenLoader(bedrijfsId).init().done(function(){
                    ko.applyBindings(new JaarCijferses(data));
                    $.unblockUI();
                });
            });
		});
	};
});