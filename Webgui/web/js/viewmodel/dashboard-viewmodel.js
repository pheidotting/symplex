                                                                  define(['jquery',
                                                                          'commons/commonFunctions',
                                                                          'knockout',
                                                                          'model/relatie',
                                                                          'model/zoekvelden',
                                                                          'commons/commonFunctions',
                                                                          'commons/block',
                                                                          'commons/3rdparty/log',
                                                                          'redirect',
                                                                          'service/toggle-service',
                                                                          'service/zoeken-service',
                                                                          'service/gebruiker-service',
                                                                          'mapper/zoekresultaat-mapper',
                                                                          'viewmodel/common/menubalk-viewmodel',
                                                                          'viewmodel/common/licentie-viewmodel',
                                                                          'repository/common/repository',
                                                                          'navRegister'],
                                                                      function ($, commonFunctions, ko, Relatie, zoekvelden, functions, block, log, redirect, toggleService, zoekenService, gebruikerService, zoekresultaatMapper, menubalkViewmodel, LicentieViewmodel, repository, navRegister) {

                                                                          return function () {
                                                                              commonFunctions.checkNieuweVersie();
                                                                              var _this = this;
                                                                              var logger = log.getLogger('dashboard-viewmodel');

                                                                              this.zoekvelden = new zoekvelden();

                                                                              this.menubalkViewmodel = null;
                                                                              this.licentieViewmodel = null;

                                                                              this.aantalOpenSchades = ko.observable();
                                                                              this.aantalRelaties = ko.observable();

                                                                              this.init = function () {
                                                                                  var deferred = $.Deferred();

                                                                                  _this.menubalkViewmodel = new menubalkViewmodel();
                                                                                  _this.licentieViewmodel = new LicentieViewmodel();

                                                                                  logger.info('Ophalen Dashboard');
                                                                                  repository.voerUitGet(navRegister.bepaalUrl('DASHBOARD')).then(function (result) {
                                                                                      _this.aantalOpenSchades(result.openSchades.length);
                                                                                      _this.aantalRelaties(result.relaties.length + result.bedrijven.length);

                                                                                      return deferred.resolve();
                                                                                  });

                                                                                  return deferred.promise();
                                                                              };

                                                                              this.zoeken = function () {
                                                                                  logger.debug('we gaan zoeken');
                                                                                  window.location = 'zoeken.html#zoeken/' +btoa(ko.toJSON(_this.zoekvelden));
                                                                              };

                                                                              this.maaklink = function (index, se) {
                                                                                  var postLink = '';
                                                                                  var soortEntiteit = se;
                                                                                  if (index) {
                                                                                      var entiteit = _this.zoekResultaat()[index()];
                                                                                      soortEntiteit = entiteit.soortEntiteit().toLowerCase();

                                                                                      postLink = '/' + entiteit.identificatie();
                                                                                  }
                                                                                  return 'beheren.html#' + soortEntiteit + postLink;
                                                                              };

                                                                              this.nieuweRelatie = function () {
                                                                                  window.location = _this.maaklink(0, 'relatie');
                                                                              };

                                                                              this.nieuwBedrijf = function () {
                                                                                  window.location = _this.maaklink(0, 'bedrijf');
                                                                              };
                                                                          };
                                                                      }
                                                                  );