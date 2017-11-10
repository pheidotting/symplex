define(['jquery',
        'model/telefoonNummer2',
        'commons/3rdparty/log2',
        'knockout'],
	function ($, Telefoonnummer, log, ko) {
        return {
            mapTelefoonnummer: function(r) {
                mappen(r);
            },

            mapTelefoonnummers: function(data) {
                var telefoonnummers = ko.observableArray([]);

                $.each(data, function(i, r){
                    telefoonnummers.push(mappen(r));
                });

                return telefoonnummers;
            }
        }

        function mappen(data){
            if(data != null) {
                var telefoonnummer = new Telefoonnummer();

                telefoonnummer.id(data.id);
                telefoonnummer.telefoonnummer(data.telefoonnummer);
                telefoonnummer.soort(data.soort);
                telefoonnummer.omschrijving(data.omschrijving);
                telefoonnummer.soortEntiteit(data.soortEntiteit);
                telefoonnummer.entiteitId(data.entiteitId);

                return telefoonnummer;
            }
        }
    }
);
