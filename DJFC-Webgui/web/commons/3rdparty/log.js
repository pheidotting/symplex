define(['commons/3rdparty/log4javascript',
        'navRegister'],
    function(logger, navRegister){
        var log = log4javascript.getLogger("LakeDigital");
        var browserAppender = new log4javascript.BrowserConsoleAppender();
        var popUpLayout = new log4javascript.PatternLayout("%d{HH:mm:ss} %-5p - %m%n");
        browserAppender.setLayout(popUpLayout);
        log.addAppender(browserAppender);

        var ajaxAppender = new log4javascript.AjaxAppender(navRegister.bepaalUrl('LOG4JAVASCRIPT'));
        log.addAppender(ajaxAppender);

        return log;
    }
);