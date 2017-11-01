define(['jquery',
        'model/communicatieproduct',
        'model/bijlage',
        'knockout',
        'commons/3rdparty/log',
        'commons/commonFunctions',
        'dataServices',
        'redirect'],
    function ($, Communicatieproduct, Bijlage, ko, log, commonFunctions, dataServices, redirect) {

	return function communicatieproductenModel (data){
		_thisCommunicatieproducten = this;

		_thisCommunicatieproducten.communicatieproducten = ko.observableArray();
		$.each(data, function(i, item) {
            var communicatieproduct = new Communicatieproduct(item);

            dataServices.leesCommunicatieProduct(communicatieproduct.id()).done(function(cm){
                communicatieproduct.beantwoordenKnopTonen(false);
                communicatieproduct.bewerkenKnopTonen(false);
                if ((cm.type === 'JsonIngaandeEmail' || cm.type === 'JsonIngaandeBrief')) {
                    communicatieproduct.beantwoordenKnopTonen(true);
                }
                if ((cm.type === 'JsonUitgaandeEmail' || cm.type === 'JsonUitgaandeBrief') && cm.datumTijdVerzending === null) {
                    communicatieproduct.bewerkenKnopTonen(true);
                }

                _thisCommunicatieproducten.communicatieproducten().push(communicatieproduct);
                _thisCommunicatieproducten.communicatieproducten.valueHasMutated();
            });

        });

		_thisCommunicatieproducten.markeerAlsGelezen = function(communicatieproduct){
		    if (communicatieproduct.ongelezenIndicatie()) {
    		    dataServices.markeerAlsGelezen(communicatieproduct.id());
		    }
            communicatieproduct.klasse('');
		};

		_thisCommunicatieproducten.beantwoorden = function(communicatieproduct){
            sessionStorage.setItem('antwoordOp', communicatieproduct.id())

    		redirect.redirect('BEHEREN_RELATIE', communicatieproduct.entiteitId(), 'communicatie', '0');
		};

		_thisCommunicatieproducten.bewerken = function(communicatieproduct){
    		redirect.redirect('BEHEREN_RELATIE', communicatieproduct.entiteitId(), 'communicatie', communicatieproduct.id());
		};
    };
});