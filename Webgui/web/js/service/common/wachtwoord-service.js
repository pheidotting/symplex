define(['commons/3rdparty/log',
        'navRegister',
        'knockout',
        'repository/common/repository',
        '../../node_modules/crypto-js/crypto-js',
        '../../node_modules/crypto-js/sha3'],
    function (log, navRegister, ko, repository, CryptoJs, sha3) {
        var logger = log.getLogger('wachtwoord-service');

        return {
            verstuur: function (gebruikersnaamEnWachtwoord) {
                logger.debug('Versturen wachtwoord');
                var deferred = $.Deferred();

                var request = {
                    identificatie: gebruikersnaamEnWachtwoord.identificatie,
                    wachtwoord: encryptWachtwoord(gebruikersnaamEnWachtwoord.wachtwoord)
                }

                logger.debug(request);
                logger.debug(JSON.stringify(request));

                repository.voerUitPost(navRegister.bepaalUrl('WIJZIG_WACHTWOORD'), JSON.stringify(request));

                return deferred.promise();
            }
        }

        function encryptWachtwoord(wachtwoord) {
            logger.debug('encrypt');

            return sha3(wachtwoord).toString(CryptoJs.enc.Base64);
        }
    }
);