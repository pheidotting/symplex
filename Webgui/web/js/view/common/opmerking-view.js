define(['jquery',
        'commons/3rdparty/log',
        'text!../../../templates/commons/opmerkingen.html'],
    function ($, log, html) {
        var logger = log.getLogger('opmerking-view');

        return {
            init: function () {
                $('#opmerkingen').html(html);

                logger.debug('content geladen, viewmodel init');
            }
        }
    }
);
