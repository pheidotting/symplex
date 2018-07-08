define(['jquery',
        'commons/3rdparty/log',
        'text!/../../templates/taken/taak.html',
        'text!/../../templates/commons/header.html',
        'viewmodel/taak-viewmodel',
        'knockout'],
    function ($, log, html, headerHtml, viewmodel, ko) {
        var logger = log.getLogger('taken-view');

        return {
            init: function (identificatie) {
                $('#hoofd').html(headerHtml);
                $('#content').html(html);

                logger.debug('content geladen, viewmodel init');

                var v = new viewmodel();
                $.when(v.init(identificatie)).then(function () {
                    ko.applyBindings(v);
                });
            }
        }
    }
);
