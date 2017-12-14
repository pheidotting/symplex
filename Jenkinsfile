pipeline {
    agent any
    stages {
        stage ('Initialize') {
            steps {
                mail to: 'patrick@heidotting.nl',
                     subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
                     body: "Something is wrong with ${env.BUILD_URL}"

                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
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
                    ssh jetty@192.168.91.230 rm -f /opt/jetty/webapps/identificatie.war
                    ssh jetty@192.168.91.230 rm -f /opt/jetty/webapps/oga.war
                    ssh jetty@192.168.91.230 rm -f /opt/jetty/webapps/pa.war
                    ssh jetty@192.168.91.230 rm -f /opt/jetty/webapps/dejonge.war
                '''
            }
        }

        stage ('Undeploy Test') {
            when {
                expression {
                    return env.BRANCH_NAME == 'development'
                }
            }
            steps {
                sh '''
                    ssh jetty@192.168.91.215 rm -f /opt/jetty/webapps/identificatie.war
                    ssh jetty@192.168.91.215 rm -f /opt/jetty/webapps/oga.war
                    ssh jetty@192.168.91.215 rm -f /opt/jetty/webapps/pa.war
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

        stage ('Build PolisAdministratie') {
            steps {
                sh '''
                    cd PolisAdministratie
                    mvn clean package  -P jenkins
                '''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Builden PolisAdministratie gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Builden PolisAdministratie Failed :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
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
                    scp IdBeheer/src/main/resources/tst2/id.app.properties jetty@192.168.91.230:/opt/jetty
                    scp IdBeheer/src/main/resources/tst2/id.log4j.xml jetty@192.168.91.230:/opt/jetty
                    scp IdBeheer/target/identificatie.war jetty@192.168.91.230:/opt/jetty/webapps

                    scp OverigeRelatieGegevensAdministratie/src/main/resources/tst2/oga.app.properties jetty@192.168.91.230:/opt/jetty
                    scp OverigeRelatieGegevensAdministratie/src/main/resources/tst2/oga.log4j.xml jetty@192.168.91.230:/opt/jetty
                    scp OverigeRelatieGegevensAdministratie/target/oga.war jetty@192.168.91.230:/opt/jetty/webapps

                    scp PolisAdministratie/src/main/resources/tst2/pa.app.properties jetty@192.168.91.230:/opt/jetty
                    scp PolisAdministratie/src/main/resources/tst2/pa.log4j.xml jetty@192.168.91.230:/opt/jetty
                    scp PolisAdministratie/target/pa.war jetty@192.168.91.230:/opt/jetty/webapps

                    scp Relatiebeheer/src/main/resources/tst2/djfc.app.properties jetty@192.168.91.230:/opt/jetty
                    scp Relatiebeheer/src/main/resources/tst2/djfc.log4j.xml jetty@192.168.91.230:/opt/jetty
                    scp Relatiebeheer/target/dejonge.war jetty@192.168.91.230:/opt/jetty/webapps

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

        stage ('Verify PolisAdministratie') {
            steps {
                sh '''
                    cd PolisAdministratie
                    mvn clean verify
                '''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Verify PolisAdministratie gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Verify PolisAdministratie Failed :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
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
                    return env.BRANCH_NAME == 'development'
                }
            }
            steps {
                slackSend (color: '#4245f4', message: "Deploy naar test :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                sh '''
                    scp IdBeheer/src/main/resources/tst/id.app.properties jetty@192.168.91.215:/opt/jetty
                    scp IdBeheer/src/main/resources/tst/id.log4j.xml jetty@192.168.91.215:/opt/jetty
                    scp IdBeheer/target/identificatie.war jetty@192.168.91.215:/opt/jetty/webapps

                    scp OverigeRelatieGegevensAdministratie/src/main/resources/tst/oga.app.properties jetty@192.168.91.215:/opt/jetty
                    scp OverigeRelatieGegevensAdministratie/src/main/resources/tst/oga.log4j.xml jetty@192.168.91.215:/opt/jetty
                    scp OverigeRelatieGegevensAdministratie/target/oga.war jetty@192.168.91.215:/opt/jetty/webapps

                    scp PolisAdministratie/src/main/resources/tst/pa.app.properties jetty@192.168.91.215:/opt/jetty
                    scp PolisAdministratie/src/main/resources/tst/pa.log4j.xml jetty@192.168.91.215:/opt/jetty
                    scp PolisAdministratie/target/pa.war jetty@192.168.91.215:/opt/jetty/webapps

                    scp Relatiebeheer/src/main/resources/tst/djfc.app.properties jetty@192.168.91.215:/opt/jetty
                    scp Relatiebeheer/src/main/resources/tst/djfc.log4j.xml jetty@192.168.91.215:/opt/jetty
                    scp Relatiebeheer/target/dejonge.war jetty@192.168.91.215:/opt/jetty/webapps

                    ssh jetty@192.168.91.215 rm -fr /data/web/gui/*
                    scp -r Webgui/web/* jetty@192.168.91.215:/data/web/gui
                '''
                sh "ssh jetty@192.168.91.230 sed -i 's/{VERSION}/${env.BRANCH_NAME}-${env.BUILD_NUMBER}/' /data/web/gui/js/commons/app.js"
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
                slackSend (color: '#4245f4', message: "Deploy naar test :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                sh '''
                    scp IdBeheer/src/main/resources/prd/id.app.properties jetty@192.168.91.220:/opt/jetty
                    scp IdBeheer/src/main/resources/prd/id.log4j.xml jetty@192.168.91.220:/opt/jetty
                    scp IdBeheer/target/identificatie.war jetty@192.168.91.220:/opt/jetty/webapps

                    scp OverigeRelatieGegevensAdministratie/src/main/resources/prd/oga.app.properties jetty@192.168.91.220:/opt/jetty
                    scp OverigeRelatieGegevensAdministratie/src/main/resources/prd/oga.log4j.xml jetty@192.168.91.220:/opt/jetty
                    scp OverigeRelatieGegevensAdministratie/target/oga.war jetty@192.168.91.220:/opt/jetty/webapps

                    scp PolisAdministratie/src/main/resources/prd/pa.app.properties jetty@192.168.91.220:/opt/jetty
                    scp PolisAdministratie/src/main/resources/prd/pa.log4j.xml jetty@192.168.91.220:/opt/jetty
                    scp PolisAdministratie/target/pa.war jetty@192.168.91.220:/opt/jetty/webapps

                    scp Relatiebeheer/src/main/resources/prd/djfc.app.properties jetty@192.168.91.220:/opt/jetty
                    scp Relatiebeheer/src/main/resources/prd/djfc.log4j.xml jetty@192.168.91.220:/opt/jetty
                    scp Relatiebeheer/target/dejonge.war jetty@192.168.91.220:/opt/jetty/webapps

                    ssh jetty@192.168.91.220 rm -fr /data/web/gui/*
                    scp -r Webgui/web/* jetty@192.168.91.220:/data/web/gui
                '''
                sh "ssh jetty@192.168.91.230 sed -i 's/{VERSION}/${env.BRANCH_NAME}-${env.BUILD_NUMBER}/' /data/web/gui/js/commons/app.js"
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Deploy naar PRD gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                    slackSend (color: '#4245f4', message: "Ik heb zojuist een nieuwe versie op de PRODUCTIEomgeving geplaatst, de wijzigingen zijn:\n```" + commitMessage() + "```", channel: "#deployments")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Deploy naar test Failed :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }

        stage ('Sonar Branch') {
            when {
                expression {
                    return env.BRANCH_NAME != 'master' && env.BRANCH_NAME != 'development'
                }
            }
            steps {
                sh '''
                    cd Commons
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=branch
                '''
                sh '''
                    cd Clients
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=branch
                '''
                sh '''
                    cd Messaging
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=branch
                '''
                sh '''
                    cd Communicatie
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=branch
                '''
                sh '''
                    cd IdBeheer
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=branch
                '''
                sh '''
                    cd OverigeRelatieGegevensAdministratie
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=branch
                '''
                sh '''
                    cd PolisAdministratie
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=branch
                '''
                sh '''
                    cd Relatiebeheer
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=branch
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
                    cd OverigeRelatieGegevensAdministratie
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=development
                '''
                sh '''
                    cd PolisAdministratie
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=development
                '''
                sh '''
                    cd Relatiebeheer
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=development
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
                    cd OverigeRelatieGegevensAdministratie
                    mvn clean test -Psonar sonar:sonar
                '''
                sh '''
                    cd PolisAdministratie
                    mvn clean test -Psonar sonar:sonar
                '''
                sh '''
                    cd Relatiebeheer
                    mvn clean test -Psonar sonar:sonar
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