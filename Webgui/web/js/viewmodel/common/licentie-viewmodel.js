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
                var aantaldagen = moment().add(7, 'days').diff(moment(_this.einddatumLicentie()), 'days');
                _this.melding('Let op! Over ' + aantaldagen + ' dag(en) loopt uw huidige licentie af!');
            }
        });
	};
});