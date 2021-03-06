define(['jquery',
        'commons/3rdparty/log',
        'text!../../../templates/beheren/beheren-relatie.html',
        'viewmodel/beheren-relatie-viewmodel',
        'knockout',
        'view/common/adres-view',
        'view/common/rekeningnummer-view',
        'view/common/telefoonnummer-view',
        'view/common/opmerking-view',
        'view/common/bijlage-view',
        'view/common/telefonie-view',
        'view/common/taak-view',
        'view/common/breadcrumbs-view'],
    function ($, log, html, viewmodel, ko, adresView, rekeningnummerView, telefoonnummerView, opmerkingView, bijlageView, telefonieView, taakView, breadcrumbsView) {
        var logger = log.getLogger('beheren-relatie-view');

        return {
            init: function (id) {
                $('#content').html(html);

                adresView.init(id);
                rekeningnummerView.init(id);
                telefoonnummerView.init(id);
                opmerkingView.init(id);
                bijlageView.init(id);
                telefonieView.init();
                taakView.init();
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
