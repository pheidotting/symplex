define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository'],
    function (log, navRegister, ko, abstractRepository) {
        var logger = log.getLogger('bedrijf-repository');

        return {
            opslaan: function (data) {
                var url = navRegister.bepaalUrl('OPSLAAN_BEDRIJF');
                logger.debug("Versturen naar " + url + " : ");
                logger.debug(ko.toJSON(data));

                return abstractRepository.voerUitPost(url, ko.toJSON(data));
            },

            verwijder: function (id) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('VERWIJDER_BEDRIJF'), {id: id});
            },

            leesBedrijf: function (id) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LEES_BEDRIJF') + '/' + id);
            },

            lijstBedrijven: function (zoekTerm) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LIJST_BEDRIJVEN_BIJ_RELATIE'), {zoekTerm: zoekTerm});
            }
        }
    }
);