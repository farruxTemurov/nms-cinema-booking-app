pipeline {
    agent any
    stages {
        stage('Build Spring Boot') {
            steps {
                dir('backend/mvb-app') {
                     sh './mvnw clean package -DskipTests'
                }
            }
        }
        stage("stop previous running container"){
            steps{
                sh "docker-compose down"  
            }
        }
        stage("run the docker container"){
            steps{
                sh "docker-compose up --build -d"  
            }
        }
    }
}