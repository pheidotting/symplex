define(['jquery',
         'knockout',
         'commons/3rdparty/log',
         'commons/commonFunctions',
         'dataServices',
         'model/bijlage'],
	function ($, ko, log, commonFunctions, dataServices, Bijlage) {

	return function groepBijlagesModel (data) {
		_groep = this;

		_groep.id = ko.observable(data.id);
		_groep.naam = ko.observable(data.naam);
        _groep.bijlages = ko.observableArray();
        if(data.bijlages != null) {
            $.each(data.bijlages, function(i, item) {
                var bijlage = new Bijlage(item);
                _groep.bijlages().push(bijlage);
            });
        };

    };
});