define(['jquery',
         'knockout',
         'model/bedrijf',
         'commons/commonFunctions',
         'knockoutValidation',
         'blockUI',
         'redirect'],
	function ($, ko, Bedrijf, functions, kv, blockUI, redirect) {

	return function(){
		var bedrijfLijstModel = ko.validatedObservable({

			lijst : ko.observableArray(),

			naarDetailScherm : function(bedrijf){
				functions.verbergMeldingen();
				$(document).ajaxStop($.unblockUI);
				$.blockUI({ message: '<img src="images/ajax-loader.gif">'});
				redirect.redirect('BEHEREN_BEDRIJF', bedrijf.id());
			},

            gezochtMetTonen : ko.observable('abc'),
            gezochtMet : ko.observable(false)

		});

		return bedrijfLijstModel;
    };
});