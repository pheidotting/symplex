define(['jquery'],
    function($) {

    return {
		block: function() {
			$(document).ajaxStop($.unblockUI);
			$.blockUI({ message: '<img src="images/ajax-loader.gif">'});
		}
     };
});