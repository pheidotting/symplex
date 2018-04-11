define(['knockout'],
    function (ko) {

        return function groepBijlagesModel() {
            var _this = this;

            _this.id = ko.observable();
            _this.naam = ko.observable();
            _this.bijlages = ko.observableArray();
        };
    });