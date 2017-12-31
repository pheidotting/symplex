define(['jquery',
        'knockout',
        'commons/validation',
        'moment',
        'commons/opmaak'],
	function($, ko, validation, moment, opmaak) {

	return function hypotheek() {
		_this = this;

		_this.id = ko.observable();
		_this.parentIdentificatie = ko.observable();
		_this.identificatie = ko.observable();
		_this.soortEntiteit = ko.observable();
		_this.bank = ko.observable();
		_this.boxI = ko.observable();
		_this.boxIII = ko.observable();
		_this.relatie = ko.observable();
		_this.hypotheekVorm = null;
		_this.hypotheekBedrag = ko.observable().extend({required: true});
		_this.rente = ko.observable().extend({number: true});
		_this.marktWaarde = ko.observable();
		_this.onderpand = ko.observable();
		_this.koopsom = ko.observable();
		_this.vrijeVerkoopWaarde = ko.observable();
		_this.taxatieDatum = ko.observable();
		_this.wozWaarde = ko.observable();
		_this.waardeVoorVerbouwing = ko.observable();
		_this.waardeNaVerbouwing = ko.observable();
		_this.ingangsDatum = ko.observable();
		_this.eindDatum = ko.observable();
		_this.duur = ko.observable().extend({number: true});
		_this.ingangsDatumRenteVastePeriode = ko.observable();
		_this.eindDatumRenteVastePeriode = ko.observable();
		_this.duurRenteVastePeriode = ko.observable();//.extend({number: true});
		_this.leningNummer = ko.observable();
		_this.omschrijving = ko.observable();
		_this.gekoppeldeHypotheek = ko.observable();
	};

	function SoortHypotheek(data){
		var _soortHypotheek = this;

		_soortHypotheek.id = ko.observable(data.id);
		_soortHypotheek.omschrijving = ko.observable(data.omschrijving);
	}
});