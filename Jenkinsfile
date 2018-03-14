pipeline {
    agent any
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }

        stage ('Sonar Sonarbranch') {
            when {
                expression {
                    return env.BRANCH_NAME == 'sonar'
                }
            }
            steps {
                sh '''
                    cd Commons
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=development
                '''
                sh '''
                    cd Clients
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=development
                '''
                sh '''
                    cd Messaging
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=development
                '''
                sh '''
                    cd Communicatie
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=development
                '''
                sh '''
                    cd IdBeheer
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=development
                '''
                sh '''
                    cd LicentieBeheer
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=development
                '''
                sh '''
                    cd OverigeRelatieGegevensAdministratie
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=development
                '''
                sh '''
                    cd Relatiebeheer
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=development
                '''
                sh '''
                    cd Webgui
                    /home/sonar-runner/sonar-scanner-3.0.3.778-linux/bin/sonar-scanner -Dsonar.branch=development
                '''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Sonar gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Sonar mislukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }

        stage ('Undeploy Testbak') {
            when {
                expression {
                    return env.BRANCH_NAME != 'master'
                }
            }
            steps {
                sh '''
                    ssh jetty@192.168.91.230 rm -f /opt/jetty/webapps/communicatie.war
                    ssh jetty@192.168.91.230 rm -f /opt/jetty/webapps/licentie.war
                    ssh jetty@192.168.91.230 rm -f /opt/jetty/webapps/identificatie.war
                    ssh jetty@192.168.91.230 rm -f /opt/jetty/webapps/oga.war
                    ssh jetty@192.168.91.230 rm -f /opt/jetty/webapps/dejonge.war
                '''
            }
        }

        stage ('Undeploy Test') {
            when {
                expression {
                    return env.BRANCH_NAME == 'acceptatie'
                }
            }
            steps {
                sh '''
                    ssh jetty@192.168.91.215 rm -f /opt/jetty/webapps/communicatie.war
                    ssh jetty@192.168.91.215 rm -f /opt/jetty/webapps/licentie.war
                    ssh jetty@192.168.91.215 rm -f /opt/jetty/webapps/identificatie.war
                    ssh jetty@192.168.91.215 rm -f /opt/jetty/webapps/oga.war
                    ssh jetty@192.168.91.215 rm -f /opt/jetty/webapps/dejonge.war
                '''
            }
        }

        stage ('Install Commons') {
            steps {
                sh '''
                    cd Commons
                    mvn clean install
                '''
            }
            post {
                failure {
                    slackSend (color: '#FF0000', message: "Commons Install Failed :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }

        stage ('Install Clients') {
            steps {
                sh '''
                    cd Clients
                    mvn clean install
                '''
            }
            post {
                failure {
                    slackSend (color: '#FF0000', message: "Clients Install Failed :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }

        stage ('Install Messaging') {
            steps {
                sh '''
                    cd Messaging
                    mvn clean install
                '''
            }
            post {
                failure {
                    slackSend (color: '#FF0000', message: "Messaging Install Failed :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }

        stage ('Build LicentieBeheer') {
            steps {
                slackSend (color: '#4245f4', message: "Start building wars :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                sh '''
                    cd LicentieBeheer
                    mvn clean package  -P jenkins
                '''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Builden LicentieBeheer gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Builden LicentieBeheer Failed :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }

        stage ('Build Communicatie') {
            steps {
                slackSend (color: '#4245f4', message: "Start building wars :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                sh '''
                    cd Communicatie
                    mvn clean package  -P jenkins
                '''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Builden Communicatie gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Builden Communicatie Failed :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }

        stage ('Build IdBeheer') {
            steps {
                sh '''
                    cd IdBeheer
                    mvn clean package  -P jenkins
                '''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Builden IdBeheer gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Builden IdBeheer Failed :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }

        stage ('Build OverigeGegevensAdministratie') {
            steps {
                sh '''
                    cd OverigeRelatieGegevensAdministratie
                    mvn clean package  -P jenkins
                '''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Builden OverigeGegevensAdministratie gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Builden OverigeGegevensAdministratie Failed :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }

        stage ('Build Relatiebeheer') {
            steps {
                sh '''
                    cd Relatiebeheer
                    mvn clean package  -P jenkins
                '''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Builden Relatiebeheer gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Builden Relatiebeheer Failed :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }

        stage ('Build GUI') {
            steps {
                sh '''
                    cd Webgui/web
                    npm cache clear
                    npm install
                    zip -r gui *
                '''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Builden GUI gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Builden GUI Failed :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }

        stage ('Deployment Testbak') {
            when {
                expression {
                    return env.BRANCH_NAME != 'master'
                }
            }
            steps {
                slackSend (color: '#4245f4', message: "Deploy naar testbak :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                sh '''
                    scp Communicatie/src/main/resources/tst2/comm.app.properties jetty@192.168.91.230:/opt/jetty
                    scp Communicatie/src/main/resources/tst2/comm.log4j.xml jetty@192.168.91.230:/opt/jetty
                    scp Communicatie/target/communicatie.war jetty@192.168.91.230:/opt/jetty/webapps

                    bash -c 'while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' http://192.168.91.230:8080/communicatie/rest/zabbix/checkDatabase)" != "200" ]]; do sleep 5; done'

                    scp LicentieBeheer/src/main/resources/tst2/lb.app.properties jetty@192.168.91.230:/opt/jetty
                    scp LicentieBeheer/src/main/resources/tst2/lb.log4j.xml jetty@192.168.91.230:/opt/jetty
                    scp LicentieBeheer/target/licentie.war jetty@192.168.91.230:/opt/jetty/webapps

                    bash -c 'while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' http://192.168.91.230:8080/licentie/rest/zabbix/checkDatabase)" != "200" ]]; do sleep 5; done'

                    scp IdBeheer/src/main/resources/tst2/id.app.properties jetty@192.168.91.230:/opt/jetty
                    scp IdBeheer/src/main/resources/tst2/id.log4j.xml jetty@192.168.91.230:/opt/jetty
                    scp IdBeheer/target/identificatie.war jetty@192.168.91.230:/opt/jetty/webapps

                    bash -c 'while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' http://192.168.91.230:8080/identificatie/rest/zabbix/checkDatabase)" != "200" ]]; do sleep 5; done'

                    scp OverigeRelatieGegevensAdministratie/src/main/resources/tst2/oga.app.properties jetty@192.168.91.230:/opt/jetty
                    scp OverigeRelatieGegevensAdministratie/src/main/resources/tst2/oga.log4j.xml jetty@192.168.91.230:/opt/jetty
                    scp OverigeRelatieGegevensAdministratie/target/oga.war jetty@192.168.91.230:/opt/jetty/webapps

                    bash -c 'while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' http://192.168.91.230:8080/oga/rest/zabbix/checkDatabase)" != "200" ]]; do sleep 5; done'

                    scp Relatiebeheer/src/main/resources/tst2/djfc.app.properties jetty@192.168.91.230:/opt/jetty
                    scp Relatiebeheer/src/main/resources/tst2/djfc.log4j.xml jetty@192.168.91.230:/opt/jetty
                    scp Relatiebeheer/src/main/resources/GeoLite2-City.mmdb jetty@192.168.91.230:/opt/jetty
                    scp Relatiebeheer/target/dejonge.war jetty@192.168.91.230:/opt/jetty/webapps

                    bash -c 'while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' http://192.168.91.230:8080/dejonge/rest/authorisatie/zabbix/checkDatabase)" != "200" ]]; do sleep 5; done'

                    ssh jetty@192.168.91.230 rm -fr /data/web/gui/*
                    scp -r Webgui/web/* jetty@192.168.91.230:/data/web/gui
                '''
                sh "ssh jetty@192.168.91.230 sed -i 's/{VERSION}/${env.BRANCH_NAME}-${env.BUILD_NUMBER}/' /data/web/gui/js/commons/app.js"
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Deploy naar testbak gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Deploy naar testbak Failed :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }

        stage ('Verify Communicatie') {
            steps {
                slackSend (color: '#4245f4', message: "War's klaar, Verify :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                sh '''
                    cd Communicatie
                    mvn clean verify
                '''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Verify Communicatie gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Verify Communicatie Failed :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }

        stage ('Verify IdBeheer') {
            steps {
                sh '''
                    cd IdBeheer
                    mvn clean verify
                '''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Verify IdBeheer gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Verify IdBeheer Failed :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }

        stage ('Verify OverigeGegevensAdministratie') {
            steps {
                sh '''
                    cd OverigeRelatieGegevensAdministratie
                    mvn clean verify
                '''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Verify OverigeGegevensAdministratie gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Verify OverigeGegevensAdministratie Failed :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }

        stage ('Undeploy Live') {
            when {
                expression {
                    return env.BRANCH_NAME == 'master'
                }
            }
            steps {
                sh '''
                    ssh jetty@192.168.91.220 rm -f /opt/jetty/webapps/communicatie.war
                    ssh jetty@192.168.91.220 rm -f /opt/jetty/webapps/licentie.war
                    ssh jetty@192.168.91.220 rm -f /opt/jetty/webapps/identificatie.war
                    ssh jetty@192.168.91.220 rm -f /opt/jetty/webapps/oga.war
                    ssh jetty@192.168.91.220 rm -f /opt/jetty/webapps/pa.war
                    ssh jetty@192.168.91.220 rm -f /opt/jetty/webapps/dejonge.war
                '''
            }
        }

        stage ('Verify Relatiebeheer') {
            steps {
                sh '''
                    cd Relatiebeheer
                    mvn clean verify
                '''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Verify Relatiebeheer gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Verify Relatiebeheer Failed :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }

        stage ('Integratietest') {
            steps {
                sh '''
                    cd TestSysteem
                    mvn clean verify
                '''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Integratietest gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Integratietest mislukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }

        stage ('Deployment Test') {
            when {
                expression {
                    return env.BRANCH_NAME == 'acceptatie'
                }
            }
            steps {
                slackSend (color: '#4245f4', message: "Deploy naar test :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                sh '''
                    scp Communicatie/src/main/resources/tst2/comm.app.properties jetty@192.168.91.215:/opt/jetty
                    scp Communicatie/src/main/resources/tst2/comm.log4j.xml jetty@192.168.91.215:/opt/jetty
                    scp Communicatie/target/communicatie.war jetty@192.168.91.215:/opt/jetty/webapps

                    bash -c 'while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' http://192.168.91.215:8080/communicatie/rest/zabbix/checkDatabase)" != "200" ]]; do sleep 5; done'

                    scp LicentieBeheer/src/main/resources/tst2/lb.app.properties jetty@192.168.91.215:/opt/jetty
                    scp LicentieBeheer/src/main/resources/tst2/lb.log4j.xml jetty@192.168.91.215:/opt/jetty
                    scp LicentieBeheer/target/licentie.war jetty@192.168.91.215:/opt/jetty/webapps

                    bash -c 'while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' http://192.168.91.215:8080/licentie/rest/zabbix/checkDatabase)" != "200" ]]; do sleep 5; done'

                    scp IdBeheer/src/main/resources/tst/id.app.properties jetty@192.168.91.215:/opt/jetty
                    scp IdBeheer/src/main/resources/tst/id.log4j.xml jetty@192.168.91.215:/opt/jetty
                    scp IdBeheer/target/identificatie.war jetty@192.168.91.215:/opt/jetty/webapps

                    bash -c 'while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' http://192.168.91.215:8080/identificatie/rest/zabbix/checkDatabase)" != "200" ]]; do sleep 5; done'

                    scp OverigeRelatieGegevensAdministratie/src/main/resources/tst/oga.app.properties jetty@192.168.91.215:/opt/jetty
                    scp OverigeRelatieGegevensAdministratie/src/main/resources/tst/oga.log4j.xml jetty@192.168.91.215:/opt/jetty
                    scp OverigeRelatieGegevensAdministratie/target/oga.war jetty@192.168.91.215:/opt/jetty/webapps

                    bash -c 'while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' http://192.168.91.215:8080/oga/rest/zabbix/checkDatabase)" != "200" ]]; do sleep 5; done'

                    scp Relatiebeheer/src/main/resources/tst/djfc.app.properties jetty@192.168.91.215:/opt/jetty
                    scp Relatiebeheer/src/main/resources/tst/djfc.log4j.xml jetty@192.168.91.215:/opt/jetty
                    scp Relatiebeheer/src/main/resources/GeoLite2-City.mmdb jetty@192.168.91.215:/opt/jetty
                    scp Relatiebeheer/target/dejonge.war jetty@192.168.91.215:/opt/jetty/webapps

                    bash -c 'while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' http://192.168.91.215:8080/dejonge/rest/authorisatie/zabbix/checkDatabase)" != "200" ]]; do sleep 5; done'

                    ssh jetty@192.168.91.215 rm -fr /data/web/gui/*
                    scp -r Webgui/web/* jetty@192.168.91.215:/data/web/gui
                '''
                sh "ssh jetty@192.168.91.215 sed -i 's/{VERSION}/${env.BRANCH_NAME}-${env.BUILD_NUMBER}/' /data/web/gui/js/commons/app.js"
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Deploy naar test gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                    slackSend (color: '#4245f4', message: "Ik heb zojuist een nieuwe versie op de TESTomgeving geplaatst, de wijzigingen zijn:\n```" + commitMessage() + "```", channel: "#deployments")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Deploy naar test Failed :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }

        stage ('Deployment Live') {
            when {
                expression {
                    return env.BRANCH_NAME == 'master'
                }
            }
            steps {
                slackSend (color: '#4245f4', message: "Deploy naar productie :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                sh '''
                    scp Communicatie/src/main/resources/tst2/comm.app.properties jetty@192.168.91.220:/opt/jetty
                    scp Communicatie/src/main/resources/tst2/comm.log4j.xml jetty@192.168.91.220:/opt/jetty
                    scp Communicatie/target/communicatie.war jetty@192.168.91.220:/opt/jetty/webapps

                    bash -c 'while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' http://192.168.91.220:8080/communicatie/rest/zabbix/checkDatabase)" != "200" ]]; do sleep 5; done'

                    scp LicentieBeheer/src/main/resources/tst2/lb.app.properties jetty@192.168.91.220:/opt/jetty
                    scp LicentieBeheer/src/main/resources/tst2/lb.log4j.xml jetty@192.168.91.220:/opt/jetty
                    scp LicentieBeheer/target/licentie.war jetty@192.168.91.220:/opt/jetty/webapps

                    bash -c 'while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' http://192.168.91.220:8080/licentie/rest/zabbix/checkDatabase)" != "200" ]]; do sleep 5; done'

                    scp IdBeheer/src/main/resources/prd/id.app.properties jetty@192.168.91.220:/opt/jetty
                    scp IdBeheer/src/main/resources/prd/id.log4j.xml jetty@192.168.91.220:/opt/jetty
                    scp IdBeheer/target/identificatie.war jetty@192.168.91.220:/opt/jetty/webapps

                    bash -c 'while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' http://192.168.91.220:8080/identificatie/rest/zabbix/checkDatabase)" != "200" ]]; do sleep 5; done'

                    scp OverigeRelatieGegevensAdministratie/src/main/resources/prd/oga.app.properties jetty@192.168.91.220:/opt/jetty
                    scp OverigeRelatieGegevensAdministratie/src/main/resources/prd/oga.log4j.xml jetty@192.168.91.220:/opt/jetty
                    scp OverigeRelatieGegevensAdministratie/target/oga.war jetty@192.168.91.220:/opt/jetty/webapps

                    bash -c 'while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' http://192.168.91.220:8080/oga/rest/zabbix/checkDatabase)" != "200" ]]; do sleep 5; done'

                    scp Relatiebeheer/src/main/resources/prd/djfc.app.properties jetty@192.168.91.220:/opt/jetty
                    scp Relatiebeheer/src/main/resources/prd/djfc.log4j.xml jetty@192.168.91.220:/opt/jetty
                    scp Relatiebeheer/src/main/resources/GeoLite2-City.mmdb jetty@192.168.91.220:/opt/jetty
                    scp Relatiebeheer/target/dejonge.war jetty@192.168.91.220:/opt/jetty/webapps

                    bash -c 'while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' http://192.168.91.220:8080/dejonge/rest/authorisatie/zabbix/checkDatabase)" != "200" ]]; do sleep 5; done'

                    ssh jetty@192.168.91.220 rm -fr /data/web/gui/*
                    scp -r Webgui/web/* jetty@192.168.91.220:/data/web/gui
                '''
                sh "ssh jetty@192.168.91.220 sed -i 's/{VERSION}/${env.BRANCH_NAME}-${env.BUILD_NUMBER}/' /data/web/gui/js/commons/app.js"
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Deploy naar PRD gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                    slackSend (color: '#4245f4', message: "Ik heb zojuist een nieuwe versie op de PRODUCTIEomgeving geplaatst, de wijzigingen zijn:\n```" + commitMessage() + "```", channel: "#deployments")

                    sh "curl --data '" + commitMessage() + "' http://192.168.91.220:8080/dejonge/rest/authorisatie/versies/nieuweversie -H 'Content-Type: application/json';"
                }
                failure {
                    slackSend (color: '#FF0000', message: "Deploy naar test Failed :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }

        stage ('Sonar Development') {
            when {
                expression {
                    return env.BRANCH_NAME == 'development'
                }
            }
            steps {
                sh '''
                    cd Commons
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=development
                '''
                sh '''
                    cd Clients
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=development
                '''
                sh '''
                    cd Messaging
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=development
                '''
                sh '''
                    cd Communicatie
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=development
                '''
                sh '''
                    cd IdBeheer
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=development
                '''
                sh '''
                    cd LicentieBeheer
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=development
                '''
                sh '''
                    cd OverigeRelatieGegevensAdministratie
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=development
                '''
                sh '''
                    cd Relatiebeheer
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=development
                '''
                sh '''
                    cd Webgui
                    /home/sonar-runner/sonar-scanner-3.0.3.778-linux/bin/sonar-scanner -Dsonar.branch=development
                '''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Sonar gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Sonar mislukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }

        stage ('Sonar Master') {
            when {
                expression {
                    return env.BRANCH_NAME == 'master'
                }
            }
            steps {
                sh '''
                    cd Commons
                    mvn clean test -Psonar sonar:sonar
                '''
                sh '''
                    cd Clients
                    mvn clean test -Psonar sonar:sonar
                '''
                sh '''
                    cd Messaging
                    mvn clean test -Psonar sonar:sonar
                '''
                sh '''
                    cd Communicatie
                    mvn clean test -Psonar sonar:sonar
                '''
                sh '''
                    cd IdBeheer
                    mvn clean test -Psonar sonar:sonar
                '''
                sh '''
                    cd LicentieBeheer
                    mvn clean test -Psonar sonar:sonar
                '''
                sh '''
                    cd LicentieBeheer
                    mvn clean test -Psonar sonar:sonar
                '''
                sh '''
                    cd OverigeRelatieGegevensAdministratie
                    mvn clean test -Psonar sonar:sonar
                '''
                sh '''
                    cd Relatiebeheer
                    mvn clean test -Psonar sonar:sonar
                '''
                sh '''
                    cd Webgui
                    /home/sonar-runner/sonar-scanner-3.0.3.778-linux/bin/sonar-scanner
                '''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Sonar gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Sonar mislukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }
    }
    post {
        success {
            slackSend (color: '#4245f4', message: "Afgerond : '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
        }
    }
}

def commitMessage() {
    sh 'git log --format=%B -n 1 HEAD > commitMessage'
    def commitMessage = readFile('commitMessage')
    sh 'rm commitMessage'
    commitMessage
}