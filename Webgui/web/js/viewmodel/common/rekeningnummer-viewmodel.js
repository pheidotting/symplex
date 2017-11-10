define(['commons/3rdparty/log2',
        'model/rekeningNummer2',
        'knockout',
        'mapper/rekeningnummer-mapper'],
    function(log, RekeningNummer, ko, rekeningnummerMapper) {

    return function(readOnly, soortEntiteit, entiteitId, rekeningnummers) {
        var _this = this;
        var logger = log.getLogger('rekeningnummer-viewmodel');

		this.id = ko.observable(entiteitId);
		this.soortEntiteit = ko.observable(soortEntiteit);
		this.rekeningnummers = ko.observableArray();
        $.each(rekeningnummerMapper.mapRekeningnummers(rekeningnummers)(), function(i, rekeningnummer){
            var rek = rekeningnummer.rekeningnummer().toUpperCase();

            if(rek !== undefined && rek.length === 18) {
                rek = rek.substring(0, 4) + " " +rek.substring(4, 8) + " " +rek.substring(8, 12) + " " +rek.substring(12, 16) + " " +rek.substring(16, 18);
            }

            rekeningnummer.rekeningnummer(rek);

            if(rek.startsWith('NL')) {
                rekeningnummer.bicTonen(false);
            } else {
                rekeningnummer.bicTonen(true);
            }

            _this.rekeningnummers.push(rekeningnummer);
        });

        this.rekeningnummers.subscribe(function(rekeningnummers) {
            logger.debug('subscriber afgegaan');
            $.each(rekeningnummers, function(i, nummer){
                _this.zetRekeningnummerOm(nummer);
            });
        });

        this.voegRekeningToe = function() {
            _this.rekeningnummers.push(new RekeningNummer());
        };

        this.verwijderRekening = function(nummer) {
            _this.rekeningnummers.remove(nummer);
        };

        this.startBewerken = function(nummer) {
            if(nummer != undefined && nummer.rekeningnummer() != undefined) {
                nummer.rekeningnummer(nummer.rekeningnummer().replace(/ /g, ""));
            }
        };

        this.stopBewerken = function(nummer) {
            _this.zetRekeningnummerOm(nummer);
        };
        
        this.bicNaarUpperCase = function(nummer) {
            if(nummer.bic() != null) {
                nummer.bic(nummer.bic().toUpperCase());
            }
        };

        this.zetRekeningnummerOm = function(nummer) {
            if(nummer.rekeningnummer() !== undefined) {
                var rek = nummer.rekeningnummer().toUpperCase();

                if(rek !== undefined && rek.length === 18) {
                    rek = rek.substring(0, 4) + " " +rek.substring(4, 8) + " " +rek.substring(8, 12) + " " +rek.substring(12, 16) + " " +rek.substring(16, 18);
                }

                nummer.rekeningnummer(rek);

                if(rek.startsWith('NL')) {
                    nummer.bicTonen(false);
                } else {
                    nummer.bicTonen(true);
                }
            }
        };
    };
});