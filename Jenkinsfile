pipeline {
    agent any

    environment {
        JAVA_HOME = tool name: 'jdk11', type: 'JDK'
    }

    stages {
        stage('Checkout') {
            steps {
                // Клонируем репозиторий с GitHub
                git branch: 'main', url: 'https://github.com/your-repo-url'
            }
        }

        stage('Install Dependencies') {
            steps {
                script {
                    // Запускаем jar файл с нужным профилем
                    sh 'java -jar ./artifacts/app-ibank.jar -P:profile=test &'
                    // Даем права на выполнение gradlew
                    sh 'chmod +x gradlew'
                }
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    // Запускаем тесты с параметрами
                    sh './gradlew test --info -Dselenide.headless=true'
                }
            }
        }
    }

    post {
        always {
            // Этот блок выполняется всегда (например, для уведомлений)
            echo 'Cleanup or notifications can be placed here.'
        }
    }
}
