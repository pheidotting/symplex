pipeline {
    agent any
    stages {
        stage ('Initialize') {
            steps {
                slackSend (color: '#4245f4', message: "Job gestart :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
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

        stage ('Build Communicatie') {
            steps {
                slackSend (color: '#4245f4', message: "Start building wars :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                sh '''
                    cd Communicatie
                    mvn clean package  -P jenkins
                '''
            }
        }

        stage ('Build IdBeheer') {
            steps {
                sh '''
                    cd IdBeheer
                    mvn clean package  -P jenkins
                '''
            }
        }

        stage ('Build OverigeGegevensAdministratie') {
            steps {
                sh '''
                    cd OverigeRelatieGegevensAdministratie
                    mvn clean package  -P jenkins
                '''
            }
        }

        stage ('Build PolisAdministratie') {
            steps {
                sh '''
                    cd PolisAdministratie
                    mvn clean package  -P jenkins
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

        stage ('Verify Communicatie') {
            steps {
                slackSend (color: '#4245f4', message: "War's klaar, Verify :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                sh '''
                    cd Communicatie
                    mvn clean verify
                '''
            }
        }

        stage ('Verify IdBeheer') {
            steps {
                sh '''
                    cd IdBeheer
                    mvn clean verify
                '''
            }
        }

        stage ('Verify OverigeGegevensAdministratie') {
            steps {
                sh '''
                    cd OverigeRelatieGegevensAdministratie
                    mvn clean verify
                '''
            }
        }

        stage ('Verify PolisAdministratie') {
            steps {
                sh '''
                    cd PolisAdministratie
                    mvn clean verify
                '''
                    GIT_BRANCH =  sh(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD').trim()
                    return GIT_BRANCH
            }
        }


        stage ('Deployment Test') {
            when {
                expression {
                    GIT_BRANCH =  sh(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD').trim()
                    return GIT_BRANCH == 'development'
                }
            }
            steps {
                slackSend (color: '#4245f4', message: "Deployment Test")
            }
        }

        stage ('Deployment Live') {
            when {
                expression {
                    GIT_BRANCH =  sh(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD').trim()
                    return GIT_BRANCH == 'master'
                }
            }
            steps {
                slackSend (color: '#4245f4', message: "Deployment Live")
            }
        }
    }
}