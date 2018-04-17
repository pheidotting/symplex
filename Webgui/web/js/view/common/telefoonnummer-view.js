define(['jquery',
        'commons/3rdparty/log',
        'text!../../../templates/commons/telefoonnummers.html'],
    function ($, log, html) {
        var logger = log.getLogger('telefoonnummer-view');

        return {
            init: function () {
                $('#telefoonnummers').html(html);

                logger.debug('content geladen, viewmodel init');
            }
        }
    }
);
