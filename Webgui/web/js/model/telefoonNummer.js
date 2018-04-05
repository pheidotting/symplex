define(['knockout'],
    function (ko) {

        return function () {
            var _this = this;

            _this.id = ko.observable();
            _this.telefoonnummer = ko.observable();
            _this.soort = ko.observable();
            _this.omschrijving = ko.observable();
            _this.soortEntiteit = ko.observable();
            _this.entiteitId = ko.observable();
            _this.parentIdentificatie = ko.observable();
            _this.identificatie = ko.observable();
        };
    });
