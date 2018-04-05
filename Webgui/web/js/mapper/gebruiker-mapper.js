define(['jquery',
        'model/relatie',
        'commons/3rdparty/log',
        'knockout',
        'mapper/adres-mapper'],
    function ($, Relatie, log, ko, adresMapper) {
        return {
            mapRelatie: function (data) {
                return mappen(data);
            },

            mapRelaties: function (data) {
                var relaties = ko.observableArray([]);

                $.each(data, function (i, r) {
                    relaties.push(mappen(r));
                });

                return relaties;
            }
        }

        function mappen(data) {
            if (data != null) {
                var relatie = new Relatie();

                relatie.identificatie(data.identificatie);
                relatie.id(data.id);
                relatie.voornaam(data.voornaam);
                relatie.roepnaam(data.roepnaam);
                relatie.tussenvoegsel(data.tussenvoegsel);
                relatie.achternaam(data.achternaam);
                relatie.bsn(data.bsn);
                relatie.geboorteDatum(data.geboorteDatum);
                relatie.overlijdensdatum(data.overlijdensdatum);
                relatie.geslacht(data.geslacht);
                relatie.burgerlijkeStaat(data.burgerlijkeStaat);
                relatie.emailadres(data.emailadres);

                if (data.adressen != null) {
                    relatie.adressen = ko.observableArray([]);
                    var adressen = adresMapper.mapAdressen(data.adressen);
                    $.each(adressen(), function (i, adres) {
                        relatie.adressen.push(adres);
                    });
                }

                return relatie;
            }
        }
    }
);
