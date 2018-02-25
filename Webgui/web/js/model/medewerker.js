define(['knockout',
        'commons/validation'],
	function (ko, validation) {

	return function(){
	    var _this = this;

        _this.identificatie = ko.observable();
        _this.voornaam = ko.observable().extend({required: true});
        _this.tussenvoegsel = ko.observable();
        _this.achternaam = ko.observable().extend({required: true});
        _this.emailadres = ko.observable().extend({required: true});

        this.volledigenaam = function() {
            var naam = _this.voornaam();
            if(_this.tussenvoegsel() != null && _this.tussenvoegsel() != ''){
                naam += ' ' + _this.tussenvoegsel();
            }
            naam += ' '+_this.achternaam();
            return naam
        };
    };
});