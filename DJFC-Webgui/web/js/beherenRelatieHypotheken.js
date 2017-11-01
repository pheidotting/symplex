define(['jquery',
        'knockout',
        'commons/3rdparty/log',
        'js/model/hypothekenEnPakketten',
        'service/hypotheek-service'],
    function($, ko, log, hypothekenEnPakketten, hypotheekService){

    return function(relatieId) {
		log.debug("inlezen hypotheken");

		hypotheekService.lijstHypotheken(relatieId).done(function(hypotheken) {
			hypotheekService.lijstHypotheekPakketten(relatieId).done(function(pakketten) {
				log.debug("opgehaald");
				log.debug(JSON.stringify(hypotheken));
				log.debug(JSON.stringify(pakketten));

				var h = new hypothekenEnPakketten(pakketten, hypotheken);

				ko.applyBindings(h);
			});
		});
	};
});