define(['commons/3rdparty/log',
        'knockout',
        'model/taak',
        'service/kantoor-service',
        'mapper/medewerker-mapper',
        'mapper/taak-mapper',
        'moment',
        'navRegister',
        'lodash'],
    function (log, ko, Taak, kantoorService, medewerkerMapper, taakMapper, moment, navRegister, _) {

        return function (t) {
            var _this = this;

            $.when(kantoorService.lees()).then(function (kantoor) {
                 var medewerkers = medewerkerMapper.mapMedewerkers(kantoor.medewerkers);

                medewerkers = _.sortBy(medewerkers(), function (m) {
                    return m.achternaam() + m.voornaam();
                });

                var $select = $('#toewijzenAan');
                $('<option>', {value: ''}).text('Toewijzen aan...').appendTo($select);

                $.each(medewerkers, function (key, medewerker) {
                    $('<option>', {value: medewerker.identificatie()}).text(medewerker.volledigenaam()).appendTo($select);
                });
            });

            _this.taken = taakMapper.mapTaken(t, false);
            _this.afgerondeTaken = taakMapper.mapTaken(t, true);

            _this.taken.push(new Taak());

            _this.exitNieuweTaakDeadline = function(taak){
                if(taak.deadline() != null && taak.deadline() != ''){
                    var invoerPattern = 'DD-MM-YYYY HH:mm';

                    taak.deadline(moment(taak.deadline()).format(invoerPattern));
                }
            };
        };
    });