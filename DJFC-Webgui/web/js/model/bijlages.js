define(['jquery',
       'model/bijlage',
       'knockout',
       'commons/3rdparty/log',
       'commons/commonFunctions'],
  	function ($, Bijlage, ko, log, commonFunctions) {

  	return function bijlagesModel (data){
		bijlages = this;

		bijlages.bijlages = ko.observableArray();
		$.each(data, function(i, item){
			bijlages.bijlages.push(new Bijlage(item, log));
		});

		bijlages.verwijderBijlage = function(bijlage){
			commonFunctions.verbergMeldingen();
			var r=confirm("Weet je zeker dat je deze bijlage wilt verwijderen?");
			if (r==true) {
				bijlages.bijlages.remove(bijlage);
				$.get( "../dejonge/rest/medewerker/bijlage/verwijder", {"bijlageId" : bijlage.id()}, function() {});
			}
		};
    };
});