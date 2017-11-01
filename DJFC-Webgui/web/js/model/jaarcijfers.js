define(['jquery',
         'knockout',
         'commons/3rdparty/log',
         'commons/commonFunctions',
         'moment',
         'model/bijlage',
         'model/groepbijlages',
         "commons/opmaak",
         'dataServices',
         'navRegister',
         'redirect',
         'fileUpload',
         'opmerkingenModel'],
	function ($, ko, log, commonFunctions, moment, Bijlage, GroepBijlages, opmaak, dataServices, navRegister, redirect, fileUpload, opmerkingenModel) {

	return function (data){
		var _cijfers = this;

		_cijfers.opmerkingenModel = new opmerkingenModel(data.opmerkingen, null, null, null, null, null, null, data.id);

        _cijfers.readOnly = ko.observable(false);
        _cijfers.notReadOnly = ko.observable(true);
		_cijfers.jaar = ko.observable(data.jaar);
		_cijfers.id = ko.observable(data.id);
		_cijfers.soortEntiteit = ko.observable('JaarCijfers');
		_cijfers.idDiv = ko.computed(function() {
	        return "collapsable" + data.id;
		}, this);
		_cijfers.idDivLink = ko.computed(function() {
	        return "#collapsable" + data.id;
		}, this);
		_cijfers.bijlages = ko.observableArray();
		if(data.bijlages != null){
			$.each(data.bijlages, function(i, item){
				var bijlage = new Bijlage(item);
				_cijfers.bijlages().push(bijlage);
			});
		};

		_cijfers.groepBijlages = ko.observableArray();
		if(data.groepBijlages != null){
			$.each(data.groepBijlages, function(i, item){
				var groepBijlages = new GroepBijlages(item);
				_cijfers.groepBijlages().push(groepBijlages);
			});
		};

		_cijfers.nieuwePolisUpload = function (){
			log.debug("Nieuwe bijlage upload");
			$('uploadprogress').show();

            fileUpload.uploaden().done(function(uploadResultaat){
                log.debug(ko.toJSON(uploadResultaat));

                if(uploadResultaat.bestandsNaam == null) {
                    _cijfers.groepBijlages().push(uploadResultaat);
                    _cijfers.groepBijlages.valueHasMutated();
                } else {
                    _cijfers.bijlages().push(uploadResultaat);
                    _cijfers.bijlages.valueHasMutated();
                }
            });
		};
    };
});