define(['jquery',
        'knockout'],
    function($, ko) {

    return function() {
        var _this = this;

        _this.naam = ko.observable();
        _this.geboortedatum = ko.observable();
        _this.tussenvoegsel = ko.observable();
        _this.polisnummer = ko.observable();
        _this.voorletters = ko.observable();
        _this.schadenummer = ko.observable();
        _this.adres = ko.observable();
        _this.postcode = ko.observable();
        _this.woonplaats = ko.observable();
        _this.bedrijf = ko.observable();
    }
});
