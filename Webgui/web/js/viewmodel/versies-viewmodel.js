define(['jquery',
        'commons/commonFunctions',
        'knockout',
        'model/relatie',
        'model/zoekvelden',
        'commons/commonFunctions',
        'commons/block',
        'commons/3rdparty/log',
        'redirect',
        'service/versies-service',
        'viewmodel/common/menubalk-viewmodel',
        'moment'],
    function ($, commonFunctions, ko, Relatie, zoekvelden, functions, block, log, redirect, versiesService, menubalkViewmodel, moment) {

        return function () {
            var _this = this;
            this.menubalkViewmodel = null;

            this.versie = ko.observable();
            this.releasenotes = ko.observable();

            this.init = function (identificatie) {
                var deferred = $.Deferred();

                _this.menubalkViewmodel = new menubalkViewmodel();

                $.when(versiesService.lees(identificatie)).then(function (result) {
                    _this.versie(result.versienummer);
                    _this.releasenotes(result.releasenotes.replace('\n', '<br />'));

                    return deferred.resolve();
                });

                return deferred.promise();
            };
        };
    });