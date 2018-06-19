define(['jquery',
        'knockout'],
    function ($, ko) {

        return function taak() {
            var _this = this;

            _this.identificatie = ko.observable();
            _this.tijdstipCreatie = ko.observable();
            _this.deadline = ko.observable();
            _this.tijdstipAfgehandeld = ko.observable();
            _this.titel = ko.observable();
            _this.omschrijving = ko.observable();
            _this.entiteitId = ko.observable();
            _this.soortEntiteit = ko.observable();
            _this.toegewezenAan = ko.observable();
            _this.wijzigingTaaks = ko.observableArray([]);

        };
    });