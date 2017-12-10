define(['jquery',
        'knockout',
        'commons/validation',
        'moment',
        'commons/opmaak'],
	function($, ko, validation, moment, opmaak) {

	return function hypotheek() {
		_this = this;

		_this.id = ko.observable();
		_this.identificatie = ko.observable();
		_this.soortEntiteit = ko.observable();
		_this.bank = ko.observable();
		_this.boxI = ko.observable().extend({number: true});
		_this.boxIII = ko.observable().extend({number: true});
		_this.relatie = ko.observable();
		_this.hypotheekVorm = null;
		_this.hypotheekBedrag = ko.observable().extend({required: true, number: true});
		_this.rente = ko.observable().extend({number: true});
		_this.marktWaarde = ko.observable().extend({number: true});
		_this.onderpand = ko.observable();
		_this.koopsom = ko.observable().extend({number: true});
		_this.vrijeVerkoopWaarde = ko.observable().extend({number: true});
		_this.taxatieDatum = ko.observable();
		_this.wozWaarde = ko.observable().extend({number: true});
		_this.waardeVoorVerbouwing = ko.observable().extend({number: true});
		_this.waardeNaVerbouwing = ko.observable().extend({number: true});
		_this.ingangsDatum = ko.observable().extend({validation: {
	        validator: function (val) {
	        	if(val != undefined){
	        		return validation.valideerDatum(val);
	        	}else{
	        		return true;
	        	}
	        },
	        message: 'Juiste invoerformaat is : dd-mm-eejj'
	    }});
		_this.eindDatum = ko.observable().extend({validation: {
	        validator: function (val) {
	        	if(val != undefined){
	        		return validation.valideerDatum(val);
	        	}else{
	        		return true;
	        	}
	        },
	        message: 'Juiste invoerformaat is : dd-mm-eejj'
	    }});
		_this.duur = ko.observable().extend({number: true});
		_this.ingangsDatumRenteVastePeriode = ko.observable().extend({validation: {
	        validator: function (val) {
	        	if(val != undefined){
	        		return validation.valideerDatum(val);
	        	}else{
	        		return true;
	        	}
	        },
	        message: 'Juiste invoerformaat is : dd-mm-eejj'
	    }});
		_this.eindDatumRenteVastePeriode = ko.observable().extend({validation: {
	        validator: function (val) {
	        	if(val != undefined){
	        		return validation.valideerDatum(val);
	        	}else{
	        		return true;
	        	}
	        },
	        message: 'Juiste invoerformaat is : dd-mm-eejj'
	    }});
		_this.duurRenteVastePeriode = ko.observable().extend({number: true});
		_this.leningNummer = ko.observable();
		_this.omschrijving = ko.observable();
//		_this.idDiv = ko.computed(function() {
//	        return "collapsable" + _this.id();
//		}, this);
//		_this.idDivLink = ko.computed(function() {
//	        return "#collapsable" + _this.id();
//		}, this);
//		_this.className = ko.computed(function() {
//		    if(_this.ingangsDatum != null) {
//                var datum = moment(_this.ingangsDatum());
//                var tijd = moment(datum).fromNow();
//                if(tijd.substr(tijd.length - 3) == "ago"){
//                    return "panel-title";
//                }else{
//                    return "polisNietActief panel-title";
//    			}
//			}
//		}, this);
//		_this.titel = ko.computed(function() {
//			var hypVorm;
//			if (_this.hypotheekVorm != null) {
//    			hypVorm = _this.hypotheekVorm.hypotheekVorm;
//             }
//
//			var omschrijving = "";
//			if(_this.leningNummer() != null){
//				omschrijving += _this.leningNummer() + " - ";
//			}
//			omschrijving += hypVorm + " - ";
//			if(_this.bank != null){
//				omschrijving += _this.bank() + " - ";
//			}
//			omschrijving += _this.rente() + "% - ";
//			if(_this.hypotheekBedrag() != null && _this.hypotheekBedrag() != '') {
//		    	omschrijving += opmaak.maakBedragOp(_this.hypotheekBedrag());
//            }
//
//			return omschrijving;
//		}, this);
		_this.gekoppeldeHypotheek = ko.observable();
	};

	function SoortHypotheek(data){
		var _soortHypotheek = this;

		_soortHypotheek.id = ko.observable(data.id);
		_soortHypotheek.omschrijving = ko.observable(data.omschrijving);
	}
});