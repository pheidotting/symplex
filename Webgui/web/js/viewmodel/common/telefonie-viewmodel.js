define(['commons/3rdparty/log',
        'knockout',
        'service/common/telefonie-service',
        'mapper/telefonie-mapper',
        'moment',
        'navRegister',
        'lodash'],
    function(log, ko, telefonieService, telefonieMapper, moment, navRegister, _) {

    return function(recordings) {
        var _this = this;
        var logger = log.getLogger('telefonie-viewmodel');
    	var korteNetnummers = ['010', '013', '014', '015', '020', '023', '024', '026', '030', '033', '035', '036', '038', '040', '043', '045', '046', '050', '053', '055', '058', '070', '071', '072', '073', '074', '075', '076', '077', '078', '079'];

		this.gesprekken = ko.observableArray();

        if(recordings && recordings.length !== 0) {
            var gesprekken = [];
            _.chain(telefonieMapper.mapGesprekken(recordings)())
            .orderBy('tijdstip')
            .flatten()
            .map(function(g) {
                g.maand = g.tijdstip().format('YYYY-MM');
                return g;
            })
            .map(function(g) {
                return g;
            })
            .groupBy(function(g) {
                return g.maand;
            })
            .each(function(g) {
                gesprekken.push({'maand':g[0].maand, 'gesprekken':g});
            })
            .orderBy('maand')
            .value();

            $.each(gesprekken, function(i, gesprek){
                _this.gesprekken.push(gesprek);
            });
		}

		this.maakUrl = function(bestandsnaam) {
		    return navRegister.bepaalUrl('TELEFONIE_DOWNLOAD') + '/' + bestandsnaam() + '.a';
		}

		this.formatDatumTijd = function(datumTijd) {
		    return datumTijd().format('DD-MM-YYYY HH:mm');
		}

        this.zetTelefoonnummerOm = function(nummer) {
            if(nummer != null) {
                var tel = nummer();
                if(tel !== null && tel !== undefined && tel.length === 10) {
                    if(tel.substring(0, 2) === "06") {
                        //06 nummers
                        tel = tel.substring(0, 2) + " - " + tel.substring(2, 4) + " " + tel.substring(4, 6) + " " + tel.substring(6, 8) + " " + tel.substring(8, 10);
                    } else if(_this.contains(korteNetnummers, tel.substring(0, 3))) {
                         //3 cijferig kengetal
                        tel = tel.substring(0, 3) + " - " + tel.substring(3, 6) + " " + tel.substring(6, 8) + " " + tel.substring(8, 10);
                    } else {
                        //4 cijferig kengetal
                        tel = tel.substring(0, 4) + " - " + tel.substring(4, 6) + " " + tel.substring(6, 8) + " " + tel.substring(8, 10);
                    }

                    return tel;
                }
            }
        };

        this.toonOfVerberg = function(a) {
            if($('#gesprekken'+a.maand).is(':visible')) {
                $('#gesprekken'+a.maand).hide();
                $('#gesprekken'+a.maand+'dicht').hide();
                $('#gesprekken'+a.maand+'open').show();
            } else {
                $('#gesprekken'+a.maand).show();
                $('#gesprekken'+a.maand+'dicht').show();
                $('#gesprekken'+a.maand+'open').hide();
            }
        };

        this.contains = function (a, obj) {
            for (var i = 0; i < a.length; i++) {
                if (a[i] === obj) {
                    return true;
                }
            }
            return false;
        }

	};
});