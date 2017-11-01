define(['commons/3rdparty/log2',
        'service/taak/taak-service',
        'service/toggle-service',
        'mapper/taak-mapper',
        'model/taak/taak2',
        'knockout',
        'moment',
        'commons/commonFunctions'],
    function(log, taakService, toggleService, taakMapper, Taak, ko, moment, commonFunctions) {

    return function(readOnly, soortEntiteit, entiteitId, relatieId, bedrijfId) {
        var _this = this;
        var logger = log.getLogger('taak-viewmodel');

		this.id = ko.observable(entiteitId);
		this.soortEntiteit = ko.observable(soortEntiteit);
		this.relatieId = relatieId;
		this.bedrijfId = bedrijfId;

        this.taken = ko.observableArray();
        this.afgerondeTaken = ko.observableArray();

        this.nieuweTaakTekst = ko.observable();
        this.nieuweTaakReminder = ko.observable().extend({validation: {
            validator: function (val) {
                if(val) {
                    if(moment(val, "DD-MM-YYYY HH:mm").format("DD-MM-YYYY HH:mm") == "Invalid date"){
                        return false;
                    }else{
                        return true;
                    }
                } else {
                    return true;
                }
            },
            message: 'Juiste invoerformaat is : dd-mm-eejj uu:mm'
        }});

//        $.when(toggleService.isFeatureBeschikbaar('TODOIST')).then(function(toggleBeschikbaar){
//            if(toggleBeschikbaar) {
//                $.when(
//                    taakService.alleTaken(_this.soortEntiteit(), _this.id(), _this.relatieId, _this.bedrijfId),
//                    taakService.afgerondeTaken(_this.soortEntiteit(), _this.id())
//                ).then(function(
//                    opgehaaldeTaken,
//                    afgerondeTaken
//                ) {
//                    _.each(taakMapper.mapTaken(opgehaaldeTaken)(), function(gemapteTaak) {
//                        _this.taken.push(gemapteTaak);
//                        _this.taken.valueHasMutated()
//                    });
//                    _.each(taakMapper.mapTaken(afgerondeTaken)(), function(gemapteTaak) {
//                        _this.afgerondeTaken.push(gemapteTaak);
//                        _this.afgerondeTaken.valueHasMutated()
//                    });
//                });
//            }
//        });

        this.voegTaakToe = function() {
            logger.debug('Toevoegen taak');
            if(_this.nieuweTaakReminder.isValid() && _this.nieuweTaakTekst() != null && _this.nieuweTaakTekst() != '') {
                $.when(taakService.voegTaakToe(_this.soortEntiteit(), _this.id(), _this.nieuweTaakTekst(), _this.nieuweTaakReminder(), _this.relatieId, _this.bedrijfId)).then(function(itemId){
                    var taak = new Taak();
                    taak.omschrijving(_this.nieuweTaakTekst());
                    taak.projectId(itemId);
                    taak.reminder(_this.nieuweTaakReminder());
                    _this.taken.push(taak);
                    _this.nieuweTaakTekst('');
                    _this.nieuweTaakReminder('');
                });
            }
        };

        this.hideOrDisplay = function() {
            if ($('#zelfToevoegen').is(':visible')) {
                $('#zelfToevoegen').hide();
            } else {
                $('#zelfToevoegen').show();
            }
        };

        this.klapOpenOfDicht = function(taak) {
            if($('#' + taak.id()).is(':visible')) {
                $('#' + taak.id()).hide();
                $('#' + taak.id() + '-hide').hide();
                $('#' + taak.id() + '-show').show();
            } else {
                $('#' + taak.id()).show();
                $('#' + taak.id() + '-hide').show();
                $('#' + taak.id() + '-show').hide();
            }
        };

        exitNieuweTaakReminder = function() {
		    _this.nieuweTaakReminder(zetDatumTijdOm(_this.nieuweTaakReminder()));
        };

        zetDatumTijdOm = function(d) {
            return commonFunctions.zetDatumTijdOm(d);
        };

	};
});