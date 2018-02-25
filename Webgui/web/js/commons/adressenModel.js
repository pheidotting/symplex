define(["commons/3rdparty/log",
        'commons/commonFunctions',
        'model/adres',
        'navRegister',
        'knockout'],
    function(log, commonFunctions, Adres, navRegister, ko) {

        return function(data, bedrijf){
            var _adressen = this;

            _adressen.adressen = ko.observableArray();
            _adressen.nieuwAdres = ko.observable();
            _adressen.bedrijf = ko.observable(bedrijf);

            if(data != null){
                $.each(data, function(i, item) {
                    _adressen.adressen().push(new Adres(item));
                });
            }

            _adressen.voegAdresToe = function(){
                log.debug("nieuwe Adres");

                var nieuwAdres = new Adres("");
                nieuwAdres.soortEntiteit('BEDRIJF')
                nieuwAdres.entiteitId(bedrijf);

                _adressen.adressen().push(nieuwAdres);
                _adressen.adressen.valueHasMutated();
            };
        }
    }
);
