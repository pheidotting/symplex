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

        stage ('Install Commons') {
            steps {
                sh '''
                    cd DJFC-Commons
                    mvn clean install
                '''
            }
        }

        stage ('Install Clients') {
            steps {
                sh '''
                    cd DJFC-Clients
                    mvn clean install
                '''
            }
        }

        stage ('Install Messaging') {
            steps {
                sh '''
                    cd DJFC-Messaging
                    mvn clean install
                '''
            }
        }

        stage ('Build Relatiebeheer') {
            steps {
                sh '''
                    cd DJFC-Relatiebeheer
                    mvn clean package  -P jenkins
                '''
            }
        }

        stage ('Build GUI') {
            steps {
                sh '''
                    cd DJFC-Webgui/web
                    npm cache clear
                    npm install
                    zip -r gui *
                '''
            }
        }

        stage ('Verify Relatiebeheer') {
            steps {
                sh '''
                    cd DJFC-Relatiebeheer
                    mvn clean verify
                '''
            }
        }

        stage ('Sonar Relatiebeheer') {
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