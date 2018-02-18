define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository',
        'repository/versies-repository'],
    function(log, navRegister, ko, repository, versiesRepository) {

        return {
            lees: function(identificatie){
                var deferred = $.Deferred();

                $.when(versiesRepository.lees(identificatie)).then(function(data) {
                    return deferred.resolve(data);
                });

                return deferred.promise();
            }
        }
    }
);