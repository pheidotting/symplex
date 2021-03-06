define(['jquery',
        'commons/3rdparty/log',
        'text!../../../templates/beheren/beheren-belastingzaken.html',
        'viewmodel/beheren-belastingzaken-viewmodel',
        'knockout',
        'view/common/opmerking-view',
        'view/common/bijlage-view',
        'view/common/breadcrumbs-view'],
    function ($, log, html, viewmodel, ko, opmerkingView, bijlageView, breadcrumbsView) {
        var logger = log.getLogger('beheren-relatie-view');

        return {
            init: function (id) {
                $('#content').html(html);

                opmerkingView.init(id);
                bijlageView.init(id);
                breadcrumbsView.init();

                logger.debug('content geladen, viewmodel init');

                var v = new viewmodel(id);
                $.when(v.init(id)).then(function () {
                    ko.applyBindings(v);
                });
            }
        }
    }
);
