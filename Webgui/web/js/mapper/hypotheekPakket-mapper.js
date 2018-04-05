define(['jquery',
        'model/hypotheekPakket',
        'commons/3rdparty/log',
        'knockout',
        'mapper/hypotheek-mapper'],
    function ($, HypotheekPakket, log, ko, hypotheekMapper) {
        return {
            mapHypotheekPakket: function (r, lijstSoortenHypotheek) {
                return mappen(r, lijstSoortenHypotheek);
            },

            mapHypotheekPakketten: function (data, lijstSoortenHypotheek) {
                var hypotheekPakketten = ko.observableArray([]);

                $.each(data, function (i, r) {
                    hypotheekPakketten.push(mappen(r, lijstSoortenHypotheek));
                });

                return hypotheekPakketten;
            }
        }

        function mappen(data, lijstSoortenHypotheek) {
            if (data != null) {
                var hypotheekPakket = new HypotheekPakket();

                hypotheekPakket.id(data.id);
                hypotheekPakket.totaalBedrag(data.totaalBedrag);
                hypotheekPakket.titel(data.titel);
                hypotheekPakket.hypotheken = hypotheekMapper.mapHypotheken(data.hypotheken, lijstSoortenHypotheek);

                return hypotheekPakket;
            }
        }
    }
);
