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

        parallel {
            stage ('Build Communicatie') {
                steps {
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
        }

        stage ('Verify Communicatie') {
            steps {
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
            }
        }

        stage ('Sonar Relatiebeheer') {
            steps {
                sh '''
                    cd Relatiebeheer
                    mvn sonar:sonar -Dsonar.branch= + ${BRANCH_NAME}
                '''
            }
        }
    }
}