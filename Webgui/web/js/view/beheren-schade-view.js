define(['jquery',
        'commons/3rdparty/log',
        'text!../../../templates/beheren/beheren-schade.html',
        'viewmodel/beheren-schade-viewmodel',
        'knockout',
        'view/common/opmerking-view',
        'view/common/bijlage-view',
        'view/common/breadcrumbs-view',
        'view/common/taak-view'],
    function ($, log, html, viewmodel, ko, opmerkingView, bijlageView, breadcrumbsView, taakView) {
        var logger = log.getLogger('beheren-schade-view');

        return {
            init: function (schadeId, basisId, readOnly, basisEntiteit) {
                $('#content').html(html);

                opmerkingView.init(schadeId);
                bijlageView.init(schadeId);
                taakView.init();
                breadcrumbsView.init();

                logger.debug('content geladen, viewmodel init');

                var v = new viewmodel();
                $.when(v.init(schadeId, basisId, readOnly, basisEntiteit)).then(function () {
                    ko.applyBindings(v);
                });
            }
        }
    }
);
