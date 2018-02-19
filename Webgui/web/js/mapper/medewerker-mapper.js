define(['jquery',
        'model/medewerker',
        'commons/3rdparty/log2',
        'knockout'],
	function ($, Medewerker, log, ko) {
        return {
            mapMedewerker: function(r) {
                mappen(r);
            },

            mapMedewerkers: function(data) {
                var medewerkers = ko.observableArray([]);

                $.each(data, function(i, r){
                    medewerkers.push(mappen(r));
                });

                return medewerkers;
            }
        }

        function mappen(data){
            if(data != null) {
                var medewerker = new Medewerker();

                medewerker.identificatie(data.identificatie);
                medewerker.voornaam(data.voornaam);
                medewerker.tussenvoegsel(data.tussenvoegsel);
                medewerker.achternaam(data.achternaam);
                medewerker.emailadres(data.emailadres);

                return medewerker;
            }
        }
    }
);
