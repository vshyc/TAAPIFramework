parameters{
        string(name: 'featureBranch', defaultValue : "master",  description : 'branch')
        string(name: 'testPlanId')
    }

    node('qa_uitest||uitest') {
        timeout(40) {
            ansiColor('xterm') {
                timestamps {
                    stage('Checkout') {
                       checkout([$class: 'GitSCM', branches: [[name: params.featureBranch]], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'CloneOption', depth: 0, noTags: true, reference: '', shallow: true, timeout: 5]], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'GIT_KEY', url: 'ssh://git@bitbucket.booxdev.com:7999/ta/ta-limit-service.git']]])
                    }
                    stage('Build test code') {
                        withMaven(jdk: 'JDK 12 LATEST', maven: 'Maven 3.6.3', options: [artifactsPublisher(disabled: true), openTasksPublisher(disabled: true), junitPublisher(disabled: true)]){
                            sh 'mvn clean install -DskipTests'
                            }
                        }
                    stage('Execute test') {
                        withCredentials([string(credentialsId: 'reqtest-pat', variable: 'PAT')]) {
                               withMaven(jdk: 'JDK 12 LATEST', maven: 'Maven 3.6.3', options: [artifactsPublisher(disabled: true), openTasksPublisher(disabled: true), junitPublisher(disabled: true)]) {
                                     sh "mvn test -Dtest.plan.id=${testPlanId} -Dreqtest.PAT='${PAT} -Denv=${env} -Dgroups=${filter} -Dreqtest.runtype=${runType} '"
                                }
                        }
                    }
                    stage('Generate allure report') {
                                steps {
                                    script {
                                        allure([
                                                includeProperties: true,
                                                jdk              : 'JDK 12 LATEST',
                                                properties       : [],
                                                reportBuildPolicy: 'ALWAYS',
                                                results          : [[path: 'target/allure-results']]
                                        ])
                                    }
                                }
                            }
         }}}}

