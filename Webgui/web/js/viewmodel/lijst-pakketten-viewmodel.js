define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log',
        'redirect',
        'service/polis-service',
        'mapper/pakket-mapper',
        'viewmodel/common/menubalk-viewmodel',
        'viewmodel/common/licentie-viewmodel',
        'viewmodel/common/breadcrumbs-viewmodel',
        'moment',
        'commons/opmaak',
        'underscore'],
    function ($, commonFunctions, ko, functions, block, log, redirect, polisService, pakketMapper, menubalkViewmodel, LicentieViewmodel, BreadcrumbsViewmodel, moment, opmaak, _) {

        return function () {
            var _this = this;
            var logger = log.getLogger('lijst-pakketten-viewmodel');
            this.menubalkViewmodel = null;
            this.licentieViewmodel = null;
            this.breadcrumbsViewmodel = null;

            this.basisEntiteit = null;
            this.id = ko.observable();
            this.pakketten = ko.observableArray();
            this.identificatie = null;

            this.init = function (id, basisEntiteit) {
                _this.identificatie = id.identificatie;

                var deferred = $.Deferred();

                _this.basisEntiteit = basisEntiteit;

                $.when(polisService.lijstPolissen(_this.identificatie), polisService.lijstVerzekeringsmaatschappijen()).then(function (data, maatschappijen) {
                    if (data.naam != null) {
                        _this.basisEntiteit = "BEDRIJF";
                    } else {
                        _this.basisEntiteit = "RELATIE";
                    }

                    _this.pakketten = pakketMapper.mapPakketten(data.pakketten, maatschappijen);
                    _.each(_this.pakketten(), function(pakket){
                        if(pakket.polissen().length > 1){
                            var status = '<br />';
                            _.each(pakket.polissen(), function(polis){
                                status += '<br />' + polis.status();
                            });
                            pakket.status = ko.observable(status);
                        }else{
                            pakket.status = ko.observable(pakket.polissen()[0].status());
                        }

                        if(pakket.polissen().length > 1){
                            if("Pakket" === pakket.polissen()[0].soort()){
                                pakket.soort = ko.observable(pakket.polissen()[0].soort());
                            }else{
                                var soort = '<b>Pakket</b><br />';
                                _.each(pakket.polissen(), function(polis){
                                    soort += '<br />' + polis.soort();
                                });
                                pakket.soort = ko.observable(soort);
                            }
                        }else{
                            pakket.soort = ko.observable(pakket.polissen()[0].soort());
                        }

                        if(pakket.polissen().length > 1){
                            var kenmerk = '<br />';
                            _.each(pakket.polissen(), function(polis){
                                kenmerk += '<br />' + polis.kenmerk();
                            });
                            pakket.kenmerk = ko.observable(kenmerk);
                        }else{
                            pakket.kenmerk = ko.observable(pakket.polissen()[0].kenmerk());
                        }

                        if(pakket.polissen().length > 1){
                            var ingangsDatum = '<br />';
                            _.each(pakket.polissen(), function(polis){
                                ingangsDatum += '<br />' + _this.formatDatum(polis.ingangsDatum);
                            });
                            pakket.ingangsDatum = ko.observable(ingangsDatum);
                        }else{
                            pakket.ingangsDatum = ko.observable(_this.formatDatum(pakket.polissen()[0].ingangsDatum));
                        }

                        pakket.polisNummer(pakket.polisNummer());
                    });

                    _this.menubalkViewmodel = new menubalkViewmodel(_this.identificatie, _this.basisEntiteit);
                    _this.licentieViewmodel = new LicentieViewmodel();
                    _this.breadcrumbsViewmodel = new BreadcrumbsViewmodel(data, null, null, true);

                    return deferred.resolve();
                });

                return deferred.promise();
            };

            this.bewerk = function (polis) {
                redirect.redirect('BEHEER_POLIS', polis.identificatie());
            }

            this.inzien = function (polis) {
                redirect.redirect('BEHEER_POLIS', polis.identificatie(), true);
            }

            this.verwijder = function (polis) {
                var r = confirm("Weet je zeker dat je deze polis wilt verwijderen?");
                if (r) {
                    _this.pakketten.remove(polis);
                    polisService.verwijderPolis(polis.identificatie());
                }
            }

            this.formatDatum = function (datum) {
                return moment(datum(), 'YYYY-MM-DD').format('DD-MM-YYYY');
            };

            this.formatBedrag = function (bedrag) {
                return opmaak.maakBedragOp(bedrag());
            };

            this.polisInzien = function (polis) {
                commonFunctions.verbergMeldingen();
                logger.debug('Polis inzien met id ' + polis.id() + ' en ' + _this.basisEntiteit + ' id : ' + _this.id());
                redirect.redirect('BEHEREN_' + _this.basisEntiteit, _this.id(), 'polisInzien', polis.id());
            };

            this.bewerkPolis = function (polis) {
                commonFunctions.verbergMeldingen();
                logger.debug('Polis bewerken met id ' + polis.id() + ' en ' + _this.basisEntiteit + ' id : ' + _this.id());
                redirect.redirect('BEHEREN_' + _this.basisEntiteit, _this.id(), 'polis', polis.id());
            };

            this.beeindigPolis = function (polis) {
                polisService.beindigPolis(polis.id());
                polis.eindDatum(moment().format("DD-MM-YYYY"));
            };
        };
    });