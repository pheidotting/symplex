define(['jquery',
        'model/aangifte',
         'knockout',
         'commons/3rdparty/log',
         'commons/commonFunctions'],
    function ($, Aangifte, ko, log, commonFunctions) {

	return function aangifteModel (data){
		_thisAangiftes = this;

		_thisAangiftes.aangiftes = ko.observableArray();
		$.each(data, function(i, item) {
			log.debug(JSON.stringify(item));
			_thisAangiftes.aangiftes().push(new Aangifte(item));
		});
    };
});