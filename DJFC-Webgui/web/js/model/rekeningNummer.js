define(['jquery',
        "knockout"],
    function($, ko) {

	return function(data) {
	    var thisRek = this;

	    thisRek.id = ko.observable(data.id);
        thisRek.rekeningnummer = ko.observable(data.rekeningnummer),
        thisRek.bic = ko.observable(data.bic)
		thisRek.soortEntiteit = ko.observable(data.soortEntiteit);
		thisRek.entiteitId = ko.observable(data.entiteitId);

        thisRek.startBewerken = function(nummer) {
            if(nummer != undefined && nummer.rekeningnummer() != undefined) {
                nummer.rekeningnummer(nummer.rekeningnummer().replace(/ /g, ""));
            }
        };

        thisRek.stopBewerken = function(nummer) {
            zetRekeningnummerOm(nummer);
        };

        thisRek.bicNaarUpperCase = function(nummer) {
            if(nummer.bic() !== undefined) {
                nummer.bic(nummer.bic().toUpperCase());
            }
        }

        zetRekeningnummerOm = function(nummer) {
            if(nummer.rekeningnummer() !== undefined) {
                var rek = nummer.rekeningnummer().toUpperCase();

                if(rek !== undefined && rek.length === 18) {
                    rek = rek.substring(0, 4) + " " +rek.substring(4, 8) + " " +rek.substring(8, 12) + " " +rek.substring(12, 16) + " " +rek.substring(16, 18);
                }

                nummer.rekeningnummer(rek);
            }
        };

        zetRekeningnummerOm(thisRek);
	};
});