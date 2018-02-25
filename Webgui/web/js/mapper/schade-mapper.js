define(['jquery',
        'model/schade2',
        'commons/3rdparty/log',
        'knockout'],
	function ($, Schade, log, ko) {
        return {
            mapSchade: function(r) {
                return mappen(r);
            },

            mapSchades: function(data) {
                var schades = ko.observableArray([]);

                $.each(data, function(i, r){
                    schades.push(mappen(r));
                });

                return schades;
            }
        }

        function mappen(data){
            if(data != null) {
                var schade = new Schade();

                schade.id(data.id);
                schade.soortEntiteit(data.soortEntiteit);
                schade.relatie(data.relatie);
                schade.bedrijf(data.bedrijf);
                schade.polis(data.polis);
                schade.schadeNummerMaatschappij(data.schadeNummerMaatschappij);
                schade.schadeNummerTussenPersoon(data.schadeNummerTussenPersoon);
                schade.soortSchade(data.soortSchade);
                schade.locatie(data.locatie);
                schade.statusSchade(data.statusSchade);
                schade.datumTijdSchade(data.datumTijdSchade);
                schade.datumTijdMelding(data.datumTijdMelding);
                schade.datumAfgehandeld(data.datumAfgehandeld);
                schade.eigenRisico(data.eigenRisico);
                schade.omschrijving(data.omschrijving);
                schade.identificatie(data.identificatie);

                return schade;
            }
        }
    }
);
