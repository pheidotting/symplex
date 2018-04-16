define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository'],
    function (log, navRegister, ko, abstractRepository) {
        var logger = log.getLogger('gebruiker-repository');

        return {

            opslaan: function (data) {
                var url = navRegister.bepaalUrl('OPSLAAN_RELATIE');
                logger.debug("Versturen naar " + url + " : ");
                logger.debug(ko.toJSON(data));

                return abstractRepository.voerUitPost(url, ko.toJSON(data));
            },

            opslaanMedewerker: function (data) {
                var url = navRegister.bepaalUrl('OPSLAAN_MEDEWERKER');
                logger.debug("Versturen naar " + url + " : ");
                logger.debug(ko.toJSON(data));

                return abstractRepository.voerUitPost(url, ko.toJSON(data));
            },

            verwijderRelatie: function (id) {
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('VERWIJDER_RELATIE') + '/' + id, null);
            },

            leesRelatie: function (id, foutmeldingOnderdrukken) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LEES_RELATIE_NW') + '/' + id, null, foutmeldingOnderdrukken);
            },

            leesMedewerker: function (id) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LEES_MEDEWERKER') + '/' + id);
            },

            opslaanOAuthCode: function (code) {
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('OPSLAAN_OAUTH_CODE'), code);
            },

            haalIngelogdeGebruiker: function (token) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('INGELOGDE_GEBRUIKER'), {'userid': token});
            },

            leesOAuthCode: function () {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LEES_OAUTH_CODE'));
            },

            wijzigWachtwoord: function (nieuwWachtwoord) {
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('WIJZIG_WACHTWOORD'), nieuwWachtwoord);
            },

            stuurNieuwWachtwoord: function (identificatie) {
                abstractRepository.voerUitPost(navRegister.bepaalUrl('STUUR_NIEUW_WACHTWOORD'), identificatie);
            }
        }
    }
);