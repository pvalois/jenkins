pipeline {
    
    agent {
        docker {
            image 'maven:3.9.3-eclipse-temurin-17'
            args  '-v /tmp:/tmp'
            reuseNode true
        }
    }
    
    options {
        // Timeout counter starts AFTER agent is allocated
        timeout(time: 1, unit: 'SECONDS')
    }
    
    stages {
        stage('Hello') {
            steps {
                sh 'hostname'
                sh 'touch /tmp/bollocks'
                echo 'Hello World'
            }
        }
        
        stage('Build') {
            agent {
                docker {
                    image 'gradle:8.2.0-jdk17-alpine'
                    // Run the container on the node specified at the
                    // top-level of the Pipeline, in the same workspace,
                    // rather than on a new node entirely:
                    reuseNode true
                }
            }
            steps {
                sh 'gradle --version'
            }
        }
    }
}
