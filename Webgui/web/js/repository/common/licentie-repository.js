define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository'],
    function(log, navRegister, ko, abstractRepository) {

        return {
            einddatum: function() {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LICENTIE_EINDDATUM'));
            }
        }
    }
);