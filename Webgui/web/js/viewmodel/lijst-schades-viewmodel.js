define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log',
        'redirect',
        'service/schade-service',
        'mapper/schade-mapper',
        'moment',
        'viewmodel/common/menubalk-viewmodel',
        'viewmodel/common/licentie-viewmodel',
        'viewmodel/common/breadcrumbs-viewmodel',
        'commons/opmaak'],
    function ($, commonFunctions, ko, functions, block, log, redirect, schadeService, schadeMapper, moment, menubalkViewmodel, LicentieViewmodel, BreadcrumbsViewmodel, opmaak) {

        return function () {
            var _this = this;
            this.menubalkViewmodel = null;
            this.licentieViewmodel = null;
            this.breadcrumbsViewmodel = null;

            this.basisEntiteit = null;
            this.id = ko.observable();
            this.schades = ko.observableArray();
            this.identificatie = null;

            this.init = function (id, basisEntiteit) {
                _this.identificatie = id.identificatie;

                var deferred = $.Deferred();

                _this.id(id);
                _this.basisEntiteit = basisEntiteit;
                if(_this.identificatie != null){
                    $.when(schadeService.lijstSchades(_this.identificatie), schadeService.lijstStatusSchade()).then(function (data, statussenSchade) {
                        _this.basisId = data.identificatie;
                        if (data.naam != null) {
                            _this.basisEntiteit = "BEDRIJF";
                        } else {
                            _this.basisEntiteit = "RELATIE";
                        }

                        var lijstSchades = _.chain(data.pakketten)
                            .map(function (pakket) {
                                return pakket.polissen;
                            })
                            .flatten()
                            .map(function (polis) {
                                _.each(polis.schades, function (schade) {
                                    schade.polis = polis.identificatie;
                                });

                                return polis;
                            })
                            .map('schades')
                            .flatten()
                            .value();


                        _this.schades = schadeMapper.mapSchades(lijstSchades, statussenSchade);
                        _this.menubalkViewmodel = new menubalkViewmodel(_this.identificatie, _this.basisEntiteit);
                        _this.licentieViewmodel = new LicentieViewmodel();
                        _this.breadcrumbsViewmodel = new BreadcrumbsViewmodel(data, null, null, false, true);

                        return deferred.resolve();
                    });
                } else {
                    $.when(schadeService.lijstStatusSchade(), schadeService.lijstOpenSchades()).then(function (statussenSchade, lijstSchades) {
                        _this.schades = schadeMapper.mapSchades(lijstSchades, statussenSchade);
                        _this.menubalkViewmodel = new menubalkViewmodel();
                        _this.licentieViewmodel = new LicentieViewmodel();

                        return deferred.resolve();
                    });
                }

                return deferred.promise();
            };

            this.formatDatum = function (datum) {
                datum(moment(datum(), 'YYYY-MM-DD').format('DD-MM-YYYY'));

                return datum;
            };

            this.formatBedrag = function (bedrag) {
                return opmaak.maakBedragOp(bedrag());
            };

            this.bewerk = function (schade) {
                redirect.redirect('BEHEER_SCHADE', schade.identificatie());
            }

            this.inzien = function (schade) {
                redirect.redirect('BEHEER_SCHADE', schade.identificatie(), true);
            }

            this.verwijder = function (schade) {
                var r = confirm("Weet je zeker dat je deze schade wilt verwijderen?");
                if (r) {
                    _this.schades.remove(schade);
                    schadeService.verwijderSchade(schade.identificatie());
                    location.reload();
                }
            }

            this.verwijderSchade = function (schade) {
                var r = confirm("Weet je zeker dat je deze schade wilt verwijderen?");
                if (r) {
                    _this.schades.remove(schade);
                    schadeService.verwijderSchade(schade.id());
                    location.reload();
                }
            };
        };
    });