define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository'],
    function (log, navRegister, ko, abstractRepository) {

        return {
            aanmelden: function (data) {
                var url = navRegister.bepaalUrl('AANMELDEN_KANTOOR');

                return abstractRepository.voerUitPost(url, ko.toJSON(data));
            },

            lees: function () {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LEES_KANTOOR'));
            },

            opslaan: function (data) {
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('OPSLAAN_KANTOOR'), ko.toJSON(data));
            }
        }
    }
);