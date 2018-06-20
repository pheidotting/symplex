define(['jquery',
        'knockout'],
    function ($, ko) {

        return function wijzigingtaak() {
            var _this = this;

            _this.identificatie = ko.observable();
            _this.taakStatus = ko.observable();
            _this.toegewezenAan = ko.observable();
            _this.tijdstip = ko.observable();
        };
    });