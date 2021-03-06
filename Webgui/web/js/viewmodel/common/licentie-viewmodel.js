define(['commons/3rdparty/log',
        'knockout',
        'service/common/licentie-service',
        'moment'],
    function (log, ko, licentieService, moment) {

        return function () {
            var _this = this;
            var logger = log.getLogger('licentie-viewmodel');

            this.einddatumLicentie = ko.observable();
            this.tonenLicentieWaarschuwing = ko.observable();
            this.melding = ko.observable();
            this.maxAantalMedewerkers = ko.observable();

            if (localStorage.getItem('symplexLicentie') == null || localStorage.getItem('symplexLicentie') == 'undefined') {
                $.when(licentieService.einddatum()).then(function (licentie) {
                    verwerkLicentie(licentie)
                });
            } else {
                let lic = JSON.parse(localStorage.getItem('symplexLicentie'));
                verwerkLicentie(lic);
            }

            function verwerkLicentie(licentie) {
                localStorage.setItem('symplexLicentie', JSON.stringify(licentie));
                _this.einddatumLicentie(licentie.einddatum);
                _this.tonenLicentieWaarschuwing(_this.einddatumLicentie() != null && moment(_this.einddatumLicentie()).isBefore(moment().add(7, 'days')));

                if (_this.tonenLicentieWaarschuwing()) {
                    var aantaldagen = moment().diff(moment(_this.einddatumLicentie()), 'days');

                    var melding;

                    if (moment().isBefore(moment(_this.einddatumLicentie())) && aantaldagen > 0) {
                        melding = 'Let op! Over ' + aantaldagen + ' dag(en) loopt uw huidige licentie af!"';
                    } else if (aantaldagen == 0) {
                        melding = 'Let op! Vandaag loopt uw huidige licentie af!';
                    } else {
                        melding = 'Let op! Uw licentie is verlopen, haal een nieuwe!';
                    }

                    logger.info(melding);
                    _this.melding(melding + ' Klik <a href="instellingen.html#licenties">hier</a> om een nieuwe licentie aan te schaffen');
                }

                if (moment(_this.einddatumLicentie()).isBefore(moment()) && window.location.pathname != '/dashboard.html' && window.location.pathname != '/instellingen.html') {
                    window.location = 'instellingen.html#licenties';
                }

                //Bepalen max aantal medewerkers
                var aantal = 999999;
                switch (licentie.soort) {
                    case 'administratiekantoor':
                    case 'brons':
                        aantal = 2;
                        break;
                    case 'zilver':
                        aantal = 5;
                        break;
                }

                _this.maxAantalMedewerkers(aantal);
            }
        }
    });