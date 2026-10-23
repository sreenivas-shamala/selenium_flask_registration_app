pipeline {

    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url:'https://github.com/sreenivas-shamala/selenium_jenkins_integration.git'
                
            }
        }

        stage('Clean') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('Run Selenium Tests') {
            steps {
                sh 'mvn test'
            }
        }
    }

    post {

        always {

            echo '=== TestNG Report Publishing ==='

        sh '''
            echo "=== TestNG XML reports ==="
            find target/surefire-reports -type f -name "*.xml" -print
        '''

        testNG(
            reportFilenamePattern: '**/testng-results.xml',
            showFailedBuilds: true
        )

        archiveArtifacts(
            artifacts: 'target/surefire-reports/**/*,test-output/**/*',
            allowEmptyArchive: true
        )
        }

        success {
            echo 'Selenium test execution completed successfully.'
        }

        failure {
            echo 'Selenium test execution failed.'
        }
    }
}
