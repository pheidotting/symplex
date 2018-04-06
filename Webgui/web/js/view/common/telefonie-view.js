define(['jquery',
        'commons/3rdparty/log',
        'service/toggle-service',
        'text!../../../templates/commons/telefonie.html'],
    function ($, log, toggleService, html) {
        var logger = log.getLogger('telefonie-view');

        return {
            init: function () {
                $('#telefonie').html(html);

                logger.debug('content geladen');
            }
        }
    }
);
