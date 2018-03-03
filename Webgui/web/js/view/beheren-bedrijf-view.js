define(['jquery',
        'commons/3rdparty/log',
        'text!../../../templates/beheren/beheren-bedrijf.html',
        'viewmodel/beheren-bedrijf-viewmodel',
        'knockout',
        'view/common/adres-view',
        'view/common/rekeningnummer-view',
        'view/common/telefoonnummer-view',
        'view/common/opmerking-view',
        'view/common/bijlage-view',
        'view/common/taak-view',
        'view/common/telefonie-view'],
    function($, log, html, viewmodel, ko, adresView, rekeningnummerView, telefoonnummerView, opmerkingView, bijlageView, taakView, telefonieView) {
        var logger = log.getLogger('beheren-relatie-view');

        return {
            init: function(id) {
				$('#content').html(html);

                adresView.init(id);
                rekeningnummerView.init(id);
                telefoonnummerView.init(id);
                opmerkingView.init(id);
                bijlageView.init(id);
                telefonieView.init();

                logger.debug('content geladen, viewmodel init');

                var v = new viewmodel(id);
                $.when(v.init(id)).then(function(){
                    ko.applyBindings(v);
                });
            }
        }
    }
);
