define(['commons/3rdparty/log4javascript2',
        'navRegister',
        'commons/3rdparty/sprintf',
        'knockout'],
    function(log4javascript, navRegister, sprintf, ko) {
        return {
            getLogger: function(name) {
                var log = log4javascript.getLogger(name);
                var browserAppender = new log4javascript.BrowserConsoleAppender();
                var popUpLayout = new log4javascript.PatternLayout("%d{HH:mm:ss} %c.js %-5p - %m%n");
                browserAppender.setThreshold(log4javascript.Level.TRACE);
                browserAppender.setLayout(popUpLayout);
                log.addAppender(browserAppender);

//                var ajaxAppender = new log4javascript.AjaxAppender(navRegister.bepaalUrl('LOG4JAVASCRIPT'));
//                log.addAppender(ajaxAppender);
                
                // alle logfuncties 'enhancen' met een die Objecten als JSON logt
                var oldDebug = log.debug;
                var oldTrace = log.trace;
                var oldInfo = log.info;
                var oldError = log.error;

                log.debug = function() { oldDebug.apply(this, expandedMsg.apply(this, arguments)); };
                log.trace = function() { oldTrace.apply(this, expandedMsg.apply(this, arguments)); };
                log.info = function() { oldInfo.apply(this, expandedMsg.apply(this, arguments)); };
                log.error = function() { oldError.apply(this, expandedMsg.apply(this, arguments)); };
                log.init = true;

                function expandedMsg() {
                    var args = new Array();
                    for (var i = 0; i < arguments.length; i++) {
                        try {
                            args[i] = typeof arguments[i] === "object" ? sprintf.sprintf('\n%s', ko.toJSON(arguments[i])) : arguments[i];
                        } catch(error) {
                            args[i] = arguments[i] + ' (logging failed: ' + error + ')';
                        }
                    }
                    return args;
                }
                return log;
            }
        }
    }
);