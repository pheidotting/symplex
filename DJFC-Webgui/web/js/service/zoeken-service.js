define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository'],
    function(log, navRegister, ko, abstractRepository) {

        return {
            zoeken: function(zoekTerm, idWeglaten) {
                var promise = $.Deferred();

                var vars = '';
                if(zoekTerm) {
                    vars = '/' + zoekTerm;
                }
                if(idWeglaten) {
                    vars = vars + '/' + idWeglaten;
                }

                abstractRepository.voerUitGet(navRegister.bepaalUrl('ZOEKEN') + vars).done(function(result) {
                    return promise.resolve(result);
                });

                return promise.promise();
            }
        }
    }
);