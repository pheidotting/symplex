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
        'commons/opmaak'],
    function($, commonFunctions, ko, functions, block, log, redirect, schadeService, schadeMapper, moment, menubalkViewmodel, LicentieViewmodel, opmaak) {

    return function() {
        var _this = this;
		this.menubalkViewmodel      = null;
		this.licentieViewmodel      = null;

        this.basisEntiteit = null;
        this.id = ko.observable();
        this.schades = ko.observableArray();
        this.identificatie = null;

        this.init = function(id, basisEntiteit) {
            _this.identificatie = id.identificatie;

            var deferred = $.Deferred();

            _this.id(id);
            _this.basisEntiteit = basisEntiteit;
            $.when(schadeService.lijstSchades(_this.identificatie), schadeService.lijstStatusSchade()).then(function(data, statussenSchade) {
                _this.basisId = data.identificatie;
                if(data.kvk != null) {
                    _this.basisEntiteit = "BEDRIJF";
                } else {
                    _this.basisEntiteit = "RELATIE";
                }

                var lijstSchades = _.chain(data.polissen)
                    .map('schades')
                    .flatten()
                    .value();

                _this.schades = schadeMapper.mapSchades(lijstSchades, statussenSchade);
                _this.menubalkViewmodel     = new menubalkViewmodel(_this.identificatie, _this.basisEntiteit);
                _this.licentieViewmodel     = new LicentieViewmodel();

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

        this.bewerk = function(schade) {
			redirect.redirect('BEHEER_SCHADE', schade.identificatie());
        }

        this.inzien = function(schade) {
			redirect.redirect('BEHEER_SCHADE', schade.identificatie(), true);
        }

        this.verwijder = function(schade) {
            var r=confirm("Weet je zeker dat je deze schade wilt verwijderen?");
			if (r==true) {
			    _this.schades.remove(schade);
			    schadeService.verwijderSchade(schade.identificatie());
			}
        }

		this.verwijderSchade = function(schade) {
            var r=confirm("Weet je zeker dat je deze schade wilt verwijderen?");
            if (r==true) {
                _this.schades.remove(schade);
                schadeService.verwijderSchade(schade.id());
            }
        };
	};
});