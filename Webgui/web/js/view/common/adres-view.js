define(['jquery',
        'commons/3rdparty/log',
        'text!../../../templates/commons/adressen.html'],
    function ($, log, html) {
        var logger = log.getLogger('adres-view');

        return {
            init: function () {
                $('#adressen').html(html);

                logger.debug('content geladen, viewmodel init');
            }
        }
    }
);
