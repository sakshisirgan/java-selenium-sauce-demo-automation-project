pipeline {
    agent any

    environment {
        APP_ENV = 'dev'
    }

    stages {
        stage('Clone') {
            steps {
                git branch: 'main', url: 'https://github.com/sakshisirgan/java-selenium-sauce-demo-automation-project.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project with forced dependency update...'
                bat 'mvn clean install -U'
            }
        }

        stage('Test') {
            steps {
                echo 'Running TestNG tests...'
                bat 'mvn test -U'
            }
        }

        stage('Publish Reports') {
            steps {
                echo 'Copying reports and screenshots...'
                archiveArtifacts artifacts: 'reports/*.html, screenshots/**/*', allowEmptyArchive: true
            }
        }

        stage('Deploy') {
            steps {
                echo "Deploying to ${env.APP_ENV} environment..."
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully.'
        }
        failure {
            echo 'Pipeline failed. Check console output and reports.'
        }
    }
}
