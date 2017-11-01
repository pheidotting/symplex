define(["commons/3rdparty/log2",
        "navRegister",
        'knockout',
        'redirect'],
    function(log, navRegister, ko, redirect) {
        var logger = log.getLogger('repository');

        return {
            voerUitGet: function(url, data){
                var deferred = $.Deferred();

                if(data == null) {
                    logger.trace('uitvoeren get op url \'' + url + '\'');
                } else {
                    logger.trace('uitvoeren get op url \'' + url + '\' met data \'' + JSON.stringify(data) + '\'');
                }
                $.get(url, data)
                .done(function(response) {
                    return deferred.resolve(response);
                })
                .fail(function(response){
                    if (response.status.toString().startsWith(40)) {
                        location.href = 'inloggen.html';
                    }
                    return deferred.reject();
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
                    beforeSend: function(xhr){xhr.setRequestHeader('trackAndTraceId', trackAndTraceId);},
                    success: function (response) {
                        return deferred.resolve(response);
                    },
                    error: function (response) {
                        if (response.status.toString().startsWith(40)) {
                            location.href = 'inloggen.html';
                        }else{
                            return deferred.resolve(response);
                        }
                    }
                });

                return deferred.promise();
            },

            leesTrackAndTraceId: function() {
                var deferred = $.Deferred();

                $.get(navRegister.bepaalUrl('TRACKANDTRACEID'))
                .done(function(response) {
                    return deferred.resolve(response);
                })
                .fail(function(response){
                    return deferred.resolve(guid());
                });


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