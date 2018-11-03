define(['jquery',
        'commons/3rdparty/log',
        'text!../../../templates/beheren/beheren-hypotheek.html',
        'viewmodel/beheren-hypotheek-viewmodel',
        'knockout',
        'view/common/opmerking-view',
        'view/common/bijlage-view',
        'view/common/taak-view',
        'view/common/breadcrumbs-view'],
    function ($, log, html, viewmodel, ko, opmerkingView, bijlageView, taakView, breadcrumbsView) {
        var logger = log.getLogger('beheren-hypotheek-view');

        return {
            init: function (hypotheekId, basisId, readOnly, basisEntiteit) {
                $('#content').html(html);

                opmerkingView.init(hypotheekId);
                bijlageView.init(hypotheekId);
                taakView.init();
                breadcrumbsView.init();

                logger.debug('content geladen, viewmodel init');

                var v = new viewmodel();
                $.when(v.init(hypotheekId, basisId, readOnly, basisEntiteit)).then(function () {
                    ko.applyBindings(v);
                });
            }
        }
    }
);
