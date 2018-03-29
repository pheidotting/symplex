define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository',
        'repository/instellingen/licentie-repository',
        'underscore'],
    function(log, navRegister, ko, repository, licentieRepository, _) {

        return {
            licentieKopen: function(licentie) {
                var deferred = $.Deferred();

                licentieRepository.licentieKopen(licentie).done(function(response) {
                    return deferred.resolve();
                });

                return deferred.promise();
        }
    }
});