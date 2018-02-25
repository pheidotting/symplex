define(['jquery',
        'model/groepbijlages2',
        'commons/3rdparty/log',
        'knockout',
        'mapper/bijlage-mapper'],
	function ($, Groepbijlage, log, ko, bijlageMapper) {
        return {
            mapGroepbijlage: function(r) {
                mappen(r);
            },

            mapGroepbijlages: function(data) {
                var groepbijlages = ko.observableArray([]);

                $.each(data, function(i, r){
                    groepbijlages.push(mappen(r));
                });

                return groepbijlages;
            }
        }

        function mappen(data){
            if(data != null) {
                var groepbijlage = new Groepbijlage();

                groepbijlage.id(data.id);
                groepbijlage.naam(data.naam);
                groepbijlage.bijlages = bijlageMapper.mapBijlages(data.bijlages);

                return groepbijlage;
            }
        }
    }
);
