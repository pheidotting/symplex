define(['redirect'],
    function(redirect) {

    return function(identificatie, soortEntiteit) {
        var _this = this;

        this.identificatie = identificatie;
        this.soortEntiteit = soortEntiteit.substring(0, 1).toUpperCase() + soortEntiteit.substring(1).toLowerCase();

        this.getSoortEntiteit = function() {
            return _this.soortEntiteit;
        }

        this.naarRelatiegegevens = function() {
            redirect.redirect('BEHEREN_RELATIE', _this.identificatie);
        };

        this.naarPolissen = function() {
            redirect.redirect('LIJST_POLISSEN', _this.identificatie);
        };

        this.nieuwePolis = function() {
            redirect.redirect('BEHEER_POLIS', _this.identificatie);
        };

        this.nieuweSchade = function() {
            redirect.redirect('BEHEER_SCHADE', _this.identificatie);
        };

        this.naarSchades = function() {
            redirect.redirect('LIJST_SCHADES', _this.identificatie);
        };
	};
});