define(["commons/3rdparty/log",
        'commons/commonFunctions',
        'model/opmerking',
        'navRegister',
        'knockout',
        'dataServices',
        'moment'],
    function(log, commonFunctions, Opmerking, navRegister, ko, dataServices, moment) {

        return function(data, schade, hypotheek, polis, relatie, bedrijf, aangifte, jaarcijfers, risicoAnalyse){
            log.debug(schade + " - " + hypotheek + " - " + polis + " - " + relatie + " - " + bedrijf + " - " + aangifte + " - " + jaarcijfers + " - " + risicoAnalyse);

            var _k = this;

            _k.opmerkingen = ko.observableArray();
            _k.nieuweOpmerking = ko.observable();
            _k.schade = ko.observable(schade);
            if(schade) {
                _k.soort = ko.observable('SCHADE');
                _k.entiteitId = ko.observable(schade);
            }
            if(hypotheek) {
                _k.soort = ko.observable('HYPOTHEEK');
                _k.entiteitId = ko.observable(hypotheek);
            }
            if(polis) {
                _k.soort = ko.observable('POLIS');
                _k.entiteitId = ko.observable(polis);
            }
            if(relatie) {
                _k.soort = ko.observable('RELATIE');
                _k.entiteitId = ko.observable(relatie);
            }
            if(bedrijf) {
                _k.soort = ko.observable('BEDRIJF');
                _k.entiteitId = ko.observable(bedrijf);
            }
            if(aangifte) {
                _k.soort = ko.observable('AANGIFTE');
                _k.entiteitId = ko.observable(aangifte);
            }
            if(jaarcijfers) {
                _k.soort = ko.observable('JAARCIJFERS');
                _k.entiteitId = ko.observable(jaarcijfers);
            }
            if(risicoAnalyse) {
                _k.soort = ko.observable('RISICOANALYSE');
                _k.entiteitId = ko.observable(risicoAnalyse);
            }

            if(data != null){
                $.each(data, function(i, item) {
                    _k.opmerkingen().push(new Opmerking(item));
                });
            }

            _k.opmerkingOpslaan = function(opm){
                log.debug(opm.opmerkingenModel.nieuweOpmerking());
                if(opm.opmerkingenModel.nieuweOpmerking() !== undefined && opm.opmerkingenModel.nieuweOpmerking().trim() !== ''){
                    $.when(dataServices.haalIngelogdeGebruiker()).then(function(response){
                        var opmerking = new Opmerking('');
                        opmerking.medewerker(response.gebruikersnaam);
                        opmerking.medewerkerId(response.id);
                        opmerking.tijd(new moment().format("DD-MM-YYYY HH:mm"));
                        opmerking.opmerking(opm.opmerkingenModel.nieuweOpmerking());

                        opmerking.soortEntiteit(opm.opmerkingenModel.soort());
                        opmerking.entiteitId(opm.opmerkingenModel.entiteitId());

                        var opmerkingen = [];
                        opmerkingen.push(opmerking)

                        log.debug(ko.toJSON(opmerkingen));

                        opmerking.id(id);
                        opm.opmerkingenModel.opmerkingen().push(opmerking);
                        opm.opmerkingenModel.opmerkingen.valueHasMutated();
                        opm.opmerkingenModel.nieuweOpmerking('');
                    });
                }
            };

            _k.verwijder = function(opmerking){
                var r = confirm("Weet je zeker dat je deze opmerking wilt verwijderen?");
                if (r === true) {
                    log.debug("verwijder opmerking met id " + opmerking.id());

                    _k.opmerkingen.remove(opmerking);
                    _k.opmerkingen.valueHasMutated();
                }
            };

            _k.voegOpmerkingToe = function() {
                $.when(dataServices.haalIngelogdeGebruiker()).then(function(response){
                    var opmerking = new Opmerking('');
                    opmerking.medewerker(response.gebruikersnaam);
                    opmerking.medewerkerId(response.id);
                    opmerking.tijd(new moment().format("DD-MM-YYYY HH:mm"));

                    _k.opmerkingen().push(opmerking);
                    _k.opmerkingen.valueHasMutated();
                    _k.nieuweOpmerking('');
                });
            }

        }
    }
);
