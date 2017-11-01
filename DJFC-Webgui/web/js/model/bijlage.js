define(['jquery',
         'knockout',
         'commons/3rdparty/log2',
         'commons/commonFunctions',
         'dataServices'],
	function ($, ko, log, commonFunctions, dataServices) {
	    var logger = log.getLogger('bijlage');

	return function bijlageModel (modelData){
		thisBijlage = this;

		thisBijlage.id = ko.observable(modelData.id);
		thisBijlage.url = ko.computed(function() {
			return "../dejonge/rest/medewerker/bijlage/download?id=" + modelData.identificatie;
		}, this);
		thisBijlage.bestandsNaam = ko.observable(modelData.bestandsNaam);
		thisBijlage.soortBijlage = ko.observable(modelData.soortBijlage);
		thisBijlage.parentId = ko.observable(modelData.parentId);
		thisBijlage.omschrijving = ko.observable(modelData.omschrijving);
		thisBijlage.datumUpload = ko.observable(modelData.datumUpload);
		thisBijlage.omschrijvingOfBestandsNaam = ko.observable(modelData.omschrijvingOfBestandsNaam);
		thisBijlage.omschrijvingOfBestandsNaamBackup = '';

		thisBijlage.resetOmschrijving = function(bijlage){
			if(bijlage.omschrijvingOfBestandsNaam() == null || bijlage.omschrijvingOfBestandsNaam() == ''){
				if(bijlage.omschrijving() == null || bijlage.omschrijving() == ''){
					bijlage.omschrijvingOfBestandsNaam(bijlage.bestandsNaam() + " " + bijlage.datumUpload());
				}else{
					bijlage.omschrijvingOfBestandsNaam(bijlage.omschrijving() + " " + bijlage.datumUpload());
				}
			}
		}

		thisBijlage.tonen = ko.computed(function() {
			return ko.utils.unwrapObservable(thisBijlage.soortBijlage) + " (" + ko.utils.unwrapObservable(thisBijlage.parentId) + ")";
		},this);
		thisBijlage.stopEditModus = function(bijlage){
			logger.debug(ko.toJSON("stopEditModus " + bijlage.id()));
			$('#tekst' + bijlage.id()).show();
			$('#edit' + bijlage.id()).hide();
			dataServices.wijzigOmschrijvingBijlage(bijlage.id(), bijlage.omschrijvingOfBestandsNaam());
		};
		thisBijlage.startEditModus = function(bijlage){
			logger.debug(ko.toJSON("startEditModus " + bijlage.id()));

			bijlage.omschrijvingOfBestandsNaamBackup = bijlage.omschrijvingOfBestandsNaam();

			$('#tekst' + bijlage.id()).hide();
			$('#edit' + bijlage.id()).show();
		};
		thisBijlage.stopEditModusAnnuleren = function(bijlage){
			bijlage.omschrijvingOfBestandsNaam(bijlage.omschrijvingOfBestandsNaamBackup);
			bijlage.stopEditModus(bijlage);
		}

		thisBijlage.resetOmschrijving(thisBijlage);
    };
});