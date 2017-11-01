define(['jquery',
        'redirect'],
    function($, redirect) {

    return function(){
		$('#content').load('templates/dashboard/dashboard.html', function(response, status, xhr) {
			if (status == "success") {
				$.get( "../dejonge/rest/medewerker/taak/aantalOpenTaken", function(data) {
					$('#aantalOpenstaandeTaken').html(data);
                    $.unblockUI();
				});
			}

			$('#naarParticulier').click(function(){
				redirect.redirect('LIJST_RELATIES');
			});

			$('#naarZakelijk').click(function(){
				redirect.redirect('LIJST_BEDRIJVEN');
			});
		});
	};
});