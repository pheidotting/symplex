define(['jquery',
        'commons/3rdparty/log2',
        'text!../../../templates/commons/telefoonnummers.html'],
    function($, log, html) {
        var logger = log.getLogger('telefoonnummer-view');

        return {
            init: function(id) {
				$('#telefoonnummers').html(html);

                logger.debug('content geladen, viewmodel init');
            }
        }
    }
);
