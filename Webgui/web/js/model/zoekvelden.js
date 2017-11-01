define(['jquery',
        'knockout'],
    function($, ko) {

    return function() {
        var _this = this;

        this.naam = ko.observable();
        this.geboortedatum = ko.observable();
        this.tussenvoegsel = ko.observable();
        this.polisnummer = ko.observable();
        this.voorletters = ko.observable();
        this.schadenummer = ko.observable();
        this.adres = ko.observable();
        this.postcode = ko.observable();
        this.woonplaats = ko.observable();
        this.bedrijf = ko.observable();
    }
});
