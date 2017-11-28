define(['jquery',
        'model/adres',
        'commons/3rdparty/log2',
        'knockout'],
	function ($, Adres, log, ko) {
        return {
            mapAdres: function(r) {
                mappen(r);
            },

            mapAdressen: function(data) {
                var adresssen = ko.observableArray([]);

                $.each(data, function(i, r){
                    adresssen.push(mappen(r));
                });

                return adresssen;
            }
        }

        function mappen(data){
            if(data != null) {
                var adres = new Adres();

                adres.id(data.id);
                adres.straat(data.straat);
                adres.huisnummer(data.huisnummer);
                adres.toevoeging(data.toevoeging);
                adres.postcode(data.postcode);
                adres.soortAdres(data.soortAdres);
                adres.plaats(data.plaats.toUpperCase());
                adres.soortEntiteit(data.soortEntiteit);
                adres.entiteitId(data.entiteitId);
                adres.identificatie(data.identificatie);

                return adres;
            }
        }
    }
);
