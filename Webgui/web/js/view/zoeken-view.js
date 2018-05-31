define(['jquery',
        'commons/3rdparty/log',
        'text!../../../templates/zoeken/zoeken.html',
        'viewmodel/zoeken-viewmodel',
        'knockout'],
    function ($, log, html, viewmodel, ko) {
        var logger = log.getLogger('zoeken-view');

        return {
            init: function (zoekvelden) {
                $('#content').html(html);

                logger.debug('content geladen, viewmodel init');

                var v = new viewmodel();
                $.when(v.init(zoekvelden)).then(function () {
                    ko.applyBindings(v);
                });
            }
        }
    }
);
