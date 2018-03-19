define(['commons/3rdparty/log',
        'model/adres',
        'service/common/bijlage-service',
        'knockout',
        'fileUpload',
        'commons/commonFunctions',
        'mapper/bijlage-mapper',
        'mapper/groepbijlage-mapper'],
    function(log, Adres, bijlageService, ko, fileUpload, commonFunctions, bijlageMapper, groepbijlageMapper) {

    return function(readOnly, soortEntiteit, entiteitId, bijlages, groepBijlages, nieuweEntiteit) {
        var _this = this;
        var logger = log.getLogger('bijlage-viewmodel');

        this.readOnly = readOnly;
        fileUpload.init();
		this.bijlages = ko.observableArray();
		this.groepBijlages = ko.observableArray();
		this.schermTonen = ko.observable(!nieuweEntiteit);
		this.override = false;

        $.each(bijlageMapper.mapBijlages(bijlages)(), function(i, bijlage){
            _this.bijlages.push(bijlage);
        });

        $.each(groepbijlageMapper.mapGroepbijlages(groepBijlages)(), function(i, groepbijlage){
            _this.groepBijlages.push(groepbijlage);
        });

		this.id = ko.observable(entiteitId.identificatie);
		this.soortEntiteit = ko.observable(soortEntiteit);

        this.setId = function(id){
            $('#identificatie').val(id);
            _this.override = true;
            _this.readOnly = false;
            _this.id(id);
        };

        this.setSchermTonen = function(schermTonen) {
            _this.override = true;
            _this.readOnly = false;
            _this.schermTonen(schermTonen);
        };

        this.tonenUploadVeld = ko.computed(function(){
          if(_this.override){
            return true;
          }else{
            return !_this.readOnly && _this.id() != null;
            }
        });

        this.notReadOnly = ko.computed(function() {
            return !_this.readOnly;
        });

        this.readOnly = ko.computed(function() {
            return _this.readOnly;
        });

		this.startEditModus = function(bijlage){
		    logger.debug('startEditModus ' + ko.toJSON(bijlage.id()));

			bijlage.omschrijvingOfBestandsNaamBackup = bijlage.omschrijvingOfBestandsNaam();

			$('#tekst' + bijlage.id()).hide();
			$('#edit' + bijlage.id()).show();
        };

		this.stopEditModus = function(bijlage){
		    logger.debug('stopEditModus ' + ko.toJSON(bijlage.id()));

			$('#tekst' + bijlage.id()).show();
			$('#edit' + bijlage.id()).hide();
			bijlageService.wijzigOmschrijvingBijlage(bijlage.id(), bijlage.omschrijvingOfBestandsNaam());
		};

		this.stopEditModusAnnuleren = function(bijlage){
		    logger.debug('stopEditModusAnnuleren ' + ko.toJSON(bijlage.id()));

			bijlage.omschrijvingOfBestandsNaam(bijlage.omschrijvingOfBestandsNaamBackup);
			_this.stopEditModus(bijlage);
        };

		this.nieuwePolisUpload = function(){
            logger.debug("Nieuwe bijlage upload");
            $('uploadprogress').show();

            fileUpload.uploaden().done(function(uploadResultaat){
                logger.debug(ko.toJSON(uploadResultaat));

                if(uploadResultaat.bestandsNaam == null) {
                    _this.groepBijlages().push(uploadResultaat);
                    _this.groepBijlages.valueHasMutated();
                } else {
                    _this.bijlages().push(uploadResultaat);
                    _this.bijlages.valueHasMutated();
                }
            });
        };

		this.verwijderBijlage = function(bijlage){
            commonFunctions.verbergMeldingen();
            var r=confirm("Weet je zeker dat je deze bijlage wilt verwijderen?");
            if (r==true) {
                _this.bijlages.remove(bijlage);
                bijlageService.verwijderBijlage(bijlage.identificatie());

                location.reload();
            }
        };
	};
});