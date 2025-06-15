pipeline {
    agent any

    stages {
        stage('Build Spring Boot') {
            steps {
                dir('backend/mvb-app') {
                    sh 'chmod +x mvnw'
                    sh './mvnw clean package -DskipTests'
                }
            }
        }

        stage("Stop previous running containers") {
            steps {
                sh "docker-compose down"
            }
        }

        stage("Run Docker containers") {
            steps {
                sh "docker-compose up --build -d"
            }
        }
    }
}
