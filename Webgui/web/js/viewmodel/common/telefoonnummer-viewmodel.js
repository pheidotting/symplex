define(['commons/3rdparty/log2',
        'model/telefoonNummer2',
        'knockout',
        'mapper/telefoonnummer-mapper'],
    function(log, TelefoonNummer, ko, telefoonnummerMapper) {

    return function(readOnly, soortEntiteit, entiteitId, telefoonnummers) {
        var _this = this;
        var logger = log.getLogger('telefoonnummer-viewmodel');
    	var korteNetnummers = ['010', '013', '014', '015', '020', '023', '024', '026', '030', '033', '035', '036', '038', '040', '043', '045', '046', '050', '053', '055', '058', '070', '071', '072', '073', '074', '075', '076', '077', '078', '079'];

		this.id = ko.observable(entiteitId);
		this.soortEntiteit = ko.observable(soortEntiteit);
		this.telefoonnummers = ko.observableArray();
		if(telefoonnummers != null) {
            $.each(telefoonnummerMapper.mapTelefoonnummers(telefoonnummers)(), function(i, telefoonnummer){
                var tel = telefoonnummer.telefoonnummer();
                if(tel !== null && tel !== undefined && tel.length === 10){
                    if(tel.substring(0, 2) === "06"){
                        //06 nummers
                        tel = tel.substring(0, 2) + " - " + tel.substring(2, 4) + " " + tel.substring(4, 6) + " " + tel.substring(6, 8) + " " + tel.substring(8, 10);
                    } else if(contains(korteNetnummers, tel.substring(0, 3))){
                         //3 cijferig kengetal
                        tel = tel.substring(0, 3) + " - " + tel.substring(3, 6) + " " + tel.substring(6, 8) + " " + tel.substring(8, 10);
                    } else {
                        //4 cijferig kengetal
                        tel = tel.substring(0, 4) + " - " + tel.substring(4, 6) + " " + tel.substring(6, 8) + " " + tel.substring(8, 10);
                    }
                    telefoonnummer.telefoonnummer(tel);
                }
                _this.telefoonnummers.push(telefoonnummer);
            });
        }

        function contains(a, obj) {
            for (var i = 0; i < a.length; i++) {
                if (a[i] === obj) {
                    return true;
                }
            }
            return false;
        }

        this.telefoonnummers.subscribe(function(telefoonnummers) {
            $.each(telefoonnummers, function(i, telefoonnummer){
                _this.zetTelefoonnummerOm(telefoonnummer);
            });
        });

        this.voegTelefoonNummerToe = function(){
            _this.telefoonnummers.push(new TelefoonNummer());
        };

        this.verwijderTelefoonNummer = function(telefoonnummer){
            _this.telefoonnummers.remove(telefoonnummer);
        };

        this.startBewerken = function(telefoonnummer){
            if(telefoonnummer.telefoonnummer()){
                telefoonnummer.telefoonnummer(telefoonnummer.telefoonnummer().replace(/ /g, "").replace("-", ""));
            }
        };

        this.stopBewerken = function(telefoonnummer){
            _this.zetTelefoonnummerOm(telefoonnummer);
        };

        this.zetTelefoonnummerOm = function(nummer){
            if(nummer.telefoonnummer != null) {
                var tel = nummer.telefoonnummer();
                if(tel !== null && tel !== undefined && tel.length === 10){
                    if(tel.substring(0, 2) === "06"){
                        //06 nummers
                        tel = tel.substring(0, 2) + " - " + tel.substring(2, 4) + " " + tel.substring(4, 6) + " " + tel.substring(6, 8) + " " + tel.substring(8, 10);
                    } else if(_this.contains(korteNetnummers, tel.substring(0, 3))){
                         //3 cijferig kengetal
                        tel = tel.substring(0, 3) + " - " + tel.substring(3, 6) + " " + tel.substring(6, 8) + " " + tel.substring(8, 10);
                    } else {
                        //4 cijferig kengetal
                        tel = tel.substring(0, 4) + " - " + tel.substring(4, 6) + " " + tel.substring(6, 8) + " " + tel.substring(8, 10);
                    }
                    nummer.telefoonnummer(tel);
                }
            }
        };

        this.contains = function (a, obj) {
            for (var i = 0; i < a.length; i++) {
                if (a[i] === obj) {
                    return true;
                }
            }
            return false;
        }

	};
});