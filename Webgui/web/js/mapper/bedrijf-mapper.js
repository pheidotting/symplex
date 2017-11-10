define(['jquery',
        'model/bedrijf2',
        'commons/3rdparty/log2',
        'knockout',
        'mapper/adres-mapper'],
	function ($, Bedrijf, log, ko, adresMapper) {
        return {
            mapBedrijf: function(r) {
                return mappen(r);
            },

            mapBedrijven: function(data) {
                var bedrijven = ko.observableArray([]);

                $.each(data, function(i, r){
                    bedrijven.push(mappen(r));
                });

                return bedrijven;
            }
        }

        function mappen(data){
            if(data != null) {
                var bedrijf = new Bedrijf();

                bedrijf.id(data.id);
                bedrijf.soortEntiteit(data.soortEntiteit);
                bedrijf.naam(data.naam);
                bedrijf.kvk(data.kvk);
                bedrijf.rechtsvorm(data.rechtsvorm);
                bedrijf.email(data.email);
                bedrijf.internetadres(data.internetadres);
                bedrijf.hoedanigheid(data.hoedanigheid);
                bedrijf.cAoVerplichtingen(data.cAoVerplichtingen);
                bedrijf.contactpersonen(data.contactpersonen);
                bedrijf.identificatie(data.identificatie);

               if(data.adressen != null){
                    bedrijf.adressen = ko.observableArray([]);
                    var adressen = adresMapper.mapAdressen(data.adressen);
                    $.each(adressen(), function(i, adres){
                        bedrijf.adressen.push(adres);
                    });
                }

                return bedrijf;
            }
        }
    }
);
