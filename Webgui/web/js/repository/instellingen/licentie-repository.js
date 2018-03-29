define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository'],
    function(log, navRegister, ko, abstractRepository) {
        var logger = log.getLogger('licentie-repository');

        return {
            licentieKopen: function(data) {
                var url = navRegister.bepaalUrl('LICENTIE_KOPEN');

                return abstractRepository.voerUitPost(url, data);
            }
        }
    }
);