define(['knockout'],
	function(ko) {

	return function taak() {
		_this = this;

		_this.id = ko.observable();
		_this.projectId = ko.observable();
		_this.omschrijving = ko.observable();
		_this.reminder = ko.observable();
		_this.notities = ko.observableArray([]);
		_this.titel = ko.computed(function() {
		    if(_this.reminder()) {
		        return _this.omschrijving() + ' (' + _this.reminder() + ')';
		    } else {
		        return _this.omschrijving();
		    }
		});
		_this.link = ko.computed(function(){
		    return 'https://todoist.com/app?lang=nl#project%2F' + _this.projectId()
		});

        this.idShowKnop = ko.computed(function(){
            return _this.id() + '-show';
        });

        this.idHideKnop = ko.computed(function(){
            return _this.id() + '-hide';
        });

        this.showKlapOpenKnop = ko.computed(function(){
            return _this.notities().length > 0;
        });

	};
});