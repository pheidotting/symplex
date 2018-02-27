define(['jquery',
        'commons/3rdparty/log',
        'text!/../../templates/aanmelden/aanmelden.html',
        'viewmodel/aanmelden-viewmodel',
        'knockout'],
    function($, log, html, viewmodel, ko) {
        var logger = log.getLogger('aanmelden-view');

        return {
            init: function() {
				$('#content').html(html);

                logger.debug('content geladen, viewmodel init');

                var v = new viewmodel();
//                $.when(v.init()).then(function(){
                    ko.applyBindings(v);
//                });
            }
        }
    }
);
