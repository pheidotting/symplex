define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'commons/3rdparty/log',
		'redirect',
        'mapper/hypotheek-mapper',
        'service/gebruiker-service',
        'service/belastingzaken-service',
        'viewmodel/common/opmerking-viewmodel',
        'viewmodel/common/bijlage-viewmodel',
        'viewmodel/common/menubalk-viewmodel',
        'moment',
        'service/toggle-service',
        'mapper/bijlage-mapper',
        'mapper/groepbijlage-mapper',
        'fileUpload',
        'lodash',
        'knockout.validation',
        'knockoutValidationLocal'],
    function($, commonFunctions, ko, log, redirect, hypotheekMapper, gebruikerService, belastingzakenService, opmerkingViewModel, bijlageViewModel, menubalkViewmodel, moment, toggleService, bijlageMapper, groepbijlageMapper, fileUpload, _) {

    return function() {
        var _this = this;
        var logger = log.getLogger('beheren-hypotheek-viewmodel');
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
        this.toonJaren = ko.observable(false);

        this.init = function(id, basisId, readOnly, basisEntiteit) {
            var deferred = $.Deferred();

            _this.readOnly(readOnly);
            _this.notReadOnly(!readOnly);
            _this.basisEntiteit = basisEntiteit;
            _this.id(id.identificatie);

            $.when(belastingzakenService.lees(_this.id())).then(function(result) {
                var data = result.data;
                _this.basisEntiteit = result.soort;
                _this.basisId = data.identificatie;

                _this.contracten.bijlages = ko.observableArray();
                if(data.belastingzaken.contracten != null){
                    _this.contracten.identificatie = data.belastingzaken.contracten.identificatie;
                    $.each(bijlageMapper.mapBijlages(data.belastingzaken.contracten.bijlages)(), function(i, bijlage){
                        _this.contracten.bijlages.push(bijlage);
                    });

                    _this.contracten.groepBijlages = ko.observableArray();
                    $.each(groepbijlageMapper.mapGroepbijlages(data.belastingzaken.contracten.groepBijlages)(), function(i, groepbijlage){
                        _this.contracten.groepBijlages.push(groepbijlage);
                    });
                }

                $.each(data.belastingzaken.jaarrekeningen, function(i, b){
                    var jaarrekeningen = {};
                    jaarrekeningen.type = 'jaarrekeningen';

                    jaarrekeningen.jaartal = ko.observable(b.jaartal);
                    jaarrekeningen.bijlages = ko.observableArray();
                    jaarrekeningen.identificatie = b.identificatie;
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
                    ibs.identificatie = b.identificatie;
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
                    btw.identificatie = b.identificatie;
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
                    loonbelastingen.identificatie = b.identificatie;
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
                    overigen.identificatie = b.identificatie;
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

                _this.menubalkViewmodel     = new menubalkViewmodel(data.identificatie, _this.basisEntiteit);

                return deferred.resolve();
            });

            this.toonOfVerberg = function(a) {
                var jaartal = '';
                var type = 'contracten';
                if(a.basisEntiteit == null){
                    jaartal = a.jaartal();
                    type = a.type;
                }
                if($('#groepBijlages'+type+jaartal).is(':visible')) {
                    $('#groepBijlages'+type+jaartal).hide();
                    $('#bijlages'+type+jaartal).hide();
                    $('#'+type+jaartal+'dicht').hide();
                    $('#'+type+jaartal+'open').show();
                } else {
                    $('#groepBijlages'+type+jaartal).show();
                    $('#bijlages'+type+jaartal).show();
                    $('#'+type+jaartal+'dicht').show();
                    $('#'+type+jaartal+'open').hide();
                }
            };

            this.toonOfVerbergHoofd = function(a) {
                if($('#' + a).is(':visible')) {
                    $('#' + a).hide();
                } else {
                    $('#' + a).show();
                }
            };

            this.bepaalJaren = function(){
                var soort = $('#soort').val();
                if(soort == '' || soort == 'Contracten'){
                    _this.toonJaren(false);
                    if(soort == 'Contracten'){
                        $('#identificatie').val(_this.contracten.identificatie);
                    }else{
                        $('#identificatie').val('');
                    }
                }else{
                    _this.toonJaren(true);

                    var $select = $('#jaren');
                    $select.empty();
                    $('<option>', { value : '' }).text('').appendTo($select);

                    if(soort == "Jaarrekening"){
                        $.each(_this.jaarrekeningen, function(key, value){
                            $('<option>', { value : value.jaartal() }).text(value.jaartal()).appendTo($select);
                        });
                    }
                    if(soort == "IB"){
                        $.each(_this.ibs, function(key, value){
                            $('<option>', { value : value.jaartal() }).text(value.jaartal()).appendTo($select);
                        });
                    }
                    if(soort == "Btw"){
                        $.each(_this.btws, function(key, value){
                            $('<option>', { value : value.jaartal() }).text(value.jaartal()).appendTo($select);
                        });
                    }
                    if(soort == "Loonbelasting"){
                        $.each(_this.loonbelastingen, function(key, value){
                            $('<option>', { value : value.jaartal() }).text(value.jaartal()).appendTo($select);
                        });
                    }
                    if(soort == "Overig"){
                        $.each(_this.overigen, function(key, value){
                            $('<option>', { value : value.jaartal() }).text(value.jaartal()).appendTo($select);
                        });
                    }
                }
            };

            this.selecteerJaar = function(){
                var soort = $('#soort').val();
                var $select = $('#jaren');

                if(soort == "Jaarrekening"){
                    $.each(_this.jaarrekeningen, function(key, value){
                        if(value.jaartal() == $select.val()){
                            $('#identificatie').val(value.identificatie);
                        }
                    });
                }
                if(soort == "IB"){
                    $.each(_this.ibs, function(key, value){
                        if(value.jaartal() == $select.val()){
                            $('#identificatie').val(value.identificatie);
                        }
                    });
                }
                if(soort == "Btw"){
                    $.each(_this.btws, function(key, value){
                        if(value.jaartal() == $select.val()){
                            $('#identificatie').val(value.identificatie);
                        }
                    });
                }
                if(soort == "Loonbelasting"){
                    $.each(_this.loonbelastingen, function(key, value){
                        if(value.jaartal() == $select.val()){
                            $('#identificatie').val(value.identificatie);
                        }
                    });
                }
                if(soort == "Overig"){
                    $.each(_this.overigen, function(key, value){
                        if(value.jaartal() == $select.val()){
                            $('#identificatie').val(value.identificatie);
                        }
                    });
                }
            };

            _this.nieuweUpload = function (){
                logger.debug("Nieuwe bijlage upload");

                fileUpload.uploaden().done(function(uploadResultaat){
                    logger.debug(ko.toJSON(uploadResultaat));

                    location.reload();
                });
            };

            return deferred.promise();
        };
    };
});