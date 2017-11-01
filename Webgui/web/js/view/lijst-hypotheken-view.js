define(['jquery',
        'commons/3rdparty/log2',
        'text!../../../templates/beherenRelatiehypotheken.html',
        'viewmodel/lijst-hypotheken-viewmodel',
        'knockout'],
    function($, log, html, viewmodel, ko) {
        var logger = log.getLogger('lijst-hypotheken-view');

        return {
            init: function(id, basisEntiteit) {
				$('#details').html(html);

                logger.debug('content geladen, viewmodel init');

                var v = new viewmodel();
                $.when(v.init(id, basisEntiteit)).then(function(){
                    ko.applyBindings(v);
                });
            }
        }
    }
);
