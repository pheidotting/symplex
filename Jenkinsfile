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

        stage ('Build') {
            steps {
                sh '''
                    cd DJFC-Relatiebeheer
                    mvn clean package  -P jenkins
                '''
                sh '''
                    cd DJFC-Webgui/web
                    npm cache clear
                    npm install
                    zip -r gui *
                '''
            }
        }

        stage ('Verify') {
            steps {
                sh '''
                    cd DJFC-Relatiebeheer
                    mvn clean verify
                '''
            }
        }

        stage ('Sonar') {
            steps {
                sh '''
                    cd DJFC-Relatiebeheer
                    mvn clean test cobertura:cobertura
                '''
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml'
                }
            }
        }
    }
}