define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'model/relatie',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log2',
		'redirect',
		'repository/common/repository',
        'service/gebruiker-service',
        'mapper/gebruiker-mapper',
        'service/common/wachtwoord-service',
        'service/mijn-gegevens-service'],
    function($, commonFunctions, ko, Relatie, functions, block, log, redirect, repository, gebruikerService, gebruikerMapper, wachtwoordService, mijnGegevensService) {

    return function() {
        window.setInterval(commonFunctions.haalIngelogdeGebruiker, 300000);
        $('#uitloggen').click(function() {
            commonFunctions.uitloggen();
        });

        var _this = this;
        var logger = log.getLogger('mijn-gegevens-viewmodel');
        this.medewerker
        this.nieuwWachtwoord = ko.observable();
        this.wachtwoordNogmaals = ko.observable().extend();
        this.wachtwoordAccoord = ko.observable(true);
        this.wachtwoordSterkte = ko.observable('');

        this.init = function() {
            var deferred = $.Deferred();

            $.when(commonFunctions.haalIngelogdeGebruiker()).then(function(ingelogdeGebruiker) {
                if(ingelogdeGebruiker == null) {
                    location.href = 'index.html#inloggen';
                    return deferred.resolve();
                } else {
                    return gebruikerService.leesMedewerker(ingelogdeGebruiker.id);
                }
            }).then(function(medewerker) {
                logger.debug(medewerker);
                _this.medewerker = gebruikerMapper.mapRelatie(medewerker);

                logger.debug('_this.medewerker ' + ko.toJSON(_this.medewerker));

                return deferred.resolve();
            });

            return deferred.promise();
        };

        this.wachtwoordGewijzigd = function() {
            if(_this.nieuwWachtwoord() !== '') {
                _this.wachtwoordSterkte(checkPassStrength(_this.nieuwWachtwoord()));

                if(_this.wachtwoordNogmaals() != null && _this.wachtwoordNogmaals() !== '') {
                    if(_this.nieuwWachtwoord() === _this.wachtwoordNogmaals()) {
                        _this.wachtwoordAccoord(true);
                    } else {
                        _this.wachtwoordAccoord(false);
                    }
                } else {
                    _this.wachtwoordAccoord(true);
                }
            } else {
                _this.wachtwoordSterkte('');
            }
        };

        this.tonenWachtwoordSterkte = ko.computed(function(){
            return _this.wachtwoordSterkte() != '';
        });

        this.wachtwoordNietAccoordTonen = ko.computed(function(){
            return !_this.wachtwoordAccoord();
        });

        this.magDoorgaan = ko.computed(function(){
            return _this.nieuwWachtwoord() !== '' && ((_this.wachtwoordNogmaals() != null || _this.wachtwoordNogmaals() !== '') && _this.wachtwoordAccoord());
        });

        this.opslaan = function() {
            logger.debug('Opslaan');
            if(_this.nieuwWachtwoord()) {
                wachtwoordService.verstuur({'identificatie': _this.medewerker.identificatie(), 'wachtwoord': _this.nieuwWachtwoord()});
            }

            $.when(mijnGegevensService.opslaan(_this.medewerker)).then(function(){
                commonFunctions.plaatsMelding('De gegevens zijn opgeslagen');
                commonFunctions.haalIngelogdeGebruiker();
            });
        };

        function scorePassword(pass) {
            var score = 0;
            if (!pass)
                return score;

            // award every unique letter until 5 repetitions
            var letters = [];
            for (var i=0; i<pass.length; i++) {
                letters[pass[i]] = (letters[pass[i]] || 0) + 1;
                score += 5.0 / letters[pass[i]];
            }

            // bonus points for mixing it up
            var variations = {
                digits: /\d/.test(pass),
                lower: /[a-z]/.test(pass),
                upper: /[A-Z]/.test(pass),
                nonWords: /\W/.test(pass),
            }

            variationCount = 0;
            for (var check in variations) {
                if (variations.hasOwnProperty(check)) {
                    variationCount += (variations[check] == true) ? 1 : 0;
                }
            }
            score += (variationCount - 1) * 10;

            return parseInt(score);
        }

        function checkPassStrength(pass) {
            var score = scorePassword(pass);
            if (score > 80)
                return "sterk";
            if (score > 60)
                return "goed";
            if (score >= 30)
                return "zwak";

            return "zeer zwak";
        }
	};
});