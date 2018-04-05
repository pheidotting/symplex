define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository'],
    function (log, navRegister, ko, abstractRepository) {

        return {
            openTakenBijRelatie: function (relatieId) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('OPEN_TAKEN_BIJ_RELATIE'), {"relatieId": relatieId});
            },

            aantalOpenTaken: function () {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('AANTAL_OPEN_TAKEN'));
            },

            afgerondeTaken: function (soortEntiteit, entiteitId) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('AFGERONDE_TAKEN') + '/' + soortEntiteit + '/' + entiteitId);
            }
        }
    }
);