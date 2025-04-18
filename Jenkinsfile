pipeline {
    agent any
    tools {
        maven 'Maven3.9'
    }

    environment {}

    stages {
        stage('Clean and install') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Build and run containers') {
            steps {
                echo "Stopping and removing containers"
                sh 'docker-compose down --rmi all'
                
                echo "Building and running containers"
                sh '''
                    docker-compose build
                    docker-compose up -d
                '''
            }
        }
    }
}