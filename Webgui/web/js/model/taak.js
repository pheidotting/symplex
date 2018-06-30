define(['jquery',
        'knockout',
        'underscore'],
    function ($, ko, _) {

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
            _this.wijzigingTaaks = ko.observableArray([]);
            _this.toegewezenAan = ko.observable();
            _this.status = ko.observable();

            _this.berekenToegewezenAanEnStatus = function() {
                var wt = _.chain(_this.wijzigingTaaks())
                .sortBy('tijdstip')
                .filter(function(wijzigingTaak) {
                    return wijzigingTaak.toegewezenAan() != null;
                })
//                .map(function(wijzigingTaak) {
//                    return wijzigingTaak.toegewezenAan();
//                })
                .last()
                .value();

                if(wt != null){
                    _this.toegewezenAan(wt.toegewezenAan());

                    var status = wt.taakStatus();

                    _this.status(status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase());
                }
            }

        };
    });