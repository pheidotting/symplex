define(['jquery',
        'view/beheren-bedrijf-view',
        'js/beherenBedrijfJaarCijfers',
        'js/beherenBedrijfRisicoAnalyse',
        'view/lijst-polissen-view',
        'view/beheren-polis-view',
        'view/lijst-schades-view',
        'view/beheren-schade-view',
        'commons/3rdparty/log',
        'commons/commonFunctions',
        'redirect'],
    function($, beherenBedrijf, beherenBedrijfJaarCijfers, beherenBedrijfRisicoAnalyse, beherenBedrijfPolissen, beherenBedrijfPolis, beherenBedrijfSchades, beherenBedrijfSchade, log, commonFunctions, redirect) {

    return function(bedrijfId, actie, subId){
		$('#content').load('templates/beherenBedrijfTemplate.html', function(response, status) {
			if(bedrijfId === undefined || bedrijfId === null || bedrijfId === 0){
				$('#bedrijven').hide();
				$('#bedrijf').hide();
				$('#polissen').hide();
				$('#polis').hide();
				$('#schades').hide();
				$('#schade').hide();
				$('#hypotheken').hide();
				$('#hypotheek').hide();
				$('#bijlages').hide();
			}
			if (status === "success") {
				//Op basis van actie de actieve tab bepalen
				var pagina = "";
				if(actie !== undefined && actie !== null){
					pagina = actie;
				}
				if(actie === undefined || actie === null) {
				    actie = "";
                }

				//Uitzondering
				if(pagina === 'polisInzien') {
					pagina= "polis";
				}

				//Onderliggende pagina aanroepen
				$('#details').load("templates/beherenBedrijf" + pagina + ".html", function(){
					if(actie === ""){
						beherenBedrijf.init(bedrijfId);
					}else if(actie === "jaarcijfers"){
						new beherenBedrijfJaarCijfers(bedrijfId);
					}else if(actie === "risicoanalyses"){
					    new beherenBedrijfRisicoAnalyse(bedrijfId);
					}else if(actie === "polissen"){
					    beherenBedrijfPolissen.init(bedrijfId, 'BEDRIJF');
					}else if(actie == "polis"){
						beherenBedrijfPolis.init(subId, bedrijfId, false, 'BEDRIJF');
					}else if(actie == "polisInzien"){
						beherenBedrijfPolis.init(subId, bedrijfId, true, 'BEDRIJF');
					}else if(actie === "schades"){
					    beherenBedrijfSchades.init(bedrijfId, 'BEDRIJF');
					}else if(actie === "schade"){
					    beherenBedrijfSchade.init(subId, bedrijfId, false, 'BEDRIJF');
					}
				});

				if(actie === ""){
					actie = "beherenBedrijf";
				}
				$("#" + actie).addClass("navdivactive");
				if(actie === "beherenBedrijf"){
					actie = "";
				}

				//Navigatie
				$("#beherenBedrijf").click(function(){
			    	commonFunctions.verbergMeldingen();
			    	redirect.redirect('BEHEREN_BEDRIJF', bedrijfId);
				});
				$("#menuPolissen").click(function(){
			    	commonFunctions.verbergMeldingen();
			    	redirect.redirect('BEHEREN_BEDRIJF', bedrijfId, 'polissen');
				});
				$("#menuPolis").click(function(){
			    	commonFunctions.verbergMeldingen();
			    	redirect.redirect('BEHEREN_BEDRIJF', bedrijfId, 'polis', '0');
				});
				$("#polisInzien").click(function(){
			    	commonFunctions.verbergMeldingen();
			    	redirect.redirect('BEHEREN_BEDRIJF', bedrijfId, 'polisInzien', '0');
				});
				$("#menuSchades").click(function(){
			    	commonFunctions.verbergMeldingen();
			    	redirect.redirect('BEHEREN_BEDRIJF', bedrijfId, 'schades');
				});
				$("#menuSchade").click(function(){
			    	commonFunctions.verbergMeldingen();
			    	redirect.redirect('BEHEREN_BEDRIJF', bedrijfId, 'schade', '0');
				});
				$("#jaarcijfers").click(function(){
			    	commonFunctions.verbergMeldingen();
			    	redirect.redirect('BEHEREN_BEDRIJF', bedrijfId, 'jaarcijfers');
				});
				$("#risicoanalyes").click(function(){
			    	commonFunctions.verbergMeldingen();
			    	redirect.redirect('BEHEREN_BEDRIJF', bedrijfId, 'risicoanalyses');
				});
			}
		});
	};
});