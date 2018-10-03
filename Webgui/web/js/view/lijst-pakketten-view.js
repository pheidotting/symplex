define(['jquery',
        'commons/3rdparty/log',
        'text!../../../templates/beheren/lijst-pakketten.html',
        'viewmodel/lijst-pakketten-viewmodel',
        'knockout'],
    function ($, log, html, viewmodel, ko) {
        var logger = log.getLogger('lijst-pakketten-view');

        return {
            init: function (id, basisEntiteit) {
                $('#content').html(html);

                logger.debug('content geladen, viewmodel init');

                var v = new viewmodel();
                $.when(v.init(id, basisEntiteit)).then(function () {
                    ko.applyBindings(v);
                });
            }
        }
    }
);