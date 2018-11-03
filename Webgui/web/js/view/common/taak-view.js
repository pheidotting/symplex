define(['jquery',
        'commons/3rdparty/log',
        'service/toggle-service',
        'text!../../../templates/commons/taken.html'],
    function ($, log, toggleService, html) {
        var logger = log.getLogger('taken-view');

        return {
            init: function () {
                $('#taken').html(html);

                logger.debug('content geladen');
            }
        }
    }
);
