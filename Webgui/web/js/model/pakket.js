define(['knockout'],
    function (ko) {

        return function Pakket() {
            var _this = this;

            _this.identificatie = ko.observable();
            _this.entiteitId = ko.observable();
            _this.soortEntiteit = ko.observable();
            _this.maatschappij = ko.observable();
            _this.polisNummer = ko.observable().extend({required: true});
            _this.polissen = ko.observableArray([]);
        };
    });