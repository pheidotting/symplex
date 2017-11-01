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
                    cd Commons
                    mvn clean install
                '''
            }
        }

        stage ('Install Clients') {
            steps {
                sh '''
                    cd Clients
                    mvn clean install
                '''
            }
        }

        stage ('Install Messaging') {
            steps {
                sh '''
                    cd Messaging
                    mvn clean install
                '''
            }
        }

        stage ('Build Relatiebeheer') {
            steps {
                sh '''
                    cd Relatiebeheer
                    mvn clean package  -P jenkins
                '''
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
        }

        stage ('Verify Relatiebeheer') {
            steps {
                sh '''
                    cd Relatiebeheer
                    mvn clean verify
                '''
            }
        }
    }
}