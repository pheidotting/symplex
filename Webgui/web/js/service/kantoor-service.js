define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository',
        'repository/kantoor-repository'],
    function(log, navRegister, ko, repository, kantoorRepository) {

        return {
            aanmelden: function(kantoor) {
                var deferred = $.Deferred();

                kantoorRepository.aanmelden(kantoor).done(function(){
                    return deferred.resolve();
                });

                return deferred.promise();
            },

            lees: function() {
                return kantoorRepository.lees();
            }
        }
});