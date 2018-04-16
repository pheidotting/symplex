define(['jquery',
        'commons/3rdparty/log',
        'text!../../../templates/commons/fileUpload.html'],
    function ($, log, html) {
        var logger = log.getLogger('adres-view');

        return {
            init: function () {
                $('#bijlages').html(html);

                logger.debug('content geladen, viewmodel init');
            }
        }
    }
);
