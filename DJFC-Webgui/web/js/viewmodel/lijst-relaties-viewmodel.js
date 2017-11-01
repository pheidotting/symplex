define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'model/relatie',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log2',
		'redirect',
		'service/gebruiker-service',
		'mapper/gebruiker-mapper'],
    function($, commonFunctions, ko, Relatie, functions, block, log, redirect, gebruikerService, gebruikerMapper) {

    return function(term) {
        var _this = this;
        this.lijst = ko.observableArray([]);
        this.zoekTerm = ko.observable(term);
        this.gezochtMetTonen = ko.observable();
        this.gezochtMet = ko.observable();

        var logger = log.getLogger('lijst-relaties-viewmodel');

        this.init = function() {
            gebruikerService.lijstRelaties(_this.zoekTerm()).done(function(data) {
                logger.debug('opgehaald ' + JSON.stringify(data));
                var lijstje = gebruikerMapper.mapRelaties(data.jsonRelaties);
                $.each(lijstje(), function(i, relatie){
                    _this.lijst().push(relatie);
                });

                logger.debug('Relaties opgehaald, refresh KO');
                _this.lijst.valueHasMutated();
                $.unblockUI();
            });
            if(term != null) {
                _this.gezochtMetTonen(true);
                _this.gezochtMet(term);
            } else {
               _this.gezochtMetTonen(false);
            }

        }

        this.naarDetailScherm = function(relatie) {
            logger.debug('Ga naar Relatie met id ' + ko.utils.unwrapObservable(relatie.id));
			commonFunctions.verbergMeldingen();
			redirect.redirect('BEHEREN_RELATIE', ko.utils.unwrapObservable(relatie.id));
        }

        this.toevoegenNieuweRelatie = function(){
            logger.debug('Toevoegen nieuwe relatie');
            functions.verbergMeldingen();
            redirect.redirect('BEHEREN_RELATIE', '0');
        }

        this.zoeken = function() {
            logger.debug('zoeken met zoekTem ' + _this.zoekTerm());
            redirect.redirect('LIJST_RELATIES', _this.zoekTerm());
        }

        this.maakAdresOp = function(adressen) {
            logger.debug('maakAdresOp');
		    var adres = null;
		    $.each(adressen(), function(index, item){
		        if(item.soortAdres() === 'WOONADRES') {
		            adres = item;
		        }
		        if(adres == null) {
                    if(item.soortAdres() === 'POSTADRES') {
                        adres = item;
                    }
                }
		    });

		    if(adres !== null) {
		        return adres.straat() + ' ' + adres.huisnummer() + ', ' + adres.plaats();
		    } else {
		        return '';
		    }
        }

        $('#zoekTerm').on('keypress', function(e) {
            if (e != null && e.keyCode == 13) {
                redirect.redirect('LIJST_RELATIES', $('#zoekTerm').val());
            }
        });

        _this.init();
	};
});