define(['commons/3rdparty/log2',
        'dataServices',
        'redirect'],
    function(log, dataServices, redirect) {

    var logger = log.getLogger('commonFunctions');

	return {
		zetDatumOm: function(datumZonderStreepjes){
			var datumMetStreepjes = datumZonderStreepjes;
			if(datumZonderStreepjes !== undefined && datumZonderStreepjes !== null && datumZonderStreepjes.length === 8 && this.isNumeric(datumZonderStreepjes)){
				datumMetStreepjes = datumZonderStreepjes.substring(0, 2) + "-" + datumZonderStreepjes.substring(2, 4) + "-" + datumZonderStreepjes.substring(4, 8);
            }

			return datumMetStreepjes;
		},

		zetDatumTijdOm: function(datumZonderStreepjes){
		    if(datumZonderStreepjes !== undefined && datumZonderStreepjes !== null) {
		        datumZonderStreepjes = datumZonderStreepjes.replace(' ', '').replace(':', '').replace('-', '').replace('-', '');
    			var datumMetStreepjes = datumZonderStreepjes;

                if(datumZonderStreepjes.length === 12 && this.isNumeric(datumZonderStreepjes)){
                    datumMetStreepjes = datumZonderStreepjes.substring(0, 2) + "-" + datumZonderStreepjes.substring(2, 4) + "-" + datumZonderStreepjes.substring(4, 8) + " " + datumZonderStreepjes.substring(8, 10) + ":" + datumZonderStreepjes.substring(10, 12);
                }
			}

			return datumMetStreepjes;
		},

		isNumeric: function(num) {
		     return (num >=0 || num < 0);
		},

 		plaatsFoutmelding: function(melding){
        	var foutmelding = jQuery.parseJSON(melding.responseText);

			$('#alertDanger').show();
			$('#alertDanger').html("Er is een fout opgetreden : " + foutmelding.foutmelding);
 		},

 		plaatsFoutmeldingString: function(melding){
			$('#alertDanger').html("Er is een fout opgetreden : " + melding);
			$('#alertDanger').show();
 		},

 		plaatsMelding: function(melding){
 			$("html, body").animate({ scrollTop: 0 }, "slow");
			$('#alertSucces').show();
			$('#alertSucces').html(melding);
			refreshIntervalId = setInterval(this.verbergMeldingen, 10000);
		},

		nietMeerIngelogd: function(data){
        	logger.error("FOUT opgehaald : " + JSON.stringify(data));
        	logger.error("naar inlogscherm");
			redirect.redirect('INLOGGEN');
			this.plaatsFoutmelding("Sessie verlopen, graag opnieuw inloggen");
		},

 		verbergMeldingen: function(){
			if(refreshIntervalId !== undefined || refreshIntervalId !== 0){
                clearInterval(this.refreshIntervalId);
                $('#alertSucces').hide();
                $('#alertDanger').hide();
            }
 		},

		uitloggen: function(){
			dataServices.uitloggen();
			$('#ingelogdeGebruiker').html("");
			$('#uitloggen').hide();
			$('#homeKnop').hide();
			redirect.redirect('INLOGGEN');
		},

		haalIngelogdeGebruiker: function(){
			logger.debug("Haal ingelogde gebruiker");
			var deferred = $.Deferred();

			dataServices.haalIngelogdeGebruiker().done(function(response){
				if(response.kantoor != null){
					logger.debug("Ingelogde gebruiker : " + response.gebruikersnaam + ", (" + response.kantoor + ")");
					$('#ingelogdeGebruiker').html("Ingelogd als : " + response.gebruikersnaam + ", (" + response.kantoor + ")");
				}else{
					logger.debug("Ingelogde gebruiker : " + response.gebruikersnaam);
					$('#ingelogdeGebruiker').html("Ingelogd als : " + response.gebruikersnaam);
				}
				$('#uitloggen').show();
				$('#homeKnop').show();

                return deferred.resolve(response);
			}).fail(function(){
				logger.debug("Niet ingelogd, naar de inlogpagina");
				$('#ingelogdeGebruiker').html("");
				$('#uitloggen').hide();
				$('#homeKnop').hide();
                location.href = 'index.html#inloggen';

                return deferred.resolve();
			});

			return deferred.promise();
		},

		uploadBestand: function(formData, url){
			var deferred = $.Deferred();

			$.ajax({
				url: url,
				type: 'POST',
				xhr: function() {
					var myXhr = $.ajaxSettings.xhr();
					if(myXhr.upload){
						myXhr.upload.addEventListener('progress',progressHandlingFunction, false);
					}
					return myXhr;
				},
				data: formData,
				cache: false,
				contentType: false,
				processData: false,
                beforeSend: function(request) {
                    if(localStorage.getItem('symplexAccessToken')!=null){
                        request.setRequestHeader('Authorization', localStorage.getItem('symplexAccessToken'));
                    }
                },
				success: function (response) {
					$('uploadprogress').hide();
					return deferred.resolve(response);
				},
				error: function (data) {
					$('#alertDanger').show();
					$('#alertDanger').html("Er is een fout opgetreden : " + data);
				}
			});
			function progressHandlingFunction(e){
				if(e.lengthComputable){
					$('uploadprogress').attr({value:e.loaded,max:e.total});
				}
			}
			return deferred.promise();
		}

    };
});