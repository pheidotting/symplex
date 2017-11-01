define(['jquery',
        'commons/3rdparty/log2',
        'text!../../../templates/commons/opmerkingen.html'],
    function($, log, html) {
        var logger = log.getLogger('opmerking-view');

        return {
            init: function(id) {
				$('#opmerkingen').html(html);

                logger.debug('content geladen, viewmodel init');
            }
        }
    }
);
