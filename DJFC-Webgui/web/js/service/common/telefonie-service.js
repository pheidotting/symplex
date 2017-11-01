define(['commons/3rdparty/log2',
        'navRegister',
        'knockout',
        'repository/common/repository',
        'repository/common/telefonie-repository'],
    function(log, navRegister, ko, repository, telefonieRepository) {
        return {
            haalOp: function(telefoonnummers) {
                return telefonieRepository.haalOp(telefoonnummers);
            }
        }
    }
);