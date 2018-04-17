define(['jquery',
        'model/hypotheek',
        'commons/3rdparty/log',
        'knockout'],
    function ($, Hypotheek, log, ko) {
        return {
            mapHypotheek: function (r, soortenHypotheek) {
                return mappen(r, soortenHypotheek);
            },

            mapHypotheken: function (data, soortenHypotheek) {
                var hypotheken = ko.observableArray([]);

                $.each(data, function (i, r) {
                    hypotheken.push(mappen(r, soortenHypotheek));
                });

                return hypotheken;
            }
        }

        function mappen(data, soortenHypotheek) {
            if (data != null) {
                var hypotheek = new Hypotheek();

                hypotheek.id(data.id);
                hypotheek.identificatie(data.identificatie);
                hypotheek.soortEntiteit(data.soortEntiteit);
                hypotheek.bank(data.bank);
                hypotheek.boxI(data.boxI);
                hypotheek.boxIII(data.boxIII);
                hypotheek.relatie(data.relatie);

                if (data.hypotheekVorm) {
                    $.each(soortenHypotheek, function (i, soort) {
                        if (parseInt(data.hypotheekVorm) == parseInt(soort.id)) {
                            var d = {};
                            d.id = soort.id;
                            d.hypotheekVorm = soort.omschrijving;

                            hypotheek.hypotheekVorm = d
                        }
                    });
                } else {
                    var d = {};
                    d.id = 0;

                    hypotheek.hypotheekVorm = d
                }

                hypotheek.hypotheekBedrag(data.hypotheekBedrag);
                hypotheek.rente(data.rente);
                hypotheek.marktWaarde(data.marktWaarde);
                hypotheek.onderpand(data.onderpand);
                hypotheek.koopsom(data.koopsom);
                hypotheek.vrijeVerkoopWaarde(data.vrijeVerkoopWaarde);
                hypotheek.taxatieDatum(data.taxatieDatum);
                hypotheek.wozWaarde(data.wozWaarde);
                hypotheek.waardeVoorVerbouwing(data.waardeVoorVerbouwing);
                hypotheek.waardeNaVerbouwing(data.waardeNaVerbouwing);
                hypotheek.ingangsDatum(data.ingangsDatum);
                hypotheek.eindDatum(data.eindDatum);
                hypotheek.duur(data.duur);
                hypotheek.ingangsDatumRenteVastePeriode(data.ingangsDatumRenteVastePeriode);
                hypotheek.eindDatumRenteVastePeriode(data.eindDatumRenteVastePeriode);
                hypotheek.duurRenteVastePeriode(data.duurRenteVastePeriode);
                hypotheek.leningNummer(data.leningNummer);
                hypotheek.omschrijving(data.omschrijving);

                return hypotheek;
            }
        }
    }
);
