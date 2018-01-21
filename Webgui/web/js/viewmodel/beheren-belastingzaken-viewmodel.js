define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'commons/3rdparty/log2',
		'redirect',
        'opmerkingenModel',
        'mapper/hypotheek-mapper',
        'service/gebruiker-service',
        'viewmodel/common/opmerking-viewmodel',
        'viewmodel/common/bijlage-viewmodel',
        'viewmodel/common/menubalk-viewmodel',
        'moment',
        'service/toggle-service',
        'viewmodel/common/taak-viewmodel',
        'mapper/bijlage-mapper',
        'mapper/groepbijlage-mapper',
        'knockout.validation',
        'knockoutValidationLocal'],
    function($, commonFunctions, ko, log, redirect, opmerkingenModel, hypotheekMapper, gebruikerService, opmerkingViewModel, bijlageViewModel, menubalkViewmodel, moment, toggleService, taakViewModel, bijlageMapper, groepbijlageMapper) {

    return function() {
        var _this = this;
        var logger = log.getLogger('beheren-hypotheek-viewmodel');
        var soortEntiteit = 'HYPOTHEEK';
		this.menubalkViewmodel      = null;

        this.basisEntiteit = null;
        this.basisId = null;
        this.bijlageModel           = null;
		this.hypotheek                = null;

		this.id = ko.observable();
        this.readOnly = ko.observable();
        this.notReadOnly = ko.observable();

        this.contracten = {};
        this.btw = [];
        this.jaarrekeningen = [];
        this.ibs = [];
        this.loonbelastingen = [];
        this.overigen = [];

        this.init = function(id, basisId, readOnly, basisEntiteit) {
            var deferred = $.Deferred();

            _this.readOnly(readOnly);
            _this.notReadOnly(!readOnly);
            _this.basisEntiteit = basisEntiteit;
            _this.id(id.identificatie);
            $.when(gebruikerService.leesRelatie(_this.id(), basisEntiteit)).then(function(data) {
                _this.basisId = data.identificatie;

                _this.contracten.bijlages = ko.observableArray();
                $.each(bijlageMapper.mapBijlages(data.belastingzaken.contracten.bijlages)(), function(i, bijlage){
                    _this.contracten.bijlages.push(bijlage);
                });

                _this.contracten.groepBijlages = ko.observableArray();
                $.each(groepbijlageMapper.mapGroepbijlages(data.belastingzaken.contracten.groepBijlages)(), function(i, groepbijlage){
                    _this.contracten.groepBijlages.push(groepbijlage);
                });

                $.each(data.belastingzaken.jaarrekeningen, function(i, b){
                    var jaarrekeningen = {};
                    jaarrekeningen.type = 'jaarrekeningen';

                    jaarrekeningen.jaartal = ko.observable(b.jaartal);
                    jaarrekeningen.bijlages = ko.observableArray();
                    $.each(bijlageMapper.mapBijlages(b.bijlages)(), function(i, bijlage){
                        jaarrekeningen.bijlages.push(bijlage);
                    });

                    jaarrekeningen.groepBijlages = ko.observableArray();
                    $.each(groepbijlageMapper.mapGroepbijlages(b.groepBijlages)(), function(i, groepbijlage){
                        jaarrekeningen.groepBijlages.push(groepbijlage);
                    });

                    _this.jaarrekeningen.push(jaarrekeningen);
                });
                _this.jaarrekeningen = _.sortBy(_this.jaarrekeningen, function(i){
                    return i.jaartal();
                });

                $.each(data.belastingzaken.ibs, function(i, b){
                    var ibs = {};
                    ibs.type = 'ibs';

                    ibs.jaartal = ko.observable(b.jaartal);
                    ibs.bijlages = ko.observableArray();
                    $.each(bijlageMapper.mapBijlages(b.bijlages)(), function(i, bijlage){
                        ibs.bijlages.push(bijlage);
                    });

                    ibs.groepBijlages = ko.observableArray();
                    $.each(groepbijlageMapper.mapGroepbijlages(b.groepBijlages)(), function(i, groepbijlage){
                        ibs.groepBijlages.push(groepbijlage);
                    });

                    _this.ibs.push(ibs);
                });
                _this.ibs = _.sortBy(_this.ibs, function(i){
                    return i.jaartal();
                });

                $.each(data.belastingzaken.btws, function(i, b){
                    var btw = {};
                    btw.type = 'btw';

                    btw.jaartal = ko.observable(b.jaartal);
                    btw.bijlages = ko.observableArray();
                    $.each(bijlageMapper.mapBijlages(b.bijlages)(), function(i, bijlage){
                        btw.bijlages.push(bijlage);
                    });

                    btw.groepBijlages = ko.observableArray();
                    $.each(groepbijlageMapper.mapGroepbijlages(b.groepBijlages)(), function(i, groepbijlage){
                        btw.groepBijlages.push(groepbijlage);
                    });

                    _this.btw.push(btw);
                });
                _this.btw = _.sortBy(_this.btw, function(i){
                    return i.jaartal();
                });

                $.each(data.belastingzaken.loonbelastingen, function(i, b){
                    var loonbelastingen = {};
                    loonbelastingen.type = 'loonbelastingen';

                    loonbelastingen.jaartal = ko.observable(b.jaartal);
                    loonbelastingen.bijlages = ko.observableArray();
                    $.each(bijlageMapper.mapBijlages(b.bijlages)(), function(i, bijlage){
                        loonbelastingen.bijlages.push(bijlage);
                    });

                    loonbelastingen.groepBijlages = ko.observableArray();
                    $.each(groepbijlageMapper.mapGroepbijlages(b.groepBijlages)(), function(i, groepbijlage){
                        loonbelastingen.groepBijlages.push(groepbijlage);
                    });

                    _this.loonbelastingen.push(loonbelastingen);
                });
                _this.loonbelastingen = _.sortBy(_this.loonbelastingen, function(i){
                    return i.jaartal();
                });

                $.each(data.belastingzaken.overigen, function(i, b){
                    var overigen = {};
                    overigen.type = 'overigen';

                    overigen.jaartal = ko.observable(b.jaartal);
                    overigen.bijlages = ko.observableArray();
                    $.each(bijlageMapper.mapBijlages(b.bijlages)(), function(i, bijlage){
                        overigen.bijlages.push(bijlage);
                    });

                    overigen.groepBijlages = ko.observableArray();
                    $.each(groepbijlageMapper.mapGroepbijlages(b.groepBijlages)(), function(i, groepbijlage){
                        overigen.groepBijlages.push(groepbijlage);
                    });

                    _this.overigen.push(overigen);
                });
                _this.overigen = _.sortBy(_this.overigen, function(i){
                    return i.jaartal();
                });

                _this.menubalkViewmodel     = new menubalkViewmodel(data.identificatie, "RELATIE");

                return deferred.resolve();
            });

            this.toonOfVerberg = function(a) {
                if($('#groepBijlages'+a.type+a.jaartal()).is(':visible')) {
                    $('#groepBijlages'+a.type+a.jaartal()).hide();
                    $('#bijlages'+a.type+a.jaartal()).hide();
                    $('#'+a.type+a.jaartal()+'dicht').hide();
                    $('#'+a.type+a.jaartal()+'open').show();
                } else {
                    $('#groepBijlages'+a.type+a.jaartal()).show();
                    $('#bijlages'+a.type+a.jaartal()).show();
                    $('#'+a.type+a.jaartal()+'dicht').show();
                    $('#'+a.type+a.jaartal()+'open').hide();
                }
            };

            this.toonOfVerbergHoofd = function(a) {
                if($('#' + a).is(':visible')) {
                    $('#' + a).hide();
                } else {
                    $('#' + a).show();
                }
            };

            return deferred.promise();
        };
    };
});