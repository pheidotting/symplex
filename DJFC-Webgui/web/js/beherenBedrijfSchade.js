define(['jquery',
        "knockout",
        'model/schade',
        'commons/block',
        'commons/3rdparty/log',
        'commons/commonFunctions',
         'dataServices',
        'fileUpload',
        'opmerkingenLoader',
        'navRegister'],
    function($, ko, Schade, block, log, commonFunctions, dataServices, fileUpload, opmerkingenLoader, navRegister) {

    return function(schadeId, bedrijfId) {
		block.block();
		dataServices.lijstPolissenBijBedrijf(bedrijfId).done(function(data) {
			var $select = $('#polisVoorSchademelding');
			$.each(data, function(key, value) {
				var polisTitel = value.soort + " (" + value.polisNummer + ")";

			    $('<option>', { value : value.id }).text(polisTitel).appendTo($select);
			});

            dataServices.lijstStatusSchade().done(function(data) {
				var $select = $('#statusSchade');
				$.each(data, function(key, value) {
				    $('<option>', { value : value }).text(value).appendTo($select);
				});
				if(schadeId !== null && schadeId !== "0"){
					log.debug("ophalen Schade met id " + schadeId);

					dataServices.leesSchade(schadeId).done(function(data) {
                        dataServices.lijstBijlages('SCHADE', schadeId).done(function(bijlages){
                            data.bijlages = bijlages;
                            dataServices.lijstOpmerkingen('SCHADE', schadeId).done(function(opmerkingen){
                                data.opmerkingen = opmerkingen;
                                dataServices.voerUitGet(navRegister.bepaalUrl('LIJST_GROEP_BIJLAGES') + '/SCHADE/' + schadeId).done(function(groepBijlages){
                                    data.groepBijlages = groepBijlages;
                                    log.debug("applybindings met " + JSON.stringify(data));
                                    var schade = new Schade(data);
                                    fileUpload.init(bedrijfId).done(function(){
                                        new opmerkingenLoader(bedrijfId).init().done(function(){
                                            ko.applyBindings(schade);
                                            $.unblockUI();
                                        });
                                    });
                                });
                            });
                        });
                    });
                }else{
                    log.debug("applybindings met een nieuw Schade object");
                    var schade = new Schade('');
                    schade.bedrijf(bedrijfId);
                    ko.applyBindings(schade);
                    $.unblockUI();
                }
			});
		});
	};
});