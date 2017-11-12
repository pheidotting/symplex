pipeline {
    agent any
    stages {

        stage ('Sonar Commons') {
            steps {
                sh '/Commons/mvn clean test -Psonar sonar:sonar -Dsonar.branch=${env.BRANCH_NAME}''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Commons Sonar gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Commons Sonar mislukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }
        stage ('Sonar Clients') {
            steps {
                sh '''
                    cd Clients
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=${env.BRANCH_NAME}
                '''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Clients Sonar gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Clients Sonar mislukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }
        stage ('Sonar Messaging') {
            steps {
                sh '''
                    cd Messaging
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=${env.BRANCH_NAME}
                '''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Messaging Sonar gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Messaging Sonar mislukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }
        stage ('Sonar Communicatie') {
            steps {
                sh '''
                    cd Communicatie
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=${env.BRANCH_NAME}
                '''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Communicatie Sonar gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Communicatie Sonar mislukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }
        stage ('Sonar IdBeheer') {
            steps {
                sh '''
                    cd IdBeheer
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=${env.BRANCH_NAME}
                '''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "IdBeheer Sonar gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "IdBeheer Sonar mislukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }
        stage ('Sonar OverigeGegevensAdministratie') {
            steps {
                sh '''
                    cd OverigeRelatieGegevensAdministratie
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=${env.BRANCH_NAME}
                '''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "OverigeGegevensAdministratie Sonar gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "OverigeGegevensAdministratie Sonar mislukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }
        stage ('Sonar PolisAdministratie') {
            steps {
                sh '''
                    cd PolisAdministratie
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=${env.BRANCH_NAME}
                '''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "PolisAdministratie Sonar gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "PolisAdministratie Sonar mislukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
            }
        }
        stage ('Sonar Relatiebeheer') {
            steps {
                sh '''
                    cd Relatiebeheer
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch=${env.BRANCH_NAME}
                '''
            }
            post {
                success {
                    slackSend (color: '#4245f4', message: "Relatiebeheer Sonar gelukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                }
                failure {
                    slackSend (color: '#FF0000', message: "Relatiebeheer Sonar mislukt :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
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
