define(['commons/3rdparty/log',
        'model/opmerking',
        'knockout',
        'moment',
        'mapper/opmerking-mapper',
        'service/common/opmerking-service',
        'repository/gebruiker-repository'],
    function (log, Opmerking, ko, moment, opmerkingMapper, opmerkingService, gebruikerRepository) {

        return function (readOnly, soortEntiteit, entiteitId, opmerkingen) {
            var _this = this;
            var logger = log.getLogger('opmerking-viewmodel');

            this.readOnly = readOnly;
            this.opmerkingen = ko.observableArray();

            $.each(opmerkingMapper.mapOpmerkingen(opmerkingen)(), function (i, opmerking) {
                _this.opmerkingen.push(opmerking);
            });

            this.nieuweOpmerking = ko.observable();
            this.id = ko.observable(entiteitId);
            this.soortEntiteit = ko.observable(soortEntiteit);

            this.verwijder = function (opmerking) {
                var r = confirm("Weet je zeker dat je deze opmerking wilt verwijderen?");
                if (r === true) {
                    logger.debug("verwijder opmerking met id " + opmerking.id());

                    _this.opmerkingen.remove(opmerking);
                    _this.opmerkingen.valueHasMutated();

                    opmerkingService.verwijder(opmerking.identificatie());

                    location.reload();
                }
            };

            this.voegOpmerkingToe = function () {
                var base64Url = localStorage.getItem('symplexAccessToken').split('.')[1];
                var base64 = base64Url.replace('-', '+').replace('_', '/');
                var token = JSON.parse(window.atob(base64));

                $.when(gebruikerRepository.haalIngelogdeGebruiker(token.sub)).then(function (response) {
                    var opmerking = new Opmerking('');
                    opmerking.medewerker(response.gebruikersnaam);
                    opmerking.medewerkerId(response.id);
                    opmerking.tijd(new moment().format("DD-MM-YYYY HH:mm"));

                    _this.opmerkingen().push(opmerking);
                    _this.opmerkingen.valueHasMutated();
                    _this.nieuweOpmerking('');
                });
            };

            this.notReadOnly = ko.computed(function () {
                return !_this.readOnly;
            });

            this.readOnly = ko.computed(function () {
                return _this.readOnly;
            });
        };
    });