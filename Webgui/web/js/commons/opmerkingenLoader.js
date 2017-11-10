define(["commons/3rdparty/log"],
    function(log) {

        return function(id){
            var _thisOpmerkingen = this;

            _thisOpmerkingen.init = function(){
                log.debug("Instantieren opmerkingen");

                var deferred = $.Deferred();

                $('#opmerkingen').load('templates/commons/opmerkingen.html', function() {
                    log.debug('opmerkingen geladen');
                    return deferred.resolve();
                });

                return deferred.promise();
            };
        }
    }
);