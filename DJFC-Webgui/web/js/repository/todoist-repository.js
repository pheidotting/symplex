define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository'],
    function(log, navRegister, ko, abstractRepository) {

        return {
            oauthToken: function(data) {
                return abstractRepository.voerUitPost(navRegister.bepaalUrl('TODOIST_OAUTHTOKEN'), ko.toJSON(data));
            },

            prefix: function(id) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('TODOIST_PREFIX'));
            },

            clientIdEnClientSecret: function(id) {
                return abstractRepository.voerUitGet(navRegister.bepaalUrl('TODOIST_CLIENT_ID_EN_SECRET'));
            }
        }
    }
);