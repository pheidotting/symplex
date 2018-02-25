define(['jquery',
        'commons/3rdparty/log',
        'text!../../../templates/commons/rekeningnummers.html'],
    function($, log, html) {
        var logger = log.getLogger('rekeningnummer-view');

        return {
            init: function(id) {
				$('#rekeningnummers').html(html);

                logger.debug('content geladen, viewmodel init');
            }
        }
    }
);
