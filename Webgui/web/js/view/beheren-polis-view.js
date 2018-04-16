define(['jquery',
        'commons/3rdparty/log',
        'text!../../../templates/beheren/beheren-polis.html',
        'viewmodel/beheren-polis-viewmodel',
        'knockout',
        'view/common/opmerking-view',
        'view/common/bijlage-view'],
    function ($, log, html, viewmodel, ko, opmerkingView, bijlageView) {
        var logger = log.getLogger('beheren-polis-view');

        return {
            init: function (polisId, basisId, readOnly, basisEntiteit) {
                $('#content').html(html);

                opmerkingView.init(polisId);
                bijlageView.init(polisId);

                logger.debug('content geladen, viewmodel init');

                var v = new viewmodel();
                $.when(v.init(polisId, basisId, readOnly, basisEntiteit)).then(function () {
                    ko.applyBindings(v);
                });
            }
        }
    }
);
