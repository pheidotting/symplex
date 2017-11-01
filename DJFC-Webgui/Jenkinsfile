pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh '''
                    cd web
                    npm cache clear
                    npm install
                    zip -r gui *
                '''
            }
        }
    }
}