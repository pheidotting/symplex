define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository'],
    function(log, navRegister, ko, abstractRepository) {

        return {
            aanmelden: function(data) {
                var url = navRegister.bepaalUrl('AANMELDEN_KANTOOR');
                log.debug("Versturen naar " + url + " : ");
                log.debug(ko.toJSON(data));

                return abstractRepository.voerUitPost(url, ko.toJSON(data));
            },

            lees: function() {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LEES_KANTOOR'));
            },

            opslaan: function(data) {
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('OPSLAAN_KANTOOR'), ko.toJSON(data));
            }
        }
    }
);