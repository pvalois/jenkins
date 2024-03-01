pipeline {
    agent none

    options {
            timeout(time: 15, unit: 'MINUTES')
    }

    env.DEPLOY_TO_LAST = input message: 'User input required',
        parameters: [choice(name: 'Deploy to LAST', choices: 'hello\nbuild')]

    if (env.DEPLOY_TO_LAST == 'hello'){    

      stages {
          stage('Hello') {
            agent {
                docker {
                    image 'maven:3.9.3-eclipse-temurin-17'
                    args  '-v /tmp:/tmp'
                    reuseNode true
                }
            }

            steps {
                sh 'hostname'
                sh 'touch /tmp/bollocks'
                echo 'Hello World'
            }
          }
     }
        
    if (env.DEPLOY_TO_LAST == 'build'){    
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
}
