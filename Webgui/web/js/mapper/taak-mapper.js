define(['jquery',
        'model/taak',
        'model/wijzigingtaak',
        'commons/3rdparty/log',
        'knockout',
        'moment',
        'underscore'],
    function ($, Taak, WijzigingTaak, log, ko, moment, _) {
        return {
            mapTaak: function (r) {
                return mappen(r);
            },

            mapTaken: function (data, afgeronde) {
                var taken = ko.observableArray([]);

                _.chain(data)
                .filter(function(taak) {
                    if(afgeronde){
                        return taak.tijdstipAfgehandeld != null;
                    }
                    return taak.tijdstipAfgehandeld == null;;
                })
                .each(function(taak) {
                    taken.push(mappen(taak));
                }).value();

                return taken;
            }
        }

        function mappen(data) {
            if (data != null) {
                var taak = new Taak();

                taak.identificatie(data.identificatie);
                taak.tijdstipCreatie(moment(data.tijdstipCreatie).format("DD-MM-YYYY HH:mm"));
                if(data.deadline != null && data.deadline != ''){
                    taak.deadline(moment(data.deadline).format("DD-MM-YYYY"));
                }
                if(data.tijdstipAfgehandeld != null && data.tijdstipAfgehandeld != ''){
                    taak.tijdstipAfgehandeld(moment(data.tijdstipAfgehandeld).format("DD-MM-YYYY HH:mm"));
                }
                taak.titel(data.titel);
                taak.omschrijving(data.omschrijving);
                taak.entiteitId(data.entiteitId);
                taak.soortEntiteit(data.soortEntiteit);
                taak.toegewezenAan(data.toegewezenAan);

                $.each(data, function (i, r) {
                    var wijzigingTaak = new WijzigingTaak();

                    wijzigingTaak.identificatie(data.identificatie);
                    wijzigingTaak.taakStatus(data.taakStatus);
                    wijzigingTaak.toegewezenAan(data.toegewezenAan);
                    wijzigingTaak.tijdstip(data.tijdstip);

                    taak.wijzigingTaaks.push(wijzigingTaak);
                });

                return taak;
            }
        }
    }
);
