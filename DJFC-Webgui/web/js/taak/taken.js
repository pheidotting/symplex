define(['jquery',
        'knockout',
        'js/model/taak/taken',
         'dataServices'],
    function($, ko, Taken, dataServices) {

	return function(){
		$('#content').load('templates/taken/taken.html', function(){
			dataServices.lijstTaken().done(function(data){
				ko.applyBindings(new Taken(data));
			});
		});
	};

});