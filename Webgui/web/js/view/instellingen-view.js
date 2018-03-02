define(['jquery',
        'commons/3rdparty/log2',
        'text!../../../templates/instellingen/instellingen.html',
        'viewmodel/instellingen-viewmodel',
        'knockout'],
    function($, log, html, viewmodel, ko) {
        var logger = log.getLogger('instellingen-view');

        return {
            init: function() {
				$('#content').html(html);

                logger.debug('content geladen, viewmodel init');

                var v = new viewmodel();
                $.when(v.init()).then(function(){
                    ko.applyBindings(v);
                });
            }
        }
    }
);
