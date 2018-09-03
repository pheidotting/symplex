define(['knockout'],
    function (ko) {

        return function Polis() {
            var _this = this;

            _this.identificatie = ko.observable();
            _this.entiteitId = ko.observable();
            _this.soortEntiteit = ko.observable();
            _this.status = ko.observable();
            _this.polisNummer = ko.observable().extend({required: true});
            _this.kenmerk = ko.observable();
            _this.ingangsDatum = ko.observable().extend({required: true});
            _this.eindDatum = ko.observable();
            _this.wijzigingsDatum = ko.observable();
            _this.prolongatieDatum = ko.observable();
            _this.maatschappij = ko.observable();//.extend({required: true});
            _this.soort = ko.observable().extend({required: true});
            _this.premie = ko.observable();
            _this.betaalfrequentie = ko.observable();
            _this.dekking = ko.observable();
            _this.verzekerdeZaak = ko.observable();
            _this.bedrijf = ko.observable();
            _this.bedrijfsId = ko.observable();
            _this.omschrijvingVerzekering = ko.observable();
            _this.titel = ko.computed(function () {
                return _this.soort() + " (" + _this.polisNummer() + ")";
            }, this);

            _this.voertuiginfo = ko.observable(false);
            _this.merk = ko.observable();
            _this.type = ko.observable();
            _this.bouwjaar = ko.observable();
            _this.voertuigImage1 = ko.observable();
            _this.voertuigImage2 = ko.observable();
            _this.voertuigImage3 = ko.observable();

        };
    });