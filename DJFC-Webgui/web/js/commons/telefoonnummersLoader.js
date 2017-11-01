define(["commons/3rdparty/log"],
    function(log) {

        return function(){
            var _thisTelefonnummers = this;

            _thisTelefonnummers.init = function(){
                log.debug("Instantieren telefoonnummers");

                var deferred = $.Deferred();

                $('#telefoonnummers').load('templates/commons/telefoonnummers.html', function() {
                    return deferred.resolve();
                });

                return deferred.promise();
            };
        }
    }
);