define(['jquery',
        'model/opmerking',
        'commons/3rdparty/log',
        'knockout'],
	function ($, Opmerking, log, ko) {
        return {
            mapOpmerking: function(r) {
                mappen(r);
            },

            mapOpmerkingen: function(data) {
                var opmerkingen = ko.observableArray([]);

                $.each(data, function(i, r){
                    opmerkingen.push(mappen(r));
                });

                return opmerkingen;
            }
        }

        function mappen(data){
            if(data != null) {
                var opmerking = new Opmerking();

                opmerking.id(data.id);
                opmerking.identificatie(data.identificatie);
                opmerking.opmerking(data.opmerking);
                opmerking.tijd(data.tijd);
                opmerking.medewerker(data.medewerker);
                opmerking.medewerkerId(data.medewerkerId);
                opmerking.soortEntiteit(data.soortEntiteit);
                opmerking.entiteitId(data.entiteitId);

                return opmerking;
            }
        }
    }
);
