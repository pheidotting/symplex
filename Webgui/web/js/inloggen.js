define(['jquery',
        "knockout",
        'model/inlog'],
    function($, ko, inlog) {

    return function(){
		$('#content').load('templates/inloggen.html', function(response, status, xhr) {
			if (status == "success") {
				ko.applyBindings(new inlog());
//                $.unblockUI();
			}
		});
	};
});