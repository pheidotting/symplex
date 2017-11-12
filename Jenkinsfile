pipeline {
    agent any
    stages {

        stage ('Sonar Commons') {
            steps {
                sh '''
                    cd Commons
                    mvn clean test sonar:sonar
                '''
            }
            post {
                failure {
                    slackSend (color: '#FF0000', message: "Commons Install Failed :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
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