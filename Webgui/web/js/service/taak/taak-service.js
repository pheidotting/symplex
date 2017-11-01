define(["commons/3rdparty/log2",
        "navRegister",
        'repository/taak/taak-repository',
        'repository/common/repository',
        'repository/todoist-repository',
        'service/toggle-service',
        'service/gebruiker-service',
        'service/bedrijf-service',
        'underscore',
        'moment'],
    function(log, navRegister, taakRepository, repository, todoistRepository, toggleService, gebruikerService, bedrijfService, _, moment) {
        var logger = log.getLogger('taak-service');

        var projectPrefix;
        var oAuthCode;

        return {
            afgerondeTaken: function(soortEntiteit, entiteitId) {
                return taakRepository.afgerondeTaken(soortEntiteit, entiteitId);
            },

            genereerPrefixVoorTaakNaam: function(relatieId, bedrijfId) {
                var deferred = $.Deferred();

                if(relatieId != null) {
                    $.when(gebruikerService.leesRelatie(relatieId)).then(function(relatie){
                        var naam = relatie.voornaam + ' ';
                        if(relatie.tussenvoegsel != '') {
                            naam += relatie.tussenvoegsel + ' ';
                        }
                        naam += relatie.achternaam + ' - ';

                        return deferred.resolve(naam);
                    });
                } else {
                    $.when(bedrijfService.leesBedrijf(bedrijfId)).then(function(bedrijf){
                        return deferred.resolve(bedrijf.naam + ' - ');
                    });
                }

                return deferred.promise();
            },

             openTakenBijRelatie: function(relatieId) {
                 return taakRepository.openTakenBijRelatie(relatieId);
             },

             aantalOpenTaken: function() {
                return $.when(this.ophalenTaken(this.getPrefix())).then(function(todoist) {
                    return _.chain(todoist.projecten).pluck('items').flatten().value().length;
                });
             },

             alleTaken: function(soortEntiteit, entiteitId, relatieId, bedrijfId) {
                var deferred = $.Deferred();
                var _this = this;

                $.when(
                    todoistRepository.prefix(),
                    _this.genereerPrefixVoorTaakNaam(relatieId, bedrijfId)
                ).then(function(
                    prefix,
                    naamPrefix
                ) {
                    projectPrefix = prefix;
                    $.when(_this.ophalenTaken(_this.getPrefix())).then(function(todoist) {
                        var items = _.chain(todoist.projecten)
                            .pluck('items')
                            .flatten()
                            .filter(function(item){
                                var labels = _.chain(item.labels)
                                    .map(function(label){
                                        return label.omschrijving;
                                    }).value();

                                return _.contains(labels, soortEntiteit) && _.contains(labels, entiteitId);
                            })
                            .map(function(item){
                                item.omschrijving = item.omschrijving.replace(naamPrefix, '');
                                return item;
                            })
                            .value();

                            return deferred.resolve(items);
                    });
                });

                return deferred.promise();
             },

             voegTaakToe: function(soortEntiteit, entiteitId, tekst, duetime, relatieId, bedrijfId) {
                var deferred = $.Deferred();
                var prefix = this.getPrefix();
                var _this = this;

                logger.debug('Toevoegen taak');
                $.when(_this.ophalenTaken(this.getPrefix())).then(function(todoist) {
                    $.when(_this.zoekProject(todoist.projecten, soortEntiteit, prefix)).then(function(proj) {
                        logger.debug('nu de labels er bij zoeken');
                        $.when(_this.zoekLabel(todoist.labels, entiteitId), _this.zoekLabel(todoist.labels, soortEntiteit)).then(function(idLabel, soortEntiteitLabel){
                            if(proj == null) {
                                logger.debug('Eerst dus een project aanmaken, deze werd namelijk niet gevonden.');
                                $.when(_this.toevoegenProject(prefix, soortEntiteit), _this.genereerPrefixVoorTaakNaam(relatieId, bedrijfId)).then(function(projectid, naamPrefix) {
                                    logger.debug('Project aangemaakt met id ' + projectid);
                                    proj = projectid;
                                    $.when(_this.voegItemToe(naamPrefix + tekst, proj, idLabel, soortEntiteitLabel, duetime)).then(function(itemId){
                                        return deferred.resolve(proj);
                                    });
                                });
                            } else {
                                $.when(_this.genereerPrefixVoorTaakNaam(relatieId, bedrijfId)).then(function(naamPrefix) {
                                    return _this.voegItemToe(naamPrefix + tekst, proj, idLabel, soortEntiteitLabel, duetime);
                                }).then(function(itemId){
                                    return deferred.resolve(proj);
                                });
                            }
                        });
                     });
                 });

                 return deferred.promise()
             },

             voegItemToe: function(tekst, proj, idLabel, soortEntiteitLabel, duetime){
                var deferred = $.Deferred();
                var _this = this;

                $.when(_this.toevoegenItem(tekst, proj, idLabel, soortEntiteitLabel)).then(function(itemId) {
                    logger.debug('Item aangemaakt met id ' + itemId);
                    if(duetime != null) {
                        $.when(_this.toevoegenReminder(itemId, duetime)).then(function(reminderid){
                            logger.debug('Reminder aangemaakt met id ' + reminderid);
                            return deferred.resolve(itemId);
                        });
                    } else {
                        return deferred.resolve(itemId);
                    }
                });

                return deferred.promise();
             },

             getPrefix: function() {
                if(projectPrefix) {
                    return projectPrefix + '-';
                }
                return '';
             },

             zoekProject: function(taken, projectnaam, prefix) {
                var _this = this;

                var project = _this.filterProjectenOpEntiteit(taken, projectnaam, prefix);
                if(project == null) {
                    var meervoudnaam;
                    var laatsteletter = projectnaam.substr(projectnaam.length - 1);
                    if (_.contains(['a', 'e', 'i', 'o', 'u'], laatsteletter)) {
                        meervoudnaam = projectnaam + 's';
                    } else if (laatsteletter == 's') {
                        meervoudnaam = projectnaam + 'sen';
                    } else {
                        meervoudnaam = projectnaam + 'en';
                    }

                    project = _this.filterProjectenOpEntiteit(taken, meervoudnaam, prefix);
                }

                if(project != null) {
                    return project.id;
                }
             },

             filterProjectenOpEntiteit: function(taken, naam, prefix) {
                return _.chain(taken)
                .filter(function(project) {
                    if (prefix) {
                        return project.naam.startsWith(prefix);
                    } else {
                        return !project.naam.startsWith('{{');
                    }
                })
                .filter(function(project) {
                    return project.naam.endsWith(naam);
                })
                .first().value();
             },

             zoekLabel: function(labels, naam) {
                var deferred = $.Deferred();
                var _this = this;

                var label = _.chain(labels)
                .filter(function(label) {
                    return label != null && label.name == naam;
                })
                .pluck('id')
                .flatten()
                .first().value();

                if(label != null) {
                    return label;
                } else {
                    $.when(_this.toevoegenLabel(naam)).then(function(id){
                        return deferred.resolve(id);
                    });
                }

                return deferred.promise();
             },

            toevoegenLabel: function(naam) {
                var deferred = $.Deferred();

                $.when(
                    this.authoriseerTodoist(),
                    repository.leesTrackAndTraceId(),
                    repository.leesTrackAndTraceId()
                ).then(function(oAuthCode, temp_id, uuid) {
                    var commands = [];
                    var command = {};
                    command.type = 'label_add';
                    command.temp_id = temp_id;
                    command.uuid = uuid;
                    command.args = {};
                    command.args.name = naam;

                    commands.push(command);

                    var url = 'https://todoist.com/API/v7/sync?token=' + oAuthCode + '&commands=' + JSON.stringify(commands);

                    $.ajax(
                        {
                            type: "GET",
                            url: url
                        }
                    )
                    .done(function(response) {
                        return deferred.resolve(response.temp_id_mapping[temp_id]);
                    });
                });

                return deferred.promise();
            },

             toevoegenProject: function(prefix, naam) {
                var deferred = $.Deferred();

                $.when(
                    this.authoriseerTodoist(),
                    repository.leesTrackAndTraceId(),
                    repository.leesTrackAndTraceId()
                ).then(function(oAuthCode, temp_id, uuid) {
                    var commands = [];
                    var command = {};
                    command.type = 'project_add';
                    command.temp_id = temp_id;
                    command.uuid = uuid;
                    command.args = {};
                    command.args.name = prefix + naam;

                    commands.push(command);

                    var url = 'https://todoist.com/API/v7/sync?token=' + oAuthCode + '&commands=' + JSON.stringify(commands);

                    $.ajax(
                        {
                            type: "GET",
                            url: url
                        }
                    )
                    .done(function(response) {
                        return deferred.resolve(response.temp_id_mapping[temp_id]);
                    });
                });

                return deferred.promise();
             },

             toevoegenItem: function(content, projectid, label1, label2) {
                var deferred = $.Deferred();

                $.when(
                    this.authoriseerTodoist(),
                    repository.leesTrackAndTraceId(),
                    repository.leesTrackAndTraceId()
                ).then(function(oAuthCode, temp_id, uuid) {
                    var commands = [];
                    var command = {};
                    command.type = 'item_add';
                    command.temp_id = temp_id;
                    command.uuid = uuid;
                    command.args = {};
                    command.args.content = content;
                    command.args.project_id = projectid;

                    var labels = [];
                    labels.push(label1);
                    labels.push(label2);

                    command.args.labels = labels;

                    commands.push(command);

                    var url = 'https://todoist.com/API/v7/sync?token=' + oAuthCode + '&commands=' + JSON.stringify(commands);

                    $.ajax(
                        {
                            type: "GET",
                            url: url
                        }
                    )
                    .done(function(response) {
                        return deferred.resolve(response.temp_id_mapping[temp_id]);
                    });
                });

                return deferred.promise();
             },

             toevoegenReminder: function(itemId, duetime) {
                var deferred = $.Deferred();

                $.when(
                    this.authoriseerTodoist(),
                    repository.leesTrackAndTraceId(),
                    repository.leesTrackAndTraceId()
                ).then(function(oAuthCode, temp_id, uuid) {

                    var commands = [];
                    var command = {};
                    command.type = 'reminder_add';
                    command.temp_id = temp_id;
                    command.uuid = uuid;
                    command.args = {};
                    command.args.item_id = itemId;
                    command.args.service = 'email';
                    command.args.due_date_utc = moment(duetime, 'DD-MM-YYYY HH:mm').subtract(1, 'hours').format().substring(0, 16);

                    commands.push(command);

                    var url = 'https://todoist.com/API/v7/sync?token=' + oAuthCode + '&commands=' + JSON.stringify(commands);

                    $.ajax(
                        {
                            type: "GET",
                            url: url
                        }
                    )
                    .done(function(response) {
                        return deferred.resolve(response.temp_id_mapping[temp_id]);
                    });
                });

                return deferred.promise();
             },

             ophalenTaak: function(id) {
                var deferred = $.Deferred();

                var url = 'https://todoist.com/API/v7/items/get?token=' + oAuthCode + '&item_id=' + id;

                $.ajax(
                    {
                        type: "GET",
                        url: url
                    }
                )
                .done(function(response) {
                    return deferred.resolve(response);
                });

                return deferred.promise();
            },

             ophalenLabel: function(id) {
                var deferred = $.Deferred();

                var url = 'https://todoist.com/API/v7/labels/get?token=' + oAuthCode + '&label_id=' + id;

                $.ajax(
                    {
                        type: "GET",
                        url: url
                    }
                )
                .done(function(response) {
                    return deferred.resolve(response);
                });

                return deferred.promise();
            },

             ophalenMedewerker: function(id) {
                var deferred = $.Deferred();

                var url = 'https://todoist.com/API/v7/collaborators/get?token=' + oAuthCode + '&collaboratorId=' + id;

                $.ajax(
                    {
                        type: "GET",
                        url: url
                    }
                )
                .done(function(response) {
                    return deferred.resolve(response);
                });

                return deferred.promise();
            },

            haalAfgerondeTaken: function() {
                var _this = this;
                var deferred = $.Deferred();

                $.when(_this.ophalenAfgerondeTaken()).then(function(afgerondeTaken){
                    return deferred.resolve(afgerondeTaken);
                });

                return deferred.promise();
            },

            ophalenAfgerondeTaken: function() {
                var deferred = $.Deferred();
                var _this = this;

                $.when(todoistRepository.prefix(), this.authoriseerTodoist()).then(function(prefix, oAuthCode) {
                    logger.debug('Todoist geauthoriseerd, code = ' + oAuthCode);

                    var url = 'https://todoist.com/API/v7/completed/get_all?token=' + oAuthCode;
                    var aantal;

                    $.ajax(
                        {
                            type: "GET",
                            url: url
                        }
                    )
                    .done(function(response) {
                        var opTeHalenItems = _.chain(response.items)
                        .map(function(item){
                            item.project = _.find(response.projects, function(project) {
                                return project.id == item.project_id;
                            });

                            return item;
                        })
                        .filter(function(item) {
                            if(prefix) {
                                return item.project.name.startsWith(prefix);
                            } else {
                                return !item.project.name.startsWith('{{');
                            }
                        }).value()
                        ;

                        aantal = opTeHalenItems.length;

                        var items = [];

                        $.each(opTeHalenItems, function(i, item){
                            $.when(_this.ophalenTaak(item.id)).then(function(opgehaaldItem){
                                opgehaaldItem.item.notities = _.chain(opgehaaldItem.notes)
                                    .filter(function(note){
                                        return note.item_id == item.id;
                                    })
                                    .map(function(note){
                                        var n = {};
                                        n.tekst = note.content;
                                        n.tijdstip = moment(note.tijdstip).format().replace('+01:00', '');//'YYYY-MM-DD HH:mm:ss');
                                        n.medewerker = note.posted_uid + '';
                                        n.todoistId = note.id;

                                        return n;
                                    }).value();
                                aantal--;
                                var aantalLabels = opgehaaldItem.item.labels.length;
                                var labels = opgehaaldItem.item.labels;
                                $.each(labels, function(i, opTeHalenLabel){
                                    opgehaaldItem.item.labels = [];

                                    $.when(_this.ophalenLabel(opTeHalenLabel)).then(function(label) {
                                        opgehaaldItem.item.labels.push(label.label.name);
                                        aantalLabels--;

                                        if(aantalLabels == 0) {
                                            opgehaaldItem.item.project = opgehaaldItem.project;
                                            items.push(opgehaaldItem.item);
                                        }

                                        if(aantal == 0 && aantalLabels == 0) {
                                            return deferred.resolve(items);
                                        }
                                    });

                                });
                            });
                        });
                    });
                });

                return deferred.promise();
            },

             ophalenTaken: function(prefix) {
                var deferred = $.Deferred();

                $.when(this.authoriseerTodoist()).then(function(oAuthCode) {
                    logger.debug('Todoist geauthoriseerd, code = ' + oAuthCode);

                    var types = [];
                    types.push('all');

                    var url = 'https://todoist.com/API/v7/sync?token=' + oAuthCode + '&sync_token=*&resource_types=' + JSON.stringify(types);

                    var projecten = [];
                    var todoist = {};

                    $.ajax(
                        {
                            type: "GET",
                            url: url
                        }
                    )
                    .done(function(response) {
                        _.chain(response.projects)
                        .filter(function(project){
                            if(prefix) {
                                return project.name.startsWith(prefix);
                            } else {
                                return !project.name.startsWith('{{');
                            }
                        })
                        .map(function(todoistproject) {
                            var project = {};
                            project.id = todoistproject.id;
                            project.naam = todoistproject.name;
                            project.items =  _.chain(response.items)
                                .filter(function(todoistitem) {
                                    return todoistitem.project_id == project.id && todoistproject.is_deleted == 0;
                                })
                                .map(function(todoistitem) {
                                    var item = {};
                                    item.id = todoistitem.id;
                                    item.omschrijving = todoistitem.content;
                                    item.notities = filterNotities(response.notes, item.id, null, response.collaborators);
                                    item.projectId = project.id;

                                    item.labels = _.chain(response.labels)
                                        .filter(function(todoistlabel) {
                                            return _.contains(todoistitem.labels, todoistlabel.id) && todoistlabel.is_deleted == 0;
                                        })
                                        .map(function(todoistlabel) {
                                            var label = {};
                                            label.id = todoistlabel.id;
                                            label.omschrijving = todoistlabel.name;

                                            return label
                                        })
                                        .value();

                                    item.reminders = _.chain(response.reminders)
                                        .filter(function(todoistreminder) {
                                            return item.id == todoistreminder.item_id && todoistreminder.is_deleted == 0;
                                        })
                                        .map(function(todoistreminder) {
                                            var reminder = {};
                                            reminder.id = todoistreminder.id;

                                            if (todoistreminder.due_date_utc != null) {
                                                reminder.due_date = moment(todoistreminder.due_date_utc);
                                            }

                                            return reminder
                                        })
                                        .value();

                                    return item;
                                }).value();

                                projecten.push(project);
                            });

                            todoist.projecten = projecten;
                            todoist.labels = response.labels
                        return deferred.resolve(todoist);
                    });
                });

                return deferred.promise();
            },

            authoriseerTodoist: function() {
                var deferred = $.Deferred();

                $.when(
                    toggleService.isFeatureBeschikbaar('TODOIST'),
                    gebruikerService.leesOAuthCode(),
                    repository.leesTrackAndTraceId(),
                    todoistRepository.clientIdEnClientSecret(),
                    todoistRepository.prefix()
                ).then(function(
                    toggleBeschikbaar,
                    oAuthCodeOpgehaald,
                    secret,
                    clientIdEnClientSecret,
                    prefix
                ) {
                    projectPrefix = prefix;
                    if (oAuthCodeOpgehaald == null || oAuthCodeOpgehaald == '') {
                        oAuthCodeOpgehaald = oAuthCode;
                    } else {
                        oAuthCode = oAuthCodeOpgehaald;
                    }


                    if (toggleBeschikbaar) {
                        if(!QueryString().code && !oAuthCode && !QueryString().error) {
                            var redirect_url = window.location.href;
                            var url = 'https://todoist.com/oauth/authorize?client_id=' + clientIdEnClientSecret.clientId + '&scope=data:read,data:read_write,data:delete&state=' + secret + '&redirect_uri=' + encodeURIComponent(redirect_url);

                            window.location.href = url;
                        }

                        if(QueryString().error) {
                            logger.debug('Gebruiker heeft waarschijnlijk geen toestemming gegeven voor koppeling met Todoist');

                            var urlTerug = window.location.href;
                            urlTerug = urlTerug.replace('error=' + QueryString().error, '');

                            window.location.href = urlTerug;
                        }

                        if(QueryString().code) {
                            var state = QueryString().state;
                            var code = QueryString().code;

                            var urlTerugNaar = window.location.href;
                            urlTerugNaar = urlTerugNaar.replace('state=' + state, '');
                            urlTerugNaar = urlTerugNaar.replace('code=' + code, '');
                            urlTerugNaar = urlTerugNaar.replace('?&', '');

                            var data = {};
                            data.client_id = clientIdEnClientSecret.clientId;
                            data.client_secret = clientIdEnClientSecret.clientSecret;
                            data.code = code;
                            data.redirect_uri = urlTerugNaar;

                            $.when(oAuthCodeOphalen(data)).then(function(ccode) {
                                oAuthCode = ccode;
                                return gebruikerService.opslaanOAuthCode(ccode);
                            }).then(function(){
                                window.location.href = urlTerug;
                            });
                        }

                        if(oAuthCode) {
                            return deferred.resolve(oAuthCode);
                        }
                    }
                });

                return deferred.promise();
             }
        }

        function filterNotities(notities, itemId, projectId, collaborators) {
            return _.chain(notities)
                .filter(function(todoistnote) {
                    if (itemId != null) {
                        return todoistnote.item_id == itemId && todoistnote.is_deleted == 0;
                    } else {
                        return todoistnote.project_id == projectId && todoistnote.is_deleted == 0;
                    }
                })
                .map(function(todoistnote) {
                    var note = {};
                    note.id = todoistnote.id;
                    note.omschrijving = todoistnote.content;
                    note.tijdstip = todoistnote.posted;

                    note.user = _.chain(collaborators)
                        .filter(function(todoistUser) {
                            return todoistnote.posted_uid == todoistUser.id;
                        })
                        .map(function(todoistUser) {
                            return todoistUser.full_name;
                        })
                        .first()
                        .value();

                    return note;
                })
                .value();
        }

        function QueryString() {
          // This function is anonymous, is executed immediately and
          // the return value is assigned to QueryString!
          var query_string = {};
          var query = window.location.search.substring(1);
          var vars = query.split("&");
          for (var i=0;i<vars.length;i++) {
            var pair = vars[i].split("=");
                // If first entry with this name
            if (typeof query_string[pair[0]] === "undefined") {
              query_string[pair[0]] = decodeURIComponent(pair[1]);
                // If second entry with this name
            } else if (typeof query_string[pair[0]] === "string") {
              var arr = [ query_string[pair[0]],decodeURIComponent(pair[1]) ];
              query_string[pair[0]] = arr;
                // If third or later entry with this name
            } else {
              query_string[pair[0]].push(decodeURIComponent(pair[1]));
            }
          }
          return query_string;
        }

        function oAuthCodeOphalen(data) {
            return todoistRepository.oauthToken(data);
//            var url="../dejonge/rest/medewerker/todoist/oauthToken";
//
//            return repository.voerUitPost(url, JSON.stringify(data), '');
        }
    }
);