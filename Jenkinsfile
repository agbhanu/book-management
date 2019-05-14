pipeline{

    agent {
        docker {
            image 'gradle:jdk8-slim'
            args '-v /root/book_management:/home/gradle/.gradle'
        }
    }

    stages{

        // clean project
        stage('clean'){
            steps{
              sh "./gradlew clean"
            }
        }

        // static code analysis using SonarQube
        stage('SonarQube analysis') {
           steps{
              withSonarQubeEnv('SonarQube') {
                  sh './gradlew sonarqube -x test'
              }
           }
        }

        // check quality gate status on SonarQube dashboard
        stage("Quality Gate Status Check") {
           steps {
              timeout(time: 10, unit: 'MINUTES') {
                 waitForQualityGate abortPipeline: true
              }
           }
        }

        // build project
        stage('build'){
            steps{
                sh "./gradlew build -x test"
            }
        }
    }
}

