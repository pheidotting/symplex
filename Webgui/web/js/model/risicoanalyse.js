define(['jquery',
         'knockout',
         'commons/3rdparty/log',
         'commons/commonFunctions',
         'model/bijlage',
         'model/groepbijlages',
         'fileUpload',
         'opmerkingenModel',
         'dataServices'],
    function ($, ko, log, commonFunctions, Bijlage, GroepBijlages, fileUpload, opmerkingenModel, dataServices) {

	return function (data){
		var _risicoAnalyse = this;

		_risicoAnalyse.opmerkingenModel = new opmerkingenModel(data.opmerkingen, null, null, null, null, null, null, null, data.id);

        _risicoAnalyse.readOnly = ko.observable(false);
        _risicoAnalyse.notReadOnly = ko.observable(true);
		_risicoAnalyse.id = ko.observable(data.id);
		_risicoAnalyse.soortEntiteit = ko.observable('RisicoAnalyse');
        _risicoAnalyse.bijlages = ko.observableArray();
        if(data.bijlages != null){
            $.each(data.bijlages, function(i, item){
                var bijlage = new Bijlage(item);
                _risicoAnalyse.bijlages().push(bijlage);
            });
        };

		_risicoAnalyse.groepBijlages = ko.observableArray();
		if(data.groepBijlages != null){
			$.each(data.groepBijlages, function(i, item){
				var groepBijlages = new GroepBijlages(item);
				_risicoAnalyse.groepBijlages().push(groepBijlages);
			});
		};

		_risicoAnalyse.nieuwePolisUpload = function (){
			log.debug("Nieuwe bijlage upload");
			$('uploadprogress').show();

            fileUpload.uploaden().done(function(uploadResultaat){
                log.debug(ko.toJSON(uploadResultaat));

                if(uploadResultaat.bestandsNaam == null) {
                    _risicoAnalyse.groepBijlages().push(uploadResultaat);
                    _risicoAnalyse.groepBijlages.valueHasMutated();
                } else {
                    _risicoAnalyse.bijlages().push(uploadResultaat);
                    _risicoAnalyse.bijlages.valueHasMutated();
                }
            });
		};
        _risicoAnalyse.verwijderBijlage = function(bijlage){
            commonFunctions.verbergMeldingen();
            var r=confirm("Weet je zeker dat je deze bijlage wilt verwijderen?");
            if (r === true) {
                _risicoAnalyse.bijlages.remove(bijlage);
                dataServices.verwijderBijlage(bijlage.id());
            }
        };

    };
});