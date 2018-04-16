define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository'],
    function (log, navRegister, ko, abstractRepository) {

        return {
            lijstSoortenHypotheek: function (id) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LIJST_SOORTEN_HYPOTHEEK'), {"id": id});
            },

            lijstHypothekenInclDePakketten: function (relatieId) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LIJST_HYPOTHEKEN_INCL_PAKKETTEN'), {relatieId: relatieId});
            },

            leesHypotheek: function (id) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LEES_HYPOTHEEK'), {"id": id});
            },

            lijstHypotheken: function (relatieId) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LIJST_HYPOTHEKEN'), {relatieId: relatieId});
            },

            lijstHypotheekPakketten: function (relatieId) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LIJST_HYPOTHEEKPAKKETTEN'), {relatieId: relatieId});
            },

            opslaanHypotheek: function (data) {
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('OPSLAAN_HYPOTHEEK'), ko.toJSON(data));
            },

            verwijderHypotheek: function (id) {
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('VERWIJDER_HYPOTHEEK') + '/' + id, null);
            }
        }
    }
);