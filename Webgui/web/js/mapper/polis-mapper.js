define(['jquery',
        'model/polis',
        'commons/3rdparty/log',
        'knockout'],
    function ($, Polis, log, ko) {
        return {
            mapPolis: function (r, maatschappijen) {
                return mappen(r, maatschappijen);
            },

            mapPolissen: function (data, maatschappijen) {
                var polissen = ko.observableArray([]);

                $.each(data, function (i, r) {
                    polissen.push(mappen(r, maatschappijen));
                });

                return polissen;
            }
        }

        function mappen(data, maatschappijen) {
            if (data != null) {
                var polis = new Polis();

                polis.identificatie(data.identificatie);
                polis.status(data.status);
                polis.polisNummer(data.polisNummer);
                polis.kenmerk(data.kenmerk);
                if (data.ingangsDatum != null) {
                    polis.ingangsDatum(data.ingangsDatum);
                }
                if (data.eindDatum != null) {
                    polis.eindDatum(data.eindDatum);
                }
                if (data.wijzigingsDatum != null) {
                    polis.wijzigingsDatum(data.wijzigingsDatum);
                }
                if (data.prolongatieDatum) {
                    polis.prolongatieDatum(data.prolongatieDatum);
                }
                polis.soort(data.soort);
                polis.premie(data.premie);
                polis.betaalfrequentie(data.betaalfrequentie);
                polis.dekking(data.dekking);
                polis.verzekerdeZaak(data.verzekerdeZaak);
                polis.bedrijf(data.bedrijf);
                polis.bedrijfsId(data.bedrijfsId);
                polis.omschrijvingVerzekering(data.omschrijvingVerzekering);
                polis.soortEntiteit(data.soortEntiteit);
                polis.entiteitId(data.entiteitId);

                $.each(maatschappijen, function (i, m) {
                    if (i == data.maatschappij) {
                        var d = {};
                        d.id = i;
                        d.omschrijving = m;

                        polis.maatschappij = d;
                    }
                });

                return polis;
            }
        }
    }
);
