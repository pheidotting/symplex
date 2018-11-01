define(['jquery',
        'commons/3rdparty/log',
        'text!../../../templates/commons/breadcrumbs.html'],
    function ($, log, html) {
        var logger = log.getLogger('breadcrumbs-view');

        return {
            init: function () {
                $('#breadcrumbs').html(html);

                logger.debug('content geladen, viewmodel init');
            }
        }
    }
);
