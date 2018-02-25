define(['jquery',
        'model/rekeningNummer2',
        'commons/3rdparty/log',
        'knockout'],
	function ($, RekeningNummer, log, ko) {
        return {
            mapRekeningnummer: function(r) {
                mappen(r);
            },

            mapRekeningnummers: function(data) {
                var rekeningnummers = ko.observableArray([]);

                $.each(data, function(i, r){
                    rekeningnummers.push(mappen(r));
                });

                return rekeningnummers;
            }
        }

        function mappen(data){
            if(data != null) {
                var rekeningnummer = new RekeningNummer();

                rekeningnummer.id(data.id);
                rekeningnummer.rekeningnummer(data.rekeningnummer);
                rekeningnummer.bic(data.bic);
                rekeningnummer.soortEntiteit(data.soortEntiteit);
                rekeningnummer.entiteitId(data.entiteitId);
                rekeningnummer.identificatie(data.identificatie);

                return rekeningnummer;
            }
        }
    }
);
