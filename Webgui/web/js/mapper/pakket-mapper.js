define(['jquery',
        'model/pakket',
        'commons/3rdparty/log',
        'mapper/polis-mapper',
        'knockout'],
    function ($, Pakket, log, polismapper, ko) {
        return {
            mapPakket: function (r, maatschappijen) {
                return mappen(r, maatschappijen);
            },

            mapPakketten: function (data, maatschappijen) {
                var pakketten = ko.observableArray([]);

                $.each(data, function (i, r) {
                    pakketten.push(mappen(r, maatschappijen));
                });

                return pakketten;
            }
        }

        function mappen(data, maatschappijen) {
            if (data != null) {
                var pakket = new Pakket();
                
                pakket.identificatie(data.identificatie);
                pakket.polisNummer(data.polisNummer);
                pakket.soortEntiteit(data.soortEntiteit);
                pakket.entiteitId(data.entiteitId);
                pakket.maatschappij(data.maatschappij);

//                $.each(data.polissen, function (i, r) {
//                    pakket.polissen.push(polismapper.mapPolis(r));
//                });
//                pakket.polissen(polismapper.mapPolissen(data.polissen));
                pakket.polissen = polismapper.mapPolissen(data.polissen);

                $.each(maatschappijen, function (i, m) {
                    if (i == data.maatschappij) {
                        var d = {};
                        d.id = i;
                        d.omschrijving = m;

                        pakket.maatschappij = d;
                    }
                });

                return pakket;
            }
        }
    }
);
