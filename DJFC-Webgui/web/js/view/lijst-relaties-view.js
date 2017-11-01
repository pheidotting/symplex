define(['jquery',
        'commons/3rdparty/log',
        'commons/commonFunctions',
        'redirect',
        'viewmodel/lijst-relaties-viewmodel',
        'knockout'],
    function($, log, commonFunctions, redirect, viewmodel, ko) {

        return {
            init: function(zoekTerm) {
                $('#content').load('templates/lijstRelaties.html', function(response, status, xhr) {
                    log.debug('content geladen, viewmodel init');

                    var v = new viewmodel(zoekTerm);
                    ko.applyBindings(v);
                });
            }
        }
    }
);
