define(['jquery',
        'knockout',
        'commons/3rdparty/log',
        'commons/commonFunctions',
        'redirect'],
    function ($, ko, log, commonFunctions, redirect) {

        return function onderlingeRelatieModel(modelData) {
            var thisonderlingeRelatie = this;

            thisonderlingeRelatie.id = ko.observable(modelData.id);
            thisonderlingeRelatie.relatieMet = ko.observable(modelData.relatieMet);
            thisonderlingeRelatie.idRelatieMet = ko.observable(modelData.idRelatieMet);
            thisonderlingeRelatie.soortRelatie = ko.observable(modelData.soortRelatie);

            thisonderlingeRelatie.navigeerNaar = function (ol) {
                redirect.redirect('BEHEREN_RELATIE', ol.idRelatieMet());
            };
        };
    });