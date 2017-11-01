define(['jquery',
        "knockout"],
    function($, ko) {

	return function(data){
    	var korteNetnummers = ['010', '013', '014', '015', '020', '023', '024', '026', '030', '033', '035', '036', '038', '040', '043', '045', '046', '050', '053', '055', '058', '070', '071', '072', '073', '074', '075', '076', '077', '078', '079'];

	    var thisTel = this;

	    thisTel.id = ko.observable(data.id);
        thisTel.telefoonnummer = ko.observable(data.telefoonnummer);
        thisTel.soort = ko.observable(data.soort);
        thisTel.omschrijving = ko.observable(data.omschrijving);
		thisTel.soortEntiteit = ko.observable(data.soortEntiteit);
		thisTel.entiteitId = ko.observable(data.entiteitId);

        thisTel.startBewerken = function(nummer){
            if(nummer.telefoonnummer()){
                nummer.telefoonnummer(nummer.telefoonnummer().replace(/ /g, "").replace("-", ""));
            }
        };

        thisTel.stopBewerken = function(nummer){
            zetTelefoonnummerOm(nummer);
        };

        zetTelefoonnummerOm = function(nummer){
            var tel = nummer.telefoonnummer();
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
                nummer.telefoonnummer(tel);
            }
        };

        contains = function (a, obj) {
            for (var i = 0; i < a.length; i++) {
                if (a[i] === obj) {
                    return true;
                }
            }
            return false;
        }

        zetTelefoonnummerOm(thisTel);
    };
});
