requirejs.config({
    paths: {
        commons: '../commons',
        jquery: '../node_modules/jquery/dist/jquery.min',
        sammy: '../commons/3rdparty/sammy-0.7.6',
        moment: '../commons/3rdparty/moment',
    	js: '../js',
    	pages: '../pages',
    	model: '../js/model',
    	knockout: '../commons/3rdparty/knockout',
        knockoutValidation: '../commons/3rdparty/knockoutValidation/knockout.validation',
        blockUI: '../commons/3rdparty/blockui',
        jqueryUI: '../commons/3rdparty/jquery-ui-1.10.3',
        dataServices: '../js/commons/dataServices',
        navRegister: '../js/commons/navRegister',
        fileUpload: '../js/commons/fileUpload',
        opmerkingenLoader: '../js/commons/opmerkingenLoader',
        opmerkingenModel: '../js/commons/opmerkingenModel',
        adressenLoader: '../js/commons/adressenLoader',
        adressenModel: '../js/commons/adressenModel',
        telefoonnummersLoader: '../js/commons/telefoonnummersLoader',
        telefoonnummersModel: '../js/commons/telefoonnummersModel',
        redirect: '../js/commons/redirect',
        service: '../js/service',
        repository: '../js/repository',
        view: '../js/view',
        viewmodel: '../js/viewmodel',
        mapper: '../js/mapper',
        util: '../js/util',
        underscore: '../js/commons/thirdparty/underscore'
    },
	shim: {
        "knockoutValidation": ["knockout"],
        'blockUI': ['jquery'],
        'jqueryUI': ['jquery']
    },
    config: {
        moment: {
            noGlobal: true
        }
//    },
//    packages: {[
//            {
//                name: '../node_modules/crypto-js',
//                location: '../node_modules/crypto-js/crypto-js',
//                main: 'index'
//            }
//        ]
        }
});

requirejs(['jquery',
           'sammy',
           'commons/commonFunctions',
           'js/inloggen',
           'js/view/lijst-bedrijven-view',
           'js/beherenRelatie',
           'js/beherenBedrijf',
           'js/taak/taken',
           'js/taak/afhandelenTaak',
           'js/view/dashboard-view',
           'js/view/lijst-relaties-view',
           'js/lijstBedrijven'],
function ($, Sammy, commonFunctions, inloggen, lijstBedrijven, beherenRelatie, beherenBedrijf, taken, afhandelenTaak, dashboard, lijstRelatiesView) {
	commonFunctions.haalIngelogdeGebruiker();
	window.setInterval(commonFunctions.haalIngelogdeGebruiker, 300000);

	$('#uitloggen').click(function() {
		commonFunctions.uitloggen();
	});

	var app = new Sammy('body');

	app.route('GET', '#taken', function() {
		new taken();
	});

	app.route('GET', '#dashboard', function() {
		dashboard.init();
	});

	app.route('GET', '#taak/:id', function() {
		var id = this.params['id'];
		new afhandelenTaak(id);
	});

	app.route('GET', '#inloggen', function() {
		new inloggen();
	});

	app.route('GET', '#lijstRelaties', function() {
	    lijstRelatiesView.init();
	});

	app.route('GET', '#lijstRelaties/:zoekTerm', function() {
		var zoekTerm = this.params['zoekTerm'];
	    lijstRelatiesView.init(zoekTerm);
	});

	app.route('GET', '#lijstBedrijven', function() {
		lijstBedrijven.init();
	});

	app.route('GET', '#lijstBedrijven/:zoekTerm', function() {
		var zoekTerm = this.params['zoekTerm'];
		lijstBedrijven.init(zoekTerm);
	});

	app.route('GET', '#beherenBedrijf/:id/:actie/:subId', function() {
		var id = this.params['id'];
		var actie = this.params['actie'];
		var subId = this.params['subId'];
		new beherenBedrijf(id, actie, subId);
	});

	app.route('GET', '#beherenBedrijf/:id/:actie', function() {
		var id = this.params['id'];
		var actie = this.params['actie'];
		new beherenBedrijf(id, actie);
	});

	app.route('GET', '#beherenBedrijf/:id', function() {
		var id = this.params['id'];
		new beherenBedrijf(id);
	});

	app.route('GET', '#beherenBedrijf', function() {
		new beherenBedrijf(null);
	});

	app.route('GET', '#beherenRelatie/:id/:actie/:subId', function() {
		var id = this.params['id'];
		var actie = this.params['actie'];
		var subId = this.params['subId'];
		new beherenRelatie(id, actie, subId);
	});

	app.route('GET', '#beherenRelatie/:id/:actie', function() {
		var id = this.params['id'];
		var actie = this.params['actie'];
		new beherenRelatie(id, actie);
	});

	app.route('GET', '#beherenRelatie/:id', function() {
		var id = this.params['id'];
		new beherenRelatie(id);
	});

	app.route('GET', '#beherenRelatie', function() {
		new beherenRelatie(null);
	});

	app.route('GET', '', function() {
		new lijstRelaties();
	});

	app.raise_errors = true;
	app.run();
});