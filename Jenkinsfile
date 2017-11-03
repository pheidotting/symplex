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
            }
        }

        stage ('Verify Relatiebeheer') {
            steps {
                sh '''
                    cd Relatiebeheer
                    mvn clean verify
                '''
                slackSend (color: '#4245f4', message: "Verify klaar, start Sonarqube build :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
            }
        }

        stage ('Sonar Relatiebeheer') {
            withSonarQubeEnv('Sonar') {
                sh '''
                    cd Relatiebeheer
                    mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.3.0.603:sonar -f all/pom.xml -Dsonar.projectKey=nl.symplex:all:master -Dsonar.login=admin -Dsonar.password=admin -Dsonar.language=java -Dsonar.sources=. -Dsonar.tests=. -Dsonar.test.inclusions=**/*Test*/** -Dsonar.exclusions=**/*Test*/**'
                '''
            }
        }
                steps {
#                sh '''
#                    cd Relatiebeheer
#                    mvn sonar:sonar -Dsonar.branch= + ${BRANCH_NAME}
#                '''
#                slackSend (color: '#4245f4', message: "Build klaar :  '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
            }
        }
    }
}