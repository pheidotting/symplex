define(['commons/3rdparty/log2',
        'model/opmerking',
        'knockout',
        'dataServices',
        'moment',
        'mapper/opmerking-mapper',
        'lodash'],
    function(log, Opmerking, ko, dataServices, moment, opmerkingMapper, _) {

    return function(readOnly, soortEntiteit, entiteitId, opmerkingen) {
        var _this = this;
        var logger = log.getLogger('opmerking-viewmodel');

        this.readOnly = readOnly;
		this.opmerkingen = ko.observableArray();

        var opm = [];

        _.chain(opmerkingMapper.mapOpmerkingen(opmerkingen)())
            .orderBy('tijd')
            .flatten()
            .map(function(g) {
                g.maand = moment(g.tijd(), 'DD-MM-YYYY HH:mm').format('YYYY-MM');
                return g;
            })
            .map(function(g) {
                return g;
            })
            .groupBy(function(g) {
                return g.maand;
            })
            .each(function(g) {
                opm.push({'maand':g[0].maand, 'opmerkingen':g});
            })
            .value();
        $.each(opm, function(i, opmerking){
            _this.opmerkingen.push(opmerking);
        });

		this.nieuweOpmerking = ko.observable();
		this.id = ko.observable(entiteitId);
		this.soortEntiteit = ko.observable(soortEntiteit);

        this.verwijder = function(opmerking){
            var r = confirm("Weet je zeker dat je deze opmerking wilt verwijderen?");
            if (r === true) {
                logger.debug("verwijder opmerking met id " + opmerking.id());

                _this.opmerkingen.remove(opmerking);
                _this.opmerkingen.valueHasMutated();
            }
        };

        this.voegOpmerkingToe = function() {
            $.when(dataServices.haalIngelogdeGebruiker()).then(function(response){
                var opmerking = new Opmerking('');
                opmerking.medewerker(response.gebruikersnaam);
                opmerking.medewerkerId(response.id);
                opmerking.tijd(new moment().format("DD-MM-YYYY HH:mm"));

                _this.opmerkingen().push(opmerking);
                _this.opmerkingen.valueHasMutated();
                _this.nieuweOpmerking('');
            });
        };

        this.notReadOnly = ko.computed(function() {
            return !_this.readOnly;
        });

        this.readOnly = ko.computed(function() {
            return _this.readOnly;
        });

        this.opslaan = function() {};
        this.annuleren = function() {};

        this.toonOfVerberg = function(a) {
            if($('#opmerkingen'+a.maand).is(':visible')) {
                $('#opmerkingen'+a.maand).hide();
                $('#opmerkingen'+a.maand+'dicht').hide();
                $('#opmerkingen'+a.maand+'open').show();
            } else {
                $('#opmerkingen'+a.maand).show();
                $('#opmerkingen'+a.maand+'dicht').show();
                $('#opmerkingen'+a.maand+'open').hide();
            }
        };

        this.startBewerken = function(telefoonnummer){
//            if(telefoonnummer.telefoonnummer()){
//                telefoonnummer.telefoonnummer(telefoonnummer.telefoonnummer().replace(/ /g, "").replace("-", ""));
//            }
        };

//        this.stopBewerken = function(telefoonnummer){
//            _this.zetTelefoonnummerOm(telefoonnummer);
//        };


	};
});