define(["commons/3rdparty/log2",
        "navRegister",
        'knockout',
        'redirect',
        'viewmodel/common/foutpagina-viewmodel'],
    function(log, navRegister, ko, redirect, foutpaginaViewmodel) {
        var logger = log.getLogger('repository');

        return {
            voerUitGet: function(url, data, foutmeldingOnderdrukken) {
                var deferred = $.Deferred();

                if(data == null) {
                    logger.trace('uitvoeren get op url \'' + url + '\'');
                } else {
                    logger.trace('uitvoeren get op url \'' + url + '\' met data \'' + JSON.stringify(data) + '\'');
                }

                $.ajax({
                    type: "GET",
                    url: url,
                    contentType: "application/json",
                    data: data,
                    ataType: "json",
                    async: false,
                    beforeSend: function(request) {
                        if(localStorage.getItem('symplexAccessToken')!=null){
                            request.setRequestHeader('Authorization', localStorage.getItem('symplexAccessToken'));
                        }
                    },
                    success: function (response, textStatus, request) {
                        if( request.getResponseHeader('Authorization') != null ) {
                            localStorage.setItem("symplexAccessToken", request.getResponseHeader('Authorization'));
                        }

                        return deferred.resolve(response);
                    },
                    error: function (response, textStatus, request) {
                        if (response.status.toString() == '401') {
                            location.href = 'inloggen.html';
                        }else{
                            if(request != '500' && request != 'Server Error' && request != 'Service Unavailable') {
                            //TODO 'Not found' afvangen
                                if( request.getResponseHeader('Authorization') != null ) {
                                    localStorage.setItem("symplexAccessToken", request.getResponseHeader('Authorization'));
                                }
                            } else if(!foutmeldingOnderdrukken) {
                                foutpagina = new foutpaginaViewmodel(response.responseText);
                            }

                            return deferred.resolve(response);
                        }
                    }
                });

                return deferred.promise();
            },

            voerUitPost: function(url, data, trackAndTraceId){
                var deferred = $.Deferred();

                $.ajax({
                    type: "POST",
                    url: url,
                    contentType: "application/json",
                    data: data,
                    ataType: "json",
                    async: false,
                    beforeSend: function(request){
                        request.setRequestHeader('trackAndTraceId', trackAndTraceId);
                        if(localStorage.getItem('symplexAccessToken')!=null){
                            request.setRequestHeader('Authorization', localStorage.getItem('symplexAccessToken'));
                        }
                    },
                    success: function (response, textStatus, request) {
                        if( request.getResponseHeader('Authorization') != null ) {
                            localStorage.setItem("symplexAccessToken", request.getResponseHeader('Authorization'));
                        }

                        return deferred.resolve(response);
                    },
                    error: function (response) {
                        if (response.status.toString() == '401') {
                            location.href = 'inloggen.html';
                        }else{
                            foutpagina = new foutpaginaViewmodel(response.responseText);
                        }
                    }
                });

                return deferred.promise();
            },

            leesTrackAndTraceId: function() {
                var deferred = $.Deferred();

//                $.get(navRegister.bepaalUrl('TRACKANDTRACEID'))
//                .done(function(response) {
//                    return deferred.resolve(response);
//                })
//                .fail(function(response){
                    return deferred.resolve(guid());
//                });


                return deferred.promise();
            }
        }

        function guid() {
          function s4() {
            return Math.floor((1 + Math.random()) * 0x10000)
              .toString(16)
              .substring(1);
          }
          return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
            s4() + '-' + s4() + s4() + s4();
        }
    }
);