pipeline {
    agent any

    stages {

        stage('RestfulWebApp') {
            steps {
                echo 'Building Microservice 1'
                dir('RestfulWebApp') {
                    sh 'chmod +x gradlew'
                    sh './gradlew clean test --stacktrace'
                }
            }
        }

        stage('MQTT') {
            steps {
                echo 'Building Microservice 2'
                dir('MQTT') {
                    sh 'chmod +x gradlew'
                    sh './gradlew clean test --stacktrace'
                }
            }
        }

        stage('Frontend') {
            steps {
                echo 'Building Frontend'
            }
        }

        stage ('Deploy') {
            steps {
                echo 'Begin Deploy on Docker-Container'
                sh 'docker compose build backend1 backend2 Frontend'
                sh 'docker compose build -d'
            }
        }
    }
}
