define(['jquery',
        "knockout",
        'commons/block',
        'commons/3rdparty/log',
        'commons/commonFunctions',
        'dataServices',
        'model/communicatieproducten'],
    function($, ko, block, log, commonFunctions, dataServices, Communicatieproducten) {

    return function(relatieId) {
		log.debug("Ophalen CommunicatieProducten bij Relatie met id " + relatieId);
		block.block();

        var producten;// = new Communicatieproducten({});
        var init = false;

//		setInterval(function(){
		dataServices.lijstCommunicatieProducten(relatieId, 'RELATIE').done(function(data){
            log.debug("opgehaald : " + JSON.stringify(data));
            ko.validation.registerExtenders();

            producten = new Communicatieproducten(data);
            if(!init) {
                ko.applyBindings(producten);
                init = true;
            } else {
                producten.communicatieproducten.valueHasMutated();
            }
			$.unblockUI();

		}).fail(function(data){
            commonFunctions.nietMeerIngelogd(data);
		});//}, 5000);

	};
});