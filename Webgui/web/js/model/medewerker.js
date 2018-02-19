define(['jquery',
         'knockout'],
	function ($, ko) {

	return function(data){
	    var _this = this;

        _this.identificatie = ko.observable();
        _this.voornaam = ko.observable();
        _this.tussenvoegsel = ko.observable();
        _this.achternaam = ko.observable();
        _this.emailadres = ko.observable();

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