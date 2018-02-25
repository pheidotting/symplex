define(['jquery',
        'model/bijlage',
        'commons/3rdparty/log',
        'knockout'],
	function ($, Bijlage, log, ko) {
        return {
            mapBijlage: function(r) {
                mappen(r);
            },

            mapBijlages: function(data) {
                var bijlages = ko.observableArray([]);

                $.each(data, function(i, r){
                    bijlages.push(mappen(r));
                });

                return bijlages;
            }
        }

        function mappen(data){
            if(data != null) {
                var bijlage = new Bijlage();

                bijlage.id(data.id);
                bijlage.identificatie(data.identificatie);
                bijlage.bestandsNaam(data.bestandsNaam);
                bijlage.datumUpload(data.datumUpload);
                bijlage.soortEntiteit(data.soortEntiteit);
                bijlage.entiteitId(data.entiteitId);
                if(data.omschrijving == null) {
                    bijlage.omschrijvingOfBestandsNaam(data.bestandsNaam);
                } else {
                    bijlage.omschrijvingOfBestandsNaam(data.omschrijving);
                }

                return bijlage;
            }
        }
    }
);
