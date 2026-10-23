
pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/sreenivas-shamala/selenium_jenkins_integration.git'
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
        echo "=== Workspace ==="
        pwd

        echo "=== TestNG XML reports ==="
        find . -type f -name "testng-results.xml" -print || true

        echo "=== Surefire XML reports ==="
        find . -type f -path "*/surefire-reports/*.xml" -print || true

        echo "=== Screenshots ==="
        find . -type f \\( -iname "*.png" -o -iname "*.jpg" \\) -print || true
    '''

    script {
        if (fileExists('target/surefire-reports/testng-results.xml')) {
            testNG(
                reportFilenamePattern: '**/testng-results.xml',
                showFailedBuilds: true
            )
        } else {
            echo 'TestNG report not found. Check Maven and TestNG configuration.'
        }
    }

    // Archive reports and screenshots
    archiveArtifacts(
        artifacts: '**/surefire-reports/**/*,**/test-output/**/*,**/screenshots/**/*.png,**/screenshots/**/*.jpg,screenshots/**/*.png,screenshots/**/*.jpg',
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
