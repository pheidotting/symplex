define(['commons/3rdparty/log',
        'navRegister',
        'knockout',
        'repository/common/repository',
        'repository/common/licentie-repository'],
    function(log, navRegister, ko, repository, licentieRepository) {
        return {
            einddatum: function() {
                return licentieRepository.einddatum();
            }
        }
    }
);