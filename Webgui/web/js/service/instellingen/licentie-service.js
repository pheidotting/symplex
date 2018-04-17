define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository',
        'repository/instellingen/licentie-repository'],
    function (log, navRegister, ko, repository, licentieRepository) {

        return {
            licentieKopen: function (licentie) {
                var deferred = $.Deferred();

                licentieRepository.licentieKopen(licentie).done(function () {
                    return deferred.resolve();
                });

                return deferred.promise();
            }
        }
    });