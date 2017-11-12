pipeline {
    agent any
    stages {

        stage ('Sonar Commons') {
            steps {
                sh '''
                    cd Commons
                    mvn clean test -Psonar sonar:sonar -Dsonar.branch env.BRANCH_NAME
                '''
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

    }
    post {
        success {
            slackSend (color: '#4245f4', message: "Afgerond : '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
        }
    }
}