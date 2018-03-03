define(['commons/3rdparty/log',
        'commons/commonFunctions',
        'model/groepbijlages',
        'model/bijlage',
        'mapper/bijlage-mapper',
        'mapper/groepbijlage-mapper',
        'commons/block',
        'navRegister'],
    function(log, commonFunctions, Groepbijlages, Bijlage, bijlageMapper, groepbijlageMapper, block, navRegister) {
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
                         ret = bijlageMapper.mapBijlage(response.bijlage);
                    } else {
                         ret = groepbijlageMapper.mapGroepbijlage(response.groepBijlages);
                    }

                    return deferred.resolve(ret);
                });

                return deferred.promise();
            }
        }
    }
);