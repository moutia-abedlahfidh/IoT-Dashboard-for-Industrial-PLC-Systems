pipeline {
    agent any

    stages {

        stage('RestfulWebApp') {
            steps {
                echo 'Building Microservice 1'
                dir('RestfulWebApp') {
                    sh 'chmod +x gradlew'
                    sh './gradlew test'
                }
            }
        }

        stage('MQTT') {
            steps {
                echo 'Building Microservice 2'
            }
        }

        stage('Frontend') {
            steps {
                echo 'Building Frontend'
            }
        }
    }
}