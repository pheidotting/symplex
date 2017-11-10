define(['jquery',
        'model/gesprek',
        'commons/3rdparty/log2',
        'knockout',
        'moment'],
	function ($, Gesprek, log, ko, moment) {
        return {
            mapGesprek: function(r) {
                mappen(r);
            },

            mapGesprekken: function(data) {
                var gesprekken = ko.observableArray([]);

                $.each(data, function(i, r){
                    $.each(mappen(r), function(i, g) {
                        gesprekken.push(g);
                    });
                });

                return gesprekken;
            }
        }

        function mappen(data){
            if(data != null) {
                var result = [];
                $.each(data.telefoongesprekken, function(i, telefoongesprek) {
                    var gesprek = new Gesprek();

                    gesprek.telefoonnummer(data.telefoonnummer);

                    gesprek.bestandsnaam(telefoongesprek.bestandsnaam);

                    var parts = telefoongesprek.bestandsnaam.split('-');
                    if(parts[0] == 'out') {
                        gesprek.uitgaand(true);
                        gesprek.inkomend(false);
//                        gesprek.telefoonnummer(parts[1]);

                    } else {
                        gesprek.uitgaand(false);
                        gesprek.inkomend(true);
//                        gesprek.telefoonnummer(parts[2]);

                    }
                    gesprek.tijdstip(moment(telefoongesprek.tijdstip, 'DD-MM-YYYY HH:mm'));

                    result.push(gesprek);
                });
                return result;
            }
        }
    }
);
