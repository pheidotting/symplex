define(['redirect',
        'knockout',
        'navRegister'],
    function(redirect, ko, navRegister) {

    return function(identificatie, soortEntiteit) {
        var _this = this;

        this.identificatie = identificatie;
        if(soortEntiteit != null) {
            this.soortEntiteit = soortEntiteit.substring(0, 1).toUpperCase() + soortEntiteit.substring(1).toLowerCase();
        }

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

        this.naarHypotheken = function() {
            redirect.redirect('LIJST_HYPOTHEKEN', _this.identificatie);
        };

        this.nieuweHypotheek = function() {
            redirect.redirect('BEHEER_HYPOTHEEK', _this.identificatie);
        };

        this.belastingzaken = function() {
            redirect.redirect('BEHEER_BELASTINGZAKEN', _this.identificatie);
        };

        this.uitloggen = function() {
            localStorage.removeItem("symplexAccessToken");
            location.reload();
        };

        this.kantoorAfkorting = ko.observable();

        if(localStorage.getItem('symplexAccessToken')!=null){
            var base64Url = localStorage.getItem('symplexAccessToken').split('.')[1];
            var base64 = base64Url.replace('-', '+').replace('_', '/');
            var token = JSON.parse(window.atob(base64));

            $.ajax({
                type: "GET",
                url: navRegister.bepaalUrl('INGELOGDE_GEBRUIKER'),
                contentType: "application/json",
                data: {'userid' : token.sub},
                ataType: "json",
                async: false,
                beforeSend: function(request) {
                    request.setRequestHeader('url', window.location);
                },
                success: function (response, textStatus, request) {
                    _this.kantoorAfkorting(response.kantoorAfkorting.toLowerCase());
                }
            });
        }
	};
});