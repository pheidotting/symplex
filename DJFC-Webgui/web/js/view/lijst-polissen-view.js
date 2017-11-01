define(['jquery',
        'commons/3rdparty/log2',
        'text!../../../templates/beheren/lijst-polissen.html',
        'viewmodel/lijst-polissen-viewmodel',
        'knockout'],
    function($, log, html, viewmodel, ko) {
        var logger = log.getLogger('lijst-polissen-view');

        return {
            init: function(id, basisEntiteit) {
				$('#content').html(html);

                logger.debug('content geladen, viewmodel init');

                var v = new viewmodel();
                $.when(v.init(id, basisEntiteit)).then(function(){
                    ko.applyBindings(v);
                });
            }
        }
    }
);
