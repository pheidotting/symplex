define(['jquery',
        'model/schade',
        'commons/3rdparty/log',
        'knockout',
        'moment'],
    function ($, Schade, log, ko, moment) {
        return {
            mapSchade: function (r) {
                return mappen(r);
            },

            mapSchades: function (data) {
                var schades = ko.observableArray([]);

                $.each(data, function (i, r) {
                    schades.push(mappen(r));
                });

                return schades;
            }
        }

        function mappen(data) {
            if (data != null) {
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
                if (data.datumSchade != null && data.datumSchade != '') {
                    schade.datumSchade(moment(data.datumSchade).format('YYYY-MM-DD'));
                }
                if(data.datumMelding != null && data.datumMelding != ''){
                    schade.datumMelding(moment(data.datumMelding).format('YYYY-MM-DD'));
                }
                schade.datumAfgehandeld(data.datumAfgehandeld);
                schade.eigenRisico(data.eigenRisico);
                schade.omschrijving(data.omschrijving);
                schade.identificatie(data.identificatie);

                return schade;
            }
        }
    }
);
