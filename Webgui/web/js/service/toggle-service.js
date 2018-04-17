define(['navRegister',
        'knockout',
        'repository/common/repository'],
    function (navRegister, ko, abstractRepository) {
        return {
            isFeatureBeschikbaar: function (toggle) {
                var togglePromise = $.Deferred();

                abstractRepository.voerUitGet(navRegister.bepaalUrl('TOGGLZ') + '/' + toggle).done(function (toggles) {
                    return togglePromise.resolve(toggles);
                });

                return togglePromise.promise();
            }
        }
    }
);