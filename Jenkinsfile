pipeline {
    agent {
        label 'ubuntu' // Укажите метку агента, если Jenkins настроен на использование конкретных агентов
    }
    tools {
        jdk 'jdk17' // Убедитесь, что JDK 17 настроен в Global Tool Configuration как 'jdk17'
    }
    environment {
        PROFILE = 'test'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Install and Start Application') {
            steps {
                script {
                    // Запускаем приложение
                    sh 'nohup java -jar ./artifacts/app-ibank.jar -P:profile=${PROFILE} &'
                    // Даем права на выполнение gradlew
                    sh 'chmod +x gradlew'
                }
            }
        }
        stage('Run Tests') {
            steps {
                // Выполнение тестов с параметром для headless-режима
                sh './gradlew test --info -Dselenide.headless=true'
            }
        }
    }
    post {
        always {
            echo 'Pipeline completed'
        }
        cleanup {
            // Любые необходимые команды для завершения, например, завершение приложения
            sh 'pkill -f app-ibank.jar' // Пример для завершения приложения
        }
    }
}
