@Library(['jenkins-kubernetes-library']) _
import com.tipico.config.ChartConfig
    JDK_INSTANCE = env.JDK_INSTANCE ?: 'JDK 8 AUTO'
    MAVEN_INSTANCE = env.MAVEN_INSTANCE ?: 'Maven 3.6.2'

pipeline {
    agent any
    stages {
        stage('Build test code') {
            steps {
            withSimpleMaven(jdk: JDK_INSTANCE, maven: MAVEN_INSTANCE){
                sh 'mvn clean install -DskipTests'
                }
            }
        }
        stage('Execute test') {
            steps {
            withSimpleMaven(jdk: JDK_INSTANCE, maven: MAVEN_INSTANCE){
                sh 'mvn test'
            }
            }
        }
        stage('Generate allure report') {
                    steps {
                        script {
                            allure([
                                    includeProperties: false,
                                    jdk              : '',
                                    properties       : [],
                                    reportBuildPolicy: 'ALWAYS',
                                    results          : [[path: 'target/allure-results']]
                            ])
                        }
                    }
                }
}}
