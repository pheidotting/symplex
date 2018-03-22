define(['commons/3rdparty/log',
        'knockout',
        'service/common/licentie-service',
        'moment',
        'navRegister',
        'lodash'],
    function(log, ko, licentieService, moment, navRegister, _) {

    return function() {
        var _this = this;

        this.einddatumLicentie = ko.observable();
        this.tonenLicentieWaarschuwing = ko.observable();
        this.melding = ko.observable();

        $.when(licentieService.einddatum()).then(function(einddatum){
            _this.einddatumLicentie(einddatum.einddatum);
            _this.tonenLicentieWaarschuwing(_this.einddatumLicentie() != null && moment(_this.einddatumLicentie()).isBefore(moment().add(7, 'days')));

            if(_this.tonenLicentieWaarschuwing()){
                var aantaldagen = moment().diff(moment(_this.einddatumLicentie()), 'days');

                if(aantaldagen > 0) {
                    _this.melding('Let op! Over ' + aantaldagen + ' dag(en) loopt uw huidige licentie af!');
                } else if(aantaldagen == 0){
                    _this.melding('Let op! Vandaag loopt uw huidige licentie af!');
                } else {
                    _this.melding('Let op! Uw licentie is verlopen, haal een nieuwe!');
                }
            }

            if(moment(_this.einddatumLicentie()).isBefore(moment())){
                if(window.location.pathname != '/zoeken.html' && window.location.pathname != '/instellingen.html'){
                    window.location = 'instellingen.html#licenties';
                }
            }
        });
	};
});