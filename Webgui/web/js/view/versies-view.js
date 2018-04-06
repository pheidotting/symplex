define(['jquery',
        'commons/3rdparty/log',
        'text!../../../templates/versies/versies.html',
        'viewmodel/versies-viewmodel',
        'knockout'],
    function ($, log, html, viewmodel, ko) {
        var logger = log.getLogger('versies-view');

        return {
            init: function (identificatie) {
                $('#content').html(html);

                logger.debug('content geladen, viewmodel init');

                var v = new viewmodel();
                $.when(v.init(identificatie.identificatie)).then(function () {
                    ko.applyBindings(v);
                });
            }
        }
    }
);
