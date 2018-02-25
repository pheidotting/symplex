define(['commons/3rdparty/log',
        'commons/commonFunctions',
        'model/groepbijlages',
        'model/bijlage',
        'commons/block',
        'navRegister'],
    function(log, commonFunctions, Groepbijlages, Bijlage, block, navRegister) {
        var logger = log.getLogger('fileUpload');

        return {
            init: function(id){
                logger.debug("Instantieren file upload");

                if(id != null && id !== 0) {
                    var deferred = $.Deferred();

                    $('#fileUpload').load('templates/commons/fileUpload.html', function() {
                        return deferred.resolve();
                    });

                    return deferred.promise();
                }
            },

            uploaden: function(){
                var deferred = $.Deferred();

                var formData = new FormData($('#fileUploadForm')[0]);
                commonFunctions.uploadBestand(formData, navRegister.bepaalUrl('UPLOAD_BIJLAGE')).done(function(response) {
                    logger.debug(JSON.stringify(response));

                    $('#bijlageFile').val("");

                    if(response.bijlage != null) {
                         ret = new Bijlage(response.bijlage);
                    } else {
                         ret = new Groepbijlages(response.groepBijlages);
                    }

                    return deferred.resolve(ret);
                });

                return deferred.promise();
            }
        }
    }
);