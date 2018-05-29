define(['knockout',
        'moment'],
    function (ko, moment) {

        return function schadeModel() {
            var _this = this;

            _this.id = ko.observable();
            _this.soortEntiteit = ko.observable();
            _this.identificatie = ko.observable();
            _this.relatie = ko.observable();
            _this.bedrijf = ko.observable();
            _this.polis = ko.observable().extend({
                validation: {
                    validator: function () {
                        if (ko.utils.unwrapObservable(_this.polis) == "Kies een polis uit de lijst..") {
                            return !_this.schadeNummerMaatschappij.isValid();
                        } else {
                            return true;
                        }
                    },
                    message: 'Dit veld is verplicht.'
                }
            });
            _this.schadeNummerMaatschappij = ko.observable().extend({required: true});
            _this.schadeNummerTussenPersoon = ko.observable();
            _this.soortSchade = ko.observable().extend({required: true});
            _this.locatie = ko.observable();
            _this.statusSchade = ko.observable().extend({
                validation: {
                    validator: function () {
                        if (ko.utils.unwrapObservable(_this.statusSchade) == "Kies een status uit de lijst..") {
                            return !_this.schadeNummerMaatschappij.isValid();
                        } else {
                            return true;
                        }
                    },
                    message: 'Dit veld is verplicht.'
                }
            });
            _this.datumSchade = ko.observable().extend({
                required: true, validation: {
                    validator: function (val) {
                        return moment(val, "DD-MM-YYYY").format("DD-MM-YYYY") != "Invalid date";
                    },
                    message: 'Juiste invoerformaat is : dd-mm-eejj'
                }
            });
            _this.datumMelding = ko.observable().extend({
                required: true, validation: {
                    validator: function (val) {
                        return moment(val, "DD-MM-YYYY").format("DD-MM-YYYY") != "Invalid date";
                    },
                    message: 'Juiste invoerformaat is : dd-mm-eejj'
                }
            });
            _this.datumAfgehandeld = ko.observable().extend({
                validation: {
                    validator: function (val) {
                        if (val != undefined && val != "") {
                            return moment(val, "DD-MM-YYYY").format("DD-MM-YYYY") != "Invalid date";
                        } else {
                            return true;
                        }
                    },
                    message: 'Juiste invoerformaat is : dd-mm-eejj'
                }
            });
            _this.eigenRisico = ko.observable();
            _this.omschrijving = ko.observable();
        };
    });