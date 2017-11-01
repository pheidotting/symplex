define([ "commons/3rdparty/log",
         "commons/validation",
         "commons/opmaak",
         'commons/commonFunctions',
         "knockout",
         'commons/block',
         'model/relatie',
         'model/adres',
         'js/model/taak/afhandelenTaak',
         'commons/3rdparty/log',
         'redirect',
         'dataServices'],
	function(logger, validation, opmaak, commonFunctions, ko, block, Relatie, Adres, AfhandelenTaak, log, redirect, dataServices) {

	return function taakAanvullenAdresAfhandelen(data) {
		_thisTaak = this;

		_thisTaak.relatie = ko.observable('');
		_thisTaak.straat = ko.observable(data.straat).extend({required: true});
		_thisTaak.huisnummer = ko.observable(data.huisnummer).extend({required: true, number: true});
		_thisTaak.toevoeging = ko.observable(data.toevoeging);
		_thisTaak.postcode = ko.observable(data.postcode).extend({required: true});
		_thisTaak.plaats = ko.observable(data.plaats).extend({required: true});

		dataServices.leesRelatie(data.gerelateerdAan).done(function(data){
			_thisTaak.relatie(data);
			_thisTaak.straat(data.straat);
			_thisTaak.huisnummer(data.huisnummer);
			_thisTaak.toevoeging(data.toevoeging);
			_thisTaak.postcode(data.postcode);
			_thisTaak.plaats(data.plaats);
		});

		_thisTaak.taakId = ko.observable(data.id);
		
		_thisTaak.opzoekenAdres = function(adres){
            log.debug(ko.toJSON(adres));
            adres.postcode(adres.postcode().toUpperCase().replace(" ", ""));

            dataServices.ophalenAdresOpPostcode(adres.postcode(), adres.huisnummer()).done(function(data){
                log.debug(JSON.stringify(data));
				adres.straat(data.straat);
				adres.plaats(data.plaats);
				adres.postcode(adres.zetPostcodeOm(adres.postcode()));
            }).fail(function(data){
                log.debug(JSON.stringify(data));
                adres.straat('');
                adres.plaats('');
            });
		};
		
		_thisTaak.opslaan = function(){
			block.block();

			var adres = new Adres('');
			adres.straat = _thisTaak.straat();
			adres.huisnummer = _thisTaak.huisnummer();
			adres.toevoeging = _thisTaak.toevoeging();
			adres.postcode = _thisTaak.postcode();
			adres.plaats = _thisTaak.plaats();
			adres.relatie = _thisTaak.relatie().id;
			
			logger.debug(ko.toJSON(adres));
			dataServices.opslaanAdresBijRelatie(adres).done(function(response){
				var afhandelenTaak = new AfhandelenTaak(_thisTaak.taakId());

				logger.debug(ko.toJSON(afhandelenTaak));

				dataServices.afhandelenTaak(ko.toJSON(afhandelenTaak)).done(function(response){
					redirect.redirect('TAKEN');
					commonFunctions.plaatsMelding("De gegevens zijn opgeslagen");
				}).fail(function(data){
					commonFunctions.plaatsFoutmelding(data);
				});
			}).fail(function(data){
				commonFunctions.plaatsFoutmelding(data);
			});
		};
		
		_thisTaak.terug = function(){
			redirect.redirect('TAKEN');
		};

        _thisTaak.zetPostcodeOm = function(postcode){
            if(postcode != null){
                if(postcode.length == 6){
                    postcode = postcode.toUpperCase();
                    postcode = postcode.substring(0, 4) + " " + postcode.substring(4);

                    return postcode;
                }
            }
        };

	};
});