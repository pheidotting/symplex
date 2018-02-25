define(['jquery',
        'model/contactpersoon2',
        'commons/3rdparty/log',
        'knockout',
        'mapper/telefoonnummer-mapper'],
	function ($, Contactpersoon, log, ko, telefoonnummerMapper) {
        return {
            mapContactpersoon: function(data) {
                return mappen(data);
            },

            mapContactpersonen: function(data) {
                var contactpersonen = ko.observableArray([]);

                $.each(data, function(i, r){
                    contactpersonen.push(mappen(r));
                });

                return contactpersonen;
            }
        }

        function mappen(data){
            if(data != null) {
                var contactpersoon = new Contactpersoon();

                contactpersoon.identificatie(data.identificatie);
                contactpersoon.voornaam(data.voornaam);
                contactpersoon.tussenvoegsel(data.tussenvoegsel);
                contactpersoon.achternaam(data.achternaam);
                contactpersoon.functie(data.functie);
                contactpersoon.emailadres(data.emailadres);
                $.each(telefoonnummerMapper.mapTelefoonnummers(data.telefoonnummers)(), function(i, item){
                    contactpersoon.telefoonnummers.push(item);
                });

                return contactpersoon;
            }
        }
    }
);
