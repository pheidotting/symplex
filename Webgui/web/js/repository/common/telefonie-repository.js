define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository'],
    function(log, navRegister, ko, abstractRepository) {

        return {
            haalOp: function(telefoonnummers) {
                var params = '';

                _.each(telefoonnummers, function(telefoonnummer){
                    if (params == '') {
                        params = '?telefoonnummers=' + telefoonnummer.telefoonnummer;
                    } else {
                        params = params + '&telefoonnummers=' + telefoonnummer.telefoonnummer;
                    }
                });

                return abstractRepository.voerUitGet(navRegister.bepaalUrl('TELEFONIE_RECORDINGS') + params);
            }
        }
    }
);