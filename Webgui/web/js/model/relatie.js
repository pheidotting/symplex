define(['knockout'],
    function (ko) {

        return function () {
            var _this = this;

            _this.identificatie = ko.observable();
            _this.id = ko.observable();
            _this.soortEntiteit = ko.observable('RELATIE');
            _this.voornaam = ko.observable().extend({required: true});
            _this.roepnaam = ko.observable();
            _this.tussenvoegsel = ko.observable();
            _this.achternaam = ko.observable().extend({required: true});
            _this.geboorteDatum = ko.observable();
            _this.overlijdensdatum = ko.observable();
            _this.geslacht = ko.observable();
            _this.burgerlijkeStaat = ko.observable();
            _this.emailadres = ko.observable();
        };
    });