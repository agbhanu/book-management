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

        stage('Run WS Script') {
           steps{
              sh 'java -jar /var/jenkins_home/wss-unified-agent/wss-unified-agent.jar -d ./'
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

    post{

      always{
        emailext(
           subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}",
           body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
           recipientProviders: [[$class: 'DevelopersRecipientProvider']],
           attachLog: true
        )
      }

      success{
         office365ConnectorSend (
            color: '#006400',
            status: '*${currentBuild.currentResult}:* Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}',
            webhookUrl: 'https://outlook.office.com/webhook/7c62aabd-b54b-4a02-90b8-1aa1eff78fdf@1f4beacd-b7aa-49b2-aaa1-b8525cb257e0/JenkinsCI/e0544b4c39d64e6297d7b2d1b9f13012/01e376f0-c6a6-4b13-aa1e-484be8466bad'
         )
      }

      failure{
        office365ConnectorSend (
           color: '#8B0000',
           status: '*${currentBuild.currentResult}:* Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}',
           webhookUrl: 'https://outlook.office.com/webhook/7c62aabd-b54b-4a02-90b8-1aa1eff78fdf@1f4beacd-b7aa-49b2-aaa1-b8525cb257e0/JenkinsCI/e0544b4c39d64e6297d7b2d1b9f13012/01e376f0-c6a6-4b13-aa1e-484be8466bad'
        )
      }
    }
}

