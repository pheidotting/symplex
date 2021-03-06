define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log',
        'redirect',
        'service/hypotheek-service',
        'mapper/hypotheek-mapper',
        'mapper/hypotheekPakket-mapper',
        'moment',
        'viewmodel/common/menubalk-viewmodel',
        'viewmodel/common/licentie-viewmodel',
        'viewmodel/common/breadcrumbs-viewmodel',
        'commons/opmaak'],
    function ($, commonFunctions, ko, functions, block, log, redirect, hypotheekService, hypotheekMapper, hypotheekPakketMapper, moment, menubalkViewmodel, LicentieViewmodel, BreadcrumbsViewmodel, opmaak) {

        return function () {
            var _this = this;
            this.menubalkViewmodel = null;
            this.licentieViewmodel = null;
            this.breadcrumbsViewmodel = null;

            this.basisEntiteit = null;
            this.id = ko.observable();
            this.hypotheken = ko.observableArray();
            this.hypotheekPakketten = ko.observableArray();

            this.init = function (id, basisEntiteit) {
                _this.identificatie = id.identificatie;

                var deferred = $.Deferred();

                _this.id(id);
                _this.basisEntiteit = basisEntiteit;
                $.when(hypotheekService.lijstHypotheken(_this.identificatie), hypotheekService.lijstSoortenHypotheek()).then(function (data, lijstSoortenHypotheek) {
                    _this.hypotheken = hypotheekMapper.mapHypotheken(data.hypotheken, lijstSoortenHypotheek);

                    _this.menubalkViewmodel = new menubalkViewmodel(_this.identificatie, "RELATIE");
                    _this.licentieViewmodel = new LicentieViewmodel();
                    _this.breadcrumbsViewmodel = new BreadcrumbsViewmodel(data, null, null, false, false, null, true);

                    return deferred.resolve();
                });

                return deferred.promise();
            };

            this.formatDatum = function (datum) {
                datum(moment(datum(), 'YYYY-MM-DD').format('DD-MM-YYYY'));

                return datum;
            };

            this.formatBedrag = function (bedrag) {
                return opmaak.maakBedragOp(bedrag());
            };

            this.bewerk = function (hypotheek) {
                redirect.redirect('BEHEER_HYPOTHEEK', hypotheek.identificatie());
            };

            this.verwijder = function (hypotheek) {
                var r = confirm("Weet je zeker dat je deze hypotheek wilt verwijderen?");
                if (r) {
                    _this.hypotheken.remove(hypotheek);
                    hypotheekService.verwijderSchade(hypotheek.id());
                }
            };
        };
    });