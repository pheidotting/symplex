define(['jquery',
        'commons/3rdparty/log',
        'text!/../../templates/dashboard/dashboard.html',
        'text!/../../templates/commons/header.html',
        'viewmodel/dashboard-viewmodel',
        'knockout'],
    function ($, log, html, headerHtml, viewmodel, ko) {
        var logger = log.getLogger('dashboard-view');

        return {
            init: function () {
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
