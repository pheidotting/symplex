define(['jquery',
        'model/zoekresultaat',
        'commons/3rdparty/log',
        'mapper/adres-mapper',
        'knockout'],
    function ($, Zoekresultaat, log, adresMapper, ko) {
        return {
            mapZoekresultaat: function (r) {
                mappen(r);
            },

            mapZoekresultaten: function (data) {
                var zoekresultaten = ko.observableArray([]);

                $.each(data, function (i, r) {
                    zoekresultaten.push(mappen(r));
                });

                return zoekresultaten;
            }
        }

        function mappen(data) {
            if (data != null) {
                var zoekresultaat = new Zoekresultaat();

                zoekresultaat.identificatie(data.identificatie);
                zoekresultaat.id(data.id);
                if (data.naam != null) {
                    //Dus een bedrijf
                    zoekresultaat.naam(data.naam);
                    zoekresultaat.soortEntiteit('BEDRIJF');
                } else {
                    //Een relatie..
                    var naam = data.roepnaam;
                    if (data.tussenvoegsel != null) {
                        naam += ' ' + data.tussenvoegsel;
                    }
                    naam += ' ' + data.achternaam;

                    zoekresultaat.naam(naam);
                    zoekresultaat.geboortedatum(data.geboortedatum);
                    zoekresultaat.soortEntiteit('RELATIE');
                }

                if (data.adres != null) {
                    zoekresultaat.adres(data.adres.straat + ' ' + data.adres.huisnummer);

                    var postcode = data.adres.postcode;
                    if (postcode != null && postcode.length === 6) {
                        postcode = postcode.substring(0, 4) + ' ' + postcode.substring(4, 6);
                    }

                    zoekresultaat.postcode(postcode);
                    zoekresultaat.plaats(data.adres.plaats);
                }

                return zoekresultaat;
            }
        }
    }
);
