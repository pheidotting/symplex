define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository'],
    function(log, navRegister, ko, abstractRepository) {

        return {
            lees: function(id) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('LEES_VERSIE') + '/' + id);
            }
        }
    }
);