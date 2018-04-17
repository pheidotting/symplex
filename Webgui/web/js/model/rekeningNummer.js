define(['knockout'],
    function (ko) {

        return function () {
            var _this = this;

            _this.id = ko.observable();
            _this.rekeningnummer = ko.observable(),
                _this.bic = ko.observable()
            _this.soortEntiteit = ko.observable();
            _this.entiteitId = ko.observable();

            _this.bicTonen = ko.observable(false);
            _this.parentIdentificatie = ko.observable();
            _this.identificatie = ko.observable();
        };
    });