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

        // run unit tests
        stage('run unit tests'){
          steps{
            sh "./gradlew test"
          }
        }

        // code coverage analysis using SonarQube
        stage('code coverage analysis') {
           steps{
              withSonarQubeEnv('SonarQube') {
                 sh './gradlew sonarqube -x test'
              }
           }
        }

        stage('Configure gradle deployer') {
           steps {
             rtGradleDeployer (
                id: "book_management_deployer",
                serverId: "PSL-Blockchain",
                repo: "gradle-dev-local",
                deployIvyDescriptors: false,
                mavenCompatible: true
             )
           }
        }

        stage('Deploy to artifactory') {
           steps {
              rtGradleRun (
                 useWrapper: true,
                 buildFile: 'build.gradle',
                 tasks: 'artifactoryPublish',
                 deployerId: "book_management_deployer"
              )
           }
        }

        stage ('Publish build info') {
           steps {
              rtPublishBuildInfo (
                  serverId: "PSL-Blockchain"
              )
           }
        }
    }
}

