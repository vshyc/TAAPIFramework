@Library(['jenkins-kubernetes-library']) _
import com.tipico.config.ChartConfig

pipeline {
    agent any
    stages {
        stage('Build test code') {
            withMaven(jdk: 'JDK 12 LATEST', maven: 'Maven 3.6.3', options: [artifactsPublisher(disabled: true), openTasksPublisher(disabled: true), junitPublisher(disabled: true)]){
                sh 'mvn clean install -DskipTests'
                }
        }
        stage('Execute test') {
            withCredentials([string(credentialsId: 'reqtest-pat', variable: 'PAT')]) {
                   withMaven(jdk: 'JDK 12 LATEST', maven: 'Maven 3.6.3', options: [artifactsPublisher(disabled: true), openTasksPublisher(disabled: true), junitPublisher(disabled: true)]) {
                         sh "mvn test -Dtest.plan.id=${testPlanId} -Dreqtest.PAT='${PAT} -Denv=${env} -Dgroups=${filter} -Dreqtest.runtype=${runType} -Dtest.run.id=${testRunId} '"
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
