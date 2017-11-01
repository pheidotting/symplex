define(['jquery',
        "knockout",
        'commons/block',
        'commons/3rdparty/log',
        'commons/commonFunctions',
        'dataServices',
        'model/communicatieproduct'],
    function($, ko, block, log, commonFunctions, dataServices, Communicatieproduct) {

    return function(subId, relatieId) {
		log.debug("Ophalen CommunicatieProduct met id " + subId + " voor Relatie met id " + relatieId);
		block.block();

        ko.validation.registerExtenders();

        if(subId > 0) {
            dataServices.leesCommunicatieProduct(subId).done(function(data){
                ko.applyBindings(new Communicatieproduct(data, relatieId));
                $.unblockUI();
            });
        } else {
            ko.applyBindings(new Communicatieproduct('', relatieId));
            $.unblockUI();
        }
	};
});