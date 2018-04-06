define(['jquery',
        "knockout",
        "js/model/taak/taken",
        "commons/3rdparty/log",
        'commons/block',
        'dataServices',
        "js/model/taak/taakAanvullenAdresAfhandelen",
        "js/model/taak/taakAanvullenBsnAfhandelen",
        "js/model/taak/taakAanvullenEmailadresAfhandelen"],
    function ($, ko, Taken, logger, block, dataServices, TaakAanvullenAdresAfhandelen, TaakAanvullenBsnAfhandelen, taakAanvullenEmailadresAfhandelen) {

        return function (id) {
            block.block();
            logger.debug("Afhandelen taak met id " + id);

            dataServices.leesTaak(id).done(function (data) {
                $('#content').load("templates/taken/AfhandelenTaakTemplate.html", function () {
                    $('#taakContent').load("templates/taken/Afhandelen" + data.soortTaak + ".html", function () {
                        if (data.soortTaak == "AanvullenAdresTaak") {
                            ko.applyBindings(new TaakAanvullenAdresAfhandelen(data));
                        } else if (data.soortTaak == "AanvullenBsnTaak") {
                            ko.applyBindings(new TaakAanvullenBsnAfhandelen(data));
                        } else if (data.soortTaak == "AanvullenEmailadresTaak") {
                            ko.applyBindings(new taakAanvullenEmailadresAfhandelen(data));
                        }
                    });
                });
            });
        };
    });