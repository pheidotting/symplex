define(['commons/3rdparty/log',
        'knockout'],
    function (log, ko) {

        return function (relatie, polis, schade, polissen, schaden, hypotheek, hypotheken, belastingzaken) {
            var _this = this;

            _this.relatie = relatie;
            _this.polis = polis;
            _this.schade = schade;
            _this.polissen = polissen;
            _this.schaden = schaden;
            _this.hypotheek = hypotheek;
            _this.hypotheken = hypotheken;
            _this.belastingzaken = belastingzaken;

            _this.relatienaamTonen = function () {
                return _this.relatie != null;
            };

            _this.relatienaam = function () {
                var naam = '';
                if (relatie.naam == null) {
                    naam = relatie.voornaam;
                    naam += " (";
                    naam += relatie.roepnaam;
                    naam += ") ";
                    if (relatie.tussenvoegsel != null && relatie.tussenvoegsel != '') {
                        naam += relatie.tussenvoegsel;
                        naam += " ";
                    }
                    naam += relatie.achternaam;
                } else {
                    naam = relatie.naam;
                }
                return naam;
            };

            _this.polisnummerTonen = function () {
                return _this.polis != null;
            };

            _this.polisnummer = function () {
                return this.polis.polisNummer;
            };

            _this.schadenummerTonen = function () {
                return _this.schade != null;
            };

            _this.schadenummer = function () {
                return this.schade.schadeNummerMaatschappij;
            };

            _this.lijstpolissenTonen = function() {
                return !!_this.polissen;
            };

            _this.lijstschadenTonen = function() {
                return !!_this.schaden;
            };

            _this.lijsthypothekenTonen = function() {
                return !!_this.hypotheken;
            };

            _this.hypotheeknummer = function() {
                return this.hypotheek.leningNummer;
            };

            _this.hypotheeknummerTonen = function() {
                return this.hypotheek != null;
            };

            _this.belastingzakenTonen = function() {
                return !!_this.belastingzaken;
            };

        };
    });