define(['jquery',
        'commons/3rdparty/log2',
        'viewmodel/lijst-bedrijven-viewmodel',
        'knockout',
        'text!../../../templates/lijstBedrijven.html'],
    function($, log, viewmodel, ko, html) {

        var logger = log.getLogger('lijst-bedrijven-view');

        return {
            init: function(zoekTerm) {
				$('#content').html(html);

                logger.debug('content geladen, viewmodel init');

                var v = new viewmodel(zoekTerm);
                ko.applyBindings(v);
            }
        }
    }
);
