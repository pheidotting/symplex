    define(['jquery',
        'view/beheren-relatie-view',
        'js/beherenRelatieBedrijven',
        'js/beherenRelatieBedrijf',
        'view/lijst-polissen-view',
        'view/beheren-polis-view',
        'view/lijst-schades-view',
        'view/beheren-schade-view',
        'view/lijst-hypotheken-view',
        'view/beheren-hypotheek-view',
        'js/beherenRelatieBijlages',
        'js/beherenRelatieAangifte',
        'js/beherenRelatieAangiftes',
        'js/beherenRelatieCommunicaties',
        'js/beherenRelatieCommunicatie',
        'commons/3rdparty/log2',
        'commons/commonFunctions',
        'redirect'],
    function($, beherenRelatie, beherenRelatieBedrijven, beherenRelatieBedrijf, beherenRelatiePolissen, beherenRelatiePolis, beherenRelatieSchades, beherenRelatieSchade, beherenRelatieHypotheken, beherenRelatieHypotheek, beherenRelatieBijlages, beherenRelatieAangifte, beherenRelatieAangiftes, beherenRelatieCommunicaties, beherenRelatieCommunicatie, log, commonFunctions, redirect) {

    return function(relatieId, actie, subId){
        var logger = log.getLogger('beherenRelatie');

		$('#content').load('templates/beherenRelatieTemplate.html', function(response, status, xhr) {
			if(relatieId == undefined || relatieId == null || relatieId == 0){
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
			if (status == "success") {
				//Op basis van actie de actieve tab bepalen
				var pagina = "";
				if(actie != undefined && actie != null){
					pagina = actie;
				}
				if(actie == undefined || actie == null) {
				    actie = "";
                }

				//Uitzondering
				if(pagina == 'polisInzien') {
					pagina= "polis";
				}

				//Onderliggende pagina aanroepen
				$('#details').load("templates/beherenRelatie" + pagina + ".html", function(){
					if(actie == ""){
                		beherenRelatie.init(relatieId);
//						new beherenRelatie(relatieId);
					}else if(actie == "bedrijven"){
						new beherenRelatieBedrijven(relatieId);
					}else if(actie == "bedrijf"){
						new beherenRelatieBedrijf(subId, relatieId);
					}else if(actie == "polissen"){
						beherenRelatiePolissen.init(relatieId, 'RELATIE');
					}else if(actie == "polis"){
						beherenRelatiePolis.init(subId, relatieId, false, 'RELATIE');
					}else if(actie == "polisInzien"){
						beherenRelatiePolis.init(subId, relatieId, true, 'RELATIE');
					}else if(actie == "schades"){
						beherenRelatieSchades.init(relatieId, 'RELATIE');
					}else if(actie == "schade"){
						beherenRelatieSchade.init(subId, relatieId, false, 'RELATIE');
					}else if(actie == "hypotheken"){
						beherenRelatieHypotheken.init(relatieId, 'RELATIE');
					}else if(actie == "hypotheek"){
						beherenRelatieHypotheek.init(subId, relatieId, false, 'RELATIE');
					}else if(actie == "bijlages"){
						new beherenRelatieBijlages(relatieId);
					}else if(actie == "aangifte"){
						new beherenRelatieAangifte(relatieId);
					}else if(actie == "aangiftes"){
						new beherenRelatieAangiftes(relatieId);
					}else if(actie == "communicaties"){
						new beherenRelatieCommunicaties(relatieId);
					}else if(actie == "communicatie"){
						new beherenRelatieCommunicatie(subId, relatieId);
					}
					_relatieId = relatieId;
					_subId = subId;

				});

				if(actie == ""){
					actie = "beherenRelatie";
				}
				$("#" + actie).addClass("navdivactive");
				if(actie == "beherenRelatie"){
					actie = "";
				}

				//Navigatie
				$("#beherenRelatie").click(function(){
			    	commonFunctions.verbergMeldingen();
			    	redirect.redirect('BEHEREN_RELATIE', relatieId);
				});
				$("#bedrijven").click(function(){
			    	commonFunctions.verbergMeldingen();
			    	redirect.redirect('BEHEREN_RELATIE', relatieId, 'bedrijven');
				});
				$("#bedrijf").click(function(){
			    	commonFunctions.verbergMeldingen();
			    	redirect.redirect('BEHEREN_RELATIE', relatieId, 'bedrijf', '0');
				});
				$("#menuPolissen").click(function(){
			    	commonFunctions.verbergMeldingen();
			    	redirect.redirect('BEHEREN_RELATIE', relatieId, 'polissen');
				});
				$("#menuPolis").click(function(){
			    	commonFunctions.verbergMeldingen();
			    	redirect.redirect('BEHEREN_RELATIE', relatieId, 'polis', '0');
				});
				$("#polisInzien").click(function(){
			    	commonFunctions.verbergMeldingen();
			    	redirect.redirect('BEHEREN_RELATIE', relatieId, 'polisInzien', '0');
				});
				$("#menuSchades").click(function(){
			    	commonFunctions.verbergMeldingen();
			    	redirect.redirect('BEHEREN_RELATIE', relatieId, 'schades');
				});
				$("#menuSchade").click(function(){
			    	commonFunctions.verbergMeldingen();
			    	redirect.redirect('BEHEREN_RELATIE', relatieId, 'schade', '0');
				});
				$("#menuHypotheken").click(function(){
					commonFunctions.verbergMeldingen();
			    	redirect.redirect('BEHEREN_RELATIE', relatieId, 'hypotheken');
				});
				$("#menuHypotheek").click(function(){
					commonFunctions.verbergMeldingen();
			    	redirect.redirect('BEHEREN_RELATIE', relatieId, 'hypotheek', '0');
				});
				$("#bijlages").click(function(){
			    	commonFunctions.verbergMeldingen();
			    	redirect.redirect('BEHEREN_RELATIE', relatieId, 'bijlages');
				});
				$("#aangiftes").click(function(){
					commonFunctions.verbergMeldingen();
			    	redirect.redirect('BEHEREN_RELATIE', relatieId, 'aangiftes');
				});
				$("#aangifte").click(function(){
					commonFunctions.verbergMeldingen();
			    	redirect.redirect('BEHEREN_RELATIE', relatieId, 'aangifte', '0');
				});
				$("#beherenRelatieCommunicaties").click(function(){
					commonFunctions.verbergMeldingen();
			    	redirect.redirect('BEHEREN_RELATIE', relatieId, 'communicaties');
				});
				$("#beherenRelatieCommunicatie").click(function(){
					commonFunctions.verbergMeldingen();
			    	redirect.redirect('BEHEREN_RELATIE', relatieId, 'communicatie', '0');
				});
			}
		});
	};
});