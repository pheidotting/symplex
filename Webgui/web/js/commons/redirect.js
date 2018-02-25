define([ "commons/3rdparty/log"],
    function(log) {
        var logger = log.getLogger('redirect');

        return {

            redirect: function(waarnaartoe, var1, var2, var3, var4, var5) {
                var vars = [{naam: 'INLOGGEN',                  url: '#inloggen'},
                            {naam: 'BEHEREN_RELATIE',           url: '#relatie'},
                            {naam: 'BEHEREN_BEDRIJF',           url: '#beherenBedrijf'},
                            {naam: 'DASHBOARD',                 url: '#dashboard'},
                            {naam: 'TAAK',                      url: '#taak'},
                            {naam: 'TAKEN',                     url: '#taken'},
                            {naam: 'BEHEREN_BEDRIJF',           url: '#beherenBedrijf'},
                            {naam: 'LIJST_POLISSEN',            url: '#polissen'},
                            {naam: 'BEHEER_POLIS',              url: '#polis'},
                            {naam: 'LIJST_SCHADES',             url: '#schades'},
                            {naam: 'BEHEER_SCHADE',             url: '#schade'},
                            {naam: 'LIJST_HYPOTHEKEN',          url: '#hypotheken'},
                            {naam: 'BEHEER_HYPOTHEEK',          url: '#hypotheek'},
                            {naam: 'BEHEER_BELASTINGZAKEN',     url: '#belastingzaken'}
                            ];

                var url = '';

                for (var i = 0; i < vars.length; i++) {
                    if(waarnaartoe === vars[i].naam) {
                        url = vars[i].url;
                        break;
                    }
                }

                if(var1 !== undefined){
                    logger.debug(var1);
                    url = url + "/" + var1;
                }
                if(var2 !== undefined){
                    logger.debug(var2);
                    url = url + "/" + var2;
                }
                if(var3 !== undefined){
                    logger.debug(var3);
                    url = url + "/" + var3;
                }
                if(var4 !== undefined){
                    logger.debug(var4);
                    url = url + "/" + var4;
                }
                if(var5 !== undefined){
                    logger.debug(var5);
                    url = url + "/" + var5;
                }

                logger.debug(url);
                document.location.hash = url;
                location.reload();
            }
        }
    }
);