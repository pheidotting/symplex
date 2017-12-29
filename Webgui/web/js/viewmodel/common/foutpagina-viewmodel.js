define(['jquery',
        'knockout',
        'viewmodel/common/menubalk-viewmodel',
        'text!../../../templates/commons/foutpagina.html'],
    function($, ko, menubalkViewmodel, html) {
    var _this = this;

    this.tekst = ko.observable();

    return function(tekst) {
        _this.menubalkViewmodel     = new menubalkViewmodel(null, '');

        $('#content').html(html);

        var index1 = tekst.indexOf('<body>');
        var index2 = tekst.indexOf('</body>');

        var gestripteTekst = tekst.substring(index1 + 6, index2);

        _this.tekst(gestripteTekst);

        ko.applyBindings(_this);
	};
});