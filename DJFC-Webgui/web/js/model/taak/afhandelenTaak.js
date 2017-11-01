define([ "commons/3rdparty/log",
         "knockout"],
	function(logger, ko) {

	return function afhandelenTaak(id) {
		_afhandelenTaak = this;
		
		_afhandelenTaak.taakId = ko.observable(id);
	};
});