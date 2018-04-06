define(['jquery',
        'commons/3rdparty/log',
        'text!/../../templates/mijn-gegevens.html',
        'text!/../../templates/commons/header.html',
        'viewmodel/mijn-gegevens-viewmodel',
        'knockout'],
    function ($, log, html, headerHtml, viewmodel, ko) {
        var logger = log.getLogger('mijn-gegevens-view');

        return {
            init: function () {
                logger.debug('Beheerpagina laden');
                $('#hoofd').html(headerHtml);
                $('#content').html(html);

                logger.debug('content geladen, viewmodel init');

                var v = new viewmodel();
                $.when(v.init()).then(function () {
                    ko.applyBindings(v);
                });
            }
        }
    }
);
