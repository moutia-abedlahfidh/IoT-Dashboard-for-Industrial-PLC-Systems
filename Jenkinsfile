pipeline {
    agent any

    stages {

        stage('RestfulWebApp') {
            steps {
                echo 'Building Microservice 1'
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