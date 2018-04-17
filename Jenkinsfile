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

        stage ('Undeploy Testbak') {
            steps {
                sh '''
                    ssh jetty@192.168.91.230 rm -f /opt/jetty/webapps/test.war
                    ssh jetty@192.168.91.230 rm -f /opt/jetty/webapps/communicatie.war
                    ssh jetty@192.168.91.230 rm -f /opt/jetty/webapps/licentie.war
                    ssh jetty@192.168.91.230 rm -f /opt/jetty/webapps/identificatie.war
                    ssh jetty@192.168.91.230 rm -f /opt/jetty/webapps/oga.war
                    ssh jetty@192.168.91.230 rm -f /opt/jetty/webapps/dejonge.war
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

        stage ('Build ') {
            steps {
                slackSend (color: '#4245f4', message: "Start building wars :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                sh '''
                    mvn clean package  -P jenkins
                '''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Builden gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Builden Failed :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
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
            steps {
                slackSend (color: '#4245f4', message: "Deploy naar testbak :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                sh '''
                    scp TestSysteem/target/test.war jetty@192.168.91.230:/opt/jetty/webapps

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
        stage ('Integratietest') {
            steps {
                sh '''
                    cd TestSysteem
                    rm -fr target
                    mvn clean test
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