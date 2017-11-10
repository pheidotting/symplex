define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log2',
		'redirect',
        'service/hypotheek-service',
        'mapper/hypotheek-mapper',
        'mapper/hypotheekPakket-mapper',
        'moment',
         'commons/opmaak'],
    function($, commonFunctions, ko, functions, block, log, redirect, hypotheekService, hypotheekMapper, hypotheekPakketMapper, moment, opmaak) {

    return function() {
        var _this = this;
        var logger = log.getLogger('lijst-hypotheken-viewmodel');
        var soortEntiteit = 'HYPOTHEEK';

        this.basisEntiteit = null;
        this.id = ko.observable();
        this.hypotheken = ko.observableArray();
        this.hypotheekPakketten = ko.observableArray();

        this.init = function(id, basisEntiteit) {
            var deferred = $.Deferred();

            _this.id(id);
            _this.basisEntiteit = basisEntiteit;
            $.when(hypotheekService.lijstHypotheken(id), hypotheekService.lijstHypotheekPakketten(id), hypotheekService.lijstSoortenHypotheek()).then(function(hypotheken, pakketten, lijstSoortenHypotheek) {
                _this.hypotheken = hypotheekMapper.mapHypotheken(hypotheken, lijstSoortenHypotheek);
                _this.hypotheekPakketten = hypotheekPakketMapper.mapHypotheekPakketten(pakketten, lijstSoortenHypotheek);



                return deferred.resolve();
            });

            return deferred.promise();
        };

		this.formatDatum = function(datum) {
		    datum(moment(datum(), 'YYYY-MM-DD').format('DD-MM-YYYY'));

		    return datum;
		};

		this.formatBedrag = function(bedrag) {
            return opmaak.maakBedragOp(bedrag());
		};

		this.bewerkHypotheek = function(hypotheek) {
            redirect.redirect('BEHEREN_' + _this.basisEntiteit, _this.id(), 'hypotheek', hypotheek.id());
        };

		this.verwijderHypotheek = function(hypotheek) {
            var r=confirm("Weet je zeker dat je deze hypotheek wilt verwijderen?");
            if (r==true) {
                _this.hypotheken.remove(hypotheek);
                hypotheekService.verwijderSchade(hypotheek.id());
            }
        };
	};
});