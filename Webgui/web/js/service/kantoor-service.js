define(["commons/3rdparty/log",
        "navRegister",
        'knockout',
        'repository/common/repository',
        'repository/kantoor-repository'],
    function (log, navRegister, ko, repository, kantoorRepository) {

        return {
            aanmelden: function (kantoor) {
                return kantoorRepository.aanmelden(kantoor);
            },

            lees: function () {
                return kantoorRepository.lees();
            },

            opslaan: function (data) {
                return kantoorRepository.opslaan(data);
            }
        }
    });