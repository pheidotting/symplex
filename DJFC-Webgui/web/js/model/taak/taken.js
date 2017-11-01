define([ "commons/3rdparty/log",
         "commons/validation",
         "js/model/taak/taak",
         "knockout"],
         function(log, validation, Taak, ko) {

	return function taken(data) {
		var _this = this;

		_this.taken = ko.observableArray();
		$.each(data, function(i, item){
			_this.taken.push(new Taak(item));
		});
	};
});