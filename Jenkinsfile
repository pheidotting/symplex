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


        stage ('Sonar Development') {
            when {
                expression {
                    return env.BRANCH_NAME == 'development'
                }
            }
            steps {
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