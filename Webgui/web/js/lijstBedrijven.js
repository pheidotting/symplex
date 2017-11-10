define(['jquery',
        "knockout",
        'model/lijstBedrijven',
        'model/bedrijf',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log',
        'dataServices',
		'redirect'],
    function($, ko, LijstBedrijven, Bedrijf, functions, block, log, dataServices, redirect) {

    return function(zoekTerm){
        var _this = this;
		var lijst = new LijstBedrijven();

		$('#content').load('templates/lijstBedrijven.html', function(response, status, xhr) {
			$('#zoekTerm').val(zoekTerm);
			if (status == "success") {
				log.debug("ophalen lijst met Bedrijven");
				block.block();

				dataServices.lijstBedrijven(zoekTerm).done(function(data) {
					log.debug("opgehaald " + JSON.stringify(data));
					if(data != undefined){
						$.each(data, function(i, item) {
							var a = new Bedrijf(item);
							lijst().lijst().push(a);
						});

						log.debug("Bedrijven opgehaald, applyBindings");
						ko.applyBindings(lijst);
						$.unblockUI();
					}
    			});
                if(zoekTerm != null && zoekTerm != '') {
                    lijst().gezochtMetTonen(true);
                    lijst().gezochtMet(zoekTerm);
                } else {
                   lijst().gezochtMetTonen(false);
                }
                lijst().gezochtMetTonen.valueHasMutated();
                lijst().gezochtMet.valueHasMutated();
            }
		
			$('#zoeken').click(function(){
				redirect.redirect('LIJST_BEDRIJVEN', $('#zoekTerm').val());
			});

			$('#zoekTerm').on("keypress", function(e) {
	            if (e.keyCode == 13) {
					redirect.redirect('LIJST_BEDRIJVEN', $('#zoekTerm').val());
	            }
			});

			$('#toevoegenNieuwBedrijf').click(function(){
				functions.verbergMeldingen();
				redirect.redirect('BEHEREN_BEDRIJF', '0');
			});
		});
	};
});